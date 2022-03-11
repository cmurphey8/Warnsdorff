//*******************************************************************
//
//   File: TaskMan.java
//
//   Author: CS112      Email:  
//
//   Class: NBody 
// 
//   Time spent on this problem: 
//   --------------------
//   
//      This program 
//
//*******************************************************************

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class ChessBoard {

    // init Panel
    static final int WIDTH = 500;
    static final int HEIGHT = 500;
    
    static DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
    static Graphics2D g = panel.getGraphics();

    // enable double buffering
    static BufferedImage offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    static Graphics2D osg = offscreen.createGraphics();

    // chess constants
    static final int EDGE = 10;
    static final int SIZE = 60;
    static int PAIRS;

    // knight image
    static BufferedImage knight;
    static final int kH = 50;
    static final int kW = 35; 

    // track last move to update from
    static int kRowOld;
    static int kColOld;

    public static int SLEEP;
    
    // YOU DO: complete method to read in song attributes
    public static void initBoard(int kR, int kC) throws IOException 
    {
        PAIRS = Warnsdorff.N / 2;
        knight = ImageIO.read(new File("knight2.png"));
        
        // set background
        osg.setColor(Color.DARK_GRAY);
        osg.fillRect(0, 0, WIDTH, HEIGHT);

        drawGrid(EDGE, EDGE, PAIRS, SIZE);
        g.drawImage(offscreen, 0, 0, null);  
        drawKnight(kR, kC);
        
        kRowOld = kR;
        kColOld = kC;
    }

    // YOU DO: complete method to read in song attributes
    public static void updateBoard(int kR, int kC)
    {
        osg.setColor(Color.GREEN);
        // drawGrid(EDGE, EDGE, PAIRS, SIZE);
        drawKnight(kR, kC);
        osg.drawLine(scaleIndex(kColOld) + SIZE / 2, scaleIndex(kRowOld) + SIZE / 2, scaleIndex(kC) + SIZE / 2, scaleIndex(kR) + SIZE / 2);
        kRowOld = kR;
        kColOld = kC;
        
        g.drawImage(offscreen, 0, 0, null);   
        panel.sleep(SLEEP);    
    }

    // YOU DO: complete method to read in song attributes
    public static void drawGrid(int x, int y, int numPairs, int SIZE) {
        for (int i = 0; i < 2 * numPairs; i+=2) {
            drawRow(x, y + SIZE * i, Color.WHITE, Color.GRAY);
            drawRow(x, y + SIZE * (i + 1), Color.GRAY, Color.WHITE);
        }
    }

    // YOU DO: complete method to read in song attributes
    public static void drawRow(int x, int y, Color first, Color second) {
        for (int i = 0; i < 2 * PAIRS; i+=2) {
            osg.setColor(first);
            osg.fillRect(x + SIZE * i, y, SIZE, SIZE);
            osg.setColor(second);
            osg.fillRect(x + SIZE * (i + 1), y, SIZE, SIZE);
        }
    }

    // YOU DO: complete method to read in song attributes
    public static void drawKnight(int row, int col) {
        osg.drawImage(knight, scaleKnight(col, kW), scaleKnight(row, kH), null);
        
    }

    // YOU DO: complete method to read in song attributes
    public static int scaleKnight(int index, int dimSize) {
        return scaleIndex(index) + (int) ((SIZE - dimSize) / 2.0);
    }

    // YOU DO: complete method to read in song attributes
    public static int scaleIndex(int index)
    {
        return index * SIZE + EDGE;
    }


    // reinit chess array with 0s
    public static void fillChess() {
        for (int i = 0; i < Warnsdorff.N; i++) {
            for (int j = 0; j < Warnsdorff.N; j++) {
                Warnsdorff.chess[i][j] = 0;
            }
        }
    }

    // reinit score array with max integer values
    public static void fillScores() {
        for (int i = 0; i < Warnsdorff.N; i++) {
            Warnsdorff.score[i] = Integer.MAX_VALUE;
        }
    }
}   
    