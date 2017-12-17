package com.javaspark.function;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.javaspark.model.CalculationResponse;

public class RecurrenceFunction extends JavaSparkFunction implements Serializable {

	public RecurrenceFunction(Double alpha, Double gamma) {
		super(alpha, gamma);
	}

	@Override
	public CalculationResponse calcFunction(JavaSparkContext sc, double k, double n) {
		CalculationResponse response = new CalculationResponse();

		try {
			response.getData().put("resMatr", 
					((n > k) ? this.toArray(rowParallel(sc, k, n), false) : this.toArray(colParallel(sc, k, n), true))); // вообще тут должны бы быть точки для графика.
		} catch(Exception e) {
			response.getErrors().add(e.getMessage());
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public Double calcFunctionElement(double k, double tau) throws Exception {
		if (k > 1) {
			return firstConst(k, tau) * calcFunctionElement(k - 1, tau) - 
					secondConst(k, tau) * calcFunctionElement(k - 2, tau);
		} else return (k == 1) ? firstConst(k, tau) * calcFunctionElement(k - 1, tau) : 1.0;
	}

	
	private double firstConst(double k, double tau) {
		return ((this.getAlpha() + 2 * k) * (this.getAlpha() + 2 * k - 2) * (1 - 2 * Math.exp(-1 * C * this.getGamma() * tau)) + this.getAlpha() * this.getAlpha()) / 
				(2 * k * (this.getAlpha() + 2 * k - 2) * (this.getAlpha() + k)) * (this.getAlpha() + 2 * k - 1);
	}
	
	private double secondConst(double k, double tau) {
		return ((this.getAlpha() + k - 1) * (k - 1) * (this.getAlpha() + 2 * k)) / (k * (this.getAlpha() + 2 * k - 2) * (this.getAlpha() + k));
	}
}
