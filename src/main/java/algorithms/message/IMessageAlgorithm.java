package algorithms.message;

import gpsutils.GpsEphemeris;
import gpsutils.GpsPosition;
import gpsutils.GpsSupportData;
import gpsutils.SatelliteMessageInfo;

import java.util.List;

/**
 * Interface for all algorithms calculating the parameters of the navigation messages corresponding to the
 * multilateration of a given spoofed {@link GpsPosition}.
 * @author Angelo G. Gaillet
 */
interface IMessageAlgorithm {
    /**
     * Computes the content of the messages necessary to make the target believe it is in the given spoofed position.
     * @param spoofedPosition the position the target should believe to be in.
     * @param referenceEphemeris reference Ephemeris values to be used.
     * @param referenceSupportData reference ionospheric and support data to be used.
     * @return a {@link List} of {@link SatelliteMessageInfo} objects containing the information to be transmitted by each satellite's message sequence.
     */
    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition, GpsEphemeris referenceEphemeris, GpsSupportData referenceSupportData);
}
