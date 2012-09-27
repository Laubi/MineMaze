package me.Laubi.MineMaze.MazeGens;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Laubi
 */
public class DeepFirstSearchChaos extends SimpleMazeGenerator{
    public DeepFirstSearchChaos(int width, int length){
        super(width, length);
    }
    
    
    @Override
    public boolean[][] generateMaze() {
        List<Point> list = new ArrayList<Point>();
        int last = 0;
        
        this.maze[1][1] = false;
        last = this.addNeighbours(new Point(1,1), list);
        
        while(!list.isEmpty()){
            int curi = this.rnd.nextInt() % last;
            curi = list.size() - ((curi < 0 ? -curi : curi) +1);
            Point cur = list.get(curi);
            if(this.hasVisitedNeighbours(cur)){
                last--;
            }else{
                last = this.addNeighbours(cur, list);
                this.maze[cur.x][cur.y] = false;
            }
            list.remove(curi);
            if(last == 0)
                last++;
        }
        return this.maze;
    }
    
    private int addNeighbours(Point p, List<Point> list) {
        int c = 0;
        if (p.x - 1 != 0)                 if (this.maze[p.x - 1][p.y] == true) {list.add(new Point(p.x - 1, p.y));c++;} 
        if (p.x + 1 != this.width - 1)    if (this.maze[p.x + 1][p.y] == true) {list.add(new Point(p.x + 1, p.y));c++;}
        if (p.y - 1 != 0)                 if (this.maze[p.x][p.y - 1] == true) {list.add(new Point(p.x, p.y - 1));c++;}
        if (p.y + 1 != this.length - 1)   if (this.maze[p.x][p.y + 1] == true) {list.add(new Point(p.x, p.y + 1));c++;}
        return c;
    }

    private boolean hasVisitedNeighbours(Point p){
        int c = 0;
        if (this.maze[p.x - 1][p.y] == false) {c++;}
        if (this.maze[p.x + 1][p.y] == false) {c++;}
        if (this.maze[p.x][p.y - 1] == false) {c++;}
        if (this.maze[p.x][p.y + 1] == false) {c++;}
        return c > 1;
    }
    
}