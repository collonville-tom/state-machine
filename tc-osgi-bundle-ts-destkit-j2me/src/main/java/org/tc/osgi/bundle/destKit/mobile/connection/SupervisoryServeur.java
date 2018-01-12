package destKit.mobile.connection;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

import destKit.mobile.SupervisorExec;

public class SupervisoryServeur implements Runnable {

	private ServerSocketConnection scn = null;
	private Vector clientsThread = new Vector();
	private Vector systemClients = new Vector();
	private SupervisorExec supervisorExec;

	public SupervisoryServeur(SupervisorExec supervisorExec) {
		this.supervisorExec = supervisorExec;
	}

	public void run() {

		try {
			if (scn == null)
				scn = (ServerSocketConnection) Connector.open("socket://:1234");
			System.out.println("Ouverture de la connection du serveur...");
		} catch (IOException e) {
			System.err.println("supervisor Can't open connection"
					+ e.toString());
		}
		try {
			while (true) {
				Thread.yield();
				SocketConnection sc = (SocketConnection) scn.acceptAndOpen();
				System.out.println("Connection accepter...");
				SystemClient client = new SystemClient(sc, this.supervisorExec);
				Thread thread = new Thread(client);
				clientsThread.addElement(thread);
				systemClients.addElement(client);
				thread.start();
			}
		} catch (IOException e) {
			System.err.println("supervisor Can't accept connection"
					+ e.toString());
		} catch (Exception e) {
			System.err.println("supervisor Can't accept connection"
					+ e.toString());
		}
	}

	public void send(Message message) {
		for (int i = 0; i < systemClients.size(); i++) {

			((SystemClient) systemClients.elementAt(i)).send(message);
		}

	}

	public int getClientNumber() {
		return this.systemClients.size();
	}

}
