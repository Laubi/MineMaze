package me.Laubi.MineMaze.Addons.MazeGenerators;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import me.Laubi.MineMaze.Addons.AddonCollector;
import static me.Laubi.MineMaze.MineMazePlugin.rnd;

/**
 *
 * @author Laubi
 */
public class MazeAddonCollector implements AddonCollector<MazeGeneratorHolder>{
    private List<MazeGeneratorHolder> registrated = new ArrayList<MazeGeneratorHolder> ();

    @Override
    public MazeGeneratorHolder getEntry(String alias) {
        Iterator<MazeGeneratorHolder> it = this.registrated.iterator();
        
        MazeGeneratorHolder cur, result = null;
        
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
            if(c.isAnnotationPresent(MazeGenerator.class) && Modifier.isStatic(c.getModifiers())){
                this.registrated.add(new MazeGeneratorHolder(c));
            }
        }
    }
    
    @Override
    public int size(){
        return this.registrated.size();
    }

    @Override
    public Iterator<MazeGeneratorHolder> iterator() {
        return this.registrated.iterator();
    }

    @Override
    public MazeGeneratorHolder getRandomEntry() {
        return this.registrated.get(rnd.nextInt(this.registrated.size()));
    }
}
