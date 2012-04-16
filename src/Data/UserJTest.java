package Data;


import static org.junit.Assert.*;


import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Remote.UserObject;

public class UserJTest {
	@Before
	public void setUp() throws Exception {

	}
	@Test
	public  void testCreate() throws SQLException {
		//user is in DB already
		UserObject user = new UserObject("testasdf@test.ca", "dvadssfk", "madfyname2", 12);
		assertTrue(UserData.createUser(user)==-1);
	}
	@Test
	public  void testAuthenticate() throws SQLException {
		int b = UserData.authenticate("test@test.ca", "");
		assertTrue(b==-1);
		int c = UserData.authenticate("test@test.ca", "234234");
		assertTrue(c==-1);
		int d = UserData.authenticate("test@te", "1234");
		assertTrue(d==-1);
	}
	@Test
	public  void testGetID() throws SQLException {
		int id = UserData.getId("lulz");
		assertTrue(id==-1);
		id = UserData.getId("");
		assertTrue(id==-1);
	}
	@Test
	public  void testGetUserObj() throws SQLException {
		UserObject user = UserData.getUserObject(99);
		//should return null if user doesnt exist
		assertTrue(user==null);
	}
	@Test
	public  void testUpdate() throws SQLException {
		UserObject user = new UserObject("test12@test.ca", "12de", "name", 3);
		user.setId(99);
		//here is shouldnt work since the user id doesnt exist
		assertTrue(UserData.updateUser(user)==-1);
	}
	@Test
	public  void testExist() throws SQLException {
		assertFalse(UserData.exists(99));
		assertFalse(UserData.exists(""));
		//we also need to make sure that the user names have to start with letter
	}
	@Test
	public  void testAddF() throws SQLException {
		assertTrue(UserData.addFriend(1, 99)==-1);
		assertTrue(UserData.addFriend(1, 1)==-1);
		assertTrue(UserData.addFriend(99, 1)==-1);
	}
	@Test
	public  void testIsF() throws SQLException{
		assertFalse(UserData.isFriend(2,99));
		assertFalse(UserData.isFriend(99,1));

	}
	@Test
	public  void testGetF() throws SQLException{
		ArrayList<UserObject> list =  UserData.getFriends(99);
		assertTrue(list.size()==0);
		return;
	}
	@Test
	public  void testDeleteF() throws SQLException{
		assertTrue(UserData.deleteFriend(99,1)==0);//user DNE
		assertTrue(UserData.deleteFriend(1,99)==0);//Friend DNE
		assertTrue(UserData.deleteFriend(99,98)==0);//both
	}
}
