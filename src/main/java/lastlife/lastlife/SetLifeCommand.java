package lastlife.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLifeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	Player senderPlayer = (Player) sender;
        if (args.length == 0) {
        	senderPlayer.sendMessage("/SetLife [Player] [Lifes]");
        	return true;
        }
        Player receiverPlayer = Bukkit.getPlayerExact(args[0]);
    	if (receiverPlayer == null) {
    		senderPlayer.sendMessage("invalid Playername");
        	return true;
    	}
    	
    	LastLifePlayer lastLifePlayerreceiver = LastLife.getLastLifePlayerByPlayer(receiverPlayer);
    	try {
    		lastLifePlayerreceiver.setLifes(Integer.parseInt(args[1]));
    		senderPlayer.sendMessage("Lifes of " + args[0] + " set to " + args[1]);
		} catch (Exception e) {
			senderPlayer.sendMessage("/SetLife [Player] [Lifes]");
		}
    	return true;
    }
}