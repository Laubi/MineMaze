package me.Laubi.MineMaze.Addons.SubCommands;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.Laubi.MineMaze.Addons.AddonCollector;
import static me.Laubi.MineMaze.MineMazePlugin.rnd;

/**
 *
 * @author Laubi
 */
public class SubAddonCollector implements AddonCollector<SubCommandHolder>{
    List<SubCommandHolder> subCommands = new ArrayList<SubCommandHolder>();

    @Override
    public SubCommandHolder getEntry(String alias) {
        Iterator<SubCommandHolder> it = this.subCommands.iterator();
        
        SubCommandHolder cur, result = null;
        
        while(it.hasNext() && result == null){
            cur = it.next();
            
            for(String c : cur.getAlias()){
                if(alias.equalsIgnoreCase(c)){
                    result = cur;
                    break;
                }
            }
        }
        return result;
    }
    
    @Override
    public void registerMethods(Class<?> clazz) {
        Method [] methods = clazz.getMethods();
        
        for(Method c : methods){
            if(c.isAnnotationPresent(SubCommand.class) && Modifier.isStatic(c.getModifiers())){
                this.subCommands.add(new SubCommandHolder(c));
            }
        }
    }
    
    @Override
    public int size(){
        return this.subCommands.size();
    }

    @Override
    public Iterator<SubCommandHolder> iterator() {
        return this.subCommands.iterator();
    }
    
    @Override
    public SubCommandHolder getRandomEntry() {
        return this.subCommands.get(rnd.nextInt(this.subCommands.size()));
    }
}
