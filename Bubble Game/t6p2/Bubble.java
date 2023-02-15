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
import java.lang.Math;

public class Bubble extends DecorationParent{
    float bubblex, bubbley, size, damage, speed, refire, bubblesize, targetX, targetY;
    //Making Float playerside here since we need it for projectiles
    float playerSide;
    float shortestDistance = 10000;
    float distanceTo = Integer.MAX_VALUE;
    Strategy0 strat0;
    Strategy1 strat1;
    Strategy2 strat2;
    Unit closestUnit = null;
    //Ints as part of the bubble creation :)
   int projectiletype, range;
  
   public float moveToX, moveToY, xR, yR, diffx, diffy;
   boolean shooting = false;
   ArrayList<projectile> proj = new ArrayList<projectile>();
   ArrayList<AbstractUnit[]> Units = new ArrayList<AbstractUnit[]>();
  
   float dCount=0;
   float lifetime;
   
   //add health params
   public Bubble(float x, float y, float radius, int playerSide, float bubblex, float bubbley, int projectiletype, float size, float damage, float speed, float refire, int range, float bubblesize){
      super(x,y,radius,playerSide);
      //This was the OG super, revert to this if it messes up.
      //super(x,y,size);
      this.next = next;
      this.bubblex=bubblex;
      this.bubbley=bubbley;
      this.projectiletype=projectiletype;
      this.size=size;
      this.damage=damage;
      this.speed=speed;
      this.refire=refire;
      this.range=range;
      this.bubblesize=bubblesize;
      this.playerSide=playerSide;
      
      strat0 = new Strategy0(speed, refire);
      strat1 = new Strategy1(speed, refire);
      strat2 = new Strategy2(speed, refire);
      
      switch(projectiletype){
         case 0:
            lifetime = 5;
         break;
         
         case 1:
            lifetime = 2;
         break;
         
         case 2:
            lifetime = 1;
         break;
         }
   }
   
   
   public void run(float unitSpeed, ArrayList<AbstractUnit[]> completedUnits, float playerSide){
      
      
      
      shortestDistance = 10000;
      
      
      // shortestDistance = Integer.MAX_VALUE;
      for(int i = 0; i < completedUnits.size(); i++){   
         AbstractUnit[] units = completedUnits.get(i);
         
         if(playerSide != ((Unit) units[units.length-1]).getPlayerSide()){
            
            float otherX = ((Unit) units[units.length-1]).getX();
            float otherY = ((Unit) units[units.length-1]).getY();
      
            
            //calc distance betwfen you and unit
            //System.out.println("x: "+  x + " y: " +y + " otherX: " + otherX + " otherY: " + otherY);
            distanceTo = ((float) Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2)));
            
            //System.out.println(distanceTo);
         
            //if there is a closer unit set that to the one to target
            if(distanceTo < shortestDistance){
            
               shortestDistance = distanceTo;
               
               //System.out.println("closest " + distanceTo);

               closestUnit = ((Unit) units[units.length-1]);
               targetX = ((Unit) units[units.length-1]).getX();
               targetY = ((Unit) units[units.length-1]).getY();
               shooting = false;
            }
             
           }
      }
   
      
      
      //if within range shoot
      for(int i = 0; i < completedUnits.size(); i++){
         AbstractUnit[] units = completedUnits.get(i);
         //if player is on opposite team         
         if(playerSide != ((Unit) units[units.length-1]).getPlayerSide()){
       
            if(shooting == false && shortestDistance < range){
               shooting = true;
               
               //this is the code that gets the shooters to refire
                  //it was this simple
               if(dCount >= refire) //shoot
               {
                  shoot(targetX, targetY, completedUnits); //shoot
                  dCount=0;  //reset the time                
               }
               dCount+=Main.getDeltaTime(); //add to time  
   
            }else{
               shooting = false; 
               if(dCount >= refire) //shoot
               {
                  shoot(0,0, completedUnits); //shoot
                  dCount=0;  //reset the time                
               }
               
               
               dCount+=Main.getDeltaTime(); //add to time 
            } 
            
            
            
                     
         }
         
         
                  
      }
      
      
      //movement check, see if its at its destination
      if(moving){
         //System.out.println("moving to " + moveToX + " " + moveToY +" x = " + x + " y = " + y);
         if(x > moveToX + 1 || x < moveToX -1 && y > moveToY + 1 || y < moveToY -1){
            x += unitSpeed * xR;
            y += unitSpeed * yR;      
            lifetime-=Main.getDeltaTime();
   
         }else{
            moving = false;
            
         }
         
      }
      
      //Executing the strategy needs to happen in the run :)
      switch(projectiletype)
      {
         case 0:
         {
            proj=strat0.execute(proj,completedUnits);
            break;
         }
         case 1:
         {
            proj = strat1.execute(proj, completedUnits);
            break;
         }
         case 2:
         {
            proj = strat2.execute(proj, completedUnits);
            break;
         }
      
      }
      
   }
   
   public void shoot(float toX, float toY, ArrayList<AbstractUnit[]> Units){

      switch(projectiletype){
         case 0:
            
            //do the math for projectiles before hand, so they do not heat seak
            diffx = toX - x;
            diffy = toY - y;
                
            double mag = Math.sqrt(diffx*diffx + diffy*diffy);
                
            diffx /= mag;
            diffy /= mag;
         
            if(shooting){
            //create projectile
               projectile0 basic = new projectile0(x, y, diffx, diffy, speed, bubblesize, projectiletype,playerSide, damage, (float)5);
               
               //add projectile to projectile arraylist
               proj.add(basic);

            
            }     
        
            //activate strategy0 here
           //proj = strat0.execute(proj, Units);
               
            break;
            
         case 1:
         
            diffx = toX - x;
            diffy = toY - y;
                
            mag = Math.sqrt(diffx*diffx + diffy*diffy);
                
            diffx /= mag;
            diffy /= mag;

            if(shooting){
            //create projectile
            projectile1 shotgun = new projectile1(x, y, diffx, diffy, speed, bubblesize, projectiletype,(float)playerSide, damage, (float)2);
            //add projectile to array list
            proj.add(shotgun);
            }
            
            //activate strategy1 here
            //proj = strat1.execute(proj, Units);
            
            break;
         
         case 2:
            //create projectile
            diffx = toX - x;
            diffy = toY - y;
                
            mag = Math.sqrt(diffx*diffx + diffy*diffy);
                
            diffx /= mag;
            diffy /= mag;
            
            if(shooting){
            projectile2 burst = new projectile2(x, y, diffx, diffy, speed, bubblesize, projectiletype,playerSide, damage, (float)1);
            
            //add projectile to array list
            proj.add(burst);
            }
            
            //activate strategy2 here
            //strat2.execute(proj);
            
            break;
      
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


   
   
   //Accessorss :D
   public float getX()
   {
      return bubblex;
   }
   
   public float getY()
   {
      return bubbley;
   }
   public int getProjectileType()
   {
      return projectiletype;
   }
   
   public float getSize()
   {
      return size;
   }
   
   public float getDamage()
   {
      return damage;
   }
   
   public float getSpeed()
   {
      return speed;
   }
   
   public float getRefire()
   {
      return refire;
   }
   
   public int getRange()
   {
      return range;
   }
   
   public float getBubbleSize()
   {
      return bubblesize;
   }
   
   
   
   public void draw(GraphicsContext gc)
   {
         
     next.draw(gc);
     
     returnVector.setX(x);
     returnVector.setY(y);
          
      gc.setFill(Color.CYAN); //pretty sure its cyan. might also be white but it probably doesnt matter the color      
      gc.fillOval(returnVector.getX()+bubblex-(size/2), returnVector.getY()+bubbley-(size/2), size, size);
           
      if(!proj.isEmpty())
      {
         for(int i=0; i<proj.size(); i++)
         {
            proj.get(i).draw(gc);
         }
      }
   
   }   
   
   
}