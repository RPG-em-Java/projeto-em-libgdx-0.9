package com.mygdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.entities.Player;

public class BarraVidaPlay extends Sprite {
    private Texture backgroundBarraVida;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private ShapeRenderer shapeRenderer;
    private ShapeRenderer shapeRendererAtras;
    private Player player;

    public BarraVidaPlay(Player player) {
        this.player = player;
        this.backgroundBarraVida = new Texture("hud/hud_base1.png");
        //this.font = new BitmapFont(); // Use a fonte padrão ou carregue uma personalizada
        this.glyphLayout = new GlyphLayout();
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRendererAtras = new ShapeRenderer();
        
        // Criando o estilo da fonte
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/upheavtt.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.WHITE;
        this.font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void drawBarraVidaP(SpriteBatch spriteBatch) {
        float vidaAtual = (float) player.getVidaAtual();
        float vidaMax = (float) player.getVidaMax();

        float barWidth = 200; // Largura total da barra de vida
        float barHeight = 20; // Altura da barra de vida

        float healthPercentage = vidaAtual / vidaMax;
        float currentBarWidth = barWidth * healthPercentage;

        float x = Gdx.graphics.getWidth() - barWidth - 40;
        float y = 50;

        // Desenhar o fundo da barra de vida
        spriteBatch.begin();
        spriteBatch.draw(backgroundBarraVida, Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() / 6 - 130, 320, 140);

        // Desenhar o texto "Vida"
        String textoVida = "Vida";
        String vidaNaBarra = vidaAtual+"/"+vidaMax;
        glyphLayout.setText(font, textoVida);
        font.draw(spriteBatch, textoVida, x + (barWidth - glyphLayout.width) / 2, y + barHeight + glyphLayout.height + 10);
        glyphLayout.setText(font, vidaNaBarra);
        font.draw(spriteBatch, vidaNaBarra, x + (barWidth - glyphLayout.width) / 2, y - barHeight + glyphLayout.height);
        spriteBatch.end();

        // Desenha o fundo da barra de vida (branco)
        shapeRendererAtras.begin(ShapeRenderer.ShapeType.Filled);
        shapeRendererAtras.setColor(Color.WHITE);
        shapeRendererAtras.rect(x, y, barWidth, barHeight);
        shapeRendererAtras.end();
        
        // Desenhar a barra de vida atual (vermelha)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(x, y, currentBarWidth, barHeight);
        shapeRenderer.end();

        // Reiniciar o batch após desenhar a barra de vida
        
    }

    public void dispose() {
        backgroundBarraVida.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
