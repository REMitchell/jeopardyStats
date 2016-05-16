package com.ryanemitchell.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ryanemitchell.entities.Game;
import com.ryanemitchell.exception.ServerException;

public class GameDao {
	private EntityDao entityDao;
	private static GameDao gameDao;
	private Map<Integer, Game> gameCache;
	
	/**
	 * Private constructor for singleton pattern, initialize the entityDao
	 * and gameCache
	 */
	private GameDao() throws ServerException {
		entityDao = EntityDao.getInstance();
		gameCache = new HashMap<Integer, Game>();
	}
	
	public static GameDao getInstance() throws ServerException {
		if(gameDao == null) {
			gameDao = new GameDao();
		}
		return gameDao;
	}
	
	/**
	 * Returns a single game object from the database by unique ID
	 * @param id
	 * @return
	 * @throws ServerException
	 */
	public Game findById(int id) throws ServerException {
		//Check in cache
		if(gameCache.get(id) != null) {
			return gameCache.get(id);
		}
		//The "LIMIT 1" should not be technically necessary here, because every id is 
		//unique. This will return 1 result with or without it.
		String queryStr = "SELECT * FROM games WHERE id = ? LIMIT 1";
		PreparedStatement statement = entityDao.getPreparedStatement(queryStr);
		try {
			statement.setInt(1, id);
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		
		ResultSet gameResult = entityDao.get(statement);
		try {
			if(gameResult.next()) {
				Game game = gameFromResult(gameResult);
				gameCache.put(game.getId(), game);
			}
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		//No results found. Cache this in case the id is requested again
		gameCache.put(id, null);
		return null;
	}
	
	private Game gameFromResult(ResultSet result) throws SQLException {
		Game game = new Game();
		game.setDate(result.getString("date"));
		game.setId(result.getInt("id"));
		return game;
	}

}
