package ch.nicolasovic.bot.commands;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.handler.DatabaseHandler;

public class SuggestionCommand extends Command{

	DatabaseHandler db;
	
	public SuggestionCommand(String name, String msg, int cooldown, String aliases) {
		super(name, msg, cooldown, aliases);
		db = new DatabaseHandler();
	}
	
	@Override
	public void prepareMessage(Client client, Message message) {
		String msg = "@"+message.nickname+" Dein Vorschlag wurde erfolgreich gespeichert.";
		if(message.content.split(" ").length <= 1) {
			msg = "Der Befehl wird wie folgt benutzt: #vorschlag [Vorschlagstext]";
		}
		else {
			boolean result = db.saveSuggestion(message.nickname, message.content.replace("#vorschlag ", ""));
			if(!result) {
				if(message.content.replace("#vorschlag ", "").length() >= 255) {
					msg = "@"+message.nickname+" Dein Vorschlag ist leider zu lang. Maximallänge ist 255 Zeichen.";
				}
				else {
					msg = "@"+message.nickname+" Dein Vorschlag wurde leider nicht gespeichert. Versuch es später noch einmal.";
				}
			}
		}
		sendMessage(client, msg, message);
	}

}
