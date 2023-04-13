package ch.nicolasovic.bot.commands;

import java.util.Calendar;
import java.util.HashMap;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class Command {
	String name;
	String msg;
	HashMap<String, Calendar> cooldowns;
	int cooldown;
	String[] aliases;
	
	
	public Command(String name, String msg, int cooldown, String aliases) {
		this.name = name;
		this.msg = msg;
		this.cooldown = cooldown;
		this.aliases = aliases.split(";");
		this.cooldowns = new HashMap<String, Calendar>();
	}
	
	public void prepareMessage(Client client, Message message) {
		sendMessage(client, msg, message);
	}
	
	public void sendMessage(Client client, String msg, Message message) {
		if(cooldowns.containsKey(message.nickname)) {
			if(Calendar.getInstance().getTimeInMillis() - cooldowns.get(message.nickname).getTimeInMillis() >= cooldown * 60 * 1000) {
				client.privmsg(msg);
				cooldowns.remove(message.nickname);
				cooldowns.put(message.nickname, Calendar.getInstance());
			}
			else {
				client.privmsg("Warte bitte, bevor du " + name + " erneut auslöst, @"+message.nickname+".");
			}
		}
		else {
			cooldowns.put(message.nickname, Calendar.getInstance());
			client.privmsg(msg);
		}
	}
	
	public boolean isCommand(String command) {
		if(command.equalsIgnoreCase(name) || isAlias(command)) {
			return true;
		}
		return false;
	}
	
	public boolean isAlias(String command) {
		boolean result = false;
		for(String alias : aliases) {
			if(command.equalsIgnoreCase(alias) && alias.length() > 1) {
				result = true;
			}
		}
		return result;
	}
}
