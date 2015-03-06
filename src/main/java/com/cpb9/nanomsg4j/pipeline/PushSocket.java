package com.cpb9.nanomsg4j.pipeline;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class PushSocket extends Socket {
  public PushSocket(int domain) {
    super(domain, Nanomsg.constants.NN_PUSH);
  }

  public PushSocket() {
    this(Nanomsg.constants.AF_SP);
  }
}
