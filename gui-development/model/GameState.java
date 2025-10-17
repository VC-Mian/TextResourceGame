package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * GameState manages all game data and logic for the GUI version.
 * This class replaces the TextManagementGame's game loop with observable state.
 * Resource Management Game - GUI Version
 * @author V. Miranda-Calleja
 */
public class GameState {
    
    // Game state variables
    private int day;
    private ArrayList<Resource> resources;
    private ArrayList<Generator> generators;
    private boolean gameOver;
    private String gameOverReason;
    
    // Resource objects
    private Wood wood;
    private Iron iron;
    private Chicken chicken;
    private Health health;
    
    // Event system
    private Event event;
    private Random random;
    
    // Listeners for UI updates (we'll add this pattern)
    private ArrayList<GameStateListener> listeners;
    
    /**
     * Creates a new GameState with initial resources
     */
    public GameState() {
        this.day = 0;
        this.resources = new ArrayList<>();
        this.generators = new ArrayList<>();
        this.gameOver = false;
        this.gameOverReason = "";
        this.listeners = new ArrayList<>();
        this.random = new Random();
        this.event = new Event();
        
        // Initialize resources
        initializeResources();
    }
    
    /**
     * Initializes starting resources
     */
    private void initializeResources() {
        wood = new Wood();
        iron = new Iron();
        chicken = new Chicken();
        health = new Health(0);
        
        resources.add(wood);
        resources.add(iron);
        resources.add(chicken);
        resources.add(health);
    }
    
    /**
     * Advances the game by one day.
     * Handles resource generation, health consumption, and events.
     * @return true if game continues, false if game over
     */
    public boolean advanceDay() {
        if (gameOver) return false;
        
        day++;
        
        // Generate resources from generators (skip day 1)
        if (day > 1) {
            generateResources();
        }
        
        // Consume health (player gets hungry)
        health.consume(2);
        
        // Check for game over
        if (isCriticalResourceEmpty()) {
            endGame("You ran out of Health!");
            return false;
        }
        
        notifyListeners();
        return true;
    }
    
    /**
     * Triggers the night event (werewolf attack)
     * @return message describing what happened
     */
    public String triggerNightEvent() {
        int eventChance = random.nextInt(4); // 0-3
        
        if (eventChance % 2 != 0) {
            // Event triggered!
            health.consume(event.getWerewolfDamage());
            
            if (isCriticalResourceEmpty()) {
                endGame("You were killed by a werewolf!");
            }
            
            notifyListeners();
            return "You rolled a " + eventChance + "\nThe Blood Moon has risen...\n" + 
                   event.eventMessage() + "\nHealth: " + health.getQuantity();
        } else {
            // No event
            notifyListeners();
            return "You rolled a " + eventChance + "\nThe Moon Looks Beautiful Tonight.\nHealth: " + health.getQuantity();
        }
    }
    
    /**
     * Generates resources from all active generators
     */
    private void generateResources() {
        for (Generator g : generators) {
            if (g instanceof LumberMill) {
                wood.add(3);
            } else if (g instanceof IronGenerator) {
                iron.add(2);
            } else if (g instanceof ChickenFarm) {
                chicken.add(1);
            }
        }
        notifyListeners();
    }
    
    /**
     * Player manually collects wood
     */
    public void collectWood() {
        wood.add(1);
        notifyListeners();
    }
    
    /**
     * Player manually collects iron
     */
    public void collectIron() {
        iron.add(1);
        notifyListeners();
    }
    
    /**
     * Player attempts to catch a chicken
     * @return message describing the result
     */
    public String catchChicken() {
        // Use the existing Chicken probability logic
        int caught = chicken.chickenCatchProbability();
        notifyListeners();
        
        if (caught == 1) {
            return "You caught a chicken!";
        } else {
            return "The chicken escaped!";
        }
    }
    
    /**
     * Player eats a chicken to restore health
     * @return message describing the result
     */
    public String eatChicken() {
        if (health.getQuantity() >= 50) {
            return "Health is full.";
        } else if (chicken.getQuantity() == 0) {
            return "Not enough Chicken.";
        } else {
            chicken.consume(1);
            health.add(3);
            if (health.getQuantity() > 50) {
                health.setQuantity(50);
            }
            notifyListeners();
            return "You ate a chicken.\nHealth: " + health.getQuantity();
        }
    }
    
    /**
     * Constructs a Lumbermill
     * @return true if successful, false otherwise
     */
    public boolean constructLumbermill() {
        LumberMill lumberMill = new LumberMill();
        lumberMill.getConstructionResources(resources, generators);
        notifyListeners();
        return generators.contains(lumberMill);
    }
    
    /**
     * Constructs an Iron Generator
     * @return true if successful, false otherwise
     */
    public boolean constructIronGenerator() {
        IronGenerator ironGen = new IronGenerator();
        ironGen.getConstructionResources(resources, generators);
        notifyListeners();
        return generators.contains(ironGen);
    }
    
    /**
     * Constructs a Chicken Farm
     * @return true if successful, false otherwise
     */
    public boolean constructChickenFarm() {
        ChickenFarm chickenFarm = new ChickenFarm();
        chickenFarm.getConstructionResources(resources, generators);
        notifyListeners();
        return generators.contains(chickenFarm);
    }
    
    /**
     * Checks if any critical resource is empty
     * @return true if game should end
     */
    private boolean isCriticalResourceEmpty() {
        for (Resource r : resources) {
            if (r.isCritical() && r.getQuantity() == 0) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Ends the game with a reason
     */
    private void endGame(String reason) {
        gameOver = true;
        gameOverReason = reason;
        notifyListeners();
    }
    
    /**
     * Calculates total score
     * @return total resource score
     */
    public int getTotalScore() {
        return wood.scoreImpact() + iron.scoreImpact() + chicken.scoreImpact();
    }
    
    // Getters
    public int getDay() { return day; }
    public ArrayList<Resource> getResources() { return resources; }
    public ArrayList<Generator> getGenerators() { return generators; }
    public Wood getWood() { return wood; }
    public Iron getIron() { return iron; }
    public Chicken getChicken() { return chicken; }
    public Health getHealth() { return health; }
    public boolean isGameOver() { return gameOver; }
    public String getGameOverReason() { return gameOverReason; }
    
    /**
     * Gets sorted resources for display
     */
    public ArrayList<Resource> getSortedResources() {
        ArrayList<Resource> sorted = new ArrayList<>(resources);
        Collections.sort(sorted);
        return sorted;
    }
    
    // Listener pattern for UI updates
    public interface GameStateListener {
        void onGameStateChanged(GameState state);
    }
    
    public void addListener(GameStateListener listener) {
        listeners.add(listener);
    }
    
    public void removeListener(GameStateListener listener) {
        listeners.remove(listener);
    }
    
    private void notifyListeners() {
        for (GameStateListener listener : listeners) {
            listener.onGameStateChanged(this);
        }
    }
}