package me.Laubi.MineMaze;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import java.util.Iterator;
import me.Laubi.MineMaze.Addons.MazeGenerators.MazeGeneratorHolder;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommand;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommandHolder;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Laubi
 */
public class SubCommands {
    @SubCommand(
            alias = "help",
            console = true,
            permission = "help",
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
            permission = "listmazes",
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
            permission = "generate",
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
            
            if(holder == null){
                throw new Exception("Could not find the choosen mazegenerator");
            }
            
            if((player instanceof BukkitPlayer) && !((BukkitPlayer)player).getPlayer().isOp()){ //TODO: implement Permission api
                throw new Exception("You are not allowed to use this command!");
            }
            
            if(!holder.validateRegion(r)){
                throw new Exception("Your selection doesn't fit the requirements!");
            }
            
            Maze maze = holder.invoke(player, handler, we, r);
            
            EditSession edit = session.createEditSession(player);
            
            for(int x = 0; x < maze.getWidth(); x++){
                for(int y = 0; y < maze.getHeight(); y++){
                    for(int z = 0; z < maze.getLength(); z++){
                        Vector v = new Vector(x,y,z);
                        edit.setBlock(r.getMinimumPoint().add(v), maze.get(v));
                    }
                }
            }
            
            session.remember(edit);
        }catch(NullPointerException e){
            //Do nothing.
        }catch(Exception e){
            player.printError("Could not generate the maze: " + e.getMessage());
        }
    }
}
