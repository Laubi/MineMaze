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

package me.Laubi.MineMaze.MazeGens;

import java.util.Random;

/**
 *
 * @author Laubi
 */
public abstract class SimpleMazeGenerator {
    protected int length, width;
    protected final Random rnd = me.Laubi.MineMaze.MineMazePlugin.rnd;
    protected boolean [][] maze;
    
    protected SimpleMazeGenerator(int width, int length){
        this.width = width;
        this.length = length;
        this.maze = new boolean[width][length];
        this.flushMaze();
    }
    
    public final void flushMaze(){
        for(int x = 0; x < this.width; x++){
            for(int y = 0; y < this.length; y++){
                this.maze[x][y] = true;
            }
        }
    }
    
    public boolean[][] reGenerateMaze(){
        this.flushMaze();
        return this.generateMaze();
    }
    
    public abstract boolean[][] generateMaze();
}
