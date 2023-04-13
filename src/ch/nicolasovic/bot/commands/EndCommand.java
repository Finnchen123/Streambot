package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.PointHandler;

public class EndCommand extends Command{

	PointHandler points;
	
	public EndCommand(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases);
		this.points = points;
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		if(message.nickname.replace(" ", "").equalsIgnoreCase("finnchen123")) {
			points.endStream();
			client.privmsg("Der Stream wird nun beendet.");
		}
		else {
			client.privmsg("Nicht genug rechte.");
		}
	}

}
