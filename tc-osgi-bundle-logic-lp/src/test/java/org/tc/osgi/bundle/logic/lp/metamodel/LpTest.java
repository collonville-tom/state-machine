package org.tc.osgi.bundle.logic.lp.metamodel;

import org.junit.Assert;
import org.junit.Test;

/**
 * LpTest.java.
 * @author Collonville Thomas
 * @version 0.0.1
 * @req STD_BUNDLE_LOGIC_LP_010
 * @track SRS_BUNDLE_LOGIC_LP_010
 */
public class LpTest {

    /**
     * testEt.
     */
    @Test
    public void testEt() {
        final Pa pa1 = new Pa("pa1");
        final Pa pa2 = new Pa("pa2");

        final Et form = new Et(pa1, pa2);
        Assert.assertEquals(F.class, form.evaluate().getClass());
        pa1.setIs(true);
        Assert.assertEquals(F.class, form.evaluate().getClass());
        pa2.setIs(true);
        Assert.assertEquals(T.class, form.evaluate().getClass());
    }

    /**
     * testNot.
     */
    @Test
    public void testNot() {
        final Pa pa1 = new Pa("pa1");

        final Not form = new Not(pa1);
        Assert.assertEquals(T.class, form.evaluate().getClass());
        pa1.setIs(true);
        Assert.assertEquals(F.class, form.evaluate().getClass());

    }

    /**
     * testOu.
     */
    @Test
    public void testOu() {
        final Pa pa1 = new Pa("pa1");
        final Pa pa2 = new Pa("pa2");

        final Ou form = new Ou(pa1, pa2);
        Assert.assertEquals(F.class, form.evaluate().getClass());
        pa1.setIs(true);
        Assert.assertEquals(T.class, form.evaluate().getClass());
        pa2.setIs(true);
        Assert.assertEquals(T.class, form.evaluate().getClass());
    }

}
