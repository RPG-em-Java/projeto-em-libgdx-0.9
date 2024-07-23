package com.mygdx.game;

import com.badlogic.gdx.Game;
//import com.mygdx.game.screens.Play;
import com.mygdx.game.screens.MainMenu;

public class MyGame extends Game {
	
	@Override
	public void create() {
		this.setScreen(new MainMenu(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		super.resize(width,height);
	}
	
	public void pause() {
		super.pause();
	}
	
	public void resume() {
		super.resume();
	}
	
}
