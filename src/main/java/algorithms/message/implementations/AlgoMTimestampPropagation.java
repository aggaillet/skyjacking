package algorithms.message.implementations;

import algorithms.message.IMessageAlgorithm;
import gpsutils.GpsEphemeris;
import gpsutils.GpsPosition;
import gpsutils.GpsSupportData;
import gpsutils.SatelliteMessageInfo;

import java.util.List;

/**
 * @author Angelo G. Gaillet
 */
public class AlgoMTimestampPropagation implements IMessageAlgorithm { //TODO: IMPLEMENT
    /**
     * Computes the content of the messages necessary to make the target believe it is in the given spoofed position.
     *
     * @param spoofedPosition the position the target should believe to be in.
     * @return a {@link List} of {@link SatelliteMessageInfo} objects containing the information to be transmitted by each satellite's message sequence.
     */
    @Override
    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition) {
        return null;
    }

    /**
     * Sets reference parameters to be used for calculations.
     *
     * @param referenceEphemeris   reference Ephemeris values to be used.
     * @param referenceSupportData reference ionospheric and support data to be used.
     */
    @Override
    public void setReferenceData(GpsEphemeris referenceEphemeris, GpsSupportData referenceSupportData) {

    }
}
