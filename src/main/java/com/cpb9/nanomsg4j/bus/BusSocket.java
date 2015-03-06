package com.cpb9.nanomsg4j.bus;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class BusSocket extends Socket {
  public BusSocket(int domain) {
    super(domain, Nanomsg.constants.NN_BUS);
  }

  public BusSocket() {
    this(Nanomsg.constants.AF_SP);
  }
}
