package gpsutils;

/**
 * Gps Satellite Ephemeris
 * @author Angelo G. Gaillet
 */
public class GpsEphemeris {
    public int week;
    public int ura = 0; // 0 -> highest accuracy
    public int svHealth = 0; // 0 -> all okay
    public int iode; // increase every full navigation message
    public int iodc; // set equal to iode
    public double toc; // set to time of sending
    public double toe; // set to time of sending
    // Clock aging parameters
    public double af0 = 0;
    public double af1 = 0;
    public double af2 = 0;
    public double tgd = 0; // time differential between L1 and L2 signals
    public double rootOfA; // to set. Square root of the orbital radius
    public double e = 0; // eccentricity = 0 -> circular orbits
    public double i0; // to set
    public double iDot = 0;
    public double omega; // to set
    public double omega0; // to set
    public double omegaDot; // to set od. Note: satisfy o0 + (od - oe) * tk - oe * toe. Where oe = 7.2921151467 * 10 ^ -5 rad/s
    public double m0 = 0;
    public double deltaN = 0;
    // Corrections
    public double crc = 0;
    public double crs = 0;
    public double cuc = 0;
    public double cus = 0;
    public double cic = 0;
    public double cis = 0;
}