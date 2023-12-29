package gpsutils;

/**
 * @author Angelo G. Gaillet
 */
public class GpsSupportData {
    public double[] ionoAlpha = {0, 0, 0, 0};
    public double[] ionoBeta = {0, 0, 0, 0};
    public double a0UTC = 0;
    public double a1UTC = 0;
    public short tot; // set to time of send
    public short wnt = 0;
    public short tls = 0;
    public short wnlsf = (short) (GpsTimeUtil.getCurrentGpsWeek() + 1); // set to next gps week
    public short dn = 7;
    public short tlsf = 0;
}
