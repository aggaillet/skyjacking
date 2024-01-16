package configuration;

import org.json.JSONObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Units Tests for ConfigLoader class.
 * @author Heiarii Collenot
 */

class ConfigLoaderTest {

    private static final String urlConfigFile = "src/main/java/configuration/configuration.json";
    private ConfigLoader configLoader;

    /**
     * SUT (system under test) initialization method.
     * "BeforeEach" simulating a "BeforeALl"
     * Reason: BeforeAll is a static method, so can't modify instance attributes inside it
     */
    private static boolean alreadyInit = false;
    @BeforeEach
    void setup() {

        if(alreadyInit) return;

        configLoader = new ConfigLoader();

        alreadyInit = true;
    }

    /**
     * --- Origin ---
     * ID: TC.01
     * Summary: "To verify that the Configuration Loader correctly reads the
     *           user-input desired landing point from a configuration file."
     *
     * --- Description ---
     * ID: UT-01-A
     * Desc: todo
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
