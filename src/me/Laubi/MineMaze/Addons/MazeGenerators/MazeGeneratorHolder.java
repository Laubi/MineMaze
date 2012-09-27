package me.Laubi.MineMaze.Addons.MazeGenerators;

import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import me.Laubi.MineMaze.CommandHandler;
import me.Laubi.MineMaze.Maze;

/**
 *
 * @author Laubi
 */
public class MazeGeneratorHolder {
    private Method m;
    
    public MazeGeneratorHolder(Method m){
        this.m = m;
    }
    
    public MazeGenerator getAnnotation(){
        return m.getAnnotation(MazeGenerator.class);
    }
    
    public String [] getAlias(){
        return this.getAnnotation().alias();
    }
    public String getAuthor(){
        return this.getAnnotation().author();
    }
    public String getPermission(){
        return this.getAnnotation().permission();
    }
    public String getFullName(){
        return this.getAnnotation().fullName();
    }
    
    public int minWidth(){
        return this.getAnnotation().minWidth();
    }
    public int minLength(){
        return this.getAnnotation().minLength();
    }
    public int minHeight(){
        return this.getAnnotation().minHeight();
    }
    public int maxWidth(){
        return this.getAnnotation().maxWidth();
    }
    public int maxLength(){
        return this.getAnnotation().maxLength();
    }
    public int maxHeight(){
        return this.getAnnotation().maxHeight();
    }
    
    public Maze invoke(LocalPlayer player, CommandHandler handler, WorldEdit we, Region region) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        return (Maze)this.m.invoke(null, player, handler, we, region);
    }
    
    public boolean validateRegion(Region r){
        return  !(
                (r.getWidth() < this.minWidth()) || 
                (r.getHeight() < this.minHeight()) || 
                (r.getLength() < this.minLength()) ||
                
                (this.maxWidth() < 0 ? false : r.getWidth() > this.maxWidth()) ||
                (this.maxHeight() < 0 ? false : r.getHeight() > this.maxHeight()) ||
                (this.maxLength() < 0 ? false : r.getLength() > this.maxLength()));
    }
}
