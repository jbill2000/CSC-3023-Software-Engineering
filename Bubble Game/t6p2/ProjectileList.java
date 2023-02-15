import java.io.*;
import java.util.*;
import java.text.*;
import java.lang.Math;
//ProjectileList is the Singleton implementation
public class ProjectileList
{
   private static ProjectileList theList = new ProjectileList();
   static ArrayList<projectile> theProjectiles = new ArrayList<projectile>();
   
   private ProjectileList(){};
   
  // Projectile next;
   
   //Returns the single instance (Singleton)
   public static ProjectileList getInstance()
   {
   
         return theList;
   }
   
   public void addToList(projectile p)
   {
   
      theProjectiles.add(p);
   
   }
   
   
   public ArrayList<projectile> getList()
   {
      
      return theProjectiles;
   
   
   }
   
   
   
   public void setList(ArrayList<projectile> pl)
   {
   
      theProjectiles=pl;
   }
      
   public void removeList(int indextoberemoved)
   {
      theProjectiles.remove(indextoberemoved);
      
   }
   //Passes in list to iterate over?
   Iterator createIterator(ArrayList<projectile> pass)
   {
       
      Iterator i = new Iterator(pass);
      
      return i;
   }
   
   //TBD
   
   public class Iterator
   {
      projectile current;
      projectile prev;
      int size;
      public Iterator(ArrayList<projectile> thePList)
      {
         boolean added=false;
         size= thePList.size();
         
         
         if(size==0)
         {
            current=null;
            prev=null;
         }
         else if(size == 1)
         {
            current=thePList.get(0);
            current.setNext(null);
            prev=null;
         }
         else if(size >=2)
         {
            if(size == 2)
            {
               current=thePList.get(1);
               current.setNext(null);
               prev=thePList.get(0);
               prev.setNext(current);
            }
            if(size >2)
            {
               for(int p=1; p<size; p++)
               {
                  current = thePList.get(p);
                  prev = thePList.get(p-1);
                  if(p+1<size)
                  {
                     if(thePList.get(p+1) != null)
                     {
                        current.setNext(thePList.get(p+1));
                        prev.setNext(current);
                     }
                  }
                  else
                  {
                     prev.setNext(current);
                     current.setNext(null);
                  }
               }
            }
         
         }

      }     
      //Gets the next thing in the linked list
      public projectile getNext()
      {
         ///Sets previous to current 
       if(size > 0)
       {  
         //Gets the "next" which is now current.
          if(current.getNext() != null)
          {
            prev=current;
            System.out.println("current");
            current = current.getNext();
             return current;
          }
          else
          {
            System.out.println("previous");
            current=null;
            return null;
          }
       }
       else
       {
         return null;
       }
        
      }
      
      //Bool to check if at end of linked list or not.
      public boolean hasNext()
      {
         if(current == null)
         {
         
            return false;
         }
         else
         {
         
            return true;
         }
         
      }
            
   }

}
