package me.parapenguin.parapgm.rotation;

import lombok.Getter;
import me.parapenguin.parapgm.map.Map;
import me.parapenguin.parapgm.map.MapLoader;

public class RotationSlot {

    @Getter Map map;
    @Getter MapLoader loader;

    public RotationSlot(MapLoader loader) {
        this.loader = loader;
    }

    public boolean isLoaded() {
        return map != null;
    }

}
