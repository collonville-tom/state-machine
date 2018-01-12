package destKit.mobile;

import java.io.IOException;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.lcdui.List;

import destKit.metamodel.FiniteStateMachine;
import destKit.metamodel.core.Event;
import destKit.metamodel.core.State;

public class ModeleChoice implements CommandListener {

	private DestkitMobile parent;
	private int config = 0;
	public final static int SUPERVISOR = 0;
	public final static int SYSTEM = 1;
	private List modelList = new List("Model Choice", List.IMPLICIT);
	private final Command BACKTOMAIN_CMD = new Command("Back", Command.BACK, 2);
	private final Command BACKTOPREC_CMD = new Command("Back", Command.BACK, 2);
	private final Command OK_CMD = new Command("Ok", Command.SCREEN, 1);
	private final Command VIEW_CMD = new Command("View", Command.SCREEN, 1);
	private final Form modelScreen = new Form("Model Viewer");

	public ModeleChoice(DestkitMobile parent, int config) {
		this.config = config;
		this.parent = parent;
		modelList.addCommand(OK_CMD);
		modelList.addCommand(BACKTOMAIN_CMD);
		modelList.addCommand(VIEW_CMD);
		if (config == SUPERVISOR) {
			modelList.append("Supervisor model for Cat and Mouse", null);
			modelList.append("Supervisor model for Bunny", null);
		}
		if (config == SYSTEM) {
			modelList.append("Cat model", null);
			modelList.append("Mouse model", null);
			modelList.append("Bunny patte1", null);
			modelList.append("Bunny patte2", null);
			modelList.append("Bunny patte3", null);
			modelList.append("Bunny patte4", null);
			modelList.append("Bunny patte5", null);
			modelList.append("Bunny patte6", null);
		}
		this.show();
		modelList.setCommandListener(this);
	}

	public void show() {
		Display.getDisplay(parent).setCurrent(modelList);
	}

	public void commandAction(Command command, Displayable displayable) {
		if (command == BACKTOMAIN_CMD) {
			parent.show();
		}

		if (command == BACKTOPREC_CMD) {
			this.show();
		}

		if (command == OK_CMD) {
			if (config == SUPERVISOR) {
				new SupervisorExec(this, this.superisorChoice(modelList
						.getSelectedIndex()), parent.getAppProperty("image-S-"
						+ modelList.getSelectedIndex()));
			}
			if (config == SYSTEM) {
				new SystemExec(this, this.systemChoice(modelList
						.getSelectedIndex()), parent.getAppProperty("image-"
						+ modelList.getSelectedIndex()));
			}
		}
		modelScreen.deleteAll();
		if (command == VIEW_CMD) {
			if (config == SUPERVISOR) {
				modelScreen.addCommand(BACKTOPREC_CMD);
				try {
					String imgName = parent.getAppProperty("image-S-"
							+ modelList.getSelectedIndex());
					Image img = Image.createImage(parent.getClass()
							.getResourceAsStream(imgName));
					ImageItem item = new ImageItem(imgName, img,
							ImageItem.PLAIN, null);
					item.setPreferredSize(200, 500);
					modelScreen.append(item);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Display.getDisplay(parent).setCurrent(modelScreen);
				modelScreen.setCommandListener(this);
			}
			if (config == SYSTEM) {
				modelScreen.addCommand(BACKTOPREC_CMD);
				try {
					String imgName = parent.getAppProperty("image-"
							+ modelList.getSelectedIndex());
					Image img = Image.createImage(parent.getClass()
							.getResourceAsStream(imgName));
					ImageItem item = new ImageItem(imgName, img,
							ImageItem.PLAIN, null);
					item.setPreferredSize(50, 50);
					modelScreen.append(item);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Display.getDisplay(parent).setCurrent(modelScreen);
				modelScreen.setCommandListener(this);
			}
		}

	}

	private FiniteStateMachine superisorChoice(int i) {
		FiniteStateMachine fsm = null;
		switch (i) {
		case 0: {
			fsm = new FiniteStateMachine("S");
			fsm.addState(new State("M0E2M0E2", false, true));
			fsm.addState(new State("M4E0M4E0", false, true));
			fsm.addState(new State("M4E3M4E3", false, true));
			fsm.addState(new State("M4E2M4E2", true, true));
			fsm.addState(new State("M3E2M3E2", false, true));
			fsm.addState(new State("M4E1M4E1", false, true));
			fsm.addEvent(new Event("m1", true, true));
			fsm.addEvent(new Event("c5", true, true));
			fsm.addEvent(new Event("c1", true, true));
			fsm.addEvent(new Event("m4", true, true));
			fsm.addEvent(new Event("c3", true, true));
			fsm.addEvent(new Event("m3", true, true));
			fsm.addEvent(new Event("m6", true, true));
			fsm.addEvent(new Event("m5", true, true));
			fsm.addEvent(new Event("c2", true, true));
			fsm.addEvent(new Event("m2", true, true));
			fsm.addEvent(new Event("c4", true, true));
			fsm.addEvent(new Event("c7", false, true));
			fsm.addEvent(new Event("c6", true, true));
			fsm.addTransition("M3E2M3E2", "m6", "M0E2M0E2");
			fsm.addTransition("M4E0M4E0", "c4", "M4E3M4E3");
			fsm.addTransition("M4E2M4E2", "m5", "M3E2M3E2");
			fsm.addTransition("M0E2M0E2", "m4", "M4E2M4E2");
			fsm.addTransition("M4E0M4E0", "c1", "M4E1M4E1");
			fsm.addTransition("M4E2M4E2", "c3", "M4E0M4E0");
			fsm.addTransition("M4E1M4E1", "c7", "M4E3M4E3");
			fsm.addTransition("M4E1M4E1", "c2", "M4E2M4E2");
			fsm.addTransition("M4E3M4E3", "c7", "M4E1M4E1");
		}
			break;
		case 1:
		{
			BifFsm bif=new BifFsm();
			fsm=bif.getSupervisor();
		}break;
		default:
			break;
		}
		return fsm;
	}

	private FiniteStateMachine systemChoice(int i) {
		FiniteStateMachine fsm = null;
		switch (i) {
		case 0: {
			State catRoom0 = new State("C0", State.NOTINITIAL, State.NOTMARKED);
			State catRoom1 = new State("C1", State.NOTINITIAL, State.NOTMARKED);
			State catRoom2 = new State("C2", State.INITIAL, State.MARKED);
			State catRoom3 = new State("C3", State.NOTINITIAL, State.NOTMARKED);
			State catRoom4 = new State("C4", State.NOTINITIAL, State.NOTMARKED);
			fsm = new FiniteStateMachine("cat");
			fsm
					.addEvent(new Event("c7", Event.UNCONTROLLABLE,
							Event.OBSERVABLE));
			fsm.addState(catRoom0);
			fsm.addState(catRoom1);
			fsm.addState(catRoom2);
			fsm.addState(catRoom3);
			fsm.addState(catRoom4);
			fsm.addTransition("C0", "c1", "C1");
			fsm.addTransition("C1", "c2", "C2");
			fsm.addTransition("C2", "c3", "C0");
			fsm.addTransition("C0", "c4", "C3");
			fsm.addTransition("C3", "c5", "C4");
			fsm.addTransition("C4", "c6", "C0");
			fsm.addTransition("C3", "c7", "C1");
			fsm.addTransition("C1", "c7", "C3");
		}
			break;
		case 1: {
			State mouseRoom0 = new State("M0", State.NOTINITIAL,
					State.NOTMARKED);
			State mouseRoom1 = new State("M1", State.NOTINITIAL,
					State.NOTMARKED);
			State mouseRoom2 = new State("M2", State.NOTINITIAL,
					State.NOTMARKED);
			State mouseRoom3 = new State("M3", State.NOTINITIAL,
					State.NOTMARKED);
			State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);
			fsm = new FiniteStateMachine("mouse");
			fsm.addState(mouseRoom0);
			fsm.addState(mouseRoom1);
			fsm.addState(mouseRoom2);
			fsm.addState(mouseRoom3);
			fsm.addState(mouseRoom4);
			fsm.addTransition("M0", "m1", "M2");
			fsm.addTransition("M2", "m2", "M1");
			fsm.addTransition("M1", "m3", "M0");
			fsm.addTransition("M0", "m4", "M4");
			fsm.addTransition("M4", "m5", "M3");
			fsm.addTransition("M3", "m6", "M0");
		}
			break;
		case 2: {
			fsm = new FiniteStateMachine("patte1");
			fsm.addState(new State("Ret1", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att1", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot1", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep1", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea1", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret1", "pep1", "Att1");
			fsm.addTransition("Att1", "protraction1", "Prot1");
			fsm.addTransition("Prot1", "pea1", "Ret1");
		}
			break;
		case 3: {
			fsm = new FiniteStateMachine("patte2");
			fsm.addState(new State("Ret2", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att2", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot2", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep2", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea2", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret2", "pep2", "Att2");
			fsm.addTransition("Att2", "protraction2", "Prot2");
			fsm.addTransition("Prot2", "pea2", "Ret2");
		}
			break;
		case 4: {
			fsm = new FiniteStateMachine("patte3");
			fsm.addState(new State("Ret3", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att3", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot3", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep3", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea3", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret3", "pep3", "Att3");
			fsm.addTransition("Att3", "protraction3", "Prot3");
			fsm.addTransition("Prot3", "pea3", "Ret3");
		}
			break;
		case 5: {
			fsm = new FiniteStateMachine("patte4");
			fsm.addState(new State("Ret4", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att4", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot4", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep4", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea4", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret4", "pep4", "Att4");
			fsm.addTransition("Att4", "protraction4", "Prot4");
			fsm.addTransition("Prot4", "pea4", "Ret4");
		}
			break;
		case 6: {
			fsm = new FiniteStateMachine("patte5");
			fsm.addState(new State("Ret5", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att5", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot5", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep5", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea5", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret5", "pep5", "Att5");
			fsm.addTransition("Att5", "protraction5", "Prot5");
			fsm.addTransition("Prot5", "pea5", "Ret5");
		}
			break;
		case 7: {
			fsm = new FiniteStateMachine("patte6");
			fsm.addState(new State("Ret6", State.INITIAL, State.MARKED));
			fsm.addState(new State("Att6", State.NOTINITIAL, State.NOTMARKED));
			fsm.addState(new State("Prot6", State.NOTINITIAL, State.NOTMARKED));
			fsm.addEvent(new Event("pep6", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addEvent(new Event("pea6", Event.UNCONTROLLABLE,
					Event.OBSERVABLE));
			fsm.addTransition("Ret6", "pep6", "Att6");
			fsm.addTransition("Att6", "protraction6", "Prot6");
			fsm.addTransition("Prot6", "pea6", "Ret6");
		}
			break;
		default:
			break;
		}
		return fsm;
	}

	public DestkitMobile getParent() {
		return parent;
	}

}
