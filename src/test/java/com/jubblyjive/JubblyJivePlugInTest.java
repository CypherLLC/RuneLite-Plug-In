package com.jubblyjive;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class JubblyJivePlugInTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(JubblyJivePlugInPlugin.class);
		RuneLite.main(args);
	}
}