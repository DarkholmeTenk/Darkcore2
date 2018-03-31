package io.darkcraft.darkcore.network.exception;

public class NetworkCommandMissingCallbackException extends NetworkCommandException
{
	private static final long serialVersionUID = 1L;

	public NetworkCommandMissingCallbackException(int ackID, Object command)
	{
		super("No callback found for " + ackID + " with command " + command);
	}
}
