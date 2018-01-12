package org.tc.osgi.bundle.ts.destkit.visitor;

import java.util.Iterator;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;

/**
 * PIVisitor.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class PIVisitor extends AbstractJavaVisitor {

    /**
     * PIVisitor constructor.
     */
    public PIVisitor() {
        super();
    }

    /**
     * generateClass.
     * @param fsm FiniteStateMachine
     * @return String
     */
    @Override
    protected String generateClass(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer("public class ");
        buff.append(fsm.getName());
        buff.append(" extends DefaultDiscreteAgent{\n\n");
        buff.append(generateDataMembers(fsm));
        buff.append(generateConstructor(fsm));
        buff.append(generateMethods(fsm));
        buff.append("}\n");
        return buff.toString();
    }

    /**
     * generateConstructor.
     * @param fsm FiniteStateMachine
     * @return String
     */
    @Override
    protected String generateConstructor(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer("	public " + fsm.getName() + "(){\n");
        final Iterator<State> its = fsm.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            buff.append("		this." + s.getName() + "=new RandWState(\"" + s.getName() + "\");\n");
            if (s.isInitial()) {
                buff.append("		this.adapt(" + s.getName() + ");\n");
            }
        }
        final Iterator<Transition> itt = fsm.getTransitionFunction().iterator();
        for (int i = 0; itt.hasNext(); i++) {
            final Transition t = itt.next();
            buff.append("		this." + t.getInput().getName() + t.getOutput().getName() + "=new RandWTransition(this," + t.getInput().getName() + ",\"" + t.getEvent().getName() + "\","
                + t.getOutput().getName() + ");\n");
        }
        buff.append("}\n\n");
        return buff.toString();
    }

    /**
     * generateDataMembers.
     * @param fsm FiniteStateMachine
     * @return String
     */
    @Override
    protected String generateDataMembers(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer();
        final Iterator<State> its = fsm.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            buff.append("	private RandWState " + s.getName() + ";\n\n");
        }
        final Iterator<Transition> itt = fsm.getTransitionFunction().iterator();
        for (int i = 0; itt.hasNext(); i++) {
            final Transition t = itt.next();
            buff.append("	private RandWTransition " + t.getInput().getName() + t.getOutput().getName() + ";\n\n");
        }
        return buff.toString();
    }

    /**
     * generateImport.
     * @return String
     */
    @Override
    protected String generateImport() {
        return "";// "import pi.defaultAgent.DefaultDiscreteAgent;\n\n";
    }

    /**
     * generateMethods.
     * @param fsm FiniteStateMachine
     * @return String
     */
    @Override
    protected String generateMethods(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer();
        final Iterator<State> its = fsm.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            buff.append("	public RandWState get" + s.getName() + "() {\n");
            buff.append("		return " + s.getName() + ";}\n\n");
            buff.append("	public void set" + s.getName() + "(RandWState " + s.getName() + ") {\n");
            buff.append("		this." + s.getName() + " = " + s.getName() + ";}\n\n");
        }
        final Iterator<Transition> itt = fsm.getTransitionFunction().iterator();
        for (int i = 0; itt.hasNext(); i++) {
            final Transition t = itt.next();
            buff.append("	public RandWTransition get" + t.getInput().getName() + t.getOutput().getName() + "() {\n");
            buff.append("		return " + t.getInput().getName() + t.getOutput().getName() + ";}\n\n");
            buff.append("	public void set" + t.getInput().getName() + t.getOutput().getName() + "(RandWTransition " + t.getInput().getName() + t.getOutput().getName() + ") {\n");
            buff.append("		this." + t.getInput().getName() + t.getOutput().getName() + " = " + t.getInput().getName() + t.getOutput().getName() + ";}\n\n");
        }
        return buff.toString();
    }

}
