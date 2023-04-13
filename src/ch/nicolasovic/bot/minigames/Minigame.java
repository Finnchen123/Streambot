package ch.nicolasovic.bot.minigames;

import ch.nicolasovic.bot.commands.Command;
import ch.nicolasovic.bot.handler.PointHandler;

public class Minigame extends Command{

	PointHandler points;
	
	public Minigame(String name, String msg, int cooldown, String aliases, PointHandler points) {
		super(name, msg, cooldown, aliases);
		this.points = points;
	}
}
