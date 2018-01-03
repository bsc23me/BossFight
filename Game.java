package me.bsc23me.BossFight2;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.bsc23me.BossFight2.boss.Boss;

public class Game {

	Main plugin;
	
	public static ItemStack inGame = new CustomItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8), " ");
	
	//Game Info
	int id;
	Arena arena;
	Location lobby;
	//Player Info
	int maxPlayers = 13;
	List<Player> players;
	List<Player> raiders;
	Player boss;
	//Team Info
	List<Player> rTeam;
	List<Player> gTeam;
	List<Player> bTeam;
	
	public Game(int id){
		this.id = id;
	}
	
	public boolean addPlayer(Player p){
		if(players.size() < maxPlayers){
			players.add(p);
			return true;
		}else{
			return false;
		}
	}
	
	public void fillTeam(Player p){
		//TODO: Add player preference
		if(rTeam.size() < 4){
			rTeam.add(p);
		}else if(gTeam.size() < 4){
			gTeam.add(p);
		}else if(bTeam.size() < 4){
			bTeam.add(p);
		}
	}
	
	public void prepare(){
		
		Random r = new Random();
		//TODO: VIP Priority
		boss = players.get(r.nextInt(players.size()));
		//players.remove(boss); //Test This!
		//Boss b = new Boss(boss);
		for(Player p : players){
			p.getInventory().setItem(10, inGame);
			if(!(p == boss)){
				raiders.add(p);
				fillTeam(p);
			}
		}
		
		/////////////////////////////////////////////
		
		//if(b.getType() == BossType.SKELETON){
			
		//}
		
		boss.teleport(arena.bossSpawn);
		
		for(Player p : rTeam){
			p.teleport(arena.rspawn);
		}
		for(Player p : gTeam){
			p.teleport(arena.gspawn);
		}
		for(Player p : bTeam){
			p.teleport(arena.bspawn);
		}
	}
	
	public void start(){
		for(Player p : raiders){
			Boss.bar.addPlayer(p);
		}
	}
	
	public void stop(){
		
	}
	
	public void reset(){
		
	}
	
	//////////////////////////////////////////////////////////////////////////////
	// Getters //
	//////////////////////////////////////////////////////////////////////////////
	
	public Arena getArena(){
		return arena;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public List<Player> getRaiders(){
		return players;
	}
	
	public List<Player> getTeam(String team){
		if(team.equalsIgnoreCase("red")){
		return rTeam;
		}else if(team.equalsIgnoreCase("green")){
			return gTeam;
		}else if(team.equalsIgnoreCase("blue")){
			return bTeam;
		}else{
			return null;
		}
	}
	
	public Player getBoss(){
		return boss;
	}
	
	
}
