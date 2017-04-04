package br.com.arua.mlp.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jfree.ui.RefineryUtilities;

import br.com.arua.mlp.exemple.XYSeriesDemo;
import br.com.arua.utils.MatrizUtils;

public class MultilayerPerceptronTrainer {

	private MultilayerPerceptron MLP;
	
	private int 	N_MAX_EPOCH;
	
	private double 	ETA;
	private double  MIN_ETA;
	
	private double 	ALPHA;
	private double  MIN_ALPHA;
	
	private double 	ERROR_TOLERANCE;
	
	private List<double[]> ENTRY;
	private List<double[]> EXIT;
	
	private int[] N_HIDEM_LAYERS;
	
	private Random r;
	
	private Map<Integer,Double> erros;
	
	public MultilayerPerceptronTrainer(double minError, int... nHidem){
		this(-1,minError,nHidem);
	}	
	public MultilayerPerceptronTrainer(int maxEpo, double minError, int... nHidem){
		erros = new HashMap<>();
		r = new Random();
		N_HIDEM_LAYERS = nHidem;
	
		N_MAX_EPOCH = maxEpo;
		ETA 	= r.nextDouble();
		MIN_ETA = 10e-5;
		
		ALPHA	= r.nextDouble();
		MIN_ALPHA = 10e-5;
		
		ERROR_TOLERANCE = minError;
		
		ENTRY = new ArrayList<double[]>();
		EXIT  = new ArrayList<double[]>();
	}	
	
	
	public MultilayerPerceptronTrainer setMinEta(double minEta){
		this.MIN_ETA = minEta;
		return this;
	}
	
	public MultilayerPerceptronTrainer setMinAlpha(double minAlpha){
		this.MIN_ALPHA = minAlpha;
		return this;
	}
	
	public MultilayerPerceptronTrainer add(double[] entry, double[] exit){
		
		if(!ENTRY.isEmpty()){
			if(entry.length != ENTRY.get(0).length){ throw new RuntimeException("Illegal entry dimensions.");}
			if(exit.length  != EXIT.get(0).length ){ throw new RuntimeException("Illegal exit  dimensions.");}
		}
		
		ENTRY.add(entry);
		EXIT.add(exit);
		return this;
	}
	
	public MultilayerPerceptron trains(){
		if(ENTRY.isEmpty()){ throw new RuntimeException("MLP MUST CONTAIN DATA FOR TRAINING");}
		
		MLP = new MultilayerPerceptron(ENTRY.get(0).length, N_HIDEM_LAYERS, EXIT.get(0).length);
		
		List<MultilayerPerceptronLayerTrainer> layers = new ArrayList<MultilayerPerceptronLayerTrainer>();
		
		layers.add(new MultilayerPerceptronLayerTrainer(ENTRY.get(0).length, N_HIDEM_LAYERS[0]));
		for(int x =1 ;x<N_HIDEM_LAYERS.length;x++){
			layers.add(new MultilayerPerceptronLayerTrainer(N_HIDEM_LAYERS[x-1],N_HIDEM_LAYERS[x]));
		} 
		layers.add(new MultilayerPerceptronLayerTrainer(N_HIDEM_LAYERS[N_HIDEM_LAYERS.length-1], EXIT.get(0).length));
		
		double[][] e = new double[ENTRY.size()][EXIT.size()];
		
		double lastError = ERROR_TOLERANCE +1;
		double minError = lastError;
		int era;
		
		MultilayerPerceptron mlpClone = null;
		
		long lastReport = 0;
		int itt = 0;
		
		for(era = 0; lastError > ERROR_TOLERANCE  && (era< N_MAX_EPOCH || N_MAX_EPOCH <= 0) ; era++){
			for(int x=0; x<ENTRY.size(); x++){
				double[] template = ENTRY.get(x);
				double[] exit = MLP.ativate(template);
				
				// Error
				double[] error = MatrizUtils.minus(EXIT.get(x) , exit);
				for(int i = 0; i< error.length; i++){
		    		e[x][i]=error[i];
		    	}
				
				// gradients
				
				List<double[]> gradients = new ArrayList<double[]>();
				
				gradients.add(getGradient(exit,error));
				
				for(int i = 1; i< layers.size(); i++){
					gradients.add(0,getGradient(MLP.getW(layers.size() - i),MLP.getLastY(layers.size() -1 -i), gradients.get(0)));
				}
				
				// Deltas
				
				for(int i = 0; i< layers.size(); i++){
					double entry[] = i == 0 ? ENTRY.get(x) : MLP.getLastY(i-1) ;
					layers.get(i).UpdateLayer(MLP.getLayer(i), entry,	gradients.get(i), ALPHA, ETA);
				}
				
			}
			
			double epError = MatrizUtils.sum(MatrizUtils.sum(MatrizUtils.multiplyItems(e, e)));
			
			if(era > 1){
				ETA  *= epError <= lastError ? 1.05F :0.05F;
				ALPHA *= ETA;
			}
			
			if(ETA < MIN_ETA){
				ETA = r.nextDouble();
			}  
			  
			if(ALPHA < MIN_ALPHA){
				ALPHA = r.nextDouble();
			}
			if(era==0){
				minError = epError+1;
			}
			if(minError > epError){
				try{
					mlpClone = (MultilayerPerceptron) MLP.clone();
				}catch(CloneNotSupportedException exception){
					throw new RuntimeException(exception);
				}
				minError = epError;
			}
			
			this.erros.put(era, epError);			
			
			if(itt == 1000 || (System.currentTimeMillis() - lastReport > 1000 ) ){
				System.out.println("estou na "+era+" epoca com um erro de "+epError + " Menor erro: "+ minError);
				lastReport = System.currentTimeMillis();
				itt = -1;
			}
			itt++;
			
			lastError = epError;
		}
		
		System.out.println("Treinei em: " + era + " epocas. Erro quadratico =" + minError);
		return mlpClone;
		
	}
	
	
	private double[] getGradient(double[][]w, double[]y, double[] g){
		double[][]  transpose 	= MatrizUtils.transpose(w);
		double[] multiply = MatrizUtils.multiply(transpose, g);
		return getGradient(y,multiply);
		
	}
	
	private double[] getGradient(double[] y, double[] g){
		double[] minusY   = MatrizUtils.minus(1, y);
		double[] multiply = MatrizUtils.multiplyItems(y, minusY);
		return MatrizUtils.multiplyItems(multiply, g); 
	}
		
	public Map<Integer, Double> getErrorMap(){
		return Collections.unmodifiableMap(erros);
	}
}
