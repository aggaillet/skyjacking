package outputFeature;

import java.util.ArrayList;

public class MainTest {

    private static String pathToLogDirectory = "./log";

    public static void main(String[] args) {
        OutputData[] chosenFromConfig = new OutputData[10]; // change 10 to the max nbr of possible output data (ensure in config reader that no duplicates can be input into the table)
        chosenFromConfig[0] = OutputData.CURRENT_POSITION;
        chosenFromConfig[1] = OutputData.SPOOFED_TRAJ;

        Logger testLog = new Logger(pathToLogDirectory, chosenFromConfig);

        testLog.write(OutputData.INITIAL_TRAJ, "traj initiale, PAS VALIDE CAR PAS DANS LA CONFIG");

        testLog.write(OutputData.CURRENT_POSITION, "DRONE IS HERE");
        testLog.write(OutputData.CURRENT_POSITION, "NOW IT IS HERE");
        testLog.write(OutputData.SPOOFED_TRAJ, "NEW TRAJ");

        testLog.terminateLogger();    //close streams
    }
}
