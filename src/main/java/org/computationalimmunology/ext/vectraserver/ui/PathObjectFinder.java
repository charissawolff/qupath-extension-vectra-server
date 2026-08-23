package org.computationalimmunology.ext.vectraserver.ui;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;

import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.viewer.QuPathViewer;
import qupath.lib.objects.PathObject;
import qupath.lib.objects.hierarchy.PathObjectHierarchy;

public class PathObjectFinder {
    
    private PathObjectFinder() {
        /* Should not be instantiated */
    }

    public static PathObject execute(String id) {
        QuPathViewer viewer = QuPathGUI.getInstance().getViewer();
        if (viewer == null || viewer.getImageData() == null) {
            VectraServerLog.error("No image open, cannot select annotation: " + id);
            return null;
        }
        PathObjectHierarchy hierarchy = viewer.getImageData().getHierarchy();
        PathObject selectedObject = hierarchy.getAnnotationObjects().stream()
                .filter(o -> id.equals(o.getMetadata().get("id")))
                .findFirst()
                .orElse(null);
        return selectedObject;
    }
}
