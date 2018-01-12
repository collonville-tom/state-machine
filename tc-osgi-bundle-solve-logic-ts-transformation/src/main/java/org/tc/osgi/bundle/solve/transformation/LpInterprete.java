package org.tc.osgi.bundle.solve.transformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tc.osgi.bundle.logic.lp.metamodel.Et;
import org.tc.osgi.bundle.logic.lp.metamodel.F;
import org.tc.osgi.bundle.logic.lp.metamodel.Not;
import org.tc.osgi.bundle.logic.lp.metamodel.Ou;
import org.tc.osgi.bundle.logic.lp.metamodel.Pa;
import org.tc.osgi.bundle.logic.lp.metamodel.T;
import org.tc.osgi.bundle.logic.m3.AbstractFormule;
import org.tc.osgi.bundle.logic.m3.IPa;
import org.tc.osgi.bundle.solve.m3.AbstractInterprete;
import org.tc.osgi.bundle.solve.m3.AbstractSatisfy;

/**
 * LpInterprete.java.
 * @author thomas collonvillé
 * @version 0.0.1
 *
 */
public class LpInterprete extends AbstractInterprete {

    /*
     * Lp:: T | F | _Pa_ | Et(Lp,Lp) | Ou(Lp,Lp) | Not(Lp)
     */

    /**
     * LpInterprete constructor.
     * @param _formule String
     */
    public LpInterprete(final String _formule) {
        super(_formule);
    }

    /**
     * @param sk AbstractSatisfy
     * @return AbstractFormule
     * @see org.tc.osgi.bundle.solve.m3.AbstractInterprete#text2Formule(org.tc.osgi.bundle.solve.m3.AbstractSatisfy)
     */
    @Override
    public AbstractFormule text2Formule(final AbstractSatisfy sk) {
        System.out.println(getStringformule());
        while (true) {
            switch (getStringformule().charAt(0)) {
                case 'T': {
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return new T();
                }

                case 'F': {
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return new F();
                }

                case 'e': {
                    setStringformule(getStringformule().substring(3, getStringformule().length()));
                    final AbstractFormule firstLp = text2Formule(sk);
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    final Et et = new Et(firstLp, text2Formule(sk));
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return et;
                }
                case 'o': {
                    setStringformule(getStringformule().substring(3, getStringformule().length()));
                    final AbstractFormule firstLp = text2Formule(sk);
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    final Ou ou = new Ou(firstLp, text2Formule(sk));
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return ou;
                }
                case 'n': {
                    setStringformule(getStringformule().substring(4, getStringformule().length()));
                    final Not not = new Not(text2Formule(sk));
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return not;
                }
                case '_': {
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    final Pattern chainePattern = Pattern.compile("_", Pattern.CASE_INSENSITIVE);
                    final Matcher chaineMatcher = chainePattern.matcher(getStringformule());
                    String stringPa = null;
                    if (chaineMatcher.find()) {
                        stringPa = getStringformule().substring(0, chaineMatcher.end() - 1);
                    }
                    final IPa pa = ((LpSatisfyOnIState) sk).getPa(stringPa);
                    setStringformule(getStringformule().substring(stringPa.length(), getStringformule().length()));
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return (Pa) pa;
                }
                default: {
                    setStringformule(getStringformule().substring(1, getStringformule().length()));
                    return null;
                }
            }
        }
    }
}