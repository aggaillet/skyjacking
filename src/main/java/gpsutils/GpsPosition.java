package gpsutils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Position Class regroup the GPS Data Position of the UAV, plus its time
 * @author Malik Willemy
 */
public class GpsPosition {
    private final double lat;
    private final double lon;
    private final double alt;
    private final OffsetDateTime dateTime;

    public GpsPosition(double lat, double lon, double alt, OffsetDateTime dateTime) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.dateTime = dateTime;
    }

    public GpsPosition(double lat, double lon, double alt, String dateTimeString) {
        this(lat, lon, alt, OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)); //TODO check formatter is correct
    }

    public GpsPosition(double lat, double lon, double alt) {
        this(lat, lon, alt, (OffsetDateTime) null);
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

    public String toString(){
        return "lat: " + lat + " lon: " + lon + " alt: " + alt + " timestamp: " + dateTime.toString() + "\n";
    }
}
