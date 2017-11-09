package com.madbar;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Kaszuba on 06.11.2017.
 */
public class FifteenPuzzle {



    private int [][] puzzleArea;
    private int puzzleSize;
    private FieldCoordinates blankField;
    private List<Node> nodes;
    private Boolean isSolved;
    protected final Logger log = Logger.getLogger(getClass().getName());
    private String solution1;           //Possible solutions: ex. 012345678
    private String solution2;           // and 123456780
    private String solutionFound;       //Solution found (step by step answer)

    /**
     * FifteenPuzzle constructor with initialization of puzzle         in case of size = 4 [ 0 1 2 3 ]
     * where 0 is always the first field and its a "blank" field                           [ 4 5 6 7 ] etc...
     * @param puzzleSize - puzzle size (puzzle is always a square)
     */
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
        this.blankField = new FieldCoordinates(0,1);
        //This is puzzle board from this website and its solvable
        //The shuffle function is down there but it is not checking if puzzle is solvable
        puzzleArea[0][0] = 1;
        puzzleArea[1][0] = 8;
        puzzleArea[2][0] = 2;
        puzzleArea[0][1] = 0;
        puzzleArea[1][1] = 4;
        puzzleArea[2][1] = 3;
        puzzleArea[0][2] = 7;
        puzzleArea[1][2] = 6;
        puzzleArea[2][2] = 5;
        this.nodes = new ArrayList<>();
        this.isSolved = false;
        configureLogger();
        getSolution();
    }

    /**
     * Function for moving blank place right
     * @return true - if move is possible
     *         false - if move is impossible
     */
    boolean moveRight(Node node){
        setFifteenPuzzleFromNode(node);                 /*Setting a previous node settings*/
        if(checkIfSolved())                             /*Check if solved*/
            this.solutionFound = node.getMoves().toString();

        if(this.blankField.x >= this.puzzleSize - 1)    /*Check if can be moved*/
            return false;

        int tmp = puzzleArea[blankField.x + 1][blankField.y];   /*Move*/
        puzzleArea[blankField.x + 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x + 1;
        Node nextNode = new Node(node.getMoves().toString()+"right", this.puzzleSize, this.puzzleArea, tmpBlankField); /*Save the new setting to new node*/
        this.nodes.add(nextNode);
        return true;
    }

    /**
     * Function for moving blank place left
     * @return true - if move is possible
     *         false - if move is impossible
     */
    boolean moveLeft(Node node){
        setFifteenPuzzleFromNode(node);
        if(checkIfSolved())
            this.solutionFound = node.getMoves().toString();
        if(this.blankField.x <= 0)
            return false;

        int tmp = puzzleArea[blankField.x - 1][blankField.y];
        puzzleArea[blankField.x - 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x - 1;

        Node nextNode = new Node(node.getMoves().toString()+"left", this.puzzleSize, this.puzzleArea, tmpBlankField);
        this.nodes.add(nextNode);
        return true;
    }

    /**
     * Function for moving blank place up
     * @return true - if move is possible
     *         false - if move is impossible
     */
    boolean moveUp(Node node){
        setFifteenPuzzleFromNode(node);
        if(checkIfSolved())
            this.solutionFound = node.getMoves().toString();
        if(this.blankField.y <= 0)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y - 1];
        puzzleArea[blankField.x][blankField.y - 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y - 1;
        Node nextNode = new Node(node.getMoves().toString()+"up", this.puzzleSize, this.puzzleArea, tmpBlankField);
        this.nodes.add(nextNode);
        return true;
    }

    /**
     * Function for moving blank place down
     * @return true - if move is possible
     *         false - if move is impossible
     */
    boolean moveDown(Node node){
        setFifteenPuzzleFromNode(node);
        if(checkIfSolved())
            this.solutionFound = node.getMoves().toString();
        if(this.blankField.y >= this.puzzleSize - 1)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y + 1];
        puzzleArea[blankField.x][blankField.y + 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y +1;
        Node nextNode = new Node(node.getMoves().toString()+"down", this.puzzleSize, this.puzzleArea, tmpBlankField);
        this.nodes.add(nextNode);
        return true;
    }

    /**
     * Function for solving puzzle using BFS
     */
    void BFS(){
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField); /*Starting node the same as generated*/
        nodes.add(startingNode);

        do{
            List<Node> currentNodes = new ArrayList<>();
            for (Node node: nodes) {        /*Add all nodes created to the temporary list and clear is so moving function can add new nodes when moved*/
                currentNodes.add(node);
            }

            this.nodes = new ArrayList<>(); /*Clear list*/
            for(Node node: currentNodes){
                //log.info(node.getMoves().toString());
                tryAllMoves(node);          /*Try all possible moves*/
            }

        }while(!this.isSolved);
        System.out.println("Solved with: " + this.solutionFound);
    }

    /**
     * Function for shuffle
     * @param wantMultiple - true enables algorithm to check if every number changed
     *                     their original place
     *                     - false makes it shuffle once
     */
    void shuffle(boolean wantMultiple) {
        Random random = new Random();
        boolean allChanged;
        do {
            allChanged = true;
            for (int i = puzzleArea.length - 1; i > 0; i--) {
                for (int j = puzzleArea[i].length - 1; j > 0; j--) {
                    int m = random.nextInt(i + 1);
                    int n = random.nextInt(j + 1);

                    int temp = puzzleArea[i][j];
                    puzzleArea[i][j] = puzzleArea[m][n];
                    puzzleArea[m][n] = temp;
                }
            }
            int counter = 0;
            for (int i = 0; i < puzzleSize; i++) {
                for (int j = 0; j < puzzleSize; j++) {
                    if (puzzleArea[j][i] == counter)
                        allChanged = false;
                    counter++;
                }
            }
            if(!wantMultiple)
                allChanged = true;

            for(int i=0; i< puzzleSize; i++)
                for(int j=0;j <puzzleSize; j++){
                    if(puzzleArea[j][i] == 0){
                        blankField.x = j;
                        blankField.y = i;
                    }
                }
        }while(!allChanged);
    }

    /**
     * Function for moving in all directions
     * @param node - to use node's setting like board setup
     */
    void tryAllMoves(Node node){
        moveDown(node);
        moveUp(node);
        moveLeft(node);
        moveRight(node);
    }

    /**
     * Simple CLI for puzzle view
     */
    void showPuzzle(){
        for(int i=0; i<puzzleSize; i++){
            for(int j=0; j<puzzleSize; j++){
                System.out.print(puzzleArea[j][i]+"\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Function for setting all settings from current node to fifteenPuzzle
     * @param node - current node
     */
    void setFifteenPuzzleFromNode(Node node){
        setPuzzleArea(node.getCurrentPuzzleState());
        setBlankField(node.getBlank());
    }

    /**
     * Function for setting puzzleArea from passed one
     * @param puzzle - passed puzzleArea
     */
    void setPuzzleArea(int [][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            puzzleArea[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
        }
    }

    /**
     * Function for setting blankField setting from passed one
     * @param blankField - passed blankField
     */
    void setBlankField(FieldCoordinates blankField) {
        this.blankField = blankField;
    }

    /**
     * Function for checking if puzzle is solved
     * @return true - if it's solved
     *         false - if it isn't
     */
    boolean checkIfSolved(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<puzzleSize; i++){
            for(int j=0; j<puzzleSize;j++){
                sb.append(puzzleArea[j][i]);
            }
        }
        if(Objects.equals(this.solution1, sb.toString()) || Objects.equals(this.solution2, sb.toString())){
            this.isSolved = true;
            return true;
        }
        else
            return false;
    }

    /**
     * Function for finding setting 2 ideal solution depending on the puzzleSize
     */
    void getSolution(){
        StringBuilder solution1 = new StringBuilder();
        StringBuilder solution2 = new StringBuilder();
        int counter1 = 0;
        int counter2 = 1;
        for(int i =0; i<puzzleSize;i++) {
            for (int j = 0; j < puzzleSize; j++) {
                solution1.append(Integer.toString(counter1));
                if(counter2 != puzzleSize*puzzleSize)
                    solution2.append(Integer.toString(counter2));
                counter1++;
                counter2++;
            }
        }
        solution2.append(0);
        this.solution1 = solution1.toString();
        this.solution2 = solution2.toString();
    }


    int getBlankFieldX() {
        return blankField.x;
    }
    int getBlankFieldY() {
        return blankField.y;
    }
    int getPuzzleValueOnPlace(int x, int y){
        return puzzleArea[x][y];
    }

    /**
     * Function for logger configuration
     */
    void configureLogger(){
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:\\Users\\Kaszuba\\Desktop\\LogFile.log");
            this.log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            log.setUseParentHandlers(false);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
