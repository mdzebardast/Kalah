package com.mz.kalah.services;

import com.mz.kalah.controllers.Game;

public interface MancalaService {
	Game createGame() throws Exception;
	Game move(long gameId, int pitId) throws Exception;
}
