package gpsutils;

/**
 * Class containing all the information present in a full message sent by a given satellite.
 * @author Angelo G. Gaillet
 */
public class SatelliteMessageInfo {
    private static int PROGRESSIVE = 0;
    private final double deltaT; // in seconds
    private final GpsEphemeris ephemeris;
    private final GpsSupportData supportData;

    public SatelliteMessageInfo(GpsEphemeris ephemeris, GpsSupportData supportData, double deltaT){
        this.ephemeris = ephemeris;
        this.supportData = supportData;
        this.deltaT = deltaT;
        PROGRESSIVE ++;
        ephemeris.week = GpsTimeUtil.getCurrentGpsWeek();
        ephemeris.iode = PROGRESSIVE;
        ephemeris.iodc = PROGRESSIVE;
    }

    public GpsEphemeris getEphemeris() {
        return ephemeris;
    }

    public GpsSupportData getSupportData() {
        return supportData;
    }

    /**
     * Returns the spoofed transmission time in seconds.
     * @return a double value representing the signal propagation time from satellite to receiver in seconds.
     */
    public double getDeltaT() {
        return deltaT;
    }
}
