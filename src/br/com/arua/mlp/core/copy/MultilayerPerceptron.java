package br.com.arua.mlp.core.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import br.com.arua.utils.MatrizUtils;

public class MultilayerPerceptron implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int N_INPUT_LAYER;
	private final int N_EXIT_LAYER;
	private final int N_HIDEM_LAYER;
	
	private double[][] W1;
	private double[][] W2;
	
	private double[] B1;
	private double[] B2;
	private double[] y1;
		
	public MultilayerPerceptron(int nInput, int nHidem, int nExit){
		N_INPUT_LAYER 	= nInput;
		N_EXIT_LAYER	= nExit;
		N_HIDEM_LAYER	= nHidem;
		
		W1 = MatrizUtils.rand(N_HIDEM_LAYER,N_INPUT_LAYER);
		B1 = MatrizUtils.rand(N_HIDEM_LAYER);
		W2 = MatrizUtils.rand(N_EXIT_LAYER,N_HIDEM_LAYER);
		B2 = MatrizUtils.rand(N_EXIT_LAYER);
	}
	
	public MultilayerPerceptron(double[][] w1,double[][] w2,double[] b1,double[] b2){
		W1 = w1;
		B1 = b1;
		W2 = w2;
		B2 = b2;
		
		N_INPUT_LAYER 	= W1[0].length;
		N_EXIT_LAYER	= b2.length;
		N_HIDEM_LAYER	= b1.length;
	}
	
	public double[] ativate(double[] entry){
		y1 = layerActivation(W1,B1,entry);
		double[] y2 = layerActivation(W2,B2,y1);
		return y2;
	}
	
	public double[] getLastY1(){
		return y1;
	}
	
	private double[] layerActivation(double[][] w, double[] b, double[] entry){
		// v = w * entry - b;
		double[] temp = MatrizUtils.multiply(w, entry);
		double[] v = MatrizUtils.minus(temp,b); 

		// y = 1./(1+exp(-v));
		temp = MatrizUtils.multiply(v, -1);
		temp = MatrizUtils.exp(temp);
		temp = MatrizUtils.plus(temp, 1);

		double[] y = MatrizUtils.division(1, temp);
		
		return y;
	}
	
	public void setW1(double[][] w1){W1 = w1;}
	public double[][] getW1(){return W1;}
	
	public void setW2(double[][] w2){W2 = w2;}
	public double[][] getW2(){return W2;}
	
	public void setB1(double[] b1){B1 = b1;}
	public double[] getB1(){return B1;}
	
	public void setB2(double[] b2){B2 = b2;}
	public double[] getB2(){return B2;}
	
	public static  void save(String path,MultilayerPerceptron mlp ) throws IOException{
		 FileOutputStream fileOut = new FileOutputStream(path);
		 ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 out.writeObject(mlp);
		 out.close();
		 fileOut.close();
	};
	
	public static MultilayerPerceptron load(String path) throws ClassNotFoundException, IOException{
		MultilayerPerceptron mlp = null;
		FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        mlp = (MultilayerPerceptron) in.readObject();
        in.close();
        fileIn.close();
        return mlp;
	}
	
}
