package configuration;

import algorithms.message.EMessageAlgorithm;
import gpsutils.GpsPosition;
import org.json.JSONObject;
import outputFeature.OutputData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {


    //"skyjacking/src/main/java/configuration/configuration.json"

    private final String directory; // where to find the file
    private final EMessageAlgorithm msgAlgoType; // the type of algo the user wishes to apply the spoofing with
    private final GpsPosition destination; // the final destination wanted by the user
    private final double latitude; // the latitude of the desired position
    private final double altitude; // the altitude of the desired position
    private final double longitude; // the longitude of the desired position
    private final boolean initialTrajectory; // the initial real trajectory, to see if the user wants to have it
    private final boolean spoofedTrajectory; //the current trajectory of the uav, it is also up to user preference to see it or not
    private final boolean resultingTrajectory; //the current trajectory of the uav, it is also up to user preference to see it or not
    private final int refreshRate; //it is a frequency of renewal of messages in nanoseconds

    public ConfigLoader(String pathForConfigFile) {   //HEIA_COL: ADDED "path for config file" as argument of constructor

        StringBuilder configText = readConfiguration(pathForConfigFile);  //HEIA_COL: pass var to readConfig method
        String config = configText.toString();
        System.err.println(config);
        JSONObject mainJsonObject = new JSONObject(config);
        this.directory = mainJsonObject.getString("Directory");
        this.msgAlgoType = EMessageAlgorithm.valueOf(mainJsonObject.getString("MessageAlgorithmType"));     //CHECK THIS
        //this.posAlgoType = EPositionAlgorithm.valueOf(mainJsonObject.getString("PositionAlgorithmType"));
        JSONObject wantedDestination = mainJsonObject.getJSONObject("destination");
        this.latitude = wantedDestination.getDouble("latitude");
        this.altitude = wantedDestination.getDouble("altitude");
        this.longitude = wantedDestination.getDouble("longitude");
        this.destination = new GpsPosition(this.latitude,this.longitude, this.altitude);
        //the stuff the user wants to see
        JSONObject dataLog = mainJsonObject.getJSONObject("dataLog");
        this.initialTrajectory = dataLog.getBoolean("initialTrajectory");
        this.spoofedTrajectory =  dataLog.getBoolean("spoofedTrajectory");
        this.resultingTrajectory =  dataLog.getBoolean("resultingTrajectory");
        this.refreshRate = dataLog.getInt("refreshRate");
    }

    private StringBuilder readConfiguration(String pathForConfigFile){  //HEIA_COL: new arg, url to config file
        StringBuilder configText= new StringBuilder();
        String line ="";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathForConfigFile));  //HEIA_COL: use here the path to create reader

            while (line!=null){
                line = bufferedReader.readLine();
                configText.append(line);
            }
            bufferedReader.close();
        }catch (FileNotFoundException e){
            System.out.print("File not found");
        }catch (IOException e){
            System.out.println("There is problem reading the file");
        }
    return configText;
    }

    public EMessageAlgorithm getMsgAlgoType() {
        return msgAlgoType;
    }

    public String getDirectory() {
        return directory;
    }

    public List<OutputData> getSelectedOutputs(){
        ArrayList<OutputData> outputData = new ArrayList<>();
        if (this.initialTrajectory) {
            outputData.add(OutputData.INITIAL_TRAJ);
        }
        if (this.spoofedTrajectory) {
            outputData.add(OutputData.SPOOFED_TRAJ);
        }
        if (this.resultingTrajectory) {
            outputData.add(OutputData.RESULT_TRAJ);
        }
        return outputData;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public GpsPosition getDestination() {
        return destination;
    }

    public boolean isInitialTrajectory() {  //HEIA_COL: added some getters
        return initialTrajectory;
    }

    public boolean isSpoofedTrajectory() {
        return spoofedTrajectory;
    }

    public boolean isResultingTrajectory() {
        return resultingTrajectory;
    }
}
