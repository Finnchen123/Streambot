package ch.nicolasovic.bot.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import ch.nicolasovic.bot.Console;
import ch.nicolasovic.bot.User;

public class DatabaseHandler {
	Connection conn;
	
	public void connect() {
		try {
			if(conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/"+PropertieHandler.getProperty("DB_NAME")+"?user=botAccount&password="+PropertieHandler.getProperty("DB_PASS"));
				Console.outputInformation("Connection to DB established");
			}
		} catch (SQLException e) {
			Console.outputError("Connection to DB failed");
			System.out.println(e);
		}
	}
	
	public void disconnect() {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
				Console.outputInformation("Connection to DB closed");
			}
		} catch (SQLException e) {
			Console.outputError("Connection to DB failed to close");
		}
	}
	
	public void saveList(HashMap<String, User> users) {
		Console.outputInformation("Saving users");
		clearTable();
		connect();
		for(User user : users.values()) {
			user.saveUser(conn);
		}
		disconnect();
	}
	
	private void clearTable() {
		Console.outputInformation("Clearing userlist");
		connect();
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("TRUNCATE TABLE [botDB].[dbo].[userStatistics]");
		} catch (SQLException e) {
			Console.outputError("Unable to clear userStatistics");
		}
		disconnect();
	}
	
	public HashMap<String, User> loadList(){
		Console.outputInformation("Loading userlist");
		connect();
		HashMap<String, User> users = new HashMap<String, User>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM [botDB].[dbo].[userStatistics]");
			while(rs.next()) {
				String username = rs.getString("username");
				int points = rs.getInt("points");
				int messages = rs.getInt("messages");
				users.put(username, new User(username, points, messages));
			}
		} catch (SQLException e) {
			Console.outputError("Unable to load userStatistics");
		}
		disconnect();
		return users;
	}
	
	public boolean saveSuggestion(String username, String suggestion) {
		Console.outputInformation("Saving suggestion");
		connect();
		try {
			PreparedStatement stmt = conn.prepareStatement("Insert into [botDB].[dbo].[userSuggestions] (username, suggestion) VALUES (?,?)");
			stmt.setString(1, username);
			stmt.setString(2, suggestion);
			stmt.execute();
			disconnect();
			return true;
		} catch (SQLException e) {
			Console.outputError("Failed to save suggestion");
			disconnect();
			return false;
		}
	}
}
