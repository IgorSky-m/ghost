package com.skachko.receiver.handlers;

import com.skachko.receiver.handlers.api.IResponseHandler;
import net.nvcm.sugar.dao.tricks.api.IReadRepository;
import net.nvcm.sugar.search.core.SearchFactory;
import net.nvcm.sugar.search.core.api.ComparisonOperator;
import net.nvcm.sugar.search.core.api.ISearchExpression;
import net.nvcm.sugar.search.core.api.ISearchFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import com.skachko.receiver.api.IGhostRequest;
import com.skachko.receiver.api.IGhostResponse;
import com.skachko.receiver.config.GhostProperties;
import com.skachko.receiver.converters.api.IConverter;
import com.skachko.receiver.dao.api.IReceiverDao;
import com.skachko.receiver.enums.GhostStatus;
import com.skachko.receiver.enums.MessageType;
import com.skachko.receiver.essences.response.GhostResponse;
import com.skachko.receiver.essences.transfer.RequestProperties;
import com.skachko.receiver.exceptions.HandlerException;
import com.skachko.receiver.handlers.api.IMsgHandler;
import com.skachko.receiver.senders.api.IMsgSender;

import java.util.*;


public class ResponseHandler implements IResponseHandler {

    private final ApplicationContext context;
    private final GhostProperties properties;
    private final IConverter converter;
    private final IReceiverDao dao;
    private final ISearchFactory sf;
    private final IMsgSender sender;


    public ResponseHandler(ApplicationContext ctx, GhostProperties properties, IConverter converter, IReceiverDao dao, IMsgSender sender) {
        this.context = ctx;
        this.properties = properties;
        this.dao = dao;
        this.converter = converter;
        this.sender = sender;
        this.sf = SearchFactory.getInstance();

    }

    public List<IGhostResponse> handle(List<String> requestList) {
        if (requestList.size() > 0) {
            String reqName = "";

            List<IGhostRequest> list = this.converter.stringToRequest(requestList);
            Map<String, List<RequestProperties>> map = new HashMap<>();
            List<IGhostResponse> responses = new ArrayList<>();

            for (IGhostRequest req : list) {
                RequestProperties requestProperties = new RequestProperties();

                requestProperties.setRequest(req);

                IReadRepository readRepository =
                        (IReadRepository) context.getBean(properties.getEssences().get(req.getBeanName()).get("dao"));

                requestProperties.setReadRepository(readRepository);

                ISearchExpression expr = this.sf
                        .or()
                        .add(
                                this.sf.sUnit(
                                        req.getFieldName(),
                                        ComparisonOperator.EQUAL,
                                        this.converter.convertValueFromString(req.getValue(), req.getFieldType())
                                )
                        );

                requestProperties.setExpression(expr);

                if (map.containsKey(req.getBeanName())) {
                    map.get(req.getBeanName()).add(requestProperties);
                } else {
                    List<RequestProperties> props = new ArrayList<>();
                    props.add(requestProperties);
                    map.put(req.getBeanName(), props);
                }


            }

            List<IGhostResponse> resp = new ArrayList<>();

            /**
             * подумать, как совместить запросы (сейчас делает 1 запрос на 1 сущность)
             * (или писать в com.skachko.receiver кастомный дао, с sql (которая возвращает returning UUID и FIELD_NAME) . и маппером, который это парсит
             * табилца и схема есть в IReadRepository
             * +
             */
            for (Map.Entry<String, List<RequestProperties>> entry : map.entrySet()) {
                List<IGhostResponse> r = this.dao.readAll(entry);
                UUID responseUuid = UUID.randomUUID();
                if (r != null) {
                    GhostStatus status;
                    for (RequestProperties requestProperties : entry.getValue()) {
                        status = GhostStatus.NOT_FOUND;
                        for (IGhostResponse response : r) {
                            if (requestProperties.getRequest().getValue().equals(response.getValue())) {
                                response.setRequestUuid(requestProperties.getRequest().getUuid());
                                response.setFieldName(requestProperties.getRequest().getFieldName());
                                response.setBeanName(requestProperties.getRequest().getBeanName());
                                response.setResponseBeanUuid(responseUuid);
                                status = GhostStatus.FOUND;
                                response.setStatus(status);

                            }

                        }
                        if (GhostStatus.NOT_FOUND.equals(status)){
                            IGhostResponse notFound = new GhostResponse();
                            notFound.setStatus(status);
                            notFound.setValue(requestProperties.getRequest().getValue());
                            notFound.setRequestUuid(requestProperties.getRequest().getUuid());
                            notFound.setFieldName(requestProperties.getRequest().getFieldName());
                            notFound.setBeanName(requestProperties.getRequest().getBeanName());
                            notFound.setResponseBeanUuid(responseUuid);
                            r.add(notFound);
                        }
                    }


                    this.sender.sendMsg(entry.getValue().get(0).getRequest().getSenderName(), MessageType.RESPONSE,this.converter.responseToString(r));
                    resp.addAll(r);
                }
            }



            return resp;
        }
        throw new HandlerException("no data to process");
    }






}
