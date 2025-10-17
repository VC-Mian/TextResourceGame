package model;

/**
 * The Resource class represents a generic resource in the game.
 * Resources have a name, a quantity, and a status of critical or not critical.
 * @author V. Miranda-Calleja
 */
import java.util.ArrayList;
import java.util.Collections; 

public class Resource implements Comparable<Resource>, Score{

    private String name;
    private int quantity;
    private boolean isCritical;
    private int score;

    /**
     * Creates a new Resource with the given name and initializes the quantity to 0.
     * @param n the name of the resource
     * @param q the quanitity of the resource
     * @param isCrit if resource is critical
     */
    public Resource(){}
    
    public Resource(String n, int q, boolean isCrit){
        this.name = n;
        this.quantity = q;
        this.isCritical = isCrit;
        score = 1;
    }

    /**
     * Gets the name of the resource.
     * @return name- the name of the resource
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the resource.
     * @param n- the name of the resource
     */
    public void setName(String n) {
        this.name = n;
    }

    /**
     * Gets the quantity of the resource.
     * @return quantity- the quantity of the resource
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets the quantity of the resource.
     * @param q- the quantity of the resource
     */
    public void setQuantity(int q) {
        this.quantity = q;
    }

    /**
     * Reports if a resource is critical. If a rsource is critical, reaching 0 ends the game.
     * @return isCritical- if the resource is critical
     */
    public boolean isCritical(){
        return isCritical;
    }

    /**
     * Sets if a given resource is critical.
     * @param boolean- value for isCritical
     */
    public void setIsCrticial(boolean isCritical){
        this.isCritical = isCritical;
    }

    /**
     * Adds the specified amount to the quantity of the resource.
     * @param amount- the amount to add
     */
    public void add(int amount) {
        quantity += amount;
        score(amount);
    }

    /**
     * Consumes the specified amount of the resource if available. Sets the resource to 0 if there is not enough to consume.
     * @param amount- the amount to consume
     */
    public void consume(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
        }else{
            quantity = 0;
            System.out.println("Not enough " + name + " to consume.");
        }
    }

    public String toString(){
        return "Resource: " + getName() + "\nQuantity: " + getQuantity() + "\nneed? " + isCritical();
    }

    /**
    * compares two different Resource objects quantity to each other
    * @param other- the resource object
    */
    @Override
    public int compareTo(Resource other) {
        // Compare based on quantity
        return Integer.compare(this.quantity, other.quantity);
    }

    /**
     * sums up resource score number
     * @param amount- amount added to score
     */
    @Override
    public void score(int amount) {
        score += amount;
    }

    /**
     * gets the resource score number
     * @return score- total resource score
     */
    @Override
    public int scoreImpact(){
        return score;
    }
}