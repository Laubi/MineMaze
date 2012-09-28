/**
 * Copyright 2012 Laubi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

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
