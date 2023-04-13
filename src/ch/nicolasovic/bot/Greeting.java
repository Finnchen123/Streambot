package ch.nicolasovic.bot;

public class Greeting {

	String username;
	String message;
	boolean isGreeted;
	
	public Greeting(String username, String message) {
		this.username = username.toLowerCase();
		this.message = message;
		this.isGreeted = false;
	}
	
	public void sendGreeting(Client client) {
		if(!isGreeted) {
			client.privmsg(message);
			isGreeted = true;
		}
	}
	
	public boolean isUser(String name) {
		return this.username.equals(name.toLowerCase());
	}
}
