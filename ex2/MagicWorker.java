import java.awt.Color;
import java.util.ArrayList;

public class MagicWorker extends Worker
{
  // Constructor method
  public MagicWorker(String name)
  {
    super(name);
  } // constructor

  // Get person type method
  public String getPersonType()
  {
    return "Magic Worker";
  } // getPersonType

  // Get class hierarchy method
  public String getClassHierarchy()
  {
    return "Magic Worker" + super.getClassHierarchy();
  } // getClassHierarchy

  // Override makeImage method in order for a Magic Worker to have an image
  // from the MagicWorkerImage class
  public MagicWorkerImage makeImage()
  {
    return new MagicWorkerImage(this);
  } // makeImage

  // Array list consisting of all magic balls
  private ArrayList< MagicBall > allMagicBalls =
          new ArrayList< MagicBall >();

  // Method to make a new magic ball
  public Ball makeNewBall(int index, Color requiredColour)
  {
    MagicBall magicBall = new MagicBall(index, requiredColour);
    allMagicBalls.add(magicBall);
    return magicBall;
  } // makeNewBall

  // doMagic method
  public void doMagic(int spellNumber)
  {
    for(MagicBall magicBall : allMagicBalls)
      magicBall.doMagic(spellNumber);
  } // doMagic
} // class MagicWorker
