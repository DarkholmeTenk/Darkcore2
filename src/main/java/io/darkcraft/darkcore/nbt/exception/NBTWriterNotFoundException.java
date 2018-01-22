package io.darkcraft.darkcore.nbt.exception;

public class NBTWriterNotFoundException extends NBTException
{
	private static final long serialVersionUID = 1L;

	public NBTWriterNotFoundException(Class<?> c, String further)
	{
		super("It was not possible to construct an injector for the class: " + c.getSimpleName()
				+ "\n"+further);
	}

}
