package org.computationalimmunology.ext.vectraserver.ui.commands.polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;
import org.computationalimmunology.ext.vectraserver.core.api.ServerGateway;
import org.computationalimmunology.ext.vectraserver.core.converters.PolygonConverter;
import org.computationalimmunology.ext.vectraserver.core.models.AnnotationPolygon;
import org.computationalimmunology.ext.vectraserver.core.store.SelectedDataStore;
import org.computationalimmunology.ext.vectraserver.ui.commands.AbstractAsyncCommand;
import org.computationalimmunology.ext.vectraserver.ui.commands.AttachPathObjectsToViewerCommand;

import qupath.lib.objects.PathObject;

public class LoadPolygonCommand extends AbstractAsyncCommand<List<AnnotationPolygon>> {
    private final SelectedDataStore selectedDataStore;
    private final ServerGateway serverGateway;

    public LoadPolygonCommand(ServerGateway serverGateway, SelectedDataStore selectedDataStore) {
        this.selectedDataStore = selectedDataStore;
        this.serverGateway = serverGateway;
    }

    @Override
    //on success, add the polygons to the selectedDataStore and also add them to the QuPath hierarchy, so they are visible in the viewer
    protected void onSuccess(List<AnnotationPolygon> polygons) {
        selectedDataStore.setPolygons(polygons);
        double dx = selectedDataStore.getDx();
        double dy = selectedDataStore.getDy();
        List<PathObject> polygonPathObjects = new ArrayList<>();
        for (AnnotationPolygon p: polygons) {
            polygonPathObjects.add(PolygonConverter.toPathObject(p, dx, dy));
            VectraServerLog.log("Fetched polygon with ID: " + p.getId() + " for dataset: " + selectedDataStore.getSelectedSlide().getDatasetName() + ", slide: " + selectedDataStore.getSelectedSlide().getSlideName());
        }
        AttachPathObjectsToViewerCommand attachCommand = new AttachPathObjectsToViewerCommand(polygonPathObjects);
        attachCommand.execute();

    }

    @Override
    protected List<AnnotationPolygon> execute(Consumer<String> progressReporter) throws Exception {
        List<AnnotationPolygon> polygons = new ArrayList<>();
        String datasetName = selectedDataStore.getSelectedSlide().getDatasetName();
        String slideName = selectedDataStore.getSelectedSlide().getSlideName();
        if (datasetName.length() < 1 || slideName.length() < 1) {
            VectraServerLog.error("fetchSlidePolygons called without tile metadata set. You need to call setTilesMetadata first for dataset: "
                    + datasetName + ", slide: " + slideName);
            return polygons; // No dataset or slide selected, exit the method
        }
        try{
            List<AnnotationPolygon> ps = serverGateway.fetchPolygons(datasetName, slideName);
            for (AnnotationPolygon p : ps) {
                VectraServerLog.log("Fetched polygon with ID: " + p.getId() + " for dataset: " + datasetName + ", slide: " + slideName);
                polygons.add(p);
            }
            return polygons;
        }catch (Exception e) {
            throw new RuntimeException("Failed to load polygon data for dataset: " + datasetName + ", slide: " + slideName, e);
        }

    }
    
}
