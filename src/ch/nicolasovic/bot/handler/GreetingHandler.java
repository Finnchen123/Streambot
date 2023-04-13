package ch.nicolasovic.bot.handler;

import java.util.ArrayList;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Console;
import ch.nicolasovic.bot.Greeting;
import ch.nicolasovic.bot.Message;

public class GreetingHandler {
	ArrayList<Greeting> greetings;
	
	public GreetingHandler() {
		greetings = new ArrayList<Greeting>();
		generateGreetings();
	}
	
	private void generateGreetings() {
		Console.outputInformation("Generate greetings");
		greetings.add(new Greeting("threetimes8", "HAIL TO THE KING!"));
		greetings.add(new Greeting("lil_bacchus", "Halli Hallo Jessica!!!"));
		greetings.add(new Greeting("archivhoehle", "Ich heisse weder Aktivkohle noch Archivkohle noch Aktivhöhle. Mein Name ist Archivhöhle, angenehm!"));
		greetings.add(new Greeting("spacelord09", "Moin!"));
		greetings.add(new Greeting("umusi", "Squid1 Squid3 Squid2 copyThis pastaThat Pasta für alle der Schrank is da!!"));
		greetings.add(new Greeting("asmodis_", "Bist auch mal wieder da"));
		greetings.add(new Greeting("noxxi17", "MEOW"));
		greetings.add(new Greeting("jmana18", "Der König ist da! Verneigt euch Pöbel!"));
		greetings.add(new Greeting("eldawillow", "Ihr Männer richtet eure Bärte, Ihr Frauen euer Dekolleté, der Zwergen König \"Willow\" ist eingekehrt! Nun gibt es Kakao und Tee!"));
		greetings.add(new Greeting("finnchen123", "Der Chef ist da"));
		greetings.add(new Greeting("extratnt_", "Well well well... fuck it and just use gnu/linux"));
		Console.outputInformation("Greetings generated");
	}
	
	public void sendGreeting(Client client, Message msg) {
		for(Greeting greeting : greetings) {
			if(greeting.isUser(msg.nickname.replace(" ", ""))) {
				greeting.sendGreeting(client);
			}
		}
	}
}
