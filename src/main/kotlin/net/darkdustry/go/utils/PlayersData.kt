package net.darkdustry.go.utils

import arc.*
import arc.struct.ObjectMap
import mindustry.game.EventType.*
import mindustry.gen.*
import mindustry.gen.Unit
import net.darkdustry.go.utils.Database.toByteArray
import net.darkdustry.go.utils.Utils.findPlayer
import org.iq80.leveldb.impl.Iq80DBFactory.bytes
import java.io.Serializable

object PlayersData {
    private val unitData = ObjectMap<Int, UnitData>()
    private val playerData = ObjectMap<String, UnitData>()

    fun init() {
        Events.on(PlayerJoin::class.java) { event ->
            val player = event.player
            val data = UnitData(player.unit())

            playerData.put(player.uuid(), data)
            unitData.put(player.unit().id, data)

            Database.write(bytes(player.uuid()), PlayerModel(player.uuid(), player.name).toByteArray())
        }

        Events.on(PlayerLeave::class.java) { event ->
            playerData.remove(event.player.uuid())
        }

        Events.run(Trigger.update) {
            Core.app.post { updateList() }
        }
    }

    class UnitData(unit: Unit) {
        var player: Player?
        var unit: Unit
        var id: Int

        init {
            this.player = unit.player
            this.unit = unit
            this.id = unit.id
        }

        companion object {
            fun get(id: Int): UnitData? {
                return unitData.get(id)
            }

            fun delete(id: Int) {
                for (data in playerData) {
                    if (data.value.id == id) {
                        playerData.remove(data.key)
                    }
                }

                unitData.remove(id)
            }
        }
    }

    class PlayerModel(uuid: String, nickname: String) : Serializable {
        val uuid: String
        val nickname: String
        val wins: Int
        val rank: Ranks

        init {
            this.uuid = uuid
            this.nickname = nickname
            this.wins = 0
            this.rank = Ranks.Rookie
        }
    }

    fun updateList() {
        for (data in playerData) {
            val player = findPlayer(data.key)

            if (player != null) {
                if (data.value.id != player.unit().id) {
                    unitData.put(data.value.id, data.value)

                    data.value.id = player.unit().id
                    data.value.player = player
                    data.value.unit = player.unit()
                }
            } else {
                unitData.remove(data.value.id)
                playerData.remove(data.key)
            }
        }
    }

    enum class Ranks {
        Rookie, Private, Carporal
    }
}