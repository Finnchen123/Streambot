package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class HugCommand extends Command{

	public HugCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "";
		String[] parts = message.content.split(" ");
		if(parts.length == 2) {
			msg = "@" + message.nickname + " nimmt @" + parts[1].replace("@", "") + " beherzt in die Arme und knuddelt @" + parts[1].replace("@", "") + " in Grund und Boden.";
		}
		else {
			msg = "Bitte nutz folgendes Format: #hug [nutzername]";
		}
		sendMessage(client, msg, message);
	}
}