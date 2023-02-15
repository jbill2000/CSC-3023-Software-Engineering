import java.util.*;

public class Strategy2{

   float speed, refire, x, y, diffx, diffy;
   projectile hit = null;
   //Boolean to ensure we only hit one decorator at a time.
   boolean damagecheck=false;
   AbstractUnit[] unit;
   boolean create = false;
   boolean hasSplit=false;
   Strategy0 strat0;
   public Strategy2(float speed, float refire)
   {
      this.speed = speed;
      this.refire = refire;
      strat0= new Strategy0(speed,refire);
   
   }
   
   public ArrayList<projectile> execute(ArrayList<projectile> proj, ArrayList<AbstractUnit[]> Units) //deals with movement of the projectiles
   {
   
      //System.out.println("executing strat 2!");
      //catch if null
      
      if(proj.size() > 0){
                 
         //iter through projectiles and move thems
         for(int i = 0; i < proj.size(); i++){
            proj.get(i).decreaseLifetime();
            x = proj.get(i).getX();
            y = proj.get(i).getY();
            
            proj.get(i).setX(x + (speed * proj.get(i).getDiffx()));
            proj.get(i).setY(y + (speed * proj.get(i).getDiffy())); 
          
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
                  
                  if(proj.get(i) == hit)
                  {
                     proj.remove(i);
                  
                  } 
                  //proj.set(i, null);
                  //proj.remove(i);
                  //hit = null;
               }
               else if(hit == null)
               {
                  
                  if(proj.get(i) instanceof projectile2)
               {
                  //tempProj=((projectile1)proj.get(i)).getProj1();
                  //while(proj.get(i).getLifetime() > 1)
                  //{
                  
                     proj.get(i).decreaseLifetime();
                  //}
                  if(proj.get(i).getLifetime() <= 0 && create == false)
                  {
            //here
                     //proj.get(i).decreaseLifetime();
                     //"create" 8 bubbles
                     for(int p=0; p<8; p++)
                     {   
      //In strat 2, there is an issue with the math 
                        switch(p)
                        {
                           case 1:
                              diffx = proj.get(i).getDiffx()+((float)0.785398);
                              diffy = proj.get(i).getDiffy()+((float)0.785398);
                           break;
                           
                           case 2:
                              diffx = proj.get(i).getDiffx()+((float)0);
                              diffy = proj.get(i).getDiffy()+((float)1);
                           break;
                           
                           case 3:
                              diffx = proj.get(i).getDiffx()+((float)-0.785398);
                              diffy = proj.get(i).getDiffy()+((float)0.785398);
                           break;
                           
                           case 4:
                              diffx = proj.get(i).getDiffx()+((float)-1);
                              diffy = proj.get(i).getDiffy()+((float)0);
                           break;
                           
                           case 5:
                              diffx = proj.get(i).getDiffx()+((float)-0.785398);
                              diffy = proj.get(i).getDiffy()+((float)-0.785398);
                           break;
                         
                           case 6:
                              diffx = proj.get(i).getDiffx()+((float)0);
                              diffy = proj.get(i).getDiffy()+((float)-1);
                           break;
                           
                           case 7:
                              diffx = proj.get(i).getDiffx()+((float)0.785398);
                              diffy = proj.get(i).getDiffy()+((float)-0.785398);
                           break;
                           
                           case 8:
                              diffx = proj.get(i).getDiffx()+((float)1);
                              diffy = proj.get(i).getDiffy()+((float)0);
                           break;
                        }
                        //make sure diffx and diffy are here
                        proj.get(i).decreaseLifetime();
                        proj.add(new projectile0(proj.get(i).getX(), proj.get(i).getY(), diffx, diffy, speed, proj.get(i).getRadius(), 0, proj.get(i).getSide(), proj.get(i).getDamage(), (float)5));
                        //proj.add(tempProj.get(p));
                     }
                     
                    create = true;
                    proj.remove(i);
                    //proj=strat0.execute(proj,Units);
                     //System.out.println("creating");
                     
                  }
                 
                   
               }
           
            if(proj.get(i).getLifetime() <= 0)
            {
            //here 
                  //proj.get(i).setLifetime(5);
                  
                  proj.remove(i);
                  create=false;
                  //initialProjSize--;
            }
               
               
               }
            }
         }
       }
      return proj;
   }
}