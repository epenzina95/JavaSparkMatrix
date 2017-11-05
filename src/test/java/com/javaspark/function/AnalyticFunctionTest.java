package com.javaspark.function;

import static org.mockito.Mockito.mock;

import java.io.Serializable;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;

public class AnalyticFunctionTest  extends  SharedJavaSparkContext implements Serializable {

	static JavaSparkFunction func;
	
	@Mock
	static Double alpha;
	@Mock
	static Double gamma;
	//Double[][] resArr = new Double[][] {{0.135, 0.216, 0.211},{0.018, 0.036, 0.051},{0.002, 0.005, 0.007}};
	
	@BeforeClass
	public static void init() {
		func = mock(AnalyticFunction.class);
		when(func.getAlpha()).thenReturn(1.0);
		when(func.getGamma()).thenReturn(1.0);
		/*alpha = 1.0;
		gamma = 1.0;
		func = new AnalyticFunction(alpha, gamma);*/
	}

	@Test
	public void calcFunctionElementTest() throws Exception {
		//func = new AnalyticFunction(1.0, 1.0);
		double res = 0.007;
		int k = 2, tau = 3;
		when(func.calcFunctionElement(k, tau)).thenReturn(res);
		assertEquals(res, func.calcFunctionElement(k, tau), 0.001);
	}
}
