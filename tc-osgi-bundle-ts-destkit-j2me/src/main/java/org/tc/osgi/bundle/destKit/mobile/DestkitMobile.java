package destKit.mobile;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

public class DestkitMobile extends MIDlet implements CommandListener {

	private final Command EXIT_CMD = new Command("Exit", Command.EXIT, 2);
	private final Command OK_CMD = new Command("Ok", Command.SCREEN, 1);
	private static final String[] elements = { "Supervisor", "System" };
	private final List menu = new List("Mobile Supervisory Demo",
			List.IMPLICIT, elements, null);

	public DestkitMobile() {
		menu.addCommand(OK_CMD);
		menu.addCommand(EXIT_CMD);
		menu.setCommandListener(this);
		this.show();
	}

	protected void destroyApp(boolean arg0) {
		System.out.println("destroy");
		notifyDestroyed();
	}
	
	

	public void show() {
		System.out.println("Midlet run...");
		Display.getDisplay(this).setCurrent(menu);
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() {
		// TODO Auto-generated method stub

	}

	public void commandAction(Command command, Displayable displayable) {
		if (command == EXIT_CMD) {
			destroyApp(true);
		}
		switch (menu.getSelectedIndex()) {
		case 0:
			new ModeleChoice(this, ModeleChoice.SUPERVISOR);
			break;
		case 1:
			new ModeleChoice(this, ModeleChoice.SYSTEM);
			break;
		default:
			break;
		}
	}

}
