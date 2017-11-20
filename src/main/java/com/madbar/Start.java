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
        int [] customPuzzleArea = {4,8,0,1,2,6,3,7,5};

        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(3);
        fifteenPuzzle.setupPuzzleArea(customPuzzleArea);
        fifteenPuzzle.showPuzzle();
        //fifteenPuzzle.shuffle(false);
        fifteenPuzzle.BFS();
    }
}
