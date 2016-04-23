package net.benmclean.badroguelike;

import com.badlogic.gdx.Game;

public class BadRoguelikeGame extends Game {
	@Override
	public void create() {
		setScreen(new net.benmclean.badroguelike.view.GameScreen());
		//setScreen(new net.benmclean.badroguelike.DebugTools.GraphicsViewer());
	}
}
