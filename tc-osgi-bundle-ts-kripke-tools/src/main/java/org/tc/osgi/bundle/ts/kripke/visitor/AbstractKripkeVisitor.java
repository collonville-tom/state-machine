package org.tc.osgi.bundle.ts.kripke.visitor;

import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitable;
import org.tc.osgi.bundle.utils.interf.pattern.visitor.IVisitor;


/**
 * AbstractKripkeVisitor.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public abstract class AbstractKripkeVisitor implements IVisitor {

    /**
     * @param o Object
     * @see org.tc.osgi.bundle.utils.visitor.AbstractVisitor#visit(java.lang.Object)
     */
    @Override
    public void visit(final IVisitable o) {
        final Kripke k = (Kripke) o;
        if (k != null) {
            this.visit(k);
        }
    }

    /**
     * visit.
     * @param k Kripke
     */
    public abstract void visit(Kripke k);

}
