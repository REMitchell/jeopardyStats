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
import com.ryanemitchell.daos.EntityDao;
import com.ryanemitchell.daos.GameDao;
import com.ryanemitchell.daos.ScoreDao;
import com.ryanemitchell.entities.Game;
import com.ryanemitchell.entities.Score;
import com.ryanemitchell.util.StatsUtil;

public class Jeopardy extends HttpServlet implements Servlet {
       
    public Jeopardy() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageTitle = "My Page Title";
        request.setAttribute("title", pageTitle);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
        ScoreDao scoreDao = ScoreDao.getInstance();
        Map<Integer, List<Score>> scoreMap = scoreDao.getScoreMap();
        boolean[][] testResults = new boolean[10][10];
        
        
        //Iterate through each set of scores, against each other
        for(int first = 1; first <= 10; first++) {
        	for(int second = first + 1; second <= 10; second ++) {
        		testResults[first][second] = StatsUtil.tTest(scoreMap.get(first), scoreMap.get(second), .95);
        	}
        }
        
        ObjectMapper mapper = new ObjectMapper();
        request.setAttribute("statsJson", mapper.writeValueAsString(testResults));
    }
}
