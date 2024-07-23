package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.NPC;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class CombatScreen implements Screen {

    private Game game;
	private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private Player player;
    private NPC npc;
    private boolean playerTurn;
    private boolean gameOver;
    private boolean victory;
    private float gameOverTimer;
    private float victoryTimer;
    private Texture playerTexture;
    private Texture npcTexture;
    private Texture attackTexture;
    private Texture backgroundCombate;
    
    public CombatScreen(Player player, NPC npc, Game game) {
        this.game = game;
    	this.player = player;
        this.npc = npc;
        this.playerTurn = true;
        this.gameOver = false;
        this.victory = false;
        this.gameOverTimer = 0;
        this.victoryTimer = 0;
        
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        generator.dispose();
        
        playerTexture = new Texture("combate/spritepistoleiro.png");
        npcTexture = new Texture("combate/spritepistoleiro.png");
        attackTexture = new Texture("combate/hitmark.png");
        backgroundCombate = new Texture("combate/fundoCombate.png");
    }

    @Override
    public void show() {
    	camera.position.set(640, 360, 0);
    	camera.update();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (gameOver) {
            gameOverTimer += delta;
            if (gameOverTimer > 5) {
                // Voltar ao menu inicial após 5 segundos
            	game.setScreen(new GameOverScreen(game));
            }
        } else if (victory) {
            victoryTimer += delta;
            if (victoryTimer > 5) {
                // Voltar ao jogo após vitória
                game.setScreen(new VictoryScreen(player, game));
            }
        } else {
            if (playerTurn) {
                handlePlayerInput();
            } else {
                npcAttack();
                playerTurn = true;
            }
        }

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backgroundCombate, 0, 0, 1280, 720);
        font.draw(batch, "Vida: " + player.getVidaAtual() + "/" + player.getVidaMax(), 50, 550);
        font.draw(batch, "Energia: " + player.getEnergiaAtual() + "/" + player.getEnergiaMax(), 50, 500);
        font.draw(batch, "Vida: " + npc.getVidaAtual() + "/" + npc.getVidaMax(), 600, 550);
        font.draw(batch, "Energia: " + npc.getEnergiaAtual() + "/" + npc.getEnergiaMax(), 600, 500);
        batch.draw(playerTexture, 160, 75, 400, 400);
        batch.draw(npcTexture, 750, 75, 400, 400);
        shapeRenderer.setColor(Color.RED);
        if (gameOver) {
            font.draw(batch, "GAME OVER", 400, 300);
        } else if (victory) {
            font.draw(batch, "VICTORY", 400, 300);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.end();
    }

    private void handlePlayerInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            npc.setVidaAtual(npc.getVidaAtual() - player.getDano());
            if (npc.getVidaAtual() <= 0) {
                victory = true;
            }
            playerTurn = false;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (player.getEnergiaAtual() >= 4) {
                npc.setVidaAtual(npc.getVidaAtual() - (player.getDano() * 2));
                player.setEnergiaAtual(player.getEnergiaAtual() - 4);
                if (npc.getVidaAtual() <= 0) {
                    victory = true;
                }
                playerTurn = false;
            }
        }
    }

    private void npcAttack() {
        player.setVidaAtual(player.getVidaAtual() - npc.getDano());
        if (player.getVidaAtual() <= 0) {
            gameOver = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        playerTexture.dispose();
        npcTexture.dispose();
    }
}
