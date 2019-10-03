package com.example.tappyspaceship01;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="DINO-RAINBOWS";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------

    // represent the TOP LEFT CORNER OF THE GRAPHIC

    // ----------------------------
    // ## GAME STATS
    // ----------------------------
//adding rainbows
Bitmap playerImage;
int playerXPositon;

int playerYPosition;
Rect playerHitbox;
Bitmap item1Image;
    Bitmap item2Image;
    Bitmap item3Image;

    Item candy;
    Item candy2;
    Item Rainbow;
    Item Rainbow2;
    Item Poop;
    Item Poop2;



int lives = 3;
    int SQUARE_WIDTH = 100;

    public GameEngine(Context context, int w, int h) {
        super(context);

        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;

        // creating array of item1
       ArrayList<Item>  candies = new ArrayList<Item>();
        ArrayList<Item> Rainbows= new ArrayList<Item>();
        ArrayList<Item> Poops= new ArrayList<Item>();

       int SQUARE_WIDTH = 100;
       this.item1Image = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.candy32);
        this.item2Image = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.rainbow32);
        this.item3Image = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.poop32);
        // Draw the candies





        this.printScreenInfo();
        this.playerImage = BitmapFactory.decodeResource(this.getContext().getResources(),R.drawable.dino32);
        this.playerXPositon = 1300;
        this.playerYPosition  = 540;
        // 1. draw a hitbox
        this.playerHitbox = new Rect(1300,
                540,
                1300+playerImage.getWidth(),
                540+playerImage.getHeight()
        );


    }



    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }


   // private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
   // }
    //private void spawnEnemyShips() {
       // Random random = new Random();

        //@TODO: Place the enemies in a random location

   // }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }
    public void moveCandy(Item candy) {
        // @TODO:  Move the square
        // 1. calculate distance between bullet and square
        double a = (playerXPositon - candy.xPosition);
        double b = (playerYPosition - candy.yPosition);
        double distance = Math.sqrt((a*a) + (b*b)); // DISTANCE FORMULA = SQRT OF (2X)+(2Y) [ AS THE FIGURE IS SQUARE]

        // 2. calculate the "rate" to move
        double xn = (a / distance);
        double yn = (b / distance);

        // 3. move the bullet
        candy.xPosition = candy.xPosition + (int)(xn * 5);
        candy.yPosition = candy.yPosition + (int)(yn * 5);
    }

    //move rainbow
    public void moveRainbow(Item rainbow) {
        // @TODO:  Move the square
        // 1. calculate distance between bullet and square
        double a = (playerXPositon - rainbow.xPosition);
        double b = (playerYPosition - rainbow.yPosition);
        double distance = Math.sqrt((a*a) + (b*b)); // DISTANCE FORMULA = SQRT OF (2X)+(2Y) [ AS THE FIGURE IS SQUARE]

        // 2. calculate the "rate" to move
        double xn = (a / distance);
        double yn = (b / distance);

        // 3. move the bullet
        rainbow.xPosition = rainbow.xPosition + (int)(xn * 10);
        rainbow.yPosition = rainbow.yPosition + (int)(yn * 10);
    }
    // move poop
    public void movePoop(Item poop) {
        // @TODO:  Move the square
        // 1. calculate distance between bullet and square
        double a = (playerXPositon - poop.xPosition);
        double b = (playerYPosition - poop.yPosition);
        double distance = Math.sqrt((a*a) + (b*b)); // DISTANCE FORMULA = SQRT OF (2X)+(2Y) [ AS THE FIGURE IS SQUARE]

        // 2. calculate the "rate" to move
        double xn = (a / distance);
        double yn = (b / distance);

        // 3. move the bullet
        poop.xPosition = poop.xPosition + (int)(xn * 5);
        poop.yPosition = poop.yPosition + (int)(yn * 5);
    }
    public void spawnCandy() {

        if (this.candy.size() < 10) { // NUMBER OF BULLETS
            Item b;
            if (this.candy.isEmpty()) {
                b = new Item(getContext(), 100, 600, SQUARE_WIDTH);
            } else {
                // prev bullet
                Item prevCandy = this.candy.get(this.candy.size() - 1);
                int newCandyX = prevCandy.xPosition + 120;
                b = new Item(getContext(), newCandyX, 600, SQUARE_WIDTH);
            }
            this.candy.add(b);
        }

    }
    public void spawnRainbow() {

        if (this.Rainbow.size() < 10) { // NUMBER OF BULLETS
            Item b;
            if (this.Rainbow.isEmpty()) {
                b = new Item(getContext(), 200, 800, SQUARE_WIDTH);
            } else {
                // prev bullet
                Item prevRainbow = this.Rainbow.get(this.candy.size() - 1);
                int newRainbowX = prevRainbow.xPosition + 120;
                b = new Item(getContext(), newRainbowX, 600, SQUARE_WIDTH);
            }
            this.Rainbow.add(b);
        }

    }public void spawnPoop() {

        if (this.Poop.size() < 10) { // NUMBER OF BULLETS
            Item b;
            if (this.Poop.isEmpty()) {
                b = new Item(getContext(), 100, 600, SQUARE_WIDTH);
            } else {
                // prev bullet
                Item prevPoop = this.Poop.get(this.Poop.size() - 1);
                int newPoopX = prevPoop.xPosition + 120;
                b = new Item(getContext(), newPoopX, 600, SQUARE_WIDTH);
            }
            this.Poop.add(b);
        }

    }

    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    public void updatePositions() {

    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(120);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------


    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {

        }
        else if (userAction == MotionEvent.ACTION_UP) {

        }

        return true;
    }
}
