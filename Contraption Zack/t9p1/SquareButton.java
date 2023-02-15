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

public class SquareButton {

  boolean currentlyPressed = false;

  int groupAssocation; // ideally, the spike would have the same number as the corresponding button

  // X Y and Color vars
  double xval;
  double yval;

  String symbol = "";

  Rectangle sbRect;

  Color col;
  boolean pressed;
  public class Point {
    double x;
    double y;

    //point and rectangle are used for collision
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
  public SquareButton(Color c, double xposval, double yposval) {

    col = c;
    xval = xposval;
    yval = yposval;

  }

  //method to turn the squarebutton data into a string
  public String write(){

    String s = "SquareButton ";

    s+= symbol + " ";
    s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
    s+= xval + " " + yval+ " ";
    s+= groupAssocation + " ";

    if(!pressed){
      s+= 1 + " ";
    }
    else{
      s+= 0 + " ";
    }

    return s;

  }

  // Returns x and y respectively
  public double getX() {
    return xval;
  }

  public double getY() {
    return yval;
  }

  //draws the button, color shifts depending on if button is pressed
  public void draw(GraphicsContext gc, double xpos, double ypos) {

    sbRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 80, ypos + 80));
    gc.setFill(Color.GRAY);
    gc.fillRect(xpos+5,ypos+5,90,90);
    if(pressed){
        Color col2 = new Color(col.getRed()*.8,col.getGreen()*.8,col.getBlue()*.8,1);
        gc.setFill(col2);

      }
      if(!pressed)
      {
        gc.setFill(col);
        
      
      }
      
      gc.fillRect(xpos+10, ypos+10, 80, 80);

    
    

  }

  //these two methods do collision
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

    return isOverlapping(characterRect);
  }

  public boolean isOverlapping(Rectangle other) {
    if (sbRect.topRight.getY() < other.bottomLeft.getY()
        || sbRect.bottomLeft.getY() > other.topRight.getY()) {
      return false;
    }
    if (sbRect.topRight.getX() < other.bottomLeft.getX()
        || sbRect.bottomLeft.getX() > other.topRight.getX()) {
      return false;
    }
    return true;
  }

}
