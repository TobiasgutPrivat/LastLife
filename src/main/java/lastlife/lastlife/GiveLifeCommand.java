package lastlife.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveLifeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            LastLifePlayer lastLifePlayersender = LastLife.getLastLifePlayerByPlayer(senderPlayer);
            if (args.length == 0) {
            	senderPlayer.sendMessage("/GiveLife [Player]");
            	return true;
            }
            Player receiverPlayer = Bukkit.getPlayerExact(args[0]);
        	if (receiverPlayer == null) {
        		senderPlayer.sendMessage("invalid Playername");
            	return true;
        	}
            if (lastLifePlayersender.isOnLastLife()) {
            	senderPlayer.sendMessage("You can't give Lifes on the Last Life");
            	return true;
            }
            LastLifePlayer lastLifePlayerreceiver = LastLife.getLastLifePlayerByPlayer(senderPlayer);
            lastLifePlayersender.removelife();
            lastLifePlayerreceiver.addlife();
            senderPlayer.sendMessage("Life transfered");
        }
    	
    	return true;
    }
}