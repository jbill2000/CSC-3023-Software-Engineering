
////Code by Dr.Mood that I referenced and used for lab 7 part 3. But he coded it for part 2. I DID NOT MODIFY THIS CODE 
import java.util.*;

import javafx.scene.paint.Color;

import java.io.*;

public class ContraptionZackLevel {

   Tile[][] data; //2d array of tiles

   String[] directions = new String[4]; //this stores the connecting level files
   int rows; //rows and columns of the 2d array
   int columns;
   double playerstartx; //player's starting x and y
   double playerstarty;
   String spikecheck = "";
   String buttoncheck = "";

   TileDataBase tbd;
   String tiledbfile = "";

   //creates a lot of 2d arrays to hold all the level components
   ArrayList<Spike> spikes = new ArrayList<Spike>();
   ArrayList<Tile> tileVals = new ArrayList<Tile>();
   Water water = new Water(450, 150, 100, 700);
   ArrayList<SquareButton> squares = new ArrayList<SquareButton>();
   ArrayList<CircleButton> circles = new ArrayList<CircleButton>();
   ArrayList<Jukebox> jukebox = new ArrayList<Jukebox>();
   ArrayList<Walls> walls = new ArrayList<Walls>();
   ArrayList<Arrow> arrows = new ArrayList<Arrow>();
   ArrayList<Spring> springs = new ArrayList<Spring>();
   ArrayList<Tool> tools = new ArrayList<Tool>();
   ArrayList<Protrusion> protrudes = new ArrayList<Protrusion>();
   ArrayList<Doors> doors = new ArrayList<Doors>();
   
   public String name;
   int levelNum;
   String levelName;

   boolean success = true; // this tracks whether the level was loaded correctly or not

   public ContraptionZackLevel(String filename) {

      name = filename;
      
      try {
         Scanner scan = new Scanner(new File(filename));
         levelName = scan.next();
         levelNum = scan.nextInt();
   
         tiledbfile = levelName + "database.txt";
         
         // uses the set tiledb

         if (filename.equals("1complete.txt")) {
            tiledbfile = "completedtileDatabaseLevel1.txt";

         } else if (filename.equals("2complete.txt")) {
            tiledbfile = "completedtileDatabaseLevel2.txt";

         }

         //loads a tile database
         tbd = new TileDataBase(tiledbfile);

         rows = scan.nextInt(); //gets rows and columns
         columns = scan.nextInt();
         
         playerstartx = scan.nextDouble(); //gets starting x and y
         playerstarty = scan.nextDouble();
         
         data = new Tile[rows][columns]; //sets the 2d array

         //fills the 2d array with tiles based on what is read from file
         for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

               String val = scan.next();

               boolean insert = true;

               for (int z = 0; z < tileVals.size(); z++) {
                  if (tileVals.get(z).symbol.equals(val)) {
                     insert = false;
                  }

               }

               if (insert) {
                  Tile t = tbd.getTile(val);
                  t.symbol = tbd.getTile(val).symbol;
                  tileVals.add(tbd.getTile(val));
               }

               data[i][j] = new Tile(tbd.getTile(val));
               data[i][j].symbol = tbd.getTile(val).symbol;

            }
         }

         //loads the four directional files
         directions[0] = scan.next();
         directions[1] = scan.next();
         directions[2] = scan.next();
         directions[3] = scan.next();

         //scans each string after and adds to the respective arraylist
         while (scan.hasNext()) {
            spikecheck = scan.next();

            if (spikecheck.equals("os1") || spikecheck.equals("os2") || spikecheck.equals("os3")
                  || spikecheck.equals("os4") || spikecheck.equals("os5") || spikecheck.equals("os6")
                  || spikecheck.equals("os7") || spikecheck.equals("os8") || spikecheck.equals("os9")) {
               // Adds a spike to the Arraylist of spikes.
               spikes.add(tbd.getSpike(spikecheck));

            }
            if (spikecheck.equals("bs1") || spikecheck.equals("bs2") || spikecheck.equals("bs3")
                  || spikecheck.equals("bs4") || spikecheck.equals("bs5") || spikecheck.equals("bs6")
                  || spikecheck.equals("bs7") || spikecheck.equals("bs8") || spikecheck.equals("bs9")
                  || spikecheck.equals("bs10") || spikecheck.equals("bs11") || spikecheck.equals("bs12")
                  || spikecheck.equals("bs13") || spikecheck.equals("bs14") || spikecheck.equals("bs15")) {
               // Adds a spike to the Arraylist of spikes.
               spikes.add(tbd.getSpike(spikecheck));
            }
            if (spikecheck.equals("ps1") || spikecheck.equals("ps2") || spikecheck.equals("ps3")
                  || spikecheck.equals("ps4") || spikecheck.equals("ps5") || spikecheck.equals("ps6")) {
               // Adds a spike to the Arraylist of spikes.
               spikes.add(tbd.getSpike(spikecheck));
            }
            if (spikecheck.equals("ys1") || spikecheck.equals("ys2") || spikecheck.equals("ys3")
                  || spikecheck.equals("ys4") || spikecheck.equals("ys5") || spikecheck.equals("ys6")) {
               // Adds a spike to the Arraylist of spikes.
               spikes.add(tbd.getSpike(spikecheck));
            }
            if (spikecheck.equals("gs1") || spikecheck.equals("gs2") || spikecheck.equals("gs3")) {

               spikes.add(tbd.getSpike(spikecheck));
            }

            
            if (spikecheck.equals("osb1") || spikecheck.equals("osb2") || spikecheck.equals("bsb1")
                  || spikecheck.equals("bsb2") || spikecheck.equals("psb1") || spikecheck.equals("psb2")
                  || spikecheck.equals("ysb1") || spikecheck.equals("ysb2")) {
               

               // Adds a square button
               squares.add(tbd.getSquare(spikecheck));

            }

            if (spikecheck.equals("ocb1") || spikecheck.equals("ocb2") || spikecheck.equals("gcb1")
                  || spikecheck.equals("gcb2") || spikecheck.equals("bcb1") || spikecheck.equals("bcb2")
                  || spikecheck.equals("pcb1") || spikecheck.equals("pcb2") || spikecheck.equals("ycb1")
                  || spikecheck.equals("ycb2")) {
               

               // adds a circle button  
               circles.add(tbd.getCircle(spikecheck));

            }

            if (spikecheck.equals("w1") || spikecheck.equals("w2") || spikecheck.equals("w3") || spikecheck.equals("w4")
                  || spikecheck.equals("w5") || spikecheck.equals("w6") || spikecheck.equals("w7")
                  || spikecheck.equals("w8") || spikecheck.equals("w9") || spikecheck.equals("w10")
                  || spikecheck.equals("w11") || spikecheck.equals("w12") || spikecheck.equals("w13")
                  || spikecheck.equals("w14") || spikecheck.equals("w15") || spikecheck.equals("w16")
                  || spikecheck.equals("w17") || spikecheck.equals("w18") || spikecheck.equals("w19")
                  || spikecheck.equals("w20") || spikecheck.equals("w21") || spikecheck.equals("w22")
                  || spikecheck.equals("w23") || spikecheck.equals("w24") || spikecheck.equals("w25")
                  || spikecheck.equals("w26")
                  || spikecheck.equals("w27") || spikecheck.equals("w28") || spikecheck.equals("w29")) {
               

               // Adds a wall
               walls.add(tbd.getWalls(spikecheck));

            }
            if (spikecheck.equals("arrow") || spikecheck.equals("al") || spikecheck.equals("ar")
                  || spikecheck.equals("ad")) {

               //adds an arrow
               arrows.add(tbd.getArrows(spikecheck));

            }
            if (spikecheck.equals("tool")) {

               //adds a tool
               tools.add(tbd.getTools(spikecheck));
            }
            if (spikecheck.equals("j")) {

               //adds a jukebox
               jukebox.add(tbd.getJukebox(spikecheck));
            }

            if (spikecheck.equals("spring1") || spikecheck.equals("spring2")) {

               //adds a spring
               springs.add(tbd.getSprings(spikecheck));

            }

            if (spikecheck.equals("p") || spikecheck.equals("p2") || spikecheck.equals("p3")) {
              
               //adds a protrusion
               protrudes.add(tbd.getProtrudes(spikecheck));
            }

            if (spikecheck.equals("door") || spikecheck.equals("door2") || spikecheck.equals("door3")
                  || spikecheck.equals("door4")) {
               
               //adds a door
               doors.add(tbd.getDoors(spikecheck));
            }

         }

      } catch (FileNotFoundException e) {

         success = false; // if it was not loaded correctly, we will set the value to false

      }
      
   }

   //accessors for data
   public int getRows() {
      return rows;

   }

   public int getLevelNum() {
      return levelNum;
   }

   public int getColumns() {
      return columns;

   }

   public double getPlayerstartx() {
      return playerstartx;
   }

   public double getPlayerstarty() {

      return playerstarty;
   }

   //returns a specific tile
   public Tile getData(int i, int j) {
      return data[j][i];
   }

   // Protrusion data methods
   public Protrusion getProtrusions(int index) {
      return protrudes.get(index);

   }

   public int protrudesSize() {

      return protrudes.size();
   }

   public boolean protrudesEmpty() {
      if (protrudes.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Tools data methods
   public Tool getTools(int index) {
      return tools.get(index);

   }

   public int toolsSize() {

      return tools.size();
   }

   public boolean toolsEmpty() {
      if (tools.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   //jukebox data methods
   public Jukebox getJukebox(int index) {
      return jukebox.get(index);

   }

   public int jukeboxSize() {

      return jukebox.size();
   }

   public boolean jukeboxEmpty() {
      if (jukebox.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Spring Methods
   public Spring getSprings(int index) {
      return springs.get(index);

   }

   public int springsSize() {

      return springs.size();
   }

   public boolean springsEmpty() {
      if (springs.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Arrow Methods
   public Arrow getArrows(int index) {
      return arrows.get(index);

   }

   public int arrowsSize() {

      return arrows.size();
   }

   public boolean arrowsEmpty() {
      if (arrows.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Wall Methods

   public Walls getWalls(int index) {
      return walls.get(index);
   }

   public int wallsSize() {

      return walls.size();
   }

   public boolean wallsEmpty() {
      if (walls.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Square Button Methods
   public SquareButton getSquareButtons(int index) {
      return squares.get(index);

   }

   public int squaresSize() {

      return squares.size();
   }

   public boolean squareEmpty() {
      if (squares.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Circle Methods
   public CircleButton getCircleButtons(int index) {
      return circles.get(index);

   }

   public int circlesSize() {

      return circles.size();
   }

   public boolean circleEmpty() {
      if (circles.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   // Spike Methods
   public Spike getSpike(int index) {

      return spikes.get(index);
   }

   public boolean spikesempty() {
      if (spikes.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }
   public int spikesize() {

      return spikes.size();
   }

   //door data methods
   public Doors getDoors(int index) {

      return doors.get(index);
   }

   public boolean doorsempty() {
      if (doors.isEmpty() == true) {
         return true;

      } else {
         return false;

      }

   }

   public int doorsize() {

      return doors.size();
   }

   // Get next file name
   public String getNextFileName(int direction) {
      return directions[direction];
   }
}