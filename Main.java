package me.bsc23me.BossFight2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.bsc23me.BossFight2.boss.Boss;
import me.bsc23me.BossFight2.boss.BossIronGolem;

public class Main extends JavaPlugin{
	//Plugin Stuff
	public static final Logger log = Logger.getLogger("Minecraft");
	public PluginManager pm = Bukkit.getServer().getPluginManager();
	
	GameManager gm = new GameManager();
	//Game Stuff
	List<Game> games = new ArrayList<Game>();
	List<Arena> arenas = new ArrayList<Arena>();
	
	@Override
	public void onEnable(){
		log.info("[BossFight2] has been enabled!");
		
		File playerData = new File(this.getDataFolder()+"/players/");
		File arenaData = new File(this.getDataFolder()+"/arenas/");
		if(!playerData.exists()){
			playerData.mkdirs();
		}
		if(!arenaData.exists()){
			arenaData.mkdirs();
		}
		// Register all events and handlers
		
		pm.registerEvents(new EventListener(), this);
		pm.registerEvents(new BossListener(), this);
		pm.registerEvents(new BossIronGolem(), this);
	}
	
	@Override
	public void onDisable(){
		log.info("[BossFight2] has been disabled!");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args){
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(l.equalsIgnoreCase("boss")){
				//Player Commands
				if(args[0].equalsIgnoreCase("help")){
					helpPlayer(player);
				}else if(args[0].equalsIgnoreCase("join")){
					if(args.length != 2){
						player.sendMessage(ChatColor.RED+"Invalid Arguments: /boss join <game>");
					}else{
						try{
							int i = Integer.parseInt(args[1]);
							if(games.get(i).addPlayer(player)){
								player.teleport(games.get(i).lobby);
							}
						}catch(Exception e){
							
						}
					}
				}
				// VIP Commands
				
				
				
				// Staff Commands
				
				
				
				// Admin Commands
				else if(args[0].equalsIgnoreCase("create")){
					if(args.length == 1){
						
					}
				}
				else if(args[0].equalsIgnoreCase("debug")){
					if(args[1].equalsIgnoreCase("createboss")){
						Boss.createBoss(player);
					}else if(args[1].equalsIgnoreCase("removeboss")){
						Boss.removeBoss(player);
					}else if(args[1].equalsIgnoreCase("sethp")){
						try{
							int i = Integer.parseInt(args[2]);
							player.setHealth(i);
						}catch(Exception e){
							
						}
					}else if(args[1].equalsIgnoreCase("join")){
						player.getInventory().setItem(9, Game.inGame);
					}
				}else{
					helpPlayer(player);
				}
			}
		}else{
			sender.sendMessage("Please run all commands through the game.");
		}
		return false;
	}
	
	public void helpPlayer(Player p){
		p.sendMessage(ChatColor.LIGHT_PURPLE+"===Boss Fight===");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"General Commands");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss help");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss join");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss vote");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"VIP Commands");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss boss");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss kit");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"Staff Commands");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss enable");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss disable");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"Admin Commands");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss start");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss stop");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss create");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss remove");
		p.sendMessage(ChatColor.LIGHT_PURPLE+"/boss setspawn");
	}
	
}
