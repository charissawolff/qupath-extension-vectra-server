# Manual testing Full User Pipeline
This test covers a full run-through from the user starting QuPath to seeing the selected side inside the QuPath image viewer.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 01: Expected normal walkthrough test - First time user

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must NOT have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _empty_ text fields: Hostname, User, Password, Database User, Database Pass. Additionally, a button with 'Connect' is visible. 

**Script:** enter the required information in the text fields and click connect.
**Expected outcome:** a green dot with the green text 'Connected' appears underneath the 'Connect' button

**Script:** click on side-panel tab with name ‘Image selector’.
**Expected outcome:** a panel is visible with the layout visualised in `DatasetSelectorTab_Fig1.png`.

**Script:** click on the button ‘Load datasets’.
**Expected outcome:** the top field has a list of clickable dataset names of which one can be clicked at a time.

**Script:** click on a dataset name.
**Expected outcome:** the clicked dataset name gets highlighted and the bottom field has a list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name.
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’.
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

**Script:** click on a different dataset name.
**Expected outcome:** the bottom field has a different list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name.
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’.
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 02: Expected normal walkthrough test - Returning user

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _filled_ text fields: Hostname, User, Password, Database User, Database Pass. The fields Password and Database Pass should show dots instead of characters inside the textfield. Additionally, a button with 'Connect' is visible. 

**Script:** click connect.
**Expected outcome:** a green dot with the green text 'Connected' appears underneath the 'Connect' button

**Script:** click on side-panel tab with name ‘Image selector’.
**Expected outcome:** a panel is visible with the layout visualised in `DatasetSelectorTab_Fig1.png`.

**Script:** click on the button ‘Load datasets’.
**Expected outcome:** the top field has a list of clickable dataset names of which one can be clicked at a time.

**Script:** click on a dataset name.
**Expected outcome:** the clicked dataset name gets highlighted and the bottom field has a list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name.
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’.
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

**Script:** click on a different dataset name.
**Expected outcome:** the bottom field has a different list of clickable slide names of which one can be clicked at a time.

**Script:** click on a slide name.
**Expected outcome:** the clicked slide name gets highlighted.

**Script:** click on the button ‘Open image’.
**Expected outcome:** the thumb images of the selected slide from the selected dataset are displayed in QuPath's image viewer forming one slide image with color.

