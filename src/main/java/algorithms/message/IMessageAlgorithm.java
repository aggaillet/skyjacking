package algorithms.message;

import gpsutils.GpsPosition;
import gpsutils.SatelliteMessageInfo;

import java.util.List;

/**
 * Interface for all algorithms calculating the parameters of the navigation messages corresponding to the
 * multilateration of a given spoofed {@link GpsPosition}.
 * @author A.G. Gaillet
 */
public interface IMessageAlgorithm {
    /**
     * Computes the content of the messages necessary to make the target believe it is in the given spoofed position.
     * @param spoofedPosition the position the target should believe to be in.
     * @return a {@link List} of {@link SatelliteMessageInfo} objects containing the information to be transmitted by each satellite's message sequence.
     */
    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition);
}
