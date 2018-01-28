package org.tc.osgi.bundle.ts.destkit;


import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.exec.FsmOpSem;
import org.tc.osgi.bundle.ts.destkit.exec.exception.ExecInitExeception;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Event;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.FsmSerialTool;
import org.tc.osgi.bundle.ts.destkit.utils.FsmTools;
import org.tc.osgi.bundle.ts.destkit.visitor.GraphvizVisitor;
import org.tc.osgi.bundle.ts.m3.module.service.UtilsServiceProxy;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;

public class BunnyTest {

	

	
    @Test
    public void desLt() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine fsm1 = new FiniteStateMachine("leg1");
        fsm1.addState(new State("Start1", State.INITIAL, State.MARKED));
        fsm1.addState(new State("Unload1", State.NOTINITIAL, State.MARKED));
        fsm1.addState(new State("Recover1", State.NOTINITIAL, State.MARKED));
        fsm1.addState(new State("Load1", State.NOTINITIAL, State.MARKED));
        fsm1.addState(new State("Drive1", State.NOTINITIAL, State.MARKED));
        fsm1.addState(new State("Slipped1", State.NOTINITIAL, State.MARKED));
        fsm1.addEvent(new Event("slip1", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm1.addTransition("Start1", "es1", "Unload1");
        fsm1.addTransition("Unload1", "eu1", "Recover1");
        fsm1.addTransition("Recover1", "er1", "Load1");
        fsm1.addTransition("Load1", "el1", "Drive1");
        fsm1.addTransition("Drive1", "slip1", "Slipped1");
        fsm1.addTransition("Drive1", "ed1", "Unload1");
        fsm1.addTransition("Slipped1", "esl1", "Unload1");

        fsm1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("leg2");
        fsm2.addState(new State("Start2", State.INITIAL, State.MARKED));
        fsm2.addState(new State("Unload2", State.NOTINITIAL, State.MARKED));
        fsm2.addState(new State("Recover2", State.NOTINITIAL, State.MARKED));
        fsm2.addState(new State("Load2", State.NOTINITIAL, State.MARKED));
        fsm2.addState(new State("Drive2", State.NOTINITIAL, State.MARKED));
        fsm2.addState(new State("Slipped2", State.NOTINITIAL, State.MARKED));
        fsm2.addEvent(new Event("slip2", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm2.addTransition("Start2", "es2", "Unload2");
        fsm2.addTransition("Unload2", "eu2", "Recover2");
        fsm2.addTransition("Recover2", "er2", "Load2");
        fsm2.addTransition("Load2", "el2", "Drive2");
        fsm2.addTransition("Drive2", "slip2", "Slipped2");
        fsm2.addTransition("Drive2", "ed2", "Unload2");
        fsm2.addTransition("Slipped2", "esl2", "Unload2");

        final FiniteStateMachine fsm3 = new FiniteStateMachine("leg3");
        fsm3.addState(new State("Start3", State.INITIAL, State.MARKED));
        fsm3.addState(new State("Unload3", State.NOTINITIAL, State.MARKED));
        fsm3.addState(new State("Recover3", State.NOTINITIAL, State.MARKED));
        fsm3.addState(new State("Load3", State.NOTINITIAL, State.MARKED));
        fsm3.addState(new State("Drive3", State.NOTINITIAL, State.MARKED));
        fsm3.addState(new State("Slipped3", State.NOTINITIAL, State.MARKED));
        fsm3.addEvent(new Event("slip3", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm3.addTransition("Start3", "es3", "Unload3");
        fsm3.addTransition("Unload3", "eu3", "Recover3");
        fsm3.addTransition("Recover3", "er3", "Load3");
        fsm3.addTransition("Load3", "el3", "Drive3");
        fsm3.addTransition("Drive3", "slip3", "Slipped3");
        fsm3.addTransition("Drive3", "ed3", "Unload3");
        fsm3.addTransition("Slipped3", "esl3", "Unload3");

        final FiniteStateMachine fsm4 = new FiniteStateMachine("leg4");
        fsm4.addState(new State("Start4", State.INITIAL, State.MARKED));
        fsm4.addState(new State("Unload4", State.NOTINITIAL, State.MARKED));
        fsm4.addState(new State("Recover4", State.NOTINITIAL, State.MARKED));
        fsm4.addState(new State("Load4", State.NOTINITIAL, State.MARKED));
        fsm4.addState(new State("Drive4", State.NOTINITIAL, State.MARKED));
        fsm4.addState(new State("Slipped4", State.NOTINITIAL, State.MARKED));
        fsm4.addEvent(new Event("slip4", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm4.addTransition("Start4", "es4", "Unload4");
        fsm4.addTransition("Unload4", "eu4", "Recover4");
        fsm4.addTransition("Recover4", "er4", "Load4");
        fsm4.addTransition("Load4", "el4", "Drive4");
        fsm4.addTransition("Drive4", "slip4", "Slipped4");
        fsm4.addTransition("Drive4", "ed4", "Unload4");
        fsm4.addTransition("Slipped4", "esl4", "Unload4");

        final FiniteStateMachine fsm = FsmTools.getInstance().freeProduct(FsmTools.getInstance().freeProduct(fsm1, fsm2), FsmTools.getInstance().freeProduct(fsm3, fsm4));
        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
        
        UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());

       	FsmSerialTool.getInstance().saveFsm(fsm);
        
        LoggerServiceProxy.getInstance().getLogger(BunnyTest.class).debug(fsm.getAlphabet().size());
        LoggerServiceProxy.getInstance().getLogger(BunnyTest.class).debug(fsm.getStatesSet().size());
        LoggerServiceProxy.getInstance().getLogger(BunnyTest.class).debug(fsm.getCloneTransitionFunction().size());
    }

    @Test
    public void multi() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine fsm = new FiniteStateMachine("supervisor1");
        fsm.addState(new State("1", State.INITIAL, State.MARKED));
        fsm.addState(new State("2", State.NOTINITIAL, State.MARKED));
        fsm.addEvent(new Event("pep1", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm.addTransition("1", "pep1", "2");
        fsm.addTransition("2", "protraction1", "1");
        fsm.accept(new GraphvizVisitor(GraphvizVisitor.NAME));

        final FiniteStateMachine fsm2 = new FiniteStateMachine("supervisor2");
        fsm2.addState(new State("1", State.INITIAL, State.MARKED));
        fsm2.addEvent(new Event("pea1", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        fsm2.addTransition("1", "pea1", "1");
        fsm2.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
    }

    public void testPatteS() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine patte = new FiniteStateMachine("system");
        patte.addState(new State("Ret1", State.INITIAL, State.MARKED));
        patte.addState(new State("Att1", State.NOTINITIAL, State.NOTMARKED));
        patte.addState(new State("Prot1", State.NOTINITIAL, State.NOTMARKED));
        patte.addState(new State("Fail", State.NOTINITIAL, State.NOTMARKED));
        patte.addEvent(new Event("pep1", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        patte.addEvent(new Event("pea1", Event.UNCONTROLLABLE, Event.OBSERVABLE));

        patte.addTransition("Ret1", "pep1", "Att1");
        patte.addTransition("Att1", "error", "Fail");
        patte.addTransition("Att1", "protraction1", "Prot1");
        patte.addTransition("Prot1", "pea1", "Ret1");

        patte.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
    }

    @Test
    public void testSemOp() {
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final FiniteStateMachine patte1 = new FiniteStateMachine("patte1");
        patte1.addState(new State("Ret1", State.INITIAL, State.MARKED));
        patte1.addState(new State("Att1", State.NOTINITIAL, State.NOTMARKED));
        patte1.addState(new State("Prot1", State.NOTINITIAL, State.NOTMARKED));
        patte1.addEvent(new Event("pep1", Event.UNCONTROLLABLE, Event.OBSERVABLE));
        patte1.addEvent(new Event("pea1", Event.UNCONTROLLABLE, Event.OBSERVABLE));

        patte1.addTransition("Ret1", "pep1", "Att1");
        patte1.addTransition("Att1", "protraction1", "Prot1");
        patte1.addTransition("Prot1", "pea1", "Ret1");

        patte1.accept(new GraphvizVisitor(GraphvizVisitor.NAME));
        FsmOpSem exec = null;
        try {
            exec = new FsmOpSem(patte1);
            if (exec.update(new Event("pep1", Event.UNCONTROLLABLE, Event.OBSERVABLE))) {
                ;
            }
            exec.next();
            if (exec.update(new Event("pea1", Event.UNCONTROLLABLE, Event.OBSERVABLE))) {
                ;
            }
            exec.next();
            if (exec.update(new Event("protraction1", Event.CONTROLLABLE, Event.OBSERVABLE))) {
                ;
            }
            exec.next();
            if (exec.update(new Event("pea1", Event.UNCONTROLLABLE, Event.OBSERVABLE))) {
                ;
            }
            exec.next();
            if (exec.update(new Event("pep1", Event.UNCONTROLLABLE, Event.OBSERVABLE))) {
                ;
            }
            exec.next();
        } catch (final ExecInitExeception e) {

            e.printStackTrace();
            Assert.fail();
        }

    }

    // @Test
    // public void bunnyPatte() {
    // FiniteStateMachine patte1 = new FiniteStateMachine("patte1");
    // patte1.addState(new State("Ret1", State.INITIAL, State.NOTMARKED));
    // patte1.addState(new State("Att1", State.NOTINITIAL, State.MARKED));
    // patte1.addState(new State("Prot1", State.NOTINITIAL, State.NOTMARKED));
    // patte1.addEvent(new Event("pep1", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte1.addEvent(new Event("pea1", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    //
    // patte1.addTransition("Ret1", "pep1", "Att1");
    // patte1.addTransition("Att1", "protraction1", "Prot1");
    // patte1.addTransition("Prot1", "pea1", "Ret1");
    //
    // // patte1.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine patte2 = new FiniteStateMachine("patte2");
    // patte2.addState(new State("Ret2", State.INITIAL, State.NOTMARKED));
    // patte2.addState(new State("Att2", State.NOTINITIAL, State.MARKED));
    // patte2.addState(new State("Prot2", State.NOTINITIAL, State.NOTMARKED));
    // patte2.addEvent(new Event("pep2", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte2.addEvent(new Event("pea2", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    //
    // patte2.addTransition("Ret2", "pep2", "Att2");
    // patte2.addTransition("Att2", "protraction2", "Prot2");
    // patte2.addTransition("Prot2", "pea2", "Ret2");
    //
    // // patte2.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine patte3 = new FiniteStateMachine("patte3");
    // patte3.addState(new State("Ret3", State.INITIAL, State.NOTMARKED));
    // patte3.addState(new State("Att3", State.NOTINITIAL, State.MARKED));
    // patte3.addState(new State("Prot3", State.NOTINITIAL, State.NOTMARKED));
    // patte3.addEvent(new Event("pep3", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte3.addEvent(new Event("pea3", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte3.addTransition("Ret3", "pep3", "Att3");
    // patte3.addTransition("Att3", "protraction3", "Prot3");
    // patte3.addTransition("Prot3", "pea3", "Ret3");
    //
    // // patte3.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine patte4 = new FiniteStateMachine("patte4");
    // patte4.addState(new State("Ret4", State.INITIAL, State.NOTMARKED));
    // patte4.addState(new State("Att4", State.NOTINITIAL, State.MARKED));
    // patte4.addState(new State("Prot4", State.NOTINITIAL, State.NOTMARKED));
    // patte4.addEvent(new Event("pep4", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte4.addEvent(new Event("pea4", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte4.addTransition("Ret4", "pep4", "Att4");
    // patte4.addTransition("Att4", "protraction4", "Prot4");
    // patte4.addTransition("Prot4", "pea4", "Ret4");
    //
    // // patte4.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine patte5 = new FiniteStateMachine("patte5");
    // patte5.addState(new State("Ret5", State.INITIAL, State.NOTMARKED));
    // patte5.addState(new State("Att5", State.NOTINITIAL, State.MARKED));
    // patte5.addState(new State("Prot5", State.NOTINITIAL, State.NOTMARKED));
    // patte5.addEvent(new Event("pep5", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte5.addEvent(new Event("pea5", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte5.addTransition("Ret5", "pep5", "Att5");
    // patte5.addTransition("Att5", "protraction5", "Prot5");
    // patte5.addTransition("Prot5", "pea5", "Ret5");
    //
    // // patte5.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine patte6 = new FiniteStateMachine("patte6");
    // patte6.addState(new State("Ret6", State.INITIAL, State.NOTMARKED));
    // patte6.addState(new State("Att6", State.NOTINITIAL, State.MARKED));
    // patte6.addState(new State("Prot6", State.NOTINITIAL, State.NOTMARKED));
    // patte6.addEvent(new Event("pep6", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte6.addEvent(new Event("pea6", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // patte6.addTransition("Ret6", "pep6", "Att6");
    // patte6.addTransition("Att6", "protraction6", "Prot6");
    // patte6.addTransition("Prot6", "pea6", "Ret6");
    //
    // // patte6.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine specautoloop = new FiniteStateMachine("specautoloop");
    // specautoloop.addState(new State("Y", State.INITIAL, State.MARKED));
    // specautoloop.addEvent(new Event("pep1", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addEvent(new Event("pep2", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addEvent(new Event("pep3", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addEvent(new Event("pep4", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addEvent(new Event("pep5", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addEvent(new Event("pep6", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // specautoloop.addTransition("Y", "pep1", "Y");
    // specautoloop.addTransition("Y", "pep2", "Y");
    // specautoloop.addTransition("Y", "pep3", "Y");
    // specautoloop.addTransition("Y", "pep4", "Y");
    // specautoloop.addTransition("Y", "pep5", "Y");
    // specautoloop.addTransition("Y", "pep6", "Y");
    // // specautoloop.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    //
    // FiniteStateMachine spec1 = new FiniteStateMachine("spec1");
    // spec1.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec1.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec1.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec1.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec1.addState(new State("X", State.INITIAL, State.MARKED));
    // spec1.addEvent(new Event("pea1", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec1.addEvent(new Event("pea2", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec1.addTransition("X", "protraction1", "A");
    // spec1.addTransition("X", "protraction2", "C");
    // spec1.addTransition("A", "pea1", "B");
    // spec1.addTransition("C", "pea2", "D");
    // spec1.addTransition("B", "protraction2", "C");
    // spec1.addTransition("D", "protraction1", "A");
    // // spec1.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec1 = FsmTools.getInstance().syncProduct(specautoloop, spec1);
    //
    // FiniteStateMachine spec2 = new FiniteStateMachine("spec2");
    // spec2.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec2.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec2.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec2.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec2.addState(new State("X", State.INITIAL, State.MARKED));
    // spec2.addEvent(new Event("pea2", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec2.addEvent(new Event("pea3", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec2.addTransition("X", "protraction2", "A");
    // spec2.addTransition("X", "protraction3", "C");
    // spec2.addTransition("A", "pea2", "B");
    // spec2.addTransition("C", "pea3", "D");
    // spec2.addTransition("B", "protraction3", "C");
    // spec2.addTransition("D", "protraction2", "A");
    // // spec2.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec2 = FsmTools.getInstance().syncProduct(specautoloop, spec2);
    //
    // FiniteStateMachine spec3 = new FiniteStateMachine("spec3");
    // spec3.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec3.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec3.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec3.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec3.addState(new State("X", State.INITIAL, State.MARKED));
    // spec3.addEvent(new Event("pea4", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec3.addEvent(new Event("pea3", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec3.addTransition("X", "protraction4", "A");
    // spec3.addTransition("X", "protraction3", "C");
    // spec3.addTransition("A", "pea4", "B");
    // spec3.addTransition("C", "pea3", "D");
    // spec3.addTransition("B", "protraction3", "C");
    // spec3.addTransition("D", "protraction4", "A");
    // // spec3.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec3 = FsmTools.getInstance().syncProduct(specautoloop, spec3);
    //
    // FiniteStateMachine spec4 = new FiniteStateMachine("spec4");
    // spec4.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec4.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec4.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec4.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec4.addState(new State("X", State.INITIAL, State.MARKED));
    // spec4.addEvent(new Event("pea4", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec4.addEvent(new Event("pea5", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec4.addTransition("X", "protraction4", "A");
    // spec4.addTransition("X", "protraction5", "C");
    // spec4.addTransition("A", "pea4", "B");
    // spec4.addTransition("C", "pea5", "D");
    // spec4.addTransition("B", "protraction5", "C");
    // spec4.addTransition("D", "protraction4", "A");
    // // spec4.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec4 = FsmTools.getInstance().syncProduct(specautoloop, spec4);
    //
    // FiniteStateMachine spec5 = new FiniteStateMachine("spec5");
    // spec5.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec5.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec5.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec5.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec5.addState(new State("X", State.INITIAL, State.MARKED));
    // spec5.addEvent(new Event("pea6", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec5.addEvent(new Event("pea5", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec5.addTransition("X", "protraction6", "A");
    // spec5.addTransition("X", "protraction5", "C");
    // spec5.addTransition("A", "pea6", "B");
    // spec5.addTransition("C", "pea5", "D");
    // spec5.addTransition("B", "protraction5", "C");
    // spec5.addTransition("D", "protraction6", "A");
    // // spec5.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec5 = FsmTools.getInstance().syncProduct(specautoloop, spec5);
    //
    // FiniteStateMachine spec6 = new FiniteStateMachine("spec6");
    // spec6.addState(new State("A", State.NOTINITIAL, State.MARKED));
    // spec6.addState(new State("B", State.NOTINITIAL, State.MARKED));
    // spec6.addState(new State("C", State.NOTINITIAL, State.MARKED));
    // spec6.addState(new State("D", State.NOTINITIAL, State.MARKED));
    // spec6.addState(new State("X", State.INITIAL, State.MARKED));
    // spec6.addEvent(new Event("pea6", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec6.addEvent(new Event("pea1", Event.UNCONTROLLABLE,
    // Event.OBSERVABLE));
    // spec6.addTransition("X", "protraction6", "A");
    // spec6.addTransition("X", "protraction1", "C");
    // spec6.addTransition("A", "pea6", "B");
    // spec6.addTransition("C", "pea1", "D");
    // spec6.addTransition("B", "protraction1", "C");
    // spec6.addTransition("D", "protraction6", "A");
    // // spec6.visit(new GraphvizVisitor(GraphvizVisitor.NAME));
    // spec6 = FsmTools.getInstance().syncProduct(specautoloop, spec6);
    //
    // FiniteStateMachine system1 = FsmTools.getInstance().syncProduct(patte1,
    // patte2);
    // FiniteStateMachine superviseur1 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec1,
    // system1));
    // superviseur1.setName("S1");
    // superviseur1.visit(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
    // FiniteStateMachine system2 = FsmTools.getInstance().syncProduct(patte2,
    // patte3);
    // FiniteStateMachine superviseur2 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec2,
    // system2));
    // superviseur2.setName("S2");
    // FiniteStateMachine system3 = FsmTools.getInstance().syncProduct(patte3,
    // patte4);
    // FiniteStateMachine superviseur3 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec3,
    // system3));
    // superviseur3.setName("S3");
    // FiniteStateMachine system4 = FsmTools.getInstance().syncProduct(patte4,
    // patte5);
    // FiniteStateMachine superviseur4 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec4,
    // system4));
    // superviseur4.setName("S4");
    // FiniteStateMachine system5 = FsmTools.getInstance().syncProduct(patte5,
    // patte6);
    // FiniteStateMachine superviseur5 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec5,
    // system5));
    // superviseur5.setName("S5");
    // FiniteStateMachine system6 = FsmTools.getInstance().syncProduct(patte6,
    // patte1);
    // FiniteStateMachine superviseur6 =
    // FsmTools.getInstance().trim(FsmTools.getInstance().supremalControllable(spec6,
    // system6));
    // superviseur6.setName("S6");
    // /*
    // * FiniteStateMachine
    // systemA=FsmTools.getInstance().syncProduct(system1,system2);
    // * FiniteStateMachine
    // systemB=FsmTools.getInstance().syncProduct(system3,system4);
    // * FiniteStateMachine
    // systemC=FsmTools.getInstance().syncProduct(system5,system6);
    // * FiniteStateMachine
    // systemD=FsmTools.getInstance().syncProduct(systemA,systemB);
    // * FiniteStateMachine
    // system=FsmTools.getInstance().syncProduct(systemD,systemC);
    // *
    // * system.setName("G"); system.visit(new
    // * GraphvizVisitor(GraphvizVisitor.NOLABEL));
    // */
    //
    // FiniteStateMachine superviseurA =
    // FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur1,
    // superviseur2));
    // superviseurA.setName("SA");
    // superviseurA.visit(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
    // FiniteStateMachine superviseurB =
    // FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur3,
    // superviseur4));
    // superviseurB.setName("SB");
    // FiniteStateMachine superviseurC =
    // FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur5,
    // superviseur6));
    // superviseurC.setName("SC");
    // FiniteStateMachine superviseurD =
    // FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseurA,
    // superviseurB));
    // superviseurD.setName("SD");
    // superviseurD.visit(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
    // FiniteStateMachine superviseur =
    // FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseurC,
    // superviseurD));
    //
    // superviseur.setName("S");
    // superviseur.visit(new GraphvizVisitor(GraphvizVisitor.NOLABEL));
    // superviseur.visit(new JavaVisitor());
    //
    // /*
    // * FiniteStateMachine
    // *
    // closeLoop=FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(superviseur,system));
    // * closeLoop.setName("closeLoop"); closeLoop.visit(new
    // * GraphvizVisitor(GraphvizVisitor.ID));
    // */
    //
    // }

}
