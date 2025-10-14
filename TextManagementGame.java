/**
 *  The TextManagementGame class represents a text-based management game where the player manages resources 
 *  and resource generators. Along with several methods to aid the games structure.
 *  Console Version 1.0 - Before GUI Implementation
 *  @author V. Miranda-Calleja
 */

import java.io.*;
import java.lang.Thread;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections; 
import java.util.Random;

public class TextManagementGame extends Thread{
    
    // Define game variables
    private int day = 0;
    private ArrayList<Resource> resources = new ArrayList<Resource>();
    private ArrayList<Generator> generators = new ArrayList<Generator>(); 
    
    // Define a Scanner for user input
    private Scanner input = new Scanner(System.in);

    //object for Resource
    Resource resourceObj = new Resource();
    Wood wood = new Wood();
    Iron iron = new Iron();
    Chicken chicken = new Chicken();
    Health health = new Health(0);
    
    //object for Event
    Event resultTrig = new Event();

    /**
     * Creates a new TextManagementGame instance with initial resources
     */
    public void starterResource() {
        resources = new ArrayList<>();

        //adds object Resource class into arraylist resources 
        addResource(wood);
        addResource(iron);
        addResource(chicken);
        addResource(health);
    }

    /**
     * gets the total resource Score of all the resources of Wood, Iron, and Chicken collected over the course of the game.
     * @return totalScore = sum of wood, iron, and chicken resources
     */
    public int getScoreImpact(){
        int totalScore = wood.scoreImpact() + iron.scoreImpact() + chicken.scoreImpact();
        System.out.println("-----> Total Resource Score: " + totalScore + " <-----");
        return totalScore;
    }

   /**
    * Event: Werewolf does damage to the player if Event is triggered when the player rolls an odd number.
    */
    public void EventTrigger() {

        Random random = new Random();
        int eventChance = random.nextInt((3 - 0) + 0) + 1; // Generates a random number between 0 - 3
        if(eventChance % 2 != 0){
            System.out.println("            You rolled a " + eventChance);
            System.out.println("        The Blood Moon has risen...");

            //consumes health based on wereolf damage in vent class
            health.consume(resultTrig.getWerewolfDamage());
            System.out.println(resultTrig.eventMessage());
            System.out.println("             Health: " + health.getQuantity()); 
        }else{
            System.out.println("           You rolled a " + eventChance);
            System.out.println("    The Moon Looks Beautiful Tonight.");
            System.out.println("            Health: " + health.getQuantity());
        }
    }

    /**
    * Prints the list of resources
    */
    public void viewResources(){
        
        //using the compareTo() organizes the resources Arraylist acending order.
        System.out.println("_________________________");
        Collections.sort(resources);
            
        for(Resource r: resources){
            System.out.println(r);
        }
    }

    /**
    * Prints the list of Generators
    */
    public void viewGenerators(){
        System.out.println("_________________________");
        for(Generator b : generators){
            System.out.println("\n" + b);
        }
        if(generators.isEmpty()){
            System.out.println("No Generators.");
        }
    }

    /**
     * Constructs a lumbermill and adds it to the arraylist of generators if it meets the conditions.
     */
    public void constructLumbermill(){

        System.out.println("_________________________");
        LumberMill lumberMill = new LumberMill();

        lumberMill.getConstructionResources(resources, generators);
    }
    
    /**
     * Constructs a iron generator and adds it to the arraylist of generators if it meets the conditions.
     */
    public void constructIronGenerator(){

        System.out.println("_________________________");
        IronGenerator ironGen = new IronGenerator();

        ironGen.getConstructionResources(resources, generators);
    }

    /**
     * Constructs a Chicken farm and adds it to the arraylist of generators if it meets the conditions.
     */
    public void constructChickenFarm(){
        
       System.out.println("_________________________");
       ChickenFarm chickenFarm = new ChickenFarm();

       chickenFarm.getConstructionResources(resources, generators);
    }

    /**
     * Increments the time counter and then adds more resources based on what generators are present.
     * @return day- day number after increment. 
     */
    public int dayTracker(){
        day++;
        return day;
    }

    /** 
     * Adds a Resource object to the ArrayList of resources.
     * @param resource the Resource object to add
     */
    public void addResource(Resource resource) {
        resources.add(resource);
    }

    /**
     * Player can eat a chicken, if chicken resources meet conditions.
     */
    public void eatChicken(){
        System.out.println("_________________________");
        if(health.getQuantity() > 50){
            health.setQuantity(50);
            System.out.println("Health is full.");
            System.out.println("Chicken: " + chicken.getQuantity());
        }else if(health.getQuantity() == 50){
            System.out.println("Health is full.");
            System.out.println("Chicken: " + chicken.getQuantity());
        }else if(chicken.getQuantity() != 0){
            chicken.consume(1);
            System.out.print("You have eaten a chicken.");
            health.add(3);
            if(health.getQuantity() > 50){
                health.setQuantity(50);
            }
            System.out.println("\nHealth: " + health.getQuantity());
            System.out.println("Chicken: " + chicken.getQuantity());
        }else{
            System.out.println("Not enough Chicken.");
            System.out.println("Chicken: " + chicken.getQuantity());
        }
    }

    /**
     * Player can catch a chicken with the probably being odd(escaped) or even(caught) to determine if they caught the chicken.
     */
    public void chickenCatch(){
        for(Resource r: resources){
            if(r instanceof Chicken){
                ((Chicken) r).chickenCatchProbability();
            }
        }
        System.out.println("Chicken: " + chicken.getQuantity());
    }   

    /**
     * Checks if we are out of any critical resources
     * @return returns true if we are out of any critical resources, returns false otherwise.
     */
    public boolean isCriticalResourceEmpty(){
        for(Resource r : resources){
            if(r.isCritical() && r.getQuantity() == 0){
                return true;
            }
        }
        return false;
    }

    
    /**
     * In charge of telling the player the time of day, triggering the event, and adding rsource output from generators. And controls the speed in which these events happen. 
     */
    public void run() {

        //while loop
        while(!isCriticalResourceEmpty()){

            try {
                Thread.sleep(400);
                //day number method for each day
                System.out.println("\n          _______________");
                System.out.println("\n               Day " + dayTracker()); 

                //generates resources each day
                for(Generator g: generators){
                    if(day != 1){
                        if(g instanceof LumberMill){
                        //does not add resources on day 1.
                            wood.add(3);     
                            System.out.println("+3 Wood"); 
                        }
                        if(g instanceof IronGenerator){
                            iron.add(2);
                            System.out.println("+2 Iron");
                        }
                        if(g instanceof ChickenFarm){
                            chicken.add(1);
                            System.out.println("+1 Chicken");
                        }
                    }
                }
                                
                //displays daytime
                System.out.println("\n       _ _ _ _ _ _ _ _ _ _ _ _");
                System.out.println("       +  Current Time: Day  +");
                
                //displays eveningtime.
                Thread.sleep(15000);
                System.out.println("\n       _ _ _ _ _ _ _ _ _ _ _ _ _");
                System.out.println("       +  Current Time: Evening  +\n       The Event will begin soon...       ");

                //Lets player know their health has been reduced
                health.consume(2);
                System.out.println("\n=========================================");
                System.out.println("            You are hungry. \n              -2 health\n              Health: " + health.getQuantity());
                System.out.println("=========================================");

                //displays nighttime
                Thread.sleep(15000);
                System.out.println("\n       _ _ _ _ _ _ _ _ _ _ _ _ _");
                System.out.println("       +  Current Time: Night  +");
                //event Method
                System.out.println("\n===============================================");
                EventTrigger();
                System.out.println("================================================");

                //sleeps again to ensure dayTime is not immediately executed
                Thread.sleep(500);
                
            }
            catch (InterruptedException e) {
            
                // catching the exception
                System.err.println("Program has stopped.");
            }
        }
        getResult();
    }

    /**
     * Starts the game and manages the game loop.
     */
    public void GameStart(){

        start();

        while (!isCriticalResourceEmpty()){
            
            System.out.println("_ - _ - _ - _ - _- _ - _- _ - _- _ - _- _ - _- _ - _");
            System.out.println("Options:");
            System.out.println("1. Collect resources: Wood, Iron, Chicken");
            System.out.println("2. Add a new Generator: Lumbermill , Iron Generator, Chicken Farm");
            System.out.println("3. Eat a Chicken.");
            System.out.println("4. View Resources");
            System.out.println("5. View Generators");
            System.out.println("6. view current score");
            System.out.println("7. Quit game");
            System.out.print("Choose an option: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("_ - _ - _ - _ - _- _ - _- _ - _- _ - _- _ - _- _ - _");
                    System.out.println("Which Resource would you like to collect?\n1. Wood\n2.Iron\n3.Chicken");
                    int resNum = input.nextInt();
                    System.out.println("____________________");
                    switch (resNum){
                        
                        case 1:
                            System.out.println("+1 Wood");
                            wood.add(1);
                            break;
                        case 2:
                        System.out.println("+1 Iron");
                            iron.add(1);
                            break;
                        case 3:
                            chickenCatch();
                            break;
                    }
                    
                    break;
                case 2:
                    System.out.println("_ - _ - _ - _ - _- _ - _- _ - _- _ - _- _ - _- _ - _");
                    System.out.println("Which Generator would you like to Build?\n1. Lumbermill\n2.Iron Generator\n3.Chicken Farm");
                    int genNum = input.nextInt();
                    switch (genNum){
                        case 1:
                            constructLumbermill();
                            break;
                        case 2:
                            constructIronGenerator();
                            break;
                        case 3:
                            constructChickenFarm();
                            break;
                    }
                    break;

                case 3: 
                    eatChicken();
                    break;

                case 4:
                    viewResources();
                    break;

                case 5:
                    viewGenerators();
                    break;

                case 6:
                    getScoreImpact();
                    break;

                case 7: 
                    getScoreImpact();
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        getResult();  
    }

    /**
     * game results when health reaches zero, immediately exits game.
    */
    public void getResult(){
        getScoreImpact();
        System.out.println("Game Over! You ran out of Health.");
        System.out.println("You survived for " + dayTracker() + " DAYS.");
        System.exit(0);
    }

    /**
     * Main method to run the game
    */
    public static void main(String[]args) {

        TextManagementGame gameTest = new TextManagementGame();

        System.out.println("Welcome to the Text Management Game!");
        gameTest.starterResource();
        
        gameTest.GameStart();
    }

}

