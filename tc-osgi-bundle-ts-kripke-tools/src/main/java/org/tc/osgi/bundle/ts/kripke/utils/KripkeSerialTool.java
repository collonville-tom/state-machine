package org.tc.osgi.bundle.ts.kripke.utils;

import java.io.FileNotFoundException;

import org.tc.osgi.bundle.ts.kripke.metamodel.Kripke;
import org.tc.osgi.bundle.ts.kripke.utils.exception.KripkeNotFoundFileException;
import org.tc.osgi.bundle.ts.m3.ITs;
import org.tc.osgi.bundle.ts.utils.TSSerialTool;
import org.tc.osgi.bundle.ts.utils.exception.TSFileNotFoundException;
import org.tc.osgi.bundle.utils.exception.FieldTrackingAssignementException;
import org.tc.osgi.bundle.utils.logger.LoggerGestionnary;

public class KripkeSerialTool extends TSSerialTool {

    public static KripkeSerialTool getInstance() {
        if (TSSerialTool.serialTool == null) {
            TSSerialTool.serialTool = new KripkeSerialTool();
        } else {
            if (!(TSSerialTool.serialTool instanceof KripkeSerialTool)) {
                TSSerialTool.serialTool = new KripkeSerialTool();
            }
        }
        return (KripkeSerialTool) TSSerialTool.serialTool;
    }

    protected KripkeSerialTool() {
        super();
    }

    // private void initPath(boolean xml2Fsm) {
    // JFileChooser chooser = new JFileChooser();
    // if (xml2Fsm)
    // chooser.setDialogTitle("XML2Fsm");
    // else
    // chooser.setDialogTitle("Fsm2XML");
    // chooser.setMultiSelectionEnabled(false);
    //
    // if (path == null)
    // chooser.setCurrentDirectory(new File(defaultPath));
    // else
    // chooser.setCurrentDirectory(new File(path));
    // chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    // chooser.setVisible(true);
    // int retval = chooser.showDialog(null, null);
    // if (retval == JFileChooser.APPROVE_OPTION) {
    // File file = chooser.getSelectedFile();
    // path = file.getAbsolutePath();
    // }
    // }

    public Kripke readKripke(final String name) throws FieldTrackingAssignementException, TSFileNotFoundException {
        final ITs ts = readTS(name);
        if (!(ts instanceof Kripke)) {
            throw new KripkeNotFoundFileException("Le fichier ne contient pas de structure de Kripke");
        }
        return (Kripke) ts;
    }

    public void saveKripke(final Kripke k) {
        try {
            saveTS(k);
        } catch (final FieldTrackingAssignementException e) {
            LoggerGestionnary.getInstance(KripkeSerialTool.class).error(e);
        } catch (final FileNotFoundException e) {
            LoggerGestionnary.getInstance(KripkeSerialTool.class).error(e);
        }
    }

}
