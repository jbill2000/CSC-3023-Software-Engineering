import java.util.*;

//each projectile gets its own class because each one is slightly different
//projectile 1 is actually like 5 normal projectiles 
public class projectile1 extends projectile
{
   float speed, size, x, y, lifetime, damage;
   int projectileType = 1;
   ArrayList<projectile> proj1 = new ArrayList<projectile>();
   
   public projectile1(float x, float y, float diffx, float diffy, float speed, float size, int projectileType, float playerSide, float damage, float lifetime){
      super(x, y, diffx, diffy, speed, size, projectileType, playerSide, damage, lifetime);
      this.projectileType = projectileType;
      //this.playerSide=playerSide;
      //projectile type one is a list with 5 basic projectiles (projectile0)
      float initialSpeed = speed;
      float initialX = x;
      float initialY = y;
      float newlife = 5;
      
      //System.out.println("projectile 1");
   }
   
   public ArrayList<projectile> getProj1()
   {
      return proj1;
   }

}