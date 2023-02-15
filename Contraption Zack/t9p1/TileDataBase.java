import java.io.*;
import java.util.*;
import javafx.scene.*;
import javafx.scene.paint.Color;

public class TileDataBase {
    
    //this hashmap will hold all the tiles, each by their own character
    HashMap<String, Tile> tiles = new HashMap<String, Tile>();
    double [] xpos = new double [3];
    double [] ypos = new double [3];
    HashMap<String, Spike> spikes = new HashMap<String, Spike>();
    HashMap<String, SquareButton> squarebutton = new HashMap<String, SquareButton>();
    HashMap<String, CircleButton> circlebutton = new HashMap<String, CircleButton>();
    HashMap<String, Walls> walls = new HashMap<String, Walls>();
    HashMap<String, Jukebox> jukebox = new HashMap<String, Jukebox>();
    HashMap<String, Spring> springs = new HashMap<String, Spring>();
    HashMap<String, Arrow> arrows = new HashMap<String, Arrow>();
    HashMap<String, Tool> tools = new HashMap<String, Tool>();
    HashMap<String, Protrusion> protrudes = new HashMap<String, Protrusion>();
    HashMap<String, Doors> doors = new HashMap<String, Doors>();
   
   double SquarexPos;
   double SquareyPos;
   
   double CirclexPos;
   double CircleyPos;
   
   double WallxPos;
   double WallyPos;
   
   double SpringxPos;
   double SpringyPos;
   
   double [] ArrowxPos = new double [3];
   double [] ArrowyPos = new double [3];
   
   double ToolxPos;
   double ToolyPos;
   
   double ProtrusionxPos;
   double ProtrusionyPos;
   
   double DoorxPos;
   double DooryPos;
   
   

    public TileDataBase(String filename){

        try{

        File data = new File(filename);
        Scanner scan = new Scanner(data);
        
        //will keep reading through the file to make sure all the tile data is read
        while(scan.hasNext()){

        String a = scan.next();
        
        if(a.equals("Tile")){ //if we are reading data about a tile

            String c = scan.next(); //this is the character
            //String type= scan.next();
            double col1 = scan.nextDouble(); //this is the color data
            double col2 = scan.nextDouble();
            double col3 = scan.nextDouble();
            int walkable = scan.nextInt(); //this is if it can be walked on

            Color col = new Color(col1,col2,col3, 1.0); //this is the color

            Tile t = new Tile(col,walkable); //creates the tile
            
            t.symbol = c;
            
            tiles.put(c,t); //puts it in the hashmap according to the character key


        }
        if(a.equals("Spike")){ //similar from above, but with spikes

            String c = scan.next();
            //Color Vals
            double col1 = scan.nextDouble();
            double col2 = scan.nextDouble();
            double col3 = scan.nextDouble();
            //Adds to X coord List
            xpos[0] = scan.nextDouble();
            xpos[1] = scan.nextDouble();
            xpos[2] = scan.nextDouble();
            //Adds to Y coord List
            ypos[0] = scan.nextDouble();
            ypos[1] = scan.nextDouble();
            ypos[2] = scan.nextDouble();
            
            //Groupnum, use tbd...
            int groupNum = scan.nextInt();

            int upDown = scan.nextInt();

            Color col = new Color(col1,col2,col3,1.0);
            
            Spike t = new Spike(col,xpos,ypos);
            t.symbol = c;
            
            if(upDown ==1){
              t.spikeUp = true;
            }
            else{
              t.spikeUp = false;
            }
            if(filename.equals("completedtileDatabaseLevel2.txt"))
            {
               t.spikeUp=false;
        
            }

            t.groupAssocation = groupNum;
            
            spikes.put(c,t);


        }
        
        if(a.equals("SquareButton")){ //Like Spikes but squares!
        //Character (key)
        String c= scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //X and Y Positions
        SquarexPos= scan.nextDouble();
        SquareyPos= scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum=scan.nextInt();
        //Color creation
        Color col = new Color(col1,col2,col3,1.0);
        //Make new SB
        SquareButton sb = new SquareButton(col,SquarexPos,SquareyPos);
        //GroupAssoc
        sb.groupAssocation = groupNum;

        int press = scan.nextInt();

        sb.symbol = c;
        if(filename.equals("completedtileDatabaseLevel1.txt"))
        {
            sb.pressed=true;
        
        }

        if(press == 1){
          sb.pressed = false;
        }
        else{
          sb.pressed = true;
        }
        //Put Into HashMap
        squarebutton.put(c,sb);
        
        }
        
        
        if(a.equals("CircleButton")){ //reads in all the needed data for circle button
        //Character (key)
         String c= scan.next();
         //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //X and Y Positions
        CirclexPos= scan.nextDouble();
        CircleyPos= scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum=scan.nextInt();
        //Color Creation
        Color col = new Color(col1,col2,col3,1.0);
        //Circle BUtton creation
        CircleButton cb = new CircleButton(col,CirclexPos,CircleyPos);
        //Group Assoc
        cb.groupAssocation = groupNum;

        double timeDif = scan.nextDouble();

        if(timeDif>1){
          cb.pressed = true;
          cb.time = System.currentTimeMillis() - timeDif;
        }


        cb.symbol = c;
        //Put into Hash Map
        if(filename.equals("completedtileDatabaseLevel2.txt"))
        {
            cb.pressed=true;
        
        }
        circlebutton.put(c,cb);
        }
        
        
        
        
        if(a.equals("Walls")){ //reads in all the needed data for walls
        //Character (key)
        String c= scan.next();
        String type= scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //X and Y Positions
        WallxPos= scan.nextDouble();
        WallyPos= scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum=scan.nextInt();
        //Color creation
        Color col = new Color(col1,col2,col3,1.0);
        //Make new Wall
        Walls w = new Walls(col,WallxPos,WallyPos);
        //GroupAssoc
        w.groupAssocation = groupNum;

        w.symbol = c;
        //Wall Type
        w.type=type;
        //Put Into HashMap
        walls.put(c,w);
        
        }
        
        if(a.equals("Spring")){ //reads in all needed data for springs
        //Character (key)
        String c= scan.next();
        String LorR=scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //X and Y Positions
        SpringxPos= scan.nextDouble();
        SpringyPos= scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum=scan.nextInt();
        //Color creation
        Color col = new Color(col1,col2,col3,1.0);
        //Make new Wall
        Spring s = new Spring(col,SpringxPos,SpringyPos);
        //GroupAssoc
        s.groupAssocation = groupNum;
        s.type=LorR;
        if(filename.equals("completedtileDatabaseLevel1.txt"))
        {
            s.pressed=true;
        
        }

        int press = scan.nextInt();

        if(press == 1){
          s.pressed = false;
        }
        else{
          s.pressed = true;
          if(s.type.equals("left"))
          {
               s.leftcount=1;
          
          }
          if(s.type.equals("right"))
          {
               s.rightcount=1;
          
          }
        }


        s.symbol = c;
        //Put Into HashMap
        springs.put(c,s);
        }
        
        if(a.equals("Arrow")){ //reads in all needed data for arrows
        String c = scan.next();
        String type=scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //Adds to X coord List
        ArrowxPos[0] = scan.nextDouble();
        ArrowxPos[1] = scan.nextDouble();
        ArrowxPos[2] = scan.nextDouble();
        //Adds to Y coord List
        ArrowyPos[0] = scan.nextDouble();
        ArrowyPos[1] = scan.nextDouble();
        ArrowyPos[2] = scan.nextDouble();
       
        //Groupnum, use tbd...
        int groupNum = scan.nextInt();

        Color col = new Color(col1,col2,col3,1.0);
            
        Arrow arr = new Arrow(col,ArrowxPos,ArrowyPos,type);
        arr.groupAssocation = groupNum;
        
        arr.symbol = c;

        arrows.put(c,arr);

        }
        if(a.equals("Tool")){ //reads in all needed data for tools
        String c = scan.next();
        String filepath = scan.next();
        //X coord
        ToolxPos = scan.nextDouble();
        //Y coord 
        ToolyPos = scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum = scan.nextInt();
        Tool t = new Tool(ToolxPos,ToolyPos,filepath);
        t.groupAssocation = groupNum;
        int takenVal = scan.nextInt();
        if(takenVal == 1)
        {
            t.taken=true;
        
        }
        else 
        {
            t.taken=false;
        }
        t.symbol = c;
        if(filename.equals("completedtileDatabaseLevel2.txt"))
        {
            t.taken=true;
        
        }
        
        tools.put(c,t);

        }
        if(a.equals("Protrusion")){ //reads in all needed data for protrusions
        String c = scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //Adds to X coord List
        ProtrusionxPos = scan.nextDouble();
        //Adds to Y coord List
        ProtrusionyPos = scan.nextDouble();
        
        double width = scan.nextDouble();
        
        double height = scan.nextDouble();
       
        //Groupnum, use tbd...
        int groupNum = scan.nextInt();

        Color col = new Color(col1,col2,col3,1.0);
            
        Protrusion p = new Protrusion(col,ProtrusionxPos,ProtrusionyPos, width, height);
        p.groupAssocation = groupNum;
        
        p.symbol = c;

        protrudes.put(c,p);

        }
        
        if(a.equals("Jukebox")){ //reads in all needed data for jukeboxes
          String c = scan.next();
          //Color Vals
          double col1 = scan.nextDouble();
          double col2 = scan.nextDouble();
          double col3 = scan.nextDouble();
          //Adds to X coord List
          ProtrusionxPos = scan.nextDouble();
          //Adds to Y coord List
          ProtrusionyPos = scan.nextDouble();
          
          double width = scan.nextDouble();
          
          double height = scan.nextDouble();
         
          //Groupnum, use tbd...
          int groupNum = scan.nextInt();
  
          Color col = new Color(col1,col2,col3,1.0);
              
         Jukebox j = new Jukebox(col,ProtrusionxPos,ProtrusionyPos, width, height);
          j.groupAssocation = groupNum;
          
          j.symbol = c;

          jukebox.put(c,j);
  
          }
        if(a.equals("Doors")){ //reads in all needed data for doors
        //Character (key)
        String c= scan.next();
        //Color Vals
        double col1 = scan.nextDouble();
        double col2 = scan.nextDouble();
        double col3 = scan.nextDouble();
        //X and Y Positions
        DoorxPos= scan.nextDouble();
        DooryPos= scan.nextDouble();
        //Groupnum, use tbd...
        int groupNum=scan.nextInt();
        //Color creation
        Color col = new Color(col1,col2,col3,1.0);
        //Make new Wall
        Doors d = new Doors(col,DoorxPos,DooryPos);
        //GroupAssoc
        d.groupAssocation = groupNum;

        d.symbol = c;

        double startingTime = scan.nextDouble();
        d.time +=startingTime;
        //Put Into HashMap
        doors.put(c,d);
        }
        


      }

        }
        catch(FileNotFoundException fe){

            System.out.println("oops, database not found");

        }

    }

    //returns a tile based on a string, from the hashmap
    public Tile getTile(String str){

        return (Tile) tiles.get(str);

    }
    //Returns A Spike from the HashMap
    public Spike getSpike(String str){
      return (Spike) spikes.get(str);
    }
    //Returns A Square from the Hashmap
    public SquareButton getSquare(String str){
      return (SquareButton) squarebutton.get(str);
    }
    //Returns A Circle from the Hashmap
    public CircleButton getCircle(String str){
      return (CircleButton) circlebutton.get(str);
    }
    
    //Returns a Wall from the HashMap
    public Walls getWalls(String str){
      return (Walls) walls.get(str);
    }
    
    //Returns a Spring from the HashMap
    public Spring getSprings(String str){
      return (Spring) springs.get(str);
    }
    //JukeBox
    public Jukebox getJukebox(String str){
      return (Jukebox) jukebox.get(str);
    }
    
    //Returns a Arrow from the HashMap
    public Arrow getArrows(String str){
      return (Arrow) arrows.get(str);
    }
    
    //Returns a Tool from the HashMap
    public Tool getTools(String str){
      return (Tool) tools.get(str);
    }
    
    //returns protrusion from hashmap
    public Protrusion getProtrudes(String str){
      return (Protrusion) protrudes.get(str);
    }
  
    //returns door from hashmap
    public Doors getDoors(String str){
      return (Doors) doors.get(str);
    }


}
