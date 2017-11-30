package com.madbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Kaszuba on 06.11.2017.
 */
public class FifteenPuzzle {


    private int[][] puzzleArea;
    private int puzzleSize;
    private FieldCoordinates blankField;
    private Queue<Node> nodes;
    private Queue<Node> priorityNodes;
    private Stack<Node> stackNodes;
    private Boolean isSolved;
    private final Logger log = Logger.getLogger(getClass().getName());
    private String solution;           // and 123456780
    private String solutionFound;       //Solution found (step by step answer)
    private int numberOfSteps;
    private HashSet<String> hashSet;
    private String solverMethod;
    private String typeOfDistance;
    private int processedStates = 0; // contains all states, can be duplicated
    private int maxRecursionDepth = 0;
    private long elapsedTime;
    private long start;
    /**
     * FifteenPuzzle constructor with initialization of puzzle         in case of size = 4 [ 0 1 2 3 ]
     * where 0 is always the first field and its a "blank" field                           [ 4 5 6 7 ] etc...
     *
     * @param puzzleSize - puzzle size (puzzle is always a square)
     */
    public FifteenPuzzle(int puzzleSize, String solverMethod, String typeOfDistance) {
        this.puzzleSize = puzzleSize;
        puzzleArea = new int[this.puzzleSize][this.puzzleSize];
        int counter = 0;
        for (int i = 0; i < this.puzzleSize; i++) {
            for (int j = 0; j < this.puzzleSize; j++) {
                this.puzzleArea[j][i] = counter;
                counter++;
            }
        }
        this.blankField = new FieldCoordinates(0, 0);

        this.nodes = new LinkedList<>();
        this.priorityNodes = new PriorityQueue<>();
        this.stackNodes = new Stack<>();
        this.isSolved = false;
        getSolution();
        hashSet = new HashSet<>();
        this.solverMethod = solverMethod;
        this.typeOfDistance = typeOfDistance;
    }

    /**
     * Function for moving blank place right
     *
     * @return true - if move is possible
     * false - if move is impossible
     */
    private boolean moveRight(Node node) {
        setFifteenPuzzleFromNode(node);                 /*Setting a previous node settings*/

        if (this.isSolved)
            return false;
        if (check()) {
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if (this.blankField.x >= this.puzzleSize - 1)    /*Check if can be moved*/
            return false;

        int tmp = puzzleArea[blankField.x + 1][blankField.y];   /*Move*/
        puzzleArea[blankField.x + 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x + 1;

        processedStates++;

        if (checkIfStateVisited(getCurrentState()))      /*Check if already visted this node before adding it*/
            return false;
        Node nextNode = new Node(node.getMoves().toString() + "R", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves, typeOfDistance); /*Save the new setting to new node*/
        if (Objects.equals(this.solverMethod, "bfs"))
            this.nodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "astr"))
            this.priorityNodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "dfs"))
            this.stackNodes.push(nextNode);
        addCurrentStateToVisited();                     /*Add this state as Visited*/
        return true;
    }

    /**
     * Function for moving blank place left
     *
     * @return true - if move is possible
     * false - if move is impossible
     */
    private boolean moveLeft(Node node) {
        setFifteenPuzzleFromNode(node);

        if (this.isSolved)
            return false;
        if (check()) {
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if (this.blankField.x <= 0)
            return false;

        int tmp = puzzleArea[blankField.x - 1][blankField.y];
        puzzleArea[blankField.x - 1][blankField.y] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.x = tmpBlankField.x - 1;

        processedStates++;

        if (checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString() + "L", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves, typeOfDistance);
        if (Objects.equals(this.solverMethod, "bfs"))
            this.nodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "astr"))
            this.priorityNodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "dfs"))
            this.stackNodes.push(nextNode);

        addCurrentStateToVisited();

        return true;
    }

    /**
     * Function for moving blank place up
     *
     * @return true - if move is possible
     * false - if move is impossible
     */
    private boolean moveUp(Node node) {
        setFifteenPuzzleFromNode(node);

        if (this.isSolved)
            return false;
        if (check()) {
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if (this.blankField.y <= 0)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y - 1];
        puzzleArea[blankField.x][blankField.y - 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y - 1;

        processedStates++;


        if (checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString() + "U", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves, typeOfDistance);
        if (Objects.equals(this.solverMethod, "bfs"))
            this.nodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "astr"))
            this.priorityNodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "dfs"))
            this.stackNodes.push(nextNode);

        addCurrentStateToVisited();

        return true;
    }

    /**
     * Function for moving blank place down
     *
     * @return true - if move is possible
     * false - if move is impossible
     */
    private boolean moveDown(Node node) {
        setFifteenPuzzleFromNode(node);

        if (this.isSolved)
            return false;
        if (check()) {
            this.solutionFound = node.getMoves().toString();
            this.numberOfSteps = node.numberOfMoves;
            return false;
        }

        if (this.blankField.y >= this.puzzleSize - 1)
            return false;

        int tmp = puzzleArea[blankField.x][blankField.y + 1];
        puzzleArea[blankField.x][blankField.y + 1] = 0;
        puzzleArea[blankField.x][blankField.y] = tmp;

        FieldCoordinates tmpBlankField = new FieldCoordinates(blankField.x, blankField.y);
        tmpBlankField.y = tmpBlankField.y + 1;

        processedStates++;


        if (checkIfStateVisited(getCurrentState()))
            return false;
        Node nextNode = new Node(node.getMoves().toString() + "D", this.puzzleSize, this.puzzleArea, tmpBlankField, node.numberOfMoves, typeOfDistance);
        if (Objects.equals(this.solverMethod, "bfs"))
            this.nodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "astr"))
            this.priorityNodes.add(nextNode);
        if (Objects.equals(this.solverMethod, "dfs"))
            this.stackNodes.push(nextNode);
        addCurrentStateToVisited();

        return true;
    }

    void DFS(int depth) {
        start = System.nanoTime();
        stackNodes.clear();
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField, 0, typeOfDistance); /*Starting node the same as generated*/
        stackNodes.push(startingNode);
        String s = getCurrentState();
        hashSet.clear();
        hashSet.add(s);

        Node current;
        while (!stackNodes.empty()) {
            do {
                current = stackNodes.pop();
            } while (current.numberOfMoves > depth);

            if (this.isSolved)
                break;
            tryAllMoves(current);
            if(maxRecursionDepth < current.numberOfMoves)
            {
                maxRecursionDepth = current.numberOfMoves;
            }
        }
        elapsedTime = System.nanoTime() - start;
//        System.out.println("====== DFS SOLUTION ======");
//        if (isSolved) {
//            System.out.println("Solution: " + this.solutionFound);
//            System.out.println("Number of moves: " + this.numberOfSteps);
//            System.out.println("Number of visited states: " + this.processedStates);
//            System.out.println("Number of processed states: " + this.hashSet.size());
//            System.out.println("Number of max recursive depth: " + maxRecursionDepth);
//
//
//        } else {
//            this.numberOfSteps = -1;
//            System.out.println("Number of moves: " + this.numberOfSteps);
//            System.out.println("Solution not found");
//        }
        if (!isSolved) {
            this.numberOfSteps = -1;
        }
    }

    /**
     * Function for solving puzzle using BFS
     */
    void BFS() {
        start = System.nanoTime();
        nodes.clear();
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField, 0, typeOfDistance); /*Starting node the same as generated*/
        nodes.add(startingNode);        //Add starting node
        String s = getCurrentState();
        hashSet.clear();
        hashSet.add(s);                 //Add starting node to the list of visited

        while (!nodes.isEmpty()) {
            Node current = nodes.poll();    //Get first element of the queue and delete it after
            if (this.isSolved) {
                break;
            }
            neighborhoodSearch(typeOfDistance,current);
            //tryAllMoves(current);
            if(maxRecursionDepth < current.numberOfMoves)
            {
                maxRecursionDepth = current.numberOfMoves;
            }
        }
        elapsedTime = System.nanoTime() - start;
//        System.out.println("====== BFS SOLUTION ======");
//        System.out.println("Solution: " + this.solutionFound);
//        System.out.println("Number of moves: " + this.numberOfSteps);
//        System.out.println("Number of visited states: " + this.processedStates);
//        System.out.println("Number of processed states: " + this.hashSet.size());
//        System.out.println("Number of max recursive depth: " + maxRecursionDepth);
        if (!isSolved) {
            this.numberOfSteps = -1;
        }
    }

    /**
     * Function for solving puzzle using AStar
     */
    void AStar() {
        start = System.nanoTime();
        priorityNodes.clear();
        Node startingNode = new Node("start", this.puzzleSize, this.puzzleArea, this.blankField, 0, typeOfDistance); /*Starting node the same as generated*/
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
            if(maxRecursionDepth < current.numberOfMoves)
            {
                maxRecursionDepth = current.numberOfMoves;
            }
        }

//        System.out.println("====== A-STAR SOLUTION with " + typeOfDistance + " distance ======");
//        System.out.println("Solution: " + this.solutionFound);
//        System.out.println("Number of moves: " + this.numberOfSteps);
//        System.out.println("Number of visited states: " + this.processedStates);
//        System.out.println("Number of processed states: " + this.hashSet.size());
//        System.out.println("Number of max recursive depth: " + maxRecursionDepth);

        if (!isSolved) {
            this.numberOfSteps = -1;
        }
        elapsedTime = System.nanoTime() - start;
    }

    /**
     * Function for moving in all directions
     *
     * @param node - to use node's setting like board setup
     */
    private void tryAllMoves(Node node) {
        moveDown(node);
        moveRight(node);
        moveUp(node);
        moveLeft(node);
    }

    /**
     * Simple CLI for puzzle view
     */
    void showPuzzle() {
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                System.out.print(puzzleArea[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Function for setting all settings from current node to fifteenPuzzle
     *
     * @param node - current node
     */
    private void setFifteenPuzzleFromNode(Node node) {
        setPuzzleArea(node.getCurrentPuzzleState());
        setBlankField(node.getBlank());
    }

    /**
     * Function for setting puzzleArea from passed one
     *
     * @param puzzle - passed puzzleArea
     */
    private void setPuzzleArea(int[][] puzzle) {
        for (int i = 0; i < puzzle.length; i++) {
            puzzleArea[i] = Arrays.copyOf(puzzle[i], puzzle[i].length);
        }
    }

    /**
     * Function for setting blankField setting from passed one
     *
     * @param blankField - passed blankField
     */
    private void setBlankField(FieldCoordinates blankField) {
        this.blankField = blankField;
    }

    /**
     * Function for checking if puzzle is solved
     *
     * @return true - if it's solved
     * false - if it isn't
     */
    private boolean checkIfSolved(String currentState) {
        if (Objects.equals(this.solution, currentState)) {
            this.isSolved = true;
            return true;
        } else
            return false;
    }

    /**
     * Alternative function for checking if puzzle is solved ( experimental)
     *
     * @return true - if it's solved
     * false - if it isn't
     */
    private boolean check() {
        int counter = 1;
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                if (counter > 15) {
                    counter = 0;
                }
                if (puzzleArea[j][i] != counter) {
                    return false;
                }
                counter++;
            }
        }
        this.isSolved = true;
        return true;
    }

    private void RDUL(Node node) {
        moveRight(node);
        moveDown(node);
        moveUp(node);
        moveLeft(node);
    }
    private void RDLU(Node node) {
        moveRight(node);
        moveDown(node);
        moveLeft(node);
        moveUp(node);
    }
    private void DRUL(Node node) {
        moveDown(node);
        moveRight(node);
        moveUp(node);
        moveLeft(node);
    }
    private void DRLU(Node node) {
        moveDown(node);
        moveRight(node);
        moveLeft(node);
        moveUp(node);
    }
    private void LUDR(Node node) {
        moveLeft(node);
        moveUp(node);
        moveDown(node);
        moveRight(node);
    }
    private void LURD(Node node) {
        moveLeft(node);
        moveUp(node);
        moveRight(node);
        moveDown(node);
    }
    private void ULDR(Node node) {

        moveUp(node);
        moveLeft(node);
        moveDown(node);
        moveRight(node);
    }
    private void ULRD(Node node) {
        moveUp(node);
        moveLeft(node);
        moveRight(node);
        moveDown(node);
    }








    void neighborhoodSearch(String typeOfDistance, Node node){

        switch (typeOfDistance) {
            case "RDUL":
                RDUL(node);
                break;
            case "RDLU":
                RDLU(node);
                break;
            case "DRUL":
                DRUL(node);
                break;
            case "DRLU":
                DRLU(node);
                break;
            case "LUDR":
                LUDR(node);
                break;
            case "LURD":
                LURD(node);
                break;
            case "ULDR":
                ULDR(node);
                break;
            case "ULRD":
                ULRD(node);
                break;

        }
    }

    /**
     * Function for finding setting 2 ideal solution depending on the puzzleSize
     */
    private void getSolution() {
        StringBuilder solution = new StringBuilder();
        int counter2 = 1;
        for (int i = 1; i < puzzleSize*puzzleSize; i++) {

                if (counter2 != puzzleSize * puzzleSize)
                    solution.append(Integer.toString(counter2));
                counter2++;

        }
        solution.append(0);
        this.solution = solution.toString();
    }

    private void addCurrentStateToVisited() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                sb.append(puzzleArea[j][i]);
            }
        }
        hashSet.add(sb.toString());
    }



    private Boolean checkIfStateVisited(String state) {

        return hashSet.contains(state);
    }

    private String getCurrentState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                sb.append(puzzleArea[j][i]);
            }
        }
        return sb.toString();
    }

    public void saveAdditionalInformationToFile(String filename) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(Integer.toString(this.numberOfSteps) + "\n");
        fw.write(Integer.toString(processedStates) + "\n");
        fw.write(Integer.toString(this.hashSet.size()) + "\n");
        fw.write(Integer.toString(maxRecursionDepth) + "\n");
        float tmp = (float)elapsedTime/1000000;
        fw.write(String.format("%.3f",tmp ) + "\n");
        fw.close();
    }
    public void saveSolutionToFile(String filename) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filename);
            try {
                fileWriter.write(this.numberOfSteps + "\n" + this.solutionFound);
            } catch (NullPointerException e) {
                fileWriter.write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void readFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        this.puzzleSize = scanner.nextInt();
        this.puzzleSize = scanner.nextInt();

        for (int i = 0; i<this.puzzleSize; ++i){
            for (int j = 0; j<this.puzzleSize ; ++j){
                int s = scanner.nextInt();
                this.puzzleArea[j][i] = s;
                if(s == 0)
                {
                    blankField.x = j;
                    blankField.y = i;
                }

            }
        }
        scanner.close();

    }
    void setupPuzzleArea(int[] area) {
        int counter = 0;
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                puzzleArea[j][i] = area[counter];
                if (area[counter] == 0) {
                    blankField.x = j;
                    blankField.y = i;
                }
                counter++;
            }
        }
    }
}
