package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.classes.Indio;
import com.mygdx.game.classes.Medico;
import com.mygdx.game.classes.Pistoleiro;
import com.mygdx.game.classes.Xerife;
import com.mygdx.game.entities.Player;

public class EscolherClasse implements Screen {

    private MyGame game;
    private Stage stage;
    private Texture backgroundTexture;
    private BitmapFont font;
    private Skin skin;
    private SpriteBatch batch;
    private Player jogador;

    private String[] classes = {"Pistoleiro", "Xerife", "Médico", "Índio"};
    private int[] poderClasses = {10, 14, 6, 8};
    private int[] resistenciaClasses = {10, 8, 12, 10};
    private int[] agilidadeClasses = {8, 6, 4, 10};
    private int[] inteligenciaClasses = {6, 6, 12, 10};
    private int[] determinacaoClasses = {6, 6, 10, 14};
    private Texture[] classTextures;
    private Texture bordaTexture;
    private Label classLabel, poderLabel, resistenciaLabel, agilidadeLabel, inteligenciaLabel, determinacaoLabel;

    private Animation<TextureRegion>[] walkRightAnimations;
    private TextureRegion[] walkRightFrames;
    private Texture[] classSpriteTextures;
    private float stateTime;
    private int currentClassIndex = -1;

    public EscolherClasse(MyGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("menu/escolherClasseMenuPixel.png");
        batch = new SpriteBatch();

        // Criando o estilo da fonte
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

        skin = new Skin(Gdx.files.internal("fonts/pixthulhu-ui.json"));

        classTextures = new Texture[] {
            new Texture("menu/pistoleiroWantedd.png"),
            new Texture("menu/xerifeWantedd.png"),
            new Texture("menu/medicoWantedd.png"),
            new Texture("menu/indioWantedd.png")
        };
        
        bordaTexture = new Texture("menu/bordaWanted.png");

        classSpriteTextures = new Texture[] {
            new Texture("sprites/cowboy_sprites64.png"),
            new Texture("sprites/xerife_sprites64.png"),
            new Texture("sprites/medico_sprites64.png"),
            new Texture("sprites/indio_sprites64.png")
        };

        walkRightAnimations = new Animation[classes.length];
        for (int i = 0; i < classes.length; i++) {
            TextureRegion[][] tmp = TextureRegion.split(classSpriteTextures[i], 64, 64);
            walkRightFrames = new TextureRegion[9];
            for (int j = 0; j < 9; j++) {
                walkRightFrames[j] = tmp[11][j];
            }
            walkRightAnimations[i] = new Animation<>(0.1f, walkRightFrames);
        }

        for (int i = 0; i < classes.length; i++) {
            final int index = i;
            Image classImage = new Image(classTextures[i]);
            Image bordaImage = new Image(bordaTexture);
            classImage.setSize(200, 200);
            bordaImage.setSize(220, 220);
            if (i <= 1) {
                classImage.setPosition(110 + (i * 250), 390);
                bordaImage.setPosition(100 + (i * 250), 380);
            } else {
                classImage.setPosition(110 + ((i * 250) - 500), 140);
                bordaImage.setPosition(100 + ((i * 250) - 500), 130);
            }

            classImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    switch (index) {
                        case 0:
                            game.setScreen(new Play(0, game, jogador, true));
                            break;
                        case 1:
                            game.setScreen(new Play(1, game, jogador, true));
                            break;
                        case 2:
                            game.setScreen(new Play(2, game, jogador, true));
                            break;
                        case 3:
                            game.setScreen(new Play(3, game, jogador, true));
                            break;
                    }
                    dispose();
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                    // Texto das classes e dos atributos
                	bordaImage.setVisible(true); // Mostrar borda quando o mouse estiver sobre a imagem
                	classLabel.setText(classes[index]);
                    classLabel.setPosition(classImage.getX() + classImage.getWidth() / 2, classImage.getY() + classImage.getHeight() + 10, Align.center);
                    poderLabel.setText("Poder: " + poderClasses[index]);
                    poderLabel.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2 - 40, Align.center);
                    resistenciaLabel.setText("Resistência: " + resistenciaClasses[index]);
                    resistenciaLabel.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2 - 80, Align.center);
                    agilidadeLabel.setText("Agilidade: " + agilidadeClasses[index]);
                    agilidadeLabel.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2 - 120, Align.center);
                    inteligenciaLabel.setText("Inteligência: " + inteligenciaClasses[index]);
                    inteligenciaLabel.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2 - 160, Align.center);
                    determinacaoLabel.setText("Determinação: " + determinacaoClasses[index]);
                    determinacaoLabel.setPosition(Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 2 - 200, Align.center);

                    currentClassIndex = index;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                	bordaImage.setVisible(false);
                	classLabel.setText("");
                    poderLabel.setText("");
                    resistenciaLabel.setText("");
                    agilidadeLabel.setText("");
                    inteligenciaLabel.setText("");
                    determinacaoLabel.setText("");
                    currentClassIndex = -1;
                }
            });
            stage.addActor(bordaImage);
            stage.addActor(classImage);
            bordaImage.setVisible(false);
        }

        classLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        classLabel.setSize(200, 50);
        classLabel.setAlignment(Align.center);
        stage.addActor(classLabel);
        
        poderLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        poderLabel.setSize(160, 40);
        poderLabel.setAlignment(Align.center);
        stage.addActor(poderLabel);
        
        resistenciaLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        resistenciaLabel.setSize(160, 40);
        resistenciaLabel.setAlignment(Align.center);
        stage.addActor(resistenciaLabel);
        
        agilidadeLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        agilidadeLabel.setSize(160, 40);
        agilidadeLabel.setAlignment(Align.center);
        stage.addActor(agilidadeLabel);
        
        inteligenciaLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        inteligenciaLabel.setSize(160, 40);
        inteligenciaLabel.setAlignment(Align.center);
        stage.addActor(inteligenciaLabel);
        
        determinacaoLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        determinacaoLabel.setSize(160, 40);
        determinacaoLabel.setAlignment(Align.center);
        stage.addActor(determinacaoLabel);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (currentClassIndex != -1) {
            stateTime += delta;
            TextureRegion currentFrame = walkRightAnimations[currentClassIndex].getKeyFrame(stateTime, true);
            batch.draw(currentFrame, Gdx.graphics.getWidth() - 400, Gdx.graphics.getHeight() / 2 - 20, 200, 200);
        } else {
        	classLabel.setText("Escolha sua classe!");
        	classLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 30, Align.center);
        }

        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        font.dispose();
        batch.dispose();
        skin.dispose();
        for (Texture texture : classTextures) {
            texture.dispose();
        }
        for (Texture texture : classSpriteTextures) {
            texture.dispose();
        }
    }
}
