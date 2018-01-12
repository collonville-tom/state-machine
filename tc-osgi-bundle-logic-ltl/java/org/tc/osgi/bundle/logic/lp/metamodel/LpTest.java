package org.tc.osgi.bundle.logic.lp.metamodel;

import static org.junit.Assert.*;

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
		Pa pa1=new Pa("pa1");
		Pa pa2=new Pa("pa2");
		
		
		Et form=new Et(pa1,pa2);
		Assert.assertEquals(F.class,form.evaluate().getClass());
		pa1.setIs(true);
		Assert.assertEquals(F.class,form.evaluate().getClass());
		pa2.setIs(true);
		Assert.assertEquals(T.class,form.evaluate().getClass());
	}
	

	/**
	 * testOu.
	 */
	@Test
	public void testOu() {
		Pa pa1=new Pa("pa1");
		Pa pa2=new Pa("pa2");
		
		
		Ou form=new Ou(pa1,pa2);
		Assert.assertEquals(F.class,form.evaluate().getClass());
		pa1.setIs(true);
		Assert.assertEquals(T.class,form.evaluate().getClass());
		pa2.setIs(true);
		Assert.assertEquals(T.class,form.evaluate().getClass());
	}
	
	/**
	 * testNot.
	 */
	@Test
	public void testNot() {
		Pa pa1=new Pa("pa1");
				
		
		Not form=new Not(pa1);
		Assert.assertEquals(T.class,form.evaluate().getClass());
		pa1.setIs(true);
		Assert.assertEquals(F.class,form.evaluate().getClass());
		
	}

}
