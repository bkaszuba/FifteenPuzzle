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
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(3);
        fifteenPuzzle.showPuzzle();
        //fifteenPuzzle.shuffle(false);
        fifteenPuzzle.BFS();
    }
}
