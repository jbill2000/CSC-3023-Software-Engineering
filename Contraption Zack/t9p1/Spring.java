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


//this is the class for the spring tile

public class Spring{
      int groupAssocation;

      //class data
      double xval;
      double yval;
      String type;
      String symbol = "";
      Rectangle sRect;
      Color col;
      boolean currentlyPressed=false;
      boolean pressed=false;
      boolean left=false;
      boolean right=false;
      int leftcount=-1;
      int rightcount=-1;
      
      
      //Collision Shtuff
      
  //point and rectangle classes are used for collision      
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

      //Constructor 
    public Spring(Color c, double xposition, double yposition){
     col=c;
     xval=xposition;
     yval=yposition;
    }

    //method to turn spring data into a string
    public String write(){

      String s = "Spring ";
  
      s+= symbol + " ";
      s+=type + " ";
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


    public double getX()
    {
      return xval; 
    }
    public double getY()
    {
      return yval;
    }
    public void draw(GraphicsContext gc, double xpos, double ypos)
    {
        
        //Drawing of the spring
        gc.setFill(col);
        gc.fillRect(xpos,ypos,100,100);
        gc.setFill(Color.GRAY);
        gc.fillRect(xpos+10,ypos+10,80,80);
        
        if(type.equals("left") && pressed == false)
        {
            gc.setStroke(col);
            gc.setLineWidth(2);
            gc.strokeLine(xpos+20,ypos+10,xpos+20,ypos+90);
            gc.setLineWidth(4);
            gc.strokeLine(xpos+40,ypos+10,xpos+40,ypos+90);
            gc.setLineWidth(6);
            gc.strokeLine(xpos+60,ypos+10,xpos+60,ypos+90);
            gc.setLineWidth(8);
            gc.strokeLine(xpos+80,ypos+10,xpos+80,ypos+90);
            gc.setLineWidth(1);
            gc.setStroke(Color.BLACK);
         
        
        }
        if(type.equals("right") && pressed == false)
        {
        gc.setStroke(col);
            
            gc.setLineWidth(8);
            gc.strokeLine(xpos+20,ypos+10,xpos+20,ypos+90);
            gc.setLineWidth(6);
            gc.strokeLine(xpos+40,ypos+10,xpos+40,ypos+90);
            gc.setLineWidth(4);
            gc.strokeLine(xpos+60,ypos+10,xpos+60,ypos+90);
            gc.setLineWidth(2);
            gc.strokeLine(xpos+80,ypos+10,xpos+80,ypos+90);
            gc.setLineWidth(1);
            gc.setStroke(Color.BLACK);
            
        
        }
        if(type.equals("left") && pressed == true)
        {
            gc.setStroke(col);
            gc.setLineWidth(8);
            gc.strokeLine(xpos+20,ypos+10,xpos+20,ypos+90);
            
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            left=true;
            leftcount++;
        
        }

        if(type.equals("right") && pressed == true)
        {
            gc.setStroke(col);
            
            gc.setLineWidth(8);
            gc.strokeLine(xpos+80,ypos+10,xpos+80,ypos+90);
            
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            right=true;
            rightcount++;
        
        }
        
        //Collision detection time
        
        sRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + 80, ypos + 80));
        
              
    }

    //these two methods do the work of checking collision
    public boolean collision(double xpos, double ypos, int height, int width) {
      Rectangle characterRect = new Rectangle(new Point(xpos, ypos), new Point(xpos + width, ypos + height));

      return isOverlapping(characterRect);
    }

  public boolean isOverlapping(Rectangle other) {
    if (sRect.topRight.getY() < other.bottomLeft.getY()
        || sRect.bottomLeft.getY() > other.topRight.getY()) {
      return false;
    }
    if (sRect.topRight.getX() < other.bottomLeft.getX()
        || sRect.bottomLeft.getX() > other.topRight.getX()) {
      return false;
    }
    return true;
  }


}
