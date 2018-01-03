package me.bsc23me.BossFight2;

import java.io.File;

import org.bukkit.entity.Player;

import me.bsc23me.BossFight2.boss.BossType;

public class PlayerOptions {

	private Main plugin;
	File dir = new File(plugin.getDataFolder() +"/players/");
	
	BossType boss;
	//ClassType clazz;
	
	public PlayerOptions(Player p){
		
	}
	
}
