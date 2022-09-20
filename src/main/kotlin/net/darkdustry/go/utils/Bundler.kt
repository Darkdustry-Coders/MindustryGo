package net.darkdustry.go.utils

import arc.files.Fi
import arc.struct.*
import arc.util.Log
import mindustry.Vars
import mindustry.gen.Player
import net.darkdustry.go.utils.Utils.locale
import java.text.MessageFormat
import java.util.*

object Bundler {
    private const val defaultLanguage = "en"

    val defaultLocale = Locale(defaultLanguage)
    val supportedLocales = Seq<Locale>()

    private val bundles = ObjectMap<Locale, ResourceBundle>()
    private val formats = ObjectMap<Locale, MessageFormat>()

    fun init() {
        val files = getBundles().seq().filter { fi ->
            fi.extEquals("properties")
        }

        files.each { file: Fi ->
            val code =
                file.nameWithoutExtension().split("_".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()

            if (code[1] == defaultLanguage) {
                supportedLocales.add(defaultLocale)
            } else {
                if (code.size == 3) {
                    supportedLocales.add(Locale(code[1], code[2]))
                } else {
                    supportedLocales.add(Locale(code[1]))
                }
            }
        }

        supportedLocales.each { locale: Locale ->
            bundles.put(locale, ResourceBundle.getBundle("bundles.bundle", locale))
            formats.put(locale, MessageFormat("", locale))
        }

        Log.debug("Supported locales: @. Default locale: @.", supportedLocales.size, defaultLocale)
    }

    @JvmOverloads
    operator fun get(key: String, defaultValue: String, locale: Locale = defaultLocale): String {
        return try {
            val bundle = bundles[locale, bundles[defaultLocale]]
            bundle.getString(key)
        } catch (ignored: MissingResourceException) {
            defaultValue
        }
    }

    @JvmOverloads
    operator fun get(key: String, locale: Locale = defaultLocale): String {
        return Bundler[key, key, locale]
    }

    private fun format(key: String, locale: Locale, vararg values: Any?): String {
        val pattern = Bundler[key, locale]

        if (values.isEmpty()) {
            return pattern
        }

        val format = formats[locale, formats[defaultLocale]]
        format.applyPattern(pattern)
        return format.format(values)
    }

    fun format(key: String, vararg values: Any?): String {
        return format(key, defaultLocale, *values)
    }

    fun bundled(player: Player, key: String, vararg values: Any?) {
        player.sendMessage(format(key, locale(player.locale), values))
    }

    fun format(player: Player, key: String, vararg values: Any?): String {
        return format(key, locale(player.locale), values)
    }

    private fun getBundles(): Fi {
        return Vars.mods.getMod("mindustry-go").root.child("bundles")
    }
}