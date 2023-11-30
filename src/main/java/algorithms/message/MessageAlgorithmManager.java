package algorithms.message;

import gpsutils.GpsEphemeris;
import gpsutils.GpsPosition;
import gpsutils.GpsSupportData;
import gpsutils.SatelliteMessageInfo;

import java.util.List;

/**
 * @author Angelo G. Gaillet
 */
public class MessageAlgorithmManager {
    private final IMessageAlgorithm algorithm;
    private final GpsEphemeris referenceEphemeris = new GpsEphemeris();
    private final GpsSupportData referenceSupportData = new GpsSupportData();

    public MessageAlgorithmManager(EMessageAlgorithm algorithmType){
        switch (algorithmType){
            case NAIVE:
                algorithm = null; //TODO
            default:
                throw new RuntimeException("Invalid algorithm type");
        }
    }

    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition){
        return algorithm.computeMessage(spoofedPosition, referenceEphemeris, referenceSupportData);
    }
}
