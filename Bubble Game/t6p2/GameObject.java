import java.util.*;
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

//base game object class. Contains position, radius, and side information. Probably want your classes for playable objects to inherit (somehow) from this class
public abstract class GameObject 
{
   //each GO has its own vector to return a position for get position. Vector2 is defined at the end of this class.
   protected Vector2 returnVector = new Vector2();
   
   protected int side;

   protected float x;
   protected float y;
   protected float radius;
   protected float radiusSquared; //unused in my implementation. would have been nice for efficency, but the decorator destroyed my hopes for an efficient program... Like literally. hopes = gone.
   
   
   public GameObject(float x, float y, float radius)
   {
      this.x = x;
      this.y = y;
      this.radius = radius;
      radiusSquared = radius*radius;
      //side=playerSide;
   }
    
   //base draw method. I tried to be helpful so it would give you a nice little warning if you use it accidently (AKA without overriding it)
   public void draw(GraphicsContext gc)
   {
      gc.setFill(Color.RED);
      gc.fillText("GAME OBJECT DRAWN w/o overloading draw",x,y);
   }
   
   //probably want to uncomment this, but the sample code would not compile unless I commented this out since I'm not giving you that class.
   public void run(float NotNeededHere)
   {
      
   }
   
   public abstract Vector2 getPosition();

   
   //returns the radius of the gameobject.
   public float getRadius()
   {
      return radius;
   }
   
   //which side owns the bubble
   public int getSide()
   {
      return side;
   }
   
   
   //a class representing a vector2 -> a vector of only x and y (as opposed to a vector 3 which would be for 3D space).
   public static class Vector2
   {
      private float x;
      private float y;
      
      public void setX(float x){this.x = x;}
      public void setY(float y){this.y = y;}
      public float getX(){return x;}
      public float getY(){return y;}
   }

}