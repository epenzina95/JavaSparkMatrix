package com.javaspark.function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.Serializable;

import org.junit.BeforeClass;
import org.junit.Test;

import com.holdenkarau.spark.testing.SharedJavaSparkContext;
import com.javaspark.helper.ModelComparator;

public class RecurrenceFunctionTest extends  SharedJavaSparkContext implements Serializable {

	static JavaSparkFunction func;
	
	private static final String HADOOP_PROPERTY = "hadoop.home.dir";
	private static final String HADOOP_DIR = "src\\main\\resources\\winutils";
	Double[][] resArr = new Double[][] {{1.0, 1.594, 2.088},{1.0, 1.945, 2.865},{1.0, 1.993, 2.970}};
	
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

	@Test
	public void calcFunctionTest() {
		CalculationResponse res = ModelComparator.generateResponse("resMatr", resArr, true);
		int k = 2, n = 3;
		when(func.calcFunction(jsc(), k, n)).thenReturn(res);
		assertTrue(ModelComparator.compareResponse(res, func.calcFunction(jsc(),k, n)));
	}
}
