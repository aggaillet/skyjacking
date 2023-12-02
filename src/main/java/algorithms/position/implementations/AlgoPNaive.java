package algorithms.position.implementations;

import algorithms.position.IPositionAlgorithm;
import gpsutils.GpsPosition;

/**
 * @author Angelo G. Gaillet
 */
public class AlgoPNaive implements IPositionAlgorithm {
    /**
     * Computes and returns the desired spoofed position.
     *
     * @param currentPosition real current position of the victim.
     * @param targetPosition  target desired position.
     * @return the spoofed position to be believed by the victim.
     */
    @Override
    public GpsPosition computeSpoofedPosition(GpsPosition currentPosition, GpsPosition targetPosition) {
        return null;
    }
}
