package gpsutils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class GpsPosition {
/**
 * Position Class regroup the GPS Data Position of the UAV, plus its time
 */
public class GpsPosition {
    private final double lat;
    private final double lon;
    private final double alt;
    private final OffsetDateTime dateTime;

    public GpsPosition() {
        this.lat = 0;
        this.lon = 0;
        this.alt = 0;
        this.dateTime = null;
    }

    public GpsPosition(double lat, double lon, double alt, String dateTimeString) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.dateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public GpsPosition(double lat, double lon, double alt, OffsetDateTime dateTime) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.dateTime = dateTime;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public double getAlt() {
        return alt;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }
}
