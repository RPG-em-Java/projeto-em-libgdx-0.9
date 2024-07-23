package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.MyGame;

public class MainMenu implements Screen {

    private MyGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private BitmapFont font;
    private Skin skin;
    private TextButton playButton;
    private SpriteBatch batch;

    public MainMenu(MyGame game) {
        this.game = game;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("menu/menuInicialGuardioesPixel.png");
        batch = new SpriteBatch();

        // Criando o estilo da fonte
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Criando o skin e o botão de jogar
        skin = new Skin(Gdx.files.internal("fonts/pixthulhu-ui.json"));
        playButton = new TextButton("Jogar", skin);
        playButton.setSize(200, 80);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new EscolherClasse(game));
                dispose();
            }
        });

        // Configurando a tabela para centralizar os widgets
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(playButton).size(200, 80).padTop(60);
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(batch, "Guardiões das Lendas Antigas", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 280, 0, Align.center, false);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        font.dispose();
        batch.dispose();
        skin.dispose();
    }
}
