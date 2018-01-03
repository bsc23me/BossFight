package me.bsc23me.BossFight2;

import java.util.Arrays;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem extends ItemStack{

	public CustomItem(ItemStack i, String name, String... lore){
		super(i);
		ItemMeta meta = getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		setItemMeta(meta);
	}
	
}
