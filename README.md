# Text Management Game

A command-line resource management game built in Java that challenges players to survive and accumulate resources while managing health and defending against random events.

## 🎮 Game Overview

Survive as long as possible by gathering resources, building generators, and managing your health. Accumulate the highest resource score before your health depletes or test your strategic skills in this dynamic survival challenge.

## 🎯 Objective

Maximize your resource score and survive as many days as possible by:
- Collecting and managing three primary resources: **Wood**, **Iron**, and **Chicken**
- Building generators to automate resource production
- Maintaining health by consuming chicken
- Surviving random werewolf attacks

## 🕹️ Gameplay Features

### Resource Management
- **Three Resource Types**: Wood, Iron, and Chicken
- **Health System**: Consume chicken to replenish health
- **Generator System**: Build unlimited generators to automate resource collection
- **Dynamic Scoring**: Final score based on total resources accumulated

### Time & Events
- **Day/Night Cycle**: Track time of day with system notifications
- **Hunger Warnings**: Receive alerts when health is running low
- **Werewolf Rush Event**: Random encounters that severely damage the player
  - Triggered on even or odd turns based on chance
  - Strategic timing can help avoid damage

### User Interface
- **Command-Line Interface**: Simple numbered menu for all actions
- **Real-Time Updates**: Immediate feedback on resource counts and game status
- **End Game Statistics**: View total score and days survived

## 🛠️ Technical Implementation

### Object-Oriented Design
This project demonstrates core OOP principles:

- **Encapsulation**: Private variables with public methods protect game state
- **Inheritance**: Structured class hierarchy for resources and generators
- **Polymorphism**: Flexible component interactions
- **Modular Architecture**: Distinct classes for resources, generators, and events

### Key Technologies
- **Java**: Core programming language
- **Multi-threading**: Timed events run asynchronously for smooth gameplay
- **Scanner Class**: Handles user input for interactive CLI experience
- **Randomization**: Dynamic event generation and gameplay variety

### Technical Highlights
- Thread management for concurrent event handling
- Conditional logic for complex game mechanics
- Resource balancing algorithms
- State management across game sessions

## 🎓 Learning Outcomes

Through this project, I developed proficiency in:

- **Object-Oriented Programming**: Practical application of encapsulation, inheritance, and polymorphism
- **Multi-threading**: Managing concurrent operations and timed events
- **Game Design**: Balancing mechanics, resource systems, and player experience
- **Code Organization**: Creating modular, maintainable, and reusable components
- **User Experience**: Building intuitive command-line interfaces
- **Problem Solving**: Implementing complex game logic and state management

## 🚀 How to Play

1. Clone the repository
2. Compile and run the Java application
3. Use numbered commands to select actions
4. Collect resources and build generators
5. Monitor your health and eat chicken when hungry
6. Watch for werewolf warnings and prepare accordingly
7. Survive as long as possible and maximize your score!

## 📊 Game Mechanics

- **No Resource Limits**: Build as many generators as you want
- **Flexible Pacing**: Play at your own speed (just watch your health!)
- **Risk vs Reward**: Balance resource collection with health management
- **Strategic Depth**: Plan generator investments and resource allocation

## 🏆 Victory Conditions

The game ends when:
- Health reaches zero
- Player chooses to quit

Your final score displays:
- Total resource points accumulated
- Number of days survived

## 🔮 Future Enhancements

### Planned Features
- **Visual Interface**: Transition from command-line to a graphical user interface
  - JavaFX or Swing-based GUI implementation
  - Animated resource collection and generator visuals
  - Visual health bar and resource counters
  - Sprite-based werewolf encounter animations
  - Day/night cycle visual effects
- **Enhanced Graphics**: Add character sprites, environmental art, and UI elements
- **Sound Effects**: Audio feedback for actions, events, and warnings
- **Expanded Gameplay**: Additional resources, events, and generator types
- **Save System**: Persistent game state between sessions

---

*This project showcases practical Java development skills and game design principles, demonstrating my ability to create engaging, well-structured applications from concept to completion.*
