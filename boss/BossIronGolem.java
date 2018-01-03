package me.bsc23me.BossFight2.boss;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.bsc23me.BossFight2.Main;
import net.minecraft.server.v1_11_R1.GenericAttributes;

public class BossIronGolem implements Listener {

	public Main plugin;

	int count;
	
	int enrageTask;

	Set<Material> m = new HashSet<Material>();

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action action = e.getAction();
		if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
			if (held(p).getType() == Material.IRON_AXE) {

			} else if (held(p).getType() == Material.ANVIL) {
				e.setCancelled(true);
				Block b = p.getTargetBlock(m, 20);
				FallingBlock a = b.getWorld().spawnFallingBlock(
						new Location(b.getWorld(), b.getX(), b.getY() + 5d, b.getZ()),
						new MaterialData(Material.ANVIL));
				a.setVelocity(new Vector(0d, -2d, 0d));
				p.getInventory().remove(Material.ANVIL);
			} else if (held(p).getType() == Material.IRON_BLOCK) {
				e.setCancelled(true);
				enrage(p);
			}
		}
	}
	
	@EventHandler
	public void swap(PlayerChangedMainHandEvent e){
		Player p = e.getPlayer();
		p.sendMessage("swap");
		if(((CraftPlayer)p).getHandle().getAttributeInstance(GenericAttributes.maxHealth).getValue() == 500d){
			enrage(p);
		}
	}
	
	@SuppressWarnings("static-access")
	public void enrage(Player p){
		p.getWorld().strikeLightningEffect(p.getLocation());
		p.setGlowing(true);
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200000000, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200000000, 1), false);
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2000000000, 1), false);
		enrageTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin.getPlugin(Main.class), new Runnable(){
			int i = 0;
			public void run(){
				if(i < 30){
					p.setLevel(30 - i);
					i++;
				}else{
					p.setLevel(0);
					p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
					p.removePotionEffect(PotionEffectType.SPEED);
					p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
					p.setGlowing(false);
					Bukkit.getScheduler().cancelTask(enrageTask);
				}
			}
		}, 0, 20);
	}

	@EventHandler
	public void fallingBlockLanded(EntityChangeBlockEvent event) {
		Block b = event.getBlock();
		if (event.getEntity() instanceof FallingBlock) {
			FallingBlock anvil = (FallingBlock) event.getEntity();
			if (anvil.getMaterial() == Material.ANVIL) {
				if (b.getType() == Material.AIR) {
					if (count < 4) {
						b.getWorld().createExplosion(b.getX(), b.getY(), b.getZ(), 6, false, true);
						count++;
					} else {
						b.setType(Material.AIR);
					}
				}
			}
		}
	}

	public ItemStack held(Player p) {
		return p.getInventory().getItemInMainHand();
	}

}
