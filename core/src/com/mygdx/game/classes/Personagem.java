package com.mygdx.game.classes;

public class Personagem {
	/*
	| Poder | Resistência | Agilidade | Inteligência | Determinacao |
	-Poder: Aumenta o dano das armas e das magias
	-Resistência: Aumenta a vida
	-Agilidade: Diminui o tempo de recarga das habilidades e aumenta sua prioridade no turno
	-Inteligência: Aumenta a mana e a experiência ganha
	-Determinacao: Aumenta o multiplicador dos buffs
	(OBS: Recompensa é a mesma coisa que XP)
	*/
	private int nivel;
	private double recompensa;
	private double poder, resistencia, agilidade, inteligencia, determinacao;
	private double dano,vida,energia,percepcao,bonusBuff;
	private String spriteDir;
	
	public Personagem(int nivel, double recompensa, double poder, double resistencia, double agilidade,
			double inteligencia, double determinacao, String spriteDir) {
		this.nivel = nivel;
		this.recompensa = recompensa;
		this.poder = poder;
		this.resistencia = resistencia;
		this.agilidade = agilidade;
		this.inteligencia = inteligencia;
		this.determinacao = determinacao;
		this.spriteDir = spriteDir;
		
		dano = nivel*poder*2;
		vida = (nivel*resistencia*4)+100;
		energia = inteligencia*2;
		percepcao = 1+(inteligencia/15);
		bonusBuff = 1+(determinacao/20);
	}
	
	/*
	public void proximoNivel(int nivel, int recompensa) {
		if (recompensa >= Math.pow((nivel-1), 2)+4) {
			nivel += 1;
			recompensa = 0;
		}
	}
	*/


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
	
	public String getSpriteDir() {
		return spriteDir;
	}
	
	public void setSpriteDir(String spriteDir) {
		this.spriteDir = spriteDir;
	}

	public double getDano() {
		return dano;
	}

	public void setDano(double dano) {
		this.dano = dano;
	}

	public double getVida() {
		return vida;
	}

	public void setVida(double vida) {
		this.vida = vida;
	}

	public double getEnergia() {
		return energia;
	}

	public void setEnergia(double energia) {
		this.energia = energia;
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
