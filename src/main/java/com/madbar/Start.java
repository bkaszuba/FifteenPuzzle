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
        //int [] customPuzzleArea = {0,1,8,3,9,2,6,4,13,5,10,7,14,15,12,11}; //SOLVABLE
        //int [] customPuzzleArea = {0,1,2,7,8,9,12,10,13,3,6,4,15,14,11,5}; //WIKAMP
        //int [] customPuzzleArea = {15,2,1,12,8,5,6,11,4,9,10,7,3,14,13,0};
        //int [] customPuzzleArea = {1,3,0,4,5,2,7,8,9,6,10,11,13,14,15,12}; // 7 moves
        int [] customPuzzleArea = {1,2,0,4,5,6,3,7,9,10,11,8,13,14,15,12};

        //TYPE OF DISTANCE: Hamming or Manhattan is only used when solving with AStar
        //TYPES OF SOLVER METHODS: BFS or AStar


        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4, "AStar", "Hamming"); //BFS or AStar
        fifteenPuzzle.setupPuzzleArea(customPuzzleArea);
        fifteenPuzzle.showPuzzle();
        long start = System.nanoTime();
        fifteenPuzzle.AStar();
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ elapsedTime/1000000 + "ms\n");

        FifteenPuzzle fifteenPuzzle2 = new FifteenPuzzle(4, "AStar", "Manhattan"); //BFS or AStar
        fifteenPuzzle2.setupPuzzleArea(customPuzzleArea);
        start = System.nanoTime();
        fifteenPuzzle2.AStar();
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ elapsedTime/1000000 + "ms");

        FifteenPuzzle fifteenPuzzle1 = new FifteenPuzzle(4, "DFS", "dontcare");
        fifteenPuzzle1.setupPuzzleArea(customPuzzleArea);
        start = System.nanoTime();
        fifteenPuzzle1.DFS();
        elapsedTime = System.nanoTime() - start;
        System.out.println("Time: "+ elapsedTime/1000000 + "ms");
    }
}
