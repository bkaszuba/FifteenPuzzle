package com.madbar;

import java.util.Objects;

/**
 * Hello world!
 *
 */
public class Start
{
    public static void main( String[] args )
    {
        long elapsedTime;
        long start;

        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,9,0,11,12,13,10,14,15}; // 4 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,0,9,11,12,13,10,14,15}; // 5 moves
        int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,0,10,14,15}; // 6 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,10,0,14,15}; // 7 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,10,14,0,15}; // 8 moves

        //TYPE OF DISTANCE: Hamming or Manhattan is only used when solving with AStar
        //TYPES OF SOLVER METHODS: BFS or AStar

        int DFSDepth = 25;

        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4, "AStar", "Hamming"); //BFS or AStar
        fifteenPuzzle.setupPuzzleArea(customPuzzleArea);
        fifteenPuzzle.showPuzzle();
        start = System.nanoTime();
        fifteenPuzzle.AStar();
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms\n");

        FifteenPuzzle fifteenPuzzle2 = new FifteenPuzzle(4, "AStar", "Manhattan"); //BFS or AStar
        fifteenPuzzle2.setupPuzzleArea(customPuzzleArea);
        start = System.nanoTime();
        fifteenPuzzle2.AStar();
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");

        FifteenPuzzle fifteenPuzzle1 = new FifteenPuzzle(4, "DFS", "dontcare");
        fifteenPuzzle1.setupPuzzleArea(customPuzzleArea);
        start = System.nanoTime();
        fifteenPuzzle1.DFS(DFSDepth);
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");

        FifteenPuzzle fifteenPuzzle3 = new FifteenPuzzle(4, "BFS", "dontcare");
        fifteenPuzzle3.setupPuzzleArea(customPuzzleArea);
        start = System.nanoTime();
        fifteenPuzzle3.BFS();
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");
    }
}
