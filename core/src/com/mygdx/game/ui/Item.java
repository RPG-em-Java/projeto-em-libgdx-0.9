package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.Texture;

public class Item {
	
	private int id, tipo;
	private int nivelMinimo;
	private String titulo, descricao;
	private Texture texturaItem = new Texture("hud/items/itemVazio.png");
	private double atPoder = 0, atResistencia = 0, atAgilidade = 0, atInteligencia = 0, atDeterminacao = 0, porcentagem = 0;
	
	public Item(int id) {
		this.id = id;
		
		switch(id) {
		case 1:
			tipo = 0;
			titulo = "Tabletes de Morfina";
			descricao = "Recupera parte da vida";
			porcentagem = 0.4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/consumiveis/id1.png");
			break;
		case 2:
			tipo = 0;
			titulo = "Seringa com Morfina";
			descricao = "Recupera toda a vida";
			porcentagem = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/consumiveis/id2.png");
			break;
		case 3:
			tipo = 0;
			titulo = "Whiskey Barato";
			descricao = "Recupera parte da energia";
			porcentagem = 0.4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/consumiveis/id3.png");
			break;
		case 4:
			tipo = 0;
			titulo = "Whiskey de Alta Qualidade";
			descricao = "Recupera toda a energia";
			porcentagem = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/consumiveis/id4.png");
			break;
		case 5:
			tipo = 0;
			titulo = "Extrato de Erva-de-São-João";
			descricao = "Aumenta o bônus dado pelos efeitos do jogador";
			porcentagem = 0.3;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/consumiveis/id5.png");
			break;
		case 101:
			tipo = 1;
			titulo = "Revólver Enferrujado";
			descricao = "Não está nas melhores condições, mas ainda dá pro gasto";
			atPoder = 2;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id101.png");
			break;
		case 102:
			tipo = 1;
			titulo = "Escopeta Enferrujada";
			descricao = "Não está nas melhores condições, mas ainda dá pro gasto";
			atPoder = 2;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id102.png");
			break;
		case 103:
			tipo = 1;
			titulo = "Instrumentais enferrujados";
			descricao = "São letais em uma cirurgia, mas em combate só dão pro gasto mesmo";
			atPoder = 1;
			atResistencia = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id103.png");
			break;
		case 104:
			tipo = 1;
			titulo = "Tomahawk Velho";
			descricao = "Foi usado por algum índio décadas atrás, e hoje se encontra precária";
			atPoder = 1;
			atAgilidade = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id104.png");
			break;
		case 105:
			tipo = 1;
			titulo = "Revólver de Ferro";
			descricao = "Uma arma sólida para um pistoleiro de qualquer habilidade";
			atPoder = 4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id105.png");
			break;
		case 106:
			tipo = 1;
			titulo = "Escopeta de Ferro";
			descricao = "Uma arma sólida para um xerife de qualquer habilidade";
			atPoder = 4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id106.png");
			break;
		case 107:
			tipo = 1;
			titulo = "Instrumentais de Ferro";
			descricao = "Ferramentas que são sólidas para um médico de qualquer habilidade";
			atPoder = 2;
			atResistencia = 2;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id107.png");
			break;
		case 108:
			tipo = 1;
			titulo = "Tomahawk Comum";
			descricao = "Apesar de estar quebrada, ainda é uma arma letal nas mãos certas";
			atPoder = 2;
			atAgilidade = 1;
			atDeterminacao = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/armas/id108.png");
			break;
		case 201:
			tipo = 2;
			titulo = "Roupa de Cowboy";
			descricao = "Conjunto padrão de qualquer fora da lei";
			atPoder = 5;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/roupas/id201.png");
			break;
		case 202:
			tipo = 2;
			titulo = "Roupa de Forasteiro";
			descricao = "Roupas usadas pelo pessoal da outra costa provavelmente";
			atPoder = 2;
			atAgilidade = 4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/roupas/id202.png");
			break;
		case 203:
			tipo = 2;
			titulo = "Roupa de Rancheiro";
			descricao = "São ótimas pra capinar um mato, só que não muito pra um tiroteio";
			atInteligencia = 1;
			atAgilidade = 1;
			atDeterminacao = 4;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/roupas/id203.png");
			break;
		case 204:
			tipo = 2;
			titulo = "Roupa de Mineiro";
			descricao = "É toda suja de poeira e não deve ter sido lavada em 1 mês, mas protege bem";
			atResistencia = 5;
			atDeterminacao = 1;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/roupas/id204.png");
			break;
		case 205:
			tipo = 2;
			titulo = "Roupa de Explorador";
			descricao = "Roupas de quem explorou outras terras, talvez até outros mundos";
			atPoder = 1;
			atInteligencia = 5;
			nivelMinimo = 0;
			texturaItem = new Texture("hud/items/roupas/id205.png");
			break;
		default:
			titulo = "item nulo";
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getNivelMinimo() {
		return nivelMinimo;
	}

	public void setNivelMinimo(int nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Texture getTexturaItem() {
		return texturaItem;
	}

	/*
	public void setTexturaItem(String texturaItem) {
		this.texturaItem = new Texture(texturaItem);
	}
	*/

	public double getAtPoder() {
		return atPoder;
	}

	public void setAtPoder(double atPoder) {
		this.atPoder = atPoder;
	}

	public double getAtResistencia() {
		return atResistencia;
	}

	public void setAtResistencia(double atResistencia) {
		this.atResistencia = atResistencia;
	}

	public double getAtAgilidade() {
		return atAgilidade;
	}

	public void setAtAgilidade(double atAgilidade) {
		this.atAgilidade = atAgilidade;
	}

	public double getAtInteligencia() {
		return atInteligencia;
	}

	public void setAtInteligencia(double atInteligencia) {
		this.atInteligencia = atInteligencia;
	}

	public double getAtDeterminacao() {
		return atDeterminacao;
	}

	public void setAtDeterminacao(double atDeterminacao) {
		this.atDeterminacao = atDeterminacao;
	}

	public double getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}
	
	//Dictionary<Integer, Integer> listaItens= new Hashtable<>();
	
	
	
}
