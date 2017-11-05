package com.javaspark.function;

import java.io.Serializable;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.holdenkarau.spark.testing.SharedJavaSparkContext;
import com.javaspark.helper.ModelComparator;

public class JavaSparkFunctionTest extends  SharedJavaSparkContext implements Serializable {

	JavaSparkFunction func;
	Double[][] resArr = new Double[][] {{0.135, 0.216, 0.211},{0.018, 0.036, 0.051},{0.002, 0.005, 0.007}};
	
	@Test
	public void toArrayTest() {
		func = mock(JavaSparkFunction.class);
		
		List<List<Double>> inA = new ArrayList<>();
		inA.add(Arrays.asList(new Double[] {0.135, 0.216, 0.211}));
		inA.add(Arrays.asList(new Double[] {0.018, 0.036, 0.051}));
		inA.add(Arrays.asList(new Double[] {0.002, 0.005, 0.007}));
		
		when(func.toArray(inA, false)).thenReturn(resArr);
		assertTrue(ModelComparator.compareMatrix(resArr, func.toArray(inA, false)));
		
		List<List<Double>> inB = new ArrayList<>();
		inB.add(Arrays.asList(new Double[] {0.135, 0.018, 0.002}));
		inB.add(Arrays.asList(new Double[] {0.216, 0.036, 0.005}));
		inB.add(Arrays.asList(new Double[] {0.211, 0.051, 0.007}));
		
		when(func.toArray(inB, true)).thenReturn(resArr);
		assertTrue(ModelComparator.compareMatrix(resArr, func.toArray(inB, true)));
	}
}
