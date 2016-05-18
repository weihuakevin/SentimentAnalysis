package org.svm;

import libsvm.*;

import java.io.*;
import java.util.StringTokenizer;

import utils.LabelProbability;


class Predict {
	private static Double accuracy;
	private static svm_model model;

	public void LoadSvmModel(String ModelPath){
		try {
			model = svm.svm_load_model(ModelPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}
	
	public static double ArrMax(double[] input){
		if(input.length <= 1){
			return 0.0;
		}
		double arrmax = input[0];
		for(int i=1;i<input.length;i++){
			if(input[i] > input[i-1]){
				arrmax = input[i];
			}
		}
		return arrmax;
	}

	private static LabelProbability predict(BufferedReader input, int predict_probability) throws IOException
	{
		LabelProbability reback = new LabelProbability();
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type=svm.svm_get_svm_type(model);
		int nr_class=svm.svm_get_nr_class(model);
		double[] prob_estimates=null;

		if(predict_probability == 1)
		{
			if(svm_type == svm_parameter.EPSILON_SVR ||
					svm_type == svm_parameter.NU_SVR)
			{
				System.out.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="+svm.svm_get_svr_probability(model)+"\n");
			}
			else
			{
				int[] labels=new int[nr_class];
				svm.svm_get_labels(model,labels);
				prob_estimates = new double[nr_class];
				for(int j=0;j<nr_class;j++){
//					System.out.print(" "+labels[j]);
				}
			}
		}
		while(true)
		{
			String line = input.readLine();
			if(line == null) break;
			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}

			double v;//V就是SVM预测的label
			if (predict_probability==1 && (svm_type==svm_parameter.C_SVC || svm_type==svm_parameter.NU_SVC))
			{
				v = svm.svm_predict_probability(model,x,prob_estimates);
				//预测概率的地方就在这！！！ 
				for(int j=0;j<nr_class;j++){
					//					output.writeBytes(prob_estimates[j]+" ");
//					System.out.print(prob_estimates[j]+" ");
				}
//				System.out.println();
			}
			else
			{
				v = svm.svm_predict(model,x);
			}
			
			if(v == target)
				++correct;
			
			double probability = ArrMax(prob_estimates);
			reback.setLabel(String.valueOf(v));
			reback.setProbability(probability);
			
			error += (v-target)*(v-target);
			sumv += v;
			sumy += target;
			sumvv += v*v;
			sumyy += target*target;
			sumvy += v*target;
			++total;
		}
		if(svm_type == svm_parameter.EPSILON_SVR ||
				svm_type == svm_parameter.NU_SVR)
		{
			System.out.print("Mean squared error = "+error/total+" (regression)\n");
			System.out.print("Squared correlation coefficient = "+
					((total*sumvy-sumv*sumy)*(total*sumvy-sumv*sumy))/
					((total*sumvv-sumv*sumv)*(total*sumyy-sumy*sumy))+
					" (regression)\n");
		}
		else
		{
//			System.out.print("Accuracy = "+(double)correct/total*100+"% ("+correct+"/"+total+") (classification)\n");
			accuracy = (double)correct/total;
		}
		return reback;
	}

	private static void exit_with_help()
	{
		System.err.print("usage: svm_predict [options] test_file model_file output_file\n"
				+"options:\n"
				+"-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n");
		System.exit(1);
	}

	public static LabelProbability LabelProbabilityPredict(String argv[]) throws IOException{

		LabelProbability reback = new LabelProbability();
		int i, predict_probability=0;
		for(i=0;i<argv.length;i++)
		{
			if(argv[i].charAt(0) != '-') break;
			++i;
			switch(argv[i-1].charAt(1))
			{
			case 'b':
				predict_probability = atoi(argv[i]);
				//					System.out.println(argv[i]);
				break;
			default:
				System.err.print("Unknown option: " + argv[i-1] + "\n");
				exit_with_help();
			}
		}
		if(i>=argv.length-1)
			exit_with_help();
		try 
		{
			BufferedReader input = new BufferedReader(new FileReader(argv[i]));
			//			svm_model model = svm.svm_load_model(argv[i+1]);
			//初始化的时候先load进来model
			if(predict_probability == 1)
			{
				if(svm.svm_check_probability_model(model)==0)
				{
					System.err.print("Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			}
			else
			{
				if(svm.svm_check_probability_model(model)!=0)
				{
					System.out.print("Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			reback = predict(input,predict_probability);
			input.close();
		} 
		catch(FileNotFoundException e) 
		{
			exit_with_help();
		}
		catch(ArrayIndexOutOfBoundsException e) 
		{
			exit_with_help();
		}
		return reback;
	}

}
