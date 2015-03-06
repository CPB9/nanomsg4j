package com.cpb9.nanomsg4j.async;

import java.lang.Throwable;

public interface IAsyncCallback<T> {
  public void success(T result);
  public void fail(Throwable t);
}
