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

public class Health extends DecorationParent{
   
   //Health Vars yay!
   float healthx;
   float healthy;
   float totalHp;
   float rad;
   float currentHp;
   public float moveToX, moveToY, xR, yR;
   //add health params
   public Health(float x, float y, float radius, int playerSide,float healthx, float healthy, float totalHp, float rad){
      super(x,y,radius,playerSide);
      //This was old super, revert to it if things go bad.
      //super(x,y,rad);
      this.next = next;
      this.healthx=healthx;
      this.healthy=healthy;
      this.totalHp=totalHp;
      this.rad=rad;
      currentHp = totalHp;
   }
   
   public float getX()
   {
      return healthx;
   }
   public float getY()
   {
      return healthy;
   }
   public float gethp()
   {
      return totalHp;
   }
   public float getRad()
   {
      return rad;
   }
   
 //HERE  
   public float getCurrentHealth()
   {
      return currentHp;
   }
   public void KILL(float damage)
   {
      currentHp-=damage;
   }
   
    public void draw(GraphicsContext gc)
   {
   
     //System.out.println("Entering Health Draw and calling next to go to the base :)");
      //Think this is how the recursion plays out?
      
      
      next.draw(gc);
      
      returnVector.setX(x);
      returnVector.setY(y);
      
      gc.setFill(new Color(0,Math.max(currentHp/totalHp,0),0,1)); //rgb for green. middle param is so the green gets gets darker or something as the unit takes damage
      gc.fillOval(returnVector.getX()+healthx-(rad/2), returnVector.getY()+healthy-(rad/2), rad, rad);
    }
   
   public void run(float unitSpeed){
      
      if(moving){
         //System.out.println("moving to " + moveToX + " " + moveToY +" x = " + x + " y = " + y);
         if(x > moveToX + 1 || x < moveToX -1 && y > moveToY + 1 || y < moveToY -1){
            x += unitSpeed * xR;
            y += unitSpeed * yR;         
         }else{
            moving = false;
         }
         
      }
   }

   
   
   public void move(float xR, float yR, float moveToXI, float moveToYI){
            
      if(selected){
         moveToX = moveToXI;
         moveToY = moveToYI;
         this.xR = xR;
         this.yR = yR;
         
         moving = true;
                    
         next.move(xR, yR, moveToXI, moveToYI);
      }
   }
   
   public void Select(){
      selected = true;
      next.Select();
   
   }
   
   public void Deselect(){
      selected = false;
      next.Deselect();
   }

   
         
}