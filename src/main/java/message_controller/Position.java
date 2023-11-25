package message_controller;

import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Position {
    private final double lat;
    private final double lon;
    private final double alt;
    private final @Nullable OffsetDateTime dateTime;

    public Position() {
        this.lat = -1;
        this.lon = -1;
        this.alt = -1;
        this.dateTime = null;
    }

    public Position(double lat, double lon, double alt, String dateTimeString) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.dateTime = OffsetDateTime.parse(dateTimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
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

    public @Nullable OffsetDateTime getDateTime() {
        return dateTime;
    }
}
