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
    private Queue<Node> nodes;
    private Queue<Node> priorityNodes;
    private Boolean isSolved;
    private final Logger log = Logger.getLogger(getClass().getName());
    private String solution1;           //Possible solutions: ex. 012345678
    private String solution2;           // and 123456780
    private String solutionFound;       //Solution found (step by step answer)
    private int numberOfSteps;
    private HashSet<String> hashSet;
    private String solverMethod;
    private String typeOfDistance;


    /**
     * FifteenPuzzle constructor with initialization of puzzle         in case of size = 4 [ 0 1 2 3 ]
     * where 0 is always the first field and its a "blank" field                           [ 4 5 6 7 ] etc...
     * @param puzzleSize - puzzle size (puzzle is always a square)
     */
    public FifteenPuzzle(int puzzleSize, String solverMethod, String typeOfDistance) {
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

        this.nodes = new LinkedList<>();
        this.priorityNodes = new PriorityQueue<>();
        this.isSolved = false;
        configureLogger();
        getSolution();
        hashSet = new HashSet<>();
        this.solverMethod = solverMethod;
        this.typeOfDistance = typeOfDistance;
    }

    /**
     * Function for moving blank place right
     * @return true - if move is possible
     *         false - if move is impossible
     */
    private boolean moveRight(Node node){
        setFifteenPuzzleFromNode(node);                 /*Setting a previous node settings*/

        if(this.isSolved)
            return false;
        if(check()){
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if(this.blankField.x >= this.puzzleSize - 1)    /*Check if can be moved*/
            return false;

        int tmp = puzzleArea[blankField.x + 1][blankField.y];   /*Move*/
        puzzleArea[blankField.x + 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x + 1;


        if(checkIfStateVisited(getCurrentState()))      /*Check if already visted this node before adding it*/
            return false;
        Node nextNode = new Node(node.getMoves().toString()+" Right", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves, typeOfDistance); /*Save the new setting to new node*/
        if(Objects.equals(this.solverMethod,"BFS"))
            this.nodes.add(nextNode);
        if(Objects.equals(this.solverMethod,"AStar"))
            this.priorityNodes.add(nextNode);
        addCurrentStateToVisited();                     /*Add this state as Visited*/
        return true;
    }

    /**
     * Function for moving blank place left
     * @return true - if move is possible
     *         false - if move is impossible
     */
    private boolean moveLeft(Node node){
        setFifteenPuzzleFromNode(node);

        if(this.isSolved)
            return false;
        if(check()){
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if(this.blankField.x <= 0)
            return false;

        int tmp = puzzleArea[blankField.x - 1][blankField.y];
        puzzleArea[blankField.x - 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x - 1;
        if(checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString()+" Left", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves,typeOfDistance);
        if(Objects.equals(this.solverMethod,"BFS"))
            this.nodes.add(nextNode);
        if(Objects.equals(this.solverMethod,"AStar"))
            this.priorityNodes.add(nextNode);
        addCurrentStateToVisited();
        return true;
    }

    /**
     * Function for moving blank place up
     * @return true - if move is possible
     *         false - if move is impossible
     */
    private boolean moveUp(Node node){
        setFifteenPuzzleFromNode(node);

        if(this.isSolved)
            return false;
        if(check()){
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if(this.blankField.y <= 0)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y - 1];
        puzzleArea[blankField.x][blankField.y - 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y - 1;
        if(checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString()+" Up", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves,typeOfDistance);
        if(Objects.equals(this.solverMethod,"BFS"))
            this.nodes.add(nextNode);
        if(Objects.equals(this.solverMethod,"AStar"))
            this.priorityNodes.add(nextNode);
        addCurrentStateToVisited();
        return true;
    }

    /**
     * Function for moving blank place down
     * @return true - if move is possible
     *         false - if move is impossible
     */
    private boolean moveDown(Node node){
        setFifteenPuzzleFromNode(node);

        if(this.isSolved)
            return false;
        if(check()){
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if(this.blankField.y >= this.puzzleSize - 1)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y + 1];
        puzzleArea[blankField.x][blankField.y + 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y +1;
        if(checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString()+" Down", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves,typeOfDistance);
        if(Objects.equals(this.solverMethod,"BFS"))
            this.nodes.add(nextNode);
        if(Objects.equals(this.solverMethod,"AStar"))
            this.priorityNodes.add(nextNode);
        addCurrentStateToVisited();
        return true;
    }

    /**
     * Function for solving puzzle using BFS
     */
    void BFS(){
        nodes.clear();
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField, 0, typeOfDistance); /*Starting node the same as generated*/
        nodes.add(startingNode);        //Add starting node
        String s = getCurrentState();
        hashSet.clear();
        hashSet.add(s);                 //Add starting node to the list of visited

        while(!nodes.isEmpty()){
            Node current = nodes.poll();    //Get first element of the queue and delete it after
            if(this.isSolved){
                break;
            }
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){  //For out of memory problems
                break;
            }
            tryAllMoves(current);
        }
        System.out.println("====== BFS SOLUTION ======");
        System.out.println("Solution: " + this.solutionFound);
        System.out.println("Number of moves: " + this.numberOfSteps);

        //This is previous method for BFS search, i was trying to find out which is faster
        /*do{
            Queue<Node> currentNodes = new LinkedList<>();

            for (Node node: nodes) {        //Add all nodes created to the temporary list and clear is so "tryAllMoves" function can add new nodes when moved
                currentNodes.add(node);
            }

            this.nodes = new LinkedList<>(); //Clear list
            for(Node node: currentNodes){
                tryAllMoves(node);          //Try all possible moves
            }
        hashSet.addAll(tempHashSet);
        tempHashSet = new HashSet<>();
        }while(!this.isSolved);
        System.out.println("Solved with: " + this.solutionFound);*/
    }

    /**
     * Function for solving puzzle using AStar
     */
     void AStar() {
        priorityNodes.clear();
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField,0, typeOfDistance); /*Starting node the same as generated*/
        priorityNodes.add(startingNode);
        String s = getCurrentState();
        hashSet.clear();
        hashSet.add(s);

        while (!priorityNodes.isEmpty()) {
            Node current = priorityNodes.poll(); //Get first element of the queue and delete it after
            if (this.isSolved) {
                break;
            }
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()) {
                break;
            }
            tryAllMoves(current);
        }
        System.out.println("====== A-STAR SOLUTION with "+typeOfDistance + " distance ======");
        System.out.println("Solution: " + this.solutionFound);
        System.out.println("Number of moves: " + this.numberOfSteps);
    }
    /**
     * Function for shuffle
     * @param wantMultiple - true enables algorithm to check if every number changed
     *                     their original place
     *                     - false makes it shuffle once
     */
    void shuffle(boolean wantMultiple) {
        Random random = new Random();
        //boolean allChanged;
        do {
            //allChanged = true;
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
                        //allChanged = false;
                    counter++;
                }
            }
            if(!wantMultiple)
                //allChanged = true;

            for(int i=0; i< puzzleSize; i++)
                for(int j=0;j <puzzleSize; j++){
                    if(puzzleArea[j][i] == 0){
                        blankField.x = j;
                        blankField.y = i;
                    }
                }
        }while(!isSolvable());
    }

    /**
     * Function for moving in all directions
     * @param node - to use node's setting like board setup
     */
    private void tryAllMoves(Node node){

        moveUp(node);
        moveDown(node);
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
    private void setFifteenPuzzleFromNode(Node node){
        setPuzzleArea(node.getCurrentPuzzleState());
        setBlankField(node.getBlank());
    }

    /**
     * Function for setting puzzleArea from passed one
     * @param puzzle - passed puzzleArea
     */
    private void setPuzzleArea(int [][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            puzzleArea[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
        }
    }

    /**
     * Function for setting blankField setting from passed one
     * @param blankField - passed blankField
     */
    private void setBlankField(FieldCoordinates blankField) {
        this.blankField = blankField;
    }

    /**
     * Function for checking if puzzle is solved
     * @return true - if it's solved
     *         false - if it isn't
     */
    private boolean checkIfSolved(String currentState){
        if(Objects.equals(this.solution1, currentState) || Objects.equals(this.solution2, currentState)){
            this.isSolved = true;
            return true;
        }
        else
            return false;
    }

    /**
     * Alternative function for checking if puzzle is solved ( experimental)
     * @return true - if it's solved
     *         false - if it isn't
     */
    private boolean check(){
        int counter = 1;
        for(int i=0; i<puzzleSize; i++){
            for(int j=0; j<puzzleSize; j++){
                if(counter>15){
                    counter = 0;
                }
                if(puzzleArea[j][i] != counter){
                    return false;
                }
                counter++;
            }
        }
        this.isSolved = true;
        return true;
    }




    /**
     * Function for finding setting 2 ideal solution depending on the puzzleSize
     */
    private void getSolution(){
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

    private static long merge(int[] array, int[] left, int[] right)
    {
        int i = 0, j = 0, count = 0;
        while (i < left.length || j < right.length)
        {
            if (i == left.length)
            {
                array[i+j] = right[j];
                j++;
            }
            else if (j == right.length)
            {
                array[i+j] = left[i];
                i++;
            }
            else if (left[i] <= right[j])
            {
                array[i+j] = left[i];
                i++;
            }
            else
            {
                array[i+j] = right[j];
                count += left.length-i;
                j++;
            }
        }
        return count;
    }

    private static long invCount(int[] array)
    {
        if (array.length < 2)
            return 0;

        int m = (array.length + 1) / 2;
        int left[] = Arrays.copyOfRange(array, 0, m);
        int right[] = Arrays.copyOfRange(array, m, array.length);

        return invCount(left) + invCount(right) + merge(array, left, right);
    }

    private boolean isSolvable()
    {
        long inversionCounter = 0;
        for (int i=0;i<puzzleSize;i++)
            inversionCounter = inversionCounter + invCount(puzzleArea[i]);

        return inversionCounter % 2 == 0;
    }


    /**
     * Function for logger configuration
     */
    private void configureLogger(){
        FileHandler fh;
        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("C:\\Users\\Kaszuba\\Desktop\\LogFile.log");
            this.log.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            log.setUseParentHandlers(false);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private void addCurrentStateToVisited(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<puzzleSize; i++){
            for (int j=0; j<puzzleSize; j++){
                sb.append(puzzleArea[j][i]);
            }
        }
        hashSet.add(sb.toString());
    }
    private Boolean checkIfStateVisited(String state){

        return hashSet.contains(state);
    }

    private String getCurrentState(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<puzzleSize; i++){
            for (int j=0; j<puzzleSize; j++){
                sb.append(puzzleArea[j][i]);
            }
        }
        return sb.toString();
    }

    void setupPuzzleArea(int[] area){
        int counter = 0;
        for(int i=0; i<puzzleSize; i++){
            for(int j=0; j<puzzleSize; j++){
                puzzleArea[j][i] = area[counter];
                if(area[counter] == 0){
                    blankField.x = j;
                    blankField.y = i;
                }
                counter++;
            }
        }
    }
}
