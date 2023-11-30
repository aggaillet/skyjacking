package gpsutils;

/**
 * Class containing all the information present in a full message sent by a given satellite.
 * @author Angelo G. Gaillet
 */
public class SatelliteMessageInfo {
    private static final int NUM_NAV_MSGS = 4;
    private final int prn;
    private int[] tows;
    private final GpsEphemeris ephemeris;
    private final GpsSupportData supportData;

    public SatelliteMessageInfo(int prn){
        this(prn, new GpsEphemeris(), new GpsSupportData(), new int[NUM_NAV_MSGS]);
    }

    public SatelliteMessageInfo(int prn, GpsEphemeris ephemeris, GpsSupportData supportData, int[] tows){
        this.prn = prn;
        this.ephemeris = ephemeris;
        this.supportData = supportData;
        this.tows = tows;
    }

    public GpsEphemeris getEphemeris() {
        return ephemeris;
    }

    public GpsSupportData getSupportData() {
        return supportData;
    }

    public int[] getTow() {
        return tows;
    }

    public void setTow(int[] tows){
        this.tows = tows;
    }

    public int getPrn() {
        return prn;
    }
}
