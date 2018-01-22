package io.darkcraft.darkcore.nbt.exception;

/**
 * A top level exception which means that something has gone wrong during an NBT operation
 * @author dark
 *
 */
public class NBTException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public NBTException(String message, Throwable e)
	{
		super(message, e);
	}

	public NBTException(String message)
	{
		super(message);
	}

	public NBTException(Throwable e)
	{
		super(e);
	}
}
