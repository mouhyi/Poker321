package Server.userModule;

import java.io.Serializable;

/**
 * This is a wrapper class for the 'Users' table
 * 
 * @author mouhyi
 * 
 */
public class UserObject implements Serializable {

	private int id;
	private String email;
	private String password;
	private String name;
	private double chips;
	private boolean online;

	/**
	 * Constructor with no parameters
	 * 
	 * @author mouhyi
	 */
	public UserObject() {
		super();
	}

	/**
	 * Constructor with parameters
	 * 
	 * @author mouhyi
	 */
	public UserObject(String email, String password, String name, double chips) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.chips = chips;
	}

	// Setters and Getters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getChips() {
		return chips;
	}

	public void setChips(double chips) {
		this.chips = chips;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String toString() {
		String result = "( id: " + id + ", email: " + email + ", password: "
				+ password+ ", name: "+name +", chips: "+chips
				+ ", online: "+online+" )";
		
		return result;
	}
}
