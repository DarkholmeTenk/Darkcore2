package io.darkcraft.darkcore.network.exception;

public class NetworkCommandException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public NetworkCommandException()
	{
		super();
	}

	public NetworkCommandException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public NetworkCommandException(String message)
	{
		super(message);
	}

	public NetworkCommandException(Throwable cause)
	{
		super(cause);
	}

}
