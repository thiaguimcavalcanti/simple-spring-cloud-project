package com.bot.schedule.binance.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.servlet.ServletContext;
import java.net.URI;

public abstract class BinanceCommonEvent extends WebSocketClient {

    private static final Logger LOG = LogManager.getLogger(BinanceCommonEvent.class.getName());

    ServletContext servletContext;

    public BinanceCommonEvent(String url, ServletContext servletContext) {
        super(URI.create(""));
        this.servletContext = servletContext;
        connect(url);
    }

    private void connect(String url) {
        this.uri = generateUri(url);
        super.connect();
    }

    abstract URI generateUri(String url);

    @Override
    public void onOpen(ServerHandshake handShakeData) {
        LOG.warn(handShakeData);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LOG.warn("Binance Ticker Stream - On close - Code: " + code + ", reason: " + reason + ", Remote: " + remote);
    }

    @Override
    public void onError(Exception ex) {
        LOG.error(ex);
    }
}
