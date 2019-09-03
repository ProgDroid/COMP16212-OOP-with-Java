public class LotteryTestB
{
  public static void main(String args[])
  {
    // Add a speed controller and a lottery GUI
    SpeedController speedController
    = new SpeedController(SpeedController.HALF_SPEED);
    LotteryGUI guiTestB = new LotteryGUI("TV Studio", speedController);

    // Create a worker
    Worker worker = new Worker("Merlin");

    // Create a dramatic game and add it to the GUI
    Game game = new DramaticGame("Lott O'Luck Larry", 49,
                                 "Slippery's Mile", 7);
    guiTestB.addGame(game);
    speedController.delay(40);

    // Make the worker fill the machine
    worker.fillMachine(game);
    speedController.delay(40);

    // Eject a number of balls until the rack is full
    for (int count = 1; count <= game.getRackSize(); count ++)
      game.ejectBall();

  } // main

} // class LotteryTestB
