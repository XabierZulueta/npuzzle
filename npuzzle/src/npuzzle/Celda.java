package npuzzle;

import java.awt.Point;

//Cada celde dentro de nuestro nPuzzle de 3x3. (Esto es aplicable a NxN)
public class Celda {
	private int valor;
	private int x;
	private int y;

	public Celda() {
		super();
	}

	public Celda(int numero) {
		this.valor = numero;
	}

	public Celda(int valor, int x, int y) {
		super();
		this.valor = valor;
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return Integer.toString(valor);
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosicion(Point posicion) {
		this.x = posicion.x;
		this.y = posicion.y;
	}
}
