import java.util.*;

//each projectile gets its own class because each one is slightly different
//projectile 0 is a normal projectile
public class projectile0 extends projectile
{
   float speed, size, x, y, lifetime, damage;
   int projectileType = 0;
   
   public projectile0(float x, float y, float diffx, float diffy, float speed, float size, int projectileType, float playerSide, float damage, float lifetime){
      super(x, y, diffx, diffy, speed, size, projectileType, playerSide, damage, lifetime);
      this.projectileType = projectileType;
   }
   
   
}