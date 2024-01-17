package message_controller;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import gpsutils.GpsPosition;

import org.junit.jupiter.api.*;

import java.io.*;
import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Units Tests for SimulatorController class.
 * @author Heiarii Collenot
 */
class SimulatorControllerTest {

    //SUT objects declaration
    static SimulatorController simController1;
    static HttpServer server;
    final static String context = "/mock-server";
    static int port = 8000;

    private final static double testLong = 43.5;
    private final static double testLat = 1.4;
    private final static double testAlt = 100;


    /**
     * Setting up the SUT environment (before starting tests)
     */
    @BeforeAll
    static void setup() throws IOException {

        simController1 = new SimulatorController(context, port);

        server = HttpServer.create(new InetSocketAddress(port), 0); //create http server
        server.createContext(context, new MockHTTPServerHandler()); //assign our custom handler
        server.setExecutor(null);
        server.start(); //start http server
    }


    /**
     * --- Origin ---
     * ID: TC.02
     * Summary: "To verify that the Message Controller get the original position of the UAV"
     *
     * --- Description ---
     * ID: UT-02-A
     * Desc: todo
     *
     * ---
     */
    @Test
    void UT_02_A(){

        GpsPosition gpsPosition = simController1.requestPosition();

        assertEquals(testLong, gpsPosition.getLon());
        assertEquals(testLat, gpsPosition.getLat());
        assertEquals(testAlt, gpsPosition.getAlt());
    }


    /**
     * --- Origin ---
     * ID: TC.03
     * Summary: "To verify that the MessageController is able to send messages to the simulator"
     *
     * --- Description ---
     * ID: UT-02-B
     * Desc: todo
     *
     * ---
     */
    @Test
    void UT_02_B(){

        boolean response = simController1.sendSpoofedMsg("test message".getBytes());
        assertTrue(response);
    }

    /**
     * Ending properly the SUT
     */
    @AfterAll
    public static void tearDown() {

        server.stop(0);  //stop http server
    }


//
//
//
//    /** /!\ DISABLED
//     * --- Origin ---
//     * ID: TC.02
//     * Summary: "To verify that the system successfully retrieves the current UAV position."
//     * ---
//     * --- Description ---
//     * ID: UT-02A
//     * Desc: A simple test that check an Exception is thrown if HTTP_URL of Simulator controller is an empty string
//     * ---
//     */
//    @Disabled
//    @Test
//    void test_requestPositionAPIEmpty(){
//
//        if(simController1.getAPI_LINK().isEmpty())
//            assertThrows(RuntimeException.class, () -> {
//                simController1.requestPosition();
//            });
//    }
//
//
//    /** /!\ DISABLED /!\ can't test because can't inject the mocked URL inside the method!
//     *
//     * --- Origin ---
//     * ID: TC.02
//     * Summary: "To verify that the system successfully retrieves the current UAV position."
//     * ---
//     *
//     * --- Description ---
//     * ID: UT-02B
//     * Desc: A test that mocks an HttpURLConnection in order to test the method "requestPosition"
//     * ---
//     */
//    @Disabled
//    @Test
//    void test_requestPosition() {
//
//        // Mocking HttpURLConnection for testing
//        HttpURLConnection mockedConnection = mock(HttpURLConnection.class);
//        try {
//            when(mockedConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK); //simulate OK response
//            when(mockedConnection.getInputStream()).thenReturn(new ByteArrayInputStream(
//                    "{\"location\": {\"longitude\": 1.0, \"latitude\": 2.0, \"altitude\": 3.0}, \"time\": \"2022-01-01T00:00:00\"}"
//                            .getBytes()));
//
//            URL mockedUrl = mock(URL.class);
//            when(mockedUrl.openConnection()).thenReturn(mockedConnection);
//
//            //Whitebox.setInternalState(simController1, "API_LINK", mockedUrl);
//
//            GpsPosition gpsPosition = simController1.requestPosition();
//
//            assertEquals(1.0, gpsPosition.getLon());
//            assertEquals(2.0, gpsPosition.getLat());
//            assertEquals(3.0, gpsPosition.getAlt());
//            assertEquals("2022-01-01T00:00:00", gpsPosition.getDateTime());
//        } catch (IOException exception) {
//            fail("IOException should not be thrown");
//        }
//    }
//
//
//    /** /!\ DISABLED /!\ can't test because can't inject the mocked URL inside the method!
//     *
//     * --- Origin ---
//     * ID: TC.XX
//     * Summary: Not defined
//     * ---
//     *
//     * --- Description ---
//     * ID: UT-XX
//     * Desc: Test the method "sendSpoofedMessage"
//     * ---
//     */
//    @Disabled
//    @Test
//    void test_sendSpoofedMsg() {
//
//        // Mocking HttpURLConnection for testing
//        HttpURLConnection mockedConnection = mock(HttpURLConnection.class);
//        try {
//            when(mockedConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
//
//            URL mockedUrl = mock(URL.class);
//            when(mockedUrl.openConnection()).thenReturn(mockedConnection);
//
//            //Whitebox.setInternalState(simController1, "API_LINK", mockedUrl);
//
//            byte[] byteMessage = "Test Message GPS".getBytes();
//            boolean result = simController1.sendSpoofedMsg(byteMessage);
//
//            assertTrue(result);
//        } catch (IOException exception) {
//            fail("IOException should not be thrown");
//        }
//    }
//
//
//    /**
//     * DISABLED /!\
//     * --- Origin ---
//     * ID: TC.XX
//     * Summary: ""
//     * ---
//     *
//     * --- Description ---
//     * ID: UT-XX
//     * Desc: to define
//     * ---
//     */
//    @Disabled
//    @ParameterizedTest
//    @MethodSource("elementsGenerator")
//    public void testParametrized(String streamOfElems) {
//
//        //the test itself
//    }
//
//    public static Stream<String> elementsGenerator() {
//
//        ArrayList<String> elemsContainer = new ArrayList<String>();
//
//        //fill the array with elems
//
//
//        return elemsContainer.stream();
//    }


    /**
     * Internal static class: wrapping the handling method of the test HTTP server
     * @author Malik Willemy
     */
     static class MockHTTPServerHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();

            if (path.equals(context+"/requestPosition")) {

                String jsonResponse =
                        "{\"location\":{" +
                                "\"longitude\":"+testLong+"," +
                                "\"latitude\":"+testLat+"," +
                                "\"altitude\":"+testAlt+
                                "}," +
                                "\"time\":\"2023-11-21T11:44:00.034524Z\"}";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();

            } else if (path.equals(context+"/sendSpoofedMsg")) {

                if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                    StringBuilder requestBody = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }
                    reader.close();

                    System.out.println("Received message: " + requestBody);

                    exchange.sendResponseHeaders(200, 0);
                } else {
                    exchange.sendResponseHeaders(405, 0);
                }
            } else {

                exchange.sendResponseHeaders(404, 0);
            }
            exchange.getResponseBody().close();
        }
    }
}
