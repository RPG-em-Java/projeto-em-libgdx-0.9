package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.classes.Buck;
import com.mygdx.game.classes.Django;
//import com.mygdx.game.entities.Player.Direction;

public class NPC extends Sprite {
    
	private Vector2 velocity = new Vector2();
    private float speed = 15 * 2;
    private TiledMapTileLayer collisionLayer;

    private Animation<TextureRegion> walkUp, walkDown, walkLeft, walkRight;
    private float stateTime;
    private float moveTimer;
    private int direction = 0;
    
    //ID 1 = Buck, ID 2 = Django
    private int idNPC, nivel;
    private double poder, resistencia, agilidade, inteligencia, determinacao;
	private double dano,vidaMax,vidaAtual,energiaMax,energiaAtual,percepcao,bonusBuff;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction lastDirection = Direction.DOWN;

    public NPC(Texture texture, TiledMapTileLayer collisionLayer, int idNPC) {
    	
    	super(new TextureRegion(texture, 0, 0, 40, 40)); // Inicia com a primeira frame
        this.collisionLayer = collisionLayer;

        switch (idNPC) {
    	case 1:
    		Buck obterAtributos = new Buck();
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
    		break;
    	case 2:
    		Django obterAtributos1 = new Django();
    		poder = obterAtributos1.getPoder(); 
        	resistencia = obterAtributos1.getResistencia(); 
        	agilidade = obterAtributos1.getAgilidade();
        	inteligencia = obterAtributos1.getInteligencia(); 
        	determinacao = obterAtributos1.getDeterminacao();
        	dano = obterAtributos1.getDano(); 
        	vidaMax = obterAtributos1.getVida(); 
        	energiaMax = obterAtributos1.getEnergia();
        	percepcao = obterAtributos1.getPercepcao(); 
        	bonusBuff = obterAtributos1.getBonusBuff();
    		break;
    }
        
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
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        moveTimer -= deltaTime;
        boolean moveHorizontal = false;
        boolean moveVertical = false;

        // Resetando a velocidade
        velocity.set(0, 0);

        if (moveTimer <= 0) {
            direction = MathUtils.random(0, 3); // Muda a direção aleatoriamente
            moveTimer = MathUtils.random(1f + 3, 2.5f + 3); // Tempo até mudar de direção novamente
        } else if (moveTimer <= 3 && moveTimer > 0) {
        	direction = 4;
        }

        
        switch (direction) {
            case 0: 
            	velocity.y = speed;
            	moveVertical = true;
                lastDirection = Direction.UP;
            	break; // Cima
            case 1: 
            	velocity.x = speed;
            	moveHorizontal = true;
                lastDirection = Direction.RIGHT;
                break; // Direita
            case 2: 
            	velocity.y = -speed;
            	moveVertical = true;
                lastDirection = Direction.DOWN;
            	break; // Baixo
            case 3: 
            	velocity.x = -speed;
            	moveHorizontal = true;
                lastDirection = Direction.LEFT;
            	break; // Esquerda
            case 4:
            	velocity.x = 0;
            	velocity.y = 0;
            	break;
        }
        
     // Calculando nova posição
        float oldX = getX(), oldY = getY();
        boolean collisionX = false, collisionY = false;

        // Movendo-se no eixo X e verificando colisões
        if (moveHorizontal) {
            setX(getX() + velocity.x * deltaTime);
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
            setY(getY() + velocity.y * deltaTime);
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
        stateTime += deltaTime;

        // Definindo a animação atual
        TextureRegion currentFrame = getCurrentFrame();
        setRegion(currentFrame);

        /* Limita o NPC ao quadrado de 120x120
        if (oldX < getX()) setX(oldX);
        if (oldX < getX() + 120) setX(oldX + 120);
        if (oldY < getY()) setY(oldY);
        if (oldY < getY() + 120) setY(oldY + 120);
        */

    }

    public void drawNPC(Batch batch) {
    	update(Gdx.graphics.getDeltaTime());
    	super.draw(batch);
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

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
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
    
    
}
