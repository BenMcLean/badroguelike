package net.benmclean.badroguelike.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.benmclean.badroguelike.BadRoguelikeGame;

public class DesktopLauncher {
	public static void main (String[] args) {
    	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
   		config.width = 50 * 22;
    	config.height = 32 * 22;
    	config.addIcon("Tentacle-16.png", Files.FileType.Classpath);
    	config.addIcon("Tentacle-32.png", Files.FileType.Classpath);
    	config.addIcon("Tentacle-128.png", Files.FileType.Classpath);
    	new LwjglApplication(new BadRoguelikeGame(), config);
    }
}
