package br.com.arua.mlp.core;

import br.com.arua.utils.MatrizUtils;


public class MultilayerPerceptronLayerTrainer  {

	private double[][]  deltaW;
	private double[] 	deltaB;
	
	public MultilayerPerceptronLayerTrainer(int inputLayer, int outPutLayer) {
		deltaW = new double[outPutLayer][inputLayer];
		deltaB = new double[outPutLayer];
	}
	
	public void UpdateLayer(MultilayerPerceptronLayer layer,double[] entry, double[] gradient, double alpha,double eta){
		
		double[][] 	dw = getWDelta(entry,gradient,eta);
		double[] 	db = MatrizUtils.multiply(gradient,eta);	
		
		double[][] w = getNewW(layer.getW(), dw, alpha); // MatrizUtils.plus( MatrizUtils.plus(MLP.getW1(), dw1), MatrizUtils.multiply(dw1a, ALFA) ) ;
    	double[]   b = getNewb(layer.getB(), db, alpha); // MatrizUtils.minus(MatrizUtils.minus(MLP.getB1(), db1), MatrizUtils.multiply(db1a, ALFA))	;
		
		layer.setW(w);
		layer.setB(b);
		
		deltaW = dw;
		deltaB = db;
	}
	
	private double[][] getWDelta(double[] yx, double[] g,double eta){
		double[][] transpose = MatrizUtils.transpose(yx);
		double[] temp = MatrizUtils.multiply(eta,g);
		return MatrizUtils.multiply(temp,transpose);
	}
	
	private double[][] getNewW(double[][] w, double[][] dw, double alpha){
		double[][] temp  = MatrizUtils.plus(w, dw);
		double[][] temp2 = MatrizUtils.multiply(alpha, deltaW);
		return MatrizUtils.plus(temp, temp2);
	}
	
	private double[] getNewb(double[] b, double[] db, double alpha){
		double[] temp  = MatrizUtils.minus(b, db);
		double[] temp2 = MatrizUtils.multiply(alpha, deltaB);
		return MatrizUtils.minus(temp, temp2);
	}
	
	
	
	
	
}
