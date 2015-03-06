package com.cpb9.nanomsg4j.reqrep;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class ReqSocket extends Socket {
    public ReqSocket(int domain) {
        super(domain, Nanomsg.constants.NN_REQ);
    }

    public ReqSocket() {
        this(Nanomsg.constants.AF_SP);
    }
}
