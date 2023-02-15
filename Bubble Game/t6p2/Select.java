public class Select implements Command{
   
   private AbstractUnit unit;
   
   public Select(AbstractUnit unit){
      this.unit = unit;
   }
   
   public void execute(){
      unit.Select();
   
   }

}

