package com.skachko.sender.essences.ghost.dao.api;

import com.skachko.sender.api.IGhost;
import com.skachko.sender.api.IGhostRequest;
import net.nvcm.sugar.dao.tricks.api.ICUDRepository;
import net.nvcm.sugar.dao.tricks.api.IReadRepository;

import java.util.List;
import java.util.UUID;

public interface IGhostDao extends ICUDRepository<IGhost, UUID>, IReadRepository<IGhost,IGhost,UUID> {
    List<IGhostRequest> getGhostEssence(long ttl, int batchSize);

}
