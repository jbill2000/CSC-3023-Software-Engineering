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

public class Protrusion {

  Rectangle protrusionRect;
  Point center;
  Walls rightWall;
  Walls leftWall;

  String symbol = "";

  double width;
  double height;

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
  public Protrusion(Color c, double xposval, double yposval, double w, double h) {

    col = c;
    xval = xposval;
    yval = yposval;
    width=w;
    height=h;

  }

  //method to turn protrusion data into a string
  public String write(){

    String s = "Protrusion ";

    s+= symbol + " ";
    s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
    s+= xval + " " + yval+ " " + width + " " + height + " ";
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

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  //draws and creates walls
  public void draw(GraphicsContext gc, double xpos, double ypos, double width, double height) {

    gc.setFill(col);
    gc.fillRect(xpos, ypos, width, height);
    protrusionRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos));

    rightWall = new Walls(new Color(0, .3, 0, 1), xpos + width, ypos+height);
    rightWall.drawSpecialWall(gc, xpos + width, ypos, width, height);
    leftWall = new Walls(new Color(0, .3, 0, 1), xpos - width, ypos);
    leftWall.drawSpecialWall(gc, xpos - height, ypos, width, height);
    
  }

  // returns true if the coords passed in overlap with this tile
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

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
