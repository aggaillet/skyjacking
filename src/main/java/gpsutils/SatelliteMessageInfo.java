package gpsutils;

/**
 * Record containing all the information present in a full message sent by a given satellite.
 *
 * @param deltaT in seconds
 * @author A.G. Gaillet
 */
public record SatelliteMessageInfo(GpsEphemeris ephemeris, GpsSupportData supportData, double deltaT) {
    private static int PROGRESSIVE = 0;

    public SatelliteMessageInfo(GpsEphemeris ephemeris, GpsSupportData supportData, double deltaT) {
        this.ephemeris = ephemeris;
        this.supportData = supportData;
        this.deltaT = deltaT;
        PROGRESSIVE++;
        ephemeris.week = GpsTimeUtil.getCurrentGpsWeek();
        ephemeris.iode = PROGRESSIVE;
        ephemeris.iodc = PROGRESSIVE;
    }

    /**
     * Returns the spoofed transmission time in seconds.
     *
     * @return a double value representing the signal propagation time from satellite to receiver in seconds.
     */
    @Override
    public double deltaT() {
        return deltaT;
    }
}
