package com.mz.kalah.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mz.kalah.controllers.Game;
import com.mz.kalah.exceptions.BusinessException;

@Service
public class MancalaServiceImpl implements MancalaService {
	// Game holder
	final Map<Long, Kalah> games;
	final AtomicLong uniqueId;

	private final KalahLogic kalahLogic;

	public MancalaServiceImpl(KalahLogic kalahLogic) {
		this.kalahLogic = kalahLogic;
		this.games = new ConcurrentHashMap<Long, Kalah>();
		this.uniqueId = new AtomicLong(1);
	}

	@Override
	public Game createGame() throws Exception {

		// getting new game id
		long gameId = uniqueId.getAndIncrement();
		// making uri
		URI uri = makeURI(gameId);

		Kalah kalah = new Kalah();
		games.put(gameId, kalah);

		Game game = new Game(String.valueOf(gameId), uri);
		return game;
	}

	@Override
	public Game move(long gameId, int pitId) throws Exception {
		Kalah kalah = games.get(gameId);
		if (kalah == null)
			throw new BusinessException("There in no such a game.");

		kalahLogic.move(kalah, pitId);
		URI uri = makeURI(gameId);
		// Converting array to map to show index!
		Map<Integer, String> map = new HashMap<>();
		IntStream.range(0, kalah.getState().length).forEach(i -> map.put(i + 1, kalah.getState()[i] + ""));
		Game game = new Game(String.valueOf(gameId), uri, map, kalah.getWinner());
		return game;
	}

	private URI makeURI(long gameID) throws URISyntaxException {
		ServletUriComponentsBuilder uriComponentsbuilder = ServletUriComponentsBuilder.fromCurrentRequest();
		URI uri = uriComponentsbuilder.build().toUri();
		String url = uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort() + "/games/" + gameID;
		return new URI(url);
	}
}
