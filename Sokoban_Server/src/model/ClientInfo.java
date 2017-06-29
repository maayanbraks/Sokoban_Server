/*
 * client's information
 */

package model;

import java.io.Serializable;
import java.net.Socket;

public class ClientInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String ip;
	private String port;
	private String state;
	private String currentLevel;
	@SuppressWarnings("unused")
	private Socket connectioSock;

	public ClientInfo() {
		this.id = 0;
		this.ip = "";
		this.port = "";
		this.state = "";
		this.currentLevel = "";
		this.connectioSock = null;
	}

	public ClientInfo(int id, String ip, String port, Socket connectioSock) {
		this.id = id;
		this.ip = ip;
		this.port = port;
		this.state = "";
		this.currentLevel = "";
		this.connectioSock = connectioSock;
	}

	public ClientInfo(int id, String ip, String port, String state, String task, Socket connectioSock) {
		this.id = id;
		this.ip = ip;
		this.port = port;
		this.state = state;
		this.currentLevel = task;
		this.connectioSock = connectioSock;
	}

	public ClientInfo(ClientInfo client) {
		this.id = client.id;
		this.ip = client.ip;
		this.port = client.port;
		this.state = client.state;
		this.currentLevel = client.currentLevel;
		this.connectioSock = client.connectioSock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	// CHECK
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

}
