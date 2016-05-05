package net.benmclean.badroguelike.model;

import squidpony.squidgrid.SpatialMap;
import squidpony.squidmath.Coord;
import squidpony.squidmath.SquidID;

/**
 * Created by Benjamin on 4/29/2016.
 */
public class LazySpatialMap<E> extends SpatialMap<SquidID, E> {
    public SquidID add(Coord coord, E element) {
        SquidID uuid = SquidID.randomUUID();
        add(coord, uuid, element);
        return uuid;
    }
    public SquidID put(Coord coord, E element) {
        SquidID uuid = SquidID.randomUUID();
        put(coord, uuid, element);
        return uuid;
    }
}
