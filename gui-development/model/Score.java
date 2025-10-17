package model;

/**
 * Score keeps track of the total resource score each time a resource is added.
 * @author V. Miranda-Calleja
 */
public interface Score {
    
    void score(int amount);

    int scoreImpact();

}