package ch.nicolasovic.bot.handler;

import java.util.ArrayList;

import ch.nicolasovic.bot.Client;
import ch.nicolasovic.bot.Console;
import ch.nicolasovic.bot.Message;
import ch.nicolasovic.bot.commands.ByeCommand;
import ch.nicolasovic.bot.commands.CharacterCommand;
import ch.nicolasovic.bot.commands.Command;
import ch.nicolasovic.bot.commands.DiscordCommand;
import ch.nicolasovic.bot.commands.EndCommand;
import ch.nicolasovic.bot.commands.GamesCommand;
import ch.nicolasovic.bot.commands.HardcoreCommand;
import ch.nicolasovic.bot.commands.HelpCommand;
import ch.nicolasovic.bot.commands.HugCommand;
import ch.nicolasovic.bot.commands.LurkCommand;
import ch.nicolasovic.bot.commands.PointCommand;
import ch.nicolasovic.bot.commands.RaceCommand;
import ch.nicolasovic.bot.commands.SlapCommand;
import ch.nicolasovic.bot.commands.SuggestionCommand;
import ch.nicolasovic.bot.minigames.BattleshipMinigame;
import ch.nicolasovic.bot.minigames.Minigame;
import ch.nicolasovic.bot.minigames.NumberMinigame;
import ch.nicolasovic.bot.minigames.RPSMinigame;

public class CommandHandler {
	Client client;
	ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandHandler(Client client, PointHandler points) {
		this.client = client;
		createMinigames(points);
		createCommands(points);
	}
	
	public void handleCommand(Message msg) {
		boolean messageSent = false;
		String commandPart = msg.content.replace("!", "").split(" ")[0];
		for(Command command : commands) {
			if(command.isCommand(commandPart)) {
				command.prepareMessage(client, msg);
				messageSent = true;
				break;
			}
		}
		if(!messageSent) {
			client.privmsg("Diesen Befehl kenne ich nicht!");
		}
	}
	
	private void createCommands(PointHandler points) {
		Console.outputInformation("Creating commands");
		Command discord = new DiscordCommand("dc", "Unter folgendem Link kann man meinem Discord beitreten: https://discord.gg/mYwXD7k", 5, "discord;");
		Command games = new GamesCommand("spiele", "Unter folgendem Link findet man meine Spiele: https://steamcommunity.com/id/finnchen/games/?tab=all", 5, "games;");
		Command lurk = new LurkCommand("lurk", "[USER] kümmert sich gerade um seine Katze und lässt den Stream nur nebenbei offen", 2, "");
		//Command point = new PointCommand("punkte", "PLACEHOLDER", 0, "points;", points);
		Command end = new EndCommand("end", "PLACEHOLDER", 0, "", points);
		//Command suggestion = new SuggestionCommand("vorschlag","PLACEHOLDER",1,"");
		Command slap = new SlapCommand("slap","PLACEHOLDER",1,"");
		Command hug = new HugCommand("hug","PLACEHOLDER",1,"");
		Command bye = new ByeCommand("bye", "PLACEHOLDER", 0, "");
		Command race = new RaceCommand("run", "PLACEHOLDER",0,"");
		Command hc = new HardcoreCommand("hc", "PLACEHOLDER",0,"");
		Command character = new CharacterCommand("char", "PLACEHOLDER",0,"");

		commands.add(discord);
		commands.add(games);
		commands.add(lurk);
		//commands.add(point);
		commands.add(end);
		//commands.add(suggestion);
		commands.add(hug);
		commands.add(slap);
		commands.add(bye);
		commands.add(race);
		commands.add(hc);
		commands.add(character);
		
		Command help = new HelpCommand("hilfe", "Folgende Befehle sind verfügbar: ", 1, "help;", commands);
		commands.add(help);
		Console.outputInformation("Commands created");
	}
	
	private void createMinigames(PointHandler points) {
		Console.outputInformation("Creating minigames");
		/*Minigame rps = new RPSMinigame("rps", "RPS game started", 1, "", points);
		Minigame gtn = new NumberMinigame("gtn", "GTN game started", 1, "", points);
		Minigame battleship = new BattleshipMinigame("ship", "Battleship game started", 0, "", points);
		
		commands.add(rps);
		commands.add(gtn);
		commands.add(battleship);*/
		Console.outputInformation("Minigames created");
	}
}
