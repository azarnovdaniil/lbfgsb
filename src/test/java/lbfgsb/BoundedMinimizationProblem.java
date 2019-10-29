package lbfgsb;

import java.util.ArrayList;

public interface BoundedMinimizationProblem extends MinimizationProblem {
    ArrayList<Bound> getBounds();

    double getExpectedBoundedMinimumFunctionValue();
}
