package org.computationalimmunology.ext.vectraserver.ui.listeners;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.viewer.QuPathViewer;
import qupath.lib.images.ImageData;
import qupath.lib.objects.PathObject;
import qupath.lib.objects.hierarchy.PathObjectHierarchy;
import qupath.lib.objects.hierarchy.events.PathObjectHierarchyEvent;
import qupath.lib.objects.hierarchy.events.PathObjectHierarchyListener;

/**
* Tracks changes to the polygon hierarchy
* Specifically, tracks when user ADDS a new polygon, so that we can save it to the server 
* Note: when I change the figure of a polygon currently there, the events change other fire, and then it's reigistered as "added"
* Sometimes when I create the polygon it also first fired change "other" and then "added". I have to check if the polygon is already in the newAnnotations list before adding it, to avoid duplicates.
*/
public class PolygonTracker implements PathObjectHierarchyListener  {
    PathObjectHierarchy hierarchy;
    private final ObservableList<PathObject> newAnnotations = FXCollections.observableArrayList();
    private final ObservableBooleanValue enabled;
    private Set<PathObject> knownObjects = new HashSet<>(); // necessary to track the objects added by pixel classifier as they dont trigger an "added" event

    public PolygonTracker(ObservableBooleanValue enabled) {
        this.enabled = enabled;
        QuPathViewer viewer = QuPathGUI.getInstance().getViewer();
        if (viewer == null) {
            return;
        }
        if (viewer.getImageData() != null) {
            this.hierarchy = viewer.getImageData().getHierarchy();
            this.hierarchy.addListener(this);
            this.knownObjects = this.hierarchy == null ? new HashSet<>() : new HashSet<>(this.hierarchy.getAllObjects(false));
        }
        //if the viewer changes, we need to update the hierarchy listener AND the newAnnotations list 
        ReadOnlyObjectProperty<ImageData<BufferedImage>> imageDataProperty = viewer.imageDataProperty();
        imageDataProperty.addListener((observable, oldValue, newValue) -> {
            newAnnotations.clear(); //clear the newAnnotations list when the viewer changes,
                //  since we don't want to keep track of polygons from a different image
            if (this.hierarchy != null) {
                this.hierarchy.removeListener(this);
            }
            this.hierarchy = newValue == null ? null : newValue.getHierarchy();
            
            if (this.hierarchy != null) {
                this.hierarchy.addListener(this);
            }
        });
    }

    @Override
    public void hierarchyChanged(PathObjectHierarchyEvent event) {
        if (!enabled.get()) {
            return;
        }
        //change the newAnnotations list to only contain annotations that are still in the hierarchy, in case the user deleted some of them
        //but that it wasn't registered (such as deleting from hierarchy tab)
        newAnnotations.removeIf(obj -> obj.getParent() == null);
        VectraServerLog.log("PathObjectHierarchyEvent changed: " + event);
        if (event.getChangedObjects() != null){ 
            VectraServerLog.log("Polygon class changed: " + event.getChangedObjects());
        }
        // if the user added, deleted or did "other structure changed", this will thus consist of manually adding polygons or via for example pixel classifier
        // we compare what was there before and what is there now
        if (event.isStructureChangeEvent()) {
            Set<PathObject> current = new HashSet<>(hierarchy.getAllObjects(false));

            Set<PathObject> added = new HashSet<>(current);
            added.removeAll(knownObjects);
            for (PathObject addedObject : added) {
                //if they have ID, skip, as this means they were already on the server, or if they are a point ignore
                if (addedObject.getROI() != null && !addedObject.getROI().isPoint()
                        && addedObject.getMetadata().get("id") == null) {
                    VectraServerLog.log("Tracking new annotation: " + addedObject);
                    newAnnotations.remove(addedObject); // guard against dupes, harmless if absent
                    newAnnotations.add(addedObject);
                
            }

            Set<PathObject> removed = new HashSet<>(knownObjects);
            removed.removeAll(current);
            newAnnotations.removeAll(removed);

            knownObjects = current;
            }
        }
    }

    public ObservableList<PathObject> getNewAnnotations() {
        return newAnnotations;
    }
}
    


    