package gpsutils;

/**
 * Gps Satellite Ephemeris
 * @author Angelo G. Gaillet
 */
public class GpsEphemeris {
    public int prn;
    public int week;
    public double svAccuracyM;
    public int ura; // TODO remove either this or svAccuracy
    public int svHealth;
    public int iode;
    public int iodc;
    public double toc;
    public double toe;
    public double tom;
    public double af0;
    public double af1;
    public double af2;
    public double tgd;
    public double rootOfA;
    public double e;
    public double i0;
    public double iDot;
    public double omega;
    public double omega0;
    public double omegaDot;
    public double m0;
    public double deltaN;
    public double crc;
    public double crs;
    public double cuc;
    public double cus;
    public double cic;
    public double cis;
}