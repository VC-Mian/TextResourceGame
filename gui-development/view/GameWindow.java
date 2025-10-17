package view;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import model.GameState;

/**
 * Main game window UI for the Resource Management Game.
 * Displays resources, generators, and provides player actions.
 * Features automatic day/night cycle with timer.
 * @author V. Miranda-Calleja
 */
public class GameWindow {
    
    private BorderPane root;
    private GameState gameState;
    
    // UI Components
    private Label dayLabel;
    private Label timeOfDayLabel;
    private Label woodLabel;
    private Label ironLabel;
    private Label chickenLabel;
    private Label healthLabel;
    private Label scoreLabel;
    private TextArea eventLog;
    private VBox generatorList;
    
    // Timer components
    private Timeline dayNightCycle;
    private String currentTimeOfDay = "Day";
    private int cyclePhase = 0; // 0=Day, 1=Evening, 2=Night
    
    public GameWindow(GameState gameState) {
        this.gameState = gameState;
        this.root = new BorderPane();
        
        setupUI();
        setupListeners();
        updateDisplay();
        startDayNightCycle();
    }
    
    /**
     * Starts the automatic day/night cycle timer
     */
    private void startDayNightCycle() {
        // Create timeline that runs every 5 seconds (adjust as needed)
        dayNightCycle = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            advanceCycle();
        }));
        dayNightCycle.setCycleCount(Timeline.INDEFINITE);
        dayNightCycle.play();
        
        logEvent("Day/Night cycle started! Time advances automatically.");
    }
    
    /**
     * Advances through the day cycle phases
     */
    private void advanceCycle() {
        if (gameState.isGameOver()) {
            dayNightCycle.stop();
            return;
        }
        
        switch (cyclePhase) {
            case 0: // Day -> Evening
                currentTimeOfDay = "Evening";
                cyclePhase = 1;
                timeOfDayLabel.setText("Time: Evening");
                timeOfDayLabel.setStyle("-fx-text-fill: #ff9933; -fx-font-size: 16px; -fx-font-weight: bold;");
                logEvent("\n=== Evening ===");
                logEvent("The event will begin soon...");
                
                // Player gets hungry
                gameState.getHealth().consume(2);
                logEvent("You are hungry. -2 Health");
                logEvent("Health: " + gameState.getHealth().getQuantity());
                updateDisplay();
                break;
                
            case 1: // Evening -> Night
                currentTimeOfDay = "Night";
                cyclePhase = 2;
                timeOfDayLabel.setText("Time: Night");
                timeOfDayLabel.setStyle("-fx-text-fill: #6666ff; -fx-font-size: 16px; -fx-font-weight: bold;");
                logEvent("\n=== Night ===");
                
                // Trigger night event
                String eventResult = gameState.triggerNightEvent();
                logEvent(eventResult);
                
                if (gameState.isGameOver()) {
                    dayNightCycle.stop();
                    showGameOver();
                }
                updateDisplay();
                break;
                
            case 2: // Night -> New Day
                cyclePhase = 0;
                boolean continuing = gameState.advanceDay();
                
                if (continuing) {
                    currentTimeOfDay = "Day";
                    timeOfDayLabel.setText("Time: Day");
                    timeOfDayLabel.setStyle("-fx-text-fill: #ffff00; -fx-font-size: 16px; -fx-font-weight: bold;");
                    logEvent("\n=== Day " + gameState.getDay() + " ===");
                    
                    if (gameState.getDay() > 1) {
                        logEvent("Morning: Resources generated from generators");
                    }
                    updateDisplay();
                } else {
                    dayNightCycle.stop();
                    showGameOver();
                }
                break;
        }
    }
    
    /**
     * Sets up the main UI layout
     */
    private void setupUI() {
        root.setStyle("-fx-background-color: #2b2b2b;");
        
        // Top: Day counter and score
        root.setTop(createTopPanel());
        
        // Left: Resources display
        root.setLeft(createResourcePanel());
        
        // Center: Action buttons
        root.setCenter(createActionPanel());
        
        // Right: Generators list
        root.setRight(createGeneratorPanel());
        
        // Bottom: Event log
        root.setBottom(createEventLogPanel());
    }
    
    /**
     * Creates the top panel with day counter, time of day, and score
     */
    private HBox createTopPanel() {
        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(15));
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setStyle("-fx-background-color: #1a1a1a;");
        
        dayLabel = new Label("Day: 0");
        dayLabel.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        timeOfDayLabel = new Label("Time: Day");
        timeOfDayLabel.setStyle("-fx-text-fill: #ffff00; -fx-font-size: 16px; -fx-font-weight: bold;");
        
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-text-fill: #ffd700; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        topPanel.getChildren().addAll(dayLabel, new Label("  |  "), timeOfDayLabel, new Label("  |  "), scoreLabel);
        return topPanel;
    }
    
    /**
     * Creates the resource display panel
     */
    private VBox createResourcePanel() {
        VBox resourcePanel = new VBox(15);
        resourcePanel.setPadding(new Insets(20));
        resourcePanel.setStyle("-fx-background-color: #353535; -fx-border-color: #555555; -fx-border-width: 2;");
        resourcePanel.setPrefWidth(200);
        
        Label title = new Label("RESOURCES");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        woodLabel = createResourceLabel("🌲 Wood: 0");
        ironLabel = createResourceLabel("⚙️ Iron: 0");
        chickenLabel = createResourceLabel("🐔 Chicken: 0");
        healthLabel = createResourceLabel("❤️ Health: 50");
        
        resourcePanel.getChildren().addAll(title, new Separator(), woodLabel, ironLabel, chickenLabel, healthLabel);
        return resourcePanel;
    }
    
    /**
     * Creates the action buttons panel
     */
    private VBox createActionPanel() {
        VBox actionPanel = new VBox(15);
        actionPanel.setPadding(new Insets(20));
        actionPanel.setAlignment(Pos.CENTER);
        
        Label title = new Label("ACTIONS");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Collect Resources Section
        Label collectLabel = new Label("Collect Resources:");
        collectLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 14px;");
        
        Button collectWoodBtn = createActionButton("Collect Wood");
        collectWoodBtn.setOnAction(e -> {
            gameState.collectWood();
            logEvent("+1 Wood");
        });
        
        Button collectIronBtn = createActionButton("Collect Iron");
        collectIronBtn.setOnAction(e -> {
            gameState.collectIron();
            logEvent("+1 Iron");
        });
        
        Button catchChickenBtn = createActionButton("Catch Chicken");
        catchChickenBtn.setOnAction(e -> {
            String result = gameState.catchChicken();
            logEvent(result);
        });
        
        // Build Generators Section
        Label buildLabel = new Label("\nBuild Generators:");
        buildLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 14px;");
        
        Button buildLumbermillBtn = createActionButton("Build Lumbermill");
        buildLumbermillBtn.setOnAction(e -> {
            gameState.constructLumbermill();
            logEvent("Attempted to build Lumbermill");
        });
        
        Button buildIronGenBtn = createActionButton("Build Iron Generator");
        buildIronGenBtn.setOnAction(e -> {
            gameState.constructIronGenerator();
            logEvent("Attempted to build Iron Generator");
        });
        
        Button buildChickenFarmBtn = createActionButton("Build Chicken Farm");
        buildChickenFarmBtn.setOnAction(e -> {
            gameState.constructChickenFarm();
            logEvent("Attempted to build Chicken Farm");
        });
        
        // Other Actions Section
        Label otherLabel = new Label("\nOther Actions:");
        otherLabel.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 14px;");
        
        Button eatChickenBtn = createActionButton("Eat Chicken");
        eatChickenBtn.setOnAction(e -> {
            String result = gameState.eatChicken();
            logEvent(result);
        });
        
        actionPanel.getChildren().addAll(
            title,
            new Separator(),
            collectLabel,
            collectWoodBtn, collectIronBtn, catchChickenBtn,
            buildLabel,
            buildLumbermillBtn, buildIronGenBtn, buildChickenFarmBtn,
            otherLabel,
            eatChickenBtn
        );
        
        return actionPanel;
    }
    
    /**
     * Creates the generator list panel
     */
    private VBox createGeneratorPanel() {
        VBox genPanel = new VBox(10);
        genPanel.setPadding(new Insets(20));
        genPanel.setStyle("-fx-background-color: #353535; -fx-border-color: #555555; -fx-border-width: 2;");
        genPanel.setPrefWidth(200);
        
        Label title = new Label("GENERATORS");
        title.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 18px; -fx-font-weight: bold;");
        
        generatorList = new VBox(5);
        generatorList.setStyle("-fx-text-fill: #cccccc;");
        
        ScrollPane scrollPane = new ScrollPane(generatorList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #353535; -fx-background-color: #353535;");
        
        genPanel.getChildren().addAll(title, new Separator(), scrollPane);
        return genPanel;
    }
    
    /**
     * Creates the event log panel
     */
    private HBox createEventLogPanel() {
        HBox logPanel = new HBox();
        logPanel.setPadding(new Insets(10));
        logPanel.setStyle("-fx-background-color: #1a1a1a;");
        
        eventLog = new TextArea();
        eventLog.setEditable(false);
        eventLog.setPrefHeight(120);
        eventLog.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: #00ff00; -fx-font-family: monospace;");
        eventLog.setText("Welcome to Resource Management Game!\n");
        
        logPanel.getChildren().add(eventLog);
        HBox.setHgrow(eventLog, Priority.ALWAYS);
        
        return logPanel;
    }
    
    /**
     * Helper to create styled resource labels
     */
    private Label createResourceLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px;");
        return label;
    }
    
    /**
     * Helper to create styled action buttons
     */
    private Button createActionButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-size: 12px;");
        return btn;
    }
    
    /**
     * Shows game over dialog
     */
    private void showGameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(gameState.getGameOverReason());
        alert.setContentText("You survived for " + gameState.getDay() + " days!\nFinal Score: " + gameState.getTotalScore());
        alert.showAndWait();
        System.exit(0);
    }
    
    /**
     * Logs a message to the event log
     */
    private void logEvent(String message) {
        eventLog.appendText(message + "\n");
        eventLog.setScrollTop(Double.MAX_VALUE); // Auto-scroll to bottom
    }
    
    /**
     * Sets up listeners to update UI when game state changes
     */
    private void setupListeners() {
        gameState.addListener(state -> updateDisplay());
    }
    
    /**
     * Updates all UI elements with current game state
     */
    private void updateDisplay() {
        dayLabel.setText("Day: " + gameState.getDay());
        woodLabel.setText("🌲 Wood: " + gameState.getWood().getQuantity());
        ironLabel.setText("⚙️ Iron: " + gameState.getIron().getQuantity());
        chickenLabel.setText("🐔 Chicken: " + gameState.getChicken().getQuantity());
        healthLabel.setText("❤️ Health: " + gameState.getHealth().getQuantity());
        scoreLabel.setText("Score: " + gameState.getTotalScore());
        
        updateGeneratorList();

        
        /* PROMPTS GAME OVER
        if (gameState.getHealth().isGameOver()) {
        showGameOver();
        }
        '''*/
    }
    
    /**
     * Updates the generator list display
     */
    private void updateGeneratorList() {
        generatorList.getChildren().clear();
        
        if (gameState.getGenerators().isEmpty()) {
            Label noGen = new Label("No generators built yet");
            noGen.setStyle("-fx-text-fill: #888888; -fx-font-style: italic;");
            generatorList.getChildren().add(noGen);
        } else {
            for (var gen : gameState.getGenerators()) {
                Label genLabel = new Label("• " + gen.getName() + " (x" + gen.getrateConstructed() + ")");
                genLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 14px;");
                generatorList.getChildren().add(genLabel);
            }
        }
    }

    public BorderPane getRoot() {
        return root;
    }
}