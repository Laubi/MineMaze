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
