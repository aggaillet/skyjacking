package algorithms.message;

import algorithms.message.implementations.AlgoMConstPropagation;
import algorithms.message.implementations.AlgoMRandPropagation;
import algorithms.message.implementations.AlgoMTimestamp;
import algorithms.message.implementations.AlgoMTimestampPropagation;
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

    public MessageAlgorithmManager(EMessageAlgorithm algorithmType){
        switch (algorithmType){
            case TIMESTAMP:
                algorithm = new AlgoMTimestamp();
                break;
            case CONST_PROPAGATION:
                algorithm = new AlgoMConstPropagation();
                break;
            case RAND_PROPAGATION:
                algorithm = new AlgoMRandPropagation();
                break;
            case TIMESTAMP_PROPAGATION:
                algorithm = new AlgoMTimestampPropagation();
                break;
            default:
                throw new RuntimeException("Invalid algorithm type");
        }
        algorithm.setReferenceData(new GpsEphemeris(), new GpsSupportData());
    }

    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition){
        return algorithm.computeMessage(spoofedPosition);
    }
}
