package lastlife.lastlife;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Events implements Listener{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if (event.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
			event.getCurrentItem().setType(Material.BOOK);
			event.getCurrentItem().getEnchantments().clear();
		};
	}
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event)
	{
		if (event.getCursor().getType() == Material.ENCHANTED_BOOK) {
			event.getCursor().setType(Material.BOOK);
			event.getCursor().getEnchantments().clear();
		};
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		if (event.getItem().getItemStack().getType() == Material.ENCHANTED_BOOK) {
			event.getItem().getItemStack().setType(Material.BOOK);
			event.getItem().getItemStack().getEnchantments().clear();
		};
	}
	
	@EventHandler
	public void onCraftItem(CraftItemEvent event)
	{
		if (event.getRecipe().getResult().getType() == Material.BOOKSHELF) {
			event.setCancelled(true);
		};
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) 
	{
		LastLifePlayer lastLifePlayer = LastLife.getLastLifePlayerByPlayer(event.getEntity());
		Player killer = event.getEntity().getKiller();
		if (killer != null) {
			if (lastLifePlayer.getLifes() > 1) {
				LastLife.getLastLifePlayerByPlayer(killer).setBoogieman(false);
			}
		}
		lastLifePlayer.removelife();
	}
}
