import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.application.Application;

//this class is for the (alleged) obstacles on the playing field
public class Obstacle extends GameObject
{
   public Obstacle(float x, float y, float rad)
   {
      super(x,y,rad);
   }

   //returns the position of the obstacle
   public Vector2 getPosition()
   {
      returnVector.setX(x);
      returnVector.setY(y);
      return returnVector;
   }

   //draws at the specified point
   public void draw(GraphicsContext gc)
   {
      gc.setFill(new Color(1,.5f,.5f,1));      
      gc.fillOval(x-radius-1,y-radius-1,radius*2+2,radius*2+2);
      gc.setFill(new Color(.75f,.5f,.5f,1));      
      gc.fillOval(x-radius+1,y-radius+1,radius*2-2,radius*2-2);
   }
   
   
   //create a new obstacle, which is a copy of this one.
   public Obstacle clone(int x, int y)
   {
      Obstacle o = new Obstacle(x,y,radius);
      return o;
   }
}