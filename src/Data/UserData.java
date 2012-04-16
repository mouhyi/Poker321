package Data;

import java.sql.*;
import java.util.ArrayList;

import Remote.UserObject;

/**
 * This class represents the facade for the user's data management
 * 
 * @author Mouhyi
 */
// TODO: escape * all inputs (Security Req)

public class UserData {

	/**
	 * This method adds a new user to the 'users' table, tested: Mar25 4:00am
	 * 
	 * @return int: 0 on success, -1 on failure
	 * @author mouhyi
	 */
	// tested Mar 29, 07:30pm
	public static int createUser(UserObject user) throws SQLException {
		if (exists(user.getName())) {
			return -1;
		}

		String email = Methods.addQuotes(user.getEmail());
		String password = Methods.addQuotes(user.getPassword());
		String name = Methods.addQuotes(user.getName());
		String chips = Methods.addQuotes(user.getChips());

		Connection con = Methods.connectToDB("5CARD");
		String query = "INSERT INTO 5CARD.Users (email, password, name, chips, online)"
				+ "VALUES ("
				+ email
				+ ", "
				+ password
				+ ", "
				+ name
				+ ", "
				+ chips + ", true )";

		Statement st = con.createStatement();
		st.executeUpdate(query);
		return 0;

	}

	/**
	 * Adds a player to the 'Players' table
	 * 
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public static int createPlayer(int userId) throws SQLException {
		Connection con = Methods.connectToDB("5CARD");
		String query = "INSERT INTO 5CARD.Players (u_id, wins, losses, gameWinnings)"
				+ "VALUES (" + Methods.addQuotes(userId) + ", 0,0,0)";
		Statement st = con.createStatement();
		st.executeUpdate(query);
		return 0;
	}

	/**
	 * This method checks that the email and password submitted by the user are
	 * valid entries in the 'users' table
	 * 
	 * @author mouhyi
	 * @return int: 0 on success, -1 on failure
	 */
	// tested: Mar25 4:00am
	public static int authenticate(String email, String password) {
		int result = -1;
		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT * FROM 5CARD.Users WHERE email='" + email
					+ "' and password='" + password + "'";
			Statement st = con.createStatement();
			if (st.executeQuery(query).next())
				result = 0;
		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			return result;
		}

	}

	/**
	 * This method retrieves user's info from database and wraps it in an
	 * UserObject
	 * 
	 * @param userID
	 * @author mouhyi
	 * @return null if user doesn't exist, otherwise return user with 'userID'
	 */
	// tested: Mar29 7:44pm
	public static UserObject getUserObject(int userId) throws SQLException {
		UserObject user = null;
		Statement st = null;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT * FROM 5CARD.Users WHERE u_id='" + userId
					+ "'";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				user = new UserObject();
				user.setId(userId);
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setChips(rs.getDouble("chips"));
				user.setOnline(rs.getBoolean("online"));
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (st != null) {
				st.close();
			}
			return user;
		}
	}

	/**
	 * This method return the id of the user corresponding to the parameter or
	 * -1 if user does not exist
	 * 
	 * @param email
	 *            or name
	 * @author mouhyi
	 */
	// tested: Mar29 07:37pm
	public static int getId(String user) throws SQLException {
		int id = -1;
		Statement st = null;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT u_id FROM 5CARD.Users WHERE email='" + user
					+ "' or name='" + user + "'";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (st != null) {
				st.close();
			}
			return id;
		}
	}

	/**
	 * Check whether a user with an email or name equal to the param, exists in
	 * the DB
	 * 
	 * @param user
	 * @return boolean: true if user exists, false otherwise
	 * @author mouhyi
	 */
	// tested: Mar29 07:40pm
	public static boolean exists(String user) throws SQLException {
		return (getId(user) != -1);
	}

	/**
	 * Check whether a user with the @param:userId exists in the DB
	 * 
	 * @param userId
	 * @return boolean: true if user exists, false otherwise
	 * @author mouhyi
	 */
	// tested: Mar29 07:40pm
	public static boolean exists(int userId) throws SQLException {
		boolean result = false;
		Statement st = null;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT email FROM 5CARD.Users WHERE u_id='"
					+ userId + "'";
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (st != null) {
				st.close();
			}
			return result;
		}
	}

	/**
	 * This method updates a user in the 'users' table
	 * 
	 * @author mouhyi
	 */
	// tested: Mar29 07:50pm
	public static int updateUser(UserObject user) throws SQLException {
		int updated = -1;
		PreparedStatement pstmt = null;

		// return -1 if the user does not exist
		if (!exists(user.getId()))
			return -1;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "UPDATE 5CARD.Users SET name=? , chips =?, online=?, password=?"
					+ " WHERE u_id=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, user.getName());
			pstmt.setDouble(2, user.getChips());
			pstmt.setBoolean(3, user.isOnline());
			pstmt.setString(4, user.getPassword());
			pstmt.setInt(5, user.getId());

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
	 * This method retrieves the user's friend list from the 'friends' table
	 * 
	 * @return ArrayList of friends UserObjects
	 * @throws SQLException
	 * @author mouhyi
	 */
	// tested: Mar30 03:30pm
	public static ArrayList<UserObject> getFriends(int u_id)
			throws SQLException {
		ArrayList<UserObject> friends = new ArrayList<UserObject>();
		Statement stmt = null;

		try {
			Connection con = Methods.connectToDB("5CARD");
			String query = "SELECT f_id FROM 5CARD.Friends WHERE u_id="
					+ Methods.addQuotes(u_id);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				friends.add(getUserObject(rs.getInt("f_id")));
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			return friends;
		}
	}

	/**
	 * This method adds a new friend the user's friend list in the 'friends'
	 * table !!! For now, the friendship need not be confirmed by the other user
	 * 
	 * @throws SQLException
	 * @return 0 on success, -1 on failure
	 * @author mouhyi
	 */
	// tested: Mar29 08:30pm
	public static int addFriend(int userId, int friendId) throws SQLException {
		PreparedStatement pstmt = null;
		int success = -1;
		Connection con = null;

		if (!exists(userId) || !exists(friendId))
			return -1;

		if (isFriend(userId, friendId))
			return -1;

		try {
			con = Methods.connectToDB("5CARD");
			con.setAutoCommit(false);

			String query = "INSERT INTO 5CARD.Friends (u_id, f_id) VALUES (?, ?)";

			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, userId);
			pstmt.setInt(2, friendId);
			pstmt.executeUpdate();
			con.commit();

			pstmt.setInt(1, friendId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			con.commit();

			success = 0;

		} catch (SQLException e) {
			Methods.printSQLException(e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					Methods.printSQLException(excep);
				}
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			con.setAutoCommit(true);
			return success;
		}
	}

	/**
	 * This method deletes new the user's friend list in the 'friends' table For
	 * now, the friendship need not be confirmed by the other user
	 * 
	 * @throws SQLException
	 * @author mouhyi
	 */
	// tested: Mar30 03:00pm
	public static int deleteFriend(int userId, int friendId)
			throws SQLException {
		PreparedStatement pstmt = null;
		int success = -1;
		Connection con = null;

		try {
			con = Methods.connectToDB("5CARD");
			con.setAutoCommit(false);

			String query = "DELETE FROM 5CARD.Friends WHERE u_id=? AND f_id=? ";

			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, userId);
			pstmt.setInt(2, friendId);
			pstmt.executeUpdate();
			con.commit();

			pstmt.setInt(1, friendId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
			con.commit();

			success = 0;

		} catch (SQLException e) {
			Methods.printSQLException(e);
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					Methods.printSQLException(excep);
				}
			}
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			con.setAutoCommit(true);
			return success;
		}
	}

	/**
	 * Checks whether two users are friends or not
	 * 
	 * @param userId
	 * @param friendId
	 * @return boolean: true if the users are friends, false otherwise
	 * @throws SQLException
	 * @author mouhyi
	 */
	// tested: Mar30 02:30pm
	public static boolean isFriend(int userId, int friendId)
			throws SQLException {
		PreparedStatement pstmt = null;
		Connection con = null;
		boolean result = false;

		try {
			con = Methods.connectToDB("5CARD");

			String query = "SELECT * FROM 5CARD.Friends WHERE u_id=? AND f_id=? ";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, friendId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = true;
			}

		} catch (SQLException e) {
			Methods.printSQLException(e);
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
			return result;
		}
	}

}
