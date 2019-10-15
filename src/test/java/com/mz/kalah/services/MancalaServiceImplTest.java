package com.mz.kalah.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mz.kalah.controllers.Game;

/**
 * unit test for move game in different cases
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MancalaServiceImplTest {

	@Autowired
	private MancalaServiceImpl mancalaServiceImpl;

	Game createdGame;

	@Before
	public void setUp() throws Exception {
		// creating game
		createdGame = mancalaServiceImpl.createGame();
	}
	
	@Test
	public void testCreateGame() throws Exception {
		// creating game
		Game testGame = mancalaServiceImpl.createGame();
		//assert
		assertNotNull(testGame.getId());
	}
	
	@Test
	public void testMove() throws Exception {
		// -given
		Long gameId = Long.parseLong(createdGame.getId());
		int pitId = 5;
		// preparing expected game!
		Kalah kalah = new Kalah();
		for (int i = 0; i < Kalah.SECOND_SCORE_PIT; i++)
			kalah.state[i] = 6;

		kalah.state[pitId - 1] = 0;
		kalah.state[Kalah.FIRST_SCORE_PIT - 1] = 7;
		kalah.state[Kalah.FIRST_SCORE_PIT] = 1;
		kalah.state[Kalah.FIRST_SCORE_PIT + 1] = 7;
		kalah.state[Kalah.FIRST_SCORE_PIT + 2] = 7;
		kalah.state[Kalah.FIRST_SCORE_PIT + 3] = 7;
		kalah.state[Kalah.FIRST_SCORE_PIT + 4] = 7;

		// Converting array to map to show index!
		Map<Integer, String> map = new HashMap<>();
		IntStream.range(0, kalah.getState().length).forEach(i -> map.put(i + 1, kalah.getState()[i] + ""));
		Game expectedGame = new Game(String.valueOf(gameId), null, map, kalah.getWinner());

		// run service method
		Game game = mancalaServiceImpl.move(gameId, pitId);

		// -assert
		assertEquals(expectedGame.getStatus(), game.getStatus());
	}

	@Test
	public void testRepeatedTurnInMove() throws Exception {

		Kalah kalahGame = mancalaServiceImpl.games.get(Long.parseLong(createdGame.getId()));
		// -given
		Long gameId = Long.parseLong(createdGame.getId());
		int pitId = 1;
		// run service method
		mancalaServiceImpl.move(gameId, pitId);

		// -assert
		assertEquals(Kalah.FIRST, kalahGame.getPlayer());
	}
}
