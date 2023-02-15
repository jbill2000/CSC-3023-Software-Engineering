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

public class CircleButton {

  Rectangle cbRect; //this rectangle is used for collision purposes
  
  //the point class is used for the rectangle
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

  //rectangle class used for collision
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

  boolean pressed = false; // bool for if button has been pressed

  int groupAssocation; // ideally, the spike would have the same number as the corresponding button
  
  // X Y and Color vals
  double xval;
  double yval;
  Color col;
  
  boolean currentlyPressed = false;
  double time;
  String symbol = "";

  // CircleButton Constructor
  public CircleButton(Color c, double xposval, double yposval) {

    col = c;
    xval = xposval;
    yval = yposval;
    time = System.currentTimeMillis();

  }

  //swaps the status of the button
  public void change() {

    pressed = !pressed;

  }

  //method to turn circle button data into a string
  public String write(){

    String s = "CircleButton ";

    s+= symbol + " ";
    s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
    s+= xval + " " + yval+ " ";
    s+= groupAssocation + " ";

    if(pressed){
      double t = System.currentTimeMillis() - time;
      s+= t + " ";
    }
    else{
      s+= 0 + " ";
    }

    return s;

  }

  // Returns x and y respectively.
  public double getX() {
    return xval;
  }

  public double getY() {
    return yval;
  }

  // CB Draw
  public void draw(GraphicsContext gc, double xpos, double ypos) {

    //creates a blank square
    gc.setFill(Color.WHITE);
    gc.fillRect(xpos+5,ypos+5,90,90);
    
    //color shifts on whether or not the button is pressed
    if (pressed==false) {
      gc.setFill(col);
    } if(pressed==true) {
      Color col2 = new Color(col.getRed() * .8, col.getGreen() * .8, col.getBlue() * .8, 1);
      gc.setFill(col2);
    }

    //draws the circle then sets the rectangle
    gc.fillOval(xpos+10, ypos+10, 80, 80);
    
    cbRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 90, ypos + 90));

  }

  //method used for checking collision
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

    return isOverlapping(characterRect);
  }

  //the behind the scenes of collision
  public boolean isOverlapping(Rectangle other) {
    if (cbRect.topRight.getY() < other.bottomLeft.getY()
        || cbRect.bottomLeft.getY() > other.topRight.getY()) {
      return false;
    }
    if (cbRect.topRight.getX() < other.bottomLeft.getX()
        || cbRect.bottomLeft.getX() > other.topRight.getX()) {
      return false;
    }
    return true;
  }

}