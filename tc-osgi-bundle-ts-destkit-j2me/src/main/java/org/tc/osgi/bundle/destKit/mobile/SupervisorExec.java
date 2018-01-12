package destKit.mobile;

import javax.microedition.lcdui.TextField;

import destKit.metamodel.Alphabet;
import destKit.metamodel.FiniteStateMachine;
import destKit.metamodel.core.Event;
import destKit.mobile.connection.Message;
import destKit.mobile.connection.Receiver;
import destKit.mobile.connection.SupervisoryServeur;
import destKit.utils.Iterator;
import destKit.utils.Listenable;

public class SupervisorExec extends ModeleExec {

	private static int synchro = 0;
	private TextField box = new TextField("Allowed Events", "", 128,TextField.UNEDITABLE);
	private Thread thread;
	private SupervisoryServeur supervisoryServeur;

	public SupervisorExec(ModeleChoice parent, FiniteStateMachine fsmChoice,
			String modelImageName) {
		super(parent, fsmChoice, modelImageName);
		this.getModelScreen().insert(0, box);
		this.getModelScreen().removeCommand(super.CONTINUE_CMD);
		supervisoryServeur = new SupervisoryServeur(this);
		thread = new Thread(supervisoryServeur);
		thread.start();
		System.out.println("Supervisor launch...");
		this.updateEvents(this.getFsmop().possibleEventInCurrentState());
	}

	protected void initAction() {
		this.supervisoryServeur.send(new Message(Message.STARTINIT, this
				.getFsmop().possibleEventInCurrentState()));
		this.getModelScreen().removeCommand(super.INIT_CMD);
		System.out.println("En attente de synchronisation...");
	}

	public void update(Listenable listenable) {
		Message message = new Message(((Receiver)listenable).getMessage());
		message.decode();
		System.out.println("header " + message.getHeader());
		if (message.getHeader().equals(Message.STARTINIT)) {
				synchro++;
			}
			if (synchro == this.supervisoryServeur.getClientNumber()) {
				System.out.println("Synchronisation realized...");
				this.supervisoryServeur.send(new Message(Message.START,
						new Alphabet()));
			}
			if (message.getHeader().equals(Message.CONTINUE)) {
				Event event = (Event) message.getAlphabet().elementAt(0);
				if (this.getFsmop().hasNext()) {
					this.getFsmop().update(event);
					this.getFsmop().next();
					this.updateEvents(this.getFsmop()
							.possibleEventInCurrentState());
					this.supervisoryServeur.send(new Message(Message.CONTINUE,
							this.getFsmop().possibleEventInCurrentState()));
				}
			}
		

	}

	// affiche les evenement possible du superviseur dans l'etat copurant
	protected void updateEvents(Alphabet alphabet) {
		try{
		System.out.println("Mise a jour affichage evenements autorisés...");
		box.setString("");
		StringBuffer buff = new StringBuffer();
		Iterator it = alphabet.iterator();
		for (; it.hasNext();) {
			Event e = (Event) it.next();
			buff.append(e.getName());
			buff.append(",");
		}
		box.setString(buff.toString());
		
		this.show();
		}catch(Exception e)
		{
			System.err.println("ERROR");
			e.printStackTrace();
		}
	}

	// pas implementée
	protected void continueAction() {

	}

}
