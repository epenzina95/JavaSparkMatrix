package com.javaspark.util;

import java.util.concurrent.TimeUnit;

public class JavaSparkHelper {

	public static final String PARALLEL_CALC_MSG = "Параллельное вычисление матрицы";
	public static final String SEQUENTIAL_CALC_MSG = "Последовательное вычисление матрицы";
	public static final String INVERSE_TEST_MSG = "Тест нахождения обратной матрицы";
	public static final String TRANSPARENCY_TEST_MSG = "Тест транспонирования матрицы";
	public static final String MULTIPLY_TEST_MSG = "Тест умножения матрицы на транспонированную";
	
	
	public static String timeFormat(long millis) {
		return String.format("%02d мин, %02d сек, %02d мсек", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
			    millis - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis))
			);
	}
}
