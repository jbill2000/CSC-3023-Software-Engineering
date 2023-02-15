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

public class Tool {

  int groupAssocation; // this keeps track of which button the spike is tied too

  double xcoords; // xcoords for screwdriver drawing
  double ycoords;// ycoords for screwdriver drawing
  String filename;
  
  Image toolImage;
  String type;
  String symbol = "";

  boolean taken=false; //tracks if user has picked it up yet

  Rectangle tileRect;
  Point center;

  //this is a point class to tie into the rectangle class
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

    //rectangle class is used for collision
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
  public Tool(double xpos, double ypos, String filepath) {
    xcoords = xpos;
    ycoords = ypos;
    filename = filepath;

  }

  //method to turn tool data into a string
  public String write(){

    String s = "Tool ";

    s+=symbol + " ";
    s+= filename + " ";
    s+= xcoords + " " + ycoords + " ";
    s+= groupAssocation + " ";
    if(taken == true)
    {
      s+= 1 + " ";
    
    }
    else
    {
      s+= 0 + " ";
    }

    return s;

  }

  //returns x, y, and the image
  public double getX() {
    return xcoords;

  }

  public double getY() {

    return ycoords;
  }

  public Image getImage() {
    toolImage = new Image(filename);

    return toolImage;

  }

  //draw method
  public void draw(GraphicsContext gc, Image image, double xpos, double ypos) {

    //draws if the tool is not taken
    if(!taken){
      gc.drawImage(image, xpos, ypos);
      
    }

    //defines the rectangle
    tileRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 100, ypos + 100));

  }

  //method used to see if player collides with the tool
  public boolean collision(Double xpos, Double ypos, int height, int width) {
    Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

    return isOverlapping(characterRect);
  }

  //the behind the scenes work of the collision method
  public boolean isOverlapping(Rectangle other) {
      if (tileRect.topRight.getY() < other.bottomLeft.getY()
              || tileRect.bottomLeft.getY() > other.topRight.getY()) {
          return false;
      }
      if (tileRect.topRight.getX() < other.bottomLeft.getX()
              || tileRect.bottomLeft.getX() > other.topRight.getX()) {
          return false;
      }
      return true;
  }

}
