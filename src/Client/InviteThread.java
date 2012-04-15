package Client;

import GUI.ServerListener;

public class InviteThread extends Thread {
	private ServerListener listener;
	private String msg;
	private UserClient cl;
	
	public InviteThread(ServerListener sl, String msg,   UserClient cl){
		this.listener = sl;
		this.msg = msg;
		this.cl = cl;
		
	}
	
	public void run(){
		listener.sendNotificationMessage(msg);
	}
}
