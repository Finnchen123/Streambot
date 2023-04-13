package ch.nicolasovic.bot;

import ch.nicolasovic.bot.handler.CommandHandler;
import ch.nicolasovic.bot.handler.GreetingHandler;
import ch.nicolasovic.bot.handler.TimerHandler;

public class Client extends IRCMessageLoop {
	CommandHandler command;
	GreetingHandler greetings;
	TimerHandler timer;
	int messageCount;
	
	Client(String server, int port) {
        super(server, port);
        command = new CommandHandler(this, points);
        greetings = new GreetingHandler();
        timer = new TimerHandler(this);
        messageCount = 0;
    }
	
    void raw(Message msg) {
    	greetings.sendGreeting(this, msg);
    	if(msg.content.charAt(0) == '!') {
    		command.handleCommand(msg);
    	}
    	else if(msg.content.charAt(0) == '#') {
    		privmsg("Ich bin von # zu ! gewechselt.");
    	}
    	else {
    		if(!msg.nickname.equalsIgnoreCase("meowkaibot")) {
    			points.addUser(msg.nickname);
        		timer.sendTimer();
        		points.addMessage(msg.nickname);
        		messageCount++;
        	}
    	}
    	if(messageCount >= 5) {
    		messageCount = 0;
    		points.saveList();
    	}
    }
}
