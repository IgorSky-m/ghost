package com.skachko.receiver.listeners.api;

import java.util.List;


public interface IMsgListener {
    void listen(List<String> listMsg);
}
