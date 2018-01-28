package org.tc.osgi.bundle.ts.destkit;

import org.junit.Assert;
import org.junit.Test;
import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.module.service.CollectionUtilsServiceProxy;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.FsmSerialTool;
import org.tc.osgi.bundle.ts.destkit.utils.FsmTools;
import org.tc.osgi.bundle.ts.destkit.visitor.Fsm2GraphvizVisitor;
import org.tc.osgi.bundle.ts.m3.module.service.UtilsServiceProxy;
import org.tc.osgi.bundle.ts.m3.utils.exception.TSFileNotFoundException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.module.service.impl.CollectionUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.LoggerUtilsServiceImpl;
import org.tc.osgi.bundle.utils.module.service.impl.UtilsServiceImpl;


public class CuveTest {

    @Test
    public void testcuve() {
    	UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
    	
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final State a = new State("A", State.INITIAL, State.MARKED);
        final State m = new State("M", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine ma = new FiniteStateMachine("MA");
        ma.addState(a);
        ma.addState(m);
        ma.addTransition("A", "m", "M");
        ma.addTransition("M", "a", "A");
        try {
        ma.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));
        }catch (Exception e)
        {
        	e.printStackTrace();
        	Assert.fail();
        }
        
        final State nn = new State("NN", State.INITIAL, State.MARKED);
        final State nb = new State("NB", State.NOTINITIAL, State.NOTMARKED);
        final State nh = new State("NH", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine cuve = new FiniteStateMachine("Cuve");
        cuve.addState(nn);
        cuve.addState(nb);
        cuve.addState(nh);
        cuve.addTransition("NN", "t1", "NB");
        cuve.addTransition("NN", "t2", "NH");
        cuve.addTransition("NB", "t3", "NN");
        cuve.addTransition("NH", "t4", "NN");
        cuve.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));

        final FiniteStateMachine systeme = FsmTools.getInstance().syncProduct(ma, cuve);
        systeme.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));

        final State init = new State("Init", State.INITIAL, State.MARKED);
        final State nonContraint = new State("NC", State.NOTINITIAL, State.NOTMARKED);
        final FiniteStateMachine spec = new FiniteStateMachine("Spec");
        spec.addState(init);
        spec.addState(nonContraint);
        spec.addTransition("Init", "m", "NC");
        spec.addTransition("NC", "a", "Init");
        spec.addTransition("NC", "t1", "NC");
        spec.addTransition("NC", "t2", "NC");
        spec.addTransition("NC", "t3", "NC");
        spec.addTransition("NC", "t4", "NC");
        spec.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));

        final FiniteStateMachine systemeC1 = FsmTools.getInstance().trim(FsmTools.getInstance().syncProduct(systeme, spec));
        systemeC1.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));
    }

    @Test
    public void testDeSerialisation() {
    	UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        FiniteStateMachine fsm;
        try {
            fsm = FsmSerialTool.getInstance().readFsm("FsmTest");
            fsm.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NOLABEL));
        } catch (final FieldTrackingAssignementException e) {
            LoggerServiceProxy.getInstance().getLogger(CuveTest.class).error(e);
        } catch (final TSFileNotFoundException e) {
            LoggerServiceProxy.getInstance().getLogger(CuveTest.class).error(e);
        }

    }

    @Test
    public void testSerialisation() {
    	UtilsServiceProxy.getInstance().setService(new UtilsServiceImpl());
        org.tc.osgi.bundle.ts.m3.module.service.LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
    	CollectionUtilsServiceProxy.getInstance().setService(new CollectionUtilsServiceImpl());
    	LoggerServiceProxy.getInstance().setService(new LoggerUtilsServiceImpl());
        final State state0 = new State("0", State.INITIAL, State.NOTMARKED);
        final State state1 = new State("1", State.NOTINITIAL, State.NOTMARKED);
        final State state2 = new State("2", State.NOTINITIAL, State.MARKED);
        final State state3 = new State("3", State.NOTINITIAL, State.MARKED);

        final FiniteStateMachine fsm = new FiniteStateMachine("FsmTest");

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

        FsmSerialTool.getInstance().saveFsm(fsm);

        fsm.accept(new Fsm2GraphvizVisitor(Fsm2GraphvizVisitor.NAME));

    }
}
