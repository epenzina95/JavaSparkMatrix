package com.javaspark.helper;

import java.util.List;
import java.util.Map;

import com.javaspark.model.CalculationResponse;

public class ModelComparator {

	
	public static CalculationResponse generateResponse(String func, Double[][] arr, boolean status) {
		CalculationResponse response = new CalculationResponse();
		
		response.setStatus(status);
		response.getData().put(func, arr);
		
		return response;
	}
	
	public static boolean compareMatrix(Object[][] a, Object[][] b) {
		if (a.length == b.length) {
			for (int i = 0; i < a.length; i++) {
				if (a[i].length == b[i].length) {
					for (int j = 0; j < a[i].length; j++) {
						if (a[i][j] != b[i][j]) return false;
					}
				} else return false;
			}
			return true;
		}else return false;
	}
	
	public static  boolean compareResponse(CalculationResponse a, CalculationResponse b) {
		if (a == b)
			return true;
		else {
			if (a.getStatus() && b.getStatus()) {
				if (!compareLists(a.getErrors(), b.getErrors())) return false;
				if (!compareMaps(a.getData(), b.getData())) return false;
				return true;
			}
			return false;
		}
	}
	
	public static  boolean compareMaps(Map<?, ?> a, Map<?, ?> b) {
		if (a != null && b != null) {
			if (a.size() == b.size()) {
				for (Object key : a.keySet()) {
					if (a.get(key) instanceof Double[][] && b.get(key) instanceof Double[][]) {
						return compareMatrix((Double[][])a.get(key), (Double[][])b.get(key));
					} else {
						if (a.get(key) instanceof String && b.get(key) instanceof String)
							if (!a.get(key).equals(b.get(key))) return false;
					}
				}
				return true;
			}
		}
		return (a == null && b == null);
	}
	
	public static  boolean compareLists (List<?> a, List<?> b) {
		if (a != null && b != null) {
			if (a.size() == b.size()) {
				for (int i = 0; i < a.size(); i++) {
					if (a.get(i) instanceof String && a.get(i) instanceof String)
						if (!a.get(i).equals(b.get(i))) return false;
				}
				return true;
			}
		}
		return (a == null && b == null);
	}
}
