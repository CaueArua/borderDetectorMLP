package br.com.arua.mlp.core;

import java.io.Serializable;

import br.com.arua.utils.MatrizUtils;

public class MultilayerPerceptronLayer implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double[][]  W;
	private double[] 	B;
	
	private double[]	exit;
	
	
	public MultilayerPerceptronLayer(int inputLayer, int outPutLayer){
		W = MatrizUtils.rand(outPutLayer,inputLayer);
		B = MatrizUtils.rand(outPutLayer);
	}
		
	public double[] activation(double[] entry){
		// v = w * entry - b;
		double[] temp = MatrizUtils.multiply(W, entry);
		double[] v = MatrizUtils.minus(temp,B); 

		// y = 1./(1+exp(-v));
		temp = MatrizUtils.multiply(v, -1);
		temp = MatrizUtils.exp(temp);
		temp = MatrizUtils.plus(temp, 1);

		exit = MatrizUtils.division(1, temp);
		
		return exit;
	}
	
	public MultilayerPerceptronLayer setW(double[][] w){
		this.W = w;
		return this;
	}
	
	public double[][] getW(){
		return W;
	}
	
	public MultilayerPerceptronLayer setB(double[] b){
		this.B = b;
		return this;
	}
	
	public double[] getB(){
		return B;
	}
	
	public double[] getLastExit(){
		return exit;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
