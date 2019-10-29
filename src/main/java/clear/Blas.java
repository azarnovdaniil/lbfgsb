package clear;

public final class Blas {

    private Blas() {
    }

    public static void scale(final int number, final double alpha, final double[] array, final int offset) {
        if (number <= 0 || offset <= 0) return;

        if (offset == 1) {
            for (int i = 0; i < number; i++) {
                array[i] = alpha * array[i];
            }
        } else {
            for (int i = 0; i < number * offset; i += offset) {
                array[i] = alpha * array[i];
            }
        }
    }

    private double dot(final int number, final double[] xArray, final int xOffset, final double[] yArray, final int yOffset) {
        if (number <= 0) {
            return 0.0;
        }
        double result = 0.0;
        for (int i = 0; i <= number; i++) {
            result += xArray[i + xOffset] * yArray[i + yOffset];
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
