package com.skachko.sender.handlers.api;

import java.util.List;

public interface IMsgHandler<T> {
    List<T> handle(List<String> requestList);
}
