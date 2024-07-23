package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.entities.Player;

public class InventarioTeste extends Sprite {
    private Texture background, cursorTexture;
    private BitmapFont font;
    private Texture[] classTextures;
    private Texture classImage;
    private Player player;
    private Stage stage;
    private Label itemLabel;
    
    private boolean oMouseEstaEmCima;
    private boolean isVisible;
    private float animationTime;
    private float animationDuration = 0.3f;
    private boolean isOpening;
    private boolean isClosing;

    public InventarioTeste(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        background = new Texture("hud/inventariobase.png");
        cursorTexture = new Texture("hud/hud_base2.png");

        isVisible = false;
        animationTime = 0;
        classTextures = new Texture[] {
            new Texture("menu/pistoleiroWantedd.png"),
            new Texture("menu/xerifeWantedd.png"),
            new Texture("menu/medicoWantedd.png"),
            new Texture("menu/indioWantedd.png")
        };

        // Criando o estilo da fonte
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        generator.dispose();

        for (int i = 0; i < 4; i++) {
            if (player.getClasseTipo() == i) {
                classImage = classTextures[i];
            }
        }
        
        itemLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        itemLabel.setSize(200, 50);
        stage.addActor(itemLabel);
    }

    public void toggle() {
        if (isVisible) {
            isClosing = true;
            isOpening = false;
        } else {
            isOpening = true;
            isClosing = false;
        }
    }

    public void update(float delta) {
        if (isOpening) {
            animationTime += delta;
            if (animationTime >= animationDuration) {
                animationTime = animationDuration;
                isOpening = false;
                isVisible = true;
                Gdx.app.log("Mytag", "Inventario abriu");
            }
        } else if (isClosing) {
            animationTime -= delta;
            if (animationTime <= 0) {
                animationTime = 0;
                isClosing = false;
                isVisible = false;
                Gdx.app.log("Mytag", "Inventario fechou");
            }
        }
    }

    public void drawInventario(SpriteBatch batch) {
        if (!isVisible && animationTime == 0) return;

        float alpha = Interpolation.fade.apply(animationTime / animationDuration);
        batch.setColor(1, 1, 1, alpha);

        batch.begin();
        batch.draw(background, 0, 0, 1280, 720);
        batch.draw(classImage, 153, 462, 120, 120);

        font.setColor(1, 1, 1, alpha);
        font.draw(batch, Double.toString(player.getVidaAtual()) + " / " + Double.toString(player.getVidaMax()), 155, 300);
        font.draw(batch, Double.toString(player.getEnergiaAtual()) + " / " + Double.toString(player.getEnergiaMax()), 195, 273);
        font.draw(batch, Double.toString(player.getPoder()), 170, 220);
        font.draw(batch, Double.toString(player.getResistencia()), 245, 193);
        font.draw(batch, Double.toString(player.getAgilidade()), 220, 168);
        font.draw(batch, Double.toString(player.getInteligencia()), 260, 141);
        font.draw(batch, Double.toString(player.getDeterminacao()), 265, 114);
        font.draw(batch, Double.toString(player.getRecompensa()) + " / " + Integer.toString(player.getRecompensaProximoNivel()) + ".0", 163, 438);

        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverter Y para coordenadas de tela
        Item[][] items = player.getMatrizItems();
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                Item item = items[i][j];
                if (item != null) {
                    int xImagem = (j * 111) + 667;
                    int yImagem = 566 - (i * 100);

                    // Desenhar a textura do item
                    batch.draw(item.getTexturaItem(), xImagem, yImagem, 75, 75);

                    // Criar uma Image widget para o item
                    Image itemImage = new Image(item.getTexturaItem());
                    itemImage.setPosition(xImagem, yImagem);
                    itemImage.setSize(75, 75);

                    // Adicionar um listener para detectar quando o mouse está sobre a imagem
                    itemImage.addListener(new ClickListener() {
                        @Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            oMouseEstaEmCima = true;
                            
                            itemLabel.setText(item.getDescricao());
                            itemLabel.setPosition(10, -5);
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            oMouseEstaEmCima = false;
                            
                            itemLabel.setText("");
                        }
                    });

                    // Adicionar a imagem ao stage
                    stage.addActor(itemImage);
                    

                }
            }
        }

        batch.end();
        batch.setColor(1, 1, 1, 1); // Reset alpha
        font.setColor(1, 1, 1, 1);

        // Desenhar a cursorTexture se o mouse estiver em cima de um item
        if (oMouseEstaEmCima) {
            batch.begin();
            batch.draw(cursorTexture, 0-100, 0, 320, 140);
            batch.end();
        }
    }

    public void dispose() {
        background.dispose();
        font.dispose();
        cursorTexture.dispose();
    }
}
