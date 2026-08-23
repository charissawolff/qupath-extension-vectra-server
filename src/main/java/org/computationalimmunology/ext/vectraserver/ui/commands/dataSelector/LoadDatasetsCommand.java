package org.computationalimmunology.ext.vectraserver.ui.commands.dataSelector;

import java.util.List;
import java.util.function.Consumer;

import org.computationalimmunology.ext.vectraserver.core.api.ServerGateway;
import org.computationalimmunology.ext.vectraserver.ui.commands.AbstractAsyncCommand;

public class LoadDatasetsCommand extends AbstractAsyncCommand<List<String>> {
    private final ServerGateway serverGateway;

    public LoadDatasetsCommand(ServerGateway serverGateway) {
        this.serverGateway = serverGateway;
    }

    @Override
    protected List<String> execute(Consumer<String> progressReporter) throws Exception {
        progressReporter.accept("Loading dataset data");
        return serverGateway.getAllDatasets();
    }
}
