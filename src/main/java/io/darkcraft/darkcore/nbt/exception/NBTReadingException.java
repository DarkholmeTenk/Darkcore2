package io.darkcraft.darkcore.nbt.exception;

public class NBTReadingException extends NBTException
{
	private static final long serialVersionUID = 1L;

	public NBTReadingException(String message)
	{
		super(message);
	}

	public NBTReadingException(String message, Throwable e)
	{
		super(message, e);
	}
}
