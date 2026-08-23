package org.computationalimmunology.ext.vectraserver.ui.tabBoxes;

import org.computationalimmunology.ext.vectraserver.VectraServerExtension;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class EnableExtensionCheckbox extends VBox {
    private static final BooleanProperty enableExtensionProperty = VectraServerExtension.enableExtensionProperty();

    private EnableExtensionCheckbox() {
        setPadding(new Insets(15));
        setSpacing(10);

        CheckBox checkBox = new CheckBox("Enable Vectra Server Extension");
        checkBox.selectedProperty().bindBidirectional(enableExtensionProperty);
        getChildren().add(checkBox);
    }

    public static Parent getInstance() {
        return new EnableExtensionCheckbox();
    }
}
