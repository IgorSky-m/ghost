package com.skachko.sender.listeners.api;

import java.util.List;

public interface IMsgListener {
    void listen(List<String> listMsg);
}
