package outputFeature;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * This class manage the wrinting of wanted data into a log files.
 * @author Heiarii COLLENOT
 * @version 1.0
 * @since 15/11/2023
 */
public class Logger implements IOutput{

//    private final static String urlDirectory = "src/main/java/outputFeature/logs";
    private String urlToDirectory;
    private final HashMap<OutputData, BufferedWriter> writtersMap = new HashMap<>(); //map of buffers, key is the data type to be logged, value is the file writter


    /**
     * Constructor of Logger,
     * @param dataToLog specified in config file, which data will be logged (init traj, spoofed trad, current position...)
     */
    public Logger(String utlToDirectory, List<OutputData> dataToLog){
        this.urlToDirectory = utlToDirectory;
        //fill all wanted output data in the map (Will allow to sort and avoid to log unwanted data), as well as their respective filewritters
        if (!dataToLog.isEmpty()) {
            //for each Data the user want to log, create its own file in the specified directory
            for (OutputData outputData : dataToLog) {
                String eachPath = utlToDirectory + "/" + outputData.toString().toLowerCase() + ".json";
                try {
                    //creating files and buffer writers for each type of output data
                    FileWriter currentFileWriter = new FileWriter(eachPath, false);
                    this.writtersMap.put(outputData, new BufferedWriter(currentFileWriter));  //add them to the map of buffers
                } catch (IOException e) {
                    System.err.println("Logger - IOException: Error creating [" + outputData + "] log file OR path given is not incorrect! \n");
                }
            }

        }else{
            System.err.println("Logger - INFO: No data to log (check config file, \"dataToLog\" section).\n");
        }
    }

    /**
     * Write specified data in the log file
     * If the specified data is not on the dataToLog array, will ignore it...
     * @param data the type of data we want to log
     * @param dataInStringForm the data itself represented in a string
     */
    @Override
    public void write(OutputData data, String dataInStringForm) {

        //ensure data in param is part of data type specified in config file (avoid log unwanted data)
        if(!this.writtersMap.containsKey(data)){
            System.out.println("Logger - INFO: Tried to log unwanted data (not part of specified data in config file), ignoring it... \n");
            return;
        }

        //we write the data in the appropriate file
        try {

            //add time for each log message?
            this.writtersMap.get(data).write(data.toString()+": ");
            this.writtersMap.get(data).write(dataInStringForm);
            this.writtersMap.get(data).newLine();

        } catch (IOException e) {

            System.err.println("Logger - IOException: Error while trying to write last data into log file! (File: " + data + ") \n");
            return;
        }
    }

    /**
     * Close properly all the streams (for all log files created)
     */
    public void terminateLogger(){
        this.writtersMap.forEach(
            (typeOfOutputData, respectiveBuffer) -> {
                try {
                    respectiveBuffer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    );
    }

    public String getDirectory() {
        return this.urlToDirectory;
    }
}

// ----------------------
