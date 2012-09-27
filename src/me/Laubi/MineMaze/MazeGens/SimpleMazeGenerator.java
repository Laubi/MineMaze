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
