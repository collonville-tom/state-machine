package org.tc.osgi.bundle.solve.transformation;

import org.tc.osgi.bundle.logic.lp.metamodel.Pa;
import org.tc.osgi.bundle.logic.m3.IPa;
import org.tc.osgi.bundle.solve.m3.AbstractSatisfy;
import org.tc.osgi.bundle.ts.m3.core.IProperty;
import org.tc.osgi.bundle.ts.m3.core.IState;
import org.tc.osgi.bundle.ts.m3.core.ITs;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;

/**
 * LpSatisfyOnIState.java.
 * @author thomas collonvillé
 * @version 0.0.1
 *
 */
public class LpSatisfyOnIState extends AbstractSatisfy {

    /**
     * LpInterprete interprete.
     */
    private LpInterprete interprete = null;

    /**
     * LpSatisfyOnIState constructor.
     * @param ts ITs
     * @param state IState
     */
    public LpSatisfyOnIState(final ITs ts, final IState state) {
        super(ts);
        analyseState(state);
    }

    /**
     * analyseState.
     * @param state IState
     */
    private void analyseState(final IState state) {
        final String name = state.getName();
        final IState s = Collections.getInstance().extract(getTransitionStructure().getStates(), new IPredicate<IState>() {
            @Override
            public boolean evaluate(final IState e1) {
                if (e1.getName().equals(name)) {
                    return true;
                }
                return false;
            }
        });
        for (final IProperty p : getTransitionStructure().getProperty()) {
            final Pa pa = new Pa(p.getName());
            pa.setIs(false);
            for (final IProperty p1 : s.getProperty()) {
                if (p.getName() == p1.getName()) {
                    pa.setIs(true);
                }
            }
            getPropertySet().add(pa);

        }
    }

    /**
     * @param formule
     * @return String
     * @see org.tc.osgi.bundle.solve.m3.AbstractSatisfy#evaluate(java.lang.String)
     */
    @Override
    public String evaluate(final String formule) {

        interprete = new LpInterprete(formule);
        return interprete.text2Formule(this).evaluate().result();
    }

    /**
     * getPa.
     * @param pa String
     * @return IPa
     */
    public IPa getPa(final String pa) {
        final String name = pa;
        return Collections.getInstance().extract(getPropertySet(), new IPredicate<IPa>() {

            @Override
            public boolean evaluate(final IPa e1) {
                if (e1.getName().equals(name)) {
                    return true;
                }
                return false;
            }
        });
    }

}
