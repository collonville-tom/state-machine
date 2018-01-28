package org.tc.osgi.bundle.ts.destkit.utils;

import java.io.FileNotFoundException;

import org.tc.osgi.bundle.ts.destkit.metamodel.FiniteStateMachine;
import org.tc.osgi.bundle.ts.destkit.module.service.LoggerServiceProxy;
import org.tc.osgi.bundle.ts.destkit.utils.exception.FiniteStateMachineNotFoundFileException;
import org.tc.osgi.bundle.ts.m3.core.ITs;
import org.tc.osgi.bundle.ts.m3.utils.TSSerialTool;
import org.tc.osgi.bundle.ts.m3.utils.exception.TSFileNotFoundException;
import org.tc.osgi.bundle.utils.interf.conf.exception.FieldTrackingAssignementException;


/**
 * FsmSerialTool.java.
 * @author thomas collonvillé
 * @version 0.0.1
 */
public class FsmSerialTool {

	protected static FsmSerialTool serialTool = null;
    /**
     * getInstance.
     * @return FsmSerialTool
     */
    public static FsmSerialTool getInstance() {
    	if (FsmSerialTool.serialTool == null) {
    		FsmSerialTool.serialTool = new FsmSerialTool();
        }
        return FsmSerialTool.serialTool;
    }

    /**
     * FsmSerialTool constructor.
     */
    protected FsmSerialTool() {
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

    /**
     * readFsm.
     * @param name String
     * @return FiniteStateMachine
     * @throws FieldTrackingAssignementException
     * @throws TSFileNotFoundException
     */
    public FiniteStateMachine readFsm(final String name) throws FieldTrackingAssignementException, TSFileNotFoundException {
        final ITs ts = TSSerialTool.getInstance().readTS(name);
        if (!(ts instanceof FiniteStateMachine)) {
            throw new FiniteStateMachineNotFoundFileException("Le fichier ne contient pas de structure de FSM");
        }
        return (FiniteStateMachine) ts;
    }

    /**
     * saveFsm.
     * @param k FiniteStateMachine
     */
    public void saveFsm(final FiniteStateMachine k) {
        try {
        	TSSerialTool.getInstance().saveTS(k);
        } catch (final FieldTrackingAssignementException e) {
        	LoggerServiceProxy.getInstance().getLogger(FsmSerialTool.class).error(e);
        } catch (final FileNotFoundException e) {
        	LoggerServiceProxy.getInstance().getLogger(FsmSerialTool.class).error(e);
        }
    }

	public String getDefaultSerialisationDir() {
		return TSSerialTool.getInstance().getDefaultSerialisationDir();
				
	}

}
