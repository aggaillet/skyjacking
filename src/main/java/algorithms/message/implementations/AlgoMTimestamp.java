package algorithms.message.implementations;

import algorithms.CsvReader;
import algorithms.message.IMessageAlgorithm;
import gpsutils.*;

import java.util.*;

import static gpsutils.CoordinateConverter.*;

/**
 * Message spoofing algorithm.
 * Leverages trasmission timestamp to spoof the receiver position.
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
        List<GpsEphemeris> satellites = new ArrayList<>(); ;
        Map<String, List<String>> constellation = CsvReader.readCsvFromResources("constellation.csv");
        List<Integer> prn = constellation.get("PRN").stream().map(Integer::parseInt).toList();
        List<Double> inclination = constellation.get("INCLINATION").stream().map(Double::parseDouble).toList();
        List<Double> rightAscension = constellation.get("RA_OF_ASC_NODE").stream().map(Double::parseDouble).toList();
        List<Double> argPeriapsis = constellation.get("ARG_OF_PERICENTER").stream().map(Double::parseDouble).toList();
        List<Double> meanAnomaly = constellation.get("MEAN_ANOMALY").stream().map(Double::parseDouble).toList();
        for (int i = 0; i < prn.size(); i++) {
            GpsEphemeris e = new GpsEphemeris();
            e.i0 = inclination.get(i);
            e.omega0 = rightAscension.get(i);
            e.omega = argPeriapsis.get(i);
            e.m0 = argPeriapsis.get(i);
        }
        return Collections.unmodifiableList(satellites);
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
