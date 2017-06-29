/**
* This class responsible on the server
* @author Maayan & Eden
*/

package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.javafx.scene.control.skin.NestedTableColumnHeader;

public class SokobanServer extends Observable implements Observer, Model {

	private int port = 0;
	private ArrayList<ClientInfo> clients;
	private int numOfClients = 0;
	private Thread threadServer = null;
	private volatile boolean stop = false;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private ServerSocket socket = null;

	public SokobanServer(int port) {
		this.port = port;
		this.clients = new ArrayList<ClientInfo>();

	}

	private void runServer() {
		try {
			socket = new ServerSocket(this.port);
			System.out.println("Server is alive.");

			SokobanServer thisServer = this;

			while (!stop) {
				Socket connectionSocket = socket.accept();
				System.out.println("Client has Conected");
				ClientInfo newClient = new ClientInfo(++numOfClients,
						connectionSocket.getInetAddress().toString().substring(1), "" + connectionSocket.getPort() + "",
						connectionSocket);
				addClient(connectionSocket);
				executor.submit(new Runnable() {
					@Override
					public void run() {			
						SokobanClientHandler ch = new SokobanClientHandler(newClient.getId() , newClient.getIp(), newClient.getPort());
						ch.addObserver(thisServer);

						try {
							
							ch.handleClient(connectionSocket.getInputStream(), connectionSocket.getOutputStream());
							newClient.setState(ch.getClientInfo().getState());
							newClient.setId(ch.getClientInfo().getId());
							newClient.setIp(ch.getClientInfo().getIp());
							newClient.setCurrentLevel(ch.getClientInfo().getCurrentLevel());
							setClient(newClient);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

			}

			socket.close();

		} catch (Exception e) {
		}
	}

	protected void addClient(Socket sock) {
		clients.add(new ClientInfo(++numOfClients, sock.getInetAddress().toString().substring(1),
				"" + sock.getPort() + "", sock));
		setChanged();
		notifyObservers();
	}
	
	protected void setClient(ClientInfo client) {
		for(ClientInfo c: clients){
			if(c.getPort()==client.getPort()){
				c.setCurrentLevel(client.getCurrentLevel());
				c.setIp(client.getIp());
				c.setId(client.getId());
				c.setState(client.getState());
			}
		}
		setChanged();
		notifyObservers();
	}

	@Override
	public void start() throws Exception {
		threadServer = new Thread(() -> runServer());
		threadServer.start();
	}

	@Override
	public void stop() throws IOException {

		this.stop = true;
		executor.shutdown();
		socket.close();
	}

	@Override
	public List<ClientInfo> getConnectedClients() {
		return this.clients;
	}

	@Override
	public void deleteClient(int clientId) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getId() == clientId) {
				clients.remove(i);
				setChanged();
				notifyObservers();
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
		notifyObservers();

	}
}
