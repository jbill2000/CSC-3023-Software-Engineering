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

public class Walls {

  Rectangle wallRect;
  Point center;
  String symbol = "";

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

  int groupAssocation;

  // X Y and Color vars
  double xval;
  double yval;

  Color col;
  String type;

  // Constructor
  public Walls(Color c, double xposval, double yposval) {

    col = c;
    xval = xposval;
    yval = yposval;

  }

  //method to turn wall data into a string
  public String write(){

    String s = "Walls ";

    s+= symbol + " ";
    s+=type + " ";
    s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
    s+= xval + " " + yval+ " ";
    s+= groupAssocation + " ";

    return s;

  }

  // Returns x and y respectively
  public double getX() {
    return xval;
  }

  public double getY() {
    return yval;
  }
  
  public String getType(){
   return type;
  }

  //collision and isOverlapping do the work of checking collision
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

    return isOverlapping(characterRect);
  }

  public boolean isOverlapping(Rectangle other) {
    if (wallRect.topRight.getY() < other.bottomLeft.getY()
        || wallRect.bottomLeft.getY() > other.topRight.getY()) {
      return false;
    }
    if (wallRect.topRight.getX() < other.bottomLeft.getX()
        || wallRect.bottomLeft.getX() > other.topRight.getX()) {
      return false;
    }
    return true;
  }

  // creates invisible walls on each side acting as barriers
  public void drawSpecialWall(GraphicsContext gc, double xpos, double ypos, double height, double width) {
    
    wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));
  }

  public void draw(GraphicsContext gc, double xpos, double ypos) {

    gc.setFill(col);
    if(getType().equals("h"))
    {
      gc.fillRect(xpos, ypos, 100, 50);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 100, ypos + 50));
    }
    if(getType().equals("h2"))
    {
      gc.fillRect(xpos, ypos, 75, 50);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 75, ypos + 50));
    }
    if(getType().equals("v"))
    {
      gc.fillRect(xpos, ypos, 50, 100);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 50, ypos + 100));
    
    }
    if(getType().equals("s"))
    {
      gc.fillRect(xpos, ypos, 25, 25);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 25, ypos + 25));
    
    }
    if(getType().equals("v2"))
    {
      gc.fillRect(xpos, ypos, 50, 75);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 50, ypos + 75));
    
    }
    if(getType().equals("void"))
    {
      gc.fillRect(xpos, ypos, 200, 140);
      wallRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 200, ypos + 140));
    
    }
    
  }

}
