package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Server.userModule.UserObject;

public class UserTest {
	public static void main(String[] args) throws SQLException {
		// testCreate();
		// testAuthenticate();
		// testGetID();
		// testGetUserObj();
		//testExist();
		//testUpdate();
		//testAddF();
		//testIsF();
		testGetF();
		//testDeleteF();

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
	
	public static void testAddF() throws SQLException {
		System.out.println(UserData.addFriend(1, 3));
		//System.out.println(UserData.addFriend(1, 4));
		//System.out.println(UserData.addFriend(1, 2));
	}
	
	public static void testIsF() throws SQLException{
		System.out.println(UserData.isFriend(1,4));
	}
	
	public static void testGetF() throws SQLException{
		ArrayList<UserObject> list =  UserData.getFriends(1);
		return;
	}
	
	public static void testDeleteF() throws SQLException{
		System.out.println(UserData.deleteFriend(1,3));
	}
	
	public static testStats(){
		
	}
}