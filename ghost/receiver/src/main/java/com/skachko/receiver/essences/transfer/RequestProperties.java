package com.skachko.receiver.essences.transfer;

import net.nvcm.sugar.dao.tricks.api.IReadRepository;
import net.nvcm.sugar.search.core.api.ISearchExpression;
import com.skachko.receiver.api.IGhostRequest;

public class RequestProperties {
    private IReadRepository readRepository;
    private IGhostRequest request;
    private ISearchExpression expression;

    public RequestProperties (){
    }

    public ISearchExpression getExpression() {
        return expression;
    }

    public void setExpression(ISearchExpression expression) {
        this.expression = expression;
    }

    public IReadRepository getReadRepository() {
        return readRepository;
    }

    public void setReadRepository(IReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public IGhostRequest getRequest() {
        return request;
    }

    public void setRequest(IGhostRequest request) {
        this.request = request;
    }


    static class Builder {

        private final RequestProperties properties;

        public Builder(){
            this.properties = new RequestProperties();
        }

        public Builder request(IGhostRequest request) {
            this.properties.request = request;
            return this;
        }

        public Builder repository(IReadRepository dao) {
            this.properties.readRepository = dao;
            return this;
        }

        public Builder searchExpression(ISearchExpression expr) {
            this.properties.expression = expr;
            return this;
        }

        public RequestProperties build(){
            return this.properties;
        }
    }
}
