package model;

import java.util.Random;

/**
 * One of the resources to be used in the game.
 * Resource Management Game - GUI Version
 * @author V. Miranda-Calleja
 */
public class Chicken extends Resource {
    
    public Chicken() {
        super("Chicken", 1, false);
    }

    /**
     * Probability of catching a chicken depending if roll is odd or even.
     * @return 1 if caught, 0 if escaped
     */
    public int chickenCatchProbability() {
        Random random = new Random();
        int chickenChance = random.nextInt(10) + 1; // 1-10
        
        if (chickenChance % 2 == 0) {
            // Even - caught the chicken!
            add(1);
            return 1; // Success
        } else {
            // Odd - chicken escaped
            return 0; // Failure
        }
    }

    public String toString() {
        return super.toString();
    }
}