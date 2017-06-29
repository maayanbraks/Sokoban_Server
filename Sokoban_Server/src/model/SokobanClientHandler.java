/**
* This class responsible to run the "talk" with clients
* @author Maayan & Eden
*/

package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import common.data.Level2D;
import common.data.MyTextLevelLoader;
import sokobanSolver.SokoSolution;
import sokobanSolver.SokobanSolver;

public class SokobanClientHandler extends Observable implements ClientHandler {

	private ExecutorService executor = Executors.newCachedThreadPool();
	private ClientInfo clientInfo;
	private SokobanSolver solver = new SokobanSolver();

	public SokobanClientHandler(int clientId, String clientIp, String clientPort){
		clientInfo = new ClientInfo();
		this.clientInfo.setId(clientId);
		this.clientInfo.setIp(clientIp);
		this.clientInfo.setPort(clientPort);
	}
	
	@Override
	public void handleClient(InputStream inFromClient, OutputStream outToClient) throws Exception {
		
		
		MyTextLevelLoader loader=new MyTextLevelLoader();//for loading level from InputStream (like txt file loading)
		Level2D lvl=loader.loadLevel(inFromClient);
		
		clientInfo.setState("Working");
		clientInfo.setCurrentLevel(lvl.getId());
		PrintWriter out = new PrintWriter(outToClient, true);
		
		setChanged();
		notifyObservers();
		
		Thread.sleep(50);
		
		//Get Solution
		SokoSolution solution = solver.solveLevel(lvl);
		
		
		out.print(solution.toString() + "\nEND" );
		out.flush();
		executor.shutdown();
		out.close();
		
		clientInfo.setState("done");
		
		setChanged();
		notifyObservers();
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	
	

}
