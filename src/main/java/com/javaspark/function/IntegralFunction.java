package com.javaspark.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.javaspark.model.CalculationResponse;

public class IntegralFunction extends JavaSparkFunction implements Serializable {

	public static final double A = 0;
	public static final double B = Math.PI/2;
	
	public IntegralFunction(Double alpha, Double gamma) {
		super(alpha, gamma);
	}

	@Override
	public CalculationResponse calcFunction(JavaSparkContext sc, double k, double n) {
		CalculationResponse response = new CalculationResponse();
		
		try {
			response.getData().put("resMatr", 
					((n > k) ? this.toArray(rowParallel(sc, k, n), false) : this.toArray(colParallel(sc, k, n), true))); 
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}
		
		return response;
	}


	@Override
	public Double calcFunctionElement(double k, double tau) throws Exception {
		return (k == 0) ? calcSecondFunctionForZeroElement(tau) : calcSecondFunctionNonZeroElement(k, tau);
	}

	
	private Double midRes(double x, double alpha) {
		return Math.atan((2 + this.getAlpha() + 1) * Math.tan(x) / (this.getAlpha() + 1)) + Math.atan((2 + this.getAlpha() + 1) * Math.tan(x) / (2 + this.getAlpha() + 1));
	}
	
	private Double calcSecondFunctionNonZeroElement(double k, double tau) throws Exception {
		// Simpson's method for integral approximation
		return (B - A) / 6 * (f(A, k, tau) + 4 * f((A + B) / 2, k, tau) + f(B, k, tau));
	}

	private Double f (double x, double k, double tau) {
		return Math.cos(x + 2 * midRes(x, this.getAlpha())) * (Math.cos((2 * k + this.getAlpha() + 1) * C * this.getGamma() * tau / (2 * Math.tan(x)))) / Math.cos(x);
	}
	
	private Double calcSecondFunctionForZeroElement(double tau) {
		// Simpson's method for integral approximation
		return (B - A) / 6 * (f0(A, tau) + 4 * f0((A + B) / 2, tau) + f0(B, tau));
	}
	
	private Double f0 (double x, double tau) {
		return  Math.cos((this.getAlpha() + 1) * C * this.getGamma() * tau * Math.cos(x)/ (2 * Math.sin(x)));
	}
}
