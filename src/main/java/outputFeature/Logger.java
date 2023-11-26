package outputFeature;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements IOutput{

    private BufferedWriter buffWritter;
    private String[] dataToLog;   //list of paramsto be logged

    /**
     * Constructor of Logger,
     * @param pathToLogFile specified in config file, where will be written
     * @param dataToLog specified in config file, which data will be stored (init traj, spoofed trad, current position...)
     */
    public Logger(String pathToLogFile, String[] dataToLog){

        //initiating writer with log file chosen in config file
        try {
            //attributes
            FileWriter fileWriter = new FileWriter(pathToLogFile, false);
            this.buffWritter = new BufferedWriter(fileWriter);
        }catch(IOException e){
            System.err.println("Logger - IOException: Error opening file OR path given is not a file OR file doesn't exist and can't create such file! \n");
            return;
        }

        this.dataToLog = dataToLog; //initiate params to be logged, chosen in config file (init traj, spoofed traj...)
    }

    @Override
    public void write() {

        for(String data: this.dataToLog){

            //Checking if current data to log is valid, if no, ignoring it
            try{
                OutputData.valueOf(data);
            }catch(IllegalArgumentException e){
                System.err.println("Logger - IllegalArgumentException: Unknown param to log, ignoring it... \n");
                continue;
            }

            //if valid, we process the data

            //TODO
        }
    }
}
