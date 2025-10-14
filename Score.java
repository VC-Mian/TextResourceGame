/**
 * Score keeps track of the total resource score each time a resource is added.
 * Resource Management Game - Console Version 1.0
 * @author V. Miranda-Calleja
 */
public interface Score {
    
    void score(int amount);

    int scoreImpact();

}