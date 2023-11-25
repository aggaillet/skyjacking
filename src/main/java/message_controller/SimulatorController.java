package message_controller;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimulatorController implements IMessageController {
    private final String APILINK = "";

    @Override
    public Position requestPosition() {
        try {
            URL apiURL = new URL(APILINK);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to get data : " + connection.getResponseCode());
            }

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

            reader.close();
            connection.disconnect();

            return new Position(longitude, latitude, altitude, time);
        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
            return new Position();
        }
    }

    @Override
    public boolean sendSpoofedMsg(byte[] byteMessage) {
        try {
            URL apiURL = new URL(APILINK);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to get data : " + connection.getResponseCode());
            }

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            os.write(byteMessage);
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
