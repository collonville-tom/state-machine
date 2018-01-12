package org.tc.osgi.bundle.ts.kripke.visitor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;

import javax.swing.JFileChooser;

import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.PropAtom;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.State;
import org.tc.osgi.bundle.ts.kripke.metamodel.core.Transition;

public class Kripke2GraphvizVisitor extends AbstractKripkeVisitor {

    public static int ID = 1;
    public static int NAME = 0;
    public static int NOLABEL = 2;
    private final String defaultPath = "d:\\workspace\\MMS\\generate\\";
    private String dotExePath = "\"C:/Program Files/ATT/Graphviz/bin/dot.exe\" -Tsvg -o ";

    private final int nameOrId;
    private String path = "target/";

    public Kripke2GraphvizVisitor() {
        this(Kripke2GraphvizVisitor.NOLABEL);
    }

    public Kripke2GraphvizVisitor(final int nameOrId) {
        this.nameOrId = nameOrId;
    }

    private void generateBatFile(final Kripke k) {
        try {
            final StringBuffer buff = new StringBuffer(dotExePath);
            buff.append("\"" + new File(path).getCanonicalPath() + "\\");
            buff.append(k.getName());
            buff.append(".svg\" ");
            buff.append("\"" + new File(path).getCanonicalPath() + "\\");
            buff.append(k.getName());
            buff.append(".dot\"");

            final PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(path + "\\" + k.getName() + ".bat")));
            ps.print(buff.toString());
            ps.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void generateDotFile(final Kripke k) {
        final StringBuffer buff = new StringBuffer("digraph ");
        buff.append(k.getName());
        buff.append("{\n");
        final Iterator<PropAtom> itpa = k.getPropAtomSet().iterator();
        for (int i = 0; itpa.hasNext(); i++) {
            final PropAtom pa = itpa.next();
            buff.append("\"" + pa.getName() + "\" [label=" + pa.getName() + ",shape=box,style=filled,color=\".7 .3 1.0\"]\n");
        }
        final Iterator<State> its = k.getStatesSet().iterator();
        for (int i = 0; its.hasNext(); i++) {
            final State s = its.next();
            if (nameOrId == Kripke2GraphvizVisitor.NAME) {
                buff.append("\"" + s.getName() + "\"");
            }
            if ((nameOrId == Kripke2GraphvizVisitor.ID) || (nameOrId == Kripke2GraphvizVisitor.NOLABEL)) {
                buff.append("\"" + s.getId() + "\"");
            } else {
                if (nameOrId != Kripke2GraphvizVisitor.NOLABEL) {
                    buff.append(" [shape= circle  , width=0.5,height=0.5];\n");
                } else {
                    buff.append(" [shape= circle  , width=0.2,height=0.2 ,label=\"\"];\n");
                }
            }
            if (s.isInitial()) {
                buff.append("entryPoint" + i + " [shape=point label=\"\"];\n");
                buff.append("entryPoint" + i + " -> \"");
                if (nameOrId == Kripke2GraphvizVisitor.NAME) {
                    buff.append(s.getName());
                }
                if ((nameOrId == Kripke2GraphvizVisitor.ID) || (nameOrId == Kripke2GraphvizVisitor.NOLABEL)) {
                    buff.append(s.getId());
                }
                buff.append("\";\n");
            }
            final Iterator<PropAtom> itpa2 = s.getPropAtomSet().iterator();
            for (int j = 0; itpa2.hasNext(); j++) {
                final PropAtom pa = itpa2.next();
                buff.append("\"" + s.getName() + "\" -> \"" + pa.getName() + "\" [style=dotted, dir=none];\n");
            }
        }

        for (final Transition t : k.getTransitionFunction()) {
            buff.append("\"");
            if (nameOrId == Kripke2GraphvizVisitor.NAME) {
                buff.append(t.getInput().getName());
            }
            if ((nameOrId == Kripke2GraphvizVisitor.ID) || (nameOrId == Kripke2GraphvizVisitor.NOLABEL)) {
                buff.append(t.getInput().getId());
            }

            buff.append("\" -> \"");
            if (nameOrId == Kripke2GraphvizVisitor.NAME) {
                buff.append(t.getOutput().getName());
            }
            if ((nameOrId == Kripke2GraphvizVisitor.ID) || (nameOrId == Kripke2GraphvizVisitor.NOLABEL)) {
                buff.append(t.getOutput().getId());
            }
            buff.append("\";\n");
        }
        buff.append("}");
        try {
            final PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(path + "\\" + k.getName() + ".dot")));
            ps.print(buff.toString());
            ps.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return   Returns the dotExePath.
     */
    public String getDotExePath() {
        return dotExePath;
    }

    private void initPath() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Kripke2Graphviz");
        chooser.setMultiSelectionEnabled(false);
        if (path == null) {
            chooser.setCurrentDirectory(new File(defaultPath));
        } else {
            chooser.setCurrentDirectory(new File(path));
        }
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setVisible(true);
        final int retval = chooser.showDialog(null, null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            final File file = chooser.getSelectedFile();
            path = file.getAbsolutePath();
        }
    }

    /**
     * @param dotExePath   The dotExePath to set.
     */
    public void setDotExePath(final String dotExePath) {
        this.dotExePath = dotExePath;
    }

    @Override
    public void visit(final Kripke k) {
        // initPath();
        generateDotFile(k);
        generateBatFile(k);
    }

}
