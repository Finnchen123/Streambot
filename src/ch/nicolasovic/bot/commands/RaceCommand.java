package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;

public class RaceCommand extends Command {

	public RaceCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "Ich versuche heute mich an einem Run mit dem folgenden Setting:";
		/*msg = msg + "Moralische Demokratie (Demokratisch, Meritokratie, Parlamentarisches System);";
		msg = msg + "Fanatisch pazifistisch, Spiritualistisch;";
		msg = msg + "Ytrellan (Schmetterlinge), Naturschützer, Agrarisch, Langsame Lerner;";
		msg = msg + "Überlebende";*/
		msg = "Einfach drauf los";
		sendMessage(client, msg, message);
	}

}
