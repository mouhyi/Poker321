package Server.userModule;

import java.rmi.RemoteException;

public class InviteThread extends Thread {
	
	int userId, friendId;
	String msg;
	UserImpl uI;
	
	public InviteThread(int userId, int friendId, String msg, UserImpl uI){
		this.userId = userId;
		this.friendId = friendId;
		this.msg = msg;
		this.uI = uI;
	}
	
	public void run(){
		try {
			uI.getClient(friendId).showNotificationMessage(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
