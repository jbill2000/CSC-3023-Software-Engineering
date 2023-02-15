import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.animation.*;
import java.util.*;
import java.io.*;

import javax.swing.text.StyledEditorKit.BoldAction;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.*;
import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.util.*;

//Lab7 Canvas Class
public class ContraptionZackCanvas extends Canvas {
   // Variable Creation
   ContraptionZackLevel level;
   GraphicsContext gc;
    int colorcount=0;
   String theLevel;
   boolean nextLevel = false;
   boolean prevLevel = false;
   boolean alternate = false;
   boolean gridDrawn = false;
   String fileLSname = ""; // file name for when saving and loading

   int gameState = 1; // this keeps track of whether the pop up menu is up or not
                      // 1 is no menu, 2 is menu, 3 is load pop up menu, 4 is save
                      // pop up menu

   int menuCursor = 1; // this keeps track of which of the four menu boxes are selected

   boolean LEFT = false, RIGHT = false, UP = false, DOWN = false; // tracks the movement

   public class AnimationHandler extends AnimationTimer { // handler that constantly updates

      //this method checks if the player is out of bounds or collided with
      //a protrusion/jukebox
      public boolean outOfBoundsCheck(double xpos, double ypos) {
         switch (level.getLevelNum()) {
            case 1:
               if (ypos > 750 || ypos < 200 || xpos > 600 
               || xpos < 250 || jukeboxCollision(xpos, ypos, 50, 50))
                  return true;
               break;

            case 2:
               if (ypos > 850 && xpos <= 750 || ypos <= 300 && xpos < 450 
               || ypos <= 300 && xpos > 600 || xpos >= 750 || xpos <= 200) {
                  if (!protrusionCollision(xpos, ypos, 1, 1)) {
                     return true;
                  }
               }
               break;
            case 3:
               if (ypos > 750 || ypos < 200 || xpos > 750 || xpos < 200) {
                  if (!protrusionCollision(xpos, ypos, 50, 50)) {
                     return true;
                  }
               }
               break;
            case 4:
               if (ypos > 700 || ypos < 150 || xpos > 800 || xpos < 150) {
                  return true;
               }
               
               break;

         }

         return false;
      }

      //this method checks if the player collided with an arrow tile
      public void arrowCheck(double xpos, double ypos) {
         for (Arrow arrow : level.arrows) {
            if (arrow.collision(xpos, ypos, 50, 50)) {
               nextLevel = true;
               y -= 1;
               
               if (arrow.type.equals("d") || arrow.type.equals("r")) {
                  theLevel = level.getNextFileName(1);
               } else if (arrow.type.equals("u") && y < 750 || arrow.type.equals("l")) {
                  theLevel = level.getNextFileName(0);
               } else if (arrow.type.equals("u") && y > 800) {
                  theLevel = "NONE";

               }
              
               if (!theLevel.equals("NONE")) {
                  level = new ContraptionZackLevel(theLevel);
                  x = level.getPlayerstartx();
                  y = level.getPlayerstarty();
                  y -= 1;
               }

            }
         }
      }

      //this method checks if the player has collided with
      //a spring, square button, circle button, or tool, and takes 
      //the appropriate action

      public void buttonCheck(double xpos, double ypos) {
         int groupNum = 0;
         boolean button = false;

         arrowCheck(xpos, ypos);

         for (SquareButton sb : level.squares) {
            // Prevents constant collision checking and thus null errors
            if (x >= sb.getX() && x <= sb.getX() + 90 && y >= sb.getY() && y <= sb.getY() + 90) {
               if (sb.collision(xpos, ypos, 50, 50)) {
                  
                  if (!sb.currentlyPressed & !sb.pressed) {
                     button = true; // then the player is touching a button
                     sb.currentlyPressed = true; //the button is pressed
                     sb.pressed = true;
                     groupNum = sb.groupAssocation; //this tells us which spikes need to shift
                  }

               } else if (sb.currentlyPressed) {
                  sb.currentlyPressed = false;
               }
            }
         }

         for (Spring s : level.springs) {
            // Prevents constant collision checking and thus null errors
            if (x >= s.getX() && x <= s.getX() + 90 && y >= s.getY() && y <= s.getY() + 90) {
               if (s.collision(xpos, ypos, 50, 50)) {
                  
                  if (!s.currentlyPressed & !s.pressed) {
                     button = true; // then the player is touching a button
                     s.currentlyPressed = true;
                     s.pressed = true;
                     groupNum = s.groupAssocation;

                  }

               }
               
            }

         }

         for (CircleButton cb : level.circles) {

            //if the timer tracking circle buttons passes 60 seconds
            if (System.currentTimeMillis() - cb.time > 60000 && cb.pressed) {
               cb.time = System.currentTimeMillis();
               groupNum = cb.groupAssocation;
               cb.pressed = false;
               cb.currentlyPressed = false;

               //the spikes are pushed down
               for (Spike spike : level.spikes) {
                  if (spike.groupAssocation == groupNum) {
                     spike.swapSpike();
                     cb.time = System.currentTimeMillis();

                  }
               }

            }

            // Prevents constant collision checking and thus null errors
            if (x >= cb.getX() && x <= cb.getX() + 90 && y >= cb.getY() && y <= cb.getY() + 90) {
               if (cb.collision(xpos, ypos, 50, 50)) {
              
                  if (cb.currentlyPressed == false) {
                     button = true; // then the player is touching a button
                     cb.currentlyPressed = true;
                     cb.pressed = true;
                     groupNum = cb.groupAssocation; //tells us which spikes to swap
                     cb.time = System.currentTimeMillis(); //starts the timing of the circle button
                  }

               }
            }
         }

         for (Tool t : level.tools) {
            // Prevents constant collision checking and thus null errors
            if (x >= t.getX() && x <= t.getX() + 90 && y >= t.getY() && y <= t.getY() + 90 && t.taken!=true) {

               if (t.collision(xpos, ypos, 50, 50)) {
                  t.taken = true; //means that the tool is taken
               }
              
            }
            
         }

         if (button) { // finds the spikes associated with the button and modifies them

            for (Spike spike : level.spikes) {
               if (spike.groupAssocation == groupNum) {
                  spike.swapSpike();
               }
            }

         }

      }

      // returns true if the character would hit a wall
      public boolean wallCollision(double xpos, double ypos, int height, int width) {
         for (Walls wall : level.walls) {
            if(x >= wall.getX() && x <= wall.getX() + 100 ||  x <= wall.getX() + 75  ||  x <= wall.getX() + 50 || x <= wall.getX() + 25 || x <= wall.getX() + 200  && y >= wall.getY() && y <= wall.getY() + 50 || y <= wall.getY() + 100 || y <= wall.getY() + 25 || y <= wall.getY() + 75 || y <= wall.getY() + 140)
            {
            if (wall.collision(xpos, ypos, height, width)) {
               return true;
            }

            if (level.getLevelNum() == 2 ||level.getLevelNum() == 3 ) {
               for (Protrusion p : level.protrudes) {
                  
                  if(x>= p.getX() && x<= p.getX()+200 && y>= p.getY() && y<= p.getY() + 250)
                  {
                     if (p.rightWall.collision(xpos, ypos, height, width)
                           || p.leftWall.collision(xpos, ypos, height, width)) {
                        return true;
                     }
                  }
               }
            }
            if (level.getLevelNum() == 4) {
               for (Protrusion p : level.protrudes) {
                  
                  if(x>= p.getX() && x<= p.getX()+10 && y>= p.getY() && y<= p.getY() + 10)
                  {
                     if (p.rightWall.collision(xpos, ypos, height, width)
                           || p.leftWall.collision(xpos, ypos, height, width)) {
                        return true;
                     }
                  }
               }
            }

         }
         }
         return false;
      }

     

      // returns true if the character would hit one of the invisible walls to the
      // right and left of the protrusion
      public boolean protrusionCollision(double xpos, double ypos, int height, int width) {
         for (Protrusion protrusion : level.protrudes) {
         if (level.getLevelNum() == 2 ||level.getLevelNum() == 3 ){
           if(x>= protrusion.getX() && x<= protrusion.getX()+200 && y>= protrusion.getY() && y<= protrusion.getY() + 250){
            if (protrusion.collision(xpos, ypos, height, width))
               return true;
            }
         }
         if (level.getLevelNum() == 4 ){
           if(x>= protrusion.getX() && x<= protrusion.getX()+10 && y>= protrusion.getY() && y<= protrusion.getY() + 10){
            if (protrusion.collision(xpos, ypos, height, width))
               return true;
            }
         }
        }
         return false;
      }

      //checks collision with jukebox
      public boolean jukeboxCollision(double xpos, double ypos, int height, int width) {
         for (Jukebox j : level.jukebox) {
            if (j.collision(xpos, ypos, height, width))
               return true;
         }
         return false;
      }

      // returns true if the character hits a spike
      public boolean spikeCollision(double xpos, double ypos, int height, int width) {
         for (Spike spike : level.spikes) {
            if (spike.collision(xpos, ypos, height, width) & spike.spikeUp) {
               
               return true;
            }
         }
         return false;
      }

      //checks colission with water
      public boolean waterCollision(double xpos, double ypos, int height, int width) {
         if (level.getLevelNum() == 4) {
            return level.water.collision(xpos, ypos, height, width);
         }
         return false;
      }

      //checks collision with spring
      public boolean springCollision(double xpos, double ypos, int height, int width) {
         for (Spring s : level.springs) {
            if (s.collision(xpos, ypos, height, width) & s.pressed) {
               
               return true;
            }
         }
         return false;
      }

      // Doors Collision
      public boolean doorCollision(double xpos, double ypos, int height, int width) {
         for (Doors door : level.doors) {
            if (door.collision(xpos, ypos, height, width) & !door.open) {
               return true;
            }
         }
         return false;
      }

      // returns true if tile in the direction of the movement is walkable
      public boolean collisionCheck(double xpos, double ypos, int height, int width) {
         
         boolean isWalkable = true;
         if (wallCollision(xpos, ypos, height, width) || spikeCollision(xpos, ypos, height, width)
               || doorCollision(xpos, ypos, height, width) || outOfBoundsCheck(xpos, ypos)
               || springCollision(xpos, ypos, height, width) || waterCollision(xpos, ypos, height, width)) {
            isWalkable = false;
         }
         return isWalkable;
      }

      public void handle(long currentTimeInNanoSeconds) {
         
         draw(gc);
         buttonCheck(x, y);

         // If moving left
         if (LEFT) {
            /// Movement
            if (collisionCheck(x - 1, y, 50, 50)) {
               x -= 1;
               buttonCheck(x, y);
            }
         }
         // If moving right
         if (RIGHT) {

            if (collisionCheck(x + 1, y, 50, 50)) {
               x += 1;
               buttonCheck(x, y);
            }
         }
         // If moving up
         if (UP) {
            if (collisionCheck(x, y - 1, 50, 50)) {
               y -= 1;
               buttonCheck(x, y);
            }

         }

         // If down
         if (DOWN) {
            if (collisionCheck(x, y + 1, 50, 50)) {
               y += 1;
               buttonCheck(x, y);
            }
         }

         
      }

   }

   double x;
   double y;
   AnimationHandler timer;

   int drawtime = 0;

   // Constructor
   public ContraptionZackCanvas(String firstLevel) {

      if (!firstLevel.equals("title")) {
         theLevel = firstLevel;
         level = new ContraptionZackLevel(theLevel);
         
         setWidth(10 * 100);
         setHeight(10 * 100);
         x = level.getPlayerstartx();
         y = level.getPlayerstarty();
         // Returns the current level for reference later
         gc = getGraphicsContext2D();
         draw(gc);
         // KeyListenerCode
         setOnKeyPressed(new KeyPressed());
         setOnKeyReleased(new KeyReleased());
         timecheck=false;
         timer = new AnimationHandler(); // creates a timer
         timer.start();
      }

   }

   // Returns current level for use later
   public String getLevelName() {
      return theLevel;
   }

   public boolean getNextLevel() {
      return nextLevel;
   }

   // Draw method that I modified from Dr.Mood's Existing code.
   double time;//System.currentTimeMillis();
   double currenttime;
   boolean timecheck;
   public void draw(GraphicsContext gc) {
      if(timecheck==false)
      {
         timecheck=true;
         time=System.currentTimeMillis();
      }
      // Clearing the title

      
      gc.clearRect(0, 0, 1000, 1000);
      gc.setFill(Color.BLACK);
      gc.fillRect(0, 0, 1000, 1000);

      // The following switch statement will draw the grid for the respective level
      // and "center" it based on the coordinates in the translate method.

      switch (level.getLevelNum()) {

         // Jukebox level
         case 1: {

            //draws the ground tiles
            gc.translate(150, 100);
            for (int i = 0; i < level.getColumns(); i++) {
               for (int j = 0; j < level.getRows(); j++) {

                  Tile k = level.getData(i, j);

                  k.draw(gc, i * 100, j * 100);

               }
            }

            gc.translate(-150, -100);
            gc.setFill(Color.BLACK);
            gc.fillRect(660, 210, 80, 580);

            //draws the arrows
            if (level.arrowsEmpty() == false) {
               for (int r = 0; r < level.arrowsSize(); r++) {
                  Arrow a = level.getArrows(r);
                  a.draw(gc, a.getX(), a.getY(), a.getName());
               }

            }

            //draws any protrusions
            if (level.protrudesEmpty() == false) {
               for (int t = 0; t < level.protrudesSize(); t++) {
                  Protrusion p = level.getProtrusions(t);
                  p.draw(gc, p.getX(), p.getY(), p.getWidth(), p.getHeight());
               }

            }

            //draws any jukeboxes
            if (level.jukeboxEmpty() == false) {
               for (int t = 0; t < level.jukeboxSize(); t++) {

                  Jukebox j = level.getJukebox(t);
                  j.draw(gc, j.getX(), j.getY(), j.getWidth(), j.getHeight());
               }

            }

            break;
         }
         // First obstacle level
         case 2: {

            gc.translate(0, 0);

            //draws the ground tiles
            for (int i = 0; i < level.getColumns(); i++) {
               for (int j = 0; j < level.getRows(); j++) {

                  Tile k = level.getData(i, j);

                  k.draw(gc, i * 100, j * 100);

                  gc.setFill(Color.BLACK);
                  int x1 = i * 100;
                  int y1 = j * 100;
                  gc.strokeLine(x1, y1, x1 + 100, y1);
                  gc.strokeLine(x1, y1, x1, y1 + 100);
                  gc.strokeLine(x1 + 100, y1, x1 + 100, y1 + 100);
                  gc.strokeLine(x1, y1 + 100, x1 + 100, y1 + 100);

               }
            }

            gc.translate(0, 0);

            //draws protrusions in the level
            if (level.protrudesEmpty() == false) {
               for (int t = 0; t < level.protrudesSize(); t++) {
                  Protrusion p = level.getProtrusions(t);
                  p.draw(gc, p.getX(), p.getY() + 100, p.getWidth(), p.getHeight());
               }

            }

            // Will "Draw" spikes
            if (level.spikesempty() == false) {
               for (int k = 0; k < level.spikesize(); k++) {
                  Spike s = level.getSpike(k);
                  // Draws spikes
                  s.draw(gc, s.getX(), s.getY());

               }

            }

            // Same as spike draw but with Squares!
            if (level.squareEmpty() == false) {
               for (int l = 0; l < level.squaresSize(); l++) {
                  SquareButton sb = level.getSquareButtons(l);
                  sb.draw(gc, sb.getX(), sb.getY());

               }
            }

            //draws any walls in the level
            if (level.wallsEmpty() == false) {
               for (int v = 0; v < level.wallsSize(); v++) {
                  Walls w = level.getWalls(v);
                  w.draw(gc, w.getX(), w.getY());
               }

            }

            //draws any springs in the level
            if (level.springsEmpty() == false) {
               for (int b = 0; b < level.springsSize(); b++) {
                  Spring s = level.getSprings(b);
                  s.draw(gc, s.getX(), s.getY());
                  if (s.left == true && s.leftcount == 0) {
                     x = 345;
                     y = 725;
                     s.leftcount++;
                  }
                  if (s.right == true && s.rightcount == 0) {
                     x = 605;
                     y = 725;
                     s.rightcount++;
                  }

               }

            }

            //draws the arrows in the level
            if (level.arrowsEmpty() == false) {
               for (int r = 0; r < level.arrowsSize(); r++) {
                  Arrow a = level.getArrows(r);
                  a.draw(gc, a.getX(), a.getY(), a.getName());
               }

            }

            break;
         }
         // Second obstacle level
         case 3: {

            gc.translate(0, 0);

            //draws the ground tiles
            for (int i = 0; i < level.getColumns(); i++) {
               for (int j = 0; j < level.getRows(); j++) {

                  Tile k = level.getData(i, j);

                  k.draw(gc, i * 100, j * 100);

               }
            }

            gc.translate(0, 0);

            //draws any protrusions
            if (level.protrudesEmpty() == false) {
               for (int t = 0; t < level.protrudesSize(); t++) {
                  Protrusion p = level.getProtrusions(t);
                  p.draw(gc, p.getX(), p.getY(), p.getWidth(), p.getHeight());
               }

            }

            // Will "Draw" spikes
            if (level.spikesempty() == false) {
               for (int k = 0; k < level.spikesize(); k++) {
                  Spike s = level.getSpike(k);
                  // Draws spikes
                  s.draw(gc, s.getX(), s.getY());

               }

            }

            // Same as spike draw but with Squares!
            if (level.squareEmpty() == false) {
               for (int l = 0; l < level.squaresSize(); l++) {
                  SquareButton sb = level.getSquareButtons(l);
                  sb.draw(gc, sb.getX(), sb.getY());

               }
            }

            //draws any walls
            if (level.wallsEmpty() == false) {
               for (int v = 0; v < level.wallsSize(); v++) {
                  Walls w = level.getWalls(v);
                  w.draw(gc, w.getX(), w.getY());
               }

            }

            //draws any springs
            if (level.springsEmpty() == false) {
               for (int b = 0; b < level.springsSize(); b++) {
                  Spring s = level.getSprings(b);
                  s.draw(gc, s.getX(), s.getY());

               }

            }

            //draws any arrows
            if (level.arrowsEmpty() == false) {
               for (int r = 0; r < level.arrowsSize(); r++) {
                  Arrow a = level.getArrows(r);
                  a.draw(gc, a.getX(), a.getY(), a.getName());
               }

            }

            //draws any circle buttons
            if (level.circleEmpty() == false) {
               for (int z = 0; z < level.circlesSize(); z++) {
                  CircleButton cb = level.getCircleButtons(z);
                  cb.draw(gc, cb.getX(), cb.getY());
               }

            }
          
            //draws any tools
               for (int u = 0; u < level.toolsSize(); u++) {
                  Tool t = level.getTools(u);
                  if(t.taken==false)
                  {
                     t.draw(gc, t.getImage(), t.getX(), t.getY());
                  }
               }

            
               //draws any doors
            if (level.doorsempty() == false) {
               for (int d = 0; d < level.doorsize(); d++) {
                  Doors door = level.getDoors(d);
                  // Draws spikes
                  door.draw(gc, door.getX(), door.getY());

               }

            }
            break;
         }
         // Third obstacle level.
         case 4: {
            gc.translate(50, 50);

            //draws the tiles on the floor
            for (int i = 0; i < level.getColumns(); i++) {
               for (int j = 0; j < level.getRows(); j++) {
                  
                  Tile k = level.getData(i, j);

                  k.draw(gc, i * 100, j * 100);

               }
            }

            gc.translate(-50, -50);
            gc.translate(0, 0);

            //draws any protrusions
            if (level.protrudesEmpty() == false) {
               for (int t = 0; t < level.protrudesSize(); t++) {
                  Protrusion p = level.getProtrusions(t);
                  p.draw(gc, p.getX(), p.getY(), p.getWidth(), p.getHeight());
               }

            }

            //draws any arrows
            if (level.arrowsEmpty() == false) {
               for (int r = 0; r < level.arrowsSize(); r++) {
                  Arrow a = level.getArrows(r);
                  a.draw(gc, a.getX(), a.getY(), a.getName());
               }

            }

            //draws any walls
            if (level.wallsEmpty() == false) {
               for (int v = 0; v < level.wallsSize(); v++) {
                  Walls w = level.getWalls(v);
                  w.draw(gc, w.getX(), w.getY());

               }

               gc.setStroke(Color.RED);
               gc.strokeLine(175, 462.5, 225, 462.5);
               gc.strokeLine(375, 462.5, 425, 462.5);
               gc.setStroke(Color.BLACK);

            }

            //draws any springs
            if (level.springsEmpty() == false) {
               for (int b = 0; b < level.springsSize(); b++) {
                  Spring s = level.getSprings(b);
                  s.draw(gc, s.getX(), s.getY());
                  if (s.left == true && s.leftcount == 0) {
                     x = 400;
                     y = 575;
                     s.leftcount++;
                  }
                  if (s.right == true && s.rightcount == 0) {
                     x = 575;
                     y = 325;
                     s.rightcount++;
                  }

               }

            }

            //drwas any square buttons
            if (level.squareEmpty() == false) {
               for (int l = 0; l < level.squaresSize(); l++) {
                  SquareButton sb = level.getSquareButtons(l);
                  sb.draw(gc, sb.getX(), sb.getY());

               }
            }

            //drwas any circle buttons
            if (level.circleEmpty() == false) {
               for (int z = 0; z < level.circlesSize(); z++) {
                  CircleButton cb = level.getCircleButtons(z);
                  cb.draw(gc, cb.getX(), cb.getY());
               }

            }

            //draws any spikes
            if (level.spikesempty() == false) {
               for (int k = 0; k < level.spikesize(); k++) {
                  Spike s = level.getSpike(k);
                  // Draws spikes
                  s.draw(gc, s.getX(), s.getY());

               }

            }

            // create / draw water
            level.water.drawWater(gc);

            break;
         }

      }
     
   
    //this is the code that periodically swaps the color of the player
    if(System.currentTimeMillis() - time > 1300)
     {
         Color col2 = new Color(0, .4, .9,1);
         gc.setFill(col2);
         colorcount++;
         if(colorcount == 200)
         {
            colorcount=0;
            
         time=System.currentTimeMillis();
         }
                                    
     }
    else
     {
      gc.setFill(Color.BLUE);
     }
     time++;
     
     //draws the player
      gc.fillRect(x, y, 50, 50);
      
      //if the game is in the menu
      if (gameState == 2) {

         //creates the 5 menu boxes
         gc.setFill(Color.GREY);
         gc.fillRect(0, 0, 100, 250);

         gc.setFill(Color.WHITE);

         if (menuCursor == 1) {
            gc.setFill(Color.BLUE);
         }

         gc.fillRect(3, 3, 92, 42);
         gc.setFill(Color.WHITE);

         if (menuCursor == 2) {
            gc.setFill(Color.BLUE);
         }

         gc.fillRect(3, 53, 92, 42);
         gc.setFill(Color.WHITE);

         if (menuCursor == 3) {
            gc.setFill(Color.BLUE);
         }
         gc.fillRect(3, 103, 92, 42);
         gc.setFill(Color.WHITE);

         if (menuCursor == 4) {
            gc.setFill(Color.BLUE);
         }
         gc.fillRect(3, 153, 92, 42);
         gc.setFill(Color.WHITE);
         if (menuCursor == 5) {
            gc.setFill(Color.BLUE);
         }
         gc.fillRect(3, 203, 92, 42);

         //adds text to the menu boxes
         gc.setFill(Color.BLACK);
         gc.fillText("Save", 10, 25);
         gc.fillText("Load", 10, 75);
         gc.fillText("Restart Area", 10, 125);
         gc.fillText("Restart Game", 10, 175);
         gc.fillText("Quit", 10, 225);

      }
      if (gameState == 3) { //creates the loading pop up box

         gc.setFill(Color.GREY);
         gc.fillRect(0, 0, 200, 100);

         String s = "Enter name of file to load from: " + "\n" + fileLSname;

         gc.setFill(Color.BLACK);
         gc.fillText(s, 10, 50);

      }
      if (gameState == 4) { //creates the saving pop up box

         gc.setFill(Color.GREY);
         gc.fillRect(0, 0, 200, 100);

         String s = "Enter name of file to save to: " + "\n" + fileLSname;

         gc.setFill(Color.BLACK);
         gc.fillText(s, 10, 50);

      }

   }

   // KeyListener
   public class KeyPressed implements EventHandler<KeyEvent> {
      public void handle(KeyEvent event) {

         draw(gc);

         //if it is saving or loading, add that 
         //letter to the name of file
         if (gameState == 3 || gameState == 4) { 
            if (event.getCode().isLetterKey()) {
               fileLSname += event.getCode();
            }

         }

         // If Left Arrow Key Is pressed
         if (event.getCode() == KeyCode.LEFT) {
            if (gameState == 1) {
               LEFT = true;
            }

         }
         // If UP Arrow Key Is pressed
         if (event.getCode() == KeyCode.UP) {
            
            //moves player
            if (gameState == 1) {
               UP = true;
            }
            //moves the menu
            if (gameState == 2) {
               menuCursor -= 1;
               if (menuCursor < 1) {
                  menuCursor = 5;
               }
            }
            
         }
         // If Down Arrow Key Is pressed
         if (event.getCode() == KeyCode.DOWN) {

            //moves player
            if (gameState == 1) {
               DOWN = true;
            }

            //moves the menu
            if (gameState == 2) {
               menuCursor += 1;
               if (menuCursor > 5) {
                  menuCursor = 1;
               }
            }
            
         }
         // If Right Arrow Key Is pressed
         if (event.getCode() == KeyCode.RIGHT) {
            if (gameState == 1) {
               RIGHT = true;
            }

         }

         //when escape is hit, either enters or exits the menu
         if (event.getCode() == KeyCode.ESCAPE) {

            LEFT = false;
            UP = false;
            DOWN = false;
            RIGHT = false;

            if (gameState == 1) {
               gameState = 2;
            } else {
               gameState = 1;
            }

         }
         if (event.getCode() == KeyCode.ENTER) {

            if (gameState == 3) {

               // this is if the file is loading

               String filename = fileLSname + ".txt";

               ContraptionZackLevel newLevel = new ContraptionZackLevel(filename); // makes a new level with the file
                                                                                   // name

               if (newLevel.success) { // if success is true, then a level was loaded successfully
                  level = newLevel; // sets up the level that was loaded

                  if(level.levelNum == 1)
                  {
                     x = level.getPlayerstartx();
                     y = level.getPlayerstarty();

                  }else if (level.levelNum == 2) {
                      x = level.getPlayerstartx();
                     y = level.getPlayerstarty();

                  } else if (level.levelNum == 3) {
                      x = level.getPlayerstartx();
                     y = level.getPlayerstarty();
                  } else if (level.levelNum == 4) {
                      x = level.getPlayerstartx();
                     y = level.getPlayerstarty();

                  }
               }

               gameState = 1;

            }

            //saves the file
            if (gameState == 4) {

               save(fileLSname);
               gameState = 1;

            }

            if (gameState == 2) {
               // Save
               if (menuCursor == 1) {
                  

                  fileLSname = "";
                  gameState = 4;

               }
               // Load
               if (menuCursor == 2) {

                  fileLSname = "";
                  gameState = 3;

               }
               // Restart Area
               if (menuCursor == 3) {
                  
                  if(level.name.equals("1complete.txt"))
                  {
                     level.name="1.txt";
                  }
                  else if(level.name.equals("2complete.txt"))
                  {
                     level.name="2.txt";
                  }
                  
                  level = new ContraptionZackLevel(level.name);
                  if (level.name.equals("0.txt")) {
                     x = 475;
                     y = 725;
                  } else if (level.name.equals("1.txt")) {
                     x = 475;
                     y = 795;

                  } else if (level.name.equals("2.txt")) {
                     x = 475;
                     y = 695;
                  } else if (level.name.equals("3.txt")) {
                     x = 700;
                     y = 250;

                  }
                  

                  gameState = 1;

               }

               // Restart Game
               if (menuCursor == 4) {
                  
                  level = new ContraptionZackLevel("0.txt");
                  x = level.getPlayerstartx();
                  y = level.getPlayerstarty();

                  gameState = 1;

               }
               // Quits
               if (menuCursor == 5) {
                  System.exit(0);
               }

            }

         }

      }
   }

   public class KeyReleased implements EventHandler<KeyEvent> {
      public void handle(KeyEvent event) {

         draw(gc);

         // If Left Arrow Key Is pressed
         if (event.getCode() == KeyCode.LEFT) {
            if (gameState == 1) {
               LEFT = false;
            }
         }
         // If UP Arrow Key Is pressed
         if (event.getCode() == KeyCode.UP) {
            if (gameState == 1) {
               UP = false;
            }
         }
         // If Down Arrow Key Is pressed
         if (event.getCode() == KeyCode.DOWN) {
            if (gameState == 1) {
               DOWN = false;
            }
         }
         // If Right Arrow Key Is pressed
         if (event.getCode() == KeyCode.RIGHT) {
            if (gameState == 1) {
               RIGHT = false;
            }
         }

      }
   }

   //method to write the save file
   public void save(String fileLSname) {

      //creates the file name
      String filename = fileLSname + ".txt";

      try {

         //writes to the file, and writes all the data
         FileWriter writer = new FileWriter(filename);
         writer.write(fileLSname + "\n"); //file name
         writer.write(level.levelNum + "\n"); //level number
         writer.write(level.rows + " " + level.columns + "\n"); //rows and columns
         writer.write(x + " " + y + "\n"); //starting x and y

         //writes the 2d array of tiles
         for (int i = 0; i < level.rows; i++) {
            for (int j = 0; j < level.columns; j++) {
               writer.write(level.getData(j, i).symbol + " ");
            }
            writer.write("\n");
         }

         //writes the directions
         for (int i = 0; i < 4; i++) {
            writer.write(level.directions[i] + "\n");
         }

         //writes the spikes
         for (int i = 0; i < level.spikes.size(); i++) {
            writer.write(level.spikes.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the square buttons
         for (int i = 0; i < level.squares.size(); i++) {
            writer.write(level.squares.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the circle buttons
         for (int i = 0; i < level.circles.size(); i++) {
            writer.write(level.circles.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the jukebox
         for (int i = 0; i < level.jukebox.size(); i++) {
            writer.write(level.jukebox.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the arrows
         for (int i = 0; i < level.arrows.size(); i++) {
            writer.write(level.arrows.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the springs
         for (int i = 0; i < level.springs.size(); i++) {
            writer.write(level.springs.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the walls
         for (int i = 0; i < level.walls.size(); i++) {
            writer.write(level.walls.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the tools
         for (int i = 0; i < level.tools.size(); i++) {
            writer.write(level.tools.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the protrusions
         for (int i = 0; i < level.protrudes.size(); i++) {
            writer.write(level.protrudes.get(i).symbol + " ");
         }
         writer.write("\n");

         //writes the doors
         for (int i = 0; i < level.doors.size(); i++) {
            writer.write(level.doors.get(i).symbol + " ");
         }
         writer.write("\n");

         writer.close();

         // writing the tiledatabase file
         String tdbName = fileLSname + "database.txt";
         FileWriter databaseWriter = new FileWriter(tdbName);

         //writes the spike database data
         for (int i = 0; i < level.spikes.size(); i++) {
            Spike s = level.spikes.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the square button database data
         for (int i = 0; i < level.squares.size(); i++) {
            SquareButton s = level.squares.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the protrusion database data
         for (int i = 0; i < level.protrudes.size(); i++) {
            Protrusion s = level.protrudes.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the arrow database data
         for (int i = 0; i < level.arrows.size(); i++) {
            Arrow s = level.arrows.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the jukebox database data
         for (int i = 0; i < level.jukebox.size(); i++) {
            Jukebox s = level.jukebox.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the tool database data
         for (int i = 0; i < level.tools.size(); i++) {
            Tool s = level.tools.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the spring database data
         for (int i = 0; i < level.springs.size(); i++) {
            Spring s = level.springs.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the walls database data
         for (int i = 0; i < level.walls.size(); i++) {
            Walls s = level.walls.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the circlebutton database data
         for (int i = 0; i < level.circles.size(); i++) {
            CircleButton s = level.circles.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the doors database data
         for (int i = 0; i < level.doors.size(); i++) {
            Doors s = level.doors.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         //writes the tile database data
         for (int i = 0; i < level.tileVals.size(); i++) {
            Tile s = level.tileVals.get(i);
            databaseWriter.write(s.write() + "\n");

         }

         databaseWriter.close();

      } catch (IOException e) {
         System.out.println("error occured");
      }

   }

}
// Last Curly Brace needed
