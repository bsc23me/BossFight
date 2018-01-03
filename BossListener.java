package me.bsc23me.BossFight2;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import me.bsc23me.BossFight2.boss.Boss;
import net.minecraft.server.v1_11_R1.GenericAttributes;

public class BossListener implements Listener{

	public Main plugin;
	
	@EventHandler
	public void onBossDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(((CraftPlayer)p).getHandle().getAttributeInstance(GenericAttributes.maxHealth).getValue() > 20d){
				float r = (float) (p.getHealth() / ((CraftPlayer)p).getHandle().getAttributeInstance(GenericAttributes.maxHealth).getValue());
				Boss.bar.setProgress(r);
			}
		}
	}
	
	@EventHandler
	public void onBossHeal(EntityRegainHealthEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(((CraftPlayer)p).getHandle().getAttributeInstance(GenericAttributes.maxHealth).getValue() > 20d){
				if(e.getRegainReason() == RegainReason.SATIATED){
					e.setCancelled(true);
				}
			}
		}
	}
	
}
