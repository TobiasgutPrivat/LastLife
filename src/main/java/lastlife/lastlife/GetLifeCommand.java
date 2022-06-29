package lastlife.lastlife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetLifeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            if (args.length == 0) {
            	senderPlayer.sendMessage("/GetLife [Player]");
            	return true;
            }
            Player receiverPlayer = Bukkit.getPlayerExact(args[0]);
        	if (receiverPlayer == null) {
        		senderPlayer.sendMessage("invalid Playername");
            	return true;
        	}
            senderPlayer.sendMessage(receiverPlayer.getName() + " has " + LastLife.getLastLifePlayerByPlayer(receiverPlayer).getLifes().toString() + " Lifes");
        }
    	return true;
    }
}