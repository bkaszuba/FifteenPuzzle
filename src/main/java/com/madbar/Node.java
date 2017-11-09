package com.madbar;

import java.util.Arrays;

/**
 * Created by Kaszuba on 09.11.2017.
 */
public class Node {
    private int numberOfMoves;
    private StringBuilder moves;
    private int [][] currentPuzzleState;
    private FieldCoordinates blank;

    /**
     * Node constructor
     * @param move currently made move to be added to the list of moves
     * @param puzzleSize size of puzzle Area
     * @param puzzle puzzle Area
     * @param blank blank Field coordinates
     */
    public Node(String move, int puzzleSize, int [][] puzzle, FieldCoordinates blank){
        moves = new StringBuilder();
        this.moves.append(move);
        currentPuzzleState = new int [puzzleSize][puzzleSize];
        coppy2dArray(puzzle);
        this.blank = blank;
    }

    /**
     * Function for copying 2d passed Array (whole objects not references)
     * @param arrayToCopyFrom - array to copy values from
     */
    private void coppy2dArray(int [][] arrayToCopyFrom){
        for (int i = 0; i < arrayToCopyFrom.length; i++) {
            currentPuzzleState[i] = Arrays.copyOf(arrayToCopyFrom[i], arrayToCopyFrom[i].length);
        }
    }

    public int[][] getCurrentPuzzleState() {
        return currentPuzzleState;
    }

    public FieldCoordinates getBlank() {
        return blank;
    }

    public StringBuilder getMoves() {
        return moves;
    }



}
