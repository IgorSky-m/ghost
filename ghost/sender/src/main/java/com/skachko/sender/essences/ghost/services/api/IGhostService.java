package com.skachko.sender.essences.ghost.services.api;

import com.skachko.sender.api.IGhost;
import com.skachko.sender.api.IGhostRequest;
import net.nvcm.sugar.service.tricks.api.IService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IGhostService extends IService<UUID, IGhost,IGhost> {
    IGhost create(UUID uuid, IGhost iGhost);
    IGhost update(UUID uuid, Date date, IGhost iGhost);
    IGhost delete(UUID uuid, Date date);
    List<IGhostRequest> extractUuidsOfDirtyShadows(long ttl, int batchSize);

}
