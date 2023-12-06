package message_controller;

import gpsutils.GpsPosition;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimulatorController implements IMessageController {
    private final String API_LINK = "";

    /**
     * Requests position data from the API-Simulator.
     *
     * @return The position retrieved from the API-Simulator.
     * @throws RuntimeException if an error occurs during API communication.
     */
    @Override
    public GpsPosition requestPosition() {
        try {
            //The URL make the link between the order requested and the answer
            URL apiURL = new URL(API_LINK);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to get data: " + connection.getResponseCode());
            }

            //Get the data answer, on a brut format.
            //Translate the data as a String format, then as a JSON readable format
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            String jsonText = jsonResponse.toString();
            JSONObject jsonObject = new JSONObject(jsonText);
            JSONObject locationObject = jsonObject.getJSONObject("location");
            double longitude = locationObject.getDouble("longitude");
            double latitude = locationObject.getDouble("latitude");
            double altitude = locationObject.getDouble("altitude");
            String time = jsonObject.getString("time");

            //Close the buffer reader and the connection to the API
            reader.close();
            connection.disconnect();

            return new GpsPosition(longitude, latitude, altitude, time);
        } catch (IOException | JSONException exception) {
            throw new RuntimeException("API handling error occurred", exception);
        }
    }

    /**
     * Sends a spoofed message via HTTP request to the API-Simulator, changing its position.
     *
     * @param byteMessage The message to be sent as a byte array.
     * @return True if the message was sent successfully; otherwise, false.
     * @throws RuntimeException if an error occurs during API communication.
     */
    @Override
    public boolean sendSpoofedMsg(byte[] byteMessage) {
        try {
            //The URL make the link between the API and the system
            URL apiURL = new URL(API_LINK);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to get data : " + connection.getResponseCode());
            }

            //Prepare the connection to send a message as a POST
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(byteMessage);
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}

