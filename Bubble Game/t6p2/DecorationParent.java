public abstract class DecorationParent extends AbstractUnit{

   //protected AbstractUnit next;
   public boolean selected = false;
   public boolean moving = false;
   public float moveToX, moveToY, xR, yR;
   
   public DecorationParent(float x, float y,float radius, int playerSide){
      super(x,y,radius,playerSide);
   }
   
}