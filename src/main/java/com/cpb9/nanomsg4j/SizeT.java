package com.cpb9.nanomsg4j;

import com.sun.jna.IntegerType;
import com.sun.jna.Native;

/**
 * @author Artem Shein
 */
public class SizeT extends IntegerType
{
    public SizeT(long value)
    {
        super(Native.SIZE_T_SIZE, value);
    }

    public SizeT()
    {
        super(Native.SIZE_T_SIZE);
    }
}
