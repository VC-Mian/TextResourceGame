/**
 * The Event class represents an event where there is a probability of it triggering and causing damage to the player.
 * Resource Management Game - Console Version 1.0
 * @author V. Miranda-Calleja
 */
import java.util.Random;

public class Event implements Score{
    private String eventName;
    private int werewolfDamage;
    private int score;

    /**
     * initalizes teh event in constructor.
     */
    public Event() {
        this.eventName = "Werewolf Rush";
        this.werewolfDamage = 15;
        score = 0;
    }

    /**
     * Gets the name of the event.
     * @return the name of the event
     */
    public String getEventName() {
        return eventName;
    }

        /**
     * Gets the damage of the event.
     * @return the int damage of the event
     */
    public int getWerewolfDamage(){
        return werewolfDamage;
    }

    /**
     * Event Message when triggered
     * @return the message of the event
     */
    public String eventMessage(){
        return "    A Werewolf inflicted damage on you!\n             -15 health";
    }

    /**
     * sums up resource score number
     * @param amount
     */
    @Override
    public void score(int amount){}

    /**
     * returns resource score number
     * @return score
     */
    @Override
    public int scoreImpact(){
        return score;
    }
}