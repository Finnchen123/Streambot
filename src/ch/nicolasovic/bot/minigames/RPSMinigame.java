package ch.nicolasovic.bot.minigames;

import java.util.Random;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.PointHandler;

public class RPSMinigame extends Minigame{

	public RPSMinigame(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases, points);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String[] parts = message.content.split(" ");
		if(checkInput(client, message, parts)) {
			int amount = Integer.parseInt(parts[1]);
			points.removePoints(message.nickname, amount);
			if(hasWon(parts[2].toLowerCase()) == 'W') {
				points.addPoints(message.nickname, amount*2);
				client.privmsg("Du hast gewonnen!");
			}
			else if(hasWon(parts[2].toLowerCase()) == 'U') {
				points.addPoints(message.nickname, amount);
				client.privmsg("Ganz knapp ein Unentschieden!");
			}
			else {
				client.privmsg("Leider verloren!");
			}
		}
	}
	
	private char hasWon(String option) {
		char result = 'L';
		Random rand = new Random();
		int botOption = rand.nextInt(3); //{"schere","stein","papier"}
		switch(option) {
			case "schere":
				if(botOption == 2) {
					result = 'W';
				}
				else if(botOption == 0) {
					result = 'U';
				}
				break;
			case "stein":
				if(botOption == 0) {
					result = 'W';
				}
				else if(botOption == 1) {
					result = 'U';
				}
				break;
			case "papier":
				if(botOption == 1) {
					result = 'W';
				}
				else if(botOption == 2) {
					result = 'U';
				}
				break;
		}
		return result;
	}
	
	private boolean checkInput(Client client, Message message, String[] parts) {
		boolean result = false;
		if(parts.length != 3) {
			client.privmsg("Bitte nutz folgende Vorlage: #rps [einsatz] [schere/stein/papier]");
		}
		else {
			try {
				int amount = Integer.parseInt(parts[1]);
				if(amount <= points.getPointsForUser(message.nickname) && amount >= 0) {
					if(parts[2].equalsIgnoreCase("schere") || parts[2].equalsIgnoreCase("stein") || parts[2].equalsIgnoreCase("papier")) {
						result = true;
					}
					else {
						client.privmsg("Bitte gib eine valide Spieloption ein");
					}	
				}
				else {
					client.privmsg("Dafür hast du nicht genug Punkte.");
				}
			}
			catch(Exception e) {
				client.privmsg("Bitte gib eine valide Zahl ein");
			}
		}
		return result;
	}

}
