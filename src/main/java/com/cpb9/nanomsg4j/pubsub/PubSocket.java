package com.cpb9.nanomsg4j.pubsub;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;

public class PubSocket extends Socket {
  public PubSocket(int domain) {
    super(domain, Nanomsg.constants.NN_PUB);
  }

  public PubSocket() {
    this(Nanomsg.constants.AF_SP);
  }
}
