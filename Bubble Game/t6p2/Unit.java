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

public class Unit extends AbstractUnit{
float x, y,radius,speed;
int playerSide;
public float moveToX, moveToY, xR, yR;
boolean moving = false;
 Color outlineColor;
   //attributes
   public Unit(float x, float y,int playerSide,float radius,float speed,String name){
      super(x,y,radius,playerSide);
      this.x=x;
      this.y=y;
      this.playerSide=playerSide;
      this.radius=radius;
      this.speed=speed;
      this.name=name;
      if(playerSide == 0){
         outlineColor = Color.RED;
      }else if(playerSide==1){
         outlineColor = Color.BLUE;
      }
      
      //this.next=next;
   }
   
   public void run(float notNeededHere){
      //System.out.println("moveing unit");
      if(moving){
         //System.out.println("moving to " + moveToX + " " + moveToY +" x = " + x + " y = " + y);
         if(x > moveToX + 1 || x < moveToX -1 && y > moveToY + 1 || y < moveToY -1){
            x += speed * xR;
            y += speed * yR;         
         }else{
            moving = false;
         }         

      }
   }
   
   public String getName()
   {
   
      return name;
   }
   
    public void draw(GraphicsContext gc)
   {
      //System.out.println("Entering Unit Draw and at base :)");
     // Vector2 vec = new Vector2();
      //Will Return x and y
      returnVector.setX(x);
      returnVector.setY(y);
      
      //System.out.println("Unit x is "+returnVector.getX()+"Unit y is "+returnVector.getY());
      
      //vec=next.getPosition();
      //System.out.println("The X from the vector is "+vec.getX()+" The Y from the vector is "+vec.getY());
      if(playerSide == 0)
      {
         
         gc.setFill(Color.RED.darker());
         //The *2 gives Diameter
         gc.fillOval(x-radius,y-radius,radius*2,radius*2);
         gc.setStroke(outlineColor);   //updated the unit draw cuz the units have an outline and their inner parts are darker
         gc.setLineWidth(3); 
         gc.strokeOval(x-radius,y-radius,radius*2,radius*2);
      
      }
      else if(playerSide == 1)
      {
         gc.setFill(Color.BLUE.darker());
         //The *2 gives radius.
         gc.fillOval(x-radius,y-radius,radius*2,radius*2);
         gc.setStroke(outlineColor);
         gc.setLineWidth(3);
         gc.strokeOval(x-radius,y-radius,radius*2,radius*2);
      
      
      }
   
   }
   
   
   //getter methods
   public void Select(){
      outlineColor = Color.GREEN;
      selected = true;
      
      //System.out.println("Selected");
   
   }
   
   public void Deselect(){
      if(playerSide == 0){
         outlineColor = Color.RED;
      }else{
         outlineColor = Color.BLUE;
      }
      selected = false;
      //System.out.println("Deselected");
   }
   
   float getX(){
      return x;
   }
   
   float getY(){
      return y;
   }
   
   float getRad(){
      return radius;
   }
   
   public float getSpeed(){
      return speed;
   }
   
   public float getPlayerSide(){
      return playerSide;
   
   }
   
   public void move(float xR, float yR, float moveToXI, float moveToYI){
          
      if(selected){
         moveToX = moveToXI;
         moveToY = moveToYI;
         this.xR = xR;
         this.yR = yR;
         
         moving = true;
         
      }
   }
   
}