package com.cpb9.nanomsg4j;

import java.lang.Runnable;
import com.cpb9.nanomsg4j.NativeLibrary;


public class Device implements Runnable {
  private final int sourceSocket;
  private final int destSocket;

  public Device(final int sourceSocket, final int destSocket) {
    this.sourceSocket = sourceSocket;
    this.destSocket = destSocket;
  }

  public void run() {
    NativeLibrary.nn_device(sourceSocket, destSocket);
  }
}
