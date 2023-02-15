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
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class Main extends Application
{
   VBox root = new VBox();
   Stage theStage;
   
   Button start = new Button("Start");
    
   Scene menu;
   Scene game;
   
   Game theGame = new Game();

   public boolean selectable = false;
   public boolean movable = false;
   Unit selected = null;
   AbstractUnit selectedStart = null;
   
   
   static double deltaTime; 
   
   //get the amount of time passed between the last frame and this frame.
   public static double getDeltaTime()
   {
      return deltaTime;
   }

   public void setMenu(Stage stage)
   {
      stage.setScene(menu);
      stage.setTitle("Bubble Poppers!?!?!???!");
      stage.show(); 
   }
   public void start(Stage stage)
   {
      //creating the menu & scences. Don't worry about this code. You shouldn't have to change anything in this method... I mean unless you want too
      Label title = new Label("Bubble Poppers 3000");
      title.setFont(Font.font ("Verdana", 34));
      
      Label subtitle = new Label("Here *they* come!");
      subtitle.setFont(Font.font ("Verdana", 18));
   
      Label spacer1 = new Label(" ");
      spacer1.setFont(Font.font ("Verdana", 52));

      start.setFont(Font.font ("Verdana", 14));      
      start.setPrefSize(150,30);

      root.getChildren().add(title);
      root.getChildren().add(subtitle);
      root.getChildren().add(spacer1);
      root.getChildren().add(start);
      root.setAlignment(Pos.TOP_CENTER);


      start.setOnAction(new ButtonListener());  
      theStage = stage;
      menu = new Scene(root,800,600);
      setMenu(stage);
      
      VBox empty = new VBox();            
      empty.getChildren().add(theGame);
      game = new Scene(empty, 800, 600);

      theGame.setOnKeyPressed(new KeyListenerDown());
      theGame.setOnMousePressed(new MousePressedListener());
   }
   
   public static void main(String [] args)
   {
      launch(args);
   }
   
   //handler for the start button
   public class ButtonListener implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent e)      
      {
         if(e.getSource() == start)
         {
            theGame.load("map1.txt");
            theAnimator.start();
            theStage.setScene(game);
            theGame.requestFocus();
         }
      }
   }
   
   
   AnimationHandler theAnimator = new AnimationHandler();
   
   public class AnimationHandler extends AnimationTimer
   {
      long lastTime=-1;
      
      //runs whatever each frame. Keeps track of the "deltatime" or the time between frames.
      public void handle(long currentTimeInNanoSeconds) 
      {
         
         if(lastTime != -1 )
         {
            long t = (currentTimeInNanoSeconds-lastTime)/1000l;
            deltaTime = t*1.0/1000000;

            theGame.run();
            theGame.draw();
         }
         lastTime = currentTimeInNanoSeconds;
      }
   }

   boolean paused=false;

   //listeners to keep track of whether a key is up or down
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      {
          if (event.getCode() == KeyCode.Z) 
          {
            selectable = true;
            movable = false;
            //System.out.println("Selectable");
          }
          if(event.getCode() == KeyCode.C)
          {
            movable = true;
            selectable = false;
            //System.out.println("Movable");
          }
      }
   }
   
   int active = 0;
   
   //what should happen when the mouse is clicked
   public class MousePressedListener implements EventHandler<MouseEvent>  
   {
      public void handle(MouseEvent me) 
      {
         float x = (int) me.getX();
         float y = (int) me.getY();
         
         //maybe do something here on a click depending on what command is active?
         
         
         
         //made for translating
         Map theMap = theGame.getMap();
         
         
         
         //if z has been clicked
         if(selectable){
            //System.out.println(x + " " + y);
            
            //list of arrays of units and decorators
            ArrayList<AbstractUnit[]> completedUnits = theGame.getUnits();
            
            //iterate through units
            for(int i = 0; i < completedUnits.size(); i++){
            
            
              AbstractUnit[] thisUnit = completedUnits.get(i);
              
              
              Deselect des = new Deselect((thisUnit[0]));
              des.execute();

           
               //Unit is the last index of array, get unTranslated x and y
               float unitX = thisUnit[thisUnit.length-1].getPosition().getX();
               float unitY = thisUnit[thisUnit.length-1].getPosition().getY();
               
               //translate them
               unitX += (800-theMap.getXSize())/2;
               unitY += (600 - theMap.getYSize())/2;
               
               //System.out.println(unitX + " " + unitY);
               
               //radius used for collision
               float unitRad = ((Unit) thisUnit[thisUnit.length-1]).getRad();
               
                              
               //if mouse over unit, select the unit and grab the bubble that starts the recursion
               if(x > unitX - unitRad && x < unitX + unitRad){
                  if(y > unitY - unitRad && y < unitY +unitRad){
                     selectedStart = thisUnit[0];
                     selected = (Unit)thisUnit[thisUnit.length-1];
                     Select sel = new Select(selectedStart);
                     sel.execute();
                     
                  }
               }
               
               
               
            }
            
            for(int i = 0; i < completedUnits.size(); i++){
               
               AbstractUnit[] checkUnit = completedUnits.get(i);
               
               if(checkUnit[checkUnit.length-1] != selected){
                  Deselect des1 = new Deselect((checkUnit[0]));
                  des1.execute();
               }
            
            }
               
         
         }
         
         //if c has been clicked
         if(movable && selectedStart != null && selected != null){
         
            
            //moods code
            float unitX = selected.getPosition().getX();
            float unitY = selected.getPosition().getY();
            
            //translate them
            //unitX += (800-theMap.getXSize())/2;
            //unitY += (600 - theMap.getYSize())/2;
            
         
            
            
            x += (theMap.getXSize()- 800)/2;
            y += (theMap.getYSize()- 600)/2;
            
            float diffx = x - unitX;
            float diffy = y - unitY;
                
            double mag = Math.sqrt(diffx*diffx + diffy*diffy);
                
            diffx /= mag;
            diffy /= mag;
            
            Move moveUnit = new Move(selectedStart, diffx, diffy, x, y);
            moveUnit.execute();
            

         } 
      }   
   }
}