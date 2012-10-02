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

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import me.Laubi.MineMaze.Interfaces.MazeGenerator;
import me.Laubi.MineMaze.Interfaces.SubCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Laubi
 */
public class MineMazePlugin extends JavaPlugin{
    public static final Random rnd = new Random();
    private List<Method> mazeGenerators = new ArrayList<Method>();
    private List<Method> subCommands = new ArrayList<Method>();
    private WorldEditPlugin worldEdit;
    
    public void registerMethods(Class<?> clazz){
        Method [] methods = clazz.getMethods();
        for(Method c : methods){
            if(Modifier.isStatic(c.getModifiers())){
                if(c.isAnnotationPresent(MazeGenerator.class)){
                    mazeGenerators.add(c);
                }else if(c.isAnnotationPresent(SubCommand.class)){
                    subCommands.add(c);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        this.getCommand("/maze").setExecutor(new MazeCommandExecutor(this));
        
        this.worldEdit = (WorldEditPlugin)this.getServer().getPluginManager().getPlugin("WorldEdit");
        
        this.registerMethods(SubCommands.class);
        this.registerMethods(BasicMazes.class);
        this.registerMethods(LadderMaze.class);
    }
    
    public WorldEditPlugin getWorldEditPlugin() {
        return worldEdit;
    }
    public WorldEdit getWorldEdit(){
        return worldEdit.getWorldEdit();
    }

    public List<Method> getMazeGenerators() {
        return mazeGenerators;
    }
    public List<Method> getSubCommands() {
        return subCommands;
    }
    public Method getSubCommandByAlias(String name){
        Method m = null;
        
        Iterator<Method> it = subCommands.iterator();
        
        while(it.hasNext() && m == null){
            Method cur = it.next();
            SubCommand sub = cur.getAnnotation(SubCommand.class);
            for(String c : sub.alias()){
                if(c.equalsIgnoreCase(name)){
                    m = cur;
                }
            }
        }
        return m;
    }
    public Method getMazeGeneratorByAlias(String genName){
        Method m = null;
        
        Iterator<Method> it = mazeGenerators.iterator();
        
        while(it.hasNext() && m == null){
            Method cur = it.next();
            MazeGenerator sub = cur.getAnnotation(MazeGenerator.class);
            for(String c : sub.alias()){
                if(c.equalsIgnoreCase(genName)){
                    m = cur;
                }
            }
        }
        return m;
    }
}
