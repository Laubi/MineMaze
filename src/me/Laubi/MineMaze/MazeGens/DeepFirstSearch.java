package me.Laubi.MineMaze.MazeGens;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laubi
 */
public class DeepFirstSearch extends SimpleMazeGenerator{
    public DeepFirstSearch(int width, int length){
        super(width, length);
    }
    
    @Override
    public boolean[][] generateMaze() {
        List<Point> list = new ArrayList<Point>();
        int last; 
        
        this.maze[1][1] = false;
        last = this.addNeighbours(new Point(1,1), list);
        while(!list.isEmpty()){
            
            int curi = this.rnd.nextInt() % last;
            curi = list.size() - ((curi < 0 ? -curi : curi) +1);
            Point cur = this.getTargetBlock(list.get(curi));
            if(cur != null){
                this.maze[cur.x][cur.y] = false;
                this.maze[list.get(curi).x][list.get(curi).y] = false;
                last = this.addNeighbours(cur, list);
                if(last == 0)
                    last ++;
            }
            list.remove(curi);
        }
        
        return this.maze;
    }

    
    private int addNeighbours(Point target, List<Point> list){
        int count = 0;
        if (target.y - 2 >= 0)                 if (this.maze[target.x][target.y - 2] == true) {list.add(new Point(target.x,  target.y - 1));count++;}
        if (target.y + 2 <= this.length - 2)   if (this.maze[target.x][target.y + 2] == true) {list.add(new Point(target.x, target.y + 1));count++;}
        if (target.x - 2 >= 0)                 if (this.maze[target.x - 2][target.y] == true) {list.add(new Point(target.x - 1, target.y));count++;}
        if (target.x + 2 <= this.width - 2)    if (this.maze[target.x + 2][target.y] == true) {list.add(new Point(target.x + 1, target.y));count++;}
    
        return count;
    }
    
    private Point getTargetBlock(Point cur){
        int cnt = 0;
        
        if (this.maze[cur.x][cur.y + 1] == false) {cnt++;}
        if (this.maze[cur.x][cur.y - 1] == false) {cnt++;}
        if (this.maze[cur.x + 1][cur.y] == false) {cnt++;}
        if (this.maze[cur.x - 1][cur.y] == false) {cnt++;}

        if (cnt == 1) {
            if (this.maze[cur.x][cur.y + 1] == false) {return new Point(cur.x, cur.y - 1);}
            if (this.maze[cur.x][cur.y - 1] == false) {return new Point(cur.x, cur.y + 1);}
            if (this.maze[cur.x + 1][cur.y] == false) {return new Point(cur.x - 1, cur.y);}
            if (this.maze[cur.x - 1][cur.y] == false) {return new Point(cur.x + 1, cur.y);}
        }
        return null; 
    }
}
