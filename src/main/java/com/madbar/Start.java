package com.madbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class Start
{
    public static void main( String[] args ) throws IOException {
        final String acronym = args[0];
        final String strategy = args[1];
        final String inputFilename = args[2];
        final String solutionFilename = args[3];
        final String additionalInformationFilename = args[4];


        int DFSDepth = 20;


        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4, acronym, strategy); //BFS or AStar
        //fifteenPuzzle.setupPuzzleArea(customPuzzleArea);
        try {
            fifteenPuzzle.readFromFile(inputFilename);
        fifteenPuzzle.showPuzzle();

        switch(acronym){

            case "bfs":
                fifteenPuzzle.BFS();
                break;
            case "dfs":
                fifteenPuzzle.DFS(DFSDepth);
                break;
            case "astr":
                fifteenPuzzle.AStar();
                break;


        }

        fifteenPuzzle.saveSolutionToFile(solutionFilename);
        fifteenPuzzle.saveAdditionalInformationToFile(additionalInformationFilename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        fifteenPuzzle.showPuzzle();



        long elapsedTime;
        long start;

        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,9,0,11,12,13,10,14,15}; // 4 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,0,9,11,12,13,10,14,15}; // 5 moves
       // int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,0,10,14,15}; // 6 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,10,0,14,15}; // 7 moves
        //int [] customPuzzleArea = {1,2,3,4,5,6,7,8,13,9,11,12,10,14,0,15}; // 8 moves

        //TYPE OF DISTANCE: Hamming or Manhattan is only used when solving with AStar
        //TYPES OF SOLVER METHODS: BFS or AStar











//        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4, "AStar", "Hamming"); //BFS or AStar
//        //fifteenPuzzle.setupPuzzleArea(customPuzzleArea);
//        try {
//            fifteenPuzzle.readFromFile("test.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        fifteenPuzzle.showPuzzle();
//        start = System.nanoTime();
//        fifteenPuzzle.AStar();
//        fifteenPuzzle.saveAdditionalInformationToFile("statAStarH.txt");
//        elapsedTime = System.nanoTime() - start;
//        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms\n");
//
//
//
//
//
//
//        FifteenPuzzle fifteenPuzzle2 = new FifteenPuzzle(4, "AStar", "Manhattan"); //BFS or AStar
//        //fifteenPuzzle2.setupPuzzleArea(customPuzzleArea);
//        try {
//            fifteenPuzzle2.readFromFile("test.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        start = System.nanoTime();
//        fifteenPuzzle2.AStar();
//        fifteenPuzzle2.saveAdditionalInformationToFile("statAStarM.txt");
//
//        elapsedTime = System.nanoTime() - start;
//        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");
//
//
//
//
//        FifteenPuzzle fifteenPuzzle1 = new FifteenPuzzle(4, "DFS", "dontcare");
////        fifteenPuzzle1.setupPuzzleArea(customPuzzleArea);
//        try {
//            fifteenPuzzle1.readFromFile("test.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        start = System.nanoTime();
//        fifteenPuzzle1.DFS(DFSDepth);
//        fifteenPuzzle1.saveAdditionalInformationToFile("statDFS.txt");
//        elapsedTime = System.nanoTime() - start;
//        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");
//
//
//
//        FifteenPuzzle fifteenPuzzle3 = new FifteenPuzzle(4, "BFS", "RDUL");
//        //fifteenPuzzle3.setupPuzzleArea(customPuzzleArea);
//        try {
//            fifteenPuzzle3.readFromFile("test.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        start = System.nanoTime();
//        fifteenPuzzle3.BFS();
//        fifteenPuzzle3.saveAdditionalInformationToFile("statBFS.txt");
//        fifteenPuzzle3.saveSolutionToFile("solutionBFS");
//        elapsedTime = System.nanoTime() - start;
//        System.out.println("Time: "+ (float)elapsedTime/1000000 + "ms");

    }
}
