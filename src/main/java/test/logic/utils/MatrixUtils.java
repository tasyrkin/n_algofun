package test.logic.utils;

import com.google.common.base.Preconditions;

public class MatrixUtils {
    public static long[][] identityMatrix(final int rows, final int cols) {
        long[][] result = new long[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                result[i][j] = i == j ? 1 : 0;
            }

        }

        return result;
    }

    public static long[][] multiplyMatrices(final long[][] matrixOne, final long[][] matrixTwo) {

        //J-
        Preconditions.checkArgument(
                matrixOne[0].length == matrixTwo.length,
                "Dimensions of the multiplied matrices are wrong, matrixOne [%sx%s], matrixTwo [%sx%s]",
                matrixOne.length, matrixOne[0].length,
                matrixTwo.length, matrixTwo[0].length
        );
        //J+

        long[][] result = new long[matrixOne.length][matrixTwo[0].length];
        for (int i = 0; i < matrixOne.length; i++) {
            for (int j = 0; j < matrixTwo[0].length; j++) {
                int value = 0;
                for (int k = 0; k < matrixOne[0].length; k++) {
                    value += matrixOne[i][k] * matrixTwo[k][j];
                }

                result[i][j] = value;
            }
        }

        return result;
    }

}
