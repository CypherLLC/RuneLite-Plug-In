package com.jubblyjive;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;


@Slf4j
@PluginDescriptor(
	name = "Jubbly Jive"
)
public class JubblyJivePlugInPlugin extends Plugin
{

    @Inject
    private ClientToolbar clientToolbar;

    private JubblyJivePanel panel;
    private NavigationButton navButton;

	@Inject
	private Client client;

	@Inject
	private JubblyJivePlugInConfig config;

	@Override
	protected void startUp() throws Exception
	{
        panel = injector.getInstance(JubblyJivePanel.class);
        panel.init();

        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/wiki_icon.png");

        navButton = NavigationButton.builder()
                .tooltip("Jubbly Jive")
                .icon(icon)
                .priority(10)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
        panel.deinit();
        clientToolbar.removeNavigation(navButton);
        panel = null;
        navButton = null;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}



	@Provides
    JubblyJivePlugInConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(JubblyJivePlugInConfig.class);
	}
}
