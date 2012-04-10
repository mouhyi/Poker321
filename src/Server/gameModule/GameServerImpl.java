package Server.gameModule;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GameServerImpl implements GameServer {

	@Override
	public IGameTable createTable(int ante, int hostId,
			ArrayList<Integer> playersId, double bringIn, String suggestedName)
			throws RemoteException {
		IGameTable table = new GameTable(ante, hostId, playersId,  bringIn, suggestedName);
		return table;
	}
}
