package ch.nicolasovic.bot.handler;

import java.util.HashMap;

import ch.nicolasovic.bot.User;

public class PointHandler {
	
	HashMap<String, User> points;
	DatabaseHandler dbHandler;
	
	public PointHandler() {
		points = new HashMap<String, User>();
		dbHandler = new DatabaseHandler();
		loadList();
	}
	
	public void addPoints(String username, int amount) {
		if(points.containsKey(username)) {
			points.get(username).addPoints(amount);
		}
		
	}
	
	public void addPointsToAll(int amount) {
		for(String key : points.keySet()) {
			points.get(key).addPoints(amount);
		}	
	}
	
	public void removePoints(String username, int amount) {
		if(points.containsKey(username)) {
			points.get(username).removePoints(amount);
		}
	}

	public void addUser(String username) {
		if(!points.containsKey(username)) {
			points.put(username, new User(username, 0 , 0));
		}
	}
	
	public void addMessage(String username) {
		if(points.containsKey(username)) {
			points.get(username).addMessage();
			addPoints(username, 10);
		}
	}
	
	public void endStream() {
		for(String key : points.keySet()) {
			points.get(key).addPoints(25);
		}
		saveList();
	}
	
	public String outputUser(String username) {
		String result = "";
		if(points.containsKey(username)) {
			result = points.get(username).outputPoints();
		}
		return result;
	}
	
	private void loadList() {
		points = dbHandler.loadList();
	}
	
	public void saveList() {
		dbHandler.saveList(points);
	}
	
	public int getPointsForUser(String username) {
		int result = 0;
		if(points.containsKey(username)) {
			result = points.get(username).getPoints();
		}
		return result;
	}
}
