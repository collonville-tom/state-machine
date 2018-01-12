package org.tc.osgi.bundle.ts.destkit.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;

/**
 * ResultsAssociationTable.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
@SuppressWarnings("serial")
public class ResultsAssociationTable extends HashMap<FiniteStateMachine, HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>>> {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = 6788221476266099784L;

    /**
     * addAssociation.
     * @param clone FiniteStateMachine
     * @param finalState State
     * @param fsm FiniteStateMachine
     * @param subState State
     */
    public void addAssociation(final FiniteStateMachine clone, final State finalState, final FiniteStateMachine fsm, final State subState) {
        if (containsKey(clone)) {
            if (get(clone).containsKey(finalState.getName())) {
                if (get(clone).get(finalState.getName()).containsKey(fsm)) {
                    if (get(clone).get(finalState.getName()).get(fsm).contains(subState.getName())) {

                    } else {
                        get(clone).get(finalState.getName()).get(fsm).add(subState.getName());
                    }
                } else {
                    final ArrayList<String> l = new ArrayList<String>();
                    l.add(subState.getName());
                    get(clone).get(finalState.getName()).put(fsm, l);
                }
            } else {
                final ArrayList<String> l = new ArrayList<String>();
                l.add(subState.getName());
                final HashMap<FiniteStateMachine, ArrayList<String>> map = new HashMap<FiniteStateMachine, ArrayList<String>>();
                map.put(fsm, l);
                get(clone).put(finalState.getName(), map);
            }
        } else {
            final ArrayList<String> l = new ArrayList<String>();
            l.add(subState.getName());
            final HashMap<FiniteStateMachine, ArrayList<String>> map = new HashMap<FiniteStateMachine, ArrayList<String>>();
            map.put(fsm, l);
            final HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>> map2 = new HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>>();
            map2.put(finalState.getName(), map);
            put(clone, map2);
        }

    }

    /**
     * getStatesAssociation.
     * @param fsm FiniteStateMachine
     * @return HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>>
     */
    public HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>> getStatesAssociation(final FiniteStateMachine fsm) {
        if (containsKey(fsm)) {
            return get(fsm);
        }
        return null;
    }

    /**
     * getStatesAssociation.
     * @param fsm FiniteStateMachine
     * @param state String
     * @return HashMap<FiniteStateMachine, ArrayList<String>>
     */
    public HashMap<FiniteStateMachine, ArrayList<String>> getStatesAssociation(final FiniteStateMachine fsm, final String state) {
        if (this.getStatesAssociation(fsm) != null) {
            if (this.getStatesAssociation(fsm).containsKey(state)) {
                return this.getStatesAssociation(fsm).get(state);
            }
        }
        return null;
    }

    /**
     * getStatesAssociation.
     * @param fsm FiniteStateMachine
     * @param state String
     * @param fsm2 FiniteStateMachine
     * @return ArrayList<String>
     */
    public ArrayList<String> getStatesAssociation(final FiniteStateMachine fsm, final String state, final FiniteStateMachine fsm2) {
        if (this.getStatesAssociation(fsm, state) != null) {
            if (this.getStatesAssociation(fsm, state).containsKey(fsm2)) {
                return this.getStatesAssociation(fsm, state).get(fsm2);
            }
        }
        return null;
    }

    /**
     * @return String
     * @see java.util.AbstractMap#toString()
     */
    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer();

        for (final FiniteStateMachine fsm : keySet()) {
            buff.append(fsm.getName());
            buff.append(":\n");
            for (final String i : this.getStatesAssociation(fsm).keySet()) {
                buff.append("->");
                buff.append(i);
                buff.append(":\n");
                for (final FiniteStateMachine fsm2 : this.getStatesAssociation(fsm, i).keySet()) {
                    buff.append("-->");
                    buff.append(fsm2.getName());
                    buff.append(":\n");
                    for (final String i2 : this.getStatesAssociation(fsm, i, fsm2)) {
                        buff.append("--->");
                        buff.append(i2);
                        buff.append("\n");
                    }
                }
            }
        }
        return buff.toString();
    }

}
