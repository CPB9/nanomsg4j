package com.cpb9.nanomsg4j.async;

import com.cpb9.nanomsg4j.Socket;
import com.cpb9.nanomsg4j.async.AsyncOperation;


public interface IAsyncScheduler {
    public void schedule(final Socket sock, final AsyncOperation op, final IAsyncRunnable runnable) throws InterruptedException;
}
