package lbfgsb;

import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class TestClass {
    protected static final double DELTA = 1E-15;
    protected static final double SOFT_DELTA = 1E-6;
}