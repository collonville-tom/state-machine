package destKit.mobile.connection;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;

import javax.microedition.io.SocketConnection;

import destKit.utils.Listenable;
import destKit.utils.Listener;

public class Receiver implements Runnable, Listenable {

	private Vector listeners = new Vector();
	private SocketConnection sc;
	private String result;

	public Receiver(SocketConnection sc) {
		this.sc = sc;
	}

	public void run() {
		DataInputStream is = null;
		try {
			is = sc.openDataInputStream();
			while (true) {
				Thread.yield();

				if ((result = is.readUTF()) != null) {
					notifyListeners();
					System.out.println("reception..." + result);
				}
				result = null;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			is.close();
			sc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getMessage() {
		return this.result;
	}

	public void addListener(Listener l) {
		this.listeners.addElement(l);
	}

	public void notifyListeners() {
		for (int i = 0; i < listeners.size(); i++) {
			((Listener) listeners.elementAt(i)).update(this);
		}

	}

}
