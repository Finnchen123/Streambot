package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class SlapCommand extends Command{

	public SlapCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "";
		String[] parts = message.content.split(" ");
		if(parts.length == 2) {
			msg = "@" + message.nickname + " nimmt Anlauf und schlägt @" + parts[1].replace("@", "") + " mit der flachen Hand ins Gesicht.";
		}
		else {
			msg = "Bitte nutz folgendes Format: #slap [nutzername]";
		}
		sendMessage(client, msg, message);
	}
}