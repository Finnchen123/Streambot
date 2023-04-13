package ch.nicolasovic.bot.minigames;

import java.util.Random;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.PointHandler;

public class NumberMinigame extends Minigame{

	public NumberMinigame(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases, points);
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String[] parts = message.content.split(" ");
		if(checkInput(client, message, parts)) {
			int amount = Integer.parseInt(parts[1]);
			points.removePoints(message.nickname, amount);
			int solution = hasWon(parts);
			if(solution == 0) {
				points.addPoints(message.nickname, calculatePoints(parts));
				client.privmsg("Du hast gewonnen!");
			}
			else {
				client.privmsg("Leider verloren! Die Nummer war: " + solution);
			}
		}
	}
	
	private int calculatePoints(String[] parts) {
		int result = 0;
		int amount = Integer.parseInt(parts[1]);
		int start = Integer.parseInt(parts[2]);
		int end = Integer.parseInt(parts[3]);
		
		double modifier = end/start;
		result = (int)(amount * modifier);
		
		return result;
	}
	
	private int hasWon(String[] parts) {
		int result = 0;
		int start = Integer.parseInt(parts[2]);
		int end = Integer.parseInt(parts[3]);
		int guess = Integer.parseInt(parts[4]);
		
		Random rand = new Random();
		
		int solution = rand.nextInt(end-start + 1) + start;
		
		if(solution != guess) {
			result = solution;
		}
		return result;
	}
	
	private boolean checkInput(Client client, Message message, String[] parts) {
		boolean result = false;
		if(parts.length != 5) {
			client.privmsg("Bitte nutz folgende Vorlage: #gtn [Einsatz] [Zahlenanfang] [Zahlenende] [eigene Zahl]");
		}
		else {
			try {
				int amount = Integer.parseInt(parts[1]);
				int start = Integer.parseInt(parts[2]);
				int end = Integer.parseInt(parts[3]);
				int guess = Integer.parseInt(parts[4]);
				if(amount > 0 && start > 0 && end > 0 && guess > 0) {
					if(start < end) {
						if(amount <= points.getPointsForUser(message.nickname)) {
							result = true;
						}
						else {
							client.privmsg("Dafür hast du nicht genug Punkte.");
						}
					}
					else {
						client.privmsg("Bitte gib eine valide Spannweite ein");
					}
				}
				else {
					client.privmsg("Bitte gib nur Zahlen über 0 ein");
				}
			}
			catch(Exception e) {
				client.privmsg("Bitte gib valide Zahlen ein");
			}
		}
		return result;
	}
}
