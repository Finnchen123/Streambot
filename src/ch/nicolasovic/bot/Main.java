package ch.nicolasovic.bot;

import ch.nicolasovic.bot.handler.PropertieHandler;

/*
 * QUELLE 
 * [IRC]: 	https://gist.github.com/kaecy/286f8ad334aec3fcb588516feb727772#file-simpleircclient-java
 * [RCON]:	https://github.com/jobfeikens/rcon
 */
public class Main {
    public static void main(String[] args) {
    	Console.outputHeading("STARTUP");
    	Console.outputInformation("Starting up bot");
    	Client client = new Client("irc.chat.twitch.tv", 6667);
        client.pass(PropertieHandler.getProperty("BOT_TOKEN"));
        client.nick(PropertieHandler.getProperty("BOT_NAME"));
        client.receiveConfirmation();
        client.reqCommands();
        client.reqMember();
        client.join(PropertieHandler.getProperty("CHANNEL"));
        Console.outputInformation("Bot joined channel");
        Console.outputInformation("Starting thread");
        Thread thread = new Thread(client);
        thread.start();
        Console.outputInformation("Thread started");
    }
}
