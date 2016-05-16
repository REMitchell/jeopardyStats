package com.ryanemitchell.entities;

public class Score {

	int id;
	Game game;
	Player player;
	int breakScore;
	int round1;
	int round2;
	int finalScore;
	int coryat;
	
	public Score() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getBreakScore() {
		return breakScore;
	}

	public void setBreakScore(int breakScore) {
		this.breakScore = breakScore;
	}

	public int getRound1() {
		return round1;
	}

	public void setRound1(int round1) {
		this.round1 = round1;
	}

	public int getRound2() {
		return round2;
	}

	public void setRound2(int round2) {
		this.round2 = round2;
	}

	public int getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}

	public int getCoryat() {
		return coryat;
	}

	public void setCoryat(int coryat) {
		this.coryat = coryat;
	}
	
	
}
