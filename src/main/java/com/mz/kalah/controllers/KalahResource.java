package com.mz.kalah.controllers;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.kalah.services.MancalaService;

@RestController
@Validated
public class KalahResource {

	MancalaService mancalaService;

	public KalahResource(MancalaService mancalaService) {
		this.mancalaService = mancalaService;
	}

	@RequestMapping(value = "/games", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> createGame() throws Exception {
		Game game = mancalaService.createGame();
		return new ResponseEntity<>(game, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/games/{gameId}/pits/{pitId}", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> move(
			@PathVariable(value = "gameId") @NotNull(message = "Please provide a gameId")
			@Min(value = 1, message = "Please provide a valid gameId. Minimum is 1")long gameId,
			@PathVariable(value = "pitId") @NotNull(message = "Please provide a pitId") 
			@Min(value = 1, message = "Please provide a valid pitId. Minimum is 1")
			@Max(value = 13, message = "Please provide a valid pitId. Maximum is 13")int pitId) throws Exception {

		Game game = mancalaService.move(gameId, pitId);

		return new ResponseEntity<>(game, HttpStatus.OK);
	}

}
