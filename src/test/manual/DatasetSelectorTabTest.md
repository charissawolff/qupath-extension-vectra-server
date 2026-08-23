# Manual testing DatasetSelectorTab

--------------------------------------------------------------------------------------------------------------------------------------

## Test 01: Expected normal walkthrough test

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must have setup the server connection!

**Script:** click on side-panel tab with name ‘Image selector’  
**Expected outcome:** a panel is visible with the layout visualised in `DatasetSelectorTab_Fig1.png`.

**Script:** click on the button ‘Load datasets’  
**Expected outcome:** the top field has a list of clickable dataset names of which one can be clicked at a time.

**Script:** click on a dataset name  
**Expected outcome:** the clicked dataset name gets highlighted and the bottom field has a list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name  
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’  
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

**Script:** click on a different dataset name  
**Expected outcome:** the bottom field has a different list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name  
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’  
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 02: No server connection

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must NOT have setup the server connection!

**Script:** click on side-panel tab with name ‘Image selector’  
**Expected outcome:** a panel is visible with the layout visualised in `DatasetSelectorTab_Fig1.png`.

**Script:** click on the button ‘Load datasets’  
**Expected outcome:** nothing happens.

**Script:** click on the button ‘Open image’  
**Expected outcome:** nothing happens.