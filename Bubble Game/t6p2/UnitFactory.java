import java.util.*;
public class UnitFactory extends AbstractFactory{
   
   HealthFactory healthFact;
   BubbleFactory bubbleFact;
   String name;
   float radius;
   float speed;
   int numberOfDecorators;
  
   public UnitFactory(String name, float radius, float speed, int numberOfDecorators){
      this.name = name;
      this.radius = radius;
      this.speed = speed;
      this.numberOfDecorators = numberOfDecorators;
   }
   //Accessors for Singleton purposes :( (*cry*)
   public float getRadius()
   {
      return radius;
   }
   
   public String getName()
   {
   
      return name;
   }
   
   //abstract create for recursion
      //creates the unit for the decorations array
   public AbstractUnit create(float x, float y, float radius,int playerSide){
   
      Unit unit = new Unit(x,y,playerSide,radius,speed,name);
      
     
      
      return unit;
   
   }
   
   //creates health factory
   public HealthFactory createHealth(float healthx, float healthy, float hp, float rad){
      
      healthFact = new HealthFactory(healthx,healthy,hp,rad);
            
      return healthFact;
   }
   
   //creates bubble factory
   public BubbleFactory createBubble(float bubblex, float bubbley, int projectiletype, float size, float damage, float speed, float refire, int range, float bubblesize){
      
      bubbleFact = new BubbleFactory(bubblex,bubbley,projectiletype, size, damage, speed, refire, range, bubblesize);
            
      return bubbleFact;
   }
   //Returns the number of decorations
   public int getDecoratorNum()
   {
   
      return numberOfDecorators;
   }

   
   public AbstractUnit[] assembleUnit(ArrayList<AbstractUnit> input){
      
        
      //parts will be added to the array as we come to them
      //define an array for the unit + decorations using this.numberOfDecorators + 1
      AbstractUnit[] Unit = new AbstractUnit[this.numberOfDecorators+1];
      int counter=0;
      //System.out.println("The size of the decorations is "+input.size());
      //loop over the decorations array and put the stuff from there into the AbstractUnit[]
      
      //this works (probably)
      for(int i=Unit.length-1; i>-1; i--)
      {
         if(i>0)
         {
            Unit[counter] = input.get(i);
            Unit[counter+1] = input.get(i-1);
            
            //System.out.println(Unit[i);
            Unit[counter].setNext(input.get(i-1));
            
            if(i>1 && counter<Unit.length-1)
            {
               Unit[counter+1].setNext(input.get(i-2));
            }
            
            counter++; 
         }
      }
      
      
      //return the array
      return Unit;
   }
}