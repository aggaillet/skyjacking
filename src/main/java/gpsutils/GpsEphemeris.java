package gpsutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Gps Satellite Ephemeris
 * @author A.G. Gaillet
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
    public double rootOfA = Math.sqrt(20200000); // Mean orbital radius = 20'200 km
    public double e = 0; // eccentricity = 0 -> circular orbits

    /**
     * Inclination angle at reference time (radians)
     */
    public double i0; // to set in creation of satellites
    public double iDot = 0;

    /**
     * Argument of periapsis/pericenter/perigee (radians)
     */
    public double omega; // to set in creation of satellites

    /**
     * Longitude of ascending node of orbit plane / Right ascension of ascending node (radians)
     */
    public double omega0; // to set in creation of satellites

    /**
     * Rate of right ascension (radians/s)
     */
    public double omegaDot = 0; // to set od : satisfy o0 + (od - oe) * tk - oe * toe. Where oe = 7.2921151467 * 10 ^ -5 rad/s ??? TODO check

    /**
     * Mean anomaly of reference time (radians)
     */
    public double m0; // to set in creation of satellites
    public double deltaN = 0;
    // Corrections
    public double crc = 0;
    public double crs = 0;
    public double cuc = 0;
    public double cus = 0;
    public double cic = 0;
    public double cis = 0;

    public int prn;

    /**
     * Provides the basic keplerian orbital elements list.
     * @return the KOE in the following order: radial distance, inclination, longitude of the ascending node, argument of latitude.
     */
    public List<Double> asKoe(){
        List<Double> keo = new ArrayList<>();
        keo.add(0, rootOfA * rootOfA);
        keo.add(1, i0);
        keo.add(2, omega0);
        keo.add(3, omega + m0);
        return keo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GpsEphemeris that = (GpsEphemeris) o;
        return week == that.week && ura == that.ura && svHealth == that.svHealth && iode == that.iode &&
                iodc == that.iodc && Double.compare(that.toc, toc) == 0 && Double.compare(that.toe, toe) == 0
                && Double.compare(that.af0, af0) == 0 && Double.compare(that.af1, af1) == 0 &&
                Double.compare(that.af2, af2) == 0 && Double.compare(that.tgd, tgd) == 0 &&
                Double.compare(that.rootOfA, rootOfA) == 0 && Double.compare(that.e, e) == 0 &&
                Double.compare(that.i0, i0) == 0 && Double.compare(that.iDot, iDot) == 0 &&
                Double.compare(that.omega, omega) == 0 && Double.compare(that.omega0, omega0) == 0 &&
                Double.compare(that.omegaDot, omegaDot) == 0 && Double.compare(that.m0, m0) == 0 &&
                Double.compare(that.deltaN, deltaN) == 0 && Double.compare(that.crc, crc) == 0 &&
                Double.compare(that.crs, crs) == 0 && Double.compare(that.cuc, cuc) == 0 &&
                Double.compare(that.cus, cus) == 0 && Double.compare(that.cic, cic) == 0 &&
                Double.compare(that.cis, cis) == 0 && prn == that.prn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(week, ura, svHealth, iode, iodc, toc, toe, af0, af1, af2, tgd, rootOfA, e, i0, iDot, omega,
                omega0, omegaDot, m0, deltaN, crc, crs, cuc, cus, cic, cis, prn);
    }

    @Override
    public String toString() {
        return "GpsEphemeris{" +
                "week=" + week +
                ", ura=" + ura +
                ", svHealth=" + svHealth +
                ", iode=" + iode +
                ", iodc=" + iodc +
                ", toc=" + toc +
                ", toe=" + toe +
                ", af0=" + af0 +
                ", af1=" + af1 +
                ", af2=" + af2 +
                ", tgd=" + tgd +
                ", rootOfA=" + rootOfA +
                ", e=" + e +
                ", i0=" + i0 +
                ", iDot=" + iDot +
                ", omega=" + omega +
                ", omega0=" + omega0 +
                ", omegaDot=" + omegaDot +
                ", m0=" + m0 +
                ", deltaN=" + deltaN +
                ", crc=" + crc +
                ", crs=" + crs +
                ", cuc=" + cuc +
                ", cus=" + cus +
                ", cic=" + cic +
                ", cis=" + cis +
                ", prn=" + prn +
                '}';
    }
}