# ![Logo](./images/logo.png) Code Guide for Photoshop-Ultra-Light

## Main
This is where is starts, [click here to view](./src/main/java/Main/Main.java)

## User Interface

You can find the main UI code [here](./src/main/resources/MainUI.fxml) and the functionality e.g. show the application, etc. can be viewed [here](./src/main/java/Main/Controller.java)

## File I/O

One of the implementation of this is when opening and saving the images which is implemented [here](./src/main/java/Main/Controller.java)

Where to look at: `open()`, `openRecent()`, `save()`, and `saveAs()`

## Socket I/O

There is a [server](./src/main/java/VIPLogin/Server.java) implemented to handle member login where it checks if the user is a VIP member or not
if the user is VIP member they will see more features. This is where [client](./src/main/java/VIPLogin/HandleAClient.java) 
initial request being handled.

## Multi-threading

We're implementing multi-thread server for the purpose of this project which the code can be viewed [here](./src/main/java/VIPLogin/Server.java)

## General and More Information About the Code

- **CamCapture**: where all the code for webcam implementation located
- **Effects**: implementation for black and white and Gaussian blur implementation
- **Global**: all the global functionality such as alert, drag and drop functionality, mouse state, openCV reuse modified functions, and switch button
- **ImageScrapper**: functionality to get picture from Google search is implemented here
- **Layers**: layers makes photoshop really convenient and better than regular photo editor, this is where to find all of the implementation
- **Main**: main function, the menu bar controls, and if everytime we make a new workspace implementation
- **Tools**: All the toolbars buttons and sliders implementation
- **UI**: the main interface is divided to three sections which the most left hand side is ToolbarView and the most right hand side is PropertiesView
- **VIPLogin**: where all the membership registration, login, and status is handled