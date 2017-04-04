package br.com.arua.mlp.exemple;

import java.io.IOException;

import javax.print.attribute.standard.OrientationRequested;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RefineryUtilities;

import br.com.arua.mlp.core.MultilayerPerceptron;
import br.com.arua.mlp.core.MultilayerPerceptronTrainer;
import br.com.arua.utils.XYGrapy;

public class MlpExemples {
	
	public static void main(String[] args) throws IOException {
		try{
			//xorExemple();
			twoClassExemple();
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
		
	public static void xorExemple() {
		System.out.println("Normal");
			MultilayerPerceptron mlp;
			MultilayerPerceptronTrainer mlpt;
			
			
			mlpt = new MultilayerPerceptronTrainer(0.1, 2)
				.add(new double[]{0, 0}, new double[]{1})
				.add(new double[]{0, 1}, new double[]{0})
				.add(new double[]{1, 0}, new double[]{0})
				.add(new double[]{1, 1}, new double[]{1});
			
			mlp = mlpt.trains();
			
			crateGraphErrors(mlpt);
			
			System.out.print("0 0 : "); print(mlp.ativate(new double[]{0, 0}));
			System.out.print("0 1 : "); print(mlp.ativate(new double[]{0, 1}));
			System.out.print("1 0 : "); print(mlp.ativate(new double[]{1, 0}));
			System.out.print("1 1 : "); print(mlp.ativate(new double[]{1, 1}));
		
	}
	
	private static void twoClassExemple() {
		MultilayerPerceptron mlp;
		MultilayerPerceptronTrainer mlpt; 
		
		mlpt = new MultilayerPerceptronTrainer(0.1, 2)
			.add(new double[]{0,0,0}, new double[]{0, 1})
			.add(new double[]{0,0,1}, new double[]{0, 1})
			.add(new double[]{0,1,0}, new double[]{0, 1})
			.add(new double[]{0,1,1}, new double[]{1, 0})
			.add(new double[]{1,0,0}, new double[]{0, 1})
			.add(new double[]{1,0,1}, new double[]{0, 1})
			.add(new double[]{1,1,0}, new double[]{1, 0})
			.add(new double[]{1,1,1}, new double[]{0, 1});
			
			mlp = mlpt.trains();
			
			crateGraphErrors(mlpt);
			
		System.out.print("0 0 0 : "); print(mlp.ativate(new double[]{0, 0, 0}));
		System.out.print("0 0 1 : "); print(mlp.ativate(new double[]{0, 0, 1}));
		System.out.print("0 1 0 : "); print(mlp.ativate(new double[]{0, 1, 0}));
		System.out.print("0 1 1 : "); print(mlp.ativate(new double[]{0, 1, 1}));
		System.out.print("1 0 0 : "); print(mlp.ativate(new double[]{1, 0, 0}));
		System.out.print("1 0 1 : "); print(mlp.ativate(new double[]{1, 0, 1}));
		System.out.print("1 1 0 : "); print(mlp.ativate(new double[]{1, 1, 0}));
		System.out.print("1 1 1 : "); print(mlp.ativate(new double[]{1, 1, 1}));
			
	}
	
	private static void print(double[] d){
		for (double i : d) {
			System.out.print( i + "\t["+ (i < 0.5 ? 0 : 1)  + "]\t");
		}
		System.out.println();
	}
	
	private static void crateGraphErrors(MultilayerPerceptronTrainer mlp){
		XYSeries series = new XYSeries("Error Data");
		mlp.getErrorMap().forEach((key, value) -> series.add(key, value));
		
		XYGrapy demo = new XYGrapy("Error Data", "Epoca", "Erro ²", PlotOrientation.VERTICAL, series);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
	
}
