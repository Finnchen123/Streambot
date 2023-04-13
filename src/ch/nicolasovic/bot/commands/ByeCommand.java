package ch.nicolasovic.bot.commands;

import java.util.ArrayList;
import java.util.Random;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class ByeCommand extends Command{

	ArrayList<String> messages;
	Random rand;
	
	public ByeCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
		this.messages = new ArrayList<String>();
		generateMessages();
		rand = new Random();
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "@"+message.nickname+" verlässt den Stream:";
		String reason = message.content.toLowerCase().replace("#bye", "");
		if(reason.length() > 1) {
			msg += reason;
		}
		else {
			msg += " " + messages.get(rand.nextInt(messages.size()));
		}
		sendMessage(client, msg, message);
	}
	
	private void generateMessages() {
		messages.add("Die Katze muss gefüttert werden");
		messages.add("Es wird an einer Anti-Hunde Demo teilgenommen");
		messages.add("Der Stream ist zu langweilig");
		messages.add("Die Schildkröte des Nachbarn 3.Grades hat heute Geburtstag");
		messages.add("Der Computer verbraucht schon wieder zu viel Strom");
	}
}
