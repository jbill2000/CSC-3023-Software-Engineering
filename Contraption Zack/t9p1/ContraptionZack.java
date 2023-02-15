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
import java.io.*;

public class ContraptionZack extends Application
{
   //Variable creation add comment
   ContraptionZackCanvas c;
   int count =0;
   boolean gameOver=false;
   boolean gameStarted=false;
 
   //Start Menu Puttons
   Button Newgame = new Button("New Game");
   Button Load = new Button("Load");
   Button Quit = new Button("Exit");
    
    
   //Pause Menu Buttons
   Button save = new Button("Save");
   Button restart = new Button("Restart");
   Button exit = new Button("Exit");
   Button loader = new Button("Load");
   
   
   public void start(Stage stage)
   {
     
      //Flowpane creation
      //Regular pane
      FlowPane fp = new FlowPane();
      //Pause menu pane
      TilePane tilepane = new TilePane();
      //title menu pane
      FlowPane titlemenu = new FlowPane();
      //Pause menu stage

      tilepane.setTileAlignment(Pos.CENTER_LEFT);

      //aligns the gameboard
      fp.setAlignment(Pos.CENTER);
         
         
      //Title Menu Stage, Positioning and more
      Stage title = new Stage();
      title.setTitle("Welcome to Contraption Zack");
      titlemenu.setAlignment(Pos.CENTER);
         
      //Title Menu Vbox
         
      VBox titlebox = new VBox();
      titlebox.getChildren().add(Newgame);
      titlebox.getChildren().add(Load);
      titlebox.getChildren().add(Quit);
      //Creates the Background image, setting it to not repeat in either x or y, null for other two since its not necessary to set as they will default accordingly. And Default is fine here.
      BackgroundImage titleback= new BackgroundImage(new Image("title.png",1000,800,false,false),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,null,null);
      //Sets the background of the game
      titlemenu.setBackground(new Background(titleback));
         
    
     
     //Pause Menu Vbox
      VBox vb = new VBox();
      vb.getChildren().add(save);
      vb.getChildren().add(restart);
      vb.getChildren().add(loader);
      vb.getChildren().add(exit);
      tilepane.getChildren().add(vb);
      
      
      
      //constructor call
      c = new ContraptionZackCanvas("title");
      titlemenu.getChildren().add(c);
      titlemenu.getChildren().add(titlebox);
      //New Stuff
      //Adds canvas to FP
     
   
      //Title Menu Scene
      Scene titlescene = new Scene(titlemenu, 1000 , 1000);
      title.setScene(titlescene);
      title.show();

      //Title Event Handlers
      Newgame.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent begin) -> {
      if (begin.getCode()== KeyCode.ENTER)
      {
            gameStarted=true;
            //If Begin is pressed, start the game fresh
            title.hide();
            c= new ContraptionZackCanvas("0.txt"); 
            count++;
            fp.getChildren().add(c);
            //Create new scene
            Scene freshscene = new Scene(fp, 1000, 1000);
            //Set that scene to the regular level stage and request focus for movement
            stage.setScene(freshscene);
            stage.show();
            c.requestFocus();
           
      }
      
      });
   
      //Title Quit functionality
       Quit.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent Quitgame) -> {
      if (Quitgame.getCode()== KeyCode.ENTER)
      {
          System.exit(0);
      }
      
      });
       
}

 public static void main(String[] args)
   {
      launch(args);
   }  
}