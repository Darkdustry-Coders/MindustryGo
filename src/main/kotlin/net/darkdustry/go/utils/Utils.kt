package net.darkdustry.go.utils

import mindustry.gen.*
import net.darkdustry.go.utils.Bundler.defaultLocale
import net.darkdustry.go.utils.Bundler.supportedLocales
import java.util.*


object Utils {
    fun locale(name: String): Locale {
        return supportedLocales.find { locale -> name.startsWith(locale.toString()) } ?: defaultLocale
    }

    fun findPlayer(uuid: String?): Player? {
        return Groups.player.find { player -> player.uuid().equals(uuid) }
    }

    fun showMenu(
        player: Player,
        menu: Int,
        title: String,
        content: String,
        buttons: Array<Array<String>>,
        titleObject: Any?,
        vararg contentObjects: Any?
    ) {
        val locale = locale(player.locale)
        for (i in buttons.indices) for (j in buttons[i].indices) buttons[i][j] = Bundler[buttons[i][j], locale]
        Call.menu(
            player.con,
            menu,
            Bundler.format(title, locale, titleObject),
            Bundler.format(content, locale, contentObjects),
            buttons
        )
    }
}