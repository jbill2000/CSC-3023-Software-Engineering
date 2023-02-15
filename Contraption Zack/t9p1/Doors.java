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
public class Doors{

  //class variables
  Rectangle wallRect;
  Rectangle wallRect2;
  boolean opencollisioncreated=false;
  Point center;
  boolean open = false;
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



Color col;
double xpos;
double ypos;
int groupAssocation;
double time;
double currentTime;

public Doors(Color c, double xval, double yval)
{

   col=c;
   xpos=xval;
   ypos=yval;
   time = System.currentTimeMillis();

}

//method to turn doors data into a string
public String write(){

  String s = "Doors ";

  s+= symbol + " ";
  s+= col.getRed() + " " + col.getGreen() + " " + col.getBlue() + " ";
  s+= xpos + " " + ypos+ " ";
  s+= groupAssocation + " ";
  s+= (System.currentTimeMillis()-time) + " ";

  return s;

}

public double getX()
{
   return xpos;
}
public double getY()
{
   return ypos;
}

//changes the status of the doors
public void swap(){
  open = !open;
}

//this method is used to make doors open and close based on time
public void tick(){
  
    currentTime = System.currentTimeMillis();
    if(currentTime - time >2500 && open ==true){
    swap();
    time = System.currentTimeMillis()+22000;
    }
    if(currentTime - time > 5000 && open == false)
    {
      swap();
      time= System.currentTimeMillis()+5000;
  
    }

}

//draws based on if door is open or closed
public void draw(GraphicsContext gc, double x, double y) 
{

  tick();
  
  if(!open){
   gc.setFill(col);
   gc.fillRect(x,y,50,75);
   gc.setStroke(Color.BLACK);
   gc.strokeLine(x,y,x+50,y+25);
   gc.strokeLine(x,y+50,x+50,y+25);
   gc.strokeLine(x,y+50,x+50,y+75);
   wallRect = new Rectangle(new Point(x, y), new Point(x + 50, y + 75));
  }
  if(open)
  {
      gc.setFill(col);
      gc.setStroke(Color.BLACK);
      gc.fillRect(x,y,50,12.5);
      gc.strokeLine(x,y,x+50,y+12.5);
      
      
      gc.fillRect(x,y+62.5,50,12.5);
      gc.strokeLine(x,y+62.5,x+50,y+75);
      wallRect = new Rectangle(new Point(x, y), new Point(x + 50, y + 12.5));
      wallRect2 = new Rectangle(new Point(x, y+62.5), new Point(x+50, y + 12.5));
      opencollisioncreated=true;

  
  }
   

}

//these 2 methods are used to track collision
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


}