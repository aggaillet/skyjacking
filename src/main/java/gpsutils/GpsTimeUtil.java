package gpsutils;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class providing functions to deal with GPS time.
 * @author A.G. Gaillet
 */
public class GpsTimeUtil {
    // UTC to GPS time offset in seconds
    private static final long GPS_EPOCH_TIME_MILLIS = 315964800000L;

    // Seconds in a GPS week
    private static final int SECONDS_IN_GPS_WEEK = 604800;

    private static final double GMST_0 = 6.697374558 + 0.06570982441908 * (System.currentTimeMillis() - new Date(2000 - 1900, Calendar.JANUARY, 1).getTime()) / 3600000.0;

    /**
     * Calculates the current Greenwich Mean Sidereal Time
     * @return the current GMST
     */
    public static double calculateGMST() {
        // Get the current time in UTC
        long currentTimeMillis = System.currentTimeMillis();
        Date utcTime = new Date(currentTimeMillis);

        // Calculate GMST
        double gmst = GMST_0 + (1.00273790925 / 15) * (utcTime.getHours() * 3600 + utcTime.getMinutes() * 60 + utcTime.getSeconds());

        // Ensure GMST is within the range [0, 24) hours
        gmst %= 24.0;

        return gmst;
    }

    /**
     * Get the current gps week.
     * @return the number of the current gps week.
     */
    public static int getCurrentGpsWeek(){
        // Get the current UTC time in milliseconds
        long currentUtcTimeMillis = System.currentTimeMillis();
        // Calculate the GPS time by subtracting the GPS epoch time
        long gpsTimeSeconds = (currentUtcTimeMillis - GPS_EPOCH_TIME_MILLIS) / 1000;
        // Calculate the GPS week
        return (int)(gpsTimeSeconds / SECONDS_IN_GPS_WEEK);
    }

    /**
     * Get the current Time Of the Week (TOW).
     * @return the TOW at the time of the method call.
     */
    public static double getTowOfNow(){
        // Get the current UTC time in milliseconds
        long currentUtcTimeMillis = System.currentTimeMillis();
        // Calculate the GPS time by subtracting the GPS epoch time
        double gpsTimeSeconds = (currentUtcTimeMillis - GPS_EPOCH_TIME_MILLIS) / 1000.;
        // Calculate the Time of Week (TOW)
        return (gpsTimeSeconds % SECONDS_IN_GPS_WEEK); // TODO: verify not 1.5 seconds
    }

    /**
     * Get the Time Of the Week in "delay" time.
     * @param delay delay in seconds.
     * @return the TOW in delay seconds.
     */
    public static double getTowDelayed(double delay){
        return (getTowOfNow() + delay) % SECONDS_IN_GPS_WEEK;
    }

}
