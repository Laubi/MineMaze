package me.Laubi.MineMaze;

import com.sk89q.worldedit.LocalPlayer;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommandHolder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Laubi
 */
public class MazeCommandExecutor implements CommandExecutor{
    private MineMazePlugin plugin;

    public MazeCommandExecutor(MineMazePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        LocalPlayer player = plugin.getWorldEditPlugin().wrapCommandSender(cs);
        try{
            CommandHandler cmdHandler = new CommandHandler(strings);

            SubCommandHolder subCommand = this.plugin.getSubCollector().getEntry(cmdHandler.getSubCommand());
            
            if(subCommand == null){
                throw new Exception("Cannot find the subcommand '" + cmdHandler.getSubCommand() + "'!");
            }
            
            if(subCommand.isConsoleAllowed() == false && player.isPlayer() == false){
                throw new Exception("Only a player is allowed to use this command!");
            }
            
            if(false){ //TODO: Add permissioncheck
                throw new Exception("You are not allowed to use this command!");
            }
            
            subCommand.invoke(player, cmdHandler, plugin);
        
        }catch(Exception e){
            player.printError(e.getMessage());
        }
        
        return true;
    }
    
}
