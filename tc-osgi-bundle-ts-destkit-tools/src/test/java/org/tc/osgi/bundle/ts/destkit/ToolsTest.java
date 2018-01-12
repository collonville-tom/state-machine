package org.tc.osgi.bundle.ts.destkit;

import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.utils.FsmTools;
import org.tc.osgi.bundle.ts.destkit.visitor.GraphvizVisitor;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

/**
 * ToolsTest.java.
 * @reg STD_BUNDLE_DESTKIT_TOOL_010
 * @track SRS_BUNDLE_DESTKIT_TOOL_010, SRS_BUNDLE_DESTKIT_TOOL_020, SRS_BUNDLE_DESTKIT_TOOL_030, SRS_BUNDLE_DESTKIT_TOOL_040, SRS_BUNDLE_DESTKIT_TOOL_050, SRS_BUNDLE_DESTKIT_TOOL_060, SRS_BUNDLE_DESTKIT_TOOL_070, SRS_BUNDLE_DESTKIT_TOOL_080, SRS_BUNDLE_DESTKIT_TOOL_090, SRS_BUNDLE_DESTKIT_TOOL_100, SRS_BUNDLE_DESTKIT_TOOL_110, SRS_BUNDLE_DESTKIT_TOOL_120, SRS_BUNDLE_DESTKIT_TOOL_130, SRS_BUNDLE_DESTKIT_TOOL_0140, SRS_BUNDLE_DESTKIT_TOOL_150
 * @author Collonville Thomas
 * @version 0.0.1
 */
public class ToolsTest {

    /**
     * automateAB_AC.
     */
    @Test
    public void automateAB_AC() {
        final Event a = new Event("a");
        final Event b = new Event("b");
        final Event c = new Event("c");

        final State x = new State("x", State.INITIAL, State.NOTMARKED);
        final State y = new State("y", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsmab = new FiniteStateMachine("ab");
        final FiniteStateMachine fsmac = new FiniteStateMachine("ac");

        fsmab.addEvent(a);
        fsmab.addEvent(b);
        fsmab.addState(x);
        fsmab.addState(y);
        fsmab.addTransition("x", "a", "y");
        fsmab.addTransition("y", "b", "x");

        fsmac.addEvent(a);
        fsmac.addEvent(c);
        fsmac.addState(x);
        fsmac.addState(y);
        fsmac.addTransition("x", "a", "y");
        fsmac.addTransition("y", "c", "x");

        final FiniteStateMachine clone = fsmab.clone();
        fsmab.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        fsmac.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        clone.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * bunnyTest.
     */
    @Test
    public void bunnyTest() {
        final FiniteStateMachine patte1 = new FiniteStateMachine("Patte1");
        patte1.addState(new State("A1", State.NOTINITIAL, State.MARKED));
        patte1.addState(new State("P1", State.NOTINITIAL, State.MARKED));
        patte1.addState(new State("R1", State.INITIAL, State.MARKED));

        patte1.addTransition("A1", "transfert1", "P1");
        patte1.addTransition("P1", "pea1", "R1");
        patte1.addTransition("R1", "pep1", "A1");
        patte1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine patte2 = new FiniteStateMachine("Patte2");
        patte2.addState(new State("A2", State.NOTINITIAL, State.MARKED));
        patte2.addState(new State("P2", State.NOTINITIAL, State.MARKED));
        patte2.addState(new State("R2", State.INITIAL, State.MARKED));

        patte2.addTransition("A2", "transfert2", "P2");
        patte2.addTransition("P2", "pea2", "R2");
        patte2.addTransition("R2", "pep2", "A2");
        patte2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine patte3 = new FiniteStateMachine("Patte3");
        patte3.addState(new State("A3", State.NOTINITIAL, State.MARKED));
        patte3.addState(new State("P3", State.NOTINITIAL, State.MARKED));
        patte3.addState(new State("R3", State.INITIAL, State.MARKED));

        patte3.addTransition("A3", "transfert3", "P3");
        patte3.addTransition("P3", "pea3", "R3");
        patte3.addTransition("R3", "pep3", "A3");
        patte3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // avec 2 pattes
        /*
         * FiniteStateMachine
         * system2Patte=FsmTools.getInstance().freeProduct(patte1,patte2);
         * system2Patte.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine finalSpec=new FiniteStateMachine("finalSpec");
         * finalSpec.addState(new State("x0",State.INITIAL,State.MARKED));
         * finalSpec.addState(new State("z0",State.NOTINITIAL,State.NOTMARKED));
         * finalSpec.addState(new State("y0",State.NOTINITIAL,State.NOTMARKED));
         * finalSpec.addState(new State("s0",State.NOTINITIAL,State.NOTMARKED));
         * finalSpec.addState(new State("t0",State.NOTINITIAL,State.NOTMARKED));
         * finalSpec.addState(new State("m0",State.NOTINITIAL,State.NOTMARKED));
         * finalSpec.addTransition("x0","pep1","y0");
         * finalSpec.addTransition("x0","pep2","z0");
         * finalSpec.addTransition("z0","pep1","s0");
         * finalSpec.addTransition("y0","pep2","s0");
         * finalSpec.addTransition("s0","transfert1","t0");
         * finalSpec.addTransition("s0","transfert2","m0");
         * finalSpec.addTransition("t0","transfert2","x0");
         * finalSpec.addTransition("m0","transfert1","x0"); finalSpec.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * supervisor=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().syncProduct(finalSpec ,system2Patte));
         * supervisor.setName("supervisor"); supervisor.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * closeLoop=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().syncProduct(supervisor ,system2Patte));
         * closeLoop.setName("closeLoop"); closeLoop.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         */

        // avec 3 pattes
        final FiniteStateMachine system3Patte = FsmTools.getInstance().freeProduct(patte1, FsmTools.getInstance().freeProduct(patte2, patte3));
        system3Patte.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        LoggerGestionnary.getInstance(ToolsTest.class).debug(system3Patte.getCloneStatesSet().size());

        final FiniteStateMachine spec1 = new FiniteStateMachine("spec1");
        spec1.addState(new State("0", State.INITIAL, State.MARKED));
        spec1.addState(new State("1", State.NOTINITIAL, State.NOTMARKED));
        spec1.addState(new State("2", State.NOTINITIAL, State.NOTMARKED));
        spec1.addState(new State("3", State.NOTINITIAL, State.NOTMARKED));
        spec1.addState(new State("4", State.NOTINITIAL, State.NOTMARKED));
        spec1.addState(new State("5", State.NOTINITIAL, State.NOTMARKED));
        spec1.addState(new State("6", State.NOTINITIAL, State.NOTMARKED));
        /*
         * spec1.addState(new State("7",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("8",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("9",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("A",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("B",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("C",State.NOTINITIAL,State.NOTMARKED));
         * spec1.addState(new State("D",State.NOTINITIAL,State.NOTMARKED));
         */
        spec1.addTransition("0", "pep1", "1");
        spec1.addTransition("0", "pep2", "2");
        spec1.addTransition("0", "pep3", "3");
        spec1.addTransition("1", "pep3", "4");
        spec1.addTransition("1", "pep2", "5");
        spec1.addTransition("3", "pep1", "4");
        spec1.addTransition("3", "pep2", "6");
        spec1.addTransition("2", "pep1", "5");
        spec1.addTransition("2", "pep3", "6");
        spec1.addTransition("4", "pep2", "0");
        spec1.addTransition("5", "pep3", "0");
        spec1.addTransition("6", "pep1", "0");
        /*
         * spec1.addTransition("7","transfert1","8");
         * spec1.addTransition("7","transfert2","9");
         * spec1.addTransition("7","transfert3","A");
         * spec1.addTransition("8","transfert3","B");
         * spec1.addTransition("8","transfert2","C");
         * spec1.addTransition("9","transfert1","C");
         * spec1.addTransition("9","transfert3","D");
         * spec1.addTransition("A","transfert2","D");
         * spec1.addTransition("A","transfert1","B");
         * spec1.addTransition("B","transfert2","0");
         * spec1.addTransition("C","transfert3","0");
         * spec1.addTransition("D","transfert1","0");
         */

        spec1.addTransition("0", "pea1", "0");
        spec1.addTransition("0", "pea2", "0");
        spec1.addTransition("0", "pea3", "0");
        spec1.addTransition("0", "transfert1", "0");
        spec1.addTransition("0", "transfert2", "0");
        spec1.addTransition("0", "transfert3", "0");
        spec1.setName("spec1");
        spec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor = FsmTools.getInstance().trim(FsmTools.getInstance().product(spec1, system3Patte));
        supervisor.setName("supervisor");
        LoggerGestionnary.getInstance(ToolsTest.class).debug(supervisor.getCloneStatesSet().size());
        supervisor.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(supervisor, system3Patte));
        closeLoop.setName("closeLoop");
        LoggerGestionnary.getInstance(ToolsTest.class).debug(closeLoop.getCloneStatesSet().size());
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * catAndMouseTest1.
     */
    @Test
    public void catAndMouseTest1() {
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

        cat.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
        mouse.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // on modelise le system global du chat et de la souris par le produit
        // libre.
        // toute les possibilit�s du chat ou de la souris sont toujours possible
        // a chaque instand.
        // le produit synchronis� aurait pu etre utilis� mais on aurait le meme
        // modele resultant car aucune synchro n est possible
        // en effet les deux alphabet sont totalement dissoci�es.
        // de plus au vue que les aplphabet sont differents, aucun non
        // determinisme ne peut apparaitre.
        // donc pas de souris (ou de soucis.....;-))
        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(cat, mouse);
        system.setName("G");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // definition de la specification ou propriet�.sans probleme de
        // controlabilit� vu que les evenement sont tous controlable ici
        // le plus gros probleme ici est l'enonciation de la spec sous le
        // formalisme d'un automate.
        // on veut le plus de libert� pour la souris et le chat sans qu'il se
        // retrouve dans une meme salle
        // il faut qu'il puisse retourn� a l etat initial.
        // par rapport au modele du produit libre il faut evit� les etats menant
        // au doublon du type xx (00,11,22,33,44)

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
        spec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

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
        spec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // pas dans la salle 2
        final FiniteStateMachine spec3 = new FiniteStateMachine("spec3");
        spec3.addState(new State("0", State.INITIAL, State.MARKED));
        spec3.addState(new State("bad", State.NOTINITIAL, State.NOTMARKED));
        spec3.addTransition("0", "m1", "bad");
        spec3.addTransition("0", "c5", "bad");
        spec3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // /
        final FiniteStateMachine finalSpec0 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec0, system));
        finalSpec0.setName("fspec");
        finalSpec0.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // FiniteStateMachine
        // supervisor0=FsmTools.getInstance().supremalControllable(finalSpec0,system);
        final FiniteStateMachine supervisor0 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec0, system));
        supervisor0.setName("supervisor0");
        supervisor0.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // /
        final FiniteStateMachine finalSpec1 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec1, system));
        finalSpec1.setName("fspec1");
        finalSpec1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // FiniteStateMachine
        // supervisor1=FsmTools.getInstance().supremalControllable(finalSpec1,system);
        final FiniteStateMachine supervisor1 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec1, system));
        supervisor1.setName("supervisor1");
        supervisor1.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // /
        final FiniteStateMachine finalSpec3 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(spec3, system));
        finalSpec3.setName("fspec3");
        finalSpec3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // FiniteStateMachine
        // supervisor3=FsmTools.getInstance().supremalControllable(finalSpec3,system);
        final FiniteStateMachine supervisor3 = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSpec3, system));
        supervisor3.setName("supervisor3");
        supervisor3.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // //
        final FiniteStateMachine finalSupervisor = FsmTools.getInstance().trim(FsmTools.getInstance().product(FsmTools.getInstance().product(supervisor1, supervisor3), supervisor0));
        finalSupervisor.setName("finalSupervisor");
        finalSupervisor.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine closeLoopNC = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSupervisor, system));
        closeLoopNC.setName("closeLoopNC");
        closeLoopNC.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine finalSupervisorC = FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(finalSupervisor, system));
        finalSupervisorC.setName("finalSupervisorC");
        finalSupervisorC.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().product(finalSupervisorC, system));
        closeLoop.setName("closeLoop");
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        // on va prendre le modele du system auquel on va supprimer les etats
        // qui nous derrange pour la specification.
        // dans le cadre de la non controllabilite, si c7 par exemple est non
        // controllable alors on a un probleme. (probleme traite ulterrierement)
        /*
         * FiniteStateMachine spec=system.clone(); spec.removeState("M0C0");
         * spec.removeState("M1C1"); spec.removeState("M2C2");
         * spec.removeState("M3C3"); spec.removeState("M4C4");
         *
         * spec.removeState("M2C0"); spec.removeState("M2C1");
         * spec.removeState("M2C3"); spec.removeState("M2C4");
         * spec.removeState("M0C4"); spec.removeState("M1C4");
         * spec.removeState("M2C4"); spec.removeState("M3C4");
         * spec=FsmTools.getInstance().trim(spec); spec.setName("K");
         * LoggerGestionnary
         * .getInstance(ToolsTest.class).debug(spec.getCloneStatesSet().size());
         * LoggerGestionnary
         * .getInstance(ToolsTest.class).debug(spec.getTransitionFunction
         * ().size()); spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * myCloseLoop=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().product(system,spec)); myCloseLoop.setName("MyCloseLoop");
         * LoggerGestionnary
         * .getInstance(ToolsTest.class).debug(myCloseLoop.getCloneStatesSet
         * ().size());
         * LoggerGestionnary.getInstance(ToolsTest.class).debug(myCloseLoop
         * .getTransitionFunction().size()); myCloseLoop.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         */

        // superviseur definit dans la doc paper de RW attention ce supperviseur
        // prend en compte le fait que c7 est non controllable.
        /*
         * FiniteStateMachine rwSupervisor=new
         * FiniteStateMachine("rwSupervisor"); rwSupervisor.addEvent(new
         * Event("c1")); rwSupervisor.addEvent(new Event("c2"));
         * rwSupervisor.addEvent(new Event("c3")); rwSupervisor.addEvent(new
         * Event("c4")); rwSupervisor.addEvent(new Event("c5"));
         * rwSupervisor.addEvent(new Event("c6")); rwSupervisor.addEvent(new
         * Event("m1")); rwSupervisor.addEvent(new Event("m2"));
         * rwSupervisor.addEvent(new Event("m3")); rwSupervisor.addEvent(new
         * Event("m4")); rwSupervisor.addEvent(new Event("m5"));
         * rwSupervisor.addEvent(new Event("m6"));
         *
         * rwSupervisor.addState(new State("0",State.INITIAL,State.MARKED));
         * rwSupervisor.addState(new State("1",State.NOTINITIAL,State.MARKED));
         *
         * rwSupervisor.addTransition("0","m5","1");
         * rwSupervisor.addTransition("0","c3","1");
         * rwSupervisor.addTransition("1","m4","0");
         * rwSupervisor.addTransition("1","c2","0");
         * rwSupervisor.addTransition("1","m6","1");
         * rwSupervisor.addTransition("1","c1","1");
         * rwSupervisor.addTransition("1","c4","1");
         * rwSupervisor.addTransition("1","c7","1");
         * rwSupervisor.setName("RWS"); rwSupervisor.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * rwCloseLoop=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().product(system,rwSupervisor)); rwCloseLoop.setName("RWCloseLoop");
         * rwCloseLoop.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
         */

    }

    /**
     * compParrTest.
     */
    @Test
    public void compParrTest() {
        final State stateX = new State("x", State.INITIAL, State.MARKED);
        final State stateY = new State("y", State.NOTINITIAL, State.NOTMARKED);
        final State stateZ = new State("z", State.NOTINITIAL, State.NOTMARKED);

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");

        fsm1.addState(stateX);
        fsm1.addState(stateY);
        fsm1.addState(stateZ);

        fsm1.addTransition("x", "a", "x");
        fsm1.addTransition("x", "g", "z");
        fsm1.addTransition("z", "b", "z");
        fsm1.addTransition("z", "a", "y");
        fsm1.addTransition("z", "g", "y");
        fsm1.addTransition("y", "b", "y");
        fsm1.addTransition("y", "a", "x");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");

        fsm2.addState(state0);
        fsm2.addState(state1);

        fsm2.addTransition("0", "b", "0");
        fsm2.addTransition("0", "a", "1");
        fsm2.addTransition("1", "a", "1");
        fsm2.addTransition("1", "b", "0");

        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine inv1 = FsmTools.getInstance().inverseProjection(fsm1, fsm2.getCloneAlphabet());
        final FiniteStateMachine inv2 = FsmTools.getInstance().inverseProjection(fsm2, fsm1.getCloneAlphabet());
        final FiniteStateMachine prod = FsmTools.getInstance().product(inv1, inv2);

        inv1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        inv2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        prod.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
    }

    /**
     * compParrTest1.
     */
    @Test
    public void compParrTest1() {
        final State stateX = new State("x", State.INITIAL, State.MARKED);
        final State stateY = new State("y", State.NOTINITIAL, State.NOTMARKED);

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");

        fsm1.addState(stateX);
        fsm1.addState(stateY);

        fsm1.addTransition("x", "a", "y");
        fsm1.addTransition("y", "c", "x");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");

        fsm2.addState(state0);
        fsm2.addState(state1);

        fsm2.addTransition("0", "a", "1");
        fsm2.addTransition("1", "b", "0");

        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine inv1 = FsmTools.getInstance().inverseProjection(fsm1, fsm2.getCloneAlphabet());
        final FiniteStateMachine inv2 = FsmTools.getInstance().inverseProjection(fsm2, fsm1.getCloneAlphabet());
        final FiniteStateMachine prod = FsmTools.getInstance().product(inv1, inv2);

        inv1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        inv2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        prod.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * factoryTest.
     */
    @Test
    public void factoryTest() {
        // Machines

        final State idle1 = new State("idle1", State.INITIAL, State.MARKED);
        final State working1 = new State("working1", State.NOTINITIAL, State.NOTMARKED);
        final State down1 = new State("down1", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine m1 = new FiniteStateMachine("M1");
        m1.addState(idle1);
        m1.addState(working1);
        m1.addState(down1);
        m1.addTransition("idle1", "a1", "working1");
        m1.addTransition("working1", "b1", "idle1");
        m1.addTransition("working1", "l1", "down1");
        m1.addTransition("down1", "m1", "idle1");
        m1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final State idle2 = new State("idle2", State.INITIAL, State.MARKED);
        final State working2 = new State("working2", State.NOTINITIAL, State.NOTMARKED);
        final State down2 = new State("down2", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine m2 = new FiniteStateMachine("M2");
        m2.addState(idle2);
        m2.addState(working2);
        m2.addState(down2);
        m2.addTransition("idle2", "a2", "working2");
        m2.addTransition("working2", "b2", "idle2");
        m2.addTransition("working2", "l2", "down2");
        m2.addTransition("down2", "m2", "idle2");
        m2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final State idle3 = new State("idle3", State.INITIAL, State.MARKED);
        final State working3 = new State("working3", State.NOTINITIAL, State.NOTMARKED);
        final State down3 = new State("down3", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine m3 = new FiniteStateMachine("M3");
        m3.addState(idle3);
        m3.addState(working3);
        m3.addState(down3);
        m3.addTransition("idle3", "a3", "working3");
        m3.addTransition("working3", "b3", "idle3");
        m3.addTransition("working3", "l3", "down3");
        m3.addTransition("down3", "m3", "idle3");
        m3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().syncProduct(m1, FsmTools.getInstance().syncProduct(m2, m3));
        system.setName("G");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // specification:
        final FiniteStateMachine buffer = new FiniteStateMachine("buffer");
        buffer.addState(new State("b0", State.INITIAL, State.MARKED));
        buffer.addState(new State("b1", State.NOTINITIAL, State.MARKED));
        buffer.addState(new State("b2", State.NOTINITIAL, State.MARKED));
        buffer.addState(new State("b3", State.NOTINITIAL, State.MARKED));
        buffer.addTransition("b0", "b1", "b1");
        buffer.addTransition("b0", "b2", "b1");
        buffer.addTransition("b1", "b1", "b2");
        buffer.addTransition("b1", "b2", "b2");
        buffer.addTransition("b2", "b1", "b3");
        buffer.addTransition("b2", "b2", "b3");
        buffer.addTransition("b3", "a3", "b2");
        buffer.addTransition("b2", "a3", "b1");
        buffer.addTransition("b1", "a3", "b0");
        buffer.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine breakRepairM1M2 = new FiniteStateMachine("breakRepairM1M2");
        breakRepairM1M2.addState(new State("br0", State.INITIAL, State.MARKED));
        breakRepairM1M2.addState(new State("br1", State.NOTINITIAL, State.MARKED));
        breakRepairM1M2.addState(new State("br2", State.NOTINITIAL, State.MARKED));
        breakRepairM1M2.addState(new State("br3", State.NOTINITIAL, State.MARKED));
        breakRepairM1M2.addTransition("br0", "l1", "br1");
        breakRepairM1M2.addTransition("br0", "l2", "br1");
        breakRepairM1M2.addTransition("br1", "m1", "br0");
        breakRepairM1M2.addTransition("br1", "m2", "br0");
        breakRepairM1M2.addTransition("br1", "l1", "br2");
        breakRepairM1M2.addTransition("br1", "l2", "br3");
        breakRepairM1M2.addTransition("br2", "m1", "br1");
        breakRepairM1M2.addTransition("br3", "m2", "br1");
        breakRepairM1M2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine breakRepairM3 = new FiniteStateMachine("breakRepairM3");
        breakRepairM3.addState(new State("brt0", State.INITIAL, State.MARKED));
        breakRepairM3.addState(new State("brt1", State.NOTINITIAL, State.MARKED));
        breakRepairM3.addTransition("brt0", "l3", "brt1");
        breakRepairM3.addTransition("brt0", "m1", "brt0");
        breakRepairM3.addTransition("brt0", "m2", "brt0");
        breakRepairM3.addTransition("brt1", "m3", "brt0");
        breakRepairM3.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // specification monolithic mauvais choix.
        // on realise la combinaison des trois definitions
        FiniteStateMachine spec = FsmTools.getInstance().trim(
            FsmTools.getInstance().deterministic(FsmTools.getInstance().syncProduct(buffer, FsmTools.getInstance().syncProduct(breakRepairM1M2, breakRepairM3))));
        spec = FsmTools.getInstance().inverseProjection(spec, system.getCloneAlphabet());
        spec.setName("K");
        LoggerGestionnary.getInstance(ToolsTest.class).debug(spec.getCloneStatesSet().size());
        LoggerGestionnary.getInstance(ToolsTest.class).debug(spec.getTransitionFunction().size());
        spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor = FsmTools.getInstance().minimise(FsmTools.getInstance().trim(FsmTools.getInstance().product(spec, system)));
        supervisor.setName("MyS");
        LoggerGestionnary.getInstance(ToolsTest.class).debug(supervisor.getCloneStatesSet().size());
        LoggerGestionnary.getInstance(ToolsTest.class).debug(supervisor.getTransitionFunction().size());
        supervisor.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine myCloseLoop = FsmTools.getInstance().minimise(FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(system, supervisor)));
        LoggerGestionnary.getInstance(ToolsTest.class).debug(myCloseLoop.getCloneStatesSet().size());
        LoggerGestionnary.getInstance(ToolsTest.class).debug(myCloseLoop.getTransitionFunction().size());
        myCloseLoop.setName("MyCloseLoop");
        myCloseLoop.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        // solution modulaire apporte par R et W
        /*
         * FiniteStateMachine buffOver=new FiniteStateMachine("buffOver");
         * buffOver.addState(new State("bo0",State.INITIAL,State.MARKED));
         * buffOver.addTransition("bo0","l1","bo0");
         * buffOver.addTransition("bo0","l2","bo0");
         * buffOver.addTransition("bo0","a1","bo1");
         * buffOver.addTransition("bo0","a2","bo1");
         * buffOver.addTransition("bo1","a1","bo2");
         * buffOver.addTransition("bo1","a2","bo2");
         * buffOver.addTransition("bo2","a1","bo3");
         * buffOver.addTransition("bo2","a2","bo3");
         * buffOver.addTransition("bo3","l1","bo2");
         * buffOver.addTransition("bo3","l2","bo2");
         * buffOver.addTransition("bo3","a3","bo2");
         * buffOver.addTransition("bo2","l1","bo1");
         * buffOver.addTransition("bo2","l2","bo1");
         * buffOver.addTransition("bo2","a3","bo1");
         * buffOver.addTransition("bo1","l1","bo0");
         * buffOver.addTransition("bo1","l2","bo0");
         * buffOver.addTransition("bo1","a3","bo0");
         * //buffOver=FsmTools.getInstance().inverseProjection
         * (buffOver,system.getCloneAlphabet()); buffOver.setName("buffOver");
         * //buffOver.removeTransition("bo3","a1","bo3");
         * //buffOver.removeTransition("bo3","a2","bo3"); buffOver.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine underFlow=new FiniteStateMachine("underFlow");
         * underFlow.addState(new State("bu0",State.INITIAL,State.MARKED));
         * underFlow.addTransition("bu0","b1","bu1");
         * underFlow.addTransition("bu0","b2","bu1");
         * underFlow.addTransition("bu1","b1","bu2");
         * underFlow.addTransition("bu1","b2","bu2");
         * underFlow.addTransition("bu2","b1","bu3");
         * underFlow.addTransition("bu2","b2","bu3");
         * underFlow.addTransition("bu3","a3","bu2");
         * underFlow.addTransition("bu2","a3","bu1");
         * underFlow.addTransition("bu1","a3","bu0");
         * //underFlow=FsmTools.getInstance().inverseProjection
         * (underFlow,system.getCloneAlphabet());
         * //underFlow.removeTransition("bu0","a3","bu0");
         * //underFlow.setName("underFlow"); underFlow.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         *
         * FiniteStateMachine
         * spec=FsmTools.getInstance().trim(FsmTools.getInstance
         * ().deterministic(FsmTools.getInstance().
         * union(buffOver,FsmTools.getInstance(
         * .union(breakRepairM1M2,FsmTools.getInstance
         * ().union(underFlow,breakRepairM3))))); spec.setName("K");
         * spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * supervisor=FsmTools.getInstance().renaming(FsmTools
         * .getInstance().minimise(FsmTools.getInstance(
         * .trim(FsmTools.getInstance().product(spec,system))));
         * supervisor.setName("MyS"); supervisor.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         *
         * FiniteStateMachine
         * myCloseLoop=FsmTools.getInstance().renaming(FsmTools
         * .getInstance().minimise(FsmTools.getInstance(
         * .trim(FsmTools.getInstance().syncProduct(system,supervisor))));
         * myCloseLoop.setName("MyCloseLoop"); myCloseLoop.accept(new
         * GraphvizVisitor(GraphvizVisitor.NAME));
         */
    }

    /**
     * freeProductTest.
     */
    @Test
    public void freeProductTest() {
        final State stateX = new State("x", State.INITIAL, State.MARKED);
        final State stateY = new State("y", State.NOTINITIAL, State.NOTMARKED);
        final State stateZ = new State("z", State.NOTINITIAL, State.NOTMARKED);

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");

        fsm1.addState(stateX);
        fsm1.addState(stateY);
        fsm1.addState(stateZ);

        fsm1.addTransition("x", "a", "x");
        fsm1.addTransition("x", "g", "z");
        fsm1.addTransition("z", "b", "z");
        fsm1.addTransition("z", "a", "y");
        fsm1.addTransition("z", "g", "y");
        fsm1.addTransition("y", "b", "y");
        fsm1.addTransition("y", "a", "x");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");

        fsm2.addState(state0);
        fsm2.addState(state1);

        fsm2.addTransition("0", "b", "0");
        fsm2.addTransition("0", "a", "1");
        fsm2.addTransition("1", "a", "1");
        fsm2.addTransition("1", "b", "0");

        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine freeProduct = FsmTools.getInstance().freeProduct(fsm1, fsm2);
        final FiniteStateMachine det = FsmTools.getInstance().deterministic(freeProduct);
        final FiniteStateMachine ac = FsmTools.getInstance().accessiblePart(det);
        final FiniteStateMachine coAc = FsmTools.getInstance().minimise(FsmTools.getInstance().coAccessiblePart(ac));

        freeProduct.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        det.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        ac.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        coAc.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        LoggerGestionnary.getInstance(ToolsTest.class).debug(FsmTools.getInstance().table.toString());

    }

    /**
     * guideWay.
     */
    @Test
    public void guideWay() {
        final FiniteStateMachine v1 = new FiniteStateMachine("V1");
        v1.addState(new State("0", State.INITIAL, State.NOTMARKED));
        v1.addState(new State("5", State.NOTINITIAL, State.MARKED));
        v1.addEvent(new Event("a12", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        v1.addEvent(new Event("a15", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        v1.addTransition("0", "a11", "1");
        v1.addTransition("1", "a12", "2");
        v1.addTransition("2", "a13", "3");
        v1.addTransition("3", "a14", "4");
        v1.addTransition("4", "a15", "5");
        v1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine v2 = new FiniteStateMachine("V2");
        v2.addState(new State("6", State.INITIAL, State.NOTMARKED));
        v2.addState(new State("11", State.NOTINITIAL, State.MARKED));
        v2.addEvent(new Event("a22", Event.CONTROLLABLE, Event.UNOBSERVABLE));
        v2.addEvent(new Event("a25", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        v2.addTransition("6", "a21", "7");
        v2.addTransition("7", "a22", "8");
        v2.addTransition("8", "a23", "9");
        v2.addTransition("9", "a24", "10");
        v2.addTransition("10", "a25", "11");
        v2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine system = FsmTools.getInstance().freeProduct(v1, v2);
        system.setName("G");
        LoggerGestionnary.getInstance(ToolsTest.class).debug(system.getStatesSet().size());
        system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine spec = system.clone();
        spec.removeState("71");
        spec.removeState("82");
        spec.removeState("93");
        spec.removeState("104");
        spec.setName("K");
        spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor = FsmTools.getInstance().trim(
            FsmTools.getInstance().syncProduct(spec, FsmTools.getInstance().deterministic(FsmTools.getInstance().projection(system, system.getObservableAlphabet()))));

        LoggerGestionnary.getInstance(ToolsTest.class).debug(supervisor.getStatesSet().size());
        supervisor.setName("supervisor");
        supervisor.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine closeLoop = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(supervisor, system));
        LoggerGestionnary.getInstance(ToolsTest.class).debug(closeLoop.getStatesSet().size());
        closeLoop.setName("closeLoop");
        closeLoop.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * minimiseTest.
     */
    @Test
    public void minimiseTest() {
        final State x0 = new State("x0", State.INITIAL, State.NOTMARKED);
        final State x1 = new State("x1", State.NOTINITIAL, State.NOTMARKED);
        final State x12 = new State("x12", State.NOTINITIAL, State.NOTMARKED);
        final State xnull = new State("xnull", State.NOTINITIAL, State.NOTMARKED);
        final State x123 = new State("x123", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("fsm");
        fsm.addState(x0);
        fsm.addState(x1);
        fsm.addState(x12);
        fsm.addState(xnull);
        fsm.addState(x123);

        fsm.addTransition("x0", "1", "x1");
        fsm.addTransition("x0", "2", "xnull");
        fsm.addTransition("x0", "3", "xnull");
        fsm.addTransition("xnull", "2", "xnull");
        fsm.addTransition("xnull", "3", "xnull");
        fsm.addTransition("xnull", "1", "x1");
        fsm.addTransition("x1", "3", "xnull");
        fsm.addTransition("x1", "1", "x1");
        fsm.addTransition("x1", "2", "x12");
        fsm.addTransition("x12", "1", "x1");
        fsm.addTransition("x12", "2", "xnull");
        fsm.addTransition("x123", "2", "xnull");
        fsm.addTransition("x123", "3", "xnull");
        fsm.addTransition("x12", "3", "x123");
        fsm.addTransition("x123", "1", "x1");

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        FsmTools.getInstance().minimise(fsm).accept(new GraphvizVisitor(GraphvizVisitor.NAME));
    }

    /**
     * nondetermToDeterm1.
     */
    @Test
    public void nondetermToDeterm1() {
        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.MARKED);
        final State state3 = new State("3", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("test");

        fsm.addState(state0);
        fsm.addState(state1);
        fsm.addState(state2);
        fsm.addState(state3);

        fsm.addTransition("0", "a", "1");
        fsm.addTransition("0", "a", "2");
        fsm.addTransition("1", "a", "2");
        fsm.addTransition("2", "a", "3");
        fsm.addTransition("3", "a", "0");
        fsm.addTransition("3", "a", "3");

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine det = FsmTools.getInstance().deterministic(fsm);

        det.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * nondetermToDeterm2.
     */
    @Test
    public void nondetermToDeterm2() {
        final Event d = new Event("E", Event.CONTROLLABLE, Event.UNOBSERVABLE);

        final State state0 = new State("0", State.INITIAL, State.MARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.NOTMARKED);
        final State state3 = new State("3", State.NOTINITIAL, State.NOTMARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("test");

        final FiniteStateMachine alph = new FiniteStateMachine("alph");

        alph.addEvent(new Event("a"));
        alph.addEvent(new Event("b"));
        alph.addEvent(new Event("c"));

        fsm.addEvent(d);

        fsm.addState(state0);
        fsm.addState(state1);
        fsm.addState(state2);
        fsm.addState(state3);

        fsm.addTransition("0", "a", "1");
        fsm.addTransition("1", "b", "0");
        fsm.addTransition("1", "b", "1");
        fsm.addTransition("1", "E", "2");
        fsm.addTransition("2", "E", "3");
        fsm.addTransition("3", "b", "0");
        fsm.addTransition("2", "a", "0");

        final FiniteStateMachine proj = FsmTools.getInstance().projection(fsm, alph.getCloneAlphabet());
        final FiniteStateMachine det = FsmTools.getInstance().deterministic(proj);

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        proj.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        det.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * nondetermToDeterm3.
     */
    @Test
    public void nondetermToDeterm3() {
        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.INITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("test");

        fsm.addState(state0);
        fsm.addState(state1);
        fsm.addState(state2);

        fsm.addTransition("0", "a", "2");
        fsm.addTransition("0", "a", "1");
        fsm.addTransition("1", "b", "2");
        fsm.addTransition("1", "b", "1");
        fsm.addTransition("2", "a", "0");
        fsm.addTransition("2", "a", "2");

        final FiniteStateMachine det = FsmTools.getInstance().deterministic(fsm);

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        det.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * productTest.
     */
    @Test
    public void productTest() {
        final State stateX = new State("x", State.INITIAL, State.MARKED);
        final State stateY = new State("y", State.NOTINITIAL, State.NOTMARKED);
        final State stateZ = new State("z", State.NOTINITIAL, State.MARKED);

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");

        fsm1.addState(stateX);
        fsm1.addState(stateY);
        fsm1.addState(stateZ);

        fsm1.addTransition("x", "a", "x");
        fsm1.addTransition("y", "a", "x");
        fsm1.addTransition("x", "g", "z");
        fsm1.addTransition("y", "b", "y");
        fsm1.addTransition("z", "a", "y");
        fsm1.addTransition("z", "g", "y");
        fsm1.addTransition("z", "b", "z");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");

        fsm2.addState(state0);
        fsm2.addState(state1);

        fsm2.addTransition("0", "b", "0");
        fsm2.addTransition("0", "a", "1");
        fsm2.addTransition("1", "a", "1");
        fsm2.addTransition("1", "b", "0");

        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine product = FsmTools.getInstance().product(fsm1, fsm2);
        final FiniteStateMachine det = FsmTools.getInstance().deterministic(product);

        product.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        det.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * projection.
     */
    @Test
    public void projection() {
        final Event a = new Event("a");
        final Event b = new Event("b");
        final Event c = new Event("c");
        final Event d = new Event("d");

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.MARKED);
        final State state3 = new State("3", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine alph1 = new FiniteStateMachine("alphabet1");

        alph1.addEvent(d);
        alph1.addEvent(b);
        alph1.addEvent(c);

        final FiniteStateMachine alph2 = new FiniteStateMachine("alphabet2");

        alph2.addEvent(a);
        alph2.addEvent(b);
        alph2.addEvent(c);

        final FiniteStateMachine fsm = new FiniteStateMachine("test");

        fsm.addState(state0);
        fsm.addState(state1);
        fsm.addState(state2);
        fsm.addState(state3);

        fsm.addTransition("0", "a", "2");
        fsm.addTransition("0", "b", "1");
        fsm.addTransition("1", "c", "2");
        fsm.addTransition("2", "c", "1");
        fsm.addTransition("2", "b", "3");

        final FiniteStateMachine inv1 = FsmTools.getInstance().inverseProjection(fsm, alph1.getCloneAlphabet());
        final FiniteStateMachine inv2 = FsmTools.getInstance().projection(inv1, alph2.getCloneAlphabet());

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        inv1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        inv2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

    /**
     * supremalControllableTest.
     */
    @Test
    public void supremalControllableTest() {
        final FiniteStateMachine system = new FiniteStateMachine("G");
        system.addEvent(new Event("u", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        system.addState(new State("q0", State.INITIAL, State.MARKED));
        system.addState(new State("q1", State.NOTINITIAL, State.MARKED));
        system.addState(new State("q2", State.NOTINITIAL, State.MARKED));
        system.addTransition("q0", "a", "q0");
        system.addTransition("q0", "b", "q0");
        system.addTransition("q0", "c", "q1");
        system.addTransition("q1", "a", "q1");
        system.addTransition("q1", "b", "q2");
        system.addTransition("q1", "u", "q2");
        system.addTransition("q2", "a", "q2");
        system.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine spec = new FiniteStateMachine("K");
        spec.addEvent(new Event("u", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        spec.addState(new State("x0", State.INITIAL, State.MARKED));
        spec.addState(new State("x1", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("x2", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("x3", State.NOTINITIAL, State.MARKED));
        spec.addState(new State("x4", State.NOTINITIAL, State.MARKED));
        spec.addTransition("x0", "a", "x1");
        spec.addTransition("x1", "b", "x0");
        spec.addTransition("x0", "c", "x4");
        spec.addTransition("x1", "c", "x2");
        spec.addTransition("x2", "b", "x4");
        spec.addTransition("x2", "u", "x3");
        spec.addTransition("x3", "a", "x3");
        spec.addTransition("x4", "a", "x3");
        spec.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisor = FsmTools.getInstance().trim(FsmTools.getInstance().product(system, spec));
        supervisor.setName("SNC");
        supervisor.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine closLoopNC = FsmTools.getInstance().trim(FsmTools.getInstance().product(system, supervisor));
        closLoopNC.setName("CLNC");
        closLoopNC.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine supervisorC = FsmTools.getInstance().supremalControllable(spec, system);
        supervisorC.setName("S");
        supervisorC.accept(new GraphvizVisitor(GraphvizVisitor.ID));

        final FiniteStateMachine closLoop = FsmTools.getInstance().trim(FsmTools.getInstance().product(system, supervisorC));
        closLoop.setName("CL");
        closLoop.accept(new GraphvizVisitor(GraphvizVisitor.ID));
    }

    /**
     * testClone.
     */
    @Test
    public void testClone() {
        final Event d = new Event("E", Event.CONTROLLABLE, Event.UNOBSERVABLE);

        final State state0 = new State("0", State.INITIAL, State.MARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.NOTMARKED);
        final State state3 = new State("3", State.NOTINITIAL, State.NOTMARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("test");

        fsm.addEvent(d);

        fsm.addState(state0);
        fsm.addState(state1);
        fsm.addState(state2);
        fsm.addState(state3);

        fsm.addTransition("0", "a", "1");
        fsm.addTransition("1", "b", "0");
        fsm.addTransition("1", "b", "1");
        fsm.addTransition("1", "E", "2");
        fsm.addTransition("2", "E", "3");
        fsm.addTransition("3", "b", "0");
        fsm.addTransition("2", "a", "0");

        final FiniteStateMachine clone = fsm.clone();
        clone.setName("clone");

        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        clone.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
    }

    /**
     * unionTest.
     */
    @Test
    public void unionTest() {
        final State stateX = new State("x", State.INITIAL, State.MARKED);
        final State stateY = new State("y", State.NOTINITIAL, State.NOTMARKED);
        final State stateZ = new State("z", State.NOTINITIAL, State.NOTMARKED);

        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm1 = new FiniteStateMachine("fsm1");

        fsm1.addState(stateX);
        fsm1.addState(stateY);
        fsm1.addState(stateZ);

        fsm1.addTransition("x", "a", "x");
        fsm1.addTransition("x", "g", "z");
        fsm1.addTransition("z", "b", "z");
        fsm1.addTransition("z", "a", "y");
        fsm1.addTransition("z", "g", "y");
        fsm1.addTransition("y", "b", "y");
        fsm1.addTransition("y", "a", "x");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("fsm2");

        fsm2.addState(state0);
        fsm2.addState(state1);

        fsm2.addTransition("0", "b", "0");
        fsm2.addTransition("0", "a", "1");
        fsm2.addTransition("1", "a", "1");
        fsm2.addTransition("1", "b", "0");

        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine union = FsmTools.getInstance().union(fsm1, fsm2);

        union.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

    }

}
