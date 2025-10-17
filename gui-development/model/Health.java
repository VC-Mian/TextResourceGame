package model;

/**
 * One of the resources to be used in the game. Health is critical in keeping the game running.
 * @author V. Miranda-Calleja
 */
public class Health extends Resource{
    private int minusHealth;

    //constructor to pass int to minus it from total health
    public Health(int mh){
        super("Health", 50, true);
        this.minusHealth = mh;
    }
    
    public String toString(){
        return super.toString();  
    }

    public boolean isGameOver() {
        return getQuantity() <= 0;
    }
}
