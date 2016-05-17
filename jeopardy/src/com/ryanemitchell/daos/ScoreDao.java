package com.ryanemitchell.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ryanemitchell.entities.Score;
import com.ryanemitchell.exception.ServerException;

public class ScoreDao {
	
	private EntityDao entityDao = null;
	private static ScoreDao scoreDao;
	
	private ScoreDao() throws ServerException{
		entityDao = EntityDao.getInstance();
	}
	
	/**
	 * This doesn't really need to use the singleton pattern, as it is, because
	 * there's no cache in place, but the pattern is used for consistency
	 * with the other daos here
	 * @return
	 * @throws ServerException 
	 */
	public static ScoreDao getInstance() throws ServerException {
		if(scoreDao == null) {
			scoreDao = new ScoreDao();
		}
		return scoreDao;
	}
	
	public Score findById(int id) throws ServerException {
		String queryStr = "SELECT * FROM scores WHERE id = ? LIMIT 1";
		PreparedStatement statement = entityDao.getPreparedStatement(queryStr);
		try {
			statement.setInt(1, id);
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		
		ResultSet scoreResult = entityDao.get(statement);
		try {
			if(scoreResult.next()) {
				return scoreFromResult(scoreResult);
			}
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		//No results found
		return null;
	}
	
	public Map<Integer, List<Score>> getScoreMap() throws ServerException {
		
		Map<Integer, List<Score>> scoreMap = new HashMap<Integer, List<Score>>();
		//Very long query. Returns a list of all scores, along with the number of games that player has won, 
		//since 2003-07-20
		String query = "SELECT * FROM  (SELECT * FROM (SELECT * FROM scores WHERE final > 0 ORDER BY final DESC) as a GROUP BY gameId ORDER BY final DESC) as winningScores JOIN (SELECT playerId, COUNT(*) as wins FROM (SELECT * FROM (SELECT scores.* FROM scores JOIN games ON scores.gameId = games.id WHERE scores.final > 0 AND STR_TO_DATE(games.date, \"%M %e, %Y\") > DATE(\"2003-07-20\") ORDER BY final DESC) AS a GROUP BY gameId ORDER BY final DESC) as b GROUP BY playerId) as numberOfWins  ON winningScores.playerId = numberOfWins.playerId;";
		ResultSet scoreSet = entityDao.get(query);
		try {
			while(scoreSet.next()) {
				int wins = scoreSet.getInt("wins");
				//For wins over 10, put it in the "10" category
				if(wins > 10) {
					wins = 10;
				}
				if(scoreMap.get(wins) == null) {
					scoreMap.put(wins, new ArrayList<Score>());
				}
				//Add the score to the appropriate array
				scoreMap.get(wins).add(scoreFromResult(scoreSet));
			}
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		return scoreMap;
	}
	
	private Score scoreFromResult(ResultSet result) throws SQLException, ServerException {
		Score score = new Score();
		score.setId(result.getInt("id"));
		
		GameDao gameDao = GameDao.getInstance();
		PlayerDao playerDao = PlayerDao.getInstance();
		score.setPlayer(playerDao.findById(result.getInt("playerId")));
		score.setGame(gameDao.findById(result.getInt("gameId")));
		
		score.setBreakScore(result.getInt("breakScore"));
		score.setRound1(result.getInt("round1"));
		score.setRound2(result.getInt("round2"));
		score.setFinalScore(result.getInt("final"));
		score.setCoryat(result.getInt("coryat"));
		return score;
	}

}
