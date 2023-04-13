package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class LurkCommand extends Command{

	public LurkCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = message.nickname + " kümmert sich gerade um die Katze und lässt den Stream nur nebenbei offen";
		sendMessage(client, msg, message);
	}
}
