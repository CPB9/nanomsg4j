package com.cpb9.nanomsg4j.reqrep;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class RepSocket extends Socket {
    public RepSocket(int domain) {
        super(domain, Nanomsg.constants.NN_REP);
    }

    public RepSocket() {
        this(Nanomsg.constants.AF_SP);
    }
}
