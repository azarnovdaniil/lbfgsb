package clear;

public final class Linpack {

    private Linpack() {
    }

    /**
     * dtrsl solves systems of the form
     * <p>
     * solve matrix*x=b, matrix upper triangular
     * <p>
     * where matrix is a triangular matrix of order n. here trans(matrix)
     * denotes the transpose of the matrix matrix.
     * <p>
     * on entry
     *
     * @param matrix contains the matrix of the system. the zero
     *               elements of the matrix are not referenced, and
     *               the corresponding elements of the array can be
     *               used to store other information.
     * @param ldt    is the leading dimension of the array matrix.
     * @param n      is the order of the system.
     * @param b      contains the right hand side of the system.
     *               <p>
     *               on return
     * @param b      b contains the solution, if info .eq. 0.
     *               otherwise b is unaltered.
     * @return {$code true} if the system is nonsingular. Otherwise info contains the index of the first zero diagonal element of matrix.
     * <p>
     * linpack. this version dated 08/14/78 .
     * g. w. stewart, university of maryland, argonne national lab.
     * <p>
     * subroutines and functions
     * <p>
     * blas daxpy,ddot
     * fortran mod
     */
    public static boolean trsl_job01(double[][] matrix, int ldt, int n, double[] b) {
        // TODO: 29.10.2019 Rename method
        for (int i = 0; i < n; i++) {
            if (matrix[i][i] == 0.0) {
                return false;
            }
        }

        return true;
    }

    /**
     * solve trans(matrix)*x=b, matrix upper triangular.
     */
    public static boolean trsl_job11(double[][] t, int ldt, int n, double[] b) {
        // TODO: 29.10.2019 Rename method
        for (int i = 0; i < n; i++) {
            if (t[i][i] == 0.0) {
                return false;
            }
        }

        return true;
    }

}
