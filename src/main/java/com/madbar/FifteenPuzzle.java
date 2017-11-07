package com.madbar;

/**
 * Created by Kaszuba on 06.11.2017.
 */
public class FifteenPuzzle {

    private class FieldCoordinates {
        private int x;
        private int y;

        private FieldCoordinates(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private int [][] puzzleArea;
    private int puzzleSize;
    private FieldCoordinates blankField;

    public FifteenPuzzle(int puzzleSize) {
        this.puzzleSize = puzzleSize;
        puzzleArea = new int[this.puzzleSize][this.puzzleSize];
        int counter = 0;
        for(int i=0; i<this.puzzleSize; i++){
            for(int j=0; j<this.puzzleSize; j++){
                this.puzzleArea[j][i] = counter;
                counter++;
            }
        }
        this.blankField = new FieldCoordinates(0,0);
    }

    public boolean moveRight(){
        if(this.blankField.x >= this.puzzleSize - 1)
            return false;
        int tmp = puzzleArea[blankField.x + 1][blankField.y];
        puzzleArea[blankField.x + 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        blankField.x = blankField.x + 1;
        return true;
    }

    public boolean moveLeft(){
        if(this.blankField.x <= 0)
            return false;
        int tmp = puzzleArea[blankField.x - 1][blankField.y];
        puzzleArea[blankField.x - 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        blankField.x = blankField.x - 1;
        return true;
    }

    public boolean moveUp(){
        if(this.blankField.y <= 0)
            return false;
        int tmp = puzzleArea[blankField.x][blankField.y - 1];
        puzzleArea[blankField.x][blankField.y - 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        blankField.y = blankField.y - 1;
        return true;
    }

    public boolean moveDown(){
        if(this.blankField.y >= this.puzzleSize - 1)
            return false;
        int tmp = puzzleArea[blankField.x][blankField.y + 1];
        puzzleArea[blankField.x][blankField.y + 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        blankField.y = blankField.y +1;
        return true;
    }

    public int getBlankFieldX() {
        return blankField.x;
    }
    public int getBlankFieldY() {
        return blankField.y;
    }
    public int getPuzzleValueOnPlace(int x, int y){
        return puzzleArea[x][y];
    }
}
