package com.skachko.sender.essences.ghost.services;

import com.skachko.sender.api.IGhostRequest;
import com.skachko.sender.essences.ghost.dao.api.IGhostDao;
import com.skachko.sender.api.IGhost;
import com.skachko.sender.essences.ghost.services.api.IGhostService;
import net.nvcm.sugar.core.dto.api.IEssence;
import net.nvcm.sugar.search.commons.IEstimatedCount;
import net.nvcm.sugar.search.commons.IPage;
import net.nvcm.sugar.search.core.api.IResourcePath;
import net.nvcm.sugar.search.core.api.ISearchQuery;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public class GhostService implements IGhostService {


    private final IGhostDao dao;

    public GhostService(IGhostDao dao) {
        this.dao = dao;
    }

    @Override
    public IGhost create(IResourcePath<UUID> iResourcePath, IGhost iGhost) {
        UUID uuid = iResourcePath.get(IEssence.ESSENCE_KEY);
        return this.create(uuid,iGhost);
    }

    public IGhost create (UUID id, IGhost ghost) {
        UUID uuid = this.dao.create(ghost);
        return this.get(uuid);
    }

    @Override
    public IGhost update(IResourcePath<UUID> iResourcePath, Date date, IGhost iGhost) {
        UUID uuid = iResourcePath.get(IEssence.ESSENCE_KEY);
        return this.update(uuid,date,iGhost);
    }

    public IGhost update (UUID id, Date date, IGhost iGhost) {
        this.dao.update(id,date,iGhost);
        return this.get(id);
    }

    @Override
    public IGhost delete(IResourcePath<UUID> iResourcePath, Date date) {
        UUID uuid = iResourcePath.get(IEssence.ESSENCE_KEY);
        return this.delete(uuid,date);
    }

    public IGhost delete(UUID uuid, Date date) {
        this.dao.delete(uuid,date);
        return this.get(uuid);
    }

    @Override
    public IGhost get(UUID uuid) {
        return this.dao.read(uuid);
    }

    @Override
    public IGhost getBrief(UUID uuid) {
        return this.dao.readBrief(uuid);
    }

    @Override
    public IGhost get(IResourcePath<UUID> iResourcePath) {
        UUID uuid = iResourcePath.get(IEssence.ESSENCE_KEY);
        return this.get(uuid);
    }

    @Override
    public IGhost getBrief(IResourcePath<UUID> iResourcePath) {
        UUID uuid = iResourcePath.get(IEssence.ESSENCE_KEY);
        return this.getBrief(uuid);
    }

    @Override
    public IPage<IGhost> getPage(ISearchQuery iSearchQuery) {
        return this.dao.page(iSearchQuery);
    }

    @Override
    public List<IGhost> get(ISearchQuery iSearchQuery) {
        return this.dao.list(iSearchQuery);
    }

    @Override
    public long count(ISearchQuery iSearchQuery) {
        return this.dao.count(iSearchQuery);
    }

    @Override
    public IEstimatedCount estimationOfCount(ISearchQuery iSearchQuery, Long aLong) {
        return this.dao.estimationOfCount(iSearchQuery,aLong);
    }

    public List<IGhostRequest> extractUuidsOfDirtyShadows(long ttl, int batchSize){
        return this.dao.getGhostEssence(ttl,batchSize);
    }
}
