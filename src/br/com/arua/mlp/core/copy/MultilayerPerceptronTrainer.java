package br.com.arua.mlp.core.copy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	private int N_HIDEM_LAYER;
	
	private Random r;
	
	public MultilayerPerceptronTrainer(int nHidem, int maxEpo, double minError){
		r = new Random();
		N_HIDEM_LAYER = nHidem;
	
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
		
		MLP = new MultilayerPerceptron(ENTRY.get(0).length, N_HIDEM_LAYER, EXIT.get(0).length);
				
		double[][] 	dw1a = 	new double[MLP.getW1().length][MLP.getW1()[0].length];
		double[] 	db1a = 	new double[MLP.getB1().length];
		
		double[][]	dw2a = 	new double[MLP.getW2().length][MLP.getW2()[0].length];
		double[] 	db2a = 	new double[MLP.getB2().length];
		
		double[][] e = new double[ENTRY.size()][EXIT.size()];
		
		double lastError = 0;
		
		for(int era = 0; era< N_MAX_EPOCH; era++){
			for(int x=0; x<ENTRY.size(); x++){
				double[] template = ENTRY.get(x);
				double[] exit = MLP.ativate(template);
				
				// Error
				double[] error = MatrizUtils.minus(EXIT.get(x) , exit);
				for(int i = 0; i< error.length; i++){
		    		e[x][i]=error[i];
		    	}
				
				// gradients
				double[] gs = getGradient(exit,error);
				double[] g1 = getGradient(MLP.getW2(),MLP.getLastY1(),gs);
				
				// Deltas
				double[][] 	dw1 = getWDelta(ENTRY.get(x),g1);
				double[] 	db1 = MatrizUtils.multiply(g1,ETA);	
				
				double[][] 	dw2 = getWDelta(MLP.getLastY1(),gs);
				double[]	db2 = MatrizUtils.multiply(gs,ETA);
				
				double[][] w1 = getNewW(MLP.getW1(), dw1, dw1a); // MatrizUtils.plus( MatrizUtils.plus(MLP.getW1(), dw1), MatrizUtils.multiply(dw1a, ALFA) ) ;
		    	double[]   b1 = getNewb(MLP.getB1(), db1, db1a);// MatrizUtils.minus(MatrizUtils.minus(MLP.getB1(), db1), MatrizUtils.multiply(db1a, ALFA))	;
		    			    	
		    	double[][] w2 = getNewW(MLP.getW2(), dw2, dw2a); // MatrizUtils.plus( MatrizUtils.plus(MLP.getW2(), dw2), MatrizUtils.multiply(dw2a, ALFA) );
		    	double[]   b2 = getNewb(MLP.getB2(), db2, db2a);// MatrizUtils.minus(MatrizUtils.minus(MLP.getB2(), db2), MatrizUtils.multiply(db2a, ALFA));
				
		    	MLP.setW1(w1);
		    	MLP.setW2(w2);
		    	MLP.setB1(b1);
		    	MLP.setB2(b2);
		    	
		    	dw1a = dw1;
		        db1a = db1;
		        dw2a = dw2;
		        db2a = db2;
			}
			
			double epError = MatrizUtils.sum(MatrizUtils.sum(MatrizUtils.multiplyItems(e, e)));
			
			if(era%10000 == 0){
				System.out.println("estou na "+era+" epoca com um erro de "+epError);
			}
			 
			
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
			
			if(epError <= ERROR_TOLERANCE || era == N_MAX_EPOCH){
				System.out.println("Treinei em: " + era + " epocas.");
				break;
			}
			
			lastError = epError;
		}
		return MLP;
		
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
	
	private double[][] getWDelta(double[] yx, double[] g){
		double[][] transpose = MatrizUtils.transpose(yx);
		double[] temp = MatrizUtils.multiply(ETA,g);
		return MatrizUtils.multiply(temp,transpose);
	}
	
	private double[][] getNewW(double[][] w, double[][] dw, double[][] dwa){
		double[][] temp  = MatrizUtils.plus(w, dw);
		double[][] temp2 = MatrizUtils.multiply(ALPHA, dwa);
		return MatrizUtils.plus(temp, temp2);
	}
	
	private double[] getNewb(double[] b, double[] db, double[] dba){
		double[] temp  = MatrizUtils.minus(b, db);
		double[] temp2 = MatrizUtils.multiply(ALPHA, dba);
		return MatrizUtils.minus(temp, temp2);
	}
	
	
}
