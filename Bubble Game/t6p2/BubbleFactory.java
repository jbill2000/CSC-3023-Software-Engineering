public class BubbleFactory extends AbstractFactory{
   //Variables that gotta be initialized for passing down????
   float bubblex, bubbley, size, damage, speed, refire, bubblesize;
   int projectiletype, range;
   public BubbleFactory(float bubblex, float bubbley, int projectiletype, float size, float damage, float speed, float refire, int range, float bubblesize){
      //Initializing for passing down?
      this.bubblex=bubblex;
      this.bubbley=bubbley;
      this.projectiletype=projectiletype;
      this.size=size;
      this.damage=damage;
      this.speed=speed;
      this.refire=refire;
      this.range=range;
      this.bubblesize=bubblesize;
   }
   
   public DecorationParent create(float x, float y,float radius,int playerSide){
      
      //add params
      Bubble bubble = new Bubble(x,y,radius,playerSide,bubblex,bubbley,projectiletype, size, damage, speed, refire, range, bubblesize);
      
      return bubble;
      
   }
}