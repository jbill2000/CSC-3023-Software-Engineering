import java.lang.reflect.Array;
import java.util.*;
import javafx.scene.paint.*;
import javafx.scene.*;

public class KeithAi extends AI
{
   public KeithAi()
   {
      name = "Keith";
   }

   //this method is called first if you need to init any information. Such as clear out arraylists...
   //and [much harder] you could generate moves for every one of your units in this method and then assign them in pick final move.
   //it really depends upon if you want to do it unit by unit or keep context. Unit by unit is much easier, but I wanted to provide the interface to be able to do it globally if you wanted to.
   public void init(Unit u, ArrayList<Unit> unitsLeftToMove, boolean firstUnitInTurn)
   {

   }

   ArrayList<MapTile> spots = new ArrayList<MapTile>();
   public void ratePossiblePlacesToMoveForAdvancement( Unit theUnit,ArrayList<MapTile> possibleSpots)
   {
      //Variables
      ArrayList<Unit> enemyUnits = new ArrayList<Unit>();
      Map.Iterator allTileIterator = Map.getMap().generateIteratorAllTiles();

      //Adds enemy units to arraylist of enemy units
      while(allTileIterator.hasNext())
      {
         MapTile t = allTileIterator.next();
         if(t != null)
         {
            if(t.getUnit() != null)
            {
               if(t.getUnit().getOwner() != theUnit.getOwner())
               {
                  enemyUnits.add(t.getUnit());
               }
            }
         }
      }

      //algorithm to calculate scores.
      spots.clear();

      //Adds enemy units to spots arraylist
      Map.Iterator theIterator = Map.getMap().generateIteratorAllTiles();
      while(theIterator.hasNext())
      {
         MapTile tile = theIterator.next();
         if(tile.getUnit()!= null && tile.getUnit().getOwner() != theUnit.getOwner())
         {
            spots.add(tile);
         }
      }

      //demonstrating custom traversal of the graph, spirals outwards from the center points.
      int currentIt = Map.initCounter(); //this gets the next traversal number. The number is used it indicate if this particular traversal has encountered a given node.

      //score the enemy tiles as 100
      for(int i=0;i<spots.size();i++)
      {
         spots.get(i).setIt(currentIt); //set that this is a starting tile and will not revisit it later (well, unless its value is to small)
         spots.get(i).setGoodScore(100);
         if(spots.get(i).getUnit().getType() == Unit.Type.ART)
         {
            spots.get(i).setGoodScore(120);
         }
         else if(spots.get(i).getUnit().getType() == Unit.Type.RK)
         {
            spots.get(i).setGoodScore(115);
         }
         if(spots.get(i).getUnit().getType() == Unit.Type.MT)
         {
            spots.get(i).setGoodScore(110);
         }
         if(spots.get(i).getUnit().getType() == Unit.Type.ST)
         {
            spots.get(i).setGoodScore(105);
         }
      }

      //Step 1, set all nodes not visited
      for(int i=0;i<Map.SIZEX;i++)
      {
         for(int j=0;j<Map.SIZEY;j++)
         {
            if(Map.getMap().getTile(i,j) != null)// null meaning tile does not exist AKA a blank space.
               Map.getMap().getTile(i,j).setVisited(false);
         }
      }

      int count = 0;
      //Step 2:
      while(spots.size()>0)
      {
         count++;

         //get and remove first item in the list
         MapTile mt = spots.get(0);
         spots.remove(0);

         //set you visited it so you cannot visit it a second time
         mt.setVisited(true);
         MapTile.Iterator it = mt.createIterator(); //each tile has the ability to get its neighbors through the use of an iterator

         while(it.hasNext()) //check & getting for the next neighboring tile of mt
         {
            MapTile next = it.next();
            if((!next.getVisited()) || next.getGoodScore()-1 > mt.getGoodScore()) //cannot visit a node twice unless the next node is larger than this node, we use currentIt to indicate whether it has visited that tile previously
            {
               if(next.getUnit()==null || next.getUnit().getOwner() == theUnit.getOwner()) // if the tile is null OR if I can move through my own unit. Cannot move through enemy units...
               {
                  next.setVisited(true);
                  spots.add(next);
                  if(next.getGoodScore() < mt.getGoodScore())
                     next.setGoodScore(mt.getGoodScore()- (float)4.5); //between 3 and 5
               }
            }
         }

         if(count > 1000)
            break; //don't let it continue forever. Safety against an inf loop.
      }


      //Iterates over all possible spots and increments score values
      for (MapTile currentPossibleSpot : possibleSpots)
      {
         //Enemies in range to attack ally at a potential spot after enemy move (calculates enemy max range and movement)
         ArrayList<Unit> enemiesInRangeToAttackAfterEnemyMove = getEnemyUnitsInRangeToAttackPotentialXY(enemyUnits, currentPossibleSpot, theUnit);

         //Enemies in range that an ally can attack from a potential spot
         ArrayList<Unit> enemiesInRangeOfCurrentAllyAttack = Map.getMap().getEnemyUnitsInRangeIfUnitUWasAtLocationXY(currentPossibleSpot.getX(), currentPossibleSpot.getY(), theUnit);

         //Enemies in range to return fire from an ally attack at a potential spot
         ArrayList<Unit> enemiesInRangeToAttackFromCurrentPos = enemiesInRangeToAttackFromCurrentPos(enemyUnits, currentPossibleSpot, theUnit);

         //Checks how much damage a specific coordinate can take
         int possibleDamageTaken = 0;
         for (Unit unit : enemiesInRangeToAttackAfterEnemyMove)
         {
            possibleDamageTaken += unit.getDamage();
         }

         //The switch statement is only for changing which code I run in order to compare and decide what i want to use
         int option = 4;
         switch (option)
         {
            case 1:
               //Reduces score of tile if possible damage taken at a spot is greater than 6
               if (possibleDamageTaken > 6)
               {
                  currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - 5);
               }
               break;
            case 2:
               //Reduces score of tile if possible damage taken at a spot is greater than 5 (yes it makes a difference)
               if (possibleDamageTaken > 5)
               {
                  currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - 5);
               }
               break;
            case 3:
               //Reduces score of tile by half the amount of possible damage taken at a spot
               currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - ((float) possibleDamageTaken/2));
               break;
            case 4:
               //Reduces score of tile by the amount of possible damage taken at a spot
               currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - possibleDamageTaken);
               break;
         }

         //If unit will die at a possible spot decrement score
         if (theUnit.getHP() < possibleDamageTaken)
         {
            currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - 5);
         }

         
         
         //If we cannot attack anyone from a possible spot, but we can be attacked next turn by an enemy's move and attack decrement score
        /* if (enemiesInRangeOfCurrentAllyAttack.size() == 0 && enemiesInRangeToAttackAfterEnemyMove.size() == 1)
         {
            currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() - 5);
         }
         */
         
         //If we can attack an enemy and not be return attacked by an enemy next turn after their move increment score
         if (enemiesInRangeOfCurrentAllyAttack.size() > 0 && enemiesInRangeToAttackAfterEnemyMove.size() <= 0) //0 or 1
         {
            currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 20);
         }

         //If we can attack an enemy and not be immediately return attacked increment score
         if (enemiesInRangeOfCurrentAllyAttack.size() > 0 && enemiesInRangeToAttackFromCurrentPos.size() == 0)
         {
            currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 20);
         }
         
         

         //Loops through enemies a unit can attack at a possible spot and increments score if we can kill them and based on enemy unit value
         for (Unit enemy : enemiesInRangeOfCurrentAllyAttack)
         {
            //If enemy type is RK
            if (enemy.getType() == Unit.Type.RK)
            {
               //And if we can kill them
               if(theUnit.getDamage() >= enemy.getHP())
               {
                  //If there is only one enemy to return attack increment higher score, else: slightly lower score
                  if (enemiesInRangeToAttackAfterEnemyMove.size() == 1)
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 12);
                  }
                  else
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 8);
                  }
               }
            }
            else if (enemy.getType() == Unit.Type.ART)
            {
               if(theUnit.getDamage() >= enemy.getHP())
               {
                  if (enemiesInRangeToAttackAfterEnemyMove.size() == 1)
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 11);
                  }
                  else
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 7);
                  }
               }
            }
            else if (enemy.getType() == Unit.Type.MT)
            {
               if(theUnit.getDamage() >= enemy.getHP())
               {
                  if (enemiesInRangeToAttackAfterEnemyMove.size() == 1)
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 10);
                  }
                  else
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 6);
                  }
               }
            }
            else if (enemy.getType() == Unit.Type.ST)
            {
               if(theUnit.getDamage() >= enemy.getHP())
               {
                  if (enemiesInRangeToAttackAfterEnemyMove.size() == 1)
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 9);
                  }
                  else
                  {
                     currentPossibleSpot.setGoodScore(currentPossibleSpot.getGoodScore() + 5);
                  }
               }
            }
         }
         //other else ifs for more precise measuring of quality of spots

      }
   }

   //can remove files from possibleSpots or assign bad scores
   public void removeDangerousMoves(Unit theUnit,ArrayList<MapTile> possibleSpots)
   {

   }

   //generates the final move based on the previous methods (or, ignoring them if you prefer to write your code in this method only)
   public Move pickFinalMove(Unit theUnit,ArrayList<MapTile> possibleSpots)
   {
      //picking highest score tile. Note this does not use the bad score at all
      int selected = 0;
      float max=0;

      //selects tile with highest score
      for(int i=0;i<possibleSpots.size();i++)
      {
         if(possibleSpots.get(i).getGoodScore() > max)
         {
            max = possibleSpots.get(i).getGoodScore();
            selected = i;
         }
      }

      //then it gets all the enemies if the unit was at that the new spot
      ArrayList<Unit> enemiesInRange = Map.getMap().getEnemyUnitsInRangeIfUnitUWasAtLocationXY(possibleSpots.get(selected).getX(),possibleSpots.get(selected).getY(),theUnit);

      //then, if there is an enemy in range, it creates the move attack it
      Move m=null;

      //if there are enemies in range...
      if(enemiesInRange.size() > 0)
      {
         //Attacks the only unit in range
         if(enemiesInRange.size() == 1)
         {
            m = new Move(theUnit,new Vector2(possibleSpots.get(selected).getX(),possibleSpots.get(selected).getY()),true,enemiesInRange.get(0));
         }
         //Selects which of the multiple units in range to attack
         else
         {
            //Check if there are enemies that are killable
            ArrayList<Unit> enemiesKillable = getKillableEnemies(theUnit, enemiesInRange);

            //If there are killable enemies, attacks the highest rank and highest hp unit of that rank to kill
            if(enemiesKillable.size() > 0)
            {
               Unit killEnemy = selectEnemyToKill(enemiesKillable);
               m = new Move(theUnit,new Vector2(possibleSpots.get(selected).getX(),possibleSpots.get(selected).getY()),true, killEnemy);
            }
            //If there are not killable enemies, attacks either the lowest hp unit or the lowest hp unit of the highest value
            else
            {
               //Attack highest value enemy with the lowest health
               Unit lowestHpUnitOfHighestValue = selectLowestHpUnitOfHighestValue(enemiesInRange, theUnit);
               m = new Move(theUnit, new Vector2(possibleSpots.get(selected).getX(), possibleSpots.get(selected).getY()), true, lowestHpUnitOfHighestValue);
            }
         }
      }
      else
      {
         //otherwise just moves there
         m = new Move(theUnit,new Vector2(possibleSpots.get(selected).getX(),possibleSpots.get(selected).getY()),false,null);
      }

      //Code to show attack range of unit
      Map.Iterator spots = Map.getMap().generateIteratorWithinAttackRange(m.getNewPosition().getX(),m.getNewPosition().getY(),theUnit.getRange(),theUnit);
      while(spots.hasNext())
      {
         MapTile tile = spots.next();
         tile.setColor(Color.DARKGOLDENROD);  //this was really so I can see what the AI is doing. You will probably find you want to display what the AI is doing too
      }

      return m;
   }

   public int pickNextUnit(ArrayList<Unit> unitsLeftToRun)
   {
      /*
      //Units go in order of ART, RK, ST, MT but picks the Highest HP of that type first
      ArrayList<Unit> alliesOfTypeRK = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeART = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeMT = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeST = new ArrayList<>();

      //Adds killable enemies of same type to corresponding array
      for (Unit unit : unitsLeftToRun)
      {
         if (unit.getType() == Unit.Type.RK)
         {
            alliesOfTypeRK.add(unit);
         }
         else if (unit.getType() == Unit.Type.ART)
         {
            alliesOfTypeART.add(unit);
         }
         else if (unit.getType() == Unit.Type.MT)
         {
            alliesOfTypeMT.add(unit);
         }
         else if (unit.getType() == Unit.Type.ST)
         {
            alliesOfTypeST.add(unit);
         }
      }

      Unit returnedUnit = null;

      if(alliesOfTypeART.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeART.size(); i++)
         {
            if(alliesOfTypeART.get(i).getHP() > alliesOfTypeART.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeART.get(maxHpLeftIndex);
      }
      else if(alliesOfTypeRK.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeRK.size(); i++)
         {
            if(alliesOfTypeRK.get(i).getHP() > alliesOfTypeRK.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeRK.get(maxHpLeftIndex);
      }
      else if(alliesOfTypeST.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeST.size(); i++)
         {
            if(alliesOfTypeST.get(i).getHP() > alliesOfTypeST.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeST.get(maxHpLeftIndex);
      }
      else if(alliesOfTypeMT.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeMT.size(); i++)
         {
            if(alliesOfTypeMT.get(i).getHP() > alliesOfTypeMT.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeMT.get(maxHpLeftIndex);
      }

      for(int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if(returnedUnit == unitsLeftToRun.get(i))
         {
            return i;
         }
      }
       */

      /*
      //Units go in order of ART, RK, ST, MT but picks the lowest HP of that type first *best results??*
      ArrayList<Unit> alliesOfTypeRK = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeART = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeMT = new ArrayList<>();
      ArrayList<Unit> alliesOfTypeST = new ArrayList<>();

      //Adds killable enemies of same type to corresponding array
      for (Unit unit : unitsLeftToRun)
      {
         if (unit.getType() == Unit.Type.RK)
         {
            alliesOfTypeRK.add(unit);
         }
         else if (unit.getType() == Unit.Type.ART)
         {
            alliesOfTypeART.add(unit);
         }
         else if (unit.getType() == Unit.Type.MT)
         {
            alliesOfTypeMT.add(unit);
         }
         else if (unit.getType() == Unit.Type.ST)
         {
            alliesOfTypeST.add(unit);
         }
      }

      Unit returnedUnit = null;

      if(alliesOfTypeART.size() > 0)
      {
         int leastHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeART.size(); i++)
         {
            if(alliesOfTypeART.get(i).getHP() < alliesOfTypeART.get(leastHpLeftIndex).getHP())
            {
               leastHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeART.get(leastHpLeftIndex);
      }
      else if(alliesOfTypeRK.size() > 0)
      {
         int leastHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeRK.size(); i++)
         {
            if(alliesOfTypeRK.get(i).getHP() < alliesOfTypeRK.get(leastHpLeftIndex).getHP())
            {
               leastHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeRK.get(leastHpLeftIndex);
      }
      else if(alliesOfTypeST.size() > 0)
      {
         int leastHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeST.size(); i++)
         {
            if(alliesOfTypeST.get(i).getHP() < alliesOfTypeST.get(leastHpLeftIndex).getHP())
            {
               leastHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeST.get(leastHpLeftIndex);
      }
      else if(alliesOfTypeMT.size() > 0)
      {
         int leastHpLeftIndex = 0;
         for(int i = 1; i < alliesOfTypeMT.size(); i++)
         {
            if(alliesOfTypeMT.get(i).getHP() < alliesOfTypeMT.get(leastHpLeftIndex).getHP())
            {
               leastHpLeftIndex = i;
            }
         }

         returnedUnit = alliesOfTypeMT.get(leastHpLeftIndex);
      }

      for(int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if(returnedUnit == unitsLeftToRun.get(i))
         {
            return i;
         }
      }
      */

      //Units go in order of ART, RK, ST, MT
      for (int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if (unitsLeftToRun.get(i).getType() == Unit.Type.ART)
         {
            return i;
         }
      }
      for (int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if (unitsLeftToRun.get(i).getType() == Unit.Type.RK)
         {
            return i;
         }
      }
      for (int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if (unitsLeftToRun.get(i).getType() == Unit.Type.ST)
         {
            return i;
         }
      }
      for (int i = 0; i < unitsLeftToRun.size(); i++)
      {
         if (unitsLeftToRun.get(i).getType() == Unit.Type.MT)
         {
            return i;
         }
      }


      return 0; //its a bad AI! just returns the first unit. Otherwise, pick the unit you want to run. Like, all of one type before all of another type...
   }

   //getting all units in attack range if a unit was at a particular location.
   public void setBadScoresInRangeOfEnemy(ArrayList<Unit> enemyUnits)
   {
      for (Unit enemyUnit : enemyUnits)
      {
         Map.Iterator spots = Map.getMap().generateIteratorWithinAttackRange(enemyUnit.getX(), enemyUnit.getY(), enemyUnit.getRange() + enemyUnit.getSpeed(), enemyUnit);
         while (spots.hasNext())
         {
            MapTile tile = spots.next();
            tile.setGoodScore(tile.getGoodScore() - 1);
         }
      }
   }

   //Function attacks unit you can kill in order of importance
   //Then if there are multiple of that type that is killable, returns the unit with the highest hp.
   public Unit selectEnemyToKill(ArrayList<Unit> enemiesToKill)
   {
      ArrayList<Unit> killableEnemiesOfTypeRK = new ArrayList<>();
      ArrayList<Unit> killableEnemiesOfTypeART = new ArrayList<>();
      ArrayList<Unit> killableEnemiesOfTypeMT = new ArrayList<>();
      ArrayList<Unit> killableEnemiesOfTypeST = new ArrayList<>();

      //Adds killable enemies of same type to corresponding array
      for (Unit unit : enemiesToKill)
      {
         if (unit.getType() == Unit.Type.RK)
         {
            killableEnemiesOfTypeRK.add(unit);
         }
         else if (unit.getType() == Unit.Type.ART)
         {
            killableEnemiesOfTypeART.add(unit);
         }
         else if (unit.getType() == Unit.Type.MT)
         {
            killableEnemiesOfTypeMT.add(unit);
         }
         else if (unit.getType() == Unit.Type.ST)
         {
            killableEnemiesOfTypeST.add(unit);
         }
      }

      //If there are Units in an array, returns the most valuable unit with the highest hp
      if(killableEnemiesOfTypeRK.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < killableEnemiesOfTypeRK.size(); i++)
         {
            if(killableEnemiesOfTypeRK.get(i).getHP() > killableEnemiesOfTypeRK.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         return killableEnemiesOfTypeRK.get(maxHpLeftIndex);
      }
      else if(killableEnemiesOfTypeART.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < killableEnemiesOfTypeART.size(); i++)
         {
            if(killableEnemiesOfTypeART.get(i).getHP() > killableEnemiesOfTypeART.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         return killableEnemiesOfTypeART.get(maxHpLeftIndex);
      }
      else if(killableEnemiesOfTypeMT.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < killableEnemiesOfTypeMT.size(); i++)
         {
            if(killableEnemiesOfTypeMT.get(i).getHP() > killableEnemiesOfTypeMT.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         return killableEnemiesOfTypeMT.get(maxHpLeftIndex);
      }
      else if(killableEnemiesOfTypeST.size() > 0)
      {
         int maxHpLeftIndex = 0;
         for(int i = 1; i < killableEnemiesOfTypeST.size(); i++)
         {
            if(killableEnemiesOfTypeST.get(i).getHP() > killableEnemiesOfTypeST.get(maxHpLeftIndex).getHP())
            {
               maxHpLeftIndex = i;
            }
         }

         return killableEnemiesOfTypeST.get(maxHpLeftIndex);
      }

      return null;
   }

   //Selects the lowest hp unit
   public Unit selectLowestHpUnit(ArrayList<Unit> enemies)
   {
      int unitWithLowestHP = 0;
      for(int i=1;i<enemies.size();i++)
      {
         if(enemies.get(i).getHP() < enemies.get(unitWithLowestHP).getHP())
         {
            unitWithLowestHP = i;
         }
      }
      return enemies.get(unitWithLowestHP);
   }

   //Selects the lowest hp unit of the highest value to attack
   public Unit selectLowestHpUnitOfHighestValue(ArrayList<Unit> enemies, Unit currentUnit)
   {
      ArrayList<Unit> enemiesOfTypeRK = new ArrayList<>();
      ArrayList<Unit> enemiesOfTypeART = new ArrayList<>();
      ArrayList<Unit> enemiesOfTypeMT = new ArrayList<>();
      ArrayList<Unit> enemiesOfTypeST = new ArrayList<>();

      //Adds enemies of same type to corresponding array
      for (Unit enemy : enemies)
      {
         if (enemy.getType() == Unit.Type.RK)
         {
            enemiesOfTypeRK.add(enemy);
         }
         else if (enemy.getType() == Unit.Type.ART)
         {
            enemiesOfTypeART.add(enemy);
         }
         else if (enemy.getType() == Unit.Type.MT)
         {
            enemiesOfTypeMT.add(enemy);
         }
         else if (enemy.getType() == Unit.Type.ST)
         {
            enemiesOfTypeST.add(enemy);
         }
      }

      //If there are Units in an array, returns the most valuable unit with the highest hp
      if(enemiesOfTypeRK.size() > 0)
      {
         int unitWithLowestHP = 0;
         for(int i=1;i<enemiesOfTypeRK.size();i++)
         {
            if(enemiesOfTypeRK.get(i).getHP() < enemiesOfTypeRK.get(unitWithLowestHP).getHP())
            {
               unitWithLowestHP = i;
            }
         }
         return enemiesOfTypeRK.get(unitWithLowestHP);
      }
      else if(enemiesOfTypeART.size() > 0)
      {
         int unitWithLowestHP = 0;
         for(int i=1;i<enemiesOfTypeART.size();i++)
         {
            if(enemiesOfTypeART.get(i).getHP() < enemiesOfTypeART.get(unitWithLowestHP).getHP())
            {
               unitWithLowestHP = i;
            }
         }
         return enemiesOfTypeART.get(unitWithLowestHP);
      }
      else if(enemiesOfTypeMT.size() > 0)
      {
         int unitWithLowestHP = 0;
         for(int i=1;i<enemiesOfTypeMT.size();i++)
         {
            if(enemiesOfTypeMT.get(i).getHP() < enemiesOfTypeMT.get(unitWithLowestHP).getHP())
            {
               unitWithLowestHP = i;
            }
         }
         return enemiesOfTypeMT.get(unitWithLowestHP);
      }
      else if(enemiesOfTypeST.size() > 0)
      {
         int unitWithLowestHP = 0;
         for(int i=1;i<enemiesOfTypeST.size();i++)
         {
            if(enemiesOfTypeST.get(i).getHP() < enemiesOfTypeST.get(unitWithLowestHP).getHP())
            {
               unitWithLowestHP = i;
            }
         }
         return enemiesOfTypeST.get(unitWithLowestHP);
      }

      return null;
   }

   //Returns list of enemies that are killable
   public ArrayList<Unit> getKillableEnemies(Unit theUnit, ArrayList<Unit> enemiesInRange)
   {
      ArrayList<Unit> enemiesKillable = new ArrayList<>();
      for (Unit enemyUnit : enemiesInRange)
      {
         if (theUnit.getDamage() > enemyUnit.getHP())
         {
            enemiesKillable.add(enemyUnit);
         }
      }
      return enemiesKillable;
   }

   //Returns list of enemies that can attack a possible spot based off their max move and range
   public ArrayList<Unit> getEnemyUnitsInRangeToAttackPotentialXY(ArrayList<Unit> enemyUnits, MapTile possibleSpot, Unit theUnit)
   {
      ArrayList<Unit> enemiesInRangeToAttackPossibleSpot = new ArrayList<>();

      for(int i = 0; i < enemyUnits.size(); i++)
      {
         Map.Iterator it = Map.getMap().generateIteratorWithinAttackRange(enemyUnits.get(i).getX(), enemyUnits.get(i).getY(), enemyUnits.get(i).getRange() + enemyUnits.get(i).getSpeed(), enemyUnits.get(i));

         while (it.hasNext())
         {
            MapTile m = it.next();
            if(m.getX() == possibleSpot.getX() && m.getY() == possibleSpot.getY())
            {
                enemiesInRangeToAttackPossibleSpot.add(enemyUnits.get(i));
            }
         }
      }

      return enemiesInRangeToAttackPossibleSpot;
   }

   //Returns list of enemies that can attack a possible spot based off their current pos and range
   public ArrayList<Unit> enemiesInRangeToAttackFromCurrentPos(ArrayList<Unit> enemyUnits, MapTile possibleSpot, Unit theUnit)
   {
      ArrayList<Unit> enemiesInRangeToAttackPossibleSpot = new ArrayList<>();

      for(int i = 0; i < enemyUnits.size(); i++)
      {
         Map.Iterator it = Map.getMap().generateIteratorWithinAttackRange(enemyUnits.get(i).getX(), enemyUnits.get(i).getY(), enemyUnits.get(i).getRange(), enemyUnits.get(i));

         while (it.hasNext())
         {
            MapTile m = it.next();
            if(m.getX() == possibleSpot.getX() && m.getY() == possibleSpot.getY())
            {
               enemiesInRangeToAttackPossibleSpot.add(enemyUnits.get(i));
            }
         }
      }

      return enemiesInRangeToAttackPossibleSpot;
   }

}
