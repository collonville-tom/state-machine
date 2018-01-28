package org.tc.osgi.bundle.ts.destkit;

import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.metamodel.Alphabet;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.FsmTools;
import org.tc.osgi.bundle.ts.destkit.visitor.GraphvizVisitor;
import org.tc.osgi.bundle.ts.destkit.visitor.PIVisitor;
import org.tc.osgi.bundle.ts.m3.module.service.UtilsServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;

public class CatMouseTest {

    @Test
    public void automateAB_AC() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final State x = new State("x", State.INITIAL, State.NOTMARKED);
        final State y = new State("y", State.NOTINITIAL, State.MARKED);
        final State z = new State("z", State.NOTINITIAL, State.NOTMARKED);
        final Event a = new Event("a", Event.CONTROLLABLE, Event.OBSERVABLE);
        final Event b = new Event("b", Event.UNCONTROLLABLE, Event.OBSERVABLE);
        final Event c = new Event("c", Event.CONTROLLABLE, Event.UNOBSERVABLE);
        final Event d = new Event("d", Event.UNCONTROLLABLE, Event.UNOBSERVABLE);

        final FiniteStateMachine fsmabd = new FiniteStateMachine("abd");
        final FiniteStateMachine fsmac = new FiniteStateMachine("ac");

        fsmabd.addState(x);
        fsmabd.addState(y);
        fsmabd.addState(z);
        fsmabd.addEvent(a);
        fsmabd.addEvent(b);
        fsmabd.addEvent(d);
        fsmabd.addTransition("x", "a", "y");
        fsmabd.addTransition("y", "b", "z");
        fsmabd.addTransition("z", "d", "x");

        fsmac.addState(x);
        fsmac.addState(y);
        fsmac.addEvent(a);
        fsmac.addEvent(c);
        fsmac.addTransition("x", "a", "y");
        fsmac.addTransition("y", "c", "x");

        fsmabd.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        fsmac.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine clone = FsmTools.getInstance().syncProduct(fsmabd, fsmac);
        clone.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

    }

    @Test
    public void catAndMouseTest1() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        // Definition des pieces du chat
        final State catRoom0 = new State("C0", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom1 = new State("C1", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom2 = new State("C2", State.INITIAL, State.MARKED);
        final State catRoom3 = new State("C3", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom4 = new State("C4", State.NOTINITIAL, State.NOTMARKED);

        // Definition des pieces de la souris
        final State mouseRoom0 = new State("M0", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom1 = new State("M1", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom2 = new State("M2", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom3 = new State("M3", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);

        final FiniteStateMachine cat = new FiniteStateMachine("cat");
        cat.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        cat.addState(catRoom0);
        cat.addState(catRoom1);
        cat.addState(catRoom2);
        cat.addState(catRoom3);
        cat.addState(catRoom4);

        final FiniteStateMachine mouse = new FiniteStateMachine("mouse");
        mouse.addState(mouseRoom0);
        mouse.addState(mouseRoom1);
        mouse.addState(mouseRoom2);
        mouse.addState(mouseRoom3);
        mouse.addState(mouseRoom4);

        cat.addTransition("C0", "c1", "C1");
        cat.addTransition("C1", "c2", "C2");
        cat.addTransition("C2", "c3", "C0");
        cat.addTransition("C0", "c4", "C3");
        cat.addTransition("C3", "c5", "C4");
        cat.addTransition("C4", "c6", "C0");
        cat.addTransition("C3", "c7", "C1");
        cat.addTransition("C1", "c7", "C3");

        mouse.addTransition("M0", "m1", "M2");
        mouse.addTransition("M2", "m2", "M1");
        mouse.addTransition("M1", "m3", "M0");
        mouse.addTransition("M0", "m4", "M4");
        mouse.addTransition("M4", "m5", "M3");
        mouse.addTransition("M3", "m6", "M0");

        cat.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        // approche modulaire sans consideration de controllabilit� (meme si c7
        // est indique comme tel)
        // pas dans la salle 0
        final FiniteStateMachine spec0 = new FiniteStateMachine("spec0");
        spec0.addState(new State("0", State.INITIAL, State.MARKED));
        spec0.addState(new State("1", State.NOTINITIAL, State.MARKED));
        spec0.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec0.addTransition("0", "c3", "1");
        spec0.addTransition("0", "c6", "1");
        spec0.addTransition("0", "m6", "1");
        spec0.addTransition("0", "m3", "1");
        spec0.addTransition("1", "c1", "0");
        spec0.addTransition("1", "c4", "0");
        spec0.addTransition("1", "m1", "0");
        spec0.addTransition("1", "m4", "0");
        spec0.addTransition("1", "c3", "bad");
        spec0.addTransition("1", "c6", "bad");
        spec0.addTransition("1", "m6", "bad");
        spec0.addTransition("1", "m3", "bad");
        // spec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 3
        final FiniteStateMachine spec1 = new FiniteStateMachine("spec1");
        spec1.addState(new State("0", State.INITIAL, State.MARKED));
        spec1.addState(new State("1", State.NOTINITIAL, State.MARKED));
        spec1.addState(new State("2", State.NOTINITIAL, State.MARKED));
        spec1.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec1.addEvent(new Event("c7", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        spec1.addTransition("0", "m5", "1");
        spec1.addTransition("1", "c4", "bad");
        spec1.addTransition("1", "c7", "bad");
        spec1.addTransition("1", "m6", "0");
        spec1.addTransition("0", "c7", "2");
        spec1.addTransition("0", "c4", "2");
        spec1.addTransition("2", "m5", "bad");
        spec1.addTransition("2", "c7", "0");
        spec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 2
        final FiniteStateMachine spec3 = new FiniteStateMachine("spec3");
        spec3.addState(new State("0", State.INITIAL, State.MARKED));
        spec3.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec3.addTransition("0", "m1", "bad");
        spec3.addTransition("0", "c5", "bad");
        spec3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine finalSpec0 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec0, system));
        finalSpec0.setName("fspec");
        finalSpec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor0 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec0, system));
        supervisor0.setName("supervisor0");
        supervisor0.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // /
        final FiniteStateMachine finalSpec1 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec1, system));
        finalSpec1.setName("fspec1");
        finalSpec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor1 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec1, system));
        supervisor1.setName("supervisor1");
        supervisor1.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // /
        final FiniteStateMachine finalSpec3 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec3, system));
        finalSpec3.setName("fspec3");
        finalSpec3.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine supervisor3 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec3, system));
        supervisor3.setName("supervisor3");
        supervisor3.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // // combinaison des supeviseurs et de leur comportement
        final FiniteStateMachine finalSupervisorNC = FsmTools.getInstance().trim(FsmTools.getInstance().product(FsmTools.getInstance().product(supervisor1, supervisor3), supervisor0));
        finalSupervisorNC.setName("finalSupervisorNC");
        finalSupervisorNC.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine closeLoopNC = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSupervisorNC, system));
        closeLoopNC.setName("closeLoopNC");
        closeLoopNC.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // controlable supremal
        final FiniteStateMachine finalSupervisor = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(finalSupervisorNC, system));
        finalSupervisor.setName("finalSupervisor");
        finalSupervisor.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSupervisor, system));
        closeLoop.setName("closeLoop");
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));

    }

    public void catAndMouseTestApprocheDecentralise() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        // Definition des pieces du chat
        final State catRoom0 = new State("C0", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom1 = new State("C1", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom2 = new State("C2", State.INITIAL, State.MARKED);
        final State catRoom3 = new State("C3", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom4 = new State("C4", State.NOTINITIAL, State.NOTMARKED);

        // Definition des pieces de la souris
        final State mouseRoom0 = new State("M0", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom1 = new State("M1", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom2 = new State("M2", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom3 = new State("M3", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);

        final FiniteStateMachine cat = new FiniteStateMachine("cat");
        cat.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        cat.addState(catRoom0);
        cat.addState(catRoom1);
        cat.addState(catRoom2);
        cat.addState(catRoom3);
        cat.addState(catRoom4);

        final FiniteStateMachine mouse = new FiniteStateMachine("mouse");
        mouse.addState(mouseRoom0);
        mouse.addState(mouseRoom1);
        mouse.addState(mouseRoom2);
        mouse.addState(mouseRoom3);
        mouse.addState(mouseRoom4);

        cat.addTransition("C0", "c1", "C1");
        cat.addTransition("C1", "c2", "C2");
        cat.addTransition("C2", "c3", "C0");
        cat.addTransition("C0", "c4", "C3");
        cat.addTransition("C3", "c5", "C4");
        cat.addTransition("C4", "c6", "C0");
        cat.addTransition("C3", "c7", "C1");
        cat.addTransition("C1", "c7", "C3");

        mouse.addTransition("M0", "m1", "M2");
        mouse.addTransition("M2", "m2", "M1");
        mouse.addTransition("M1", "m3", "M0");
        mouse.addTransition("M0", "m4", "M4");
        mouse.addTransition("M4", "m5", "M3");
        mouse.addTransition("M3", "m6", "M0");

        // cat.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        // mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        // system.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine ssystem0 = FsmTools.getInstance().deterministic(FsmTools.getInstance().projection(system, cat.getCloneAlphabet()));
        ssystem0.setName("G0");
        // System.out.println(ssystem0.toString());
        // ssystem0.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine ssystem1 = FsmTools.getInstance().deterministic(FsmTools.getInstance().projection(system, mouse.getCloneAlphabet()));
        ssystem1.setName("G1");
        // System.out.println(ssystem1.toString());
        // ssystem1.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final Alphabet alph = system.getCloneAlphabet();
        alph.removeEvent(new Event("m1", Event.CONTROLLABLE, Event.OBSERVABLE));
        alph.removeEvent(new Event("m3", Event.CONTROLLABLE, Event.OBSERVABLE));
        alph.removeEvent(new Event("m2", Event.CONTROLLABLE, Event.OBSERVABLE));
        alph.removeEvent(new Event("c5", Event.CONTROLLABLE, Event.OBSERVABLE));
        alph.removeEvent(new Event("c6", Event.CONTROLLABLE, Event.OBSERVABLE));

        final FiniteStateMachine ssystem2 = FsmTools.getInstance().deterministic(FsmTools.getInstance().projection(system, alph));
        ssystem2.setName("G2");
        // System.out.println(ssystem2.toString());
        // ssystem2.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        FiniteStateMachine spec0 = ssystem0.clone();
        spec0.removeTransition("M4C3_M3C3_M2C3_M0C3", "c5", "M4C4_M3C4_M2C4_M1C4_M0C4");
        spec0.removeTransition("M4C3_M3C3_M2C3_M1C3_M0C3", "c5", "M4C4_M3C4_M2C4_M1C4_M0C4");
        spec0 = FsmTools.getInstance().trim(spec0);
        spec0.setName("spec0");
        // spec0.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        FiniteStateMachine spec1 = ssystem1.clone();
        spec1.removeTransition("M0C4_M0C3_M0C2_M0C1_M0C0", "m1", "M2C4_M2C3_M2C2_M2C1_M2C0");
        spec1 = FsmTools.getInstance().trim(spec1);
        spec1.setName("spec1");
        // spec1.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        FiniteStateMachine spec2 = ssystem2.clone();
        spec2.removeState("M2C1_M1C1_M0C1");
        spec2.removeState("M2C2_M1C2_M0C2");
        spec2.removeState("M3C3");
        spec2.removeState("M2C2_M0C2");
        spec2.removeState("M2C0_M0C0");
        spec2.removeState("M3C4_M3C3");
        spec2.removeState("M0C4_M0C3_M0C0");
        spec2.removeState("M0C0");
        spec2.removeState("M4C4_M4C3");
        spec2.removeState("M2C0_M1C0_M0C0");
        spec2.removeState("M4C4_M4C3_M4C0");
        spec2.removeState("M3C4_M3C3_M3C0");
        spec2 = FsmTools.getInstance().trim(spec2);
        spec2.setName("spec2");
        // spec2.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine supervisor0 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec0, ssystem0));
        supervisor0.setName("S0");
        // supervisor0.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine supervisor1 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec1, ssystem1));
        supervisor1.setName("S1");
        // supervisor1.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine supervisor2 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec2, ssystem2));
        supervisor2.setName("S2");
        // supervisor2.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine supervisor = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(supervisor0, FsmTools.getInstance().syncProduct(supervisor1, supervisor2)));
        supervisor.setName("S");
        // supervisor.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine closeloop = FsmTools.getInstance().trim(FsmTools.getInstance().product(supervisor, system));
        closeloop.setName("closeLoop");
        // closeloop.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
    }

    @Test
    public void catAndMouseTestApprocheDirect() {
    	UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final State catRoom0 = new State("E0", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom1 = new State("E1", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom2 = new State("E2", State.INITIAL, State.MARKED);
        final State catRoom3 = new State("E3", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom4 = new State("E4", State.NOTINITIAL, State.NOTMARKED);

        final State mouseRoom0 = new State("M0", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom1 = new State("M1", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom2 = new State("M2", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom3 = new State("M3", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);

        final FiniteStateMachine cat = new FiniteStateMachine("cat");
        cat.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        cat.addState(catRoom0);
        cat.addState(catRoom1);
        cat.addState(catRoom2);
        cat.addState(catRoom3);
        cat.addState(catRoom4);

        final FiniteStateMachine mouse = new FiniteStateMachine("mouse");
        mouse.addState(mouseRoom0);
        mouse.addState(mouseRoom1);
        mouse.addState(mouseRoom2);
        mouse.addState(mouseRoom3);
        mouse.addState(mouseRoom4);

        cat.addTransition("E0", "c1", "E1");
        cat.addTransition("E1", "c2", "E2");
        cat.addTransition("E2", "c3", "E0");
        cat.addTransition("E0", "c4", "E3");
        cat.addTransition("E3", "c5", "E4");
        cat.addTransition("E4", "c6", "E0");
        cat.addTransition("E3", "c7", "E1");
        cat.addTransition("E1", "c7", "E3");

        mouse.addTransition("M0", "m1", "M2");
        mouse.addTransition("M2", "m2", "M1");
        mouse.addTransition("M1", "m3", "M0");
        mouse.addTransition("M0", "m4", "M4");
        mouse.addTransition("M4", "m5", "M3");
        mouse.addTransition("M3", "m6", "M0");

        // cat.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        cat.accept(new PIVisitor());
        // mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        mouse.accept(new PIVisitor());

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        // system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        // system.accept(new JavaVisitor());

        FiniteStateMachine specification = system.clone();
        specification.removeState("M0E0");
        specification.removeState("M1E1");
        specification.removeState("M2E2");
        specification.removeState("M3E3");
        specification.removeState("M4E4");
        specification.removeTransition("M0E1", "m1", "M2E1");
        specification.removeTransition("M0E3", "m1", "M2E3");
        specification.removeTransition("M0E4", "m1", "M2E4");
        specification.removeTransition("M0E3", "c5", "M0E4");
        specification.removeTransition("M1E3", "c5", "M1E4");
        specification.removeTransition("M2E3", "c5", "M2E4");
        specification = FsmTools.getInstance().trim(specification);
        specification.setName("K");
        // specification.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine superviseur = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(specification, system));
        superviseur.setName("S");
        // superviseur.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
        superviseur.accept(new PIVisitor());
        // superviseur.accept(new JavaVisitor());

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().product(superviseur, system));
        closeLoop.setName("closeLoop");
        // closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));

    }

    @Test
    public void catAndMouseTestApprocheModulaire() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        // Definition des pieces du chat
        final State catRoom0 = new State("C0", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom1 = new State("C1", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom2 = new State("C2", State.INITIAL, State.MARKED);
        final State catRoom3 = new State("C3", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom4 = new State("C4", State.NOTINITIAL, State.NOTMARKED);

        // Definition des pieces de la souris
        final State mouseRoom0 = new State("M0", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom1 = new State("M1", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom2 = new State("M2", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom3 = new State("M3", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);

        final FiniteStateMachine cat = new FiniteStateMachine("cat");
        cat.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        cat.addState(catRoom0);
        cat.addState(catRoom1);
        cat.addState(catRoom2);
        cat.addState(catRoom3);
        cat.addState(catRoom4);

        final FiniteStateMachine mouse = new FiniteStateMachine("mouse");
        mouse.addState(mouseRoom0);
        mouse.addState(mouseRoom1);
        mouse.addState(mouseRoom2);
        mouse.addState(mouseRoom3);
        mouse.addState(mouseRoom4);

        cat.addTransition("C0", "c1", "C1");
        cat.addTransition("C1", "c2", "C2");
        cat.addTransition("C2", "c3", "C0");
        cat.addTransition("C0", "c4", "C3");
        cat.addTransition("C3", "c5", "C4");
        cat.addTransition("C4", "c6", "C0");
        cat.addTransition("C3", "c7", "C1");
        cat.addTransition("C1", "c7", "C3");

        mouse.addTransition("M0", "m1", "M2");
        mouse.addTransition("M2", "m2", "M1");
        mouse.addTransition("M1", "m3", "M0");
        mouse.addTransition("M0", "m4", "M4");
        mouse.addTransition("M4", "m5", "M3");
        mouse.addTransition("M3", "m6", "M0");

        // cat.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        // mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        // system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 0
        final FiniteStateMachine spec0 = new FiniteStateMachine("spec0");
        spec0.addState(new State("0", State.INITIAL, State.MARKED));
        spec0.addState(new State("1", State.NOTINITIAL, State.MARKED));
        spec0.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec0.addTransition("0", "c3", "1");
        spec0.addTransition("0", "c6", "1");
        spec0.addTransition("0", "m6", "1");
        spec0.addTransition("0", "m3", "1");
        spec0.addTransition("1", "c1", "0");
        spec0.addTransition("1", "c4", "0");
        spec0.addTransition("1", "m1", "0");
        spec0.addTransition("1", "m4", "0");
        spec0.addTransition("1", "c3", "bad");
        spec0.addTransition("1", "c6", "bad");
        spec0.addTransition("1", "m6", "bad");
        spec0.addTransition("1", "m3", "bad");
        // spec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 3
        final FiniteStateMachine spec1 = new FiniteStateMachine("spec1");
        spec1.addState(new State("0", State.INITIAL, State.MARKED));
        spec1.addState(new State("1", State.NOTINITIAL, State.MARKED));
        spec1.addState(new State("2", State.NOTINITIAL, State.MARKED));
        spec1.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec1.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        spec1.addTransition("0", "m5", "1");
        spec1.addTransition("1", "c4", "bad");
        spec1.addTransition("1", "c7", "bad");
        spec1.addTransition("1", "m6", "0");
        spec1.addTransition("0", "c7", "2");
        spec1.addTransition("0", "c4", "2");
        spec1.addTransition("2", "m5", "bad");
        spec1.addTransition("2", "c7", "0");
        // spec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 2
        final FiniteStateMachine spec3 = new FiniteStateMachine("spec3");
        spec3.addState(new State("0", State.INITIAL, State.MARKED));
        spec3.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec3.addTransition("0", "m1", "bad");
        spec3.addTransition("0", "c5", "bad");
        // spec3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine finalSpec0 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec0, system));
        finalSpec0.setName("finalSpec0");
        finalSpec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine finalSpec1 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec1, system));
        finalSpec1.setName("finalSpec1");
        finalSpec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine finalSpec3 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec3, system));
        finalSpec3.setName("finalSpec3");
        finalSpec3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // verrif non conflict
        /*
         * FiniteStateMachine
         * t1=FsmTools.getInstance().prefClosure(FsmTools.getInstance
         * ().trim(FsmTools.getInstance().product
         * (finalSpec3,FsmTools.getInstance(
         * .trim(FsmTools.getInstance().product(finalSpec0,finalSpec1)))));
         * t1.setName("t1"); t1.accept(new GraphvizVisitor(GraphvizVisitor.ID));
         *
         * FiniteStateMachine
         * t2=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().product(FsmTools.getInstance().prefClosure
         * (finalSpec3),FsmTools.getInstance(
         * .trim(FsmTools.getInstance().product
         * (FsmTools.getInstance().prefClosure(finalSpec0
         * ),FsmTools.getInstance().prefClosure(finalSpec1)))));
         * t2.setName("t2"); t2.accept(new GraphvizVisitor(GraphvizVisitor.ID));
         */

        final FiniteStateMachine supervisor0 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(finalSpec0, system));
        supervisor0.setName("supervisor0");
        supervisor0.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine supervisor1 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(finalSpec1, system));
        supervisor1.setName("supervisor1");
        supervisor1.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine supervisor3 = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(finalSpec3, system));
        supervisor3.setName("supervisor3");
        supervisor3.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine supervisor = FsmTools.getInstance().trim(
            FsmTools.getInstance().product(supervisor3, FsmTools.getInstance().trim(FsmTools.getInstance().product(supervisor1, supervisor0))));
        supervisor.setName("supervisor");
        supervisor.accept(new GraphvizVisitor(GraphvizVisitor.ID));

    }

    @Test
    public void catAndMouseTestNormalite() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        // Definition des pieces du chat
        final State catRoom0 = new State("C0", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom1 = new State("C1", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom2 = new State("C2", State.INITIAL, State.MARKED);
        final State catRoom3 = new State("C3", State.NOTINITIAL, State.NOTMARKED);
        final State catRoom4 = new State("C4", State.NOTINITIAL, State.NOTMARKED);

        // Definition des pieces de la souris
        final State mouseRoom0 = new State("M0", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom1 = new State("M1", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom2 = new State("M2", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom3 = new State("M3", State.NOTINITIAL, State.NOTMARKED);
        final State mouseRoom4 = new State("M4", State.INITIAL, State.MARKED);

        final FiniteStateMachine cat = new FiniteStateMachine("cat");
        cat.addEvent(new Event("c7", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        cat.addState(catRoom0);
        cat.addState(catRoom1);
        cat.addState(catRoom2);
        cat.addState(catRoom3);
        cat.addState(catRoom4);

        final FiniteStateMachine mouse = new FiniteStateMachine("mouse");
        mouse.addState(mouseRoom0);
        mouse.addState(mouseRoom1);
        mouse.addState(mouseRoom2);
        mouse.addState(mouseRoom3);
        mouse.addState(mouseRoom4);

        cat.addTransition("C0", "c1", "C1");
        cat.addTransition("C1", "c2", "C2");
        cat.addTransition("C2", "c3", "C0");
        cat.addTransition("C0", "c4", "C3");
        cat.addTransition("C3", "c5", "C4");
        cat.addTransition("C4", "c6", "C0");
        cat.addTransition("C3", "c7", "C1");
        cat.addTransition("C1", "c7", "C3");

        mouse.addTransition("M0", "m1", "M2");
        mouse.addTransition("M2", "m2", "M1");
        mouse.addTransition("M1", "m3", "M0");
        mouse.addTransition("M0", "m4", "M4");
        mouse.addTransition("M4", "m5", "M3");
        mouse.addTransition("M3", "m6", "M0");

        // cat.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        // mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        FiniteStateMachine specification = system.clone();
        specification.removeState("M0C0");
        specification.removeState("M1C1");
        specification.removeState("M2C2");
        specification.removeState("M3C3");
        specification.removeState("M4C4");
        specification.removeTransition("M0C1", "m1", "M2C1");
        specification.removeTransition("M0C3", "m1", "M2C3");
        specification.removeTransition("M0C4", "m1", "M2C4");
        specification.removeTransition("M0C3", "c5", "M0C4");
        specification.removeTransition("M1C3", "c5", "M1C4");
        specification.removeTransition("M2C3", "c5", "M2C4");
        specification = FsmTools.getInstance().trim(specification);
        specification.setName("K");
        specification.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine superviseur = FsmTools.getInstance().trim(FsmTools.getInstance().supremalNormal(specification, system));
        superviseur.setName("S");
        superviseur.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur, system));
        closeLoop.setName("closeLoop");
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));
    }

    @Test
    public void exemple() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");
        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");
        fsm1.addState(new State("0", State.INITIAL, State.MARKED));
        fsm1.addState(new State("1", State.NOTINITIAL, State.NOTMARKED));
        fsm1.addEvent(new Event("c", Event.CONTROLLABLE, Event.OBSERVABLE));
        fsm2.addState(new State("x", State.INITIAL, State.MARKED));
        fsm2.addState(new State("y", State.NOTINITIAL, State.NOTMARKED));
        fsm2.addEvent(new Event("b", Event.CONTROLLABLE, Event.OBSERVABLE));
        fsm1.addTransition("0", "a", "1");
        fsm1.addTransition("1", "b", "0");
        fsm2.addTransition("x", "a", "y");
        fsm2.addTransition("y", "c", "x");
        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm = FsmTools.getInstance().syncProduct(fsm1, fsm2);
        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    @Test
    public void specInvalid() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine spec = new FiniteStateMachine("spec");
        spec.addEvent(new Event("c", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        spec.addState(new State("0", State.INITIAL, State.MARKED));
        spec.addState(new State("1", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("2", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("3", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("4", State.NOTINITIAL, State.NOTMARKED));
        spec.addTransition("0", "a", "1");
        spec.addTransition("1", "b", "2");
        spec.addTransition("1", "c", "3");
        spec.addTransition("3", "b", "4");
        spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    @Test
    public void testNorm() {
    	UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine system = new FiniteStateMachine("system");
        system.addEvent(new Event("b", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        system.addState(new State("A", State.INITIAL, State.MARKED));
        system.addState(new State("B", State.NOTINITIAL, State.MARKED));
        system.addState(new State("C", State.NOTINITIAL, State.MARKED));
        system.addState(new State("D", State.NOTINITIAL, State.MARKED));
        system.addState(new State("E", State.NOTINITIAL, State.MARKED));
        system.addTransition("A", "a", "B");
        system.addTransition("B", "b", "C");
        system.addTransition("C", "c", "D");
        system.addTransition("B", "c", "E");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine spec = new FiniteStateMachine("spec");
        spec.addEvent(new Event("b", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        spec.addState(new State("A", State.INITIAL, State.MARKED));
        spec.addState(new State("B", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("C", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("D", State.NOTINITIAL, State.MARKED));
        spec.addTransition("A", "a", "B");
        spec.addTransition("B", "b", "C");
        spec.addTransition("B", "c", "D");
        spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine superviseur = FsmTools.getInstance().trim(FsmTools.getInstance().supremalNormal(spec, system));
        superviseur.setName("S");
        superviseur.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur, system));
        closeLoop.setName("closeLoop");
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));
        closeLoop.accept(new PIVisitor());

    }
}
