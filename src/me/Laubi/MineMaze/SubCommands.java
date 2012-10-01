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

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import java.util.Iterator;
import me.Laubi.MineMaze.Addons.MazeGenerators.MazeGeneratorHolder;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommand;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommandHolder;
import me.Laubi.MineMaze.Exceptions.MineMazeException;
import me.Laubi.MineMaze.Exceptions.PermissionException;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Laubi
 */
public class SubCommands {
    @SubCommand(
            alias = "help",
            console = true,
            permission = "worldedit.minemaze.help",
            author = "Laubi",
            description = "You need help? Here you get it!"
    )
    public static void help(LocalPlayer player, CommandHandler handler, MineMazePlugin plugin){
        Iterator<SubCommandHolder> it = plugin.getSubCollector().iterator();
        
        
        player.print("All registrated subcommands:");
        while(it.hasNext()){
            SubCommandHolder cur = it.next();
            
            String msg = "    §9%1: §a: %2";
            msg = msg.replace("%1", StringUtils.join(cur.getAlias(),"|")).replace("%2",cur.getDescription());
            player.print(msg);
        }
    }
    
    
    @SubCommand(
            alias = "list",
            console = true,
            permission = "worldedit.minemaze.list",
            author = "Laubi",
            description = "List all aviable MazeGenerators"
    )
    public static void listmazes(LocalPlayer player, CommandHandler handler, MineMazePlugin plugin){
        if(plugin.getMazeCollector().size() == 0){
            player.printError("No mazes are registrated!");
            return;
        }
        
        Iterator<MazeGeneratorHolder> it = plugin.getMazeCollector().iterator();
        
        player.print("Registrated maze generators:");
        while(it.hasNext()){
            MazeGeneratorHolder cur = it.next();
            
            String msg = "    §9%1 => §a%2";
            msg = msg
                    .replace("%1", cur.getFullName())
                    .replace("%2", StringUtils.join(cur.getAlias(),'|'));
            
            player.print(msg);
        }
    }
    
    @SubCommand(
            alias = "maze",
            permission = "worldedit.minemaze.generate",
            author = "Laubi",
            description = "Create you own maze"
    )
    public static void maze(LocalPlayer player, CommandHandler handler, MineMazePlugin plugin){
        final WorldEdit we = plugin.getWorldEdit();
        try{
            MazeGeneratorHolder holder;
            LocalSession session;
            Region r;
            
            r = (session = we.getSession(player)).getSelection(player.getWorld());
            
            if(handler.containsArgument("gen")){
                holder = plugin.getMazeCollector().getEntry(handler.getArgumentValue("gen"));
            }else{
                holder = plugin.getMazeCollector().getRandomEntry();
            }
            
            if(holder == null) 
                throw new MineMazeException("Could not find the choosen mazegenerator");
            
            if(!holder.validateRegion(r)) 
                throw new MineMazeException("Your selection doesn't fit the requirements!");
            
            try{
                Maze maze = holder.invoke(player, handler, we, r);
            
                EditSession edit = session.createEditSession(player);
                edit.enableQueue();
                session.tellVersion(player);
                int affected = 0;
                
                for(int x = 0; x < maze.getWidth(); x++){
                    for(int y = 0; y < maze.getHeight(); y++){
                        for(int z = 0; z < maze.getLength(); z++){
                            Vector v = new Vector(x,y,z);
                            if(edit.setBlock(r.getMinimumPoint().add(v), maze.get(v))){
                                affected++;
                            }
                        }
                    }
                }
                session.remember(edit);
                edit.flushQueue();
                we.flushBlockBag(player, edit);
                player.print(affected + " block(s) have been changed.");
            }catch(Exception e){
                throw e.getCause();
            }
        }catch(IncompleteRegionException e){
            player.printError("Make a region selection first.");
        }catch(NumberFormatException e){
            player.printError("Number expected; string given.");
        }catch(MaxChangedBlocksException e){
            player.printError("Max blocks changed in an operation reached (" + e.getBlockLimit() + ").");
        } catch (UnknownItemException e) {
            player.printError("Block name '" + e.getID() + "' was not recognized.");
        } catch (InvalidItemException e) {
            player.printError(e.getMessage());
        } catch (DisallowedItemException e) {
            player.printError("Block '" + e.getID() + "' not allowed (see WorldEdit configuration).");
        }catch(MineMazeException e){
            player.printError(e.getMessage());
        }catch(Throwable e){
            player.printError("Please report this error: [See console]");
            player.printRaw(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
