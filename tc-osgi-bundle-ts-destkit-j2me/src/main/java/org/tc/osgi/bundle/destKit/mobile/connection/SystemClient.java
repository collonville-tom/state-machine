package destKit.mobile.connection;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

import destKit.mobile.SupervisorExec;
import destKit.mobile.SystemExec;

public class SystemClient implements Runnable {

	private SocketConnection sc = null;
	private Sender sender;
	private Receiver receiver;
	private Thread sendThread;
	private Thread receiveThread;
	private SystemExec systemExec;
	private SupervisorExec supervisorExec;

	public SystemClient(SocketConnection sc, SupervisorExec supervisorExec) {
		this.sc = sc;
		this.supervisorExec = supervisorExec;
	}

	public SystemClient(SystemExec systemExec) {
		this.systemExec = systemExec;
	}

	public void run() {
		try {
			if (sc == null)
				sc = (SocketConnection) Connector
						.open("socket://localhost:1234");
			System.out.println("Ouverture de la connection du client...");
		} catch (IOException e) {
			System.err.println("System can't open connection" + e.toString());
		} catch (Exception e) {
			System.err.println("System can't open connection" + e.toString());
		}

		sender = new Sender(sc);
		sendThread = new Thread(sender);
		receiver = new Receiver(sc);
		if (systemExec != null)
			receiver.addListener(this.systemExec);
		if (supervisorExec != null)
			receiver.addListener(this.supervisorExec);
		receiveThread = new Thread(receiver);
		sendThread.start();
		receiveThread.start();
	}

	public void send(Message message) {

		this.sender.setMessage(message.encode());
	}


}
