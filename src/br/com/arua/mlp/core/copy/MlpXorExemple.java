package br.com.arua.mlp.core.copy;

import java.io.IOException;

import br.com.arua.mlp.core.copy.MultilayerPerceptron;
import br.com.arua.mlp.core.copy.MultilayerPerceptronTrainer;

public class MlpXorExemple {
	
	public static void main(String[] args) throws IOException {
		new MlpXorExemple(true, "mlp3");
	}
	
	public MlpXorExemple(boolean trains, String path) {
		System.out.println("BKP");
		try{
			MultilayerPerceptron mlp;
			if(trains){
				mlp = new MultilayerPerceptronTrainer(5,10000,0.2)
					.add(new double[]{0, 0}, new double[]{0})
					.add(new double[]{0, 1}, new double[]{1})
					.add(new double[]{1, 0}, new double[]{1})
					.add(new double[]{1, 1}, new double[]{0})
//					.add(new double[]{0,0,0}, new double[]{0, 1})
//					.add(new double[]{0,0,1}, new double[]{0, 1})
//					.add(new double[]{0,1,0}, new double[]{0, 1})
//					.add(new double[]{0,1,1}, new double[]{1, 0})
//					.add(new double[]{1,0,0}, new double[]{0, 1})
//					.add(new double[]{1,0,1}, new double[]{0, 1})
//					.add(new double[]{1,1,0}, new double[]{1, 0})
//					.add(new double[]{1,1,1}, new double[]{0, 1})					
					.trains();
				
				MultilayerPerceptron.save(path,mlp);
			}else{
				mlp = MultilayerPerceptron.load(path);
			}
			
			System.out.print("0 0 : "); print(mlp.ativate(new double[]{0, 0}));
			System.out.print("0 1 : "); print(mlp.ativate(new double[]{0, 1}));
			System.out.print("1 0 : "); print(mlp.ativate(new double[]{1, 0}));
			System.out.print("1 1 : "); print(mlp.ativate(new double[]{1, 1}));
			
//			System.out.print("0 0 0 : "); print(mlp.ativate(new double[]{0, 0, 0}));
//			System.out.print("0 0 1 : "); print(mlp.ativate(new double[]{0, 0, 1}));
//			System.out.print("0 1 0 : "); print(mlp.ativate(new double[]{0, 1, 0}));
//			System.out.print("0 1 1 : "); print(mlp.ativate(new double[]{0, 1, 1}));
//			System.out.print("1 0 0 : "); print(mlp.ativate(new double[]{1, 0, 0}));
//			System.out.print("1 0 1 : "); print(mlp.ativate(new double[]{1, 0, 1}));
//			System.out.print("1 1 0 : "); print(mlp.ativate(new double[]{1, 1, 0}));
//			System.out.print("1 1 1 : "); print(mlp.ativate(new double[]{1, 1, 1}));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void print(double[] d){
		for (double i : d) {
			System.out.print( (i < 0.5 ? 0 : 1)  + " ");
		}
		System.out.println("");
	}
}
