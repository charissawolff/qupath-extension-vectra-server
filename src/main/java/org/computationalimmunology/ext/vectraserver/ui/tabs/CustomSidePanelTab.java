package org.computationalimmunology.ext.vectraserver.ui.tabs;

import org.computationalimmunology.ext.vectraserver.core.models.Dimensions;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class CustomSidePanelTab {
    String name;

    public CustomSidePanelTab(String panelName) {
        this.name = panelName;
    }

    public Tab addCustomTab(TabPane tabpane){
        Tab tab = new Tab();
        tab.setText(this.name); 
        VBox content = getContent();
        tab.setContent(content); 
        tabpane.getTabs().add(tab);
        return tab;
    }

    /**
     * Get tab content.
     * @return empty box
     */
    public VBox getContent(){
        return new VBox();
    }

    public Button makeButton(String name, Dimensions dim){
        Button btn = new Button();
        btn.setText(name);
        btn.setPrefHeight(dim.getHeight());
        btn.setPrefWidth(dim.getWidth());
        return btn;
    }
    
}
