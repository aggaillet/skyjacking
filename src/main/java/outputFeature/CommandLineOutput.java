package outputFeature;

import java.util.ArrayList;
import java.util.List;

/* */
public class CommandLineOutput implements IOutput{

    private final ArrayList<OutputData> dataToPrint = new ArrayList<>();   //list of data to be printed in console

    public CommandLineOutput(List<OutputData> dataToPrint){
        if (dataToPrint.size() > 0) {
            this.dataToPrint.addAll(dataToPrint);
        }else{
            System.err.println("Logger - INFO: No data to log (check config file, \"dataToLog\" section).\n");
        }
    }

    /**
     * Write specified data in the console
     * If the specified data is not on the dataToLog array, will ignore it...
     * @param data the type of data we want to log
     * @param dataInStringForm the data itself represented in a string
     */
    @Override
    public void write(OutputData data, String dataInStringForm) {       //replace string with data represented as an object directly? for json
        //ensure data in param is part of data type specified in config file
        if(!this.dataToPrint.contains(data)){
            System.out.println("Logger - INFO: Tried to print unwanted data (not part of specified data in config file), ignoring it... \n");
            return;
        }

        //we write the data in the standard output

        //add time ref for each message?
        System.out.println("--- " + data.toString() + " ---");
        System.out.println(dataInStringForm + "\n");
    }
}