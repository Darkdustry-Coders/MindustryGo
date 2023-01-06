package net.darkdustry.go;

import static arc.util.Log.infoTag;
import static mindustry.Vars.netServer;
import static mindustry.net.Packets.KickReason.serverRestarting;

import arc.ApplicationListener;
import arc.Core;
import arc.util.Log;
import mindustry.mod.Plugin;
import net.darkdustry.go.components.PlayerData;
import net.darkdustry.go.components.PluginEvents;

public class Main extends Plugin {

    public Main() {
        infoTag("MindustryGo", "Starting...");

        Core.app.addListener(
            new ApplicationListener() {
                @Override
                public void dispose() {
                    netServer.kickAll(serverRestarting);
                    Log.infoTag("Shutdown", "The server will now be shutdown!");
                }
            }
        );
    }

    @Override
    public void init() {
        infoTag("MindustryGo", "Loading...");

        PlayerData.init();
        PluginEvents.init();

        infoTag("MindustryGo", "Plugin loaded.");
    }
}
