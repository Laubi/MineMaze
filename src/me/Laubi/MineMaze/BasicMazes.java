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

import com.sk89q.worldedit.DisallowedItemException;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.UnknownItemException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.Region;
import me.Laubi.MineMaze.Addons.MazeGenerators.MazeGenerator;
import me.Laubi.MineMaze.MazeGens.*;
import static me.Laubi.MineMaze.MineMazePlugin.rnd;

/**
 *
 * @author Laubi
 */
public class BasicMazes {

    private static final BaseBlock airBlock = new BaseBlock(BlockID.AIR);

    @MazeGenerator(
            alias = "prims",
            fullName = "Prims Maze Generator",
            author = "Laubi"
    )
    public static Maze prims(LocalPlayer player, CommandHandler handler, WorldEdit we, Maze maze)
            throws UnknownItemException, DisallowedItemException {
        return genBasicMaze(player, handler, we, maze, new Prims(maze.getWidth(), maze.getLength()));
    }

    @MazeGenerator(
            alias = "primsc",
            fullName = "Prims Chaotic Maze Generator",
            author = "Laubi"
    )
    public static Maze primsc(LocalPlayer player, CommandHandler handler, WorldEdit we, Maze maze)
            throws UnknownItemException, DisallowedItemException {
        return genBasicMaze(player, handler, we, maze, new PrimsChaos(maze.getWidth(), maze.getLength()));
    }

    @MazeGenerator(
            alias = "dfs",
            fullName = "DeepFirstSearch Maze Generator",
            author = "Laubi"
    )
    public static Maze dfs(LocalPlayer player, CommandHandler handler, WorldEdit we, Maze maze)
            throws UnknownItemException, DisallowedItemException {
        return genBasicMaze(player, handler, we, maze, new DeepFirstSearch(maze.getWidth(), maze.getLength()));
    }

    @MazeGenerator(
            alias = "dfsc",
            fullName = "DeepFirstSearch Chaotic Maze Generator",
            author = "Laubi"
    )
    public static Maze dfsc(LocalPlayer player, CommandHandler handler, WorldEdit we, Maze maze)
            throws UnknownItemException, DisallowedItemException {
        return genBasicMaze(player, handler, we, maze, new DeepFirstSearchChaos(maze.getWidth(), maze.getLength()));
    }

    private static Maze genBasicMaze(LocalPlayer player, CommandHandler handler, WorldEdit we, Maze maze, SimpleMazeGenerator gen)
            throws UnknownItemException, DisallowedItemException {
        //Maze maze = new Maze(region);

        Pattern wallPattern = new SingleBlockPattern(new BaseBlock(BlockID.STONE));

        if (handler.containsArgument("mat")) {
            wallPattern = we.getBlockPattern(player, handler.getArgumentValue("mat"));
            if (wallPattern == null) {
            }
        }



        boolean[][] flatMaze = gen.generateMaze();

        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                for (int z = 0; z < maze.getLength(); z++) {
                    maze.set(x, y, z, flatMaze[x][z] ? wallPattern.next(x, y, z) : airBlock);
                }
            }
        }



        if (!handler.containsArgument("nodoors")) {
            for (int i = 0; i < maze.getHeight(); i++) {
                maze.set(0, i, 1, airBlock);
                maze.set(maze.getWidth() - 1, i, maze.getLength() - 2, airBlock);

                if (maze.getWidth() % 2 == 0 || maze.getLength() % 2 == 0) {
                    maze.set(maze.getWidth() - 2, i, maze.getLength() - 2, airBlock);
                }
                if (maze.getWidth() % 2 == 0 && maze.getLength() % 2 == 0) {
                    maze.set(maze.getWidth() - 2, i, maze.getLength() - 3, airBlock);
                }
            }
        }

        if (handler.containsArgument("bottom")) {
            Pattern floorPattern = we.getBlockPattern(player, handler.getArgumentValue("bottom"));
            for (int i = 0; i < maze.getWidth(); i++) {
                for (int j = 0; j < maze.getLength(); j++) {
                    maze.set(i, 0, j, floorPattern.next(i, 0, j));
                }
            }
        }

        if (handler.containsArgument("top")) {
            Pattern roofPattern = we.getBlockPattern(player, handler.getArgumentValue("top"));
            for (int i = 0; i < maze.getWidth(); i++) {
                for (int j = 0; j < maze.getLength(); j++) {
                    maze.set(i, maze.getHeight() - 1, j, roofPattern.next(i, maze.getHeight() - 1, j));
                }
            }
        }

        if (handler.containsArgument("torches")) {
            final BaseBlock torchBlock = handler.containsArgument("r") ? new BaseBlock(BlockID.REDSTONE_TORCH_ON) : new BaseBlock(BlockID.TORCH);
            final int p = handler.containsArgument("p") ? Integer.parseInt(handler.getArgumentValue("p")) : 10;
            int he = handler.containsArgument("h") ? Integer.parseInt(handler.getArgumentValue("h")) : 2;

            he += (handler.containsArgument("bottom") ? 1 : 0);

            if (he <= maze.getHeight() && he > 0) {
                for (int i = 1; i < maze.getWidth() - 1; i++) {
                    for (int j = 1; j < maze.getLength() - 1; j++) {
                        if (rnd.nextInt(100) < p && maze.get(i, he - 1, j) == airBlock) {
                            if (maze.get(i - 1, he - 1, j) != airBlock
                                    || maze.get(i + 1, he - 1, j) != airBlock
                                    || maze.get(i, he - 1, j - 1) != airBlock
                                    || maze.get(i, he - 1, j + 1) != airBlock) {
                                maze.set(i, he - 1, j, torchBlock);
                            }
                        }
                    }
                }
            }
        }
        return maze;
    }
}
