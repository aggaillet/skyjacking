package outputFeature;

import java.util.ArrayList;

public class MainTest {

    private static final String pathToLogDirectory = "./log";

    public static void main(String[] args) {
        OutputData[] chosenFromConfig = new OutputData[2];  //ensure no duplicate in config reader/extractor
        chosenFromConfig[0] = OutputData.CURRENT_POSITION;
        chosenFromConfig[1] = OutputData.SPOOFED_TRAJ;

//        Logger testLog = new Logger(pathToLogDirectory, chosenFromConfig);
//
//        testLog.write(OutputData.INITIAL_TRAJ, "traj initiale, PAS VALIDE CAR PAS DANS LA CONFIG");
//
//        testLog.write(OutputData.CURRENT_POSITION, "DRONE IS HERE");
//        testLog.write(OutputData.CURRENT_POSITION, "NOW IT IS HERE");
//        testLog.write(OutputData.SPOOFED_TRAJ, "NEW TRAJ");
//
//        testLog.terminateLogger();    //close streams


        CommandLineInput cli = new CommandLineInput(chosenFromConfig);
        cli.write(OutputData.INITIAL_TRAJ, "traj initiale, PAS VALIDE CAR PAS DANS LA CONFIG");

        cli.write(OutputData.CURRENT_POSITION, "DRONE IS HERE");
        cli.write(OutputData.CURRENT_POSITION, "NOW IT IS HERE");
        cli.write(OutputData.SPOOFED_TRAJ, "NEW TRAJ");
    }
}
