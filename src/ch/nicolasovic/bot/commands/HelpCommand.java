package ch.nicolasovic.bot.commands;

import java.util.ArrayList;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class HelpCommand extends Command{

	ArrayList<Command> commands;
	
	public HelpCommand(String name, String msg, int cooldown, String aliases, ArrayList<Command> commands) {
		super(name, msg, cooldown, aliases);
		this.commands = commands;
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "Folgende Befehle sind verfügbar: ";
		for(Command command : commands) {
			msg += command.name.toLowerCase() + ", ";
		}
		msg = msg.substring(0, msg.length() - 2);
		sendMessage(client, msg, message);
	}

}
