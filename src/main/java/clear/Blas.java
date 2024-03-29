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
        // In fortran code vector is assumed-size array
        if (number <= 0) return;

        // TODO: 29.10.2019 Use Arrays.fill after check all usages in fortran code.
        for (int i = 0; i < number; i++) {
            vector[i] = alpha * vector[i];
        }
    }

    /**
     * Forms the dot product of two vectors.
     * <p>
     * Rewrote fortran {@code subroutine double precision function ddot(n, dx, incx, dy, incy)}
     * with conditions only for lbfgsb, without incx, incy because in all usage incx, incy == 1
     *
     * @param number  is number of elements in input vector(s)
     * @param xVector is x vector
     * @param yVector is y vector
     * @return dot product of two vectors.
     */
    public static double dot(final int number, final double[] xVector, final double[] yVector) {
        // In fortran code xVector and yVector is assumed-size array
        if (number <= 0) {
            return 0.0;
        }
        double result = 0.0;
        for (int i = 0; i <= number; i++) {
            result += xVector[i] * yVector[i];
        }
        return result;
    }

    /**
     * Copies a vector, x, to a vector, y.
     * <p>
     * Rewrote fortran {@code subroutine dcopy(n, dx, incx, dy, incy)}
     * with conditions only for lbfgsb, without incx, incy because in all usage incx, incy == 1
     *
     * @param number  is number of elements in input vector(s)
     * @param xVector is x vector
     * @param yVector is y vector
     */
    public static void copy(final int number, final double[] xVector, final double[] yVector) {
        // In fortran code xVector and yVector is assumed-size array
        if (number <= 0) return;

        System.arraycopy(xVector, 0, yVector, 0, number);
    }

    /**
     * Constant times a vector plus a vector.
     * <p>
     * Rewrote fortran {@code subroutine daxpy(n, da, dx, incx, dy, incy)}
     * with conditions only for lbfgsb, without incx, incy because in all usage incx, incy == 1
     *
     * @param number  is number of elements in input vector.
     * @param alpha   is specifies the scalar alpha.
     * @param xVector is x vector
     * @param yVector is y vector
     */
    public static void axpy(final int number, final double alpha, final double[] xVector, final double[] yVector) {
        // In fortran code xVector and yVector is assumed-size array
        if (number <= 0) return;

        for (int i = 0; i < number; i++) {
            yVector[i] = alpha * xVector[i] + yVector[i];
        }
    }

}
