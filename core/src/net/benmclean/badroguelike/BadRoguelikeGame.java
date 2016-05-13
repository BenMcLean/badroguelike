package net.benmclean.badroguelike;

import com.badlogic.gdx.Game;

public class BadRoguelikeGame extends Game {
	public long SEED = System.currentTimeMillis();

	@Override
	public void create() {
		setScreen(new net.benmclean.badroguelike.view.GameScreen(SEED));
		//setScreen(new net.benmclean.badroguelike.DebugTools.GraphicsViewer());
	}
}
