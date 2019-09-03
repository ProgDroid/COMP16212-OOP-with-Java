import java.awt.Color;

public class LotteryTestD
{
  public static void main(String args[])
  {
    // Add a speed controller and a lottery GUI
    SpeedController speedController
    = new SpeedController(SpeedController.HALF_SPEED);
    LotteryGUI guiTestD = new LotteryGUI("TV Studio", speedController);

    // Create two magic workers
    MagicWorker worker1 = new MagicWorker("Merlin");
    MagicWorker worker2 = new MagicWorker("Jim");

    // Add magic workers to the gui
    guiTestD.addPerson(worker1);
    speedController.delay(40);
    guiTestD.addPerson(worker2);
    speedController.delay(40);

    // Create two games and add them to the GUI
    Game game1 = new Game("Lott O'Luck Larry", 12,
                          "Slippery's Mile", 4);
    guiTestD.addGame(game1);
    speedController.delay(40);

    Game game2 = new Game("Second Time Lucky", 34,
                          "Oooz OK Lose", 6);
    guiTestD.addGame(game2);
    speedController.delay(40);

    // Make the workers fill the machines
    worker1.fillMachine(game1);
    speedController.delay(40);
    worker2.fillMachine(game2);
    speedController.delay(40);

    // Eject balls from both machines
    for(int count = 1; count <= game1.getRackSize(); count ++)
      game1.ejectBall();
    for(int count = 1; count <= game2.getRackSize(); count ++)
      game2.ejectBall();

  } // main
} // class LotteryTestC
