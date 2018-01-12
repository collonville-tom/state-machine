package nm_midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

public class DestKitMobile extends MIDlet implements CommandListener {

	private final Command OK_CMD = new Command("okCommand", Command.OK, 1);

	private final Command EXIT_CMD = new Command("exitCommand", Command.EXIT, 1);

	private SupervisorChoice supervisor;

	private SystemChoice system;

	private static final String[] elements = { "Superviseur", "Systeme" };
	private final List slaveMasterChoice = new List("slaveMasterChoice",
			List.IMPLICIT, elements, null);

	public DestKitMobile() {

		slaveMasterChoice.addCommand(OK_CMD);

		slaveMasterChoice.addCommand(EXIT_CMD);

		slaveMasterChoice.setCommandListener(this);
		this.show();
	}

	public void commandAction(Command command, Displayable displayable) {

		if (command == OK_CMD) {

			switch (slaveMasterChoice.getSelectedIndex()) {

			case 0:
				supervisor = new SupervisorChoice(this);

			case 1:
				system = new SystemChoice(this);
			}
		}

		if (command == EXIT_CMD) {
			this.getMidlet().destroy();

		}

	}

	public void show() {
		System.out.println("Midlet run...");
		Display.getDisplay(this).setCurrent(slaveMasterChoice);
	}

	protected void destroyApp(boolean arg0) {
		System.out.println("destroy");
		notifyDestroyed();
	}

	public void destroy() {
		this.destroyApp(true);
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
	}

	protected void startApp() {
		// TODO Auto-generated method stub
	}

	public DestKitMobile getMidlet() {
		return this;
	}
}
