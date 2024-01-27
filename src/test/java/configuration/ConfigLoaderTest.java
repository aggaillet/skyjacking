package configuration;

import org.json.JSONException;
import org.json.JSONObject;

import org.junit.jupiter.api.*;
import outputFeature.Logger;
import outputFeature.OutputData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Units Tests for ConfigLoader class.
 * @author Heiarii Collenot
 */

class ConfigLoaderTest {

    private static final String urlConfigFile = "src/main/java/configuration/configuration.json";
    private static ConfigLoader configLoader;

    private static Logger logger;

    /**
     * Setting up the SUT environment (before starting tests)
     */
    @BeforeAll
    static void setup() {

        configLoader = new ConfigLoader();

        List<OutputData> dataToLog = new ArrayList<OutputData>();
        dataToLog.add(OutputData.SPOOFED_TRAJ);
        logger = new Logger("src/main/java/outputFeature/logs", dataToLog);
    }


    /**
     * --- Origin ---
     * ID: TC.01
     * Summary: "To verify that the Configuration Loader correctly reads the
     *           user-input desired landing point from a configuration file."
     *
     * --- Description ---
     * ID: UT-01-A
     * Desc: "Reads the config file real destination values, compare them to the ConfigLoader stored values."
     *
     * ---
     */
    @Test
    void UT_01_A() throws IOException {

        //extracting the real data from the file as oracle (the real values we should get from Config Loader)
        StringBuilder configText= new StringBuilder();
        String line ="";

        //open buffer on the config file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(urlConfigFile));

        //read it
        while (line!=null){
            line = bufferedReader.readLine();
            configText.append(line);
        }
        bufferedReader.close();

        //transform wanted content into JSON
        JSONObject jsonDestination = new JSONObject(configText.toString()).getJSONObject("destination");

        //extract wanted inputs (lat, long, alt)
        double realLat = jsonDestination.getDouble("latitude");
        double realLong = jsonDestination.getDouble("longitude");
        double realAlt = jsonDestination.getDouble("altitude");

        //compare values
        Assertions.assertEquals(realLong, configLoader.getLongitude());
        Assertions.assertEquals(realLat, configLoader.getLatitude());
        Assertions.assertEquals(realAlt, configLoader.getAltitude());
    }


    /**
     * --- Origin ---
     * ID: TC.06
     * Summary: "To verify the system's ability to provide the UAV's spoofed trajectory
     *           to the user in a textual form using the interface IOutput."
     *
     * --- Description ---
     * ID: UT-01-B
     * Desc: todo
     *
     * ---
     */
    @Test
    void UT_01_B() throws IOException {

        //Log a random spoofed trajectory
        logger.write(OutputData.SPOOFED_TRAJ, "Lat: 15; Long: 38; ALt: 140; Time: 80802424;");

        //terminate the logger, to close files and apply changes
        logger.terminateLogger();


        StringBuilder configText= new StringBuilder();
        String line ="";

        //initiate buffer on the dedicated log file for spoofed_traj
        FileReader reader = new FileReader(logger.getDirectory()+"/spoofed_traj.json"); //TODO: assert that the file exists proof that Logger hqs successfully created it
        BufferedReader bufferedReader = new BufferedReader(reader);

        //read it
        while (line!=null){
            line = bufferedReader.readLine();
            configText.append(line);
        }
        bufferedReader.close();



        //check that data has been log successfully (to precise: format of the log file)
        Assertions.assertTrue((configText.indexOf("SPOOFED_TRAJ") != -1), "not found");
    }


    /**
     * Ending properly the SUT
     */
    @AfterAll
    static void tearDown() {


    }


    /* ------


    @Disabled
    @Test
    void testConfigLoaderInitialization() throws IOException {
        // Mock method readConfiguration to avoid exec real code
        when(bufferedReader.readLine()).thenReturn(
                        "{\"Directory\":\"src/main/java/configuration/configuration.json\",\"MessageAlgorithmType\":\"TIMESTAMP\"," +
                                "\"destination\":{\"latitude\":12.34,\"altitude\":90.12,\"longitude\":56.78}," +
                                "\"dataLog\":{\"initialTrajectory\":true,\"spoofedTrajectory\":true,\"resultingTrajectory\":true,\"refreshRate\":1000000000}}")
                .thenReturn(null); // Pour signaler la fin du fichier

        configLoader = new ConfigLoader(urlConfigFile);

        // test init config values
        assertEquals(urlConfigFile, configLoader.getDirectory());
        assertEquals(EMessageAlgorithm.TIMESTAMP, configLoader.getMsgAlgoType());
        assertEquals(12.34, configLoader.getLatitude());
        assertEquals(56.78, configLoader.getLongitude());
        assertEquals(90.12, configLoader.getAltitude());
        assertEquals(new GpsPosition(12.34, 56.78, 90.12), configLoader.getDestination());
        assertTrue(configLoader.isInitialTrajectory());
        assertTrue(configLoader.isSpoofedTrajectory());
        assertTrue(configLoader.isResultingTrajectory());
        assertEquals(1000000000, configLoader.getRefreshRate());
    }

    @Disabled
    @Test
    void testConfigLoaderWithEmptyFile() throws IOException {
        // Mock method readConfiguration to avoid exec real code
        when(bufferedReader.readLine()).thenReturn(null); // empty file

        // test config with empty file
        assertNull(configLoader.getDirectory());
        assertNull(configLoader.getMsgAlgoType());
        assertEquals(0.0, configLoader.getLatitude());
        assertEquals(0.0, configLoader.getLongitude());
        assertEquals(0.0, configLoader.getAltitude());
        assertNull(configLoader.getDestination());
        assertFalse(configLoader.isInitialTrajectory());
        assertFalse(configLoader.isSpoofedTrajectory());
        assertFalse(configLoader.isResultingTrajectory());
        assertEquals(0, configLoader.getRefreshRate());
    }

    @Disabled
    @Test
    void testGetSelectedOutputs() throws NoSuchFieldException, IllegalAccessException {
        // create real configLoader object to test method "getSelectedOutputs"
        ConfigLoader configLoader = new ConfigLoader(urlConfigFile);

        // using reflexivity to access private members (even if it is not recommended)
        Field initialTrajectoryField = ConfigLoader.class.getDeclaredField("initialTrajectory");
        Field spoofedTrajectoryField = ConfigLoader.class.getDeclaredField("spoofedTrajectory");
        Field resultingTrajectoryField = ConfigLoader.class.getDeclaredField("resultingTrajectory");

        // make fields accessible
        initialTrajectoryField.setAccessible(true);
        spoofedTrajectoryField.setAccessible(true);
        resultingTrajectoryField.setAccessible(true);

        // modify values (for the test only)
        initialTrajectoryField.setBoolean(configLoader, true);
        spoofedTrajectoryField.setBoolean(configLoader, false);
        resultingTrajectoryField.setBoolean(configLoader, true);


        // call method to test
        List<OutputData> selectedOutputs = configLoader.getSelectedOutputs();

        // verify results
        assertEquals(Arrays.asList(OutputData.INITIAL_TRAJ, OutputData.RESULT_TRAJ), selectedOutputs);
    }

     */
}