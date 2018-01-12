package org.tc.osgi.bundle.ts.kripke.metamodel;

import java.util.Collection;
import java.util.HashSet;

import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.utils.collection.Collections;
import org.tc.osgi.bundle.utils.collection.IPredicate;
import org.tc.osgi.bundle.utils.collection.ITransformer;

@SuppressWarnings("serial")
public class PropAtomSet extends HashSet<PropAtom> implements Cloneable {

    /**
     * long serialVersionUID.
     */
    private static final long serialVersionUID = -6586015386739136353L;

    public PropAtomSet() {
    }

    public boolean addPropAtom(final PropAtom e) {
        if (!containsPropAtom(e)) {
            return super.add(e);
        }
        return false;

    }

    @Override
    public PropAtomSet clone() {
        return (PropAtomSet) Collections.getInstance().collect(this, new ITransformer<PropAtom>() {

            @Override
            public void evaluate(final Collection<PropAtom> c, final PropAtom e) {
                ((PropAtomSet) c).addPropAtom(e.clone());
            }
        });
    }

    public boolean containsPropAtom(final PropAtom e) {
        final PropAtom propAtom = e;
        if (Collections.getInstance().extract(this, new IPredicate<PropAtom>() {

            @Override
            public boolean evaluate(final PropAtom e1) {
                if (e1.equals(propAtom)) {
                    return true;
                }
                return false;
            }

        }) != null) {
            return true;
        }
        return false;
    }

    public boolean removePropAtom(final PropAtom e) {
        final PropAtom s = e;
        final PropAtom propAtom = Collections.getInstance().extract(this, new IPredicate<PropAtom>() {

            @Override
            public boolean evaluate(final PropAtom e1) {
                if (e1.equals(s)) {
                    return true;
                }
                return false;
            }
        });
        if (propAtom != null) {
            return super.remove(propAtom);
        }
        return false;

    }

    @Override
    public String toString() {
        final StringBuffer buff = new StringBuffer("\nPropAtomSet:");
        for (final PropAtom e : this) {
            buff.append("\n");
            buff.append(e.toString());
        }
        return buff.toString();
    }

}
