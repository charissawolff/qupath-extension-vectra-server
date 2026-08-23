package org.computationalimmunology.ext.vectraserver.core.imageServers;

import qupath.lib.images.servers.ImageServer;
import qupath.lib.images.servers.ImageServerBuilder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;

import org.computationalimmunology.ext.vectraserver.core.api.ServerGateway;
import org.computationalimmunology.ext.vectraserver.core.models.TileMetadata;

public class TileImageServerBuilder implements ImageServerBuilder<BufferedImage> {
    TileMetadata tileMetadata;
    String datasetName;
    String slideName;
    double downsampleValue;
    ServerGateway serverGateway;

    @Override
    public UriImageSupport<BufferedImage> checkImageSupport(URI uri, String... args) throws IOException {
        return null;
    }

    @Override
    public ImageServer<BufferedImage> buildServer(URI uri, String... args) throws Exception {
        throw new UnsupportedOperationException(
            "TileImageServer cannot be reconstructed from a URI alone, it needs a live ImageRequestHandler...");
    }

    public TileMetadata.ImageType getType() {
    return tileMetadata.getType();
}

    @Override
    public String getName() {
        return "Streamed Image Server Builder";
    }

    @Override
    public String getDescription() {
        return "Builder for streamed png.";
    }

    @Override
    public Class<BufferedImage> getImageType() {
        return BufferedImage.class;
    }
}
