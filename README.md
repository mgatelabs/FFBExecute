# FFBExecute
Set of user editable scripts to control FFBE for TMR/LB/Esper farming.

Some scripts are a bit outdated, and need to be re-evaluated again.

## Features
* Scriptable, simple JSON language
* Device agnostic, the script is seperate from the view
* Loggin, it prints what it's doing
* Editing, built in method to make or edit views
* Extensible, views are built upon other views

## Getting Started

0. Have the right phone
* Axon7
* Samsung S7 (1080P)
* More devices are possible, but until I have a definition file
1. Install Java 8
* http://www.oracle.com/technetwork/java/javase/downloads/index.html
* JRE is to only run the app
* SDK lets you develop your own code, or build from source
2. Install a way to communicate with your Android device
* Android Tools (Basic) Use this just to connect
* https://developer.android.com/studio/releases/platform-tools.html
* Android Studio (Advanced) This lets you also deploy from src, the helper app
* https://developer.android.com/studio/index.html
3. Get the helper app, and install it onto your device
* Released version
* https://github.com/mgatelabs/ImageReaderApp/releases
* Build from source
* https://github.com/mgatelabs/ImageReaderApp
4. Get this app
* Release version
* https://github.com/mgatelabs/FFBExecute/releases
* Build from source (Use Intellij Community edition)
5. Make sure ADB works
* Plug in your device
* Enable developer mode
* https://developer.android.com/studio/debug/dev-options.html
* Open a cmd window
* type "adb device-state", press enter and see what is returned.  If it's working you should see "device"
6. Run the app
* From intellij you need to setup a Java 8 configuration to execute "com.mgatelabs.ffbe.Runner"
* From windows, double click FFBExecute.jar to start the app.

## Common Configurations

## TMR farming
Make sure your already at the earth shrine, entrance, at the depart screen.  Make sure your TMR team is selected.

1. Setup
* Mode: script
* Action: run
* Device: Samsung-S7-1080
* Views: 1080x1920
* Scripts: earthshrine-entrance-attack
* Maps: Leave blank
2. Hit start
3. Check the screen, make sure your player level is right, and the phone's IP address is entered.
4. Make sure the phone helper is running
5. Hit the play button and look at the log, if the log says, "BAD SHELL", try pressing play/pause again

 and press the play button

## LB farming (Non-destructive)
Make sure your already at the earth shrine, entrance, at the depart screen, with no friend.  Make sure your single LB farming unit is in slot 1.  The unit should have gear that constantly fills the LB guage and heals.

1. Setup
* Mode: script
* Action: run
* Device: Samsung-S7-1080
* Views: 1080x1920
* Scripts: battle-farm-lb-1unit
* Maps: Leave blank
2. Hit start
3. Check the screen, make sure your player level is right, and the phone's IP address is entered.
4. Make sure the phone helper is running
5. Hit the play button and look at the log, if the log says, "BAD SHELL", try pressing play/pause again

## ESPER farming (Non-destructive)
Make sure your already at the earth shrine, entrance, at the depart screen, with no friend.  Make sure your single unit is Rydia in slot 1.  The unit should have gear that constantly fills the LB guage and heals.  When you get the esper achievement, Rydia's LB level should be maxed.

1. Setup
* Mode: script
* Action: run
* Device: Samsung-S7-1080
* Views: 1080x1920
* Scripts: battle-farm-esper-1unit
* Maps: Leave blank
2. Hit start
3. Check the screen, make sure your player level is right, and the phone's IP address is entered.
4. Make sure the phone helper is running
5. Hit the play button and look at the log, if the log says, "BAD SHELL", try pressing play/pause again

## What is it doing

0. Loading Script, Device, View, Connection & Player Information
1. Executing the main loop
2. Grabbing the current screen
3. Handle conditions and execute actions

## What needs to happen

1. Map parsing
2. Finish built-in script editor
