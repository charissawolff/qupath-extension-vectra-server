package org.computationalimmunology.ext.vectraserver.ui.tabBoxes;

import org.computationalimmunology.ext.vectraserver.core.VectraServerLog;
import org.computationalimmunology.ext.vectraserver.core.api.ServerUploadGateway;
import org.computationalimmunology.ext.vectraserver.core.converters.PolygonConverter;
import org.computationalimmunology.ext.vectraserver.core.models.AnnotationPolygon;
import org.computationalimmunology.ext.vectraserver.core.store.SelectedDataStore;
import org.computationalimmunology.ext.vectraserver.ui.commands.SelectPathObjectCommand;
import org.computationalimmunology.ext.vectraserver.ui.commands.polygon.AddPolygonCommand;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import qupath.lib.images.ImageData;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.tools.PathObjectImageViewers;
import qupath.lib.gui.viewer.QuPathViewer;
import qupath.lib.objects.PathObject;

/*
Viewer box for viewing USER added new polygons, not the ones that are fetched from the server
Here user can see the polygons they added, and can choose to upload them to the server or remove them.
*/
public class NewPolygonViewerBox extends VBox {
    private final TableView<PathObject> tableView;
    private final ServerUploadGateway dataUploadHandler;
    private final SelectedDataStore selectedDataStore;

    public NewPolygonViewerBox(ObservableList<PathObject> items, ServerUploadGateway dataUploadHandler, SelectedDataStore selectedDataStore) {
        Label title = new Label("User added polygons");
        title.setFont(Font.font("System", FontWeight.BOLD, 16));
        VBox.setMargin(title, new Insets(0, 2, 5, 2));
        tableView = new TableView<>(items);
        this.dataUploadHandler = dataUploadHandler;
        this.selectedDataStore = selectedDataStore;
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tableView.getColumns().add(buildThumbnailColumn());
        tableView.getColumns().add(buildNameColumn());
        tableView.getColumns().add(buildDatasetColumn());
        tableView.getColumns().add(buildSlideColumn());
        tableView.getColumns().add(buildAddColumn());
        VBox.setVgrow(tableView, Priority.ALWAYS);
        tableView.setPrefHeight(250);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            //what the user selects in the list view
            SelectPathObjectCommand selectAnnotationCommand = new SelectPathObjectCommand(newSel);
            selectAnnotationCommand.execute();
        });    

        getChildren().addAll(title, tableView);
    }

    public TableView.TableViewSelectionModel<PathObject> getSelectionModel() {
        return tableView.getSelectionModel();
    }

    private TableColumn<PathObject, String> buildDatasetColumn() {
        TableColumn<PathObject, String> col = new TableColumn<>("Dataset");
        col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMetadata().get("dataset")));
        return col;
    }
    private TableColumn<PathObject, String> buildSlideColumn() {
        TableColumn<PathObject, String> col = new TableColumn<>("Slide");
        col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getMetadata().get("slide")));
        return col;
    }

    private TableColumn<PathObject, String> buildNameColumn() {
        TableColumn<PathObject, String> col = new TableColumn<>("Name");
        col.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));
        return col;
    }

    private TableColumn<PathObject, Void> buildAddColumn() {
        TableColumn<PathObject, Void> col = new TableColumn<>("Add");
        col.setCellFactory(column -> new TableCell<>() {
            private final Button button = new Button("Add");
            {
                button.setOnAction(e -> handleAddClicked(getTableView().getItems().get(getIndex()), button));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                PathObject row = getTableView().getItems().get(getIndex());
                boolean uploaded = row.getMetadata().get("uploaded") != null;
                button.setText(uploaded ? "Added" : "Add");
                button.setDisable(uploaded);
                setGraphic(button);
            }
        });
        return col;
    }

    private TableColumn<PathObject, PathObject> buildThumbnailColumn() {
        TableColumn<PathObject, PathObject> col = new TableColumn<>("Thumbnail");
        col.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        QuPathViewer viewer = QuPathGUI.getInstance().getViewer();
        col.setCellFactory(c -> {
            ImageData<BufferedImage> imageData = viewer.getImageData();
            if (imageData == null) {
                return new TableCell<>();
            }
            return PathObjectImageViewers.createTableCell(viewer, imageData.getServer(), true, 5);
        });
        return col;
    }

    private void handleAddClicked(PathObject polygon, Button button) {
        button.setDisable(true);
        VectraServerLog.log("Adding polygon: " + polygon.getName() + " which is:" + polygon);
        try {
            AnnotationPolygon polygonData = PolygonConverter.fromPathObject(polygon, selectedDataStore.getDx(), selectedDataStore.getDy());
            VectraServerLog.log("Polygon from PathObject is " + polygonData);
            JSONObject polygonJson = PolygonConverter.toJSONObject(polygonData);
            VectraServerLog.log("Polygon JSON is " + polygonJson);
            AddPolygonCommand command = new AddPolygonCommand(polygonJson, dataUploadHandler);
            command.build();
            //visible on screen and not editable anymore
            command.setOnDone(() -> { 
                polygon.setLocked(true); tableView.refresh(); 
                polygon.getMetadata().put("uploaded", "true");
                tableView.refresh();
            
            });
            command.setOnFailed(() -> { 
                button.setDisable(false); 
                tableView.refresh(); 
            });
            command.start();
        } catch (IllegalArgumentException iae) {
            VectraServerLog.log("Could not save polygon because missing/ incorrect information provided: " + iae);
        } catch (Exception e) {
            VectraServerLog.log("Could not save polygon:" + e);
        }
    }
}
