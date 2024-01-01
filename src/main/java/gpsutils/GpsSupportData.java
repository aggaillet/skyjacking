package gpsutils;

import java.util.Arrays;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GpsSupportData that = (GpsSupportData) o;
        return Double.compare(that.a0UTC, a0UTC) == 0 && Double.compare(that.a1UTC, a1UTC) == 0 && tot == that.tot &&
                wnt == that.wnt && tls == that.tls && wnlsf == that.wnlsf && dn == that.dn && tlsf == that.tlsf &&
                Arrays.equals(ionoAlpha, that.ionoAlpha) && Arrays.equals(ionoBeta, that.ionoBeta);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(a0UTC, a1UTC, tot, wnt, tls, wnlsf, dn, tlsf);
        result = 31 * result + Arrays.hashCode(ionoAlpha);
        result = 31 * result + Arrays.hashCode(ionoBeta);
        return result;
    }

    @Override
    public String toString() {
        return "GpsSupportData{" +
                "ionoAlpha=" + Arrays.toString(ionoAlpha) +
                ", ionoBeta=" + Arrays.toString(ionoBeta) +
                ", a0UTC=" + a0UTC +
                ", a1UTC=" + a1UTC +
                ", tot=" + tot +
                ", wnt=" + wnt +
                ", tls=" + tls +
                ", wnlsf=" + wnlsf +
                ", dn=" + dn +
                ", tlsf=" + tlsf +
                '}';
    }
}
