public class Move implements Command{
   
   private AbstractUnit unit;
   private float x;
   private float y;
   private float xR;
   private float yR;
   
   public Move(AbstractUnit unit, float xR, float yR, float x, float y){
      this.unit = unit;
      this.x = x;
      this.y = y;
      this.xR = xR;
      this.yR = yR;
   }
   
   public void execute(){
      //if(unit instanceof Unit || unit instanceof Bubble || unit instanceof Health){
      if(unit!=null){
         unit.move(xR, yR, x, y);
      }
   
   }

}

