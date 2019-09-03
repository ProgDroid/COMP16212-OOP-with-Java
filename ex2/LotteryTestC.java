import java.awt.Color;

public class LotteryTestC
{
  public static void main(String args[])
  {
    // Add a speed controller and a lottery GUI
    SpeedController speedController
    = new SpeedController(SpeedController.HALF_SPEED);
    LotteryGUI guiTestC = new LotteryGUI("TV Studio", speedController);

    // Create a worker
    Worker worker = new Worker("Merlin");

    // Create a game and add it to the GUI
    Game game = new Game("Lott O'Luck Larry", 49,
                         "Slippery's Mile", 7);
    guiTestC.addGame(game);
    speedController.delay(40);

    // Make the worker fill the machine
    //worker.fillMachine(game);
    //speedController.delay(40);

    // Insert some magic balls
    Color pink = Color.pink;
    int noOfBalls = game.getMachineSize();
    for (int index = 1; index <= noOfBalls; index++)
    {
      MagicBall magicBall = new MagicBall(index, pink);
      game.machineAddBall(magicBall);
    } // for

    // Eject a number of balls until the rack is full
    //for (int count = 1; count <= game.getRackSize(); count ++)
  //    game.ejectBall();

  } // main
} // class LotteryTestC
