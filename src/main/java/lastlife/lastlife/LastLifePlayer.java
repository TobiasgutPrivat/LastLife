package lastlife.lastlife;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

public class LastLifePlayer implements ConfigurationSerializable{
	private Player player;
	private Integer lifes;
	private boolean Boogieman;
	
	public static Integer startLifes = 4;
	
	public void removelife() {
		if (!isOnLastLife()) {
			setLifes(getLifes() - 1);
		}
	}
	
	public void addlife() {
		lifes += 1;
	}
	
	public boolean isOnLastLife() {
		return lifes <= 1;
	}
	
	//getter, setter
	public boolean isBoogieman() {
		return Boogieman;
	}

	public void setBoogieman(boolean boogieman) {
		Boogieman = boogieman;
	}

	public static Integer getStartLifes() {
		return startLifes;
	}

	public static void setStartLifes(Integer startLifes) {
		LastLifePlayer.startLifes = startLifes;
	}


	public LastLifePlayer(Player player) {
		super();
		this.player = player;
		setLifes(startLifes);
	}

	public LastLifePlayer(Map<String, Object> data) {
		super();
		deserialize(data);
	}

	public Integer getLifes() {
		return lifes;
	}

	public void setLifes(Integer plifes) {
		if (plifes < 1) {
			lifes = 1;
		} else {
			lifes = plifes;
		}
		
		switch (lifes) {
		case 1:
			player.setDisplayName(ChatColor.RED + player.getName() + ChatColor.RESET);
			player.setPlayerListName(ChatColor.RED + player.getDisplayName() + ChatColor.RESET);
			break;
		case 2:
			player.setDisplayName(ChatColor.YELLOW + player.getName() + ChatColor.RESET);
			player.setPlayerListName(ChatColor.YELLOW + player.getDisplayName() + ChatColor.RESET);
			break;
		case 3:
			player.setDisplayName(ChatColor.GREEN + player.getName() + ChatColor.RESET);
			player.setPlayerListName(ChatColor.GREEN + player.getDisplayName() + ChatColor.RESET);
			break;
		default:
			player.setDisplayName(ChatColor.DARK_GREEN + player.getName() + ChatColor.RESET);
			player.setPlayerListName(ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.RESET);
			break;
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("PlayerUUID", player.getUniqueId().toString());
		data.put("lives", lifes);
		data.put("Boogieman", Boogieman);
		return data;
	}
	
	public void deserialize(Map<String, Object> data) {
		player = Bukkit.getServer().getPlayer(UUID.fromString(data.get("PlayerUUID").toString()));
		setLifes(Integer.parseInt(data.get("lives").toString()));
		setBoogieman(Boolean.parseBoolean(data.get("Boogieman").toString()));
	}
}
