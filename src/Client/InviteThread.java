package Client;

import GUI.ServerListener;

/**
 * Thread for sending game invitation
 * 
 * @author mouhyi
 * 
 */

public class InviteThread extends Thread {
	private ServerListener listener;
	private String msg;
	private UserClient cl;

	/**
	 * Constructor
	 * 
	 * @param sl
	 * @param msg
	 * @param cl
	 */

	public InviteThread(ServerListener sl, String msg, UserClient cl) {
		this.listener = sl;
		this.msg = msg;
		this.cl = cl;

	}

	/**
	 * starts thread
	 * 
	 */
	public void run() {
		listener.sendNotificationMessage(msg);
	}
}