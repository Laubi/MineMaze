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

import com.sk89q.worldedit.LocalPlayer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.Laubi.MineMaze.CommandHandler;
import me.Laubi.MineMaze.MineMazePlugin;

/**
 *
 * @author Laubi
 */
public class SubCommandHolder {
    private Method m;
    
    public SubCommandHolder(Method m){
        this.m = m;
    }
    
    public SubCommand getAnnotation(){
        return this.m.getAnnotation(SubCommand.class);
    }
    
    public String[] getAlias(){
        return this.getAnnotation().alias();
    }
    public boolean isConsoleAllowed(){
        return this.getAnnotation().console();
    }
    
    public String getPermission(){
        return this.getAnnotation().permission();
    }
    public String getAuthor(){
        return this.getAnnotation().author();
    }
    public String getDescription(){
        return this.getAnnotation().description();
    }

    public void invoke(LocalPlayer o1, CommandHandler o2, MineMazePlugin o3) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        this.m.invoke(null, o1, o2, o3);
    }
}
