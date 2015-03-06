package com.cpb9.nanomsg4j;

import com.sun.jna.Memory;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Shein
 */
public class MessageHeader extends Structure
{
    private IoVec.ByReference iov;
    private int iovlen;
    private Memory control;
    private SizeT controllen;

    @Override
    protected List getFieldOrder()
    {
        return Arrays.asList("iov", "iovlen", "control", "controllen");
    }
}
