package gpsutils;

import org.json.JSONMLParserConfiguration;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {
    private String configuration;// c quoi?

    public ConfigLoader(String configuration) {
        this.configuration = configuration;
    }

    public void readConfiguration(String filename) throws IOException {

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }




//        try {
//           bufferedReader.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }// est ce que si on veut appeler la methode pour lire la configuration et ensuite get ce qu'on veut, ça ne cré pas des problèmes lorsqu'elle ferme le bufferedReader??
    }

//    public String getAlgorithm() throws IOException {
//        JSONParser parser = new JSONParser();
//        Object o = parser.parse(new FileReader("configuration.json"));
//        JSONObject mainJsonObject = (JSONObject) o;
//        return (String) mainJsonObject.get("AlgorithmType");
//    }

//    public Position getDestination(){
//        JSONParser parser = new JSONParser();
//        Object o = null;
//        try {
//            o = parser.parse(new FileReader("configuration.json"));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        JSONObject mainJsonObject = (JSONObject) o;
//        return (Position) mainJsonObject.get("Destination");
//    }

}
