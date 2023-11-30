package algorithms.position;

import gpsutils.GpsPosition;

/**
 * @author Angelo G. Gaillet
 * Interface for all algorithms calculating the desired spoofed position (i.e. the position that will be believed by the target
 * as real) given the current real position of the victim and the desired final target position of the vicitim after the attack's
 * conclusion.
 */
interface IPositionAlgorithm {
    /**
     * Computes and returns the desired spoofed position.
     * @param currentPosition real current position of the victim.
     * @param targetPosition target desired position.
     * @return the spoofed position to be believed by the victim.
     */
    public GpsPosition computeSpoofedPosition(GpsPosition currentPosition, GpsPosition targetPosition);
}
