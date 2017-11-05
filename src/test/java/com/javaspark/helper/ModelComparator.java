package com.javaspark.helper;

import java.util.List;
import java.util.Map;

import com.javaspark.function.CalculationResponse;

public class ModelComparator {

	
	public static boolean compareMatrix(Double[][] a, Double[][] b) {
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
						Double[][] tmpA = ((Double[][])a.get(key));
						Double[][] tmpB = ((Double[][])b.get(key));
						for (int i = 0; i < tmpA.length; i++) {
							for (int j = 0; j < tmpA[i].length; j++)
								if (tmpA[i][j] != tmpB[i][j]) return false;
						}
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
