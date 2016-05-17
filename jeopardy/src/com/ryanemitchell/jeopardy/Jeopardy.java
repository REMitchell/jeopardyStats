package com.ryanemitchell.jeopardy;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ryanemitchell.daos.ScoreDao;
import com.ryanemitchell.entities.Score;
import com.ryanemitchell.util.StatsUtil;

/**
 * This class is primarily responsible for retrieving the user input variables to produce
 * the Jeopardy T Test data, and returning a JSON array of test results
 * @author ryan
 *
 */
public class Jeopardy extends HttpServlet implements Servlet {

	private static final long serialVersionUID = -1195744060565249829L;
	private final String DEFAULT_TESTSCORE = "final";
    private final double DEFAULT_ALPHA = .05;
	
    public Jeopardy() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	double alpha = DEFAULT_ALPHA;
    	String testScore = DEFAULT_TESTSCORE;
    	
    	if(request.getParameter("alpha") != null) {
    		alpha = Double.valueOf(request.getParameter("alpha"));
    	}
    	
    	if(request.getParameter("testScore") != null) {
    		testScore = request.getParameter("testScore");
    	}
    	
    	
        ScoreDao scoreDao = ScoreDao.getInstance();
        Map<Integer, List<Score>> scoreMap = scoreDao.getScoreMap();
        Boolean[][] testResults = new Boolean[10][10];
        Integer[] nVals = new Integer[10];
        
        //Iterate through each set of scores, against each other
        for(int first = 1; first <= 10; first++) {
        	nVals[first-1] = scoreMap.get(first).size();
        	//Where the groups are the same, the null hypothesis is obviously accepted,
        	//and running the test doesn't really make sense
        	testResults[first-1][first-1] = false;
        	for(int second = first + 1; second <= 10; second ++) {
        		Boolean testResult = StatsUtil.tTest(scoreMap.get(first), scoreMap.get(second), alpha, testScore);
        		//This is symmetric along the diagonal
        		testResults[first-1][second-1] = testResult;
        		testResults[second-1][first-1] = testResult;
        	}
        }
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(testResults);
        String nValsStr = mapper.writeValueAsString(nVals);
        request.setAttribute("alpha", alpha);
        request.setAttribute("testScore", testScore);
        request.setAttribute("stats", result);
        request.setAttribute("nVals", nValsStr);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
