package lbfgsb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleProblemsTest extends TestClass {

    @Test
    public void hellicalValleyTest() throws LBFGSBException {
        checkMinimizationProblem(new HellicalValleyProblem(), 10e-6);
    }

    @Test
    public void boundedHellicalValleyTest() throws LBFGSBException {
        checkBoundedMinimizationProblem(new HellicalValleyProblem(), 10e-3);
    }

    @Test // uncommented
	public void powellBadlyScaledTest() throws LBFGSBException{
		checkMinimizationProblem(new PowellBadlyScaledProblem(), 10);
	}

	@Test // uncommented
	public void boundedPowellBadlyScaledTest() throws LBFGSBException{
		checkBoundedMinimizationProblem(new PowellBadlyScaledProblem(), 1);
	}

    @Test
    public void woodTest() throws LBFGSBException {
        checkMinimizationProblem(new WoodProblem(), 10e-5);
    }

    @Test
    public void boundedWoodTest() throws LBFGSBException {
        checkBoundedMinimizationProblem(new WoodProblem(), 10e-6);
    }

    @Test
    public void rosenbrockTest() throws LBFGSBException {
        checkMinimizationProblem(new RosenbrockProblem(), 10e-6);
    }

    @Test
    public void squareTest() throws LBFGSBException {
        checkMinimizationProblem(new SquareProblem(), 10e-6);
    }

    @Test // uncommented
    public void boundedSquareTest() throws LBFGSBException {
        checkBoundedMinimizationProblem(new SquareProblem(), 10e-6);
    }

    @Test // uncommented
    public void testPowellSingular() throws LBFGSBException {
        checkMinimizationProblem(new PowellSingularProblem(), 10e-2);
    }

    private void checkMinimizationProblem(MinimizationProblem prob,
                                          double precision)
            throws LBFGSBException {
        Minimizer alg = new Minimizer();

//		StopConditions stopConditions = alg.getStopConditions();
//		stopConditions.setFunctionReductionFactorInactive();
//		stopConditions.setMaxIterationsInactive();
//		stopConditions.setMaxGradientNormInactive();
//		alg.getStopConditions().setFunctionReductionFactor(1);
//		alg.setIterationFinishedListener(new IterationListener());
//		alg.setDebugLevel(999);

        Result ret = alg.run(prob.getFunction(), prob.getStartingPoint());

//		assertTrue(toString(prob.getExpectedMinimum())+"!="+toString(ret.point),
//				areEqual(prob.getExpectedMinimum(), ret.point, precision));
        double[] expectedMinimum = prob.getExpectedMinimum();
        double[] point = ret.point;
        assertArrayEquals(expectedMinimum, point, precision);
    }

    private void checkBoundedMinimizationProblem(
            BoundedMinimizationProblem prob, double precision)
            throws LBFGSBException {
        Minimizer alg = new Minimizer();

//		StopConditions stopConditions = alg.getStopConditions();
//		stopConditions.setFunctionReductionFactorInactive();
//		stopConditions.setMaxIterationsInactive();
//		stopConditions.setMaxGradientNormInactive();

        alg.setBounds(prob.getBounds());

        Result ret = alg.run(prob.getFunction(), prob.getStartingPoint());
        double f = ret.functionValue;
        assertEquals(prob.getExpectedBoundedMinimumFunctionValue(),
                f, precision);
    }

    private static String toString(double[] point) {
        StringBuilder b = new StringBuilder();
        b.append("[");
        for (int i = 0; i < point.length; i++) {
            b.append(point[i]);
            if (i != point.length - 1) b.append(",");
        }
        b.append("]");
        return b.toString();
    }

    private static boolean areEqual(double[] p0, double[] p1,
                                    double precision) {
        if (p0.length != p1.length) return false;
        for (int i = 0; i < p0.length; i++)
            if (Math.abs(p0[i] - p1[i]) > precision) return false;
        return true;
    }
}

//class IterationListener implements IterationFinishedListener{
//	int i = 0;
//	@Override
//	public void iterationFinished(double[] point,
//			double functionValue, double[] gradient) {
//		System.out.println("Iteration "+i+
//				": finished with x="+toString(point)+", "+
//				"fun_val="+functionValue+", gradient="+toString(gradient));
//		i++;
//	}
//	
//	private static String toString(double[] point){
//		StringBuilder b = new StringBuilder();
//		b.append("[");
//		for(int i = 0; i < point.length; i++){
//			b.append(point[i]);
//			if(i!=point.length-1) b.append(",");
//		}
//		b.append("]");
//		return b.toString();
//	}	
//}
