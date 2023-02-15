public class Deselect implements Command{
   
   private AbstractUnit unit;
   
   public Deselect(AbstractUnit unit){
      this.unit = unit;
   }
   
   public void execute(){
      unit.Deselect();
   
   }
}
