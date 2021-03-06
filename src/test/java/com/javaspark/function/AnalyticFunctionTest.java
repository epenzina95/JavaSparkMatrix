package com.javaspark.function;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.Serializable;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockitoAnnotations.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;
import com.javaspark.helper.ModelComparator;
import com.javaspark.model.CalculationResponse;

public class AnalyticFunctionTest  extends  SharedJavaSparkContext implements Serializable {

	static JavaSparkFunction func;

	private static final String HADOOP_PROPERTY = "hadoop.home.dir";
	private static final String HADOOP_DIR = "src\\main\\resources\\winutils";
	Double[][] resArr = new Double[][] {{0.135, 0.216, 0.211},{0.018, 0.036, 0.051},{0.002, 0.005, 0.007}};
	
	@BeforeClass
	public static void init() {
		
		String hadoopDirAbsPath = new File(HADOOP_DIR).getAbsolutePath();
		System.setProperty(HADOOP_PROPERTY, hadoopDirAbsPath);
		
		func = mock(AnalyticFunction.class);
		when(func.getAlpha()).thenReturn(1.0);
		when(func.getGamma()).thenReturn(1.0);
	}

	@Test
	public void calcFunctionElementTest() throws Exception {
		double res = 0.007;
		int k = 2, tau = 3;
		when(func.calcFunctionElement(k, tau)).thenReturn(res);
		assertEquals(res, func.calcFunctionElement(k, tau), 0.001);
	}
	
	@Test
	public void calcFunctionTest() {
		CalculationResponse res = ModelComparator.generateResponse("resMatr", resArr, true);
		int k = 2, n = 3;
		when(func.calcFunction(jsc(), k, n)).thenReturn(res);
		assertTrue(ModelComparator.compareResponse(res, func.calcFunction(jsc(),k, n)));
	}
}
