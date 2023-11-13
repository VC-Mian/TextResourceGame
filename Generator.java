/**
 * The Generator class represents a generic resource generating item in the game.
 * Generators have a name, a construction cost, and a resource production rate.
 * @author V. Miranda-Calleja
 */
import java.util.ArrayList;

abstract public class Generator implements Score{
    private String name;
    private ArrayList<Resource> resources;
    private ArrayList<Generator> generators;
    private int rateConstructed;
    private String product;
    private int score;

    /** testing parameters for lumbermill
     * Creates a new Generator with the given name, resource production rate, Resource product generated.
     *
     * @param name                   the name of the Generator
     * @param rateConstructed      the number of units of this generator constructed at this time
     * @param product                the type of resource this generator produces
     *
     */
    public Generator(String name, int rateConstructed, String product) {
        this.name = name;
        this.rateConstructed = rateConstructed;
        this.product = product;
        score = 0;
    }

    /**
     * Gets the name of the Generator.
     *
     * @return the name of the Generator
     */
    public String getName() {
        return name;
    }

    // sets generators name
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the construction cost of the Generator then makes that generator.
     * @param resources- gets amount of rsource needed to construct generator.
     * @param generators= gets the generator arraylist to add generator to it.
     */
    public abstract void getConstructionResources(ArrayList<Resource> resources, ArrayList<Generator> generators);

    /**
     * Gets the number of units constructed of this Generator.
     * @return rateConstructed- the number of units constructed of the generator
     */
    public int getrateConstructed() {
        return rateConstructed;
    }

    /**
     * sets the number of units constructed of this Generator.
     * @param rateConstructed- sets the number of units constructed of the generator
     */
    public void setrateConstructed(int rateConstructed) {
        this.rateConstructed = rateConstructed;
    }

    /**
     * Gets the resources constructed by this Generator.
     * @return product the name of the resource constructed from the generator
     */
    public String getProduct(){
        return product;
    }

    /**
     * Sets the resources constructed by this Generator.
     * @param product- the name of the resource constructed
     */
    public void setProduct(String product){
        this.product = product;
    }

    public String toString(){
        return "Generator Name: " + getName() + "\nQuanitity constructed: " + getrateConstructed() + "\nProduct: " + getProduct();
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