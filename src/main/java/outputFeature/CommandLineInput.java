package outputFeature;

import java.io.IOException;
import java.util.ArrayList;

/* */
public class CommandLineInput implements IOutput{

    private final ArrayList<OutputData> dataToPrint;   //list of data to be printed in console

    public CommandLineInput(ArrayList<OutputData> dataToPrint){

        this.dataToPrint = new ArrayList<>(dataToPrint); //initiate params to be logged, chosen in config file (init traj, spoofed traj...)
    }

    /**
     * Write specified data in the console
     * If the specified data is not on the dataToLog array, will ignore it...
     * @param data the type of data we want to log
     * @param dataInString the data itself represented in a string
     */
    @Override
    public void write(OutputData data, String dataInString) {       //replace string with data represented as an object directly? for json

        //ensure data in param is part of data type specified in config file
        if(!this.dataToPrint.contains(data)){
            System.out.println("Logger - INFO: Tried to print unwanted data (not part of specified data in config file), ignoring it... \n");
            return;
        }

        //we write the data in the standard output

        //add time ref for each message?
        System.out.println(data.toString()+": ");
        System.out.print(dataInString + "\n");
    }
}
