package Server.statisticsModule;
import java.rmi.*;
import java.sql.SQLException;

public interface IStatistics extends Remote {
	public String[][] leaderBoardDisplay() throws SQLException, RemoteException;
}
