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


public class Game extends Canvas
{
   public ArrayList<AbstractUnit[]> completedUnits= new ArrayList<AbstractUnit[]>();

   Map theMap = new Map();
   //Declaring this up here so we can access its indexes for drawing :))))
      public Game()
   {
      setHeight(600);
      setWidth(800);

   }
   
   
   public void clear()
   {

   }
      
      
   //this method reads the level data. Stops before the unit data since you have to write that bit!
   public void load(String mapString)
   {
      clear(); 
     
      
      try
      {
         Scanner scan = new Scanner(new File(mapString));
         theMap = new Map(); //clear it all out first!!!!!
         theMap.readMap(scan); //pass scanner to map to read the map
         //Copies the Completed Unit setup
         completedUnits= theMap.getCompleteUnits();
         
         
         //For Debugging
         //System.out.println("There are "+completedUnits.size()+" completed units in the list");
       
         
      }
      catch(Exception e)
      {
         System.out.println("File Read Error!");
         e.printStackTrace();
      }
      
      
      
      
   }
   
   
   //this method is called each frame. Hmm I wonder what should happen here? is it a shame I deleted all this code before giving it to you?
   public void run()
   {
      AbstractUnit[] units;
      int loopSize = completedUnits.size();
      for(int i = 0; i < loopSize; i++){
         units = completedUnits.get(i);
         float unitSpeed = ((Unit) units[units.length-1]).getSpeed();
         float playerSide = ((Unit) units[units.length-1]).getPlayerSide();
         
         
         for(int j = 0; j < units.length; j++){
            if(units[j] instanceof Bubble){
               ((Bubble) units[j]).run(unitSpeed, completedUnits, playerSide);
            
            }
               else{
               units[j].run(unitSpeed);  
            }
            
            
            if(units[j] instanceof Health && units[j+1] instanceof Health){
               continue;
            }
            else if(units[j] instanceof Health){
               if(((Health) units[j]).getCurrentHealth() <= 0)
               {
                  completedUnits.remove(i);
                  loopSize--;
                  if(i < loopSize){
                     units = completedUnits.get(i);
                  }
               } 
            }
         } 
      } 
      
      
   }
   
   public void draw()
   {
      GraphicsContext gc = getGraphicsContext2D();
      gc.setFill(Color.BLACK);
      gc.fillRect(0,0,800,600);
      
      //moves to center 
      gc.translate( (800-theMap.getXSize())/2, (600 - theMap.getYSize())/2);
      theMap.draw(gc);

      
      //you probably want some code here to draw the bubbles and units. Just a thought! No pressure though!
      for(int i=0; i<completedUnits.size(); i++)
      {
         AbstractUnit[] array = completedUnits.get(i);
         //System.out.println(array[i]);
         array[0].draw(gc);
      
      }         
      
      
      //un "centers". Why pop matrix no work :(  ?????  Meh
      gc.translate( (800-theMap.getXSize())/-2, (600 - theMap.getYSize())/-2);
   }
   
   public ArrayList<AbstractUnit[]> getUnits(){
      return completedUnits;
   }
   
   public Map getMap(){
      return theMap;
   
   }
   
}