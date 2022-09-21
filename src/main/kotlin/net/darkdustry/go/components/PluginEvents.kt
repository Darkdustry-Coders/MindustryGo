package net.darkdustry.go.components

import arc.Events
import arc.struct.Seq
import mindustry.game.EventType
import mindustry.gen.*
import mindustry.gen.Unit
import net.darkdustry.go.utils.Bundler.format
import net.darkdustry.go.utils.PlayersData

object PluginEvents {
    fun init() {
        Events.on(EventType.UnitDestroyEvent::class.java) { event ->
            val data = PlayersData.UnitData.get(event.unit.id)

            if (data?.unit?.player != null) {
                val player = data.player!!

                val bullets = Seq<Bullet>()
                Groups.bullet.copy(bullets)

                bullets.removeAll { bullet -> bullet.team == event.unit.team }
                bullets.sort { bullet -> bullet.dst2(event.unit) }

                if (!bullets.isEmpty && bullets.first().owner != null) {
                    val bullet = bullets.first().owner as Unit
                    val killer = PlayersData.UnitData.get(bullet.id)?.player?.name ?: "[gray]unknown[]"

                    Call.infoToast(
                        format(player, "game.player.killed", player.name, killer),
                        3f
                    )
                } else {
                    Call.infoToast(
                        format(player, "game.player.dead", player.name),
                        3f
                    )
                }
            }

            PlayersData.UnitData.delete(event.unit.id)
        }
    }
}