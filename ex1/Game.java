// Game method of Snake

public class Game
{

// ----------------------------------------------------------------------
// Part a: the score message

// Create a variable for storing the score message; alter the accessor and
// mutator methods to use this variable appropriately and make the getAuthor()
// method return my name

  // The score message variable
  private String scoreMessage;

  public String getScoreMessage()
  {
    return scoreMessage;
  } // getScoreMessage


  public void setScoreMessage(String message)
  {
    scoreMessage = message;
  } // setScoreMessage


  public String getAuthor()
  {
    return "Fernando Macedo Ferreira";
  } // getAuthor


// ----------------------------------------------------------------------
// Part b: constructor and grid accessors

// Create the grid of cells and its associated accessor methods

  // Variables to store the grid size, and the other to store the grid
  private final int gridSize;
  private final Cell[][] grid;

  // Store the required grid size, create the array and assign it to the grid
  // variable. Fill the grid with cells
  public Game(int requiredGridSize)
  {
    gridSize = requiredGridSize;
    grid = new Cell[gridSize][gridSize];
    for (int rowIndex = 0; rowIndex < gridSize; rowIndex++)
      for (int columnIndex = 0; columnIndex < gridSize; columnIndex++)
        grid[rowIndex][columnIndex] = new Cell();
  } // Game

  // Return the grid size
  public int getGridSize()
  {
    return gridSize;
  } // getGridSize

  // Return the a cell with indices x and y
  public Cell getGridCell(int x, int y)
  {
    return grid[x][y];
  } // getGridCell


// ----------------------------------------------------------------------
// Part c: initial game state

// Part c-1: setInitialGameState method

  // This method will invoke two private methods from the next two sub-parts,
  // one to place the food and one to place the snake
  public void setInitialGameState(int requiredTailX, int requiredTailY,
                                  int requiredLength, int requiredDirection)
  {
    // Make all the cells in the grid get the clear cell type
    for (int rowIndex = 0; rowIndex < gridSize; rowIndex++)
      for (int columnIndex = 0; columnIndex < gridSize; columnIndex++)
        grid[rowIndex][columnIndex].setClear();

    // If trees are enabled, plant a single tree
    if (enabledTrees)
      placeTree();

    // Call the method to place the snake
    placeSnake(requiredTailX, requiredTailY, requiredLength,
               requiredDirection);

    // Call the method to place the food
    placeFood();

    // Set the score to zero
    score = 0;

    // Set number of trees to zero
    currentNumberOfTrees = 0;

    // Set the food movement to false
    enabledFoodMovement = false;

  } // setInitialGameState

  // Variable to store the game score
  public static int score;

// ----------------------------------------------------------------------
// Part c-2 place food

  // Variables to store the X and Y positions of the food

  private int xPosition;
  private int yPosition;

  // Method to place the food in the field. This will need to find a randomly
  // chosen clear cell, and then set the type of that to 'food'
  private void placeFood()
  {
    do
    {
      xPosition = (int) (Math.random() * gridSize);
      yPosition = (int) (Math.random() * gridSize);
    } while (grid[xPosition][yPosition].getType() != Cell.CLEAR);
    // Set the type of the cell to food
    grid[xPosition][yPosition].setFood();

  } // placeFood

// ----------------------------------------------------------------------
// Part c-3: place snake

  private void placeSnake(int requiredTailX, int requiredTailY,
                          int requiredLength, int requiredDirection)
  {
    // Place tail, set the direction of the snake and the positions of the tail
    xTail = requiredTailX;
    yTail = requiredTailY;
    snakeDirection = requiredDirection;
    // Store the opposite direction of the snake
    oppSnakeDirection = Direction.opposite(snakeDirection);
    grid[xTail][yTail].setSnakeTail(oppSnakeDirection, snakeDirection);

    // Place body and set the length of the snake
    snakeLength = requiredLength;
    // Store the next cell position
    int nextXTail = xTail + Direction.xDelta(snakeDirection);
    int nextYTail = yTail + Direction.yDelta(snakeDirection);

    // Loop to place cells
    for (int index = 1; index <= requiredLength-2; index++)
    {
      // Make the cell at next cell position be a snake body
      grid[nextXTail][nextYTail].setSnakeBody(oppSnakeDirection,
                                              snakeDirection);
      // Update the next cell position
      nextXTail+=Direction.xDelta(snakeDirection);
      nextYTail+=Direction.yDelta(snakeDirection);
    } // for

    // Place head
    xHead = nextXTail;
    yHead = nextYTail;
    grid[xHead][yHead].setSnakeHead(oppSnakeDirection, snakeDirection);

  } // placeSnake

  // Add instance variables to hold:
  // The direction of the snake
  private int snakeDirection;

  // The opposite direction of the snake
  private int oppSnakeDirection;

  // The X and Y positions of the tail of the snake
  private int xTail;
  private int yTail;

  // The X and Y positions of the head of the snake
  private int xHead;
  private int yHead;

  // Length of the snake
  private int snakeLength;

// ----------------------------------------------------------------------
// Part d: set snake direction


  public void setSnakeDirection(int requiredDirection)
  {
    // Set the snake out direction of the head of the snake, to the direction
    // given in the argument. If the required direction is the same as the
    // snake in direction of the snake head, don't permit the direction change
    // and put a suitable message in the score message variable
    if(grid[xHead][yHead].getSnakeInDirection() == requiredDirection)
      setScoreMessage("Oops! This is not possible! Change your direction");
    else
    {
      grid[xHead][yHead].setSnakeOutDirection(requiredDirection);
      snakeDirection = requiredDirection;
    }
  } // setSnakeDirection


// ----------------------------------------------------------------------
// Part e: snake movement

// Part e-1: move method


  public void move(int moveValue)
  {
    // Check if the head of the snake is not bloody: if it is, do nothing
    if (!grid[xHead][yHead].isSnakeBloody())
    {
      // Compute new position of the head
      int xHeadPosition = xHead + Direction.xDelta(snakeDirection);
      int yHeadPosition = yHead + Direction.yDelta(snakeDirection);

      // Check if it's ok to move and whether there is food at the new
      // head position
      if (checkCrashes(xHeadPosition, yHeadPosition))
      {
        boolean isFood;
        if (grid[xHeadPosition][yHeadPosition].getType() == Cell.FOOD)
          isFood = true;
        else
          isFood = false;

        // Move the head to the new position
        moveSnakeHead(xHeadPosition, yHeadPosition);

        // If there was food at the new position eat it, else move the tail
        if (isFood)
          eatFood(moveValue);
        else
          moveSnakeTail();

        // Update head position
        xHead = xHeadPosition;
        yHead = yHeadPosition;
      } // if

      // Move food
      if (enabledFoodMovement)
        makeFoodMove();
    } // if
  } // move


// ----------------------------------------------------------------------
// Part e-2: move the snake head

  public void moveSnakeHead(int xNewHead, int yNewHead)
  {
    // Make existing head cell into a snake body
    grid[xHead][yHead].setSnakeBody();

    // Make new position into a snake head
    oppSnakeDirection = Direction.opposite(snakeDirection);
    grid[xNewHead][yNewHead].setSnakeHead(oppSnakeDirection, snakeDirection);

  } // moveSnakeHead

// ----------------------------------------------------------------------
// Part e-3: move the snake tail

  public void moveSnakeTail()
  {
    // Clear the current tail cell and turn the next cell along the snake into
    // a tail cell
    Cell oldTail = grid[xTail][yTail];
    oldTail.setClear();
    xTail = xTail + Direction.xDelta(oldTail.getSnakeOutDirection());
    yTail = yTail + Direction.yDelta(oldTail.getSnakeOutDirection());
    grid[xTail][yTail].setSnakeTail();

  } // moveSnakeTail

// ----------------------------------------------------------------------
// Part e-4: check for and deal with crashes

  public boolean checkCrashes(int xNewHead, int yNewHead)
  {
    // Check if the new position of the head is off the side or occupied by
    // a piece of snake. If there is such a problem, then the method must
    // return false as its result, but also make the snake bloody and put a
    // message into the score message indicating the problem

    if ( xNewHead < 0 || yNewHead < 0 ||
         xNewHead >= gridSize || yNewHead >= gridSize)
    {
      // Make head bloody only if the reduced countdown value is 0. Print a
      // message
      if (reduceCountdown())
      {
        grid[xHead][yHead].setSnakeBloody(true);
        setScoreMessage("No, don't leave!");
      } // if
      return false;
    } // if

    if (grid[xNewHead][yNewHead].isSnakeType())
    {
      // Make head and part it crashed into go bloody and print message if the
      // reduced countdown value is 0
      if (reduceCountdown())
      {
        //
        grid[xHead][yHead].setSnakeBloody(true);
        grid[xNewHead][yNewHead].setSnakeBloody(true);
        setScoreMessage("Please don't eat yourself.");
      } // if
      return false;
    } // if

    // Check whether there is a tree at the new head position
    if (grid[xNewHead][yNewHead].getType() == Cell.TREE)
    {
      // Make head bloody and print a message if the reduced countdown value is
      // 0
      if (reduceCountdown())
      {
        grid[xHead][yHead].setSnakeBloody(true);
        setScoreMessage("You just hit a tree!");
      } // if
      return false;
    } // if

    // Reset the countdown value to the countdown start value
    resetCountdown();

    return true;

  } // checkCrashes


// ----------------------------------------------------------------------
// Part e-5: eat the food

  public void eatFood(int moveValue)
  {
    snakeLength++;
    // Increase the score, message the player and place more food in the field
    score += moveValue * ((snakeLength / (gridSize * gridSize / 36 + 1)) +1);
    scIncrease = moveValue *((snakeLength / (gridSize * gridSize / 36 + 1)) +1);
    setScoreMessage("Your score increased by " + scIncrease);
    placeFood();

    //Check if trees are enabled
    if (enabledTrees)
    {
      // Raw score
      rawScore = score;
      // Increase the score gained
      score *= currentNumberOfTrees;

      // Add another tree
      placeTree();

      // Add message to indicate the raw score increase, the number of trees
      // and the actual score increase
      setScoreMessage("Your raw score is " + rawScore + ". There are "
                      + currentNumberOfTrees + " trees in the field. So your "
                      + "score is now " + score);
    } // if

  } // eatFood

  // Variable to hold the raw score
  private static int rawScore;

  // Variable to hold the score increase
  public static int scIncrease;

  public int getScore()
  {
    return score;
  } // getScore


// ----------------------------------------------------------------------
// Part f: cheat

  public void cheat()
  {
    // Score is divided by two, the used is informed about the new score
    // All the bloody cells are made not bloody
    score /=2;
    setScoreMessage("Your score has just been halved. You've lost " + score +
                    " points");
    for (int rowIndex = 0; rowIndex < gridSize; rowIndex++)
      for (int columnIndex = 0; columnIndex < gridSize; columnIndex++)
        grid[rowIndex][columnIndex].setSnakeBloody(false);
    // Reset the countdown value
    resetCountdown();
  } // cheat


// ----------------------------------------------------------------------
// Part g: trees


  public void toggleTrees()
  {
    // Switch trees on if they are off, and off if they are on
    if (enabledTrees)
      {
        enabledTrees = false;
        currentNumberOfTrees = 0;
        // Remove trees
        for (int rowIndex = 0; rowIndex < gridSize; rowIndex++)
          for (int columnIndex = 0; columnIndex < gridSize; columnIndex++)
            if (grid[rowIndex][columnIndex].getType() == Cell.TREE)
              grid[rowIndex][columnIndex].setClear();
      } // if
    else
      {
        enabledTrees = true;
        // Place tree in a clear cell
        placeTree();
      } // else
  } // toggleTrees

  // Store current number of trees
  private static int currentNumberOfTrees;

  // Variable to record whether trees are enabled or not
  private boolean enabledTrees;

  // Method to place a tree in the field. This will need to find a randomly
  // chosen clear cell, and then set the type of that to 'tree'

  //Variables to hold the position of the tree
  private int xTree;
  private int yTree;

  private void placeTree()
  {
    do
    {
      xTree = (int) (Math.random() * gridSize);
      yTree = (int) (Math.random() * gridSize);
    } while (grid[xTree][yTree].getType() != Cell.CLEAR);

    // Set the type of the cell to 'tree'
    grid[xTree][yTree].setTree();
    // Increase the total number of trees
    currentNumberOfTrees++;

  } // placeTree

// ----------------------------------------------------------------------
// Part h: crash countdown

  // Variables to record the crash countdown start value and the current
  // countdown value
  private final int countdownStartValue = 5;
  public static int currentCountdownValue;

  // Private method to reset the countdown to the countdown start
  private void resetCountdown()
  {
    // Check if the current countdown is less than the countdown start and
    // indicate to the player that they have been lucky to escape death
    if (currentCountdownValue < countdownStartValue &&
        currentCountdownValue != 0)
      setScoreMessage("You've been lucky to escape death by " +
                       currentCountdownValue + " moves");
    // Reset the countdown to the countdown start
    currentCountdownValue = countdownStartValue;
  } // resetCountdown

  // Private method to reduce the current countdown by one
  private boolean reduceCountdown()
  {
    // Reduce the current countdown by one
    currentCountdownValue--;
    // If the current countdown is greater than 0, indicate to the player the
    // number of moves they have before death and return false to indicate that
    // the snake is not yet dead
    if (currentCountdownValue > 0)
    {
      setScoreMessage("You have " + currentCountdownValue + " moves left " +
                      "before you die! Move quickly!");
      return false;
    } // if
    // If the countdown value reached 0, then reset to the countdown start
    // value and return true
    else
    {
      currentCountdownValue = countdownStartValue;
      return true;
    } // if
  } // reduceCountdown


// ----------------------------------------------------------------------
// Part i: optional extras

  public String optionalExtras()
  {
    return "  No optional extras defined\n";
  } // optionalExtras

  // Method to burn trees
  public void burnTrees()
  {
    // Compute the next head positions
    int xNewHead = xHead + Direction.xDelta(snakeDirection);
    int yNewHead = yHead + Direction.yDelta(snakeDirection);
    // If the next cell is a tree and is not off the grid
    if ( !(xNewHead < 0 || yNewHead < 0 ||
         xNewHead >= gridSize || yNewHead >= gridSize) &&
         grid[xNewHead][yNewHead].getType() == Cell.TREE)
    {
      // Set the cell to be clear and decrement the total number of trees
      grid[xNewHead][yNewHead].setClear();
      currentNumberOfTrees--;
      // Update score message
      setScoreMessage("Your raw score is " + rawScore + ". There are "
                      + currentNumberOfTrees + " trees in the field. So your "
                      + "score is now " + score);
    } // if
  } // burnTrees

  // Variable to record whether food movement is on or off
  private boolean enabledFoodMovement;

  // Method to toggle food movement
  public void toggleFoodMovement()
  {
    // Make food move if the method is off, and off if it's on
    if (enabledFoodMovement)
      enabledFoodMovement = false;
    else
      enabledFoodMovement = true;
  } // moveFood

  // Method to move the food
  private void makeFoodMove()
  {
    // Move the cell representing the food
    // Compute direction for the food to move in
    int xNewFoodPosition = xPosition + Direction.xDelta(snakeDirection);
    int yNewFoodPosition = yPosition + Direction.yDelta(snakeDirection);

    // Calculate distance between new food position and snake head
    double newDistance = Math.sqrt(Math.pow((xHead - xNewFoodPosition),2) +
                                   Math.pow((yHead - yNewFoodPosition), 2));
    // Calculate distance between food position and snake head
    double distance = Math.sqrt(Math.pow((xPosition - xHead),2) +
                                Math.pow((yPosition - yHead), 2));

    // Move if the cell it would move to exists, is clear and is not
    // geometrically nearer to the snake head than the food's current position
    if (!(xNewFoodPosition < 0 || yNewFoodPosition < 0 ||
          xNewFoodPosition >= gridSize || yNewFoodPosition >= gridSize)
          && grid[xNewFoodPosition][yNewFoodPosition].getType() == Cell.CLEAR
          && newDistance > distance)
    {
      // Update the food position
      grid[xPosition][yPosition].setClear();
      xPosition = xNewFoodPosition;
      yPosition = yNewFoodPosition;
      grid[xPosition][yPosition].setFood();
    } // if
    else
    {
        // Turn right if the new position is closer
        int newDirection = Direction.rightTurn(snakeDirection);
        xNewFoodPosition = xPosition + Direction.xDelta(newDirection);
        yNewFoodPosition = yPosition + Direction.yDelta(newDirection);

        if (!(xNewFoodPosition < 0 || yNewFoodPosition < 0 ||
              xNewFoodPosition >= gridSize || yNewFoodPosition >= gridSize)
            && grid[xNewFoodPosition][yNewFoodPosition].getType() == Cell.CLEAR)
        {
          // Update the food position
          grid[xPosition][yPosition].setClear();
          xPosition = xNewFoodPosition;
          yPosition = yNewFoodPosition;
          grid[xPosition][yPosition].setFood();
       }
    } // else
  } // makeFoodMove

  public void optionalExtraInterface(char c)
  {
    if (c == 'b')
      burnTrees();
    else if (c == 'm')
      toggleFoodMovement();
    else
      setScoreMessage("Key " + new Character(c).toString()
                      + " is unrecognised (try h)");
  } // optionalExtraInterface

} // class Game
