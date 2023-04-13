package ch.nicolasovic.bot.minigames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.PointHandler;

public class BattleshipMinigame extends Minigame{
	
	ArrayList<String> participants;
	int cost = 0;
	int state = 0;
	ScheduledExecutorService executor;
	ArrayList<String> coordinates;
	boolean scheduled = false;
	int counter = 0;
	
	public BattleshipMinigame(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases, points);
		participants = new ArrayList<String>();
		executor = Executors.newSingleThreadScheduledExecutor();
		coordinates = new ArrayList<String>();
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String[] parts = message.content.split(" ");
		switch(state) {
		case 0:
			handleIdleInput(message.nickname, client, parts);
			break;
		case 1:
			handleJoinInput(message.nickname, client, parts);
			break;
		case 2:
			handleRunningInput(message.nickname, client, parts);
			break;
		case 3:
			handleFinishInput(message.nickname, client, parts);
			break;
		}
	}
	
	private void handleIdleInput(String nickname, Client client, String[] parts) {
		if(parts.length == 1) {
			if(points.getPointsForUser(nickname) >= cost) {
				participants.add(nickname);
				points.removePoints(nickname, cost);
				client.privmsg("Eine Runde Schiffeversenken wurde gestartet! Gib #ship ein um mitzumachen. (1min Zeit)");
				state = 1;
			}
			else {
				client.privmsg("Dafür hast du nicht genug Punkte.");
			}
		}
		else {
			client.privmsg("Derzeit läuft keine Runde. Gib #ship ein, um eine Runde zu starten.");
		}
	}
	
	private void handleJoinInput(String nickname, Client client, String[] parts) {
		if(parts.length == 1) {
			if(points.getPointsForUser(nickname) >= cost) {
				if(!participants.contains(nickname)) {
					participants.add(nickname);
					points.removePoints(nickname, cost);
					client.privmsg("@" + nickname + " ist der Partie beigetreten.");
					if(!scheduled) {
						executor.schedule(() -> {
							if(participants.size() >= 2) {
								state = 2;
								client.privmsg("Alle Teilnehmer sind versammelt. Gebt #ship [1-" + participants.size() * 2 + "] [1-" + participants.size() * 2 + "] ein, um eure Schüsse abzugeben. (1min Zeit)");
								executor.schedule(() -> {
									client.privmsg("Die Runde ist vorbei. Die Punkteverteilung wird nun ermittelt.");
									state = 3;
									calculateWinner(client);
								}, 1, TimeUnit.MINUTES);
							}
							else {
								state = 0;
								client.privmsg("Leider haben sich nicht genug Teilnehmer angemeldet.");
								participants.clear();
							}
						}, 1, TimeUnit.MINUTES);
						scheduled = true;
					}
				}
				else {
					client.privmsg("Du bist schon in der Runde.");
				}
			}
			else {
				client.privmsg("Dafür hast du nicht genug Punkte.");
			}
		}
		else {
			client.privmsg("Kein valider Input.");
		}
	}
	
	private void handleRunningInput(String nickname, Client client, String[] parts) {
		if(parts.length == 3) {
			if(participants.contains(nickname)) {
				coordinates.add(nickname + " " + parts[1] + ":" + parts[2] + " " + counter);
				counter++;
			}
			else {
				client.privmsg("Du bist kein Mitglied der Runde.");
			}
		}
		else {
			client.privmsg("Kein valider Input.");
		}
	}
	
	private void handleFinishInput(String nickname, Client client, String[] parts) {
		client.privmsg("Die Runde ist vorbei. Es wird kein weiterer Input akzeptiert.");
	}
	
	private void calculateWinner(Client client) {
		String[][] board = generateBoard();
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		String username = "";
		String point = "";
		
		for(String coordinate : coordinates) {
			username = coordinate.split(" ")[0];
			point = coordinate.split(" ")[1];
			if(!result.containsKey(username)) {
				result.put(username, 0);
			}
			System.out.println(coordinate);
			if(checkUserInput(point)) {
				System.out.println("valid");
				if(checkBoard(board, point, participants.indexOf(username))) {
					System.out.println("hit");
					result.put(username, result.get(username) + 50);
				}
			}
			System.out.println("---------------");
		}
		for(String user : result.keySet()) {
			points.addPoints(user, result.get(user));
			client.privmsg("@" + user + " hat " + result.get(user) + "Punkte gewonnen.");
		}
		participants.clear();
		state = 0;
		scheduled = false;
		counter = 0;
	}
	
	private boolean checkBoard(String[][] board, String point, int userIndex) {
		boolean result = true;
		int x = Integer.parseInt(point.split(":")[0]) - 1;
		int y = Integer.parseInt(point.split(":")[1]) - 1;
		//System.out.println("Board: " + board[x][y] + ", index: " + userIndex);
		if(board[x][y] == "W") {
			result = false;
		}
		else if(board[x][y] == userIndex+"") {
			result = false;
		}
		return result;
	}
	
	private String[][] generateBoard(){
		String[][] board = new String[participants.size() * 2][participants.size() * 2];
		Random rand = new Random();
		int x = 0;
		int y = 0;
		for(int i = 0; i < participants.size(); i++) {
			x = rand.nextInt(board.length);
			y = rand.nextInt(board.length);	
			if(board[x][y] == null) {
				board[x][y] = i + "";
			}
			else {
				i--;
			}
		}
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] == null) {
					board[i][j] = "W";
				}
			}
		}
		return board;
	}
	
	private boolean checkUserInput(String userinput) {
		boolean result = false;
		try {
			int x = Integer.parseInt(userinput.split(":")[0]);
			int y = Integer.parseInt(userinput.split(":")[1]);
			if(x > 0 && x <= participants.size() * 2 && y > 0 && y <= participants.size() * 2) {
				result = true;
			}
		}
		catch(Exception e) {
			//
		}
		return result;
	}
}
