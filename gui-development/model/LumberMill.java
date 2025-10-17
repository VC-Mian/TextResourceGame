package model;

/**
 * One of the generators that can constructed by using the resources in the game.
 * @author V. Miranda-Calleja
 */
import java.util.ArrayList;

public class LumberMill extends Generator{

    public LumberMill(){
        super("Lumber Mill", 3, "Wood");
    } 
    
    //constructs Lumbermill
    public void getConstructionResources(ArrayList<Resource> resources, ArrayList<Generator> generators){
        
        LumberMill lm = new LumberMill();

        for(Resource r: resources){
            //consumes 10 wood
            if(r instanceof Wood){
                int woodNum = ((Wood) r).getQuantity();
                if(woodNum >= 10){
                    ((Wood) r).consume(10);
                    System.out.println("\n-10 Wood\nLumberMill has been constructed.");
                    generators.add(lm);
                }else{
                    //shows the amount of wood needed to build lumbermill
                    int needWood = 10- woodNum;
                    System.out.println("\nNot enough wood.\nWood Required: 10\nWood Needed: " + needWood + "\nCurrent Wood: " + woodNum);
                }
            }
        }
    }
    
    public String toString(){
        return super.toString();
    }
}