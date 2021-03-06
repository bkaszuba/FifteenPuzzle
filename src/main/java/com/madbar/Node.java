package com.madbar;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Kaszuba on 09.11.2017.
 */
public class Node implements Comparable<Node> {
    public int numberOfMoves;
    private StringBuilder moves;
    private int[][] currentPuzzleState;
    private FieldCoordinates blank;
    private int comparableValue;

    /**
     * Node constructor
     *
     * @param move       currently made move to be added to the list of moves
     * @param puzzleSize size of puzzle Area
     * @param puzzle     puzzle Area
     * @param blank      blank Field coordinates
     */
    public Node(String move, int puzzleSize, int[][] puzzle, FieldCoordinates blank, int numberOfMoves, String whichDistance) {
        //this.numberOfMoves=0;
        moves = new StringBuilder();
        if(!move.contains("start")){
        this.moves.append(move);
        this.numberOfMoves = numberOfMoves + 1;
        }
        currentPuzzleState = new int[puzzleSize][puzzleSize];
        coppy2dArray(puzzle);
        this.blank = blank;

        int hammingDistance = getHammingDistance();
        int manhattanDistance = getManhattanDistance();
        int linearConflict = getVerticalLinearConflict() + getHorizontalLinearConflict() + manhattanDistance;

        if (Objects.equals(whichDistance, "hamm"))
            comparableValue = hammingDistance;
        else if (Objects.equals(whichDistance, "manh"))
            comparableValue = manhattanDistance;
        else if (Objects.equals(whichDistance, "LinearConflict"))
            comparableValue = linearConflict;
    }

    /**
     * Function for copying 2d passed Array (whole objects not references)
     *
     * @param arrayToCopyFrom - array to copy values from
     */
    private void coppy2dArray(int[][] arrayToCopyFrom) {
        for (int i = 0; i < arrayToCopyFrom.length; i++) {
            currentPuzzleState[i] = Arrays.copyOf(arrayToCopyFrom[i], arrayToCopyFrom[i].length);
            //System.arraycopy(arrayToCopyFrom[i],0, currentPuzzleState[i], 0, arrayToCopyFrom[i].length);
        }
    }


    int[][] getCurrentPuzzleState() {
        return currentPuzzleState;
    }

    FieldCoordinates getBlank() {
        return blank;
    }

    StringBuilder getMoves() {
        return moves;
    }

    private int getHammingDistance() {
        return getMisplacedNumber() + this.numberOfMoves;
    }

    private int getMisplacedNumber() {
        int counter = 1;
        int misplaced = 0;
        for (int i = 0; i < currentPuzzleState.length; i++) {
            for (int j = 0; j < currentPuzzleState.length; j++) {
                if (counter > 15) {
                    counter = 0;
                }
                if (currentPuzzleState[j][i] != counter) {
                    misplaced++;
                }
                counter++;
            }
        }
        return misplaced;
    }

    private int getManhattanDistance() {
        int distance = 1;
        for (int i = 0; i < currentPuzzleState.length; i++) {
            for (int j = 0; j < currentPuzzleState.length; j++) {
                int piece = this.currentPuzzleState[j][i];
                if (piece != 0) {
                    double originalLine = Math.floor((piece - 1) / currentPuzzleState.length);
                    int originalColumn = (piece - 1) % this.currentPuzzleState.length;
                    distance += Math.abs(i - originalLine) + Math.abs(j - originalColumn);
                }
            }
        }
        return distance;

    }

    private int getVerticalLinearConflict() {
        int linear = 0;
        for (int i = 0; i < currentPuzzleState.length; i++) {
            int max = -1;

            for (int j = 0; j < currentPuzzleState.length; j++) {
                int piece = this.currentPuzzleState[j][i];
                if (piece != 0 && ((piece - 1) / currentPuzzleState.length) == i) {
                    if (piece > max) {
                        max = piece;
                    } else {
                        linear += 2;
                    }
                }
            }

        }
        return  linear;
    }
    private int getHorizontalLinearConflict() {
        int linear = 0;
        for (int j = 0; j < currentPuzzleState.length; j++) {
            int max = -1;

            for (int i = 0; i < currentPuzzleState.length; i++) {
                int piece = this.currentPuzzleState[j][i];
                if(j<3) {
                    if (piece != 0 && (piece % currentPuzzleState.length) == j + 1) {
                        if (piece > max) {
                            max = piece;
                        } else {
                            linear += 2;
                        }
                    }
                }
                else{
                    if (piece != 0 && (piece % currentPuzzleState.length) == 0) {
                        if (piece > max) {
                            max = piece;
                        } else {
                            linear += 2;
                        }
                    }
                }
            }

        }
        return  linear;
    }

        /**
         * Function for finding least value for priority queue used in AStar heuristic
         *
         * @param o - element to compare
         * @return 1 - if value is higher
         * -1 - if value is less
         */
        @Override
        public int compareTo (Node o){
            if (this.equals(o))
                return 0;
            else if (comparableValue > o.comparableValue)
                return 1;
            else
                return -1;
        }
    }

