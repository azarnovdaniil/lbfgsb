package lbfgsb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * High-level wrapper for the L-BFGS-B algorithm
 *
 * @author Mateusz Kobos
 */
public class Minimizer {
    private static int defaultCorrectionsNo = 5;

    private int correctionsNo = defaultCorrectionsNo;
    private StopConditions stopConditions = new StopConditions();
    private List<Bound> bounds = null;
    private IterationFinishedListener listener = null;
    private int debugLevel = -1;

    /**
     * Create algorithm with default values for all of the optional features.
     */
    public Minimizer() {
    }

    public int getCorrectionsNo() {
        return correctionsNo;
    }

    /**
     * Set number of corrections used in the limited memory matrix.
     * According to the original fortran documentation,
     * [3, 20] range is recommended.
     *
     * @param value number of corrections
     */
    public void setCorrectionsNo(int value) {
        this.correctionsNo = value;
    }

    public StopConditions getStopConditions() {
        return stopConditions;
    }

    public List<Bound> getBounds() {
        return Collections.unmodifiableList(bounds);
    }

    /**
     * Set bounds for all of the variables.
     *
     * @param bounds consecutive elements of the list
     *               correspond to consecutive variables
     */
    public void setBounds(List<Bound> bounds) {
        this.bounds = new ArrayList<>(bounds);
    }

    /**
     * A convenience method that makes all of the variables unbounded
     *
     * @param variablesNo number of variables in the problem
     */
    public void setNoBounds(int variablesNo) {
        ArrayList<Bound> boundArrayList = new ArrayList<>();
        for (int i = 0; i < variablesNo; i++)
            boundArrayList.add(new Bound(null, null));
        this.bounds = boundArrayList;
    }

    /**
     * Set the listener which will be called after each iteration
     *
     * @param listener called after the iteration is finished
     */
    public void setIterationFinishedListener(
            IterationFinishedListener listener) {
        this.listener = listener;
    }

    public int getDebugLevel() {
        return debugLevel;
    }

    /**
     * @param value see the description of the debug level in the original
     *              fortran documentation for {@code iprint} argument
     */
    public void setDebugLevel(int value) {
        debugLevel = value;
    }


    /**
     * Starts the algorithm
     *
     * @param fun           function we want to minimize
     * @param startingPoint starting point of the algorithm
     * @return result of the minimization
     * @throws LBFGSBException thrown if the input parameters were erroneous
     */
    public Result run(DifferentiableFunction fun, double[] startingPoint) throws LBFGSBException {
        int variablesNo = startingPoint.length;
        if (bounds == null) setNoBounds(variablesNo);
        if (bounds.size() != variablesNo)
            throw new LBFGSBException("Bounds number (" + bounds.size() + ") " +
                    "doesn't match starting point size (" + variablesNo + ")");
        Wrapper alg = new Wrapper(variablesNo, correctionsNo);
        alg.setX(startingPoint);
        alg.setBounds(bounds);
        alg.setFunctionFactor(stopConditions.getFunctionReductionFactor());
        alg.setMaxGradientNorm(stopConditions.getMaxGradientNorm());
        alg.setDebugLevel(debugLevel);
        IterationsInfo info = alg.iterate(fun,
                stopConditions.getMaxIterations(), listener);
        Result result = new Result(alg.getX(),
                alg.getFunctionValue(), alg.getGradient(), info);
        alg.close();
        return result;
    }
}
