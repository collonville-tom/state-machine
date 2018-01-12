package destKit.mobile;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;

import destKit.exec.FsmOpSem;
import destKit.exec.exception.ExecInitExeception;
import destKit.metamodel.Alphabet;
import destKit.metamodel.FiniteStateMachine;
import destKit.utils.Listener;

public abstract class ModeleExec implements CommandListener, Listener {

	private ModeleChoice parent;
	private FsmOpSem fsmop;
	protected final Command CONTINUE_CMD = new Command("Continue", Command.OK,
			1);
	protected final Command INIT_CMD = new Command("Init", Command.OK, 2);
	private final Command BACKTOMAIN_CMD = new Command("BackToMain",
			Command.BACK, 3);
	private final Command BACKTOPREC_CMD = new Command("BackToPrec",
			Command.BACK, 3);
	private Form modelScreen = new Form("Model Viewer");

	public ModeleExec(ModeleChoice parent, FiniteStateMachine fsmChoice,
			String modelImageName) {
		ImageItem imgItem = null;
		try {
			this.parent = parent;
			this.fsmop = new FsmOpSem(fsmChoice);
			imgItem = new ImageItem(modelImageName, Image.createImage(parent
					.getClass().getResourceAsStream(modelImageName)),
					ImageItem.PLAIN, null);
		} catch (ExecInitExeception e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		modelScreen.addCommand(INIT_CMD);
		modelScreen.addCommand(BACKTOPREC_CMD);
		modelScreen.addCommand(BACKTOMAIN_CMD);
		modelScreen.append(imgItem);
		this.show();
		modelScreen.setCommandListener(this);

	}

	protected abstract void updateEvents(Alphabet alphabet);

	protected abstract void continueAction();

	protected abstract void initAction();

	public void commandAction(Command command, Displayable display) {
		if (command == CONTINUE_CMD) {
			continueAction();
		}
		if (command == INIT_CMD) {
			initAction();
		}
		if (command == BACKTOMAIN_CMD) {
			parent.getParent().show();
		}

		if (command == BACKTOPREC_CMD) {
			parent.show();
		}
	}

	public void show() {
		Display.getDisplay(parent.getParent()).setCurrent(modelScreen);
	}

	public ModeleChoice getParent() {
		return parent;
	}

	public Form getModelScreen() {
		return modelScreen;
	}

	public FsmOpSem getFsmop() {
		return fsmop;
	}

}
