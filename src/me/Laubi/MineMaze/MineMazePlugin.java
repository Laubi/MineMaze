package me.Laubi.MineMaze;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.util.Random;
import me.Laubi.MineMaze.Addons.AddonCollector;
import me.Laubi.MineMaze.Addons.MazeGenerators.MazeAddonCollector;
import me.Laubi.MineMaze.Addons.MazeGenerators.MazeGeneratorHolder;
import me.Laubi.MineMaze.Addons.SubCommands.SubAddonCollector;
import me.Laubi.MineMaze.Addons.SubCommands.SubCommandHolder;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Laubi
 */
public class MineMazePlugin extends JavaPlugin{
    public static final Random rnd = new Random();
    private AddonCollector<MazeGeneratorHolder> mazeCollector   = new MazeAddonCollector();
    private AddonCollector<SubCommandHolder>    subCollector    = new SubAddonCollector();
    
    private WorldEditPlugin worldEdit;
    
    public void registerMethods(Class<?> clazz){
        this.mazeCollector.registerMethods(clazz);
        this.subCollector.registerMethods(clazz);
    }

    @Override
    public void onEnable() {
        this.getCommand("/maze").setExecutor(new MazeCommandExecutor(this));
        
        this.worldEdit = (WorldEditPlugin)this.getServer().getPluginManager().getPlugin("WorldEdit");
        
        this.registerMethods(SubCommands.class);
        this.registerMethods(BasicMazes.class);
        this.registerMethods(LadderMaze.class);
    }

    public AddonCollector<MazeGeneratorHolder> getMazeCollector() {
        return mazeCollector;
    }
    public AddonCollector<SubCommandHolder> getSubCollector() {
        return subCollector;
    }

    public WorldEditPlugin getWorldEditPlugin() {
        return worldEdit;
    }
    public WorldEdit getWorldEdit(){
        return worldEdit.getWorldEdit();
    }
}
