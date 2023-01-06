package net.darkdustry.go.components;

import arc.Events;
import arc.struct.IntMap;
import mindustry.game.EventType;
import mindustry.gen.*;

public class PlayerData {

    private static final IntMap<Data> entities = new IntMap<>();

    public static void init() {
        Events.run(
            EventType.Trigger.update,
            () ->
                Groups.player.forEach(player -> {
                    if (!entities.containsKey(player.unit().id)) entities.put(player.unit().id, new Data(player));
                })
        );
    }

    private record Data(int id, Player player, Unit unit) {
        public Data(Player player) {
            this(player.unit().id, player, player.unit());
        }
    }
}
