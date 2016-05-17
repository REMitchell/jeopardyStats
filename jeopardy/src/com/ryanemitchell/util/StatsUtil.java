package com.ryanemitchell.util;

import java.util.List;
import org.apache.commons.math3.stat.inference.TTest;
import com.ryanemitchell.entities.Score;
import com.ryanemitchell.exception.ClientException;

public class StatsUtil {

	public static boolean tTest(List<Score> scoreObjs1, List<Score> scoreObjs2, Double alpha) {
		double[] scores1 = new double[scoreObjs1.size()];
		double[] scores2 = new double[scoreObjs2.size()];
 		for(int i = 0; i < scoreObjs1.size(); i++) {
 			scores1[i] = (double)scoreObjs1.get(i).getFinalScore();
 		}
 		for(int i = 0; i < scoreObjs2.size(); i++) {
 			scores2[i] = (double)scoreObjs2.get(i).getFinalScore();
 		}
 		TTest tester = new TTest();
 		return tester.homoscedasticTTest(scores1,scores2, alpha);
	}
	
	public static boolean tTest(List<Score> scoreObjs1, List<Score> scoreObjs2, Double alpha, String testScore) throws ClientException {
		double[] scores1 = new double[scoreObjs1.size()];
		double[] scores2 = new double[scoreObjs2.size()];
		
		if(testScore.equalsIgnoreCase("FINAL")) {
			for(int i = 0; i < scoreObjs1.size(); i++) {
	 			scores1[i] = (double)scoreObjs1.get(i).getFinalScore();
	 		}
	 		for(int i = 0; i < scoreObjs2.size(); i++) {
	 			scores2[i] = (double)scoreObjs2.get(i).getFinalScore();
	 		}
			
		} else if(testScore.equalsIgnoreCase("CORYAT")) {
			for(int i = 0; i < scoreObjs1.size(); i++) {
	 			scores1[i] = (double)scoreObjs1.get(i).getCoryat();
	 		}
	 		for(int i = 0; i < scoreObjs2.size(); i++) {
	 			scores2[i] = (double)scoreObjs2.get(i).getCoryat();
	 		}
		} else if(testScore.equalsIgnoreCase("BREAKSCORE")) {
			for(int i = 0; i < scoreObjs1.size(); i++) {
	 			scores1[i] = (double)scoreObjs1.get(i).getBreakScore();
	 		}
	 		for(int i = 0; i < scoreObjs2.size(); i++) {
	 			scores2[i] = (double)scoreObjs2.get(i).getBreakScore();
	 		}
		} else if(testScore.equalsIgnoreCase("ROUND1")) {
			for(int i = 0; i < scoreObjs1.size(); i++) {
	 			scores1[i] = (double)scoreObjs1.get(i).getRound1();
	 		}
	 		for(int i = 0; i < scoreObjs2.size(); i++) {
	 			scores2[i] = (double)scoreObjs2.get(i).getRound1();
	 		}
			
		} else if(testScore.equalsIgnoreCase("ROUND2")) {
			for(int i = 0; i < scoreObjs1.size(); i++) {
	 			scores1[i] = (double)scoreObjs1.get(i).getRound2();
	 		}
	 		for(int i = 0; i < scoreObjs2.size(); i++) {
	 			scores2[i] = (double)scoreObjs2.get(i).getRound2();
	 		}
		} else {
			throw new ClientException("Invalid score type: "+testScore);
		}
		
		TTest tester = new TTest();
 		return tester.homoscedasticTTest(scores1,scores2, alpha);
	}
	
}
