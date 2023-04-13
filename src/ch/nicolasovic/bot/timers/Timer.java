package ch.nicolasovic.bot.timers;


import ch.nicolasovic.bot.Client;

public class Timer {
	
	int messageCounter;
	int maxMessageCounter;
	String msg;
	
	public Timer(int maxMessageCounter, String msg) {
		this.messageCounter = 0;
		this.maxMessageCounter = maxMessageCounter;
		this.msg = msg;
	}
	
	public void increaseCounter(Client client) {
		this.messageCounter++;
		if(this.messageCounter >= this.maxMessageCounter) {
			client.privmsg(msg);
			this.messageCounter = 0;
		}
	}
}
