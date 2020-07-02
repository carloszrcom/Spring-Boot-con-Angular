package com.ejemplo.consumirrest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

	private String tipo;
	private Value valor;
	
	public Quote() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Value getValor() {
		return valor;
	}

	public void setValor(Value valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
	    return "Quote{" +
	            "type='" + tipo + '\'' +
	            ", value=" + valor +
	            '}';
	}
}
