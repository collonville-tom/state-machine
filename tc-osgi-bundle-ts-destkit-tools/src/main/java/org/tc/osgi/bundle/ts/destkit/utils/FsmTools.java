package org.tc.osgi.bundle.ts.destkit.utils;

import java.util.ArrayList;
import java.util.HashMap;

import org.tc.osgi.bundle.ts.destkit.metamodel.Alphabet;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.StatesSet;
import org.tc.osgi.bundle.ts.destkit.metamodel.TransitionsSet;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * FsmTools.java.
 * @author collonville thomas
 * @version 0.0.1
 */
public class FsmTools {

    /**
     * FsmTools tools.
     */
    private static FsmTools tools = null;

    /**
     * getInstance.
     * @return FsmTools
     */
    public static FsmTools getInstance() {
        if (FsmTools.tools == null) {
            FsmTools.tools = new FsmTools();
        }
        return FsmTools.tools;
    }

    public ResultsAssociationTable table = new ResultsAssociationTable();

    /**
     * FsmTools constructor.
     */
    private FsmTools() {
        super();
    }

    public FiniteStateMachine accessiblePart(final FiniteStateMachine fsm) {
        final FiniteStateMachine clone = fsm.clone();
        clone.setName("Ac_" + clone.getName());

        // on cherche les etats valides apartir des etats initiaux
        final StatesSet statesSetValid = clone.getInitialStateSet();
        final StatesSet tmp = new StatesSet();
        int invariant = 0;
        while (statesSetValid.size() != invariant) {
            invariant = statesSetValid.size();
            for (final State s : statesSetValid) {
                for (final Transition t : clone.getTransitionsWhere(s)) {
                    if (t.getInput().equals(s)) {
                        tmp.addState(t.getOutput());
                    }
                }
            }
            statesSetValid.addAll(tmp);
        }

        // on inverse la selection afin de determiner les mauvais etats
        final StatesSet badStatesSet = clone.getCloneStatesSet();
        for (final State stateValid : statesSetValid) {
            badStatesSet.removeState(stateValid);
        }

        // enfin on supprime ces etats mauvais ainsi que les transitions
        // associ�s.
        for (final State s : badStatesSet) {
            clone.removeState(s);
        }
        for (final State s : clone.getCloneStatesSet()) {
            table.addAssociation(clone, s, fsm, fsm.getCloneState(s.getName()));
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("accessiblePart of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine coAccessiblePart(final FiniteStateMachine fsm) {
        final FiniteStateMachine clone = fsm.clone();
        clone.setName("CoAc_" + clone.getName());

        // on cherche les etats valides apartir des etats initiaux
        final StatesSet statesSetValid = clone.getMarkedStateSet();
        final StatesSet tmp = new StatesSet();
        int invariant = 0;
        while (statesSetValid.size() != invariant) {
            invariant = statesSetValid.size();
            for (final State s : clone.getStatesSet()) {
                for (final Transition t : clone.getTransitionsWhere(s)) {
                    final State so = t.getOutput();
                    if (statesSetValid.containsState(so)) {
                        tmp.addState(s);
                    }
                }
            }
            statesSetValid.addAll(tmp);
        }

        // on inverse la selection afin de determiner les mauvais etats
        final StatesSet badStatesSet = clone.getCloneStatesSet();
        for (final State validStates : statesSetValid) {
            badStatesSet.removeState(validStates);
        }

        // enfin on supprime ces etats mauvais ainsi que les transitions
        // associ�s.
        for (final State s : badStatesSet) {
            clone.removeState(s);
        }
        for (final State s : clone.getCloneStatesSet()) {
            table.addAssociation(clone, s, fsm, fsm.getCloneState(s.getName()));
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("coAccessiblePart of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine complement(final FiniteStateMachine fsm) {
        final FiniteStateMachine clone = FsmTools.getInstance().trim(fsm.clone());
        clone.setName("Comp_" + clone.getName());

        final State xd = new State("xd", State.NOTINITIAL, State.MARKED);
        clone.addState(xd);

        for (final State s : clone.getStatesSet()) {
            for (final Event e : clone.getCloneAlphabet()) {
                if (!clone.getPossibleEvents(s).containsEvent(e)) {
                    ;
                }
                clone.addTransition(new Transition(s, e, xd));
            }
            if (!s.equals(xd)) {
                if (s.isMarked()) {
                    s.setMarked(State.NOTMARKED);
                } else {
                    s.setMarked(State.MARKED);
                }
            }

        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("complement of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine deterministic(final FiniteStateMachine fsm) {
        final FiniteStateMachine clone = new FiniteStateMachine("Det_" + fsm.getName());

        String name = "";
        final String delimiter = "_";
        boolean isMarked = State.NOTMARKED;
        final ArrayList<State> ltmp = new ArrayList<State>();
        for (final State s : fsm.getInitialStateSet()) {
            if (name == "") {
                name = s.getName();
            } else {
                name = name + delimiter + s.getName();
            }
            ltmp.add(s);
            if (s.isMarked()) {
                isMarked = State.MARKED;
            }
        }
        NameBuilder builder = new NameBuilder(name, delimiter);
        State newState = new State(builder.getName(), State.INITIAL, isMarked);
        clone.addState(newState);
        for (final Event e : fsm.getCloneAlphabet()) {
            clone.addEvent(e);
        }
        int invariant = 0;
        while (invariant != clone.getCloneStatesSet().size()) {
            invariant = clone.getCloneStatesSet().size();
            for (final State s : clone.getCloneStatesSet()) {
                for (final Event event : clone.getCloneAlphabet()) {
                    builder = new NameBuilder(s.getName(), delimiter);
                    final TransitionsSet transitions = new TransitionsSet();
                    for (final String str : builder.getDeComposeName()) {
                        final State state = fsm.getCloneState(str);
                        if (state.getPossibleEvents().containsEvent(event)) {
                            transitions.addAll(Collections.getInstance().select(fsm.getTransitionsWhere(state), new IPredicate<Transition>() {

                                @Override
                                public boolean evaluate(final Transition t) {
                                    if (t.getEvent().equals(event) && t.getInput().equals(state)) {
                                        return true;
                                    }
                                    return false;
                                }
                            }));

                        }
                    }
                    name = "";
                    isMarked = State.NOTMARKED;
                    boolean isInitial = State.INITIAL;
                    final ArrayList<State> ltmp2 = new ArrayList<State>();
                    for (final Transition t : transitions) {
                        if (name == "") {
                            name = t.getOutput().getName();
                        } else {
                            name = name + delimiter + t.getOutput().getName();
                        }
                        ltmp2.add(t.getOutput());
                        if (t.getOutput().isMarked()) {
                            isMarked = State.MARKED;
                        }
                        if (!t.getOutput().isInitial()) {
                            isInitial = State.NOTINITIAL;
                        }
                    }
                    if (name != "") {
                        builder = new NameBuilder(name, delimiter);
                        newState = new State(builder.getName(), isInitial, isMarked);
                        if (!clone.getInitialStateSet().containsState(newState)) {
                            newState.setInitial(State.NOTINITIAL);
                        }
                        clone.addState(newState);
                        clone.addTransition(new Transition(s, event, newState));
                    }
                }
            }
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("deterministic of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine freeProduct(final FiniteStateMachine fsmL, final FiniteStateMachine fsmR) {

        final FiniteStateMachine clone = new FiniteStateMachine(fsmL.getName() + "_FreeX_" + fsmR.getName());
        for (final Event e : fsmL.getCloneAlphabet()) {
            clone.addEvent(e);
        }
        for (final Event e : fsmR.getCloneAlphabet()) {
            clone.addEvent(e);
        }

        for (final Transition t : fsmL.getCloneTransitionFunction()) {
            for (final State s : fsmR.getCloneStatesSet()) {
                String nameState1 = "";
                final State s1i = t.getInput();
                nameState1 = s.getName() + s1i.getName();
                final State stateInput = new State(nameState1, s1i.isInitial() && s.isInitial(), s1i.isMarked() || s.isMarked());
                String nameState2 = "";
                final State s1o = t.getOutput();
                nameState2 = s.getName() + s1o.getName();
                final State stateOutput = new State(nameState2, s1o.isInitial() && s.isInitial(), s1o.isMarked() || s.isMarked());
                clone.addTransition(new Transition(stateInput, t.getEvent(), stateOutput));
                table.addAssociation(clone, stateInput, fsmL, t.getInput());
                table.addAssociation(clone, stateInput, fsmR, s);
                table.addAssociation(clone, stateOutput, fsmL, t.getOutput());
                table.addAssociation(clone, stateOutput, fsmR, s);
            }
        }

        for (final Transition t : fsmR.getCloneTransitionFunction()) {
            for (final State s : fsmL.getCloneStatesSet()) {
                String nameState1 = "";
                final State s1i = t.getInput();
                nameState1 = s1i.getName() + s.getName();
                final State stateInput = new State(nameState1, s1i.isInitial() && s.isInitial(), s1i.isMarked() || s.isMarked());
                String nameState2 = "";
                final State s1o = t.getOutput();
                nameState2 = s1o.getName() + s.getName();
                final State stateOutput = new State(nameState2, s1o.isInitial() && s.isInitial(), s1o.isMarked() || s.isMarked());
                clone.addTransition(new Transition(stateInput, t.getEvent(), stateOutput));
                table.addAssociation(clone, stateInput, fsmR, t.getInput());
                table.addAssociation(clone, stateInput, fsmL, s);
                table.addAssociation(clone, stateOutput, fsmR, t.getOutput());
                table.addAssociation(clone, stateOutput, fsmL, s);
            }
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("freeProduct of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine inverseProjection(final FiniteStateMachine fsmL, final Alphabet alp) {

        final FiniteStateMachine clone = fsmL.clone();
        clone.setName("InvProj_" + clone.getName());

        for (final Event e : alp.clone()) {
            if (!clone.getCloneAlphabet().containsEvent(e)) {

                for (final State s : clone.getCloneStatesSet()) {
                    clone.getTransitionFunction().addTransition(new Transition(s, e, s));
                }

            }
        }
        for (final State s : clone.getCloneStatesSet()) {
            table.addAssociation(clone, s, fsmL, fsmL.getCloneState(s.getName()));
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("inverseProjection of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine minimise(final FiniteStateMachine fsm) {
        final FiniteStateMachine clone = fsm.clone();
        clone.setName("Min_" + clone.getName());

        int invariant = 0;
        while (invariant != clone.getCloneStatesSet().size()) {
            invariant = clone.getCloneStatesSet().size();
            for (final State s1 : clone.getCloneStatesSet()) {
                boolean isSame = false;
                for (final State s2 : clone.getCloneStatesSet()) {

                    if ((s1.getPossibleEvents().size() == s2.getPossibleEvents().size()) && (s1.isMarked() == s2.isMarked()) && (!s1.equals(s2))) {

                        for (final Transition t : clone.getTransitionsWhere(s1)) {
                            if (t.getInput().equals(s1)) {
                                final Transition trans = Collections.getInstance().extract(clone.getTransitionsWhere(s2), new IPredicate<Transition>() {

                                    @Override
                                    public boolean evaluate(final Transition e) {
                                        if (e.getEvent().equals(t.getEvent()) && e.getOutput().equals(t.getOutput())) {
                                            return true;
                                        }
                                        return false;
                                    }
                                });
                                if (trans == null) {
                                    isSame = false;
                                    break;
                                } else {
                                    isSame = true;
                                }

                            }
                        }
                        if (isSame) {
                            String name = "";
                            if (s1.getName().compareTo(s2.getName()) < 0) {
                                name = s2.getName() + s1.getName();
                            } else {
                                name = s1.getName() + s2.getName();
                            }
                            final State newState = new State(name, s1.isInitial() || s2.isInitial(), s1.isMarked());
                            clone.addState(newState);
                            for (final Transition t : clone.getTransitionsWhere(s1)) {
                                Transition transition = null;
                                if (t.getInput().equals(s1)) {
                                    transition = new Transition(newState, t.getEvent(), t.getOutput());
                                }
                                if (t.getOutput().equals(s1)) {
                                    transition = new Transition(t.getInput(), t.getEvent(), newState);
                                }
                                if (t.getInput().equals(s1) && t.getOutput().equals(s1)) {
                                    transition = new Transition(newState, t.getEvent(), newState);
                                }
                                if (transition != null) {
                                    clone.addTransition(transition);
                                }
                            }
                            for (final Transition t : clone.getTransitionsWhere(s2)) {
                                Transition transition = null;
                                if (t.getInput().equals(s2)) {
                                    transition = new Transition(newState, t.getEvent(), t.getOutput());
                                }
                                if (t.getOutput().equals(s2)) {
                                    transition = new Transition(t.getInput(), t.getEvent(), newState);
                                }
                                if (t.getInput().equals(s2) && t.getOutput().equals(s2)) {
                                    transition = new Transition(newState, t.getEvent(), newState);
                                }
                                if (transition != null) {
                                    clone.addTransition(transition);
                                }
                            }
                            clone.removeState(s1);
                            clone.removeState(s2);
                            break;
                        }

                    }
                    if (isSame) {
                        break;
                    }

                }
            }
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("minimise of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine prefClosure(final FiniteStateMachine fsm) {
        final FiniteStateMachine fsmClone = fsm.clone();
        if (fsmClone.getMarkedStateSet().size() == fsmClone.getCloneStatesSet().size()) {
            for (final State s : fsmClone.getCloneStatesSet()) {
                table.addAssociation(fsmClone, s, fsm, fsm.getCloneState(s.getName()));
            }
            LoggerGestionnary.getInstance(FsmTools.class).debug("prefClosure of " + fsmClone.getName() + " realized");
            return fsmClone;
        } else {
            fsmClone.setName("PrefClose_" + fsmClone.getName());
            for (final State s : fsmClone.getStatesSet()) {
                s.setMarked(true);
            }
            for (final State s : fsmClone.getCloneStatesSet()) {
                table.addAssociation(fsmClone, s, fsm, fsm.getCloneState(s.getName()));
            }
            LoggerGestionnary.getInstance(FsmTools.class).debug("prefClosure of " + fsmClone.getName() + " realized");
            return fsmClone;
        }
    }

    public FiniteStateMachine product(final FiniteStateMachine fsmL, final FiniteStateMachine fsmR) {
        final FiniteStateMachine clone = new FiniteStateMachine(fsmL.getName() + "_X_" + fsmR.getName());

        for (final Event e : fsmL.getCloneAlphabet()) {
            for (final Event e2 : fsmR.getCloneAlphabet()) {
                if (e.equals(e2)) {
                    clone.addEvent(e);
                }
            }
        }

        for (final Transition t1 : fsmL.getCloneTransitionFunction()) {
            for (final Transition t2 : fsmR.getCloneTransitionFunction()) {
                if (t1.getEvent().equals(t2.getEvent())) {
                    String nameState1 = "";
                    final State s1i = t1.getInput();
                    final State s2i = t2.getInput();
                    nameState1 = s2i.getName() + s1i.getName();
                    final State stateInput = new State(nameState1, s1i.isInitial() && s2i.isInitial(), s1i.isMarked() && s2i.isMarked());

                    String nameState2 = "";
                    final State s1o = t1.getOutput();
                    final State s2o = t2.getOutput();
                    nameState2 = s2o.getName() + s1o.getName();
                    final State stateOutput = new State(nameState2, s1o.isInitial() && s2o.isInitial(), s1o.isMarked() && s2o.isMarked());

                    clone.addTransition(new Transition(stateInput, t1.getEvent(), stateOutput));

                    table.addAssociation(clone, clone.getCloneState(nameState1), fsmL, s1i);
                    table.addAssociation(clone, clone.getCloneState(nameState1), fsmR, s2i);

                    table.addAssociation(clone, clone.getCloneState(nameState2), fsmL, s1o);
                    table.addAssociation(clone, clone.getCloneState(nameState2), fsmR, s2o);
                }
            }
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("product of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine projection(final FiniteStateMachine fsmL, final Alphabet fsmR) {

        final FiniteStateMachine clone = fsmL.clone();
        clone.setName("Proj_" + clone.getName());
        for (final Event e : clone.getCloneAlphabet()) {
            if (!fsmR.containsEvent(e)) {
                int invariant = 0;
                while (clone.getMarkedStateSet().size() != invariant) {
                    invariant = clone.getMarkedStateSet().size();
                    for (final State s : clone.getMarkedStateSet()) {
                        for (final Transition t : clone.getTransitionFunction().getTransitionsWhere(s)) {
                            if (t.getOutput().equals(s) && t.getEvent().equals(e)) {
                                t.getInput().setMarked(true);
                            }
                        }
                    }
                }
                final TransitionsSet transitionToRemove = new TransitionsSet();
                final TransitionsSet transitionToAdd = new TransitionsSet();
                for (final Transition t : clone.getTransitionFunction()) {
                    if (t.getEvent().equals(e)) {
                        transitionToRemove.addTransition(t);
                        for (final Transition t2 : clone.getTransitionsWhere(t.getOutput())) {
                            if (t2.getInput().equals(t.getOutput()) && fsmR.containsEvent(t2.getEvent())) {
                                transitionToAdd.addTransition(new Transition(t.getInput(), t2.getEvent(), t2.getOutput()));
                            }
                        }
                    }
                }
                for (final Transition t : transitionToRemove) {
                    clone.removeTransition(t);
                }
                for (final Transition t : transitionToAdd) {
                    clone.addTransition(t);
                }
                clone.getAlphabet().removeEvent(e);

            }
        }
        for (final State s : clone.getCloneStatesSet()) {
            table.addAssociation(clone, s, fsmL, fsmL.getCloneState(s.getName()));
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("projection of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine supremalControllable(final FiniteStateMachine specification, final FiniteStateMachine system) { // a
        // finir
        // pour
        // la
        // table
        // d'association
        final FiniteStateMachine clone = FsmTools.getInstance().trim(FsmTools.getInstance().product(specification, system));
        clone.setName("SupC_" + clone.getName());

        final StatesSet badStatesSet = new StatesSet();
        for (final State s : clone.getCloneStatesSet()) {
            final Alphabet stateAlphabet = s.getPossibleEvents();
            final HashMap<FiniteStateMachine, ArrayList<String>> map = table.getStatesAssociation(clone, s.getName());
            for (final FiniteStateMachine fsm : map.keySet()) {
                final ArrayList<String> l = table.getStatesAssociation(fsm, s.getName(), system);
                if (l != null) {
                    for (final String si : l) {
                        final Alphabet events = system.getCloneState(si).getPossibleEvents();
                        for (final Event e : events) {
                            if (!e.isControllable() && !stateAlphabet.containsEvent(e)) {
                                badStatesSet.addState(s);
                            }
                        }
                    }
                }
            }
        }
        int index = 0;
        while (index != badStatesSet.size()) {
            index = badStatesSet.size();
            for (final State s : badStatesSet) {
                final TransitionsSet ts = clone.getTransitionsWhere(s);
                for (final Transition t : ts) {
                    if (t.getOutput().equals(s) && !t.getEvent().isControllable()) {
                        // etre
                        // ajouter
                        // le
                        // fait
                        // que
                        // l'etat
                        // doit
                        // etre
                        // marqu�
                        // sinon
                        // deadlock
                        // eventuel
                        // non?
                        // ou
                        // alors
                        // fair
                        // un
                        // trim
                        // a
                        // la
                        // fin
                        badStatesSet.addState(s);
                    }
                }
                if (index != badStatesSet.size()) {
                    break;
                }
            }
        }
        for (final State s : badStatesSet) {
            clone.removeState(s);
        }
        /*
         * for(State s:clone.getCloneStatesSet()) { for(String
         * s2:table.getStatesAssociation(clone,s.getName(),specification))
         * table.
         * addAssociation(clone,s,specification,specification.getCloneState
         * (s2)); }
         */

        LoggerGestionnary.getInstance(FsmTools.class).debug("Supremal Controlable of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine supremalNormal(final FiniteStateMachine specification, final FiniteStateMachine system) { // a
        // finir
        // pour
        // la
        // table
        // d'association
        // et
        // a
        // corriger
        // ne
        // correspond
        // a
        // l'alogo
        // qu'il
        // faut.
        // pas
        // tout
        // a
        // fait
        // du
        // moins
        // marche
        // a
        // moitier
        // voir
        // exemple
        // main2
        final FiniteStateMachine specClone = specification.clone();
        final FiniteStateMachine systemClone = system.clone();
        for (final Event e : specClone.getAlphabet()) {
            if (!e.isObservable()) {
                e.setControllable(false);
            }
        }
        for (final Event e : systemClone.getAlphabet()) {
            if (!e.isObservable()) {
                e.setControllable(false);
            }
        }
        final FiniteStateMachine clone = FsmTools.getInstance().supremalControllable(specClone, systemClone);
        clone.setName("SupN_" + clone.getName());
        LoggerGestionnary.getInstance(FsmTools.class).debug("Supremal Normal of " + clone.getName() + " realized");
        return clone;
    }

    public FiniteStateMachine syncProduct(final FiniteStateMachine fsmL, final FiniteStateMachine fsmR) {
        final FiniteStateMachine inv1 = FsmTools.getInstance().inverseProjection(fsmL, fsmR.getAlphabet());
        final FiniteStateMachine inv2 = FsmTools.getInstance().inverseProjection(fsmR, fsmL.getAlphabet());
        final FiniteStateMachine prod = FsmTools.getInstance().product(inv1, inv2);

        for (final State s : prod.getCloneStatesSet()) {
            HashMap<String, HashMap<FiniteStateMachine, ArrayList<String>>> map = table.getStatesAssociation(inv1);
            for (final String str : map.keySet()) {
                for (final String stmp : table.getStatesAssociation(inv1, str, fsmL)) {
                    table.addAssociation(prod, s, fsmL, fsmL.getCloneState(stmp));
                }
            }
            map = table.getStatesAssociation(inv2);
            for (final String str : map.keySet()) {
                for (final String stmp : table.getStatesAssociation(inv2, str, fsmR)) {
                    table.addAssociation(prod, s, fsmR, fsmR.getCloneState(stmp));
                }
            }
        }

        prod.setName(fsmL.getName() + "_XSync_" + fsmR.getName());
        LoggerGestionnary.getInstance(FsmTools.class).debug("	->syncProduct of " + prod.getName() + " realized");
        return prod;

    }

    public FiniteStateMachine trim(final FiniteStateMachine fsm) {
        final FiniteStateMachine fsmResult = FsmTools.getInstance().coAccessiblePart(FsmTools.getInstance().accessiblePart(fsm.clone()));
        fsmResult.setName("Trim_" + fsm.getName());
        for (final State s : fsmResult.getCloneStatesSet()) {
            table.addAssociation(fsmResult, s, fsm, fsm.getCloneState(s.getName()));
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("	->trim of " + fsmResult.getName() + " realized");
        return fsmResult;
    }

    public FiniteStateMachine union(final FiniteStateMachine fsmL, final FiniteStateMachine fsmR) {

        final FiniteStateMachine clone = fsmL.clone();
        clone.setName(fsmL.getName() + "_U_" + fsmR.getName());

        for (final Event e : fsmR.getCloneAlphabet()) {
            clone.addEvent(e);
        }
        for (final State s : fsmR.getStatesSet()) {
            clone.addState(s);
        }

        for (final Transition t : fsmR.getTransitionFunction()) {
            clone.addTransition(t);
        }

        String name = "";
        boolean isMarked = State.NOTMARKED;
        final TransitionsSet transitionsToRemove = new TransitionsSet();
        final ArrayList<State> ltmp = new ArrayList<State>();
        for (final State s : clone.getInitialStateSet()) {
            if (s.getName().compareTo(name) < 0) {
                name = name + s.getName();
            }
            if (s.getName().compareTo(name) > 0) {
                name = s.getName() + name;
            }
            if (s.isMarked()) {
                isMarked = State.MARKED;
            }
            ltmp.add(s);
            transitionsToRemove.addAll(clone.getTransitionsWhere(s));
            clone.removeState(s);
        }
        final State newState = new State(name, State.INITIAL, isMarked);
        for (final State s : ltmp) {
            if (fsmR.contains(s)) {
                table.addAssociation(clone, s, fsmR, fsmR.getCloneState(s.getName()));
            }
            if (fsmL.contains(s)) {
                table.addAssociation(clone, s, fsmL, fsmL.getCloneState(s.getName()));
            }
        }
        for (final Transition t : transitionsToRemove) {
            Transition transition = null;
            if (t.getInput().isInitial()) {
                transition = new Transition(newState, t.getEvent(), t.getOutput());
            }
            if (t.getOutput().isInitial()) {
                transition = new Transition(t.getInput(), t.getEvent(), newState);
            }
            if (t.getInput().isInitial() && t.getOutput().isInitial()) {
                transition = new Transition(newState, t.getEvent(), newState);
            }
            if (transition != null) {
                clone.addTransition(transition);
            }
        }
        for (final State s : clone.getCloneStatesSet()) {
            if (fsmL.contains(s)) {
                table.addAssociation(clone, s, fsmL, fsmL.getCloneState(s.getName()));
            }
        }
        for (final State s : clone.getCloneStatesSet()) {
            if (fsmR.contains(s)) {
                table.addAssociation(clone, s, fsmR, fsmR.getCloneState(s.getName()));
            }
        }
        LoggerGestionnary.getInstance(FsmTools.class).debug("union of " + clone.getName() + " realized");
        return clone;
    }
}
