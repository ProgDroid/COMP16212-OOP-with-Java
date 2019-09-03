import java.awt.Color;

public class MagicBall extends Ball
{
  // All the states a magic ball can be in
  private static final int NORMAL = 0;
  private static final int INVISIBLE = 1;
  private static final int FLASHING = 2;
  private static final int COUNTING = 3;

  // The state of the magic ball
  private int stateOfMagicBall;

  // Instance variable to store the last value returned by getValue()
  private int lastValue = getValue();

  // Instance of an image for magic ball
  private MagicBallImage image;

  // Override makeImage to ensure that magic balls are shown using an
  // instance of MagicBallImage
  public MagicBallImage makeImage()
  {
    image =  new MagicBallImage(this);
    return image;
  } // makeImage

  // Constructor for MagicBall
  public MagicBall(int requiredValue, Color requiredColour)
  {
    super(requiredValue, requiredColour);
    // Set the state of the magic ball to be normal
    stateOfMagicBall = NORMAL;
  } // constructor method

  // Method that makes the state of the magic ball to change to the next state
  // or go back to the last state after the last if spellNumber has the value
  // 1, or change the magic ball to a normal state if spellNumber is 2
  // If the state of the ball changes, update the image
  public void doMagic(int spellNumber)
  {
    if(spellNumber == 1)
      if(stateOfMagicBall == COUNTING)
      {
        stateOfMagicBall = NORMAL;
        image.update();
      }
      else
      {
        stateOfMagicBall++;
        image.update();
      }
    if(spellNumber == 2)
    {
      stateOfMagicBall = NORMAL;
      image.update();
    }
    // else - do nothing
  } // doMagic

  // Method to check if the magic ball is visible or not
  public boolean isVisible()
  {
    if(stateOfMagicBall == INVISIBLE)
      return false;
    else
      return true;
  } // isVisible

  // Method to check if the magic ball is flashing or not
  public boolean isFlashing()
  {
    if(stateOfMagicBall == FLASHING || stateOfMagicBall == COUNTING)
      return true;
    return false;
  } // isFlashing

  // Overwrite the getValue() method to return the numeric value of the ball
  // if the ball is not in a changing state, otherwise increase the value
  // that appears
  public int getValue()
  {
    if(stateOfMagicBall == COUNTING)
    {
      if(lastValue < 99)
        return lastValue++;
      else
      {
        lastValue = 0;
        return lastValue;
      }
    } // if COUNTING
    return super.getValue();
  } // getValue

} // class MagicBall
