package org.tc.osgi.bundle.ts.destkit.visitor;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitor;


/**
 * AbstractFsmVisitor.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public abstract class AbstractFsmVisitor implements IVisitor<FiniteStateMachine> {

    /**
     * visit.
     * @param fsm FiniteStateMachine
     */
    // public abstract void visit(FiniteStateMachine fsm);

    /**
     * @param _object Object
     * @see org.tc.osgi.bundle.utils.visitor.AbstractVisitor#visit(java.lang.Object)
     */
    @Override
    public void visit(final FiniteStateMachine _object) {
        if (_object instanceof FiniteStateMachine) {
            visit(_object);
        }
    }

}
