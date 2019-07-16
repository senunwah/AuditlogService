package com.jumiapay.interview.auditlogservice.messaging;

import java.io.IOException;

public interface IListener {

    public void onListen(Object Object) throws IOException, ClassNotFoundException;
}
