package br.com.arua.mlp.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MultilayerPerceptron implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<MultilayerPerceptronLayer> layers;
	
	public MultilayerPerceptron(int nInput, int[] nHidem, int nExit){
		layers = new ArrayList<MultilayerPerceptronLayer>();
		layers.add(new MultilayerPerceptronLayer(nInput, nHidem[0]));
		for(int x =1 ;x<nHidem.length;x++){
			layers.add(new MultilayerPerceptronLayer(nHidem[x-1],nHidem[x]));
		} 
		layers.add(new MultilayerPerceptronLayer(nHidem[nHidem.length-1], nExit));
	}
	
	public double[] ativate(double[] entry){
		double[] exit = entry;
		for(int x = 0; x< layers.size() ;x++){
			exit = layers.get(x).activation(exit);
		}
		return exit;
	}
	
	public MultilayerPerceptronLayer getLayer(int x){
		return layers.get(x);
	}
	
	public double[] getLastY(int x){
		return layers.get(x).getLastExit();
	}
	
	public void setW(int x, double[][] w){ layers.get(x).setW(w);}
	public double[][] getW(int x){ return layers.get(x).getW();}
	
	public void setB(int x, double[] b)  { layers.get(x).setB(b);}
	public double[] getB(int x){ return layers.get(x).getB();}
	
	public void save(String path) throws IOException{
		this.save(path,this);
	}
	
	
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
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		MultilayerPerceptron clone =  (MultilayerPerceptron)super.clone();
		clone.layers = new ArrayList<MultilayerPerceptronLayer>();
		for (MultilayerPerceptronLayer layer : layers) {
			clone.layers.add((MultilayerPerceptronLayer)layer.clone());
		}
		return clone;
	}
}
