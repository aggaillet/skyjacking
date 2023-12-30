package algorithms.position.implementations;

import algorithms.position.IPositionAlgorithm;
import gpsutils.GpsPosition;

/**
 * Naive algorithm for spoofed position determination.
 * Provides the symmetric position to the target position with respect to the current real position of the UAV.
 * @author A.G. Gaillet
 */
public class AlgoPNaive implements IPositionAlgorithm {
    /**
     * Computes and returns the desired spoofed position.
     * Implements a naive algorithm determining the spoofed position to be the position that is the symmetric to the
     * target position with respect to the real position.
     *
     * @param currentPosition real current position of the victim.
     * @param targetPosition  target desired position.
     * @return the spoofed position to be believed by the victim.
     */
    @Override
    public GpsPosition computeSpoofedPosition(GpsPosition currentPosition, GpsPosition targetPosition) {
        double x = 2. * currentPosition.getLat() - targetPosition.getLat();
        double y = 2. * currentPosition.getLon() - targetPosition.getLon();
        double z = 2. * currentPosition.getAlt() - targetPosition.getAlt();
        return new GpsPosition(x, y, z, currentPosition.getDateTime());
    }
}
