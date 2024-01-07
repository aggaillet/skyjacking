package configuration;

import gpsutils.GpsPosition;
import org.junit.jupiter.api.Disabled;
import outputFeature.OutputData;
import algorithms.message.EMessageAlgorithm;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



/**
 * Units Tests for ConfigLoader class.
 * @author Heiarii Collenot
 */
class ConfigLoaderTest {

    private static final String urlConfigFile = "src/main/java/configuration/configuration.json";

    @Mock
    private BufferedReader bufferedReader;

    @InjectMocks
    private ConfigLoader configLoader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    /**
     * IMPOSSIBLE TO TEST THE ConfigLoader CLASS WITH ITS ACTUAL IMPLEMENTATION /!\
     * BECAUSE NOT POSSIBLE TO INJECT DEPENDENCIES
     */


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
}
