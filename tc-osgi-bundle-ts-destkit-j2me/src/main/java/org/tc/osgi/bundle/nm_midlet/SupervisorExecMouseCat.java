package nm_midlet;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;

public class SupervisorExecMouseCat implements CommandListener {

	private final Command OK_CMD = new Command("initCommand", Command.OK, 1);

	private final Command EXIT_CMD = new Command("exitCommand", Command.EXIT, 1);

	private final Command BACK_CMD = new Command("backCommand", Command.BACK, 2);

	private Form supervisorForm = new Form("");

	private TextField possibleEvents = new TextField("", "", 128,
			TextField.UNEDITABLE);

	private ImageItem supervisorView = new ImageItem("", Image
			.createImage(supervisor.getClass().getResourceAsStream("")),
			ImageItem.PLAIN, null);

	private SupervisorChoice supervisor;

	public SupervisorExecMouseCat(SupervisorChoice supervisor) {
		this.supervisor = supervisor;

		supervisorForm.addCommand(OK_CMD);

		supervisorForm.addCommand(EXIT_CMD);

		supervisorForm.addCommand(BACK_CMD);

		supervisorForm.append(possibleEvents);

		supervisorForm.append(supervisorView);

		this.show();
		supervisorForm.setCommandListener(this);
	}

	public void commandAction(Command command, Displayable displayable) {

		if (command == OK_CMD) {

			switch (supervisorForm.getSelectedIndex()) {
			}
		}

		if (command == EXIT_CMD) {
			this.getMidlet().destroy();

		}

		if (command == BACK_CMD) {

		}

	}

	public void show() {
		Display.getDisplay(this.getMidlet()).setCurrent(supervisorForm);
	}

	public DestKitMobile getMidlet() {
		return this.supervisor.getMidlet();
	}
}
