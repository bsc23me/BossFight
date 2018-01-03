package me.bsc23me.BossFight2;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EventListener implements Listener{

	public Main plugin;
	
	@EventHandler
	public void onPlayerHunger(FoodLevelChangeEvent e){
		Player p = (Player) e.getEntity();
		if(p.getInventory().contains(Game.inGame)){
			e.setCancelled(true);
		}
	}
	
}
