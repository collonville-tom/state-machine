package nm_midlet;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

public class SystemExecCat implements CommandListener {

	private final Command OK_CMD = new Command("continueCommand", Command.OK, 1);

	private final Command EXIT_CMD = new Command("exitCommand", Command.EXIT, 1);

	private final Command BACK_CMD = new Command("backCommand", Command.BACK, 2);

	private Form systemForm = new Form("");

	private ChoiceGroup selectableEvents = new ChoiceGroup("Choice",
			ChoiceGroup.EXCLUSIVE);

	private ImageItem systemView = new ImageItem("", Image.createImage(system
			.getClass().getResourceAsStream("")), ImageItem.PLAIN, null);

	private SystemChoice system;

	public SystemExecCat(SystemChoice system) {
		this.system = system;

		systemForm.addCommand(OK_CMD);

		systemForm.addCommand(EXIT_CMD);

		systemForm.addCommand(BACK_CMD);

		systemForm.append(selectableEvents);

		systemForm.append(systemView);

		this.show();
		systemForm.setCommandListener(this);
	}

	public void commandAction(Command command, Displayable displayable) {

		if (command == OK_CMD) {

			switch (systemForm.getSelectedIndex()) {
			}
		}

		if (command == EXIT_CMD) {
			this.getMidlet().destroy();

		}

		if (command == BACK_CMD) {

		}

	}

	public void show() {
		Display.getDisplay(this.getMidlet()).setCurrent(systemForm);
	}

	public DestKitMobile getMidlet() {
		return this.system.getMidlet();
	}
}
