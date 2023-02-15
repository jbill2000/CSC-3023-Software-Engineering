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
import java.io.*;
import java.text.*;
import java.lang.Math;

public class projectile
{
   float speed, size, x, y, diffx, diffy, damage, lifetime;
   float playerSide;
   int projectileType;
   
   public projectile(float x, float y, float diffx, float diffy, float speed, float size, int projectileType, float playerSide, float damage, float lifetime){
      
      this.speed = speed;
      this.size = size;
      this.x = x;
      this.y = y;
      this.diffx = diffx;
      this.diffy = diffy;
      this.damage = damage;
      this.lifetime = lifetime;
      this.projectileType = projectileType;
      
      //need to put player side into projectile to make physics work, please implement this
      this.playerSide=playerSide;
      //Why is this 3?
     // playerSide = 3;
      
      //System.out.println("projectile");
   }
   public void draw(GraphicsContext gc)
   {
      //System.out.println("drawing"+ size);
    if(projectileType==0)
    {
      gc.setFill(Color.TAN);
      gc.setStroke(Color.LIGHTGRAY);
      gc.setLineWidth(1);
      gc.fillOval(x, y, size*2, size*2);
      gc.strokeOval(x, y, size*2, size*2);
    }
    else if(projectileType==1)
    {
      gc.setFill(Color.DARKGREEN);
      gc.setStroke(Color.LIGHTGREEN);
      gc.setLineWidth(1);
      gc.fillOval(x, y, size*2, size*2);
      gc.strokeOval(x, y, size*2, size*2);
    
    }
    else if(projectileType==2)
    {
      gc.setFill(Color.PURPLE);
      gc.setStroke(Color.LIGHTGRAY);
      gc.setLineWidth(1);
      gc.fillOval(x, y, size*2, size*2);
      gc.strokeOval(x, y, size*2, size*2);
    
    }
      
      
     
   }
   
   public float getSide(){
      return playerSide;
   }
   public int getPType()
   {
      return projectileType;
   }
   public float getX()
   {
      return x;
   }
   
   public float getY()
   {
      return y;
   }
   
   public float getDiffx(){
   
      return diffx;
   }
   
   public float getDiffy(){
   
      return diffy;
   }
   
   public float getRadius(){
      return size;
   
   }

   public void setX(float xIn){
      x = xIn;
   }
   
   public void setY(float yIn){
   
      y = yIn;
   
   }
   public float getDamage()
   {
      return damage;
   }
   public float getLifetime()
   {
      return lifetime;
   }
   public void setLifetime(float life)
   {
      lifetime = life;
   }
   public void decreaseLifetime()
   {
      lifetime-=Main.getDeltaTime();
   }
   
}