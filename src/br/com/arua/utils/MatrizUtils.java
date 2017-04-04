package br.com.arua.utils;

import java.util.Random;

public class MatrizUtils {

	public static void show(int[][] matrixA) {
		
		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixA[0].length; j++){ 
				System.out.printf(" "+matrixA[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static double[][] division(double[][] matrixA, int scalar){
		
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = (double)(matrixA[i][j] / scalar);
			}
		}

		return matrixC;
	}
	
	public static double[] division(double[] matrixA, int scalar){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
			matrixC[i] = (double)(matrixA[i] / scalar);
		}
		return matrixC;
	}
	
	public static double[] division(int scalar,double[] matrixA){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
			matrixC[i] = (double)(scalar/matrixA[i]);
		}
		return matrixC;
	}
	
	
	public static double[][] division(int[][] matrixA, int scalar){
		
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = (double)(matrixA[i][j] / scalar);
			}
		}

		return matrixC;
	}
	
	public static double[][] rand(int x,int y){
		double[][] ret = new double[x][y];
		Random r = new Random();
		for(int i =0; i< x; i++){
			for(int j =0; j<y; j++){
				ret[i][j] = r.nextDouble();
			}
		}
		return ret;
	}
	
	public static double[] rand(int x){
		double[] ret = new double[x];
		Random r = new Random();
		for(int i =0; i< x; i++){
			ret[i] = r.nextDouble();
		}
		return ret;
	}
	
	public static double[] extract(double[][] matrix, int colum){
		double[] ret = new double[matrix.length];
		for(int x=0;x<matrix.length;x++){
			ret[x]=matrix[x][colum];
		}
		return ret;
	}
	
	public static double[][] exp(double[][] matrix){
		double[][] ret = new double[matrix.length][matrix[0].length];
		
		for(int i = 0; i< matrix.length; i++){
			for(int j =0; j<matrix[0].length; j++){
				ret[i][j]= Math.pow(Math.E, matrix[i][j]);
			}
		}
		return ret;
	}
	
	public static double[] exp(double[] matrix){
		double[] ret = new double[matrix.length];
		for(int i = 0; i< matrix.length; i++){
			ret[i]= Math.pow(Math.E, matrix[i]);
		}
		return ret;
	}
	
	public static int[][] plus(int[][] matrixA, int scalar){

		int[][] matrixC = new int[matrixA.length][matrixA[0].length];
		
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] =  (matrixA[i][j] + scalar);
			}
		}
		
		return matrixC;
	}
	
	
	public static double[][] plus(double[][] matrixA, double scalar){

		double[][] matrixC = null;
		matrixC = new double[matrixA.length][matrixA[0].length];
		
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] + scalar;
			}
		}
		
		return matrixC;
	}
	
	public static double[] plus(double[] matrixA, double scalar){
		double[] matrixC = null;
		matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
			matrixC[i] = matrixA[i] + scalar;
		}
		return matrixC;
	}
	
	
	public static double[][] division(double scalar, double[][] matrixA){
		
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = (double)(scalar/ matrixA[i][j]) ;
			}
		}

		return matrixC;
	}
	
	public static double[] multiply(double[] matrixA, double scalar){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
				matrixC[i] = matrixA[i] * scalar;
		}
		return matrixC;
	}
	
	public static double[] multiply(double scalar,double[] matrixA){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
				matrixC[i] = scalar * matrixA[i];
		}
		return matrixC;
	}
	
	public static double[][] multiply(double[] matrixA, double[][] matrixB){
		return multiply(convert(matrixA), matrixB);
	}
	
	public static double[][] multiply(double scalar, double[][] matrixA){
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = scalar * matrixA[i][j];
			}

		}
		return matrixC;
	}
	
	public static double[][] multiply(double[][] matrixA, double scalar){
		
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] * scalar;
			}

		}

		return matrixC;

	}
	
	
	public static double[][] convert(double[] matrix){
		double[][] matrixC = new double[matrix.length][1];
		for (int x = 0; x< matrix.length; x++) {
			matrixC[x][0] = matrix[x];
		}
		return matrixC;
	}
	
	public static double[] minus(double[] matrixA, double[] matrixB){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
			matrixC[i] = matrixA[i] - matrixB[i];
		}
		return matrixC;
	}
	
	public static double[] minus(double saclar, double[] matrixA){
		double[] matrixC = new double[matrixA.length];
		for(int i = 0; i < matrixA.length; i++){
			matrixC[i] = saclar - matrixA[i];
		}
		return matrixC;
	}
	
	public static double[][] minus(double[] matrixA, double[][] matrixB){
		return minus(convert(matrixA), matrixB);
	}
	
	public static double[][] minus(double[][] matrixA, double[][] matrixB){
		double[][] matrixC = null;
		if( matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length ){
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		matrixC = new double[matrixA.length][matrixA[0].length];
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] - matrixB[i][j];
			}
		}
		return matrixC;
	}
	
	
	public static double[][] transpose(double[][] matrixA){

		double[][] matrixC = new double[matrixA[0].length][matrixA.length];

		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[j][i] = matrixA[i][j] ;
			}
		}

		return matrixC;
	}
	
	public static double[][] minus(double scalar, double[][] matrixB){
		double[][] matrixC = null;
		matrixC = new double[matrixB.length][matrixB[0].length];
		for(int i = 0; i < matrixB.length; i++){
			for(int j = 0; j < matrixB[0].length; j++){
				matrixC[i][j] = scalar - matrixB[i][j];
			}
		}
		return matrixC;
	}
	
	
	public static double[] multiply(double[][] matrixA, double[] matrixB){
		return extract(multiply(matrixA, convert(matrixB)),0);
	}
	
	public static double[][] multiply(double[][] matrixA, double[][] matrixB){
		double[][] matrixC = null;
		
		if (matrixA[0].length != matrixB.length){
			throw new RuntimeException("Illegal matrix dimensions.");
		} 
		
		matrixC = new double[matrixA.length][matrixB[0].length];
		
		for (int i = 0; i < matrixC.length; i++){
			for (int j = 0; j < matrixC[0].length; j++){
				for (int k = 0; k < matrixA[0].length; k++){
					matrixC[i][j] += (matrixA[i][k] * matrixB[k][j]);
				}
			}
		}
		
		return matrixC;
	}
	
	public static double[][] multiplyItems(double[] matrixA, double[][] matrixB){
		return multiplyItems(convert(matrixA), matrixB);
	}
	
	public static double[] multiplyItems(double[] matrixA, double[] matrixB){
		double[] matrixC = new double[matrixA.length];
		for (int i = 0; i < matrixC.length; i++){
				matrixC[i] = matrixA[i] * matrixB[i];
		}
		return matrixC;
	}
	
	public static double[][] multiplyItems(double[][] matrixA, double[][] matrixB){
		double[][] matrixC = new double[matrixA.length][matrixA[0].length];
		
		for (int i = 0; i < matrixC.length; i++){
			for (int j = 0; j < matrixC[0].length; j++){
				matrixC[i][j] = matrixA[i][j] * matrixB[i][j];
			}
		}
		return matrixC;
	}


	public static double[][] plus(double[][] matrixA, double[][] matrixB){
	
		double[][] matrixC = null;
		
		if( matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length ){
			throw new RuntimeException("Illegal matrix dimensions.");
		}
		
		matrixC = new double[matrixA.length][matrixA[0].length];
		
		for(int i = 0; i < matrixA.length; i++){
			for(int j = 0; j < matrixA[0].length; j++){
				matrixC[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}
		
		return matrixC;
	}
	
	public static double[] sum(double[][] matrix){
		double[] matrixB = new double[matrix[0].length];
		
		for(int j = 0; j< matrix[0].length;j++){
			matrixB[j] = sum(extract(matrix, j));
		}
		return matrixB;
	}
	
	
	public static double sum(double[] matrix){
		double ret = 0;
		for(int i = 0; i< matrix.length;i++){
			ret += matrix[i];
		}
		return ret;
	}
	
	/**
	 * Creates an matrix from a string
	 * @param matrixString (line separator "\n"  - columns separator = " " exemple: "1 2\n 3 4" = new double{{1,2},{3,4}}
	 * @return double[][] matrix
	 */
	public static double[][] loadMatrix(String matrixString){
		
		String[] lines = matrixString.split("\n");
		
		double[][] matrix = new double[lines.length][]; 

		for(int i = 0; i< lines.length; i++){
			String[] temp = lines[i].split(" ");
			matrix[i] = new double[temp.length];
			
			for(int j = 0; j<temp.length; j++){
				matrix[i][j] = Double.parseDouble(temp[j]);
			}
		}
		return matrix;
	}
	
	public static String saveMatix(int[][] matrix){
		StringBuilder matrixString = new StringBuilder();
		
		for(int i = 0; i< matrix.length;i++){
			if(i>0){
				matrixString.append("\n");
			}
			
			for(int j=0;j<matrix[0].length; j++){
				if(j>0){
					matrixString.append(" ");
				}
				matrixString.append(matrix[i][j]);
			}
		}
		
		return matrixString.toString();
	}
	
	public static String saveMatix(double[] matrix){
		return saveMatix(convert(matrix));
	}
	
	public static String saveMatix(double[][] matrix){
		StringBuilder matrixString = new StringBuilder();
		
		for(int i = 0; i< matrix.length;i++){
			if(i>0){
				matrixString.append("\n");
			}
			
			for(int j=0;j<matrix[0].length; j++){
				if(j>0){
					matrixString.append(" ");
				}
				matrixString.append(matrix[i][j]);
			}
		}
		
		return matrixString.toString();
	}


	public static double[][] transpose(double[] extract) {
		return transpose(convert(extract));
	}
	
}
