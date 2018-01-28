package org.tc.osgi.bundle.ts.destkit.visitor;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.FsmSerialTool;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;


public abstract class AbstractJavaVisitor extends AbstractFsmVisitor {

    public static final String javaExtension = ".java";

    protected abstract String generateClass(FiniteStateMachine fsm);

    protected abstract String generateConstructor(FiniteStateMachine fsm);

    protected abstract String generateDataMembers(FiniteStateMachine fsm);

    protected abstract String generateImport();

    /**
     * generateJavaFile.
     * @param fsm FiniteStateMachine
     * @return
     */
    private void generateJavaFile(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer();
        buff.append(generateImport());
        buff.append(generateClass(fsm));
        try {
            final PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(FsmSerialTool.getInstance().getDefaultSerialisationDir() + fsm.getName()
                + AbstractJavaVisitor.javaExtension)));
            ps.print(buff.toString());
            ps.close();
        } catch (final FileNotFoundException e) {
        	LoggerServiceProxy.getInstance().getLogger(AbstractJavaVisitor.class).error(e);
        } 
    }

    protected abstract String generateMethods(FiniteStateMachine fsm);

    @Override
    public void visit(final FiniteStateMachine fsm) {
        generateJavaFile(fsm);

    }

}
