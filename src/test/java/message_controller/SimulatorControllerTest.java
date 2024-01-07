package message_controller;


import gpsutils.GpsPosition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.powermock.reflect.Whitebox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Units Tests for SimulatorController class.
 * @author Heiarii Collenot
 */
class SimulatorControllerTest {

    //SUT objects declaration
    SimulatorController simController1;


    private static boolean alreadyInit = false;

    /**
     * SUT (system under test) initialization method.
     * "BeforeEach" simulating a "BeforeALl"
     * Reason: BeforeAll is a static method, so can't modify instance attributes inside it
     */
    @BeforeEach
    void setup(){
        if(alreadyInit) return;

        simController1 = new SimulatorController();
    }


    /**
     * --- Origin ---
     * ID: TC.02
     * Summary: "To verify that the system successfully retrieves the current UAV position."
     * ---
     *
     * --- Description ---
     * ID: UT-02A
     * Desc: A simple test that check an Exception is thrown if HTTP_URL of Simulator controller is an empty string
     * ---
     */
    @Test
    void test_requestPositionAPIEmpty(){

        if(simController1.getAPI_LINK().isEmpty())
            assertThrows(RuntimeException.class, () -> {
                simController1.requestPosition();
            });
    }


    /** /!\ DISABLED /!\ can't test because can't inject the mocked URL inside the method!
     *
     * --- Origin ---
     * ID: TC.02
     * Summary: "To verify that the system successfully retrieves the current UAV position."
     * ---
     *
     * --- Description ---
     * ID: UT-02B
     * Desc: A test that mocks an HttpURLConnection in order to test the method "requestPosition"
     * ---
     */
    @Disabled
    @Test
    void test_requestPosition() {

        // Mocking HttpURLConnection for testing
        HttpURLConnection mockedConnection = mock(HttpURLConnection.class);
        try {
            when(mockedConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK); //simulate OK response
            when(mockedConnection.getInputStream()).thenReturn(new ByteArrayInputStream(
                    "{\"location\": {\"longitude\": 1.0, \"latitude\": 2.0, \"altitude\": 3.0}, \"time\": \"2022-01-01T00:00:00\"}"
                            .getBytes()));

            URL mockedUrl = mock(URL.class);
            when(mockedUrl.openConnection()).thenReturn(mockedConnection);

            Whitebox.setInternalState(simController1, "API_LINK", mockedUrl);

            GpsPosition gpsPosition = simController1.requestPosition();

            assertEquals(1.0, gpsPosition.getLon());
            assertEquals(2.0, gpsPosition.getLat());
            assertEquals(3.0, gpsPosition.getAlt());
            assertEquals("2022-01-01T00:00:00", gpsPosition.getDateTime());
        } catch (IOException exception) {
            fail("IOException should not be thrown");
        }
    }


    /** /!\ DISABLED /!\ can't test because can't inject the mocked URL inside the method!
     *
     * --- Origin ---
     * ID: TC.XX
     * Summary: Not defined
     * ---
     *
     * --- Description ---
     * ID: UT-XX
     * Desc: Test the method "sendSpoofedMessage"
     * ---
     */
    @Disabled
    @Test
    void test_sendSpoofedMsg() {

        // Mocking HttpURLConnection for testing
        HttpURLConnection mockedConnection = mock(HttpURLConnection.class);
        try {
            when(mockedConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);

            URL mockedUrl = mock(URL.class);
            when(mockedUrl.openConnection()).thenReturn(mockedConnection);

            Whitebox.setInternalState(simController1, "API_LINK", mockedUrl);

            byte[] byteMessage = "Test Message GPS".getBytes();
            boolean result = simController1.sendSpoofedMsg(byteMessage);

            assertTrue(result);
        } catch (IOException exception) {
            fail("IOException should not be thrown");
        }
    }











    /**
     * DISABLED /!\
     * --- Origin ---
     * ID: TC.XX
     * Summary: ""
     * ---
     *
     * --- Description ---
     * ID: UT-XX
     * Desc: to define
     * ---
     */
    @Disabled
    @ParameterizedTest
    @MethodSource("elementsGenerator")
    public void testParametrized(String streamOfElems) {

        //the test itself
    }

    public static Stream<String> elementsGenerator() {

        ArrayList<String> elemsContainer = new ArrayList<String>();

        //fill the array with elems


        return elemsContainer.stream();
    }
}
