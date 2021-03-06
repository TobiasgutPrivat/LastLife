package lastlife.lastlife;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.common.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class LastLife extends JavaPlugin{
	public static LastLife instance;
	public static ArrayList<LastLifePlayer> lastLifePlayers = new ArrayList<LastLifePlayer>();
	
	private static final File folder = new File(String.valueOf("plugins//LastLife//"));
	private static final File playerFile = new File("plugins//LastLife//players.json");
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public static HashMap<String, Object> playerData = new HashMap<String, Object>();
	public static Map tempData;

	@Override
	public void onEnable() {
		instance = this;
		instance.getServer().getPluginManager().registerEvents(new Events(), instance);
		
		this.getCommand("GiveLife").setExecutor(new GiveLifeCommand());
		this.getCommand("SetLife").setExecutor(new SetLifeCommand());
		this.getCommand("GetLive").setExecutor(new GetLifeCommand());
		this.getCommand("PickBoogieman").setExecutor(new PickBoogiemanCommand());
		
		playerData = readFile(playerFile);
		Bukkit.getServer().broadcastMessage(playerData.toString());

		for(Map.Entry<String, Object> playerData : playerData.entrySet()) {
			new LastLifePlayer((Map<String, Object>) playerData.getValue());
		}
	}
	
	@Override
	public void onDisable() {
		savePlayers();
	}
	
	public void savePlayers() {
		for (LastLifePlayer lastLifePlayer : lastLifePlayers) {
			Bukkit.getServer().broadcastMessage(lastLifePlayer.toString());
			playerData.put(lastLifePlayer.getPlayer().getName(), lastLifePlayer.serialize());
			Bukkit.getServer().broadcastMessage(playerData.toString());
		}
		saveFile(playerFile, playerData);
	}
	
	public static LastLifePlayer getLastLifePlayerByPlayer(Player player) {
		for (LastLifePlayer lastLifePlayer : LastLife.lastLifePlayers) {
			if (lastLifePlayer.getPlayer() == player) {
				return lastLifePlayer;
			}
		}
		LastLifePlayer lastLifePlayer = new LastLifePlayer(player);
		lastLifePlayers.add(lastLifePlayer);
		return lastLifePlayer;
	}
	
	public static void pickBoogiemans(int quantity) {
		Bukkit.getServer().broadcastMessage("Boogiemans get selected in 1 minute");
		
		new java.util.Timer().schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {
		            	for (LastLifePlayer lastLifePlayer : lastLifePlayers) {
		        			if (lastLifePlayer.isBoogieman()) {
		        				lastLifePlayer.setLifes(1);
		        			}
		        		}
		            	
		            	for (int i = 0;i < quantity / 5; i++) {
		        			Player player = Bukkit.getOnlinePlayers().stream().skip((int) (Bukkit.getOnlinePlayers().size() * Math.random())).findFirst().orElse(null);
		        			if (!getLastLifePlayerByPlayer(player).isBoogieman()) {
		        				getLastLifePlayerByPlayer(player).setBoogieman(true);
		        				player.sendMessage("You are a Boogieman. Kill someone until next Session");
		        			}
		        		}
		            }
		        }, 
		        5000 
		);
	}
	
	
	public static HashMap<String, Object> readFile(File file) {
		if(!folder.exists()) {
			folder.mkdir();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileReader fileReader;
		try {
			fileReader = new FileReader(playerFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
		//if (gson.fromJson(fileReader, HashMap.class) == null) {
		//	return new HashMap<String, Object>();
		//}
		HashMap<String, Object> result = new HashMap<>();
		//Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
		Data data = gson.fromJson(fileReader, Data.class);
		tempData = gson.fromJson(fileReader, Map.class);
		if(data == null) {
			data = new Data();
		}
		data.getData().entrySet().forEach(entry -> {
			result.put(entry.getKey(),entry.getValue());
		});

		return result;
		//return gson.fromJson(fileReader, Map.class);
	}
	
	public static void saveFile(File file, Map<String, Object> data) {
		if(!folder.exists()) {
			try {
				folder.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fileWriter = new FileWriter(file);
			gson.toJson(data, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
