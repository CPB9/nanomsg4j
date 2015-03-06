package com.cpb9.nanomsg4j.pipeline;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class PullSocket extends Socket {
  public PullSocket(int domain) {
    super(domain, Nanomsg.constants.NN_PULL);
  }

  public PullSocket() {
    this(Nanomsg.constants.AF_SP);
  }
}
