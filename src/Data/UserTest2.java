

package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Remote.UserObject;

//this is is designed to show that it can fail properly
public class UserTest2 {
	public static void main(String[] args) throws SQLException {
		// testCreate();
		// testAuthenticate();
		// testGetID();
		// testGetUserObj();
		//testExist();
		//testUpdate();
		//testAddF();
		//testIsF();
		//testGetF();
		testDeleteF();

	}

	public static void testCreate() throws SQLException {
		//user is in DB already
		UserObject user = new UserObject("test@test.ca", "dvsk", "myname2", 0);
		System.out.println(UserData.createUser(user));
	}

	public static void testAuthenticate() throws SQLException {
		int b = UserData.authenticate("test@test.ca", "");
		System.out.print(b);
		int c = UserData.authenticate("test@test.ca", "234234");
		int d = UserData.authenticate("test@te", "1234");
		System.out.print(c);
		System.out.print(d);
	}

	public static void testGetID() throws SQLException {
		int id = UserData.getId("lulz");
		System.out.println(id);
		id = UserData.getId("");
		System.out.println(id);
	}

	public static void testGetUserObj() throws SQLException {
		UserObject user = UserData.getUserObject(99);
		//should return null if user doesnt exist
		System.out.println(user==null);
	}

	public static void testUpdate() throws SQLException {
		UserObject user = new UserObject("test12@test.ca", "12de", "name", 3);
		user.setId(99);
		//here is shouldnt work since the user id doesnt exist
		System.out.print(UserData.updateUser(user));
	}
	
	public static void testExist() throws SQLException {
		System.out.println(UserData.exists(99));
		System.out.print(UserData.exists(""));
		//we also need to make sure that the user names have to start with letter
	}
	
	public static void testAddF() throws SQLException {
		System.out.println(UserData.addFriend(1, 99));
		System.out.println(UserData.addFriend(1, 1));
		System.out.println(UserData.addFriend(99, 1));
	}
	
	public static void testIsF() throws SQLException{
		System.out.println(UserData.isFriend(2,99));
		System.out.println(UserData.isFriend(99,1));
	
	}
	
	public static void testGetF() throws SQLException{
		ArrayList<UserObject> list =  UserData.getFriends(99);
		System.out.println(list);
		return;
	}
	
	public static void testDeleteF() throws SQLException{
		System.out.println(UserData.deleteFriend(99,1));//user DNE
		System.out.println(UserData.deleteFriend(1,99));//Friend DNE
		System.out.println(UserData.deleteFriend(99,98));//both
	}
}