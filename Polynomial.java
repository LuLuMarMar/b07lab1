import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;

public class Polynomial {
	//part a)
	public double[] coefficients;
	public int[] power;
	
	//Constructors ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public Polynomial() {
		coefficients = new double[] {0};
		power = new int[] {0};
	}
	
	//part d)
	public Polynomial(File f) { 
		if(!f.exists()) {
			Scanner file = new Scanner(f);
			String line = file.nextLine();
			file.close();
			
			if(!line.contains("+")) {
				line = line.replaceAll("x(\\d)", "+$0");
			}
			
			String[] terms = line.split("\\+");
			
			ArrayList<Double> c = new ArrayList<>();
			ArrayList<Integer> e = new ArrayList<>();
			
			for(String term : terms) {
				if(term.matches("\\d+(\\.\\d+)?x\\d+")) {
					String[] nums = term.split("x");
					double coeff = Double.parseDouble(nums[0]);
					int expo = Integer.parseInt(nums[1]);
					c.add(coeff);
					e.add(expo);
				} else {
					double coeff = Double.parseDouble(term);
					c.add(coeff);
					e.add(0);
				}
			}
			
			double[] c_array = new double[c.size()];
			int[] e_array = new int[e.size()];
			
			for(int i = 0; i < c.size(); i++) {
				c_array[i] = c.get(i);
				e_array[i] = e.get(i);
			}
	} 
	
	public Polynomial(double[] coeff, int[] expo) {
		coefficients = coeff;
		power = expo;
	}
	
	//Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public int find_max(int[] array) {
		int max = 0;
		for(int i = 0; i < array.length; i++) {
			if(this.power[i] > max) {
				max = this.power[i];
			}
		}
		return max;
	}
	
	private boolean isZeroArray(double[] array) {
	    for (int i = 0; i < array.length; i++) {
	        if (array[i] != 0) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public Polynomial add(Polynomial poly) {
	    if (poly == null) {
	        return this;
	    } else if (isZeroArray(poly.coefficients)) {
	    	return this;
	    } else {
	        double[] p1_c = poly.coefficients;
	        int[] p1_e = poly.power;

	        double[] p2_c = coefficients;
	        int[] p2_e = power;

	        int p1_max = find_max(p1_e);
	        int p2_max = find_max(p2_e);

	        int max_length = Math.max(p1_max, p2_max);

	        int[] p3_e = new int[max_length + 1];
	        double[] p3_c = new double[max_length + 1];

	        // Case: Each entry in power does not match with the other polynomial
	        for (int i = 0; i < p1_e.length; i++) {
	            for (int j = 0; j < p2_e.length; j++) {
	                if (p1_e[i] == p2_e[j]) {
	                    p3_c[p1_e[i]] += p1_c[i] + p2_c[j];
	                    p3_e[p1_e[i]] = p1_e[i];
	                }
	            }
	        }

	        // Making the redundant 0s into a final array
	        int targetIndex_c = 0;
	        int targetIndex_e = 0;
	        for (int i = 0; i <= max_length; i++) {
	            if (p3_e[i] != 0) {
	                p3_e[targetIndex_e++] = p3_e[i];
	            }
	            if (p3_c[i] != 0) {
	                p3_c[targetIndex_c++] = p3_c[i];
	            }
	        }

	        double[] temp_c = new double[targetIndex_c];
	        int[] temp_e = new int[targetIndex_e];

	        System.arraycopy(p3_c, 0, temp_c, 0, targetIndex_c);
	        System.arraycopy(p3_e, 0, temp_e, 0, targetIndex_e);

	        poly.coefficients = temp_c;
	        poly.power = temp_e;

	        return poly;
	    }
	}
	
	public double evaluate(double x) {
		int n = coefficients.length;
		double result = 0;
		
		for(int i = 0; i < n; i++) {
			if(power[i] == 0) {
				result += coefficients[i];
			} else {
				double y = x;
				for(int j = 0; j < power[j]; j++) {
					y *= y;
				}
				y *= coefficients[i]; 
				result += y;
			}
		}
		return result;
	}
	
	//Does not need to be changed
	public boolean hasRoot(double x) {
		if(evaluate(x) == 0.0) {
			return true;
		} else {
			return false;
		}
	}
	
	//part c) 
	public Polynomial mulitply(Polynomial poly) {
	    if (poly == null) {
	        return this;
	    } else if (isZeroArray(poly.coefficients)) {
	        return poly;
	    } else {
	        double[] p1_c = this.coefficients;
	        int[] p1_e = this.power;

	        double[] p2_c = poly.coefficients;
	        int[] p2_e = poly.power;

	        int p1_max = find_max(p1_e);
	        int p2_max = find_max(p2_e);

	        int max_length = 1 + p1_max + p2_max;

	        double[] p3_c = new double[max_length];
	        Arrays.fill(p3_c, 0);
	        int[] p3_e = new int[max_length];
	        Arrays.fill(p3_e, 0);

	        for (int i = 0; i < p1_c.length; i++) {
	            for (int j = 0; j < p2_c.length; j++) {
	                int index = i * p2_c.length + j;
	                p3_c[index] = p1_c[i] * p2_c[j];
	                p3_e[index] = p1_e[i] + p2_e[j];
	            }
	        }

	        Polynomial p3 = new Polynomial(p3_c, p3_e);

	        return p3;
	    }
	}
	
	//part e) 
	// Coefficient then x then exponent then regex + or -
	public void saveToFile(String file_name) {
		FileWriter writer = new FileWriter(file_name);
		
		for(int i = 0; i < coefficients[i]; i++) {
			double c = coefficients[i]; 
			int e = power[i];
			
			if(i == 0) {
				writer.write(String.valueOf(c));
			} else {
				writer.write(c > 0 ? "+" + c : String.valueOf(c));
			} 
			
			if(e != 0) {
				writer.write("x");
			} 
		}
		writer.close();
	}
	
	
}