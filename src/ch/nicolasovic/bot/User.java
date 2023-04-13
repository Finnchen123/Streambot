package ch.nicolasovic.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
	String username;
	int points;
	int messages;
	
	public User(String username, int points, int messages) {
		this.username = username.toLowerCase().replace(" ", "");
		this.points = points;
		this.messages = messages;
	}
	
	public String outputPoints() {
		return username + " hat schon " + messages + " Nachrichten geschrieben und hat " + points + " Punkte.";
	}
	
	public void addPoints(int amount) {
		points = points + amount;
	}
	
	public void removePoints(int amount) {
		if(points - amount == 0) {
			points = 0;
		}
		else {
			points = points - amount;
		}
	}
	
	public void addMessage() {
		messages++;
	}
	
	public void saveUser(Connection conn) {
		try {
			PreparedStatement stmt = conn.prepareStatement("Insert into [botDB].[dbo].[userStatistics] (username, points, messages) VALUES (?,?,?)");
			stmt.setString(1, username);
			stmt.setInt(2, points);
			stmt.setInt(3, messages);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getPoints() {
		return this.points;
	}
}
