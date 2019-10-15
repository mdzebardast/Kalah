package com.mz.kalah.controllers;

import java.net.URI;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Game {
	private String id;
	private URI uri;
	@JsonInclude(Include.NON_NULL)
	private Map<Integer, String> status;
	@JsonInclude(Include.NON_NULL)
	private String winner;

	public Game(String id, URI uri) {
		this.id = id;
		this.uri = uri;
	}

	public Game(String id, URI uri, Map<Integer, String> status, String winner) {
		this.id = id;
		this.uri = uri;
		this.status = status;
		this.winner = winner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public Map<Integer, String> getStatus() {
		return status;
	}

	public void setStatus(Map<Integer, String> status) {
		this.status = status;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
}
