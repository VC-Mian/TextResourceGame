import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.GameState;
import view.GameWindow;

/**
 * Main entry point for the graphical version of the Resource Management Game.
 * Resource Management Game - GUI Version
 * @author V. Miranda-Calleja
 */
public class GraphicalGame extends Application {
    
    private GameState gameState;
    private GameWindow gameWindow;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize game state
        gameState = new GameState();
        
        // Create the main game window
        gameWindow = new GameWindow(gameState);
        
        // Set up the scene
        Scene scene = new Scene(gameWindow.getRoot(), 900, 700);
        
        // Configure the stage
        primaryStage.setTitle("Resource Management Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Handle window close
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
