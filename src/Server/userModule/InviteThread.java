package Server.userModule;

import java.rmi.RemoteException;

/**
 * Thread to implement invite notification
 * @author mouhyi
 *
 */
public class InviteThread extends Thread {
	
	int userId, friendId;
	String msg;
	UserImpl uI;
	
	/**
	 * Constructor
	 * @param userId
	 * @param friendId
	 * @param msg
	 * @param uI
	 */
	public InviteThread(int userId, int friendId, String msg, UserImpl uI){
		this.userId = userId;
		this.friendId = friendId;
		this.msg = msg;
		this.uI = uI;
	}
	
	/**
	 * RUN method of the thread, starts the threads
	 */
	public void run(){
		try {
			uI.getClient(friendId).showNotificationMessage(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
