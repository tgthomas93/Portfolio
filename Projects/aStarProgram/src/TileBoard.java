
import java.util.ArrayList;
import java.util.Random;

public class TileBoard {

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_BLUE = "\u001B[34m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_PURPLE = "\u001B[35m";

    private Node[][] board;
    private Random rand = new Random();

    public TileBoard() {
        board = new Node[15][15];
        generateBoard();
    }

    public Node getNode(int row, int col) {
        return board[row][col];
    }

    private void generateBoard() {
        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                int type = rand.nextInt(10);
                if (type == 1) {
                    board[row][column] = new Node(row, column, 1);
                } else {
                    board[row][column] = new Node(row, column, 0);
                }

            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                if (board[row][column].getType() == 1) {
                    System.out.print(ANSI_RED + "[]" + ANSI_RESET);
                } else {
                    System.out.print(ANSI_GREEN + "[]" + ANSI_RESET);

                }
            }
            System.out.println();
        }
    }

    public void printBoard(Node n, Node g) {
        for (int row = 0; row < 15; row++) {
            for (int column = 0; column < 15; column++) {
                if (board[row][column].equals(n)) {
                    System.out.print(ANSI_PURPLE + "[]" + ANSI_RESET);
                } else if (board[row][column].equals(g)) {
                    System.out.print(ANSI_BLUE + "[]" + ANSI_RESET);
                } //Use X's to represent Nodes that are unpassable
                else if (board[row][column].getType() == 1) {
                    System.out.print(ANSI_RED + "[]" + ANSI_RESET);
                } else {
                    //Empty brackets represent Nodes that are passable
                    System.out.print(ANSI_GREEN + "[]" + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public boolean isGoal(Node c, Node g) {
        return c.equals(g);
    }

    //Calculate the cost to move to a Node from the start
    public void getPathCost(Node start, Node current) {
        int min = Math.min(Math.abs(start.getCol() - current.getCol()), Math.abs(start.getRow() - current.getRow()));
        int max = Math.max(Math.abs(start.getCol() - current.getCol()), Math.abs(start.getRow() - current.getRow()));
        int pathCost = (14 * min) + (10 * (max - min));
        current.setG(pathCost);

    }

    //Calculate the distance from current Node to goal Node
    public void getHeuristic(Node current, Node goal) {
        int heuristic = Math.abs(current.getRow() - goal.getRow()) + Math.abs(current.getCol() - goal.getCol());
        //Set the heuristic of the current Node
        current.setH(heuristic * 10);
    }

    //Find all adjacent Nodes to the current
    public ArrayList<Node> getNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int upNeighbor = n.getRow() - 1;
        int downNeighbor = n.getRow() + 1;
        int leftNeighbor = n.getCol() - 1;
        int rightNeighbor = n.getCol() + 1;

        //Check for the top neighbor
        if (upNeighbor >= 0) {
            //Check to see if the neighbor is a passable Node
            if (board[upNeighbor][n.getCol()].getType() == 0) {
                neighbors.add(board[upNeighbor][n.getCol()]);
            }
        }
        //Check for upper right neighbor
        if (upNeighbor >= 0 && rightNeighbor < 15) {
            //Check to see if the neighbor is a passable Node
            if (board[upNeighbor][rightNeighbor].getType() == 0) {
                neighbors.add(board[upNeighbor][rightNeighbor]);
            }
        }
        //Check for right neighbor
        if (rightNeighbor < 15) {
            //Check to see if the neighbor is a passable Node
            if (board[n.getRow()][rightNeighbor].getType() == 0) {
                neighbors.add(board[n.getRow()][rightNeighbor]);
            }
        }
        //Check for bottom right neighbor
        if (downNeighbor < 15 && rightNeighbor < 15) {
            //Check to see if the neighbor is a passable Node
            if (board[downNeighbor][rightNeighbor].getType() == 0) {
                neighbors.add(board[downNeighbor][rightNeighbor]);
            }
        }
        //Check for bottom neighbor
        if (downNeighbor < 15) {
            //Check to see if the neighbor is a passable Node
            if (board[downNeighbor][n.getCol()].getType() == 0) {
                neighbors.add(board[downNeighbor][n.getCol()]);
            }
        }
        //Check for bottom left neighbor
        if (downNeighbor < 15 && leftNeighbor >= 0) {
            //Check to see if the neighbor is a passable Node
            if (board[downNeighbor][leftNeighbor].getType() == 0) {
                neighbors.add(board[downNeighbor][leftNeighbor]);
            }
        }
        //Check for left neighbor
        if (leftNeighbor >= 0) {
            //Check to see if the neighbor is a passable Node
            if (board[n.getRow()][leftNeighbor].getType() == 0) {
                neighbors.add(board[n.getRow()][leftNeighbor]);
            }
        }
        //Check for upper left neighbor
        if (leftNeighbor >= 0 && upNeighbor >= 0) {
            //Check to see if the neighbor is a passable Node
            if (board[upNeighbor][leftNeighbor].getType() == 0) {
                neighbors.add(board[upNeighbor][leftNeighbor]);
            }
        }
        return neighbors;
    }
}
