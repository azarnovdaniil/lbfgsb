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
        // TODO: 29.10.2019 add method body

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
        // TODO: 29.10.2019 add method body

        return true;
    }

    /**
     * dpofa factors a double precision symmetric positive definite
     * matrix.
     * <p>
     * dpofa is usually called by dpoco, but it can be called
     * directly with a saving in time if  rcond  is not needed.
     * (time for dpoco) = (1 + 18/n)*(time for dpofa) .
     * <p>
     * on entry
     * <p>
     * a       double precision(lda, n)
     * the symmetric matrix to be factored.  only the
     * diagonal and upper triangle are used.
     * <p>
     * lda     integer
     * the leading dimension of the array  a .
     * <p>
     * n       integer
     * the order of the matrix  a .
     * <p>
     * on return
     * <p>
     * a       an upper triangular matrix  r  so that  a = trans(r)*r
     * where  trans(r)  is the transpose.
     * the strict lower triangle is unaltered.
     * if  info .ne. 0 , the factorization is not complete.
     * <p>
     * info    integer
     * = 0  for normal return.
     * = k  signals an error condition.  the leading minor
     * of order  k  is not positive definite.
     * <p>
     * linpack.  this version dated 08/14/78 .
     * cleve moler, university of new mexico, argonne national lab.
     * <p>
     * subroutines and functions
     * <p>
     * blas ddot
     * fortran sqrt
     * <p>
     * internal variables
     */
    public static boolean dpofa(double[][] a, int lda, int n) {

        double s = 0.0;
        int jm1;

        for (int i = 0; i < n; i++) {
            jm1 = i - 1;
            if (jm1 < 0) {
                s = a[i][i] - s;
                if (s <= 0.0) {
                    return false;
                } else {
                    a[i][i] = Math.sqrt(s);
                }
            } else {
                for (int j = 0; j < jm1; j++) {
                    // TODO: 29.10.2019 add method body
                }
            }
        }
        return true;
    }

}
