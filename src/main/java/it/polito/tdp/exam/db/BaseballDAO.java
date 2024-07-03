package it.polito.tdp.exam.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.exam.model.People;
import it.polito.tdp.exam.model.Team;

public class BaseballDAO {

	public List<People> readAllPlayers() {
		String sql = "SELECT * " + "FROM people";
		List<People> result = new ArrayList<People>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new People(rs.getString("playerID"), rs.getString("birthCountry"), rs.getString("birthCity"),
						rs.getString("deathCountry"), rs.getString("deathCity"), rs.getString("nameFirst"),
						rs.getString("nameLast"), rs.getInt("weight"), rs.getInt("height"), rs.getString("bats"),
						rs.getString("throws"), getBirthDate(rs), getDebutDate(rs), getFinalGameDate(rs),
						getDeathDate(rs)));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Team> readAllTeams() {
		String sql = "SELECT * " + "FROM  teams";
		List<Team> result = new ArrayList<Team>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Team(rs.getInt("iD"), rs.getInt("year"), rs.getString("teamCode"), rs.getString("divID"),
						rs.getInt("div_ID"), rs.getInt("teamRank"), rs.getInt("games"), rs.getInt("gamesHome"),
						rs.getInt("wins"), rs.getInt("losses"), rs.getString("divisionWinnner"),
						rs.getString("leagueWinner"), rs.getString("worldSeriesWinnner"), rs.getInt("runs"),
						rs.getInt("hits"), rs.getInt("homeruns"), rs.getInt("stolenBases"), rs.getInt("hitsAllowed"),
						rs.getInt("homerunsAllowed"), rs.getString("name"), rs.getString("park")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Team> getTeam(Map<String, Team> mappa) {
		String sql = "SELECT DISTINCT  t.name as n "
				+ "FROM appearances a, teams t  "
				+ "WHERE a.teamID = t.ID "
				+ "ORDER BY t.name asc";
		List<Team> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Team t = mappa.get(rs.getString("n"));
				result.add(t);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Integer> getAnni(Team t){
		String sql = "SELECT DISTINCT  a.`year` as y "
				+ "FROM appearances a, teams t  "
				+ "WHERE t.name = ? AND a.teamCode = t.teamCode";
		List<Integer> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, t.getName());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getInt("y"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<People> getGiocatori(Team t, int anno, Map<String, People> mappa){
		String sql = "SELECT a.playerID as id "
				+ "FROM appearances a, teams t  "
				+ "WHERE t.name = ? AND a.teamID = t.ID AND a.`year`= ? ";
		List<People> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, t.getName());
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(mappa.get(rs.getString("id")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
				
	}
	
	

	// =================================================================
	// ==================== HELPER FUNCTIONS =========================
	// =================================================================

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getBirthDate(ResultSet rs) {
		try {
			if (rs.getDate("birth_date") != null) {
				return rs.getDate("birth_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDebutDate(ResultSet rs) {
		try {
			if (rs.getDate("debut_date") != null) {
				return rs.getDate("debut_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getFinalGameDate(ResultSet rs) {
		try {
			if (rs.getDate("finalgame_date") != null) {
				return rs.getDate("finalgame_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Helper function per leggere le date e gestire quando sono NULL
	 * 
	 * @param rs
	 * @return
	 */
	private LocalDate getDeathDate(ResultSet rs) {
		try {
			if (rs.getDate("death_date") != null) {
				return rs.getDate("death_date").toLocalDate();
			} else {
				return null;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
