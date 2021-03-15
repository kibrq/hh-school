package ru.hh.school.util;

import org.springframework.beans.factory.annotation.Qualifier;

public class PopularityDecider {
    private enum PopularityTags {
        REGULAR, POPULAR;
    }

    private final Integer threshold;

    public PopularityDecider(@Qualifier("popularity_threshold") Integer threshold) {
        this.threshold = threshold;
    }

    public String decide(int views) {
        return views < threshold ? PopularityTags.REGULAR.name() : PopularityTags.POPULAR.name();
    }
}
