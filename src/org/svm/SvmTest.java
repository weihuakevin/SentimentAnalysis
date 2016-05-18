package org.svm;

import java.io.IOException;


public class SvmTest {

	public static void main(String[] args) throws IOException{
		long starTime=System.currentTimeMillis();
		
		String[] arg={"-b","1","-t","2","-c","32","trainfile/train","trainfile/model.txt"};
//		String[] arg={"-t","2","-c","32","-v","5","trainfile/train","trainfile/model.txt"};
		String[] parg={"trainfile/test","trainfile/model.txt","trainfile/out.txt"};
		System.out.println("-------SVM训练开始-------");
		svm_train.main(arg);
		svm_predict.main(parg);
		
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("程序运行时间：  "+Time);
	}
}
