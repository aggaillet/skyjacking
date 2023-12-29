package algorithms.message.implementations;

import algorithms.message.IMessageAlgorithm;
import gpsutils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static gpsutils.CoordinateConverter.*;

/**
 * @author Angelo G. Gaillet
 */
public class AlgoMTimestamp implements IMessageAlgorithm {
    private static final double C = 2.99792458 * Math.pow(10, 8);

    private final List<GpsEphemeris> referenceEphem;

    public AlgoMTimestamp(){
        referenceEphem = createFakeSatellites();
    }

    /**
     * Computes the content of the messages necessary to make the target believe it is in the given spoofed position.
     *
     * @param spoofedPosition the position the target should believe to be in.
     * @return a {@link List} of {@link SatelliteMessageInfo} objects containing the information to be transmitted by each satellite's message sequence.
     */
    @Override
    public List<SatelliteMessageInfo> computeMessage(GpsPosition spoofedPosition) {
        List<SatelliteMessageInfo> messageInfoList = new ArrayList<>();
        List<GpsEphemeris> satellites = getVisibleSatellites(spoofedPosition);

        // Calculate spoofed position in cartesian frame
        List<Double> spoofedPositionEci = geodetic2ecef(spoofedPosition);

        for (GpsEphemeris s : satellites){
            // Calculate position of s in cartesian frame
            List<Double> sPositionEci = koe2ecef(s.asKoe());

            // Calculate distance to spoofed postion
            double d = getCartesianDistance(sPositionEci, spoofedPositionEci);

            // Calculate propagation delay
            double dT = d / C;

            // Create message info object
            SatelliteMessageInfo i = new SatelliteMessageInfo(s, new GpsSupportData(), dT);
            messageInfoList.add(i);
        }

        return messageInfoList;
    }

    private List<GpsEphemeris> createFakeSatellites(){
        List<GpsEphemeris> satellites = Collections.unmodifiableList(new ArrayList<>());
        // TODO: generate ephemeris for a full constellation
        return satellites;
    }

    private List<GpsEphemeris> getVisibleSatellites(GpsPosition receiverPosition){
        List<GpsEphemeris> result = new ArrayList<>(referenceEphem);
        // Get the 4 closer satellites. Assuming that the orbits all have a similar radius and that satellites are just
        // enough to cover the earth and are equally spaced this should allow to get 4 visible satellites without
        // incurring in issues related to diluition of precision
        result.sort(Comparator.comparingDouble(x -> getSatelliteDistance(receiverPosition, x)));
        return result.subList(referenceEphem.size() - 4, referenceEphem.size());
    }
}
