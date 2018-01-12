package destKit.mobile.connection;

import destKit.metamodel.Alphabet;
import destKit.metamodel.core.Event;
import destKit.utils.HashSetIterator;

public class Message {

	public static String CONTINUE = "Continue";
	public static String STARTINIT = "StartInit";
	public static String START = "Start";
	private String trame;
	private String header;
	private Alphabet alphabet;

	public Message(String trame) {
		this.trame = trame;
	}

	public Message(String header, Alphabet alph) {
		this.header = header;
		this.alphabet = alph;
	}

	public String encode() {
		StringBuffer buff = new StringBuffer(this.header);
		buff.append(":");
		buff.append(this.serializeAlphabet(this.alphabet));
		System.out.println("Trame a envoyer: " + buff.toString());
		return buff.toString();
	}

	public void decode() {
		if(trame==null) System.out.println("error trame null");
		int index = trame.indexOf(":");
		header = trame.substring(0, index);
		alphabet = this.deserializeAlphabet(trame.substring(index + 1, trame.length()));
	}

	private String serializeEvent(Event e) {
		StringBuffer buff = new StringBuffer();
		buff.append(e.getName());
		buff.append(",");
		buff.append(e.isControllable());
		buff.append(",");
		buff.append(e.isObservable());
		return buff.toString();
	}

	private Event deserializeEvent(String e) {
		int index = e.indexOf(",");
		Event event = new Event(e.substring(0, index));
		e = e.substring(index + 1);
		index = e.indexOf(",");
		if (e.substring(0, index).equals("false"))
			event.setControllable(Event.UNCONTROLLABLE);
		if (e.substring(index + 1).equals("false"))
			event.setObservable(Event.UNOBSERVABLE);
		return event;
	}

	private String serializeAlphabet(Alphabet alphabet) {
		HashSetIterator it = (HashSetIterator) alphabet.iterator();
		StringBuffer buff = new StringBuffer();
		for (; it.hasNext();) {
			buff.append(serializeEvent((Event) it.next()));
			buff.append("|");
		}
		return buff.toString();
	}

	private Alphabet deserializeAlphabet(String string) {
		Alphabet alph = new Alphabet();
		int indexmax = 0;
		int indexmin = 0;
		char[] message = string.toCharArray();
		for (; indexmax < string.length(); indexmax++) {
			if (message[indexmax] == '|') {
				alph.addEvent(deserializeEvent(string.substring(indexmin,
						indexmax)));
				indexmin = indexmax + 1;
			}
		}
		return alph;
	}

	public String getTrame() {
		return trame;
	}

	public String getHeader() {
		return header;
	}

	public Alphabet getAlphabet() {
		return alphabet;
	}

}
