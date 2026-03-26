package models;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<Integer, Rack> racks;

    public Inventory(Integer rackCount, Integer maxUnitsPerRack) {
        if (rackCount <= 0) {
            throw new IllegalArgumentException("rackCount must be positive");
        }
        racks = new HashMap<>();
        for (int i = 1; i <= rackCount; i++) {
            racks.put(i, new Rack(i, maxUnitsPerRack));
        }
    }

    //getters
    public Integer getRackCount() {
        return racks.size();
    }

    public Rack getRack(int rackNumber) {
        return racks.get(rackNumber);
    }
}
