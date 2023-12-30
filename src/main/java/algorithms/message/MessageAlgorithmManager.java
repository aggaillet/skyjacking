package algorithms.message;

import algorithms.message.implementations.AlgoMTimestamp;
import gpsutils.GpsPosition;
import gpsutils.SatelliteMessageInfo;

import java.util.List;

/**
 * Algorithm manager. It is responsible for creating the instance of the algorithm corresponding to the system settings.
 * It provides an algorithm counterpart indipendent from the implementation.
 * @author A.G. Gaillet
 */
public class MessageAlgorithmManager implements IMessageAlgorithm{
    private final IMessageAlgorithm algorithm;

    public MessageAlgorithmManager(EMessageAlgorithm algorithmType){
        switch (algorithmType){
            case TIMESTAMP:
                algorithm = new AlgoMTimestamp();
                break;
            default:
                throw new RuntimeException("Invalid algorithm type");
        }
    }

    /**
     * Delegate to the proper algorrithm the computation of the content of the messages necessary to make the target believe it is in the given spoofed position.
     * @param spoofedPosition the position the target should believe to be in.
     * @return a {@link List} of {@link SatelliteMessageInfo} objects containing the information to be transmitted by each satellite's message sequence.
     */
    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition){
        return algorithm.computeMessage(spoofedPosition);
    }
}
