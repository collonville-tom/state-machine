package org.tc.osgi.bundle.ts.destkit.visitor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.State;
import org.tc.osgi.bundle.ts.destkit.metamodel.core.Transition;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.FsmSerialTool;
import org.tc.osgi.bundle.ts.m3.utils.TSSerialTool;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;


/**
 * @author   Collonvill�
 */
public class Fsm2GraphvizVisitor extends AbstractFsmVisitor {

    public static final String batExtension = ".bat";

    public static final String dotExtension = ".dot";

    public final static int ID = 1;

    public final static int NAME = 0;

    public final static int NOLABEL = 2;
    private String dotExePath = "\"C:/Program Files/ATT/Graphviz/bin/dot.exe\" -Tsvg -o ";
    private final int nameOrId;

    public Fsm2GraphvizVisitor() {
        this(Fsm2GraphvizVisitor.NOLABEL);
    }

    public Fsm2GraphvizVisitor(final int nameOrId) {
        this.nameOrId = nameOrId;
    }

    private void generateBatFile(final FiniteStateMachine fsm) {
        try {
            final StringBuffer buff = new StringBuffer(dotExePath);
            buff.append("\"" + new File(TSSerialTool.getInstance().getDefaultSerialisationDir()).getCanonicalPath() + "\\");
            buff.append(fsm.getName());
            buff.append(".svg\" ");
            buff.append("\"" + new File(TSSerialTool.getInstance().getDefaultSerialisationDir()).getCanonicalPath() + "\\");
            buff.append(fsm.getName());
            buff.append(".dot\"");

            final PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(FsmSerialTool.getInstance().getDefaultSerialisationDir() + fsm.getName()
                + Fsm2GraphvizVisitor.batExtension)));
            ps.print(buff.toString());
            ps.close();
        } catch (final FileNotFoundException e) {
        	LoggerServiceProxy.getInstance().getLogger(Fsm2GraphvizVisitor.class).error(e);
        } catch (final FieldTrackingAssignementException e) {
        	LoggerServiceProxy.getInstance().getLogger(Fsm2GraphvizVisitor.class).error(e);
        } catch (final IOException e) {
        	LoggerServiceProxy.getInstance().getLogger(Fsm2GraphvizVisitor.class).error(e);
        }
    }

    private void generateDotFile(final FiniteStateMachine fsm) {
        final StringBuffer buff = new StringBuffer("digraph ");
        buff.append(fsm.getName());
        buff.append("{\n");
        final Iterator<State> its = fsm.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            if (nameOrId == Fsm2GraphvizVisitor.NAME) {
                buff.append("\"" + s.getName() + "\"");
            }
            if ((nameOrId == Fsm2GraphvizVisitor.ID) || (nameOrId == Fsm2GraphvizVisitor.NOLABEL)) {
                buff.append("\"" + s.getId() + "\"");
            }

            if (s.isMarked()) {
                if (nameOrId != Fsm2GraphvizVisitor.NOLABEL) {
                    buff.append(" [shape= doublecircle  , width=0.5,height=0.5];\n");
                } else {
                    buff.append(" [shape= doublecircle  , width=0.2,height=0.2 ,label=\"\"];\n");
                }
            } else {
                if (nameOrId != Fsm2GraphvizVisitor.NOLABEL) {
                    buff.append(" [shape= circle  , width=0.5,height=0.5];\n");
                } else {
                    buff.append(" [shape= circle  , width=0.2,height=0.2 ,label=\"\"];\n");
                }
            }
            if (s.isInitial()) {
                buff.append("entryPoint" + i + " [shape=point label=\"\"];\n");
                buff.append("entryPoint" + i + " -> \"");
                if (nameOrId == Fsm2GraphvizVisitor.NAME) {
                    buff.append(s.getName());
                }
                if ((nameOrId == Fsm2GraphvizVisitor.ID) || (nameOrId == Fsm2GraphvizVisitor.NOLABEL)) {
                    buff.append(s.getId());
                }
                buff.append("\";\n");
            }
        }

        for (final Transition t : fsm.getTransitionFunction()) {
            buff.append("\"");
            if (nameOrId == Fsm2GraphvizVisitor.NAME) {
                buff.append(t.getInput().getName());
            }
            if ((nameOrId == Fsm2GraphvizVisitor.ID) || (nameOrId == Fsm2GraphvizVisitor.NOLABEL)) {
                buff.append(t.getInput().getId());
            }

            buff.append("\" -> \"");
            if (nameOrId == Fsm2GraphvizVisitor.NAME) {
                buff.append(t.getOutput().getName());
            }
            if ((nameOrId == Fsm2GraphvizVisitor.ID) || (nameOrId == Fsm2GraphvizVisitor.NOLABEL)) {
                buff.append(t.getOutput().getId());
            }

            buff.append("\" [label=\"");
            buff.append(t.getEvent().getName() + "\"");
            if (!t.getEvent().isControllable() && !t.getEvent().isObservable()) {
                buff.append(" color=green];\n");
            } else
                if (!t.getEvent().isControllable()) {
                    buff.append(" color=red];\n");
                } else
                    if (!t.getEvent().isObservable()) {
                        buff.append(" color=blue];\n");
                    } else {
                        buff.append("];\n");
                    }
        }
        buff.append("}");
        try {
            final PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(TSSerialTool.getInstance().getDefaultSerialisationDir() + "\\" + fsm.getName()
                + Fsm2GraphvizVisitor.dotExtension)));
            ps.print(buff.toString());
            ps.close();
        } catch (final FileNotFoundException e) {
        	LoggerServiceProxy.getInstance().getLogger(Fsm2GraphvizVisitor.class).error(e);
        } catch (final FieldTrackingAssignementException e) {
        	LoggerServiceProxy.getInstance().getLogger(Fsm2GraphvizVisitor.class).error(e);
        }
    }

    /**
     * @return   Returns the dotExePath.
     */
    public String getDotExePath() {
        return dotExePath;
    }

    /**
     * @param dotExePath   The dotExePath to set.
     */
    public void setDotExePath(final String dotExePath) {
        this.dotExePath = dotExePath;
    }

    @Override
    public void visit(final FiniteStateMachine fsm) {
        generateDotFile(fsm);
        generateBatFile(fsm);
    }

}
