package com.cpb9.nanomsg4j.pair;

import com.cpb9.nanomsg4j.Nanomsg;
import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.Nanomsg;


public class PairSocket extends Socket {
  public PairSocket(int domain) {
    super(domain, Nanomsg.constants.NN_PAIR);
  }

  public PairSocket() {
    this(Nanomsg.constants.AF_SP);
  }
}
