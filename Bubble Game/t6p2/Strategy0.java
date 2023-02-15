import java.util.*;

public class Strategy0{

   float speed, refire, x, y;
   projectile hit = null;
   //Boolean to ensure we only hit one decorator at a time.
   boolean damagecheck=false;
   AbstractUnit[] unit;
   public Strategy0(float speed, float refire)
   {
      this.speed = speed;
      this.refire = refire;
   }
   
   public ArrayList<projectile> execute(ArrayList<projectile> proj, ArrayList<AbstractUnit[]> Units) //deals with movement of the projectiles
   {
      //catch if null
      if(proj.size() > 0){
         
         //iter through projectiles and move thems
         for(int i = 0; i < proj.size(); i++){
                        
            x = proj.get(i).getX();
            y = proj.get(i).getY();
           
            proj.get(i).setX(x + (speed * proj.get(i).getDiffx()));
            proj.get(i).setY(y + (speed * proj.get(i).getDiffy()));
            
            proj.get(i).decreaseLifetime();
            
            if(proj.get(i).getLifetime() <= 0)
            {
            
               proj.remove(i);
                  
                  
            }

            
         }
                  
         
          for(int j = 0; j < Units.size(); j++){
            for(int i = 0; i < proj.size(); i++){
                unit = Units.get(j);
              
              
               
               hit = Physics.overlapping(((Unit) unit[unit.length-1]), proj);
              
               if(hit != null){
               
            //HERE
            
            
                  //Damages the left side first, Keep if we want that to happen :) Otherwise use below code.
                  for(int k = 0; k < unit.length-1; k++){
                     //Will Only Damage one Health decorator at a time :), works for things with Different Health vals too :)
                     if(unit[k] instanceof Health && damagecheck==false && ((Health) unit[k]).getCurrentHealth()>0){
                        ((Health) unit[k]).KILL(hit.getDamage());
                        damagecheck=true;
                        
                     }
                     
                  }
                  //Resets Damage check post loop.
                  damagecheck=false;
                  
                 if(proj.get(i) == hit)
                  {
                     proj.remove(i);
                  
                  } 
               }
            }
         }

         
         
      }
      return proj;
   }
   
}