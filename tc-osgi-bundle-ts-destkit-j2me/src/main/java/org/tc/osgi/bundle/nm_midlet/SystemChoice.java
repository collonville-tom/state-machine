package nm_midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

public class SystemChoice implements CommandListener {

	private final Command OK_CMD = new Command("okCommand", Command.OK, 1);

	private final Command EXIT_CMD = new Command("exitCommand", Command.EXIT, 1);

	private final Command BACK_CMD = new Command("backCommand", Command.BACK, 2);

	private SystemExecCat systExecCat;

	private SystemExecMouse systExecMouse;

	private static final String[] elements = { "Cat", "Mouse", "Patte1",
			"Patte2", "Patte3", "Patte4", "Patte5", "Patte6" };
	private final List systemList = new List("systemList", List.IMPLICIT,
			elements, null);

	private DestKitMobile midlet;

	public SystemChoice(DestKitMobile midlet) {
		this.midlet = midlet;

		systemList.addCommand(OK_CMD);

		systemList.addCommand(EXIT_CMD);

		systemList.addCommand(BACK_CMD);

		this.show();
		systemList.setCommandListener(this);
	}

	public void commandAction(Command command, Displayable displayable) {

		if (command == OK_CMD) {

			switch (systemList.getSelectedIndex()) {

			case 0:
				systExecCat = new SystemExecCat(this);

			case 1:
				systExecMouse = new SystemExecMouse(this);
			}
		}

		if (command == EXIT_CMD) {
			this.getMidlet().destroy();

		}

		if (command == BACK_CMD) {

		}

	}

	public void show() {
		Display.getDisplay(this.getMidlet()).setCurrent(systemList);
	}

	public DestKitMobile getMidlet() {
		return this.midlet.getMidlet();
	}
}
