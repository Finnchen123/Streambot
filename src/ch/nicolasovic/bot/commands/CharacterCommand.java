package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class CharacterCommand extends Command {

	public CharacterCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "Ich spiele einen Untoten Mage, WoW Classic (Era), Realm Hydraxian Waterlords (RP-PvE)";
		sendMessage(client, msg, message);
	}

}
