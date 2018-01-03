package me.bsc23me.BossFight2;

import java.io.File;
import java.util.List;

public class GameManager {

	Main plugin;
	
	List<Arena> arenas;
	List<Game> games;
	
	public GameManager(){
		
	}
	
	public void loadArenas(){
		File dir = new File(plugin.getDataFolder()+"/Arenas/");
		for(File f : dir.listFiles()){
				arenas.add(new Arena(f));
		}
	}
	
	public List<Game> getGames(){
		return games;
	}
	
	public Game getGame(int id){
		return games.get(id);
	}
	
	public void registerGame(Game game){
		games.add(game);
	}
	
}
