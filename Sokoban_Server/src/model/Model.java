package model;

import java.util.List;

public interface Model {

	void start() throws Exception;
	void stop() throws Exception;
	List<ClientInfo> getConnectedClients();
	void deleteClient(int clientId);

}
