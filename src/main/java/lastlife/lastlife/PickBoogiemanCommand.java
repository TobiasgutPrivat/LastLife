package lastlife.lastlife;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PickBoogiemanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	LastLife.pickBoogiemans(Integer.parseInt(args[0]));
    	
    	return true;
    }
}