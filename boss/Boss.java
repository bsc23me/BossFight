package me.bsc23me.BossFight2.boss;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.bsc23me.BossFight2.CustomItem;
import me.bsc23me.BossFight2.Main;
import net.minecraft.server.v1_11_R1.DataWatcher;
import net.minecraft.server.v1_11_R1.GenericAttributes;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutSpawnEntityLiving;

public class Boss {
	
	Main plugin;
	
	BossType type = BossType.IRON_GOLEM;
	public static BossBar bar = Bukkit.createBossBar("Iron Golem", BarColor.RED, BarStyle.SOLID, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY, BarFlag.PLAY_BOSS_MUSIC);
	
	public Boss(Player player){
		File dir = new File(plugin.getDataFolder()+"/players/");
		File file = new File(dir.getPath()+player+".yml");
		FileConfiguration data = new YamlConfiguration();
		if(file.exists()){
			try{
				data.load(file);
				
				type = (BossType) data.get("Boss");
				
			}catch(Exception e){
					
			}
		}else{
			try {
				file.createNewFile();
			} catch (Exception e) {
				
			}
		}
		
		createBoss(player);
		
	}
	
	public static void createBoss(Player player){
		player.spawnParticle(Particle.BARRIER, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 2000);
		
		((CraftPlayer)player).getHandle().getAttributeInstance(GenericAttributes.maxHealth).setValue(500d);
		player.setHealth(500d);
		player.setHealthScale(20d);
		bar.addPlayer(player);
		player.getInventory().setItem(8, new CustomItem(new ItemStack(Material.IRON_BLOCK),
				ChatColor.DARK_RED + "Rage Token", ChatColor.GRAY + "Enters rage mode!"));
		
		/*for(Player p : Bukkit.getOnlinePlayers()){
			try{
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(destroyEntity(player));
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(spawnMob(player.getLocation(), 1000001));
			}catch(Exception e){
				
			}
		}*/
	}
	
	public static void removeBoss(Player p){
		((CraftPlayer)p).getHandle().getAttributeInstance(GenericAttributes.maxHealth).setValue(20d);
		bar.removeAll();
	}
	
	public static PacketPlayOutEntityDestroy destroyEntity(Entity e){
		return new PacketPlayOutEntityDestroy(e.getEntityId());
	}
	
	public static DataWatcher field;
	public static PacketPlayOutSpawnEntityLiving spawnMob(Location loc, int id) throws Exception{
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
		for(Field f : packet.getClass().getDeclaredFields()){
			f.setAccessible(true);
		}
		packet.getClass().getDeclaredField("c").set(packet, id);//Entity ID
		packet.getClass().getDeclaredField("a").setInt(packet, 50);//Mob ID
		packet.getClass().getDeclaredField("d").setDouble(packet, Math.floor(loc.getX() *32d));
		packet.getClass().getDeclaredField("e").setDouble(packet, Math.floor(loc.getY() *32d));
		packet.getClass().getDeclaredField("f").setDouble(packet, Math.floor(loc.getZ() *32d));
		packet.getClass().getDeclaredField("j").setByte(packet, (byte) loc.getYaw());
		packet.getClass().getDeclaredField("k").setByte(packet, (byte) loc.getPitch());
		packet.getClass().getDeclaredField("l").setByte(packet, (byte) loc.getYaw());
		
		Field meta = packet.getClass().getDeclaredField("m");
		meta.setAccessible(true);
		meta.set(packet, field);
		
		return packet;
	}
	
	public BossType getType(){
		return type;
	}
	
}
