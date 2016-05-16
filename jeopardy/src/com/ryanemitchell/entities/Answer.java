package com.ryanemitchell.entities;

public class Answer{

	int id;
	Question question;
	Player player;
	boolean correct;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public boolean isCorrect() {
		return correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	
}
