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
