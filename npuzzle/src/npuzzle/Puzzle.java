package npuzzle;

import java.util.ArrayList;

public class Puzzle {

	private Estado puzzle;
	private Estado estadoFinal;
	private ArrayList<Estado> abiertos;
	private ArrayList<Estado> cerrados;
	private int h1;
	private int h2;

	public void generarPuzzle() {
		h1 = 0;
		h2 = 0;
		puzzle = new Estado();
		puzzle.generateNewPuzzle();

	}

	public void mostrarPuzzle() {
		puzzle.calcularHeuristicaManhattan();
		puzzle.calcularCeldasDescolocadas();
		System.out.println(puzzle.getH2());
		System.out.println(puzzle.getH1());
	}

	public boolean esFinal() {
		return puzzle == estadoFinal;
	}

	private ArrayList<Estado> operadores(Celda celda) {
		ArrayList<Estado> posibilidades = new ArrayList<>();
		// ORDEN DE OPERADORES ><^v
		// DERECHAº
		if (celda.getY() != 2) {
			// posibilidades.add(hijo(celda, 0, 1));
		}
		// IZQUIERDA
		if (celda.getY() != 0) {

		}
		// ARRIBA
		if (celda.getX() != 0) {

		}
		// ABAJO
		if (celda.getX() != 2) {

		}
		return posibilidades;

	}

	private Estado hijo(Estado estadoActual, int x, int y) {
		Estado sucesor = new Estado(estadoActual);
		// intercambio posiciones.
		return sucesor;
	}

	public ArrayList<Estado> getAbiertos() {
		return abiertos;
	}

	public void setAbiertos(ArrayList<Estado> abiertos) {
		this.abiertos = abiertos;
	}

	public ArrayList<Estado> getCerrados() {
		return cerrados;
	}

	public void setCerrados(ArrayList<Estado> cerrados) {
		this.cerrados = cerrados;
	}
}
