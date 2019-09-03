public class LotteryTestA
{
  public static void main(String args[])
  {
    // Add a speed controller and a lottery GUI
    SpeedController speedController
    = new SpeedController(SpeedController.HALF_SPEED);
    LotteryGUI guiTestA = new LotteryGUI("TV Studio", speedController);

    // Add a TV host
    TVHost tvHost = new TVHost("Terry Bill Woah B'Gorne");
    guiTestA.addPerson(tvHost);

    // Add an audience member
    AudienceMember audienceMember = new AudienceMember("Ivana Di Yowt");
    guiTestA.addPerson(audienceMember);

    // Add a punter
    Punter punter = new Punter("Ian Arushfa Rishly Ving");
    guiTestA.addPerson(punter);

    // Add a psychic
    Psychic psychic = new Psychic("Miss T. Peg de Gowt");
    guiTestA.addPerson(psychic);

    // Add a director
    Director director = new Director("Sir Lance Earl Otto");
    guiTestA.addPerson(director);

    // Add a clever punter
    CleverPunter cleverPunter = new CleverPunter("Wendy Athinkile-Win");
    guiTestA.addPerson(cleverPunter);

    // Add a trainee worker
    Worker traineeWorker = new TraineeWorker("Jim", 2.5);
    guiTestA.addPerson(traineeWorker);

    // Add a teenager
    Teenager teenager = new Teenager("Luke");
    guiTestA.addPerson(teenager);

    // Add a worker
    Worker worker = new Worker("Merlin");
    guiTestA.addPerson(worker);

  } // main

} // class LotteryTestA
