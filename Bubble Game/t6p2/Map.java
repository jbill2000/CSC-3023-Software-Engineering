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

//I'm not expecting you to modify this file in any shape or form since it literally is just drawing the background. Hence, little comments.

public class Map
{
   ArrayList<Obstacle> obs = new ArrayList<Obstacle>();
   //ArrayLists to store data, Attributes is temporary as we have to scan data below it so we need to store the data somewhere. 
   ArrayList<UnitFactory> unitFactories = new ArrayList<UnitFactory>();
   ArrayList<AbstractUnit> decorations;
   ArrayList<String> Attributes = new ArrayList<String>();
   ArrayList<AbstractUnit[]> completeUnits= new ArrayList<AbstractUnit[]>();
   ArrayList<Integer> decoSizeArray = new ArrayList<Integer>();
   
   //Array for later
   AbstractUnit[] theUnitArray;
   AbstractUnit next;
   int xSize;
   int ySize;
   
   //this method takes the filescanner and reads ONLY the map parts. does not read the unit parts. Those up to you!
   public void readMap(Scanner fileScanner) throws Exception
   {
      xSize = fileScanner.nextInt();
      ySize = fileScanner.nextInt();
      
      int amountObTypes = fileScanner.nextInt();
      
      HashMap<String,Obstacle> theMap = new HashMap<String,Obstacle>();
      
      for(int i=0; i<amountObTypes; i++)
      {
         theMap.put(fileScanner.next(),new Obstacle(0,0,fileScanner.nextFloat()));
      }
      
      int numObjects = fileScanner.nextInt();
      for(int i = 0; i < numObjects;i++)
      {
         obs.add(theMap.get(fileScanner.next()).clone(fileScanner.nextInt(),fileScanner.nextInt()));
      } 
      
      //luke code #2 -----------------------------------------------------------------------
      
      int numberOfTypes = fileScanner.nextInt();
      
       //Variables :)
       String name= "";
       float xposition=0;
       float yposition=0;
       int playernum=0;
       int loopcount=0;
       int numberOfDecorators =0;
       float speed=0;
       float radius=0;
       String type="";
    while(loopcount<numberOfTypes){
      //iterate over number of types
      //for(int i = 0; i < numberOfTypes; i++){
         //Variables being read in
         type = fileScanner.next();
         radius = fileScanner.nextFloat();
         speed = fileScanner.nextFloat();
         numberOfDecorators = fileScanner.nextInt();
         decoSizeArray.add(numberOfDecorators);
         //Things I need for singleton :)
         UnitFactory uf;
         boolean matched=false;
         /* uf= new UnitFactory(type, radius, speed, numberOfDecorators);
          unitFactories.add(uf);*/
          //If there is 1 or more UF's in list
         if(unitFactories.size() >= 1)
         {
            //Loop through each and see if the name being read in matches any already established uf names
            for(int u=0; u<unitFactories.size(); u++)
            {
               String ufnameheld="";
               if(unitFactories.size() > 1)
               {
                  ufnameheld=unitFactories.get(u).getName();
               }
               else
               {
                  ufnameheld=unitFactories.get(u).getName();
               }
               
               //Debugs for Singleton
               
               //System.out.println("In Unit Factory "+u+"the name of the read in unit is "+ufnameheld);
              // System.out.println("The Name being read in at this moment is "+type);
               
               //If a match is found, set matched == true
               if(type.equals(ufnameheld))
               {
                    //DO NOT ADD THE UF AKA SINGLETON PATTERN IMPLEMENTATION.
                  matched=true;
               }
               
               
               
            }
           //If a match is not found by the end, create a new unitfactory and add it to the list
           if(matched==false)
           {
             uf= new UnitFactory(type, radius, speed, numberOfDecorators);
             unitFactories.add(uf);
           }
            
         
         }
         //This else is only when there is no already added Unitfactory, to prevent errors, since we couldnt do singleton as there would be nothing to compare to!
         else 
         {
           
           uf= new UnitFactory(type, radius, speed, numberOfDecorators);
             unitFactories.add(uf);
            
         }
         
        /* uf= new UnitFactory(type, radius, speed, numberOfDecorators);
         System.out.println("adding a new unitF");
         unitFactories.add(uf);*/
            
         
         

    
         //System.out.println(type+ " " +radius+ " " +speed+ " " +numberOfDecorators);
         
         
         
         for(int j=0; j<=numberOfDecorators; j++)
         {
            String tba = fileScanner.nextLine();
            if(tba.contains("health"))
            {
               Attributes.add(tba);
            } 
            else if(tba.contains("bubble"))
            {
              Attributes.add(tba);
            }
            //System.out.println(tba);
            
         
        }

         String spacetrash= fileScanner.nextLine();
              loopcount++;
         
      }
         
     
      
         
        
         //read unit non static data for unit constructor
      int numberOfUnits = fileScanner.nextInt();
       
      
        //Variables for counting attributes, very important!
        int start=0;
        int bound=0;
        //System.out.println(numberOfUnits);
        
   //adding stuff here
   
      //System.out.println(unitFactories.size());
       // int counter = -1;
        
        for(int k=0; k<numberOfTypes; k++){
           //Variables :)
           ArrayList<AbstractUnit> decorations = new ArrayList<AbstractUnit>();
           name = fileScanner.next();
           xposition = fileScanner.nextFloat();
           yposition= fileScanner.nextFloat();
           playernum = fileScanner.nextInt();
           //Boolean for matching to avoid errors
           boolean matched=false;
           bound=decoSizeArray.get(k);
           /*if(numberOfUnits > 1)
           {
               
               bound = decoSizeArray.get(counter);
               counter++;
           }
           else
           {
            bound=decoSizeArray.get(0);
            counter++;
           }*/
           
           
           int factorycheck=0;
           //int to get the right index to match
           int indexhold=0;
           
                      
           decorations.add(unitFactories.get(k).create(xposition,yposition,unitFactories.get(k).getRadius(),playernum));

           for(int l=start; l<bound+start; l++)
           {
               Scanner Attscan= new Scanner(Attributes.get(l));
            //System.out.println("Attributes of L is "+Attributes.get(l));         
            //Gets health or bub
            
            String AttType= Attscan.next();
            
            if(AttType.contains("health"))
            {
               HealthFactory healthFact;
               float healthx = Attscan.nextFloat();
               float healthy= Attscan.nextFloat();
               float hp = Attscan.nextFloat();
               float rad= Attscan.nextFloat();
               healthFact= unitFactories.get(k).createHealth(healthx,healthy,hp,rad);
               decorations.add(healthFact.create(xposition,yposition,unitFactories.get(k).getRadius(),playernum));

              // }
               //create HealthFactory
               
               
               
               //calls create and creates Health
               
               
               
               
            }
            else if(AttType.contains("bubble"))
            {
               
               float bubblex,bubbley,size,damage,bubspeed,refire,bubblesize;
               int projectiletype,range;
               
               bubblex=Attscan.nextFloat();
               bubbley=Attscan.nextFloat();
               projectiletype=Attscan.nextInt();
               size=Attscan.nextFloat();
               damage= Attscan.nextFloat();
               bubspeed= Attscan.nextFloat();
               refire= Attscan.nextFloat();
               range= Attscan.nextInt();
               bubblesize=Attscan.nextFloat();
               
                                  
               BubbleFactory bubbleFact = unitFactories.get(k).createBubble(bubblex,bubbley,projectiletype, size, damage, bubspeed, refire, range, bubblesize);
               decorations.add(bubbleFact.create(xposition,yposition,unitFactories.get(k).getRadius(),playernum));
               


           }
        }
        //Calls assemble
        theUnitArray = new AbstractUnit[numberOfDecorators+1];
        theUnitArray= unitFactories.get(k).assembleUnit(decorations);

      
        //Sets start to keep track
        start=start+bound;
        //Adds the completed Array unit set to a list of abstractUnit []
        //System.out.println("added a unit in the type area");
        completeUnits.add(theUnitArray);
        
      
   }
   
      //System.out.println("Made it past the Types");
      //ESSENTIALLY A CLONE OF THE ABOVE WITH SOME MORE LOOPING YAYYYYYYYYYYY..... :(
        boolean full=false;
        
        //New stuff that might break things
      if(completeUnits.size() >= 1 && numberOfUnits > numberOfTypes)
      {
         int tracker = numberOfTypes;
         //System.out.println("Number of Types is "+numberOfTypes);
         //System.out.println("Number of Units is "+numberOfUnits);
         while(full==false)
         {
            //System.out.println("Number of Types is "+tracker);
         //System.out.println("Number of Units is "+numberOfUnits);
            //Reads in the next player
            
            name= fileScanner.next();
            xposition= fileScanner.nextFloat();
            yposition= fileScanner.nextFloat();
            playernum=fileScanner.nextInt();
            
            //Variables for determining fun stuff :).
            boolean matched=false;
            int indexhold=0;
            //Temp ArrayList for holding things
            ArrayList<AbstractUnit> temp = new ArrayList<AbstractUnit>();
            //Will Create the unit with the respective factory
            for(int j=0; j<unitFactories.size(); j++)
            {
               
                //This will help with Singleton and errors, indexhold will be used to ensure we use the right unit factory for the unit type ala singleton pattern.
               if(unitFactories.get(j).getName().equals(name))
              {
                  matched=true;
                  indexhold=j;
                  break;
               }
            
            }
            //If the names match up, to again prevent errors later and for singleton purposes add it to deco
            if(matched==true)
            {
               
               //Fetches Radius for use later
               radius= unitFactories.get(indexhold).getRadius();
               //System.out.println("Radius outside type definitions for type "+name+" is"+radius);
               //Adds the new unit to a temp AL, so it doesnt mess with the existing decorations :)
               temp.add(unitFactories.get(indexhold).create(xposition,yposition,radius,playernum));
            }
            
            //Now things get Tricky
            
            //Need to Extract the Unit from Completed Units with the matching name
            int initialcompletedunitssize= completeUnits.size();
            for(int i=0; i<initialcompletedunitssize; i++)
            {
               AbstractUnit[] arr = completeUnits.get(i);
                  //If the Units name equals the name of the unit we are looking at.
                  if(arr[arr.length-1].getName().equals(name))
                  {
                  
              //nested loop for looping through the array
                  //Since unit is at the back of the array, We want that to be the first check, hence the loop needs to be in reverse.
                  
                    //Start at length -2 since we already create the unit, we just want the decorations.
                  for(int k=arr.length-2; k>-1; k--)
                  {
                     //yay Continue with copying.
                     
                     if(arr[k] instanceof Health)
                     {
                        Health h= (Health)arr[k];
                        float healthx= h.getX();
                        float healthy= h.getY();
                        float hp= h.gethp();
                        float rad = h.getRad();
                        /*System.out.println("Healthx is "+h.getX());
                        System.out.println("Healthy is "+h.getY());
                        System.out.println("HP is "+h.gethp());
                        System.out.println("Rad is "+h.getRad());*/
                        
                        HealthFactory tempFact= unitFactories.get(indexhold).createHealth(healthx,healthy,hp,rad);
                        temp.add(tempFact.create(xposition,yposition,radius,playernum));
                        
                       // temp.add(healthtba);
                     
                        //System.out.println("Health");
                     }
                     else if(arr[k] instanceof Bubble)
                     {
                         //System.out.println("Bubble");
                         Bubble b = (Bubble) arr[k];
                         float bubblex,bubbley,size,damage,bubspeed,refire,bubblesize;
                           int projectiletype,range;
                           
                           bubblex=b.getX();
                           bubbley=b.getY();
                           projectiletype=b.getProjectileType();
                           size=b.getSize();
                           damage= b.getDamage();
                           bubspeed= b.getSpeed();
                           refire= b.getRefire();
                           range= b.getRange();
                           bubblesize=b.getBubbleSize();
                           
                           
                         BubbleFactory tempBFact= unitFactories.get(indexhold).createBubble(bubblex,bubbley,projectiletype, size, damage, bubspeed, refire, range, bubblesize);
                         temp.add(tempBFact.create(xposition,yposition,radius,playernum));

                     
                     }
                  
                 
                  }
                  //System.out.println(temp);
                  //Almost there!!!!!
                  
                  //Now that the bubble and health decos are done, we bring it all together like above and add it to complete unit
                     
                     theUnitArray = new AbstractUnit[unitFactories.get(indexhold).getDecoratorNum()+1];
                     //Need this comparison for singleton! (So it uses the right factory)
                       
                     theUnitArray= unitFactories.get(indexhold).assembleUnit(temp);
                     for(int q=0; q<theUnitArray.length; q++)
                     {
                        System.out.println(theUnitArray[q]);
                     }
                       //Adds the completed Array unit set to a list of abstractUnit []
                     completeUnits.add(theUnitArray);
                     
                     //System.out.println("Added to completed units");

                      tracker++;
                     //Boolean to end the loop if true
                     if(tracker == numberOfUnits)
                      {
                        full=true;
                       // break;
            
                     }
                  
                  
                  }
               
               }
         }    
     }   
   }

   boolean first=true;
   
   //have to get the size for the Game class.
   public int getXSize(){return xSize;}
   public int getYSize(){return ySize;}
   
   final int SQAURESIZE = 50;
   
   //drawing the map and obstacles
   public void draw(GraphicsContext gc)
   {

   
      for(int i=0;i<xSize;i+=SQAURESIZE)
      {
         for(int j=0;j<ySize;j+=SQAURESIZE)
         {
            gc.setStroke(Color.GRAY);
            gc.strokeRect(i,j,SQAURESIZE,SQAURESIZE);
         }
      }
   
      for(int i=0;i<obs.size();i++)
      {
         obs.get(i).draw(gc);
      }
      

   }
   
   //getters
   public ArrayList<UnitFactory> getUnitFactories(){
      return unitFactories;
   }
   public ArrayList<AbstractUnit> getUnitDecos(){ //i hate the "non-static variable decorations cannot be referenced from a static context" error
      return decorations;
   }
   //Might not need? Keep for now
   public AbstractUnit[] getArray()
   {
   
      return theUnitArray;
   }
   //Need to access the data in Game :)
   public ArrayList<AbstractUnit[]> getCompleteUnits()
   {
   
      return completeUnits;
   }
   
   
}