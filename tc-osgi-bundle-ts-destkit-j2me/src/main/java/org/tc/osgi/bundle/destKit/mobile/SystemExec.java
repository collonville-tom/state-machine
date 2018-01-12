package destKit.mobile;

import javax.microedition.lcdui.ChoiceGroup;

import destKit.metamodel.Alphabet;
import destKit.metamodel.FiniteStateMachine;
import destKit.metamodel.core.Event;
import destKit.mobile.connection.Message;
import destKit.mobile.connection.Receiver;
import destKit.mobile.connection.SystemClient;
import destKit.utils.Iterator;
import destKit.utils.Listenable;

public class SystemExec extends ModeleExec {

	private ChoiceGroup choice = new ChoiceGroup("Possible Events",	ChoiceGroup.EXCLUSIVE);
	private SystemClient systemClient;
	private Thread thread;
	private Alphabet receive;

	public SystemExec(ModeleChoice parent, FiniteStateMachine fsmChoice,
			String modelImageName) {
		super(parent, fsmChoice, modelImageName);
		this.getModelScreen().insert(0, choice);
		this.getModelScreen().removeCommand(super.INIT_CMD);
		systemClient = new SystemClient(this);
		thread = new Thread(systemClient);
		thread.start();
		System.out.println("System launch...");
	}

	protected void continueAction() {

		int index = this.choice.getSelectedIndex();
		String eventName = this.choice.getString(index);
		Event e = this.receive.getEvent(eventName);
		if (e != null) {
			this.getFsmop().update(e);
			this.getFsmop().next();
			Alphabet alph = new Alphabet();
			alph.addEvent(e);
			this.systemClient.send(new Message(Message.CONTINUE, alph));
		} else
			System.out.println("evenement non reconu");
		this.getModelScreen().removeCommand(super.CONTINUE_CMD);
	}

	public void update(Listenable listenable) {
		Message message = new Message(((Receiver)listenable).getMessage());
		message.decode();
		if (message.getHeader().equals(Message.STARTINIT)) {
			System.out.println("Demande de synchronisation..."
					+ message.getAlphabet().toString());
			this.updateEvents(message.getAlphabet());
			this.receive = message.getAlphabet();
			this.systemClient.send(new Message(Message.STARTINIT,
					new Alphabet()));
		}
		if (message.getHeader().equals(Message.START)) {
			System.out.println("synchronisation realisé...");
			this.getModelScreen().addCommand(super.CONTINUE_CMD);
		}
		if (message.getHeader().equals(Message.CONTINUE)) {
			this.updateEvents(message.getAlphabet());
			this.receive = message.getAlphabet();
			this.getModelScreen().addCommand(super.CONTINUE_CMD);
		}

	}

	protected void updateEvents(Alphabet alphabet) {
		System.out.println("Mise a jour affichage evenement autorisé...");
		this.choice.deleteAll();
		Iterator it = this.getFsmop().possibleEventInCurrentState().iterator();
		for (; it.hasNext();) {
			Event e = (Event) it.next();
			if (alphabet.containsEvent(e)) {
				choice.append(e.getName(), null);
			}
		}
		this.show();
	}

	// pas implementée
	protected void initAction() {
		// TODO Auto-generated method stub

	}

}
