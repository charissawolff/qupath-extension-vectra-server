import org.junit.jupiter.api.Test;

import javafx.scene.control.Button;
import org.testfx.framework.junit5.ApplicationTest;
import org.computationalimmunology.ext.vectraserver.core.models.Dimensions;
import org.computationalimmunology.ext.vectraserver.ui.tabs.CustomSidePanelTab;
import org.junit.jupiter.api.Assertions;

class CustomSidePanelTabTests extends ApplicationTest {
    @Test
    void makeButton(){
        CustomSidePanelTab tab = new CustomSidePanelTab("Test");
        Button result = tab.makeButton("Test button", new Dimensions(1,1));
        Assertions.assertEquals("Test button", result.getText());
        Assertions.assertEquals(1, result.getPrefHeight());
        Assertions.assertEquals(1, result.getPrefWidth());

        result = tab.makeButton("", new Dimensions(1,1));
        Assertions.assertEquals("", result.getText());
    }

    @Override
    public void start(javafx.stage.Stage stage) throws Exception {
        // This is required but can be empty for this use case
    }
}
