package lbfgsb;

public interface MinimizationProblem {
    int getVariablesNo();

    DifferentiableFunction getFunction();

    double[] getStartingPoint();

    double[] getExpectedMinimum();
}
