package Server.statisticsModule;

import java.sql.SQLException;
import java.util.ArrayList;

import Server.userModule.UserObject;

public class leaderBoard {

	public static String[][] leaderBoardDisplay() throws SQLException{
		return Data.Statistics.createLeaderBoard();
	}
	/** 
	 * This creates a 2D array where the first layer is the usernames in ranked order, the second is the number of games won
	 * and the third is the amount earned while playing
	 */
}
