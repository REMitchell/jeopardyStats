package com.ryanemitchell.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ryanemitchell.entities.Player;
import com.ryanemitchell.exception.ServerException;

public class PlayerDao {
	
	private EntityDao entityDao = null;
	private Map<Integer, Player> playerCache;
	private static PlayerDao playerDao;
	
	private PlayerDao() throws ServerException{
		entityDao = EntityDao.getInstance();
		playerCache = new HashMap<Integer, Player>();
	}
	
	public static PlayerDao getInstance() throws ServerException {
		if(playerDao == null) {
			playerDao = new PlayerDao();
		}
		return playerDao;
	}
	
	
	public Player findById(int id) throws ServerException {
		if(playerCache.get(id) != null) {
			return playerCache.get(id);
		}
		
		String queryStr = "SELECT * FROM players WHERE id = ? LIMIT 1";
		PreparedStatement statement = entityDao.getPreparedStatement(queryStr);
		try {
			statement.setInt(1, id);
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		
		ResultSet gameResult = entityDao.get(statement);
		try {
			if(gameResult.next()) {
				Player player = playerFromResult(gameResult);
				playerCache.put(player.getId(), player);
				return player;
			}
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		//No results found
		playerCache.put(id, null);
		return null;
	}

	
	private Player playerFromResult(ResultSet result) throws SQLException {
		Player player = new Player();
		player.setId(result.getInt("id"));
		player.setName(result.getString("name"));
		player.setShortname(result.getString("shortname"));
		player.setBackground(result.getString("background"));
		return player;
	}
	

}
