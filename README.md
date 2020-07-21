![Logo](https://i.imgur.com/JDpBx0J.png)

# Islands
A simple minecraft mod that adds a new World Type generating an endless ocean filled with islands.

* For Singleplayer:
    1. When starting a new world open the "More World Options..." menu
    2. Set the World Type to Islands
    3. Create the world

* For Multiplayer:
    1. Open the server.properties file found in the same folder as your server
    2. Add `use-modded-worldtype=islands` at the end of the file
    3. Save it
    4. Make sure the server doesn't already have a world generated, **this only works when first generating the server's world!**

## How to Install
1. Download and install MinecraftForge from their [official website](https://files.minecraftforge.net/)
	* This Mod is made using 1.14.4 - 28.2.21, so using that version or something higher than it is recommended
2. Download the Mod from either our [official website](https://pixelatedw.xyz/islands/downloads), or the [curse page](https://www.curseforge.com/minecraft/mc-mods/islands)
3. Create a new "mods" folder, if not already present, inside your ".minecraft" folder
    * On Windows the ".minecraft" folder can be found by pressing Windows Key + R and running the %appdata% command
    * On Linux it can be found inside the "~/.minecraft" directory
    * On macOS it can be found inside "~/Library/Application Support/minecraft" directory
4. Move the Mod file (the .jar file) inside this folder
5. Start Minecraft and choose the newly created "forge" profile
6. Enjoy

## Support
* Donations via [Paypal](shorturl.at/cfhHR) or [Patreon](https://www.patreon.com/wynd) help us keep the servers going
* Help us localize the mod in your language, make sure nobody already thought about doing that first
* Report bugs when you encounter them in a **clear** and **concise** way
* Join the [discord server](http://discord.gg/CYK9xs8), helping newcomers, chatting or just sharing dank memes
* Spread the word about our projects

## Development
1. Clone the repository
    ```http
    https://github.com/PixelatedW/islands
    ```

2. Run one of the following command, in order to setup forge and generate the .launch files
    * For Eclipse:
    ```bash
    ./gradlew eclipse genEclipseRuns
    ```
    * For IntelliJ:
    ```bash
    ./gradlew intellij genIntellijRuns
    ```

3. Open your IDE and Import the project
    * For Eclipse:
        1. File -> Import
        2. General/Existing Projects into Workspace
        3. In the "Select root directory" field browse and select the new project
        4. Make sure its marked under the "Projects" list
        5. Finish
    
    * For IntelliJ:
        1. File -> New 
        2. Project from existing source...
        3. Click the build.gradle
        
        - You can alternatively open it as a project folder with the 
        windows extension and it should start importing it automatically.

**Note: When changing between version branches make sure to run `./gradlew eclipse genEclipseRuns/genIntellijRuns` in order to refresh the project!**

## License
GNU General Public License v3.0
