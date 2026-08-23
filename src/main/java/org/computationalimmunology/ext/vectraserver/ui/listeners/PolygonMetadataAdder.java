package org.computationalimmunology.ext.vectraserver.ui.listeners;

import org.computationalimmunology.ext.vectraserver.core.SelectedSlide;
import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;
import org.computationalimmunology.ext.vectraserver.core.store.SelectedDataStore;

import javafx.collections.ListChangeListener;
import qupath.lib.objects.PathObject;

/*
Keeps track of new polyogns added by user, automatically adds the slidename and dataset name to the data,
so that user can upload it to the server.
*/
public class PolygonMetadataAdder {

    public PolygonMetadataAdder(PolygonTracker tracker, SelectedDataStore selectedDataStore) {
        tracker.getNewAnnotations().addListener((ListChangeListener<PathObject>) change -> {
            while (change.next()) {
                if (!change.wasAdded()) continue;
                for (PathObject added : change.getAddedSubList()) {
                    // keep track of it per polygon so that we don't have it out of sync with the selected slide
                    SelectedSlide selectedSlide = selectedDataStore.getSelectedSlide();
                    if (selectedSlide == null) {
                        VectraServerLog.error("Selected slide is null, cannot add metadata to new polygon: " + added);
                        continue;
                    }
                    added.getMetadata().put("dataset", selectedSlide.getDatasetName());
                    added.getMetadata().put("slide", selectedSlide.getSlideName());
                    added.getMetadata().put("created", String.valueOf(System.currentTimeMillis()));
                }
            }
        });
    }
}
    
