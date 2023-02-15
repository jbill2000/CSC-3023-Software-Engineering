import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.Math;
public class BubbleList
{
   
   
   
   public BubbleList()
   {
   
   
   }
   public class BubbleIterator
   {
      projectile next;
      ArrayList<projectile> theProjectiles = new ArrayList<projectile>();
            //Gets the next thing in the linked list
      public projectile getNext(int initialindex)
      {
         next= theProjectiles.get(initialindex);
         return next;
      }
      //Bool to check if at end of linked list or not.
      public boolean hasNext()
      {
         //If theList.get(i+1) is != null, then true, else false
         
         return false;
      }
      //Removes
      public ArrayList<projectile> remove(ArrayList<projectile> projectiles, int indextoberemoved)
      {
            projectiles.remove(indextoberemoved);
            
            return projectiles;
      
      }
      
   }

}
