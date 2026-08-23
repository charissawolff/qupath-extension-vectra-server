package org.computationalimmunology.ext.vectraserver.ui.commands.polygon;

import java.util.List;
import java.util.function.Consumer;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;
import org.computationalimmunology.ext.vectraserver.core.api.ServerUploadGateway;
import org.computationalimmunology.ext.vectraserver.ui.commands.AbstractAsyncCommand;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddPolygonCommand extends AbstractAsyncCommand<JSONObject> {
    private final JSONObject polygonJson;
    private final ServerUploadGateway dataUploadHandler;

    public AddPolygonCommand(JSONObject polygonJson, ServerUploadGateway dataUploadHandler) {
        this.polygonJson = polygonJson;
        this.dataUploadHandler = dataUploadHandler;
    }

    @Override
    protected void onSuccess(JSONObject result) {
        VectraServerLog.log("Successfully uploaded polygon! result: " + result.toString());
    }

    @Override
    protected JSONObject execute(Consumer<String> progressReporter) throws Exception {
    //now rest of information to get format we needed: 
        try {
            progressReporter.accept("Uploading polygon data...");
            JSONObject response = dataUploadHandler.uploadPolygonAnnotations(new JSONArray(List.of(polygonJson)));
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Could not upload polygon data", e);
        }
    }
}
