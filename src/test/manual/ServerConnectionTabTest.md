# Manual testing ServerConnectionTab

--------------------------------------------------------------------------------------------------------------------------------------

## Test 01: Expected normal walkthrough test - First time user

--------------------------------------------------------------------------------------------------------------------------------------

**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must NOT have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _empty_ text fields: Hostname, User, Password, Database User, Database Pass. Additionally, a button with 'Connect' is visible. 

**Script:** enter the required information in the text fields and click connect.
**Expected outcome:** a green dot with the green text 'Connected' appears underneath the 'Connect' button.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 02: Expected normal walkthrough test - Returning user

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _filled_ text fields: Hostname, User, Password, Database User, Database Pass. The fields Password and Database Pass should show dots instead of characters inside the textfield. Additionally, a button with 'Connect' is visible. 

**Script:** click connect.
**Expected outcome:** a green dot with the green text 'Connected' appears underneath the 'Connect' button.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 03: Empty fields

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must NOT have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _empty_ text fields: Hostname, User, Password, Database User, Database Pass. The fields Password and Database Pass should show dots instead of characters inside the textfield. Additionally, a button with 'Connect' is visible. 

**Script:** click connect with at least one empty text field.
**Expected outcome:** nothing should happen.

--------------------------------------------------------------------------------------------------------------------------------------

## Test 04: Connection failed

--------------------------------------------------------------------------------------------------------------------------------------
**Start state:** open QuPath through the terminal with the Vectra Server extension installed. The user must NOT have entered their credentials in a previous run!

**Script:** click on side-panel tab with name ‘Server Connection’.
**Expected outcome:** a panel is visible with 5 _empty_ text fields: Hostname, User, Password, Database User, Database Pass. The fields Password and Database Pass should show dots instead of characters inside the textfield. Additionally, a button with 'Connect' is visible. 

**Script:** click connect with at least one empty text field.
**Expected outcome:** nothing should happen.

**Script:** click connect with all text fields filled in correctly and the field 'Hostname' incorrectly filled text field.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

**Script:** click connect with all text fields filled it and the field 'User' incorrectly filled text field.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

**Script:** click connect with all text fields filled it and the field 'Password' incorrectly filled text field.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

**Script:** click connect with all text fields filled it and the field 'Database User' incorrectly filled text field.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

**Script:** click connect with all text fields filled it and the field 'Database Pass' incorrectly filled text field.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

**Script:** enter the required correct information in the text fields and click connect while having port 8082 occuppied in another terminal.
**Expected outcome:** a red dot with the red text 'Connection failed' appears underneath the 'Connect' button.

