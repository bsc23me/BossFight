package me.bsc23me.BossFight2;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Arena {

	/*An arena makes up the playable area for the boss battle. It Requires:
	- a name (for multiple)
	- dimensions (to limit the player and the boss from flying off)
	- spawn locations for all three teams (could be one if player can move a bit or else 4)
	
	should be saved out to a file for later in plugin.getDataFolder()+"/Arenas/"+name+".yml"*/
	private Main plugin;
	
	String name;
	String world = "world";
	int x1 = 0;
	int y1 = 0;
	int z1 = 0;
	int x2 = 0;
	int y2 = 0;
	int z2 = 0;
	Location rspawn = new Location(Bukkit.getWorld(world), 0d, 0d, 0d);
	Location gspawn = new Location(Bukkit.getWorld(world), 0d, 0d, 0d);
	Location bspawn = new Location(Bukkit.getWorld(world), 0d, 0d, 0d);
	Location bossSpawn = new Location(Bukkit.getWorld(world), 0d, 0d, 0d);
	
	File file;
	
	public Arena(String name){
		  this.name = name;
		  file = new File(plugin.getDataFolder()+"/Arenas/"+name+".yml");
		  load();
	}
	
	public Arena(File file){
		this.file = file;
		load();
	}
	
	public Arena(String name, int x1, int y1, int z1, int x2, int y2, int z2){
		this.name = name;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		
	}
	
	public void save(){
		FileConfiguration data = new YamlConfiguration();
		if(file.exists()){
			try{
				data.load(file);
				
				data.set("World", world);
				data.set("Bounds.x1", x1);
				data.set("Bounds.y1", y1);
				data.set("Bounds.z1", z1);
				data.set("Bounds.x2", x2);
				data.set("Bounds.y2", y2);
				data.set("Bounds.z2", z2);
				data.set("Spawns.Red", rspawn);
				data.set("Spawns.Green", gspawn);
				data.set("Spawns.Blue", bspawn);
				data.set("Spawns.Boss", bossSpawn);
				
				data.save(file);
			}catch(Exception e){
				
			}
		}else{
			try{
				file.createNewFile();
				data.load(file);
				
				data.set("World", world);
				data.set("Bounds.x1", x1);
				data.set("Bounds.y1", y1);
				data.set("Bounds.z1", z1);
				data.set("Bounds.x2", x2);
				data.set("Bounds.y2", y2);
				data.set("Bounds.z2", z2);
				data.set("Spawns.Red", rspawn);
				data.set("Spawns.Green", gspawn);
				data.set("Spawns.Blue", bspawn);
				data.set("Spawns.Boss", bossSpawn);
				
				data.save(file);
			}catch(Exception e){
				File dir = new File(plugin.getDataFolder()+"/Arenas/");
				if(!(dir.exists())){
					dir.mkdirs();
					save();
				}else{
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public void load(){
		FileConfiguration data = new YamlConfiguration();
		if(file.exists()){
			try{
				data.load(file);
				
				world = data.getString("World");
				x1 = data.getInt("Bounds.x1");
				y1 = data.getInt("Bounds.y1");
				z1 = data.getInt("Bounds.z1");
				x2 = data.getInt("Bounds.x2");
				y2 = data.getInt("Bounds.y2");
				z2 = data.getInt("Bounds.z2");
				rspawn = (Location) data.get("Spawns.Red");
				gspawn = (Location) data.get("Spawns.Green");
				bspawn = (Location) data.get("Spawns.Blue");
				bossSpawn = (Location) data.get("Spawns.Boss");
				
			}catch(Exception e){
				
			}
		}else{
			save();
		}
	}
}
