# QuPath Extension for Vectra Image Server
QuPath extension for opening multichannel TIFF files for Vectra microscope slides which are stored on a server with a specific backend API.
## Overview
The QuPath extension was made for the Computational Immunology Group at Radboud University and can only work to its full capacity given access to the lab's backend API. It was made to circumvent the need of downloading hundreds of tiles per Vectra slide to stitch together into one image to view in QuPath. Instead, the slide images are only ever stored on the server. With server access, users can open slide images as 3-channel JPEGs or full multichannel TIFFs directly in QuPath, view saved polygon and point annotations, and draw and save new polygons back to the server using QuPath's drawing tools.

## Requirements 
- QuPath v0.7.0.
- Access to the server
- Without access to the server, this extension WILL NOT BE USEFUL

## Installation
1. Download the latest `.jar` from the [Releases page](../../releases).
1. Open QuPath v0.7.0, click on Extensions > Manage extensions. 
2. Click on "Open Extension Directory". You'll be prompted to create a user directory if you don't already have one. A folder will then open. 
3. Drag the downloaded jar file into that folder.
4. Restart QuPath.

Alternatively, you can build from source - see [Build the extension](#build-the-extension).
## Examples

<img src="example-images/JPEG-and-TIFF.png" alt="Opening a slide as JPEG or TIFF" width="33%">

User can open a specified slide in both JPEG (left) and TIFF (right) versions, depending on usecase. Opening a TIFF file also means that QuPath's feature to view only specified channels is available, while it is not for the JPEG version. The TIFF version showcases more detail in the slide. The multi tile viewer is used for comparing the two image versions here only. The extension only works when one single image is opened.

<img src="example-images/polygons.png" alt="Viewing and adding polygons" width="33%">

User can view polygons from the server (left top table) or add new polygons (left bottom table) to the server.

## Features 
- Open Vectra slide images stored on the lab's server. It is not necessary to first download all the tiles locally.
- Open images as 3-channel JPEG or as full multichannel TIFF format.
- Load and display saved polygon or point annotations from the server.
- Use QuPath's drawing tools to draw polygon annotations and save them to the server.

## Build the extension

Building the extension with Gradle should be pretty easy - you don't even need to install Gradle separately, because the 
[Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) will take care of that.
Open a command prompt, navigate to where the code lives, and use
```bash
gradlew build shadowJar
```

The built extension should be found inside `build/libs`.

To install the build, do the following:
1. Open QuPath v0.7.0, click on Extensions > Manage extensions. 
2. Click on "Open Extension Directory". You'll be prompted to create a user directory if you don't already have one. A folder will then open. 
3. Drag the built jar from `build/libs/` into this folder. 
4. Restart QuPath. 

## FAQ

**Q: I get an error when opening a slide. What should I check first?**

A: Confirm you have access to the server and that you're running QuPath v0.7.0.

**Q: It takes forever to load the TIFF version of a single slide. Can this be done faster?**

A: Not in this version. Once you open a specific slide, the next time it will open faster due to caching server side.

**Q: I can not open any slides in TIFF format. The QuPath application crashes.**

A: Make sure you provide enough memory for caching the tile images. The TIFF files can be rather large. You can do this by going to Settings > General and bumping the "Percentage memory for tile caching" up. It's also best to close any other apps you might be using on the computer.

**Q: My polygons are not saving or I can not add a "name".**

A: Make sure the "name" column is filled in and immediately pressing Enter on your keyboard while still selecting the row.

**Q: The application lags/stutters when I'm zoomed in and panning over the image.**

A: This happens because each pan triggers a request for full-resolution tile data from the server. QuPath then tries to paint this large image on the viewer.  This causes some lag at times. It's best to pan while zoomed out and only zoom in once you've decided on which area to inspect.