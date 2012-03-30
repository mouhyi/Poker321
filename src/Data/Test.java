package Data;

import java.sql.*;
import java.util.HashMap;

import Server.userModule.UserObject;

public class Test {
	public static void main(String[] args) throws SQLException {
		// testCreate();
		// testAuthenticate();
		// testGetID();
		// testGetUserObj();
		//testExist();
		testUpdate();
	}

	public static void testCreate() throws SQLException {
		UserObject user = new UserObject("test2@test.ca", "dvsk", "myname2", 0);
		UserData.createUser(user);
	}

	public static void testAuthenticate() throws SQLException {
		int b = UserData.authenticate("test@test.ca", "1234");
		System.out.print(b);
	}

	public static void testGetID() throws SQLException {
		int id = UserData.getId("myname2");
		System.out.println(id);
		id = UserData.getId("test@test.ca");
		System.out.println(id);
	}

	public static void testGetUserObj() throws SQLException {
		UserObject user = UserData.getUserObject(4);
		System.out.println(user.toString());
	}

	public static void testUpdate() throws SQLException {
		UserObject user = new UserObject("test12@test.ca", "12de", "name", 3);
		user.setId(3);
		System.out.print(UserData.updateUser(user));
	}
	
	public static void testExist() throws SQLException {
		System.out.println(UserData.exists(2));
		System.out.print(UserData.exists("mouhyi1"));
	}
}
