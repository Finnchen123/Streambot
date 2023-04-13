package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.PointHandler;

public class PointCommand extends Command{
	PointHandler points;
	
	public PointCommand(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases);
		this.points = points;
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String operator = "";
		int amount = 0;
		String username;
		String[] parts = message.content.split(" ");
		if(parts.length != 4 && parts.length != 1) {
			msg = "Argumente stimmen nicht überein #punkte [add/remove] [username] [amount]";
		}
		else {
			if(parts.length == 4) {
				if(message.nickname.replace(" ", "").equalsIgnoreCase("finnchen123")) {
					try {
						amount = Integer.parseInt(parts[3]);
						operator = parts[1];
						username = parts[2];
						if(operator.equalsIgnoreCase("add")) {
							points.addPoints(username, amount);
							msg = username + " wurden " + amount + " Punkte geschenkt!";
						}
						else if (operator.equalsIgnoreCase("remove")){
							points.removePoints(username, amount);
							msg = username + " wurden " + amount + " Punkte gestohlen!";
						}
						else {
							msg = "Bitte gib einen validen Operator ein";
						}
					}
					catch(Exception e) {
						msg = "Bitte gib eine valide Zahl ein";
					}
				}
				else {
					msg = "Du hast nicht genug Rechte";
				}
			}
			else {
				msg = points.outputUser(message.nickname.replace(" ", ""));
			}
		}
		sendMessage(client, msg, message);
	}
}
