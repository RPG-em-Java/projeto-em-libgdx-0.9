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

import java.util.ArrayList;
import java.util.List;

public class Inventario extends Sprite {
    private Texture background, cursorTexture;
    private BitmapFont font;
    private Texture[] classTextures;
    private Texture classImage;
    private Player player;
    private Stage stage;
    private Label itemLabel;
    private Item armaEquipada, roupaEquipada;
    
    private boolean oMouseEstaEmCima;
    private boolean isVisible;
    private float animationTime;
    private float animationDuration = 0.3f;
    private boolean isOpening;
    private boolean isClosing;
    private boolean trocarArma;
    private boolean trocarRoupa;
    private List<Image> itemImages; // Lista para rastrear imagens dos itens

    public Inventario(Player player, Stage stage) {
        this.player = player;
        this.stage = stage;
        background = new Texture("hud/inventariobase.png");
        cursorTexture = new Texture("hud/hud_base2.png");
        armaEquipada = new Item(0);
        roupaEquipada = new Item(0);

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
        
        // Criar o label de descrição do item
        itemLabel = new Label("", new Label.LabelStyle(font, Color.WHITE));
        itemLabel.setSize(200, 50);
        itemLabel.setPosition(10, 10);
        stage.addActor(itemLabel);

        // Inicializar a lista de imagens dos itens
        itemImages = new ArrayList<>();
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
        if (trocarArma) {
        	
        }
    	
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
                // Remover imagens dos itens do stage quando o inventário for fechado
                for (Image itemImage : itemImages) {
                    itemImage.remove();
                }
                itemImages.clear();
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
        batch.draw(player.getArmaEquipada().getTexturaItem(),460,315,75,75);
        batch.draw(player.getRoupaEquipada().getTexturaItem(),460,429,75,75);
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                Item item = items[i][j];
                int indexI = i;
                int indexJ = j;
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
                        public void clicked(InputEvent event, float x, float y) {
                    		Gdx.app.log("Mytag", "Clicou no item");
                    		if (item.getTipo() == 1) {
                    			player.trocarArma(items[indexI][indexJ], armaEquipada);
                    			Gdx.app.log("Mytag", armaEquipada.getTitulo());
                    		} else if (item.getTipo() == 2) {
                    			player.trocarRoupa(items[indexI][indexJ], roupaEquipada);
                    		}
                    		//player.setMatrizItems(items);
                    	}
                    	
                    	@Override
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                            oMouseEstaEmCima = true;
                            itemLabel.setText(item.getDescricao());
                            itemLabel.setPosition(10, 0 - 10); // Ajustar posição do label se necessário
                        }

                        @Override
                        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                            oMouseEstaEmCima = false;
                            itemLabel.setText("");
                        }
                    });

                    // Adicionar a imagem ao stage e à lista
                    stage.addActor(itemImage);
                    itemImages.add(itemImage);
                }
            }
        }

        batch.end();
        batch.setColor(1, 1, 1, 1); // Reset alpha
        font.setColor(1, 1, 1, 1);

        // Desenhar a cursorTexture se o mouse estiver em cima de um item
        if (oMouseEstaEmCima) {
            batch.begin();
            batch.draw(cursorTexture, 0-200, 0-75, 1400, 140); // Ajustar posição do cursorTexture; // Ajustar posição do cursorTexture
            batch.end();
        }

        // O itemLabel é desenhado pelo stage no topo de todos os elementos
        stage.draw();
    }

    public void dispose() {
        background.dispose();
        cursorTexture.dispose();
        font.dispose();
        cursorTexture.dispose();
    }
}

