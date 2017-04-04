package br.com.arua.mlp.exemple;

import java.io.File;
import java.io.IOException;

import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import br.com.arua.mlp.core.MultilayerPerceptron;
import br.com.arua.mlp.core.MultilayerPerceptronTrainer;
import br.com.arua.utils.ImageUtils;
import br.com.arua.utils.MatrizUtils;

public class BorderDetector {
	public static void main(String[] args) {
		new BorderDetector(false,2);
	
		//Integer g = Integer.parseInt("aaa");
		
		
	}
	
	public BorderDetector(Boolean trains, int nImages){
		try{
			int nPad = 28;
			
			//int[][] networks = new int[][]{{1,255},{6,85},{15,51},{120,17},{153,15}};
			int[][] networks = new int[][]{{15,51},{153,15}};
			//int[][] networks = new int[][]{{120,17}};
			
			
			if(trains){
				
					File file = new File("redes");
					if (!file.exists()) {
						if (file.mkdir()) {
							System.out.println("Directory is created!");
						} else {
							System.out.println("Failed to create directory!");
						}
					}
					
					System.out.print("-------Inicio-------\n\n\n");
					
					int x = 0;
					for (int[] network : networks) {
						System.out.print("------Rodada " + (x+1) + " (" + network[0] +" Padrão por classe)-------\n\n");
						trains(nPad, network[1]).save("redes/"+network[0]+".mlp");
						x++;
					}
			}
			
			for (int x =0;x<networks.length; x++){
				int[] network = networks[x];
				
				System.out.println("------Rodada " + (x+1) + " (" + network[0] +" Padrão por classe)-------");
				
				File file = new File("Imgfim/"+network[0]+"/");
				if (!file.exists()) {
					if (file.mkdir()) {
						System.out.println("Directory is created!");
					} else {
						System.out.println("Failed to create directory!");
					}
				}
				
				
				MultilayerPerceptron mlp = MultilayerPerceptron.load("redes/"+network[0]+".mlp");
			
				for(int i =1;i<= nImages; i++){
					System.out.print("----Executando imagem "+ i + " de "+ nImages+ "...");
					
					MarvinImage img = execute("imgs/"+i+".png", mlp);
					System.out.print("SALVANDO...");
					MarvinImageIO.saveImage(img, "Imgfim/"+network[0]+"/"+i+".jpg");
					System.out.println("OK");
				}
			
			
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	
	private MultilayerPerceptron trains(int nPad, int variation){
		MultilayerPerceptronTrainer trainer = new MultilayerPerceptronTrainer(0.02, 10);
		
		double[][] white = MatrizUtils.plus(new double[3][3], 255);
		
		for(int x = 1; x<=nPad;x++ ){
			MarvinImage pad = MarvinImageIO.loadImage("padroes/pad"+x+".bmp");
			double[][] img = ImageUtils.rgb2gray(pad);
			
			double[][] exit = img;
			if(x >26){
				exit = white;
			}
			
			addPad(img, variation, exit, trainer);
		}
		
		MultilayerPerceptron trains = trainer.trains();
		return trains;
	}
	
	private void addPad(double[][] img, int variation, double[][] out, MultilayerPerceptronTrainer trainer){
		double[][] tempImg = new double[img.length][img[0].length];
		double[] exit = convertMatrix(out);
		for(int black = 0; black < 255; black+=variation){
			for(int white = 255; white>black; white -= variation){
				for(int i =0; i< 3; i++){
					for(int j = 0; j< 3; j++){
						tempImg[i][j] = (img[i][j] == 0 ? black : white); 
					}
				}
				double[] entry = convertMatrix(tempImg);
				trainer.add(entry, exit);
			}
		}
	}
	
	private double[] convertMatrix(double[][] matrix){
		double[][] temp = MatrizUtils.division(matrix, 255);
		double[] out = new double[9];
		
		out[0] = temp[0][0];
		out[1] = temp[0][1];
		out[2] = temp[0][2];
		out[3] = temp[1][0];
		out[4] = temp[1][1];
		out[5] = temp[1][2];
		out[6] = temp[2][0];
		out[7] = temp[2][1];
		out[8] = temp[2][2];
		
		return out;
	}
	
	private double[][] convertMatrix(double[] matrix){
		double[][] out = new double[3][3];
		double[] temp = new double[matrix.length];
		for(int x =0;x<temp.length;x++) {
			temp[x] = matrix[x] > 0.5 ? 255 : 0;
		}
		
		out[0][0] = temp[0];
		out[0][1] = temp[1];
		out[0][2] = temp[2];
		out[1][0] = temp[3];
		out[1][1] = temp[4];
		out[1][2] = temp[5];
		out[2][0] = temp[6];
		out[2][1] = temp[7];
		out[2][2] = temp[8];
		
		return out;
	}
		
	private MarvinImage execute(String path, MultilayerPerceptron mlp){
		MarvinImage img = MarvinImageIO.loadImage(path);
		double[][] imgMatrix = ImageUtils.rgb2gray(img);
		int[][] retImage = new int[imgMatrix.length][imgMatrix[0].length];
		
		retImage = MatrizUtils.plus(retImage, 255);
		
		for(int i =1; i<imgMatrix.length-1;i++){
			for(int j =1; j<imgMatrix[0].length-1;j++){
				double[][] vet = new double[3][3];
				
				vet[0][0]=imgMatrix[i-1][j-1];
				vet[0][1]=imgMatrix[i-1][j];
				vet[0][2]=imgMatrix[i-1][j+1];
				vet[1][0]=imgMatrix[i][j-1];
				vet[1][1]=imgMatrix[i][j];
				vet[1][2]=imgMatrix[i][j+1];
				vet[2][0]=imgMatrix[i+1][j-1];
				vet[2][1]=imgMatrix[i+1][j];
				vet[2][2]=imgMatrix[i+1][j+1];
			
				double[] entry = convertMatrix(vet);
				
				double[] exit = mlp.ativate(entry);
				
				double[][] convertMatrix = convertMatrix(exit);
				for(int x = -1; x< 2; x++){
					for(int y = -1; y< 2; y++){
						if(retImage[i+x][j+y] != 0){
							retImage[i+x][j+y] = (int) convertMatrix[1+x][1+y];
						}
					}	
				}
			}
		}
		
		MarvinImage ret = new MarvinImage(retImage[0].length, retImage.length);
		
		for(int i =0; i< imgMatrix.length; i++){
			for(int j =0; j< imgMatrix[0].length; j++){
				ret.setIntColor(j, i, retImage[i][j], retImage[i][j], retImage[i][j]);
			}
		}
		
		return ret;
	}
}
