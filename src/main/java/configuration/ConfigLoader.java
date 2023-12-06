package configuration;

import gpsutils.GpsPosition;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    private String directory; // where to find the file
    private String algoType; // the type of algo the user wishes to apply the spoofing with
    private GpsPosition destination; // the final destination wanted by the user
    private double latitude; // the latitude of the desired position
    private double altitude; // the altitude of the desired position
    private double longitude; // the longitude of the desired position
    private JSONObject dataLog; //the stuff the user wants to see
    private boolean baseTrajectory; // the initial real trajectory, to see if the user wants to have it
    private boolean currentTrajectory; //the current trajectory of the uav, it is also up to user preference to see it or not
    private int refreshRate; //it is a frequency of renewal of messages

    public ConfigLoader() {
        StringBuilder configText = readConfiguration();
        String config = configText.toString();
        JSONObject mainJsonObject = new JSONObject(config);
        this.directory = mainJsonObject.getString("Directory");
        this.algoType = mainJsonObject.getString("AlgorithmType");
        JSONObject wantedDestination = mainJsonObject.getJSONObject("destination");
        this.latitude = wantedDestination.getDouble("latitude");
        this.altitude = wantedDestination.getDouble("altitude");
        this.longitude = wantedDestination.getDouble("longitude");
        this.destination = new GpsPosition(this.latitude,this.longitude, this.altitude);
        this.dataLog = mainJsonObject.getJSONObject("dataLog");
        this.baseTrajectory = dataLog.getBoolean("baseTrajectory");
        this.currentTrajectory =  dataLog.getBoolean("currentTrajectory");
        this.refreshRate = dataLog.getInt("refreshRate");
    }

    public StringBuilder readConfiguration(){
        StringBuilder configText= new StringBuilder();
        String line ="";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("skyjacking/src/main/java/configuration/configuration.json"));

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

    public String getAlgoType() {
        return algoType;
    }

    public String getDirectory() {
        return directory;
    }

    public JSONObject getDataLog() {
        return dataLog;
    }

    public boolean isBaseTrajectory() {
        return baseTrajectory;
    }

    public boolean isCurrentTrajectory() {
        return currentTrajectory;
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
//    public static void main(String[] arg){
//        ConfigLoader c = new ConfigLoader();
//        System.out.println(c.getAlgoType());
//        System.out.println(c.getLatitude());
//        System.out.println(c.getDestination());
//        System.out.println(c.getDataLog());
//
//    }
}
