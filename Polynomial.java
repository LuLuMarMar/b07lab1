import java.util.Arrays;

public class Polynomial {
	public double[] coefficients;
	
	public Polynomial() {
		coefficients = new double[] {0};
	}
	
	public Polynomial(double[] array) {
		coefficients = array;
	}
	
	public Polynomial add(Polynomial poly) {
		int n = poly.coefficients.length;
		int m = coefficients.length; 
		
		if(n > m) {
			double[] arr = new double[n];
			Arrays.fill(arr, 0); 
			for(int i = 0; i < m; i++) {
				arr[i] = coefficients[i];
			}
			
			for(int i = 0; i<n; i++) {
				poly.coefficients[i] += arr[i];
			}
			
		} else if (m > n) {
			double[] arr = new double[m];
			Arrays.fill(arr, 0); 
			for(int i =0; i < n; i++) {
				arr[i] = poly.coefficients[i];
			}
			
			for(int i=0; i<m;i++) {
				arr[i] += coefficients[i];
			} 
			poly.coefficients = arr;
		} else {
			for(int i = 0; i < n; i++) {
				poly.coefficients[i] += coefficients[i];
			} 
		}
		return poly; 
	}
	
	public double evaluate(double x) {
		int n = coefficients.length;
		double result = 0;
		result += coefficients[0];
		
		for(int i = 1; i < n; i++) {
			result += coefficients[i]*x;
			x *= x;
		}
		
		return result;
	}
	
	public boolean hasRoot(double x) {
		if(evaluate(x) == 0.0) {
			return true;
		} else {
			return false;
		}
	}
}