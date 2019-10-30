package clear;

import org.junit.jupiter.api.Test;

import static clear.Blas.scale;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BlasTest {

    @Test
    void testScale0() {
        double[] vector = {2, 4};
        scale(2, 2.0, vector);

        assertArrayEquals(new double[]{4.0, 8.0}, vector, 1e-6);
    }

    @Test
    void testScale1() {
        double[] vector = {2, 4};
        scale(4, 2.0, vector);

        assertArrayEquals(new double[]{8.0, 16.0}, vector, 1e-6);
    }

}