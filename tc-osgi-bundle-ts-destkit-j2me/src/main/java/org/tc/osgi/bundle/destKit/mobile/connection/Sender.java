package destKit.mobile.connection;

import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.SocketConnection;

public class Sender implements Runnable {

	private Object lock = new Object();
	private SocketConnection sc;
	private String message;

	public Sender(SocketConnection sc) {
		this.sc = sc;
	}

	public void run() {
		DataOutputStream os = null;
		try {
			os = sc.openDataOutputStream();
			while (true) {
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (message != null) {
					os.writeUTF(message);
					System.out.println("Envoie..." + message);
				}
				this.message = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		try {
			os.close();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setMessage(String mess) {
		synchronized (lock) {
			lock.notify();
		}
		this.message = mess;
	}

}
