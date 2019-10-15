package com.mz.kalah.services;

public class Kalah {
	public static final int TOTAL_PITS = 14, STONES = 6, PLAY_PITS = 6, FIRST_SCORE_PIT = 6, SECOND_SCORE_PIT = 13;

	public static final int FIRST = 0, SECOND = 1;

	public static final int NOT_FINISHED = -1;
	
	/** current player */
	private int player ;

	public int[] state = new int[TOTAL_PITS];
	
	private String winner = null;

	public Kalah() {
		state = new int[TOTAL_PITS];
		// six pieces initially in each pit...
		for (int i = 0; i < TOTAL_PITS; i++)
			state[i] = STONES;
		// ...except scoring pits.
		state[FIRST_SCORE_PIT] = state[SECOND_SCORE_PIT] = 0;
		// FIRST player is base on first Request
		player = NOT_FINISHED;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int[] getState() {
		return state;
	}

	public void setState(int[] state) {
		this.state = state;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("    13 12 11 10  9  8\n-------------------------\n|  ");
		for (int i = SECOND_SCORE_PIT - 1; i > FIRST_SCORE_PIT; i--)
			if (state[i] > 9)
				sb.append("|" + state[i]);
			else
				sb.append("| " + state[i]);
		sb.append("|  |");
		if (player == SECOND)
			sb.append(" <--");
		if (state[SECOND_SCORE_PIT] > 9)
			sb.append("\n|" + state[SECOND_SCORE_PIT]);
		else
			sb.append("\n| " + state[SECOND_SCORE_PIT]);
		sb.append("|-----------------|");
		if (state[FIRST_SCORE_PIT] > 9)
			sb.append(state[FIRST_SCORE_PIT]);
		else
			sb.append(" " + state[FIRST_SCORE_PIT]);
		sb.append("|\n|  ");
		for (int i = 0; i < FIRST_SCORE_PIT; i++)
			if (state[i] > 9)
				sb.append("|" + state[i]);
			else
				sb.append("| " + state[i]);
		sb.append("|  |");
		if (player == FIRST)
			sb.append(" <--");
		sb.append("\n-------------------------\n     1  2  3  4  5  6\n");
		return sb.toString();
	}
}
