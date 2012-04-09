package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Server.userModule.UserObject;

public class statistics {

	public static int updateUserStatistics(UserObject user, double newGameWinnings, boolean gameWon) throws SQLException {
		int updated = -1;
		PreparedStatement pstmt = null;

		// return -1 if the user does not exist
		if (!UserData.exists(user.getId()))
			return -1;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "UPDATE 5CARD.Players SET gameWinnings=? wins=?, losses=?"
					+ " WHERE u_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, user.getName());
			pstmt.setDouble(2, user.getChips()+newGameWinnings);
			pstmt.setBoolean(3, user.isOnline());
			pstmt.setDouble(4, user.getGameWinnings()+newGameWinnings);
			if(gameWon){
				pstmt.setInt(5, user.getWins()+1);
			}
			if(!gameWon){
				pstmt.setInt(6, user.setLosses()+1);
			}
			pstmt.setInt(7, user.getId());

			System.out.println(pstmt.toString());

			updated = pstmt.executeUpdate();

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			return updated;
		}
	}
	/** 
	 * This updates the statistics in the database for a user
	 * @param user
	 * @param newGameWinnings
	 * @param gameWon
	 * @return
	 * @author Peter
	 * @throws SQLException 
	 */
	
	public static String[][] createLeaderBoard() throws SQLException{
		Statement stmt = null;
		int i=0;
		String[][] leaderboard = new String[20][5];
		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT * FROM 5Card.Players ORDER BY gameWinnings DESC LIMIT 20";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				leaderboard[i][0]=rs.getString("name");
				leaderboard[i][1]=rs.getString("gameWinnings");
				leaderboard[i][2]=rs.getString("wins");
				leaderboard[i][3]=rs.getString("losses");
				leaderboard[i][4]="Great!";
				++i;
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			return leaderboard;
		}
		
	}
	/**
	 * This generates an array of Strings that is the leaderboard of top 25, ordered by game winnings
	 * @return
	 * @author Peter
	 */
}
