package nm_midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

public class SupervisorChoice implements CommandListener {

	private final Command OK_CMD = new Command("okCommand", Command.OK, 1);

	private final Command EXIT_CMD = new Command("exitCommand", Command.EXIT, 1);

	private final Command BACK_CMD = new Command("backCommand", Command.BACK, 2);

	private SupervisorExecBunny supExecBunny;

	private SupervisorExecMouseCat supExecMouseCat;

	private static final String[] elements = { "CatAndMouseSupervisor",
			"BunnySupervisor" };
	private final List supervisorList = new List("supervisorList",
			List.IMPLICIT, elements, null);

	private DestKitMobile midlet;

	public SupervisorChoice(DestKitMobile midlet) {
		this.midlet = midlet;

		supervisorList.addCommand(OK_CMD);

		supervisorList.addCommand(EXIT_CMD);

		supervisorList.addCommand(BACK_CMD);

		this.show();
		supervisorList.setCommandListener(this);
	}

	public void commandAction(Command command, Displayable displayable) {

		if (command == OK_CMD) {

			switch (supervisorList.getSelectedIndex()) {

			case 0:
				supExecBunny = new SupervisorExecBunny(this);

			case 1:
				supExecMouseCat = new SupervisorExecMouseCat(this);
			}
		}

		if (command == EXIT_CMD) {
			this.getMidlet().destroy();

		}

		if (command == BACK_CMD) {

		}

	}

	public void show() {
		Display.getDisplay(this.getMidlet()).setCurrent(supervisorList);
	}

	public DestKitMobile getMidlet() {
		return this.midlet.getMidlet();
	}
}
