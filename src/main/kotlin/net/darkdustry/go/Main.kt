package net.darkdustry.go

import mindustry.Vars.state
import mindustry.core.Version
import mindustry.mod.Plugin
import net.darkdustry.go.components.PluginEvents
import net.darkdustry.go.utils.*

@Suppress("unused")
class Main : Plugin() {
    override fun init() {

        PlayersData.init()
        PluginEvents.init()
        Database.init()
        Bundler.init()

        val rules = state.rules
        rules.modeName = "MindustryGo"

        Version.build = -1
    }
}