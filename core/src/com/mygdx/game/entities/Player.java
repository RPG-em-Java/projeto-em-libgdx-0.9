package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.classes.Indio;
import com.mygdx.game.classes.Medico;
import com.mygdx.game.classes.Pistoleiro;
import com.mygdx.game.classes.Xerife;
import com.mygdx.game.excecoes.ClasseInvalidaExcecao;
import com.mygdx.game.ui.Item;

public class Player extends Sprite {
	
	private int classeTipo;
	private int nivel = 1, recompensa = 0;
	private double poder, resistencia, agilidade, inteligencia, determinacao;
	private double dano,vidaMax,vidaAtual,energiaMax,energiaAtual,percepcao,bonusBuff;
	private String spriteDir;
	private Object characterClass;
	private int recompensaProximoNivel;
	
	private Item[][] matrizItems;
	private Item armaEquipada, roupaEquipada;
	private static Item itemVazio = new Item(0);
	
    private Vector2 velocity = new Vector2();
    private float speed = 60 * 2;
    private TiledMapTileLayer collisionLayer;

    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private float stateTime;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction lastDirection = Direction.DOWN;

    public Player(Texture texture, TiledMapTileLayer collisionLayer, Object characterClass) {
        super(new TextureRegion(texture, 0, 0, 40, 40)); // Inicia com a primeira frame
        this.collisionLayer = collisionLayer;
        armaEquipada = new Item(0);
        roupaEquipada = new Item(0);
        
        //Atribuicao dos atributos do jogador para ser usado no jogo
        if (characterClass instanceof Pistoleiro) {
            Pistoleiro obterAtributos = new Pistoleiro();
        	classeTipo = 0;
            poder = obterAtributos.getPoder(); 
        	resistencia = obterAtributos.getResistencia(); 
        	agilidade = obterAtributos.getAgilidade();
        	inteligencia = obterAtributos.getInteligencia(); 
        	determinacao = obterAtributos.getDeterminacao();
        	dano = obterAtributos.getDano(); 
        	vidaMax = obterAtributos.getVida(); 
        	energiaMax = obterAtributos.getEnergia();
        	percepcao = obterAtributos.getPercepcao(); 
        	bonusBuff = obterAtributos.getBonusBuff();
        	spriteDir = obterAtributos.getSpriteDir();
        } else if (characterClass instanceof Xerife) {
        	Xerife obterAtributos = new Xerife();
        	classeTipo = 1;
        	poder = obterAtributos.getPoder(); 
        	resistencia = obterAtributos.getResistencia(); 
        	agilidade = obterAtributos.getAgilidade();
        	inteligencia = obterAtributos.getInteligencia(); 
        	determinacao = obterAtributos.getDeterminacao();
        	dano = obterAtributos.getDano(); 
        	vidaMax = obterAtributos.getVida(); 
        	energiaMax = obterAtributos.getEnergia();
        	percepcao = obterAtributos.getPercepcao(); 
        	bonusBuff = obterAtributos.getBonusBuff();
        	spriteDir = obterAtributos.getSpriteDir();
        } else if (characterClass instanceof Medico) {
        	Medico obterAtributos = new Medico();
        	classeTipo = 2;
        	poder = obterAtributos.getPoder(); 
        	resistencia = obterAtributos.getResistencia(); 
        	agilidade = obterAtributos.getAgilidade();
        	inteligencia = obterAtributos.getInteligencia(); 
        	determinacao = obterAtributos.getDeterminacao();
        	dano = obterAtributos.getDano(); 
        	vidaMax = obterAtributos.getVida(); 
        	energiaMax = obterAtributos.getEnergia();
        	percepcao = obterAtributos.getPercepcao(); 
        	bonusBuff = obterAtributos.getBonusBuff();
        	spriteDir = obterAtributos.getSpriteDir();
        } else if (characterClass instanceof Indio) {
        	Indio obterAtributos = new Indio();
        	classeTipo = 3;
        	poder = obterAtributos.getPoder(); 
        	resistencia = obterAtributos.getResistencia(); 
        	agilidade = obterAtributos.getAgilidade();
        	inteligencia = obterAtributos.getInteligencia(); 
        	determinacao = obterAtributos.getDeterminacao();
        	dano = obterAtributos.getDano(); 
        	vidaMax = obterAtributos.getVida(); 
        	energiaMax = obterAtributos.getEnergia();
        	percepcao = obterAtributos.getPercepcao(); 
        	bonusBuff = obterAtributos.getBonusBuff();
        	spriteDir = obterAtributos.getSpriteDir();
        }
        vidaAtual = vidaMax;
        energiaAtual = energiaMax;
        
        //Gdx.app.log("MyTag", Double.toString(dano));

        TextureRegion[][] tmp = TextureRegion.split(texture, 40, 40);
        Array<TextureRegion> frames;

        // Carrega animações de andar
        frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(tmp[8][i]);
        }
        walkUp = new Animation<>(0.1f, frames);

        frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(tmp[9][i]);
        }
        walkLeft = new Animation<>(0.1f, frames);

        frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(tmp[10][i]);
        }
        walkDown = new Animation<>(0.1f, frames);

        frames = new Array<>();
        for (int i = 0; i < 9; i++) {
            frames.add(tmp[11][i]);
        }
        walkRight = new Animation<>(0.1f, frames);
        
        //Criar inventario 6x5
        matrizItems = new Item[6][5];
        
        //Exemplo de itens
        matrizItems[0][0] = new Item(2);
        matrizItems[1][0] = new Item(101);
        matrizItems[0][1] = new Item(203);
        matrizItems[1][2] = new Item(1);
        matrizItems[2][3] = new Item(3);
        matrizItems[3][4] = new Item(4);
        matrizItems[4][4] = new Item(5);
        
    }

    public void drawPlayer(Batch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void update(float delta) {
        boolean moveHorizontal = false;
        boolean moveVertical = false;

        // Resetando a velocidade
        velocity.set(0, 0);

        // Checando entrada do usuário para movimentação horizontal
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            velocity.x = -speed;
            moveHorizontal = true;
            lastDirection = Direction.LEFT;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            velocity.x = speed;
            moveHorizontal = true;
            lastDirection = Direction.RIGHT;
        }

        // Checando entrada do usuário para movimentação vertical, só se não estiver movendo horizontalmente
        if (!moveHorizontal) {
            if (Gdx.input.isKeyPressed(Keys.UP)) {
                velocity.y = speed;
                moveVertical = true;
                lastDirection = Direction.UP;
            } else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
                velocity.y = -speed;
                moveVertical = true;
                lastDirection = Direction.DOWN;
            }
        }

        // Calculando nova posição
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // Movendo-se no eixo X e verificando colisões
        if (moveHorizontal) {
            setX(getX() + velocity.x * delta);
            if (velocity.x < 0) {
                collisionX = collidesLeft();
            } else if (velocity.x > 0) {
                collisionX = collidesRight();
            }

            if (collisionX) {
                setX(oldX);
                velocity.x = 0;
            }
        }

        // Movendo-se no eixo Y e verificando colisões
        if (moveVertical) {
            setY(getY() + velocity.y * delta);
            if (velocity.y < 0) {
                collisionY = collidesBottom();
            } else if (velocity.y > 0) {
                collisionY = collidesTop();
            }

            if (collisionY) {
                setY(oldY);
                velocity.y = 0;
            }
        }

        // Atualizando o estado de animação
        stateTime += delta;

        // Definindo a animação atual
        TextureRegion currentFrame = getCurrentFrame();
        setRegion(currentFrame);
        
        //Checkando o nível e xp
        recompensaProximoNivel = (int) (Math.pow((nivel-1), 2)+4);
        if (recompensa >= recompensaProximoNivel) {
        	nivel += 1;
			recompensa = 0;
        }
        
        //Atribucao dos atributos dos equipamentos
	    
    }

    private TextureRegion getCurrentFrame() {
        if (velocity.x > 0) {
            return walkRight.getKeyFrame(stateTime, true);
        } else if (velocity.x < 0) {
            return walkLeft.getKeyFrame(stateTime, true);
        } else if (velocity.y > 0) {
            return walkUp.getKeyFrame(stateTime, true);
        } else if (velocity.y < 0) {
            return walkDown.getKeyFrame(stateTime, true);
        } else {
            // Retorna o primeiro frame da última direção
            switch (lastDirection) {
                case UP:
                    return walkUp.getKeyFrame(0, true);
                case DOWN:
                    return walkDown.getKeyFrame(0, true);
                case LEFT:
                    return walkLeft.getKeyFrame(0, true);
                case RIGHT:
                    return walkRight.getKeyFrame(0, true);
                default:
                    return walkDown.getKeyFrame(0, true);
            }
        }
    }

    private boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
        		(int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("blocked");
    }

    private boolean collidesLeft() {
        for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
            if (isCellBlocked(getX(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesRight() {
        for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
            if (isCellBlocked(getX() + getWidth(), getY() + step)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesTop() {
        for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
            if (isCellBlocked(getX() + step, getY() + getHeight())) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesBottom() {
        for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
            if (isCellBlocked(getX() + step, getY())) {
                return true;
            }
        }
        return false;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public void trocarArma(Item itemInv, Item itemEquip) {
    	if (itemEquip != itemVazio) {
	    	Item itemTransitorio;
	    	itemTransitorio = itemEquip;
	    	itemEquip = itemInv;
	    	itemInv = itemTransitorio;
	    	armaEquipada = itemEquip;
    	} else {
    		itemEquip = itemInv;
    		itemInv = null;
    	}
    	poder = poder + armaEquipada.getAtPoder();
	    resistencia = resistencia + armaEquipada.getAtResistencia();
        agilidade = agilidade + armaEquipada.getAtAgilidade();
        inteligencia = inteligencia + armaEquipada.getAtInteligencia();
        determinacao = determinacao + armaEquipada.getAtDeterminacao();
    }
    
    public Item getArmaEquipada() {
    	return armaEquipada;
    }
    
    public void trocarRoupa(Item itemInv, Item itemEquip) {
    	if (itemEquip != new Item(0)) {
	    	Item itemTransitorio;
	    	itemTransitorio = itemEquip;
	    	itemEquip = itemInv;
	    	itemInv = itemTransitorio;
	    	roupaEquipada = itemEquip;
    	} else {
    		itemEquip = itemInv;
    		itemInv = null;
    	}
    	poder = poder + roupaEquipada.getAtPoder();
	    resistencia = resistencia + roupaEquipada.getAtResistencia();
        agilidade = agilidade + roupaEquipada.getAtAgilidade();
        inteligencia = inteligencia + roupaEquipada.getAtInteligencia();;
        determinacao = determinacao + roupaEquipada.getAtDeterminacao();
    }
    
    public Item getRoupaEquipada() {
    	return roupaEquipada;
    }
    
    // Getters e Setters
    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

	public double getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public double getRecompensa() {
		return recompensa;
	}

	public void setRecompensa(int recompensa) {
		this.recompensa = recompensa;
	}

	public double getPoder() {
		return poder;
	}

	public void setPoder(double poder) {
		this.poder = poder;
	}

	public double getResistencia() {
		return resistencia;
	}

	public void setResistencia(double resistencia) {
		this.resistencia = resistencia;
	}

	public double getAgilidade() {
		return agilidade;
	}

	public void setAgilidade(double agilidade) {
		this.agilidade = agilidade;
	}

	public double getInteligencia() {
		return inteligencia;
	}

	public void setInteligencia(double inteligencia) {
		this.inteligencia = inteligencia;
	}

	public double getDeterminacao() {
		return determinacao;
	}

	public void setDeterminacao(double determinacao) {
		this.determinacao = determinacao;
	}

	public double getDano() {
		return dano;
	}

	public void setDano(double dano) {
		this.dano = dano;
	}

	public double getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(double vidaMax) {
		this.vidaMax = vidaMax;
	}
	
	public double getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(double vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public double getEnergiaMax() {
		return energiaMax;
	}

	public void setEnergiaMax(double energiaMax) {
		this.energiaMax = energiaMax;
	}
	
	public double getEnergiaAtual() {
		return energiaAtual;
	}

	public void setEnergiaAtual(double energiaAtual) {
		this.energiaAtual = energiaAtual;
	}

	public double getPercepcao() {
		return percepcao;
	}

	public void setPercepcao(double percepcao) {
		this.percepcao = percepcao;
	}

	public double getBonusBuff() {
		return bonusBuff;
	}

	public void setBonusBuff(double bonusBuff) {
		this.bonusBuff = bonusBuff;
	}
    
    public int getClasseTipo() {
    	return classeTipo;
    }
    
    public int getRecompensaProximoNivel() {
    	return recompensaProximoNivel;
    }
    
    public Item[][] getMatrizItems() {
    	return matrizItems;
    }
    
    public void setMatrizItems(Item[][] item) {
    	matrizItems = item;
    }
    
    public String getSpriteDir() {
		return spriteDir;
	}

	public void setSpriteDir(String spriteDir) {
		this.spriteDir = spriteDir;
	}

	public void adicionarItemInventario(int id) {
    	for (int i = 0; i < matrizItems.length; i++) {
            for (int j = 0; j < matrizItems[i].length; j++) {
                Item item = matrizItems[i][j];
                if (item != null) {
                	matrizItems[i][j] = new Item(id);
                }
            }
    	}
    }
    
}
