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
import java.io.*;
import java.util.*;
import java.text.*;

public abstract class AbstractUnit extends GameObject{
   
   protected AbstractUnit next;
   String name;
   public boolean selected = false;
   public AbstractUnit(float x, float y, float radius, int playerSide){
      super(x, y, radius);
   
      side=playerSide;
   }
   //Gets the position
   public Vector2 getPosition(){
      return returnVector;
   }
   //Needed for singleton fun probably :))))
   public String getName()
   {
      return name;
   }
   //Sets the next thing for recursion?
   public void setNext(AbstractUnit input)
   {
      next=input;
   
   }
   //gets the next thing
   public AbstractUnit getNext()
   {
      
      return next;
      
   }
   
   public void Select(){
      selected = true;
   
   }
   
   public void Deselect(){
      selected = false;
   
   }
   
   public abstract void move(float xR, float yR, float x, float y);
   
   public abstract void draw(GraphicsContext gc);
   //public abstract void run();
   
   //public abstract AbstractUnit create(AbstractUnit next);
   
   
}