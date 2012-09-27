package me.Laubi.MineMaze;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.Region;

/**
 *
 * @author Laubi
 */
public class Maze {
    private BaseBlock [][][] maze;
    private int width, length, height;
    
    public Maze(Region r){
        width = r.getWidth();
        height = r.getHeight();
        length = r.getLength();
        maze = new BaseBlock[width][height][length];
    }
    public boolean set(int x, int y, int z, BaseBlock b){
        return set(new Vector(x,y,z),b);
    }
    public boolean set(Vector at, BaseBlock b){
        if(!has(at)){
            return false;
        }
        maze[at.getBlockX()][at.getBlockY()][at.getBlockZ()] = b;
        return true;
    }
    
    public BaseBlock get(int x, int y, int z){
        return get(new Vector(x,y,z));
    }
    public BaseBlock get(Vector at){
        if(!has(at)){
            return null;
        }
        return maze[at.getBlockX()][at.getBlockY()][at.getBlockZ()];
    }
    
    public boolean has(Vector v){
        final int x = v.getBlockX(), y = v.getBlockY(), z = v.getBlockZ();
        return x >= 0 && x < width && y >= 0 && y < height && z >= 0 && z < length;
    }

    public BaseBlock[][][] getMaze() {
        return maze;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
    
    
}
