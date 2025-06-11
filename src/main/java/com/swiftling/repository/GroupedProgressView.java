package com.swiftling.repository;

import java.util.UUID;

public interface GroupedProgressView {
    UUID getOwnerUserAccountId();
    Integer getLearned();
    Integer getAdded();
}
