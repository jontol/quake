package com.tidal.quake;

import java.util.Comparator;

/**
 * Created by Jon on 14.09.2015.
 */
public class FeatureComparator implements Comparator<Feature>{
    @Override
    public int compare(Feature lhs, Feature rhs) {
        float change1 = lhs.getProperties().getMag();
        float change2 = rhs.getProperties().getMag();
        if (change1 < change2) return -1;
        if (change1 > change2) return 1;
        return 0;
    }
}
