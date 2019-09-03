// Dramatic lottery game class
public class DramaticGame extends Game
{
  // Constructor for a dramatic game
  public DramaticGame(String machineName, int machineSize,
                      String rackName, int rackSize)
  {
    super(machineName, machineSize, rackName, rackSize);  
  } // constructor

  // Overrides makeMachine method
  public Machine makeMachine(String machineName, int machineSize)
  {
    return new DramaticMachine(machineName, machineSize);
  } // makeMachine

} // class DramaticGame
