package io.darkcraft.darkcore.network.exception;

public class NetworkCommandTypeException extends NetworkCommandException
{
	private static final long serialVersionUID = 1L;

	public NetworkCommandTypeException()
	{
		super();
	}

	public NetworkCommandTypeException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

	public NetworkCommandTypeException(String arg0)
	{
		super(arg0);
	}

	public NetworkCommandTypeException(Throwable arg0)
	{
		super(arg0);
	}
}
