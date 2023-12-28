package gpsutils;

/**
 * Class containing all the information present in a full message sent by a given satellite.
 * @author Angelo G. Gaillet
 */
public class SatelliteMessageInfo {
    private static final int NUM_NAV_MSGS = 4;
    private final int prn;
    private double deltaT; // in seconds
    private final GpsEphemeris ephemeris;
    private final GpsSupportData supportData;

    public SatelliteMessageInfo(int prn, GpsEphemeris ephemeris, GpsSupportData supportData, int deltaT){
        this.prn = prn;
        this.ephemeris = ephemeris;
        this.supportData = supportData;
        this.deltaT = deltaT;
    }

    public GpsEphemeris getEphemeris() {
        return ephemeris;
    }

    public GpsSupportData getSupportData() {
        return supportData;
    }

    /**
     * Returns the spoofed transmission time in seconds.
     * @return an int value representing the signal propagation time from satellite to receiver in seconds.
     */
    public double getDeltaT() {
        return deltaT;
    }

    public void setDeltaT(double deltaT){
        this.deltaT = deltaT;
    }

    public int getPrn() {
        return prn;
    }
}
