/**
 * One of the generators that can constructed by using the resources in the game.
 * @author V. Miranda-Calleja
 */
import java.util.ArrayList;

public class ChickenFarm extends Generator{
     
    public ChickenFarm(){
        super("Chicken Farm", 1, "Chicken");
    } 
    
    //constructs Chicken Farm
    public void getConstructionResources(ArrayList<Resource> resources, ArrayList<Generator> generators){

        ChickenFarm cFarm = new ChickenFarm();

        for(Resource r: resources){ 
            //consumes 50 iron
            if(r instanceof Iron){
                int ironNum = ((Iron) r).getQuantity();
                if(ironNum >= 50){
                    ((Iron) r).consume(50);
                    System.out.println("\n-50 Iron\nChicken Farm has been constructed.");

                    //adds ig to generators arraylist if meet the conditions
                    generators.add(cFarm);
                
                }else{
                    //shows the amount of iron needed to build Chicken Farm
                    int needIron = 50 - ironNum;
                    System.out.println("\nNot enough iron.\nIron Required: 50\nIron Needed: " + needIron + "\nCurrent Iron: " + ironNum);
                }
            }
        }
    }

    public String toString(){
        return super.toString();
    }
}