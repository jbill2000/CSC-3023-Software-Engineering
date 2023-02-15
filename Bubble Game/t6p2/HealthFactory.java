public class HealthFactory extends AbstractFactory{
   float healthx;
   float healthy;
   float hp;
   float rad;
   //params for health
   public HealthFactory(float healthx, float healthy, float hp, float rad){
      //Initializing Solely for passing down to Health 
      this.healthx=healthx;
      this.healthy=healthy;
      this.hp=hp;
      this.rad=rad;
   }
   
   public DecorationParent create(float x, float y, float radius,int playerSide){      
   
      //add params
      Health health = new Health(x, y,radius,playerSide,healthx,healthy,hp,rad);
      
      return health;
      
   }

}