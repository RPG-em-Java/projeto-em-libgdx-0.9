package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.Player;
import com.mygdx.game.ui.BarraVidaPlay;
import com.mygdx.game.ui.Inventario;
import com.mygdx.game.classes.Indio;
import com.mygdx.game.classes.Medico;
import com.mygdx.game.classes.Pistoleiro;
import com.mygdx.game.classes.Xerife;
import com.mygdx.game.entities.NPC;

public class Play implements Screen {

    private Game game;
	private TiledMap mapa1;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch rendererHud;
    private Stage stage;
    private OrthographicCamera camera;
    private Player jogador;
    private NPC buck;

    private Object characterClass;
    private boolean primeiraVez;
    private int classeTipoP;
    
    private BarraVidaPlay barraVida;
    private Inventario inventarioHud;

    public Play(int classeTipoP, Game game, Player jogador, boolean primeiraVez) {
        this.classeTipoP = classeTipoP;
        this.game = game;
        this.primeiraVez = primeiraVez;
        this.jogador = jogador;
    }

    @Override
    public void show() {
        mapa1 = new TmxMapLoader().load("maps/mapafinal3.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa1);
        rendererHud = new SpriteBatch();
        camera = new OrthographicCamera();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture playerTexture;
        if (primeiraVez == true) {
	        if (classeTipoP == 0) {
	            playerTexture = new Texture("sprites/cowboy_sprites.png");
	            jogador = new Player(playerTexture, (TiledMapTileLayer) mapa1.getLayers().get(0), new Pistoleiro());
	        } else if (classeTipoP == 1) {
	            playerTexture = new Texture("sprites/xerife_sprites.png");
	            jogador = new Player(playerTexture, (TiledMapTileLayer) mapa1.getLayers().get(0), new Xerife());
	        } else if (classeTipoP == 2) {
	            playerTexture = new Texture("sprites/medico_sprites.png");
	            jogador = new Player(playerTexture, (TiledMapTileLayer) mapa1.getLayers().get(0), new Medico());
	        } else if (classeTipoP == 3) {
	            playerTexture = new Texture("sprites/indio_sprites.png");
	            jogador = new Player(playerTexture, (TiledMapTileLayer) mapa1.getLayers().get(0), new Indio());
	        }
        } else {
        	playerTexture = new Texture("sprites/medico_sprites.png");
        }

        jogador.setPosition(55 * jogador.getCollisionLayer().getTileWidth(), 
                (jogador.getCollisionLayer().getHeight() - 95) * jogador.getCollisionLayer().getTileHeight());

        Texture npcTexture = new Texture("sprites/buck_sprites.png");
        buck = new NPC(npcTexture, (TiledMapTileLayer) mapa1.getLayers().get(0), 1);
        buck.setPosition(55 * buck.getCollisionLayer().getTileWidth(), 
                (buck.getCollisionLayer().getHeight() - 90) * buck.getCollisionLayer().getTileHeight());
        
        barraVida = new BarraVidaPlay(jogador);
        inventarioHud = new Inventario(jogador, stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(jogador.getX(), jogador.getY(), 0);
        camera.zoom = 0.60f;
        camera.update();

        renderer.setView(camera);
        renderer.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            inventarioHud.toggle();
        }

        delta = Gdx.graphics.getDeltaTime();
        inventarioHud.update(delta);

        barraVida.drawBarraVidaP(rendererHud);
        
        renderer.getBatch().begin();
        
        if (jogador.getY() >= buck.getY()) {
            jogador.drawPlayer(renderer.getBatch());
            buck.drawNPC(renderer.getBatch());
        } else {
            buck.drawNPC(renderer.getBatch());
            jogador.drawPlayer(renderer.getBatch());
        }
        
        renderer.getBatch().end();
        
     // Dentro do método render() da classe Play, logo após renderer.getBatch().end();

        if (jogador.getBounds().overlaps(buck.getBounds())) {
            game.setScreen(new CombatScreen(jogador, buck, game));
        }

        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
        inventarioHud.update(Gdx.graphics.getDeltaTime());
        inventarioHud.drawInventario(rendererHud);
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
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        mapa1.dispose();
        stage.dispose();
        renderer.dispose();
        jogador.getTexture().dispose();
        buck.getTexture().dispose();
        inventarioHud.dispose();
    }
}
