import java.util.*;

//each projectile gets its own class because each one is slightly different
//projectile 2 is like 8 normal projectiles
public class projectile2 extends projectile
{
   float speed, size, x, y, lifetime, damage;
   int projectileType = 1;
   ArrayList<projectile> proj2 = new ArrayList<projectile>();
   
   public projectile2(float x, float y, float diffx, float diffy, float speed, float size, int projectileType, float playerSide, float damage, float lifetime){
      super(x, y, diffx, diffy,speed, size, projectileType, playerSide, damage, lifetime);
      this.projectileType = projectileType;
      
      //System.out.println("projectile 2");
   }


}