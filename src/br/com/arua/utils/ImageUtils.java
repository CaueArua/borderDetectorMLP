package br.com.arua.utils;

import marvin.image.MarvinImage;

public class ImageUtils {
	public static double[][] rgb2gray(MarvinImage image){
		double[][] grayArray = new double[image.getHeight()][image.getWidth()];
		
		for(int x =0; x<image.getHeight() ;x++){
			for(int y =0; y<image.getWidth() ;y++){
			
				double rComp = 0.2126f * image.getIntComponent0(y, x);
				double gComp = 0.7152f * image.getIntComponent1(y, x);
				double bComp = 0.0722f * image.getIntComponent2(y, x);
				
				//int rComp = Math.round(0.2126f * image.getIntComponent0(y, x));
				//int gComp = Math.round(0.7152f * image.getIntComponent1(y, x));
				//int bComp = Math.round(0.0722f * image.getIntComponent2(y, x));
				
				grayArray[x][y] = rComp + gComp + bComp;				
			}
		}
		
		return grayArray;
	}
	
	
	public static double[][] getRedLayer(MarvinImage image){
		double[][] array = new double[image.getHeight()][image.getWidth()];
		
		for(int x =0; x<image.getHeight() ;x++){
			for(int y =0; y<image.getWidth() ;y++){
				array[x][y] = image.getIntComponent0(y, x);				
			}
		}
		
		return array;
	}
	
	public static double[][] getGeenLayer(MarvinImage image){
		double[][] array = new double[image.getHeight()][image.getWidth()];
		
		for(int x =0; x<image.getHeight() ;x++){
			for(int y =0; y<image.getWidth() ;y++){
				array[x][y] = image.getIntComponent1(y, x);				
			}
		}
		
		return array;
	}
	
	public static double[][] getBlueLayer(MarvinImage image){
		double[][] array = new double[image.getHeight()][image.getWidth()];
		
		for(int x =0; x<image.getHeight() ;x++){
			for(int y =0; y<image.getWidth() ;y++){
				array[x][y] = image.getIntComponent2(y, x);				
			}
		}
		
		return array;
	}
	
}
