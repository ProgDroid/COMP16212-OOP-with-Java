// Dramatic lottery machine to be used in a dramatic game
public class DramaticMachine extends Machine
{
  // Constructor for a dramatic machine
  public DramaticMachine(String name, int size)
  {
    super(name, size);
  } // constructor

  // Returns the name of the type of Machine
  public String getType()
  {
    return "Dramatic lottery machine";
  } // getType

  // Randomly chooses a ball in the machine
  // Starting with the first ball in the machine, each ball will be
  // flashed in turn, until the one to be ejected is reached
  // At that point, the chosen ball is flashed and ejected as before
  // If the machine is empty then it has no effect, and returns null
  public Ball ejectBall()
  {
    if(getNoOfBalls() <= 0)
      return null;
    else
    {
      // Math.random() * getNoOfBalls yields a number which is
      // >=0 and < number of balls
      int ejectedBallIndex = (int)(Math.random() * getNoOfBalls());

      // Flash
      for(int index = 0; index <= ejectedBallIndex-1; index++)
      {
        Ball ejectedBall = getBall(index);
        ejectedBall.flash(4, 5);
      } // for

      Ball ejectedBall = getBall(ejectedBallIndex);
      ejectedBall.flash(4, 5);
      swapBalls(ejectedBallIndex, getNoOfBalls() -1);
      removeBall();

      return ejectedBall;

    } // else
  } // ejectBall

} // class DramaticMachine
