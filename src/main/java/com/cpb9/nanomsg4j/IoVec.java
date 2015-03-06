package com.cpb9.nanomsg4j;

import com.sun.jna.Memory;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Shein
 */
public class IoVec extends Structure
{
    private final Memory base;
    private final SizeT len;

    public IoVec(Memory base, long len)
    {
        this.base = base;
        this.len = new SizeT(len);
    }

    @Override
    protected List getFieldOrder()
    {
        return Arrays.asList("base", "len");
    }
}
