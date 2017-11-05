package com.javaspark.function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.junit.BeforeClass;
import org.junit.Test;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;

public class RecurrenceFunctionTest extends  SharedJavaSparkContext implements Serializable {

	static JavaSparkFunction func;
	
	@BeforeClass
	public static void init() {
		func = mock(AnalyticFunction.class);
		when(func.getAlpha()).thenReturn(1.0);
		when(func.getGamma()).thenReturn(1.0);
	}

	@Test
	public void calcFunctionElementTest() throws Exception {

		double res = 1.0;
		int k = 0;
		int tau = 3;
		when(func.calcFunctionElement(k, tau)).thenReturn(res);
		assertEquals(res, func.calcFunctionElement(k, tau), 0.001);
		
		res = 1.993; 
		k = 1;
		when(func.calcFunctionElement(k, tau)).thenReturn(res);
		assertEquals(res, func.calcFunctionElement(k, tau), 0.001);
		
		res = 2.970;
		k = 2;
		when(func.calcFunctionElement(k, tau)).thenReturn(res);
		assertEquals(res, func.calcFunctionElement(k, tau), 0.001);
	}

}
