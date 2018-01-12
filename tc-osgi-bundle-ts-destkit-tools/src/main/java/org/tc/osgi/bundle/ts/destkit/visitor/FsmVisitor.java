package org.tc.osgi.bundle.ts.destkit.visitor;

import java.util.Iterator;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;

/**
 * JavaVisitor.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class FsmVisitor extends AbstractJavaVisitor {

    /**
     * generateClass.
     * @param fsm FiniteStateMachine
     * @return String
     */
    @Override
    protected String generateClass(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer("public class ");
        buff.append(fsm.getName());
        buff.append(" {\n\n");
        buff.append(generateMethods(fsm));
        buff.append("}\n");
        return buff.toString();
    }

    @Override
    protected String generateConstructor(final FiniteStateMachine fsm) {
        return "";
    }

    @Override
    protected String generateDataMembers(final FiniteStateMachine fsm) {
        return "";
    }

    /**
     * generateImport.
     * @return String
     */
    @Override
    protected String generateImport() {
        final StringBuffer buff = new StringBuffer();
        // buff.append("import destKit.metamodel.FiniteStateMachine;\n");
        // buff.append("import destKit.metamodel.core.Event;\n");
        // buff.append("import destKit.metamodel.core.State;\n\n");
        return buff.toString();
    }

    /**
     * generateMethods.
     * @param fsm FiniteStateMachine
     * @return
     */
    @Override
    protected String generateMethods(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer("public FiniteStateMachine getFsm(){\n");
        buff.append("FiniteStateMachine fsm=new FiniteStateMachine(\"" + fsm.getName() + "\");\n");
        final Iterator<State> its = fsm.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            buff.append("fsm.addState(new State(\"" + s.getName() + "\"," + s.isInitial() + "," + s.isMarked() + "));\n");
        }
        final Iterator<Event> ite = fsm.getAlphabet().iterator();
        for (int i = 0; ite.hasNext(); i++) {
            final Event e = ite.next();
            buff.append("fsm.addEvent(new Event(\"" + e.getName() + "\"," + e.isControllable() + "," + e.isObservable() + "));\n");
        }
        final Iterator<Transition> itt = fsm.getTransitionFunction().iterator();
        for (int i = 0; itt.hasNext(); i++) {
            final Transition t = itt.next();
            buff.append("fsm.addTransition(\"" + t.getInput().getName() + "\",\"" + t.getEvent().getName() + "\",\"" + t.getOutput().getName() + "\");\n");
        }
        buff.append("return fsm;\n");
        buff.append("}");
        return buff.toString();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "";
    }

}
