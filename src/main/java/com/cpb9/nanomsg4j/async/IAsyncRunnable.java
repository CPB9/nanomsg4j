package com.cpb9.nanomsg4j.async;

import com.cpb9.nanomsg4j.exceptions.EAgainException;

public interface IAsyncRunnable {
  public void run() throws EAgainException;
}
