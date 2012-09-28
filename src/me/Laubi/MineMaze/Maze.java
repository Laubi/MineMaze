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
