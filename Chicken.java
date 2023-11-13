/**
 * One of the resources to be used in the game.
 * @author V. Miranda-Calleja
 */
import java.util.Random;

public class Chicken extends Resource{
    public Chicken(){
        super("Chicken", 1, false);
    }

    /**
    * probability of catching a chicken depending if roll an odd or even.
    */
    public void chickenCatchProbability(){
        Random random = new Random();
        int chickenChance = random.nextInt((10 - 1) + 1) + 1;
        String result;
        if(chickenChance % 2 == 0){
            System.out.println(chickenChance + " is even");
            System.out.println("You caught the chicken!");
            System.out.println("+1 Chicken");
            //add method to chicken quantity
            add(1);
            
        }else{
            System.out.println(chickenChance + " is odd");
            System.out.println("The chicken got away!");
        }
    } 

    public String toString(){
        return super.toString();
    }

}