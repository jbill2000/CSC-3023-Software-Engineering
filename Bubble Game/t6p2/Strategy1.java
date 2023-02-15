import java.util.*;

public class Strategy1{

   float speed, refire, x, y;
   projectile hit = null;
   //Boolean to ensure we only hit one decorator at a time.
   boolean damagecheck=false;
   AbstractUnit[] unit;
   boolean create = false;
   
   public Strategy1(float speed, float refire)
   {
      this.speed = speed;
      this.refire = refire;
   
   }
   
   
//Need to mess with this to figure out why the projectiles are going hypersonic.
//You might want to try messing around with projectile 1 as well, as the problem may be there.
   public ArrayList<projectile> execute(ArrayList<projectile> proj, ArrayList<AbstractUnit[]> Units) //deals with movement of the projectiles
   {
      //System.out.println("executing strat 1!");
      //catch if null
      
      float initialSpeed = speed*(float).1;
      
      if(proj.size() > 0){
                 
         //iter through projectiles and move thems
         for(int i = 0; i < proj.size(); i++){
                        
            x = proj.get(i).getX();
            y = proj.get(i).getY();
            
            if (proj.get(i) instanceof projectile1)
            {
               proj.get(i).setX(x + (speed * proj.get(i).getDiffx()));
               proj.get(i).setY(y + (speed * proj.get(i).getDiffy()));
            }
            else if (proj.get(i) instanceof projectile0)
            {
               proj.get(i).setX(x + (initialSpeed * proj.get(i).getDiffx()));
               proj.get(i).setY(y + (initialSpeed * proj.get(i).getDiffy()));
            }
            
         }
         
         
         
         for(int i = 0; i < proj.size(); i++){
            proj.get(i).decreaseLifetime();
            if(proj.get(i) instanceof projectile1)
               {
                  
                  
                  proj.get(i).decreaseLifetime();
                  proj.get(i).decreaseLifetime();
                  //tempProj=((projectile1)proj.get(i)).getProj1();
                  if(proj.get(i).getLifetime() <= 1 && create == false)
                  {
            //here
                     //"create" 5 bubbles
                     for(int p=0; p<5; p++)
                     {
                           //need some calculations for x, y, and speed in here
                              //horrifying
                        //float diffx = proj.get(i).getDiffx()+(float)Math.floor(Math.random()*(.05-(-.05)+1)+(-.05));
                        //float diffy = proj.get(i).getDiffy()+(float)Math.floor(Math.random()*(.05-(-.05)+1)+(-.05));
                        
                        float diffx = proj.get(i).getDiffx()+(float)(-0.05 + Math.random() * (0.05 + 0.05));
                        float diffy = proj.get(i).getDiffy()+(float)(-0.05 + Math.random() * (0.05 + 0.05));

                        //System.out.println(speed);
                        //float initialSpeed = speed*(float).1;
                        //System.out.println(initialSpeed);
                        
                        //System.out.println(initialSpeed+ " " +initialX+ " " +initialY);
                        //System.out.println("In Proj 1, getSide() returns "+getSide());
                        proj.add(new projectile0(proj.get(i).getX(), proj.get(i).getY(), diffx, diffy, initialSpeed, proj.get(i).getRadius(), proj.get(i).getPType(), proj.get(i).getSide(), proj.get(i).getDamage(), (float)5));
                        //proj.add(tempProj.get(p));
                     }
                     create = true;
                    // System.out.println("creating");
                  }
               }
            
            if(proj.get(i).getLifetime() <= 0)
            {
            //here 
                  //proj.get(i).setLifetime(5);
                  proj.remove(i);
                  create = false;
                  //initialProjSize--;
            }
            
          }  
         
          for(int j = 0; j < Units.size(); j++){
            for(int i = 0; i < proj.size(); i++){
                unit = Units.get(j);
               hit = Physics.overlapping(((Unit) unit[unit.length-1]), proj);
              
               if(hit != null){
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
                  //proj.set(i, null);
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