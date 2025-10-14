/**
 * One of the generators that can constructed by using the resources in the game.
 * Resource Management Game - Console Version 1.0
 * @author V. Miranda-Calleja
 */
import java.util.ArrayList;

public class IronGenerator extends Generator{

    public IronGenerator(){
        super("Iron Generator", 2, "Iron");
    } 
    
    //Constructs Iron Generator
    public void getConstructionResources(ArrayList<Resource> resources, ArrayList<Generator> generators){

        IronGenerator iGen = new IronGenerator();

        for(Resource r: resources){
            //consumes 30 wood
            if(r instanceof Wood){
                int woodNum = ((Wood) r).getQuantity();
                if(woodNum >= 30){
                    ((Wood) r).consume(30);
                    System.out.println("\n-30 Wood\nIronGenerator has been constructed.");
                    generators.add(iGen);
                }else{
                    //shows the amount of wood needed to build Iron Generator
                    int needWood = 30- woodNum;
                    System.out.println("\nNot enough wood.\nWood Required: 30\nWood Needed: " + needWood + "\nCurrent Wood: " + woodNum);
                }
            }
        }
    }

    public String toString(){
        return super.toString();
    }
}