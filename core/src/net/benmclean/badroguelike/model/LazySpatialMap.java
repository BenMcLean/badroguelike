package net.benmclean.badroguelike.model;

import squidpony.squidgrid.SpatialMap;
import squidpony.squidmath.Coord;
import java.util.UUID;

/**
 * Created by Benjamin on 4/29/2016.
 */
public class LazySpatialMap<E> extends SpatialMap<UUID, E> {
    public UUID add(Coord coord, E element) {
        UUID uuid = UUID.randomUUID();
        add(coord, uuid, element);
        return uuid;
    }
    public UUID put(Coord coord, E element) {
        UUID uuid = UUID.randomUUID();
        put(coord, uuid, element);
        return uuid;
    }
}
