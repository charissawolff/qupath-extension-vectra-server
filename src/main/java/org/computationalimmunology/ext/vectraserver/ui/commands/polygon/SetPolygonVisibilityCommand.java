package org.computationalimmunology.ext.vectraserver.ui.commands.polygon;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;

import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.viewer.OverlayOptions;
import qupath.lib.objects.classes.PathClass;

public class SetPolygonVisibilityCommand {

    private final String polygonId;
    private final boolean visible;

    public SetPolygonVisibilityCommand(String polygonId, boolean visible) {
        this.polygonId = polygonId;
        this.visible = visible;
    }

    public void execute() {
        OverlayOptions overlayOptions = QuPathGUI.getInstance().getOverlayOptions();
        if (overlayOptions == null) {
            VectraServerLog.error("No overlay options available, cannot toggle polygon visibility for id: " + polygonId);
            return;
        }
        overlayOptions.setPathClassHidden(PathClass.getInstance(polygonId), !visible);
    }
}