package com.madbar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.Assert.assertNotEquals;

/**
 * Unit test for simple Start.
 */
public class StartTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public StartTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( StartTest.class );
    }

/*
    public void testPuzzleInitialBlankFieldCoordinates(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);

        assertEquals(0, fifteenPuzzle.getBlankFieldX());
        assertEquals(0, fifteenPuzzle.getBlankFieldY());
    }
    public void testPuzzleMoveRight(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);
        assertTrue(fifteenPuzzle.moveRight());

        assertEquals(1, fifteenPuzzle.getBlankFieldX());
        assertEquals(0, fifteenPuzzle.getBlankFieldY());
        assertEquals(1, fifteenPuzzle.getPuzzleValueOnPlace(0,0));
        assertEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(1,0));
    }
    public void testPuzzleMoveLeft(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);
        assertFalse(fifteenPuzzle.moveLeft());

        assertTrue(fifteenPuzzle.moveRight());
        assertTrue(fifteenPuzzle.moveLeft());

        assertEquals(0, fifteenPuzzle.getBlankFieldX());
        assertEquals(0, fifteenPuzzle.getBlankFieldY());
        assertEquals(1, fifteenPuzzle.getPuzzleValueOnPlace(1,0));
        assertEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(0,0));
    }
    public void testPuzzleMoveDown(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);
        assertTrue(fifteenPuzzle.moveDown());

        assertEquals(0, fifteenPuzzle.getBlankFieldX());
        assertEquals(1, fifteenPuzzle.getBlankFieldY());
        assertEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(0,1));
        assertEquals(4, fifteenPuzzle.getPuzzleValueOnPlace(0,0));
    }
    public void testPuzzleMoveUp(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);
        assertFalse(fifteenPuzzle.moveUp());

        assertTrue(fifteenPuzzle.moveDown());
        assertTrue(fifteenPuzzle.moveUp());

        assertEquals(0, fifteenPuzzle.getBlankFieldX());
        assertEquals(0, fifteenPuzzle.getBlankFieldY());
        assertEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(0,0));
        assertEquals(4, fifteenPuzzle.getPuzzleValueOnPlace(0,1));
    }
    public void testPuzzleAllMovesCombination(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);

        assertFalse(fifteenPuzzle.moveLeft());
        assertTrue(fifteenPuzzle.moveRight());
        assertTrue(fifteenPuzzle.moveRight());
        assertTrue(fifteenPuzzle.moveRight());
        assertFalse(fifteenPuzzle.moveRight());

        assertTrue(fifteenPuzzle.moveDown());
        assertTrue(fifteenPuzzle.moveDown());

        assertTrue(fifteenPuzzle.moveLeft());
        assertTrue(fifteenPuzzle.moveLeft());

        assertEquals(1, fifteenPuzzle.getBlankFieldX());
        assertEquals(2, fifteenPuzzle.getBlankFieldY());
        assertEquals(7, fifteenPuzzle.getPuzzleValueOnPlace(3, 0));
        assertEquals(11, fifteenPuzzle.getPuzzleValueOnPlace(3, 1));
        assertEquals(10, fifteenPuzzle.getPuzzleValueOnPlace(3, 2));
        assertEquals(1, fifteenPuzzle.getPuzzleValueOnPlace(0,0));
        assertEquals(9, fifteenPuzzle.getPuzzleValueOnPlace(2,2));
    }
    public void testPuzzleShuffle(){
        FifteenPuzzle fifteenPuzzle = new FifteenPuzzle(4);

        assertEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(0, 0));
        assertEquals(1, fifteenPuzzle.getPuzzleValueOnPlace(1, 0));
        assertEquals(4, fifteenPuzzle.getPuzzleValueOnPlace(0, 1));
        assertEquals(15, fifteenPuzzle.getPuzzleValueOnPlace(3, 3));
        //shuffle
        fifteenPuzzle.shuffle(true);
        assertNotEquals(0, fifteenPuzzle.getPuzzleValueOnPlace(0, 0));
        assertNotEquals(1, fifteenPuzzle.getPuzzleValueOnPlace(1, 0));
        assertNotEquals(4, fifteenPuzzle.getPuzzleValueOnPlace(0, 1));
        assertNotEquals(15, fifteenPuzzle.getPuzzleValueOnPlace(3, 3));
    }
    */
}
