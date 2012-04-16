package Remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * Hand Remote Interface
 * @author mouhyi
 *
 */
public interface IHand  extends Remote  {
	/**
	 * Gets cards in hand
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<ICard>  getCards() throws RemoteException;
}
