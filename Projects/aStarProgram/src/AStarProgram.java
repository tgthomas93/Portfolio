
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AStarProgram {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Create a priorityQueue using a Comparator for Node objects
        PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                if (a.getF() > b.getF()) {
                    //Returns 1 if a has higher priority
                    return 1;
                } else if (a.getF() < b.getF()) {
                    //Returns -1 if b has higher priority
                    return -1;
                } else {
                    //Return 0 if both F values are the same, giving them equal priority
                    return 0;
                }
            }
        });
        ArrayList<Node> path = new ArrayList<>();
        Hashtable<Coordinates, Node> closedList = new Hashtable<>();
        TileBoard board = new TileBoard();
        Node start, current, goal;
        String userChoice;

        System.out.println("Would you like to start the pathfinding program? Y/N:  ");
        userChoice = input.next().toUpperCase();

        while (userChoice.equals("Y") || userChoice.equals("YES")) {
            System.out.println("Green represents open Nodes, Purple is the current Node, Blue is the goal Node.");
            board.printBoard();

            //Set the current Node to the user's starting node
            current = validateNode(board, 1);

            //Keep track of the beginning Node
            start = current;
            //Set the goal Node to the user's chosen position
            goal = validateNode(board, 2);
            board.printBoard(current, goal);
            //Add the start Node to the openList
            openList.add(current);

            boolean foundPath = false;
            while (!openList.isEmpty()) {
                Coordinates key = new Coordinates();
                //Current Node is set to the first object in the openList
                current = openList.poll();
                //Create a unique key for each Node based on row and col
                key.setValues(current.getCol(), current.getRow());
                //Add the current Node to the closedList
                closedList.put(key, current);
                //Calculate g and h for current
                board.getHeuristic(current, goal);
                board.getPathCost(start, current);

                if (board.isGoal(current, goal)) {
                    //Solution has been found
                    foundPath = true;
                    System.out.println("Path found!");
                    break;
                }

                //Get the current Node's children
                ArrayList<Node> children = board.getNeighbors(current);

                //Loop through each child
                for (Node c : children) {
                    if (closedList.contains(c)) {
                        continue;
                    }
                    //Calculate f, g, and h values for each child
                    board.getHeuristic(c, goal);
                    board.getPathCost(start, c);
                    c.setF();
                    //Set the child's parent to the current Node
                    c.setParent(current);
                    //If the child is already in the open list and its g cost is cheaper, recalculate the c's f,g,h and set its parent to the current Node
                    if (openList.contains(c)) {
                        if (c.getG() < current.getG()) {
                            board.getHeuristic(c, goal);
                            board.getPathCost(start, c);
                            c.setF();
                            c.setParent(current);
                            continue;
                        }
                    }
                    //Add the child to the openList
                    openList.add(c);
                }
            }
            //If a path was found, generate it
            if (foundPath) {
                Node root = goal;
                //Loop from the goal Node to the start Node
                while (!root.equals(start)) {
                    path.add(root);
                    root = root.getParent();
                }
                //Print all of the Nodes in the path
                System.out.println("The path is: ");
                for (int i = path.size() - 1; i >= 0; i--) {
                    System.out.println(path.get(i));
                }
                //Simulate the Node moving to the goal
                System.out.println("\nMoving Node....");
                for (int i = path.size() - 1; i >= 0; i--) {
                    Node pathNode = path.get(i);
                    System.out.println();
                    board.printBoard(pathNode, goal);
                    System.out.println();
                }

            } else {
                System.out.println("No path found.");
            }
            //Prompt user to either quit or continue
            System.out.println("Would you like to enter more Nodes? Y/N: ");
            userChoice = input.next().toUpperCase();
        }
        System.out.println("Goodbye!");
    }

//Validate that user input is within the specified range
    private static boolean validateInput(int x, int y) throws Exception {
        if ((x < 0 || x > 14) || (y < 0 || y > 14)) {
            throw new Exception("Value(s) out of range");
        }
        return true;
    }

    //Used to validate userInput and ensure that the Node is not unpassable
    private static Node validateNode(TileBoard board, int type) {
        Scanner input = new Scanner(System.in);
        Node n;
        int row, col;
        String goalOrStart = "";
        //Use an arbitrary number to set a String for formatting purposes
        if (type == 1) {
            goalOrStart += "Start";
        } else {
            goalOrStart += "Goal";
        }
//Loop until the user enters valid input, i.e. not a String
        while (true) {
            try {
                System.out.println("Enter the row for" + " " + goalOrStart + " " + "Node between 0-14: ");
                row = input.nextInt();
                System.out.println("Enter the column for" + " " + goalOrStart + " " + "Node between 0-14: ");
                col = input.nextInt();
                //Use a nested while loop to ensure that user cannot enter value outside of the range
                while (true) {
                    try {
                        //Exit the loop if the user's input is in a valid range
                        if (validateInput(row, col)) {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        input.nextLine();
                        System.out.println("Enter the row for" + " " + goalOrStart + " " + "Node between 0-14: ");
                        row = input.nextInt();
                        System.out.println("Enter the column for" + " " + goalOrStart + " " + "Node between 0-14: ");
                        col = input.nextInt();
                    }
                }
                break;
            } catch (InputMismatchException error) {
                System.out.println("Wrong input entered.");
                input.nextLine();
            }
        }
//Create a new Node object with the user's information
        n = board.getNode(row, col);

//Loop until the user selects a Node that is open
        while (n.getType() != 0) {
            //Print a message to let the user know that the Node is unpassable
            System.out.println("That Node is set as unpassable.");
            try {
                System.out.println("Enter the row for" + " " + goalOrStart + " " + "Node between 0-14: ");
                row = input.nextInt();
                System.out.println("Enter the column for" + " " + goalOrStart + " " + "Node between 0-14: ");
                col = input.nextInt();
                while (true) {
                    try {
                        if (validateInput(row, col)) {
                            n = board.getNode(row, col);
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        input.nextLine();
                        System.out.println("Enter the row for" + " " + goalOrStart + " " + "Node between 0-14: ");
                        row = input.nextInt();
                        System.out.println("Enter the column for" + " " + goalOrStart + " " + "Node between 0-14: ");
                        col = input.nextInt();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input entered.");
                input.nextLine();
            }
        }

        return n;
    }
}
