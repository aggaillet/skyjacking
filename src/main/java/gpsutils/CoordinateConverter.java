package gpsutils;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Contains static methods providing common operations and conversions between reference frames.
 * @author A.G. Gaillet
 */
public class CoordinateConverter {

    /**
     * Converts a {@link GpsPosition} (latitude, longitude, altitude) into Earth-Centered, Earth-Fixed cartesian
     * coordinates (x, y, z).<br>
     * Where the reference frame has:<br>
     * <ul>
     *     <li>The origin at the center of the Earth</li>
     *     <li>The x-axis pointing towards the intersection of the Equator and Prime Meridian</li>
     *     <li>The y-axis pointing towards the intersection of the Equator and 90° East longitude</li>
     *     <li>The z-axis pointing towards the North Pole</li>
     * </ul>
     * @param gpsPosition the geodetic position to convert.
     * @return List[x, y, z] in ECEF reference frame.
     */
    public static List<Double> geodetic2ecef(GpsPosition gpsPosition){
        double N, x, y, z;
        double a = 6378137;
        double b = 6356752.3142;
        double e = 0.0818191908426;
        double lat = Math.toRadians(gpsPosition.getLat());
        double lon = Math.toRadians(gpsPosition.getLon());
        double h = gpsPosition.getAlt();

        N = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(lat), 2));
        x = (N + h) * Math.cos(lat) * Math.cos(lon);
        y = (N + h) * Math.cos(lat) * Math.sin(lon);
        z = ((b * b) / (a * a) * N + h) * Math.sin(lat);
        return new ArrayList<>(Arrays.asList(x, y, z));
    }

    /**
     * Converts an ECEF position into Earth-Centered Inertial frame coordinates<br>
     * Where the ECEF reference frame has:
     * <ul>
     *     <li>The origin at the center of the Earth</li>
     *     <li>The x-axis pointing towards the intersection of the Equator and Prime Meridian</li>
     *     <li>The y-axis pointing towards the intersection of the Equator and 90° East longitude</li>
     *     <li>The z-axis pointing towards the North Pole</li>
     * </ul>
     * And the ECI reference frame has:
     * <ul>
     *     <li>The origin at the center of the Earth</li>
     *     <li>The x-axis pointing towards the intersection of the Equator and Prime Meridian</li>
     *     <li>The y-axis pointing towards the intersection of the Equator and 90° East longitude</li>
     *     <li>The z-axis pointing towards the North Pole</li>
     * </ul>
     *
     * @param xyz the ECEF position to convert.
     * @return List[x, y, z] in ECI reference frame.
     */
    public static List<Double> ecef2eci(List<Double> xyz){
        if (xyz.size() != 3) {
            throw new RuntimeException("Invalid ECF coordinates. Size of list must be 3");
        }
        double gmst = GpsTimeUtil.calculateGMST();
        double xECI, yECI, zECI;
        double xECEF = xyz.get(0);
        double yECEF = xyz.get(1);
        double zECEF = xyz.get(2);
        xECI = xECEF * Math.cos(gmst) - yECEF * Math.sin(gmst);
        yECI = xECEF * Math.cos(gmst) + yECEF * Math.cos(gmst);
        zECI = zECEF;
        return new ArrayList<>(Arrays.asList(xECI, yECI, zECI));
    }

    /**
     * Calculates the Keplerian Orbital Elements associated to the given ECI coordinates.<br>
     * <b>This simplified function assumes a stationary satellite (Vx, Vy, Vz) = 0 on a circular (e = 0) orbit.</b>
     * Where the orbital elements are:
     * <ul>
     *     <li>Radial distance (magnitude of the position vector)</li>
     *     <li>Inclination (angle between the orbital plane and the ECI z-axis)</li>
     *     <li>Longitude of the Ascending Node (angle between the ECI x-axis and the descending node in the orbital plane</li>
     * </ul>
     * And the ECI reference frame has:
     * <ul>
     *     <li>The origin at the center of the Earth</li>
     *     <li>The x-axis pointing towards the intersection of the Equator and Prime Meridian</li>
     *     <li>The y-axis pointing towards the intersection of the Equator and 90° East longitude</li>
     *     <li>The z-axis pointing towards the North Pole</li>
     * </ul>
     * @param xyz the ECI position to convert
     * @return the KOE in the following order: radial distance, inclination, longitude of the ascending node (Right ascention)
     */
    public static List<Double> eci2koe(List<Double> xyz){
        double xECI = xyz.get(0);
        double yECI = xyz.get(1);
        double zECI = xyz.get(2);
        double Omega_lon = Math.atan2(yECI, xECI); // Longitude of the Ascending Node
        double r = Math.sqrt(xECI * xECI + yECI * yECI + zECI * zECI);
        double i;
        if (r != 0.){
            i = 0; // Valid for a stationary satellite with non-zero radial distance
        } else {
            throw new RuntimeException("Impossible to calculate KOE for a stationary satellite with radial distance 0");
        }
        return new ArrayList<>(Arrays.asList(r, i, Omega_lon));
    }

    /**
     * Calculates the ECEF coordinates corresponding to the given Keplerian Orbital Elements.
     * <b>This simplified function assumes a stationary satellite (Vx, Vy, Vz) = 0 on a circular (e = 0) orbit.</b>
     * Where the ECEF reference frame has:
     * <ul>
     *     <li>The origin at the center of the Earth</li>
     *     <li>The x-axis pointing towards the intersection of the Equator and Prime Meridian</li>
     *     <li>The y-axis pointing towards the intersection of the Equator and 90° East longitude</li>
     *     <li>The z-axis pointing towards the North Pole</li>
     * </ul>
     * and the orbital elements are:
     * <ul>
     *     <li>Radial distance (magnitude of the position vector)</li>
     *     <li>Inclination (angle between the orbital plane and the ECI z-axis)</li>
     *     <li>Longitude of the Ascending Node (angle between the ECI x-axis and the descending node in the orbital plane</li>
     * </ul>
     * @param riO the KOE in the following order: radial distance, inclination, longitude of the ascending node (Right ascention)
     * @return List[x, y, z] in ECEF reference frame
     */
    public static List<Double> koe2ecef(List<Double> riO){
        double i = riO.get(1);
        double Omega = riO.get(2);
        double semiMajorAxis = riO.get(0);

        // Calculate the satellite's position in the orbital plane for a circular orbit
        double x_orbital = semiMajorAxis * Math.cos(0);  // Mean anomaly is 0 for a circular orbit
        double y_orbital = semiMajorAxis * Math.sin(0);

        // Perform the coordinate transformations to ECEF
        double x_ecef = Math.cos(Omega) * x_orbital - Math.sin(Omega) * y_orbital;
        double y_ecef = Math.sin(Omega) * x_orbital + Math.cos(Omega) * y_orbital;
        double z_ecef = y_orbital * Math.sin(i);

        return new ArrayList<>(Arrays.asList(x_ecef, y_ecef, z_ecef));
    }

    /**
     * Calculates the cartesian distance between two points.
     * @param xyz1 first point
     * @param xyz2 second point
     * @return the cartesian distance
     */
    public static double getCartesianDistance(List<Double> xyz1, List<Double> xyz2){
        if (xyz1.size() != xyz2.size()){
            throw new InvalidParameterException("The vectorial spaces must be of the same size");
        }
        double cumsum = 0;
        for (int i = 0; i < xyz1.size(); i++){
            cumsum += Math.pow((xyz1.get(i) - xyz2.get(i)), 2);
        }
        return Math.sqrt(cumsum);
    }

    /**
     * Provides the distance between the receiver and the satellite.
     * @param receiverPosition the position of the receiver.
     * @param satellite the ephemeris describing the position and orbit of the satellite.
     * @return the distance.
     */
    public static double getSatelliteDistance(GpsPosition receiverPosition, GpsEphemeris satellite){
        return getCartesianDistance(geodetic2ecef(receiverPosition), koe2ecef(satellite.asKoe()));
    }
}
