package clear;

public final class Blas {

    private Blas() {
    }

    /**
     * Scales {@code vector} by a {@code alpha}
     * <p>
     * Rewrote fortran {@code subroutine dscal(n, da, dx, incx)}
     * with conditions only for lbfgsb, without incx, because in all usage incx == 1
     *
     * @param number is number of elements in input vector.
     * @param alpha  is specifies the scalar alpha.
     * @param vector is vector.
     */
    public static void scale(final int number, final double alpha, final double[] vector) {
        if (number <= 0) return;

        // TODO: 29.10.2019 Use Arrays.fill after check all usages in fortran code.
        for (int i = 0; i < number; i++) {
            vector[i] = alpha * vector[i];
        }
    }

    /**
     * DDOT forms the dot product of two vectors.
     * <p>
     * Rewrote fortran {@code subroutine double precision function ddot(n, dx, incx, dy, incy)}
     * with conditions only for lbfgsb, without incx, incy because in all usage incx, incy == 1
     *
     * @param number is number of elements in input vector(s)
     * @param xVector is x vector
     * @param yVector is y vector
     * @return dot product of two vectors.
     */
    private double dot(final int number, final double[] xVector, final double[] yVector) {
        if (number <= 0) {
            return 0.0;
        }
        double result = 0.0;
        for (int i = 0; i <= number; i++) {
            result += xVector[i] * yVector[i];
        }
        return result;
    }

    private void copy(final int number, final double[] xArray, final int xOffset, final double[] yArray, final int yOffset) {
        if (number <= 0) return;

        if (xOffset == 1 && yOffset == 1) {
            System.arraycopy(xArray, 0, yArray, 0, number);
        } else {
            int ix = 0;
            int iy = 0;

            if (xOffset < 0) ix = (-number + 1) * xOffset;
            if (yOffset < 0) iy = (-number + 1) * yOffset;

            for (int i = 0; i < number; i++) {
                yArray[iy] = xArray[ix];
                ix = ix + xOffset;
                iy = iy + yOffset;
            }
        }
    }

}
