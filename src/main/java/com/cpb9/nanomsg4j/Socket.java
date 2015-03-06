package com.cpb9.nanomsg4j;

import com.cpb9.nanomsg4j.exceptions.IOException;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.cpb9.nanomsg4j.exceptions.IOException;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;


public abstract class Socket
{
    private final int domain;
    private final int protocol;
    private final int socket;

    private boolean closed = false;
    private boolean opened = false;

    public Socket(final int domain, final int protocol)
    {
        this.domain = domain;
        this.protocol = protocol;
        this.socket = NativeLibrary.nn_socket(domain, protocol);
        this.opened = true;

        this.setSendTimeout(600);
        this.setRecvTimeout(600);
    }

    public synchronized void close() throws IOException
    {
        if (this.opened && !this.closed)
        {
            this.closed = true;
            final int rc = NativeLibrary.nn_close(this.socket);

            if (rc < 0)
            {
                final int errno = Nanomsg.getErrorNumber();
                final String msg = Nanomsg.getError();
                throw new IOException(msg, errno);
            }
        }
    }

    public int getNativeSocket()
    {
        return this.socket;
    }

    public synchronized void bind(final String dir) throws IOException
    {
        final int rc = NativeLibrary.nn_bind(this.socket, dir);

        if (rc < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }
    }

    public synchronized void connect(final String dir) throws IOException
    {
        final int rc = NativeLibrary.nn_connect(this.socket, dir);

        if (rc < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }
    }

    /**
     * Helper method for send string to socket
     * with option to set blocking flag.
     *
     * @param data     string value that represents a message.
     * @param blocking set blocking or non blocking flag.
     * @return number of sended bytes.
     */
    public int send(final String data, final boolean blocking) throws IOException
    {
        final Charset encoding = Charset.forName("UTF-8");
        return this.send(data.getBytes(encoding), blocking);
    }

    /**
     * Helper method for send string to socket.
     * <p/>
     * This operation is blocking by default.
     *
     * @param data string value that represents a message.
     * @return number of sended bytes.
     */
    public int send(final String data) throws IOException
    {
        return this.send(data, true);
    }

    /**
     * Helper method for send bytes array to socket
     * with option to set blocking flag.
     *
     * @param data     a bytes array that represents a message.
     * @param blocking set blocking or non blocking flag.
     * @return number of sended bytes.
     */
    public synchronized int send(final byte[] data, final boolean blocking) throws IOException
    {
        final int socket = getNativeSocket();
        final int flags = blocking ? 0 : Nanomsg.constants.NN_DONTWAIT;
        final int rc = NativeLibrary.nn_send(socket, data, data.length, flags);

        if (rc < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }

        return rc;
    }

    /**
     * Helper method for send bytes array to socket.
     * <p/>
     * This operation is blocking by default.
     *
     * @param data a bytes array that represents a message.
     * @return number of sended bytes.
     */
    public int send(final byte[] data) throws IOException
    {
        return this.send(data, true);
    }

    /**
     * Helper method for receive message from socket as string
     * with option for set blocking flag.
     * <p/>
     * This method uses utf-8 encoding for converts a bytes array
     * to string.
     *
     * @param blocking set blocking or non blocking flag.
     * @return receved data as unicode string.
     */
    public String recvString(final boolean blocking) throws IOException
    {
        final byte[] received = this.recvBytes(blocking);
        final Charset encoding = Charset.forName("UTF-8");
        return new String(received, encoding);
    }

    /**
     * Helper method for receive message from socket as string
     * in a blocking mode.
     * <p/>
     * This method uses utf-8 encoding for converts a bytes array
     * to string.
     *
     * @return receved data as unicode string.
     */
    public String recvString() throws IOException
    {
        return this.recvString(true);
    }

    /**
     * Helper method for receive message from socket as bytes array
     * with option for set blocking flag.
     *
     * @param blocking set blocking or non blocking flag.
     * @return receved data as bytes array
     */
    public synchronized byte[] recvBytes(boolean blocking) throws IOException
    {
        final PointerByReference ptrBuff = new PointerByReference();

        final int socket = getNativeSocket();
        final int flags = blocking ? 0 : Nanomsg.constants.NN_DONTWAIT;
        final int received = NativeLibrary.nn_recv(socket, ptrBuff, Nanomsg.constants.NN_MSG, flags);

        if (received < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            if (errno == Nanomsg.constants.EAGAIN)
            {
                return null;
            }
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }

        final Pointer result = ptrBuff.getValue();
        final byte[] bytesResult = result.getByteArray(0, received);

        NativeLibrary.nn_freemsg(result);
        return bytesResult;
    }

    /**
     * Helper method for receive message from socket as bytes array
     * in a blocking mode.
     * <p/>
     * This operation is blocking by default.
     *
     * @return receved data as bytes array
     */
    public byte[] recvBytes() throws IOException
    {
        return this.recvBytes(true);
    }

    /**
     * Receive message with option for set blocking flag.
     *
     * @param blocking set blocking or non blocking flag.
     * @return Message instance.
     */
    public ByteBuffer recv(final boolean blocking) throws IOException
    {
        final PointerByReference ptrBuff = new PointerByReference();

        final int socket = getNativeSocket();
        final int flags = blocking ? 0 : Nanomsg.constants.NN_DONTWAIT;
        final int received = NativeLibrary.nn_recv(socket, ptrBuff, Nanomsg.constants.NN_MSG, flags);

        if (received < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }

        final Pointer result = ptrBuff.getValue();
        final ByteBuffer buffer = result.getByteBuffer(0, received);

        NativeLibrary.nn_freemsg(result);
        return buffer;
    }

    /**
     * Send message to socket with option to set blocking flag.
     *
     * @param data     byte buffer that represents a message.
     * @param blocking set blocking or non blocking flag.
     * @return number of sended bytes.
     */
    public synchronized int send(final ByteBuffer data, final boolean blocking) throws IOException
    {
        final int socket = getNativeSocket();
        final int flags = blocking ? 0 : Nanomsg.constants.NN_DONTWAIT;
        final int rc = NativeLibrary.nn_send(socket, data, data.limit(), flags);

        if (rc < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }

        return rc;
    }

    /**
     * Send message to socket.
     *
     * @param data byte buffer that represents a message.
     * @return number of sended bytes.
     */
    public synchronized int send(final ByteBuffer data) throws IOException
    {
        return this.send(data, true);
    }

    /**
     * Get read file descriptor.
     *
     * @return file descriptor.
     */
    public int getRcvFd()
    {
        final int flag = Nanomsg.constants.NN_RCVFD;
        return getFd(flag);
    }

    /**
     * Get write file descriptor.
     *
     * @return file descriptor.
     */
    public int getSndFd()
    {
        final int flag = Nanomsg.constants.NN_SNDFD;
        return getFd(flag);
    }

    /**
     * Get file descriptor.
     *
     * @return file descriptor.
     */
    private synchronized int getFd(final int flag)
    {
        final IntByReference fd = new IntByReference();
        final IntByReference size_t = new IntByReference(Native.SIZE_T_SIZE);

        final int rc = NativeLibrary.nn_getsockopt(this.socket, Nanomsg.constants.NN_SOL_SOCKET,
                flag, fd.getPointer(), size_t.getPointer());

        if (rc < 0)
        {
            throw new IOException(Nanomsg.getError());
        }

        if (rc < 0)
        {
            final int errno = Nanomsg.getErrorNumber();
            final String msg = Nanomsg.getError();
            throw new IOException(msg, errno);
        }

        return fd.getValue();
    }

    /**
     * Set send timeout option to the socket.
     */
    public synchronized void setSendTimeout(final int milis)
    {
        final int socket = getNativeSocket();

        NativeLibrary.nn_setsockopt(socket, Nanomsg.constants.NN_SOL_SOCKET,
                Nanomsg.constants.NN_SNDTIMEO, newSizeT(milis), Native.SIZE_T_SIZE);
    }

    /**
     * Set recv timeout option to the socket.
     */
    public synchronized void setRecvTimeout(int milis)
    {
        final int socket = getNativeSocket();

        NativeLibrary.nn_setsockopt(socket, Nanomsg.constants.NN_SOL_SOCKET,
                Nanomsg.constants.NN_RCVTIMEO, newSizeT(milis), Native.SIZE_T_SIZE);
    }

    /**
     * Allocate and set size_t
     *
     * @param ptr   pointer
     * @param value timeout
     */
    private static void setSizeT(Memory ptr, long value)
    {
        if (Native.SIZE_T_SIZE == 8)
        {
            ptr.setLong(0, value);
        }
        else
        {
            ptr.setInt(0, (int) value);
        }
    }

    /**
     * Allocate new size_t
     *
     * @param value value
     * @return Memory pointer
     */
    private static Memory newSizeT(long value)
    {
        Memory ptr = new Memory(Native.SIZE_T_SIZE);
        setSizeT(ptr, value);
        return ptr;
    }
}
