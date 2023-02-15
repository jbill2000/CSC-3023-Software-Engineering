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

public class Jukebox {

    Rectangle protrusionRect;
    Point center;
    Walls rightWall;
    Walls leftWall;
  
    double width;
    double height;
    String symbol = "";

    int timer = 0;
    boolean playing = false;
  
    // boolean pressed = false; //bool for if button has been pressed
  
    int groupAssocation;
  
    // X Y and Color vars
    double xval;
    double yval;
  
    Color col;
  
    //point and rectangle are used for collision purposes
    public class Point {
      double x;
      double y;
  
      public Point(double x, double y) {
        this.x = x;
        this.y = y;
      }
  
      public double getX() {
        return x;
      }
  
      public double getY() {
        return y;
      }
    }
  
    public class Rectangle {
      Point bottomLeft;
      Point topRight;
  
      public Rectangle(Point bottomLeft, Point topRight) {
        this.bottomLeft = bottomLeft;
        this.topRight = topRight;
      }
  
      public Point getBottomLeft() {
        return bottomLeft;
      }
  
      public Point getTopRight() {
        return topRight;
      }
    }
  
    // Constructor
    public Jukebox(Color c, double xposval, double yposval, double w, double h) {
  
      col = c;
      xval = xposval;
      yval = yposval;
      width=w;
      height=h;
  
    }
  
    //method to turn jukebox data into a string
    public String write(){

      String s = "Jukebox ";
  
      s+= symbol + " ";
      s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
      s+= xval + " " + yval+ " " + width + " " + height + " ";
      s+= groupAssocation + " ";
  
      return s;
  
    }
  
    // Returns x, y, width, and height
    public double getX() {
      return xval;
    }
  
    public double getY() {
      return yval;
    }
  
    public double getWidth() {
      return width;
    }
  
    public double getHeight() {
      return height;
    }
  
    //if timer hits 100, the jukebox switches what state it is in
    //this is used to create animation

    public void tick(){

        timer ++;
        if(timer>100){
            timer=0;
            playing = !playing;
        }

    }

    //draws and creates walls
    public void draw(GraphicsContext gc, double xpos, double ypos, double width, double height) {
  
      tick();

      gc.setFill(col);

      gc.fillRect(xpos, ypos, width, height);
      protrusionRect = new Rectangle(new Point(xpos, ypos), new Point(xpos+100, ypos+100));
  
      rightWall = new Walls(new Color(0, .3, 0, 1), xpos+100, ypos);
      rightWall.drawSpecialWall(gc, xpos+100, ypos, width, height);
      leftWall = new Walls(new Color(0, .3, 0, 1), xpos - 50, ypos);
      leftWall.drawSpecialWall(gc, xpos - 50, ypos, width, height);
    
      if(playing){

        gc.fillRect(xpos-20,ypos+20,20,60);

      }
     
    }
  
    // returns true if the coords passed in overlap with this tile
    public boolean collision(Double xpos, Double ypos, int height, int width) {
      Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos+width, ypos+height));
  
      return isOverlapping(characterRect);
    }
  
    public boolean isOverlapping(Rectangle other) {
      if (protrusionRect.topRight.getY() < other.bottomLeft.getY()
          || protrusionRect.bottomLeft.getY() > other.topRight.getY()) {
        return false;
      }
      if (protrusionRect.topRight.getX() < other.bottomLeft.getX()
          || protrusionRect.bottomLeft.getX() > other.topRight.getX()) {
        return false;
      }
      return true;
    }
  }