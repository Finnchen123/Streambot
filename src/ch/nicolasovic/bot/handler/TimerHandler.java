package ch.nicolasovic.bot.handler;

import java.util.ArrayList;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Console;
import ch.nicolasovic.bot.timers.InformationTimer;
import ch.nicolasovic.bot.timers.Timer;

public class TimerHandler {

	ArrayList<Timer> timers;
	Client client;
	
	public TimerHandler(Client client) {
		timers = new ArrayList<Timer>();
		this.client = client;
		createTimer();
	}
	
	public void sendTimer() {
		for(Timer timer : timers) {
			timer.increaseCounter(client);
		}
	}
	
	private void createTimer() {
		Console.outputInformation("Creating timers");
		
		Timer information = new InformationTimer(10, "Willkommen in meinem Stream. Alle möglichen Befehle und Minispiele sind über #hilfe nachzulesen. Viel Spass.");
		
		timers.add(information);
		
		Console.outputInformation("Timers created");
	}
	
	
}
