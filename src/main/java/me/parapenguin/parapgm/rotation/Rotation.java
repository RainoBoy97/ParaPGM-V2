package me.parapenguin.parapgm.rotation;

import lombok.Getter;
import lombok.Setter;
import me.parapenguin.parapgm.ParaPGM;
import me.parapenguin.parapgm.map.MapLoader;


import java.util.ArrayList;
import java.util.List;

public class Rotation {

    @Getter ParaPGM instance;
    @Getter List<RotationSlot> order;

    @Getter RotationSlot current;
    @Getter @Setter boolean modified;

    public Rotation(ParaPGM instance) {
        this.instance = instance;
        this.modified = false;
    }

    public Rotation(ParaPGM instance, List<RotationSlot> order) {
        this(instance);
        this.order = order;
    }

    public Rotation(ParaPGM instance, MapLoader loader) {
        this(instance);

        List<RotationSlot> order = new ArrayList<RotationSlot>();
        order.add(new RotationSlot(loader));

        this.order = order;
    }

    public boolean isLoaded() {
        return order != null;
    }

    public int getLocation() {
        return getLocation(current);
    }

    public int getLocation(RotationSlot slot) {
        for (int i = 0; i < order.size(); i++)
            if (slot == order.get(i))
                return i;

        return -1;
    }

    public void setNext(MapLoader loader) {
        order.add(getLocation() + 1, new RotationSlot(loader));
        setModified(true);
    }

    public boolean hasNext() {
        try {
            getNext();
        } catch (IndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    public boolean hasPrevious() {
        try {
            getPrevious();
        } catch (IndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    public RotationSlot getNext() {
        return order.get(getLocation() + 1);
    }

    public RotationSlot getPrevious() {
        return order.get(getLocation() - 1);
    }

}
