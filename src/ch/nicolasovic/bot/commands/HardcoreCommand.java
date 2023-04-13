package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class HardcoreCommand extends Command {

	public HardcoreCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "Es gibt ein tolles WoW-Addon, mit dem man WoW im Hardcore-Modus spielen kann. ";
		msg = msg + "Hardcore bedeutet: Stirbt der Char, ist der Run vorbei. Webseite vom Addon findet ihr hier: https://classichc.net/";
		sendMessage(client, msg, message);
	}

}
