//*******************************************************************
//
//   File: Warnsdorff.java
//
//*******************************************************************

import java.io.IOException;

public class Warnsdorff {
    // declare data arrays
    public static int[][] chess;
    public static final int N = 8;

    // list of all moves... eg, xDiff[0], yDiff[0] shifts right (+1) col, down (+2) rows
    public static final int[] xDiff = {1, -1, 2, 2, 1, -1, -2, -2};
    public static final int[] yDiff = {2, 2, 1, -1, -2, -2, 1, -1};
    public static int[] score;
    
    public static void main(String[] args) throws IOException {
        if (args.length == 1) ChessBoard.SLEEP = Integer.parseInt(args[0]);
        else ChessBoard.SLEEP = 200;

        // declare chess board and move scoring arrays
        chess = new int[N][N];
        score = new int[N];

        // run sims until we find a valid knight's tour!
        int count = 0;
        while (!runSim()){
            System.out.println("Round: " + count++);
        }
        
        System.out.println("got one!");
    }

    // run a knight's tour simulation
    public static boolean runSim() throws IOException {   
        // init arrays and graphics
        ChessBoard.fillChess();
        ChessBoard.fillScores();
            
        // find random initial pos to start the tour
        int knightRow = (int) Math.round(Math.random() * (N - 1));
        int knightCol = (int) Math.round(Math.random() * (N - 1));
        chess[knightRow][knightCol] = 1;

        ChessBoard.initBoard(knightRow, knightCol);

        // true at each index where we have a valid move
        boolean[] moves = allMoves(knightRow, knightCol);

        // continue the tour so long as at least one index of moves array is true
        while(canMove(moves)) {
            
            // select direction where we will have (nonzero) minimum number of possible next moves
            int direction = findMin();
            
            knightCol += xDiff[direction];
            knightRow += yDiff[direction];
            chess[knightRow][knightCol] = 1;

            // update graphics
            ChessBoard.updateBoard(knightRow, knightCol);

            // update moves array from new location
            moves = allMoves(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }

    // find all possible moves from chess board index (kR, kC)
    public static boolean[] allMoves(int kR, int kC)
    {   
        // reinit scores array for this set of moves
        ChessBoard.fillScores();

        // find all possible moves from index (kR, kC) in chess array
        boolean[] moves = new boolean[8];
        for (int i = 0; i < 8; i++) {
            boolean xCheck = kC + xDiff[i] < N && kC + xDiff[i] >= 0;
            boolean yCheck = kR + yDiff[i] < N && kR + yDiff[i] >= 0;

            // if this move falls in range of our array indexes...
            if (xCheck && yCheck) {

                // && if this move does not revisit an index...
                moves[i] = (chess[kR + yDiff[i]][kC + xDiff[i]] == 0);
                if (moves[i]) {

                    // find the number of possible moves FROM this index
                    score[i] = countMoves(kR + yDiff[i], kC + xDiff[i]);
                }
            } 
            else moves[i] = false;
        }
        return moves;
    }
    
    // count the number of moves a knight can take from (row, col) on the chess board
    public static int countMoves(int row, int col)
    {   
        // same ideas as in allMoves(...) - repurposed for counting
        int count = 0;
        for (int i = 0; i < 8; i++) {
            boolean xCheck = col + xDiff[i] < N && col + xDiff[i] >= 0;
            boolean yCheck = row + yDiff[i] < N && row + yDiff[i] >= 0;

            if (xCheck && yCheck)
            {
                if (chess[row + yDiff[i]][col + xDiff[i]] == 0);
                    count++;
            } 
        }
        return count;
    }

    // confirm the knight still has at least one valid move
    public static boolean canMove(boolean[] moves)
    {   
        for (int i = 0; i < moves.length; i++) {
            if (moves[i]) {
                return true;
            }
        }
        return false;
    }

    // find the knight's next move such that they will have the fewest possible moves on the next round
    public static int findMin()
    {   
        int minIndex = Integer.MAX_VALUE;
        int minVal = Integer.MAX_VALUE; 
        for (int i = 0; i < 8; i++) {
            if (score[i] < minVal) {
                minIndex = i;
                minVal = score[i];                
            } 
        }
        return minIndex;
    }

    // check for a complete knight's tour
    public static boolean allOnes() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (chess[i][j] == 0) return false;
            }
        }
        return true;
    }    
}   