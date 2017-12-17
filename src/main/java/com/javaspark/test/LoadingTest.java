package com.javaspark.test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.javaspark.model.CalculationResponse;
import com.javaspark.util.JavaSparkHelper;

import Jama.Matrix;

public class LoadingTest {

	private static Matrix transposedMatrix;
	
	
	public static void doTests(CalculationResponse response, Double[][] matrix) throws Exception {
		
		double[][] tmp = convertDouble(matrix);
		
		response.getData().put(JavaSparkHelper.INVERSE_TEST_MSG, JavaSparkHelper.timeFormat(inverseMatrixTest(tmp)));
		response.getData().put(JavaSparkHelper.TRANSPARENCY_TEST_MSG, JavaSparkHelper.timeFormat(transposedMatrixTest(tmp)));
		response.getData().put(JavaSparkHelper.MULTIPLY_TEST_MSG, JavaSparkHelper.timeFormat(matrixMultiplicationTest(tmp)));
		
	}
	
	public static long inverseMatrixTest(double[][] matrix) throws Exception {
		long startTime = System.currentTimeMillis();
		Matrix matr = (new Matrix(matrix)).inverse();
		return (System.currentTimeMillis() - startTime);
	}
	
	
	public static long transposedMatrixTest(double[][] matrix) throws Exception {
		long startTime = System.currentTimeMillis();
		transposedMatrix = (new Matrix(matrix)).transpose();
		return (System.currentTimeMillis() - startTime);
	}
	
	public static long matrixMultiplicationTest (double[][] matrix) throws Exception {

		long startTime = System.currentTimeMillis();
		
		if(transposedMatrix == null)
			transposedMatrix = (new Matrix(matrix)).transpose();
		
		double[][] tmp = new double[matrix.length][transposedMatrix.getArray()[0].length];
		toZero(tmp);
		
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				for (int k = 0; k < transposedMatrix.getArray().length; k++) {
					tmp[i][j] += matrix[i][k] * transposedMatrix.getArray()[k][j];
				}
			}
		}
		
		return (System.currentTimeMillis() - startTime);
	}
	
	private static void toZero (double[][] input) {
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length; j++) {
				input[i][j] = 0.0;
			}
		}
	}
	
	public static double[][] convertDouble (Double[][] matrix) {
		double[][] res = new double[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				res[i][j] = matrix[i][j];
		return res;
	}
}
