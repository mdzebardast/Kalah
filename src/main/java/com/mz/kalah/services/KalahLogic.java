package com.mz.kalah.services;

import org.springframework.stereotype.Component;

import com.mz.kalah.exceptions.BusinessException;

@Component
public class KalahLogic {
	
	public void move(Kalah kalah, int pitId) throws Exception {
		int player;
		int scorePit, oppositePit;
		int position = pitId - 1;

		if (kalah.getWinner() != null)
			throw new BusinessException("Game has been finished. The winner is : " + kalah.getWinner());

		if (position == Kalah.FIRST_SCORE_PIT || position == Kalah.SECOND_SCORE_PIT)
			throw new BusinessException("Please provide correct pit number. You can just pick up from your pits");
		// Specifying player turn
		if (kalah.getPlayer() != Kalah.NOT_FINISHED)
			player = kalah.getPlayer();
		else {
			player = pitId <= Kalah.FIRST_SCORE_PIT ? Kalah.FIRST : Kalah.SECOND;
		}
		
		if (player != Kalah.NOT_FINISHED) {
			if (player == Kalah.FIRST && position > Kalah.FIRST_SCORE_PIT - 1)
				throw new BusinessException("It's First player turn so you should provide correct pit number.");

			if (player == Kalah.SECOND && position <= Kalah.FIRST_SCORE_PIT - 1)
				throw new BusinessException("It's Second player turn so you should provide correct pit number.");
		}

		// Take the pieces from the indicated pit.
		int pieces = kalah.state[position];
		kalah.state[position] = 0;

		while (pieces > 0) {
			position = (position + 1) % Kalah.TOTAL_PITS;

			// Skip over opponent's scoring pit
			if (position == ((player == Kalah.FIRST) ? Kalah.SECOND_SCORE_PIT : Kalah.FIRST_SCORE_PIT))
				continue;

			// Distribute piece
			kalah.state[position]++;
			pieces--;
		}

		// If the last piece distributed landed in an empty pit on
		// one's side opposite a non-empty pit, capture both the last
		// piece and the pieces opposite.
		scorePit = (player == Kalah.FIRST) ? Kalah.FIRST_SCORE_PIT : Kalah.SECOND_SCORE_PIT;
		// if last piece distributed in empty pit on own side
		if (kalah.state[position] == 1 && ((player == Kalah.FIRST && position < Kalah.FIRST_SCORE_PIT)
				|| (player == Kalah.SECOND && position > Kalah.FIRST_SCORE_PIT && position < Kalah.SECOND_SCORE_PIT))) {

			oppositePit = Kalah.SECOND_SCORE_PIT - position - 1;
			kalah.state[scorePit]++;
			kalah.state[position]--;
			if (kalah.state[oppositePit] > 0) {
				kalah.state[scorePit] += kalah.state[oppositePit];
				kalah.state[oppositePit] = 0;
			}
		}

		// If the last piece did not land in one's scoring pit, then
		// the player changes.
		if (position != scorePit)
			player = (player == Kalah.FIRST) ? Kalah.SECOND : Kalah.FIRST;

		kalah.setPlayer(player);
		// Checking for game over
		int win = gameOver(kalah);
		int sum = 0;
		// Collecting stones
		if (win == Kalah.FIRST) {
			System.out.println("first Game over");
			for (position = Kalah.SECOND_SCORE_PIT - Kalah.PLAY_PITS; position < Kalah.SECOND_SCORE_PIT; position++) {
				sum += kalah.state[position];
				kalah.state[position] = 0;
			}
			kalah.state[Kalah.SECOND_SCORE_PIT] = kalah.state[Kalah.SECOND_SCORE_PIT] + sum;
		} else if (win == Kalah.SECOND) {
			System.out.println("second Game over");
			for (position = Kalah.FIRST_SCORE_PIT - Kalah.PLAY_PITS; position < Kalah.FIRST_SCORE_PIT; position++) {
				sum += kalah.state[position];
				kalah.state[position] = 0;
			}
			kalah.state[Kalah.FIRST_SCORE_PIT] = kalah.state[Kalah.FIRST_SCORE_PIT] + sum;
		}

		// Specifying winner
		if (win != Kalah.NOT_FINISHED)
			if (kalah.state[Kalah.FIRST_SCORE_PIT] > kalah.state[Kalah.SECOND_SCORE_PIT])
				kalah.setWinner("First player");
			else
				kalah.setWinner("Second player");
	}

	/*
	 * Check who finished the game
	 */
	public int gameOver(Kalah kalah) {
		boolean noPiecesLeft = true;
		for (int position = Kalah.FIRST_SCORE_PIT - Kalah.PLAY_PITS; noPiecesLeft
				&& (position < Kalah.FIRST_SCORE_PIT); position++)
			noPiecesLeft = noPiecesLeft && (kalah.state[position] == 0);

		if (noPiecesLeft)
			return Kalah.FIRST;

		noPiecesLeft = true;
		for (int position = Kalah.SECOND_SCORE_PIT - Kalah.PLAY_PITS; noPiecesLeft
				&& (position < Kalah.SECOND_SCORE_PIT); position++)
			noPiecesLeft = noPiecesLeft && (kalah.state[position] == 0);
		if (noPiecesLeft)
			return Kalah.SECOND;
		return Kalah.NOT_FINISHED;
	}
}
