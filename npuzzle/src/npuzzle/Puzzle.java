package npuzzle;

import java.awt.Point;
import java.util.ArrayList;

public class Puzzle {

	private Estado puzzle;
	private ArrayList<Estado> abiertos = new ArrayList<>();
	private ArrayList<Estado> cerrados;

	public void generarPuzzle() {
		puzzle = new Estado();
		puzzle.generateNewPuzzle();
		mostrarPuzzle(puzzle);
		System.out.println("EMPIEZA LA BUSQUEDA COSTE UNIFORME");
		busquedaCosteUniforme();
	}

	private void busquedaCosteUniforme() {
		abiertos.add(puzzle);
		cerrados = new ArrayList<>();
		do {
			Estado nodoActual = abiertos.remove(0);
			cerrados.add(nodoActual);
			if (nodoActual.isFinal()) {
				mostrarSolucion(nodoActual);
				return;
			} else {
				ArrayList<Estado> sucesores = operadores(nodoActual);
				sucesores.sort((c1, c2) -> c1.getCoste() - c2.getCoste());
				abiertos.addAll(sucesores);
			}
		} while (!abiertos.isEmpty());
	}

	private void mostrarSolucion(Estado nodoActual) {
		if (nodoActual.getPadre() == null) {
			mostrarPuzzle(nodoActual);
			return;
		} else {
			mostrarPuzzle(nodoActual);
			mostrarSolucion(nodoActual.getPadre());
		}

	}

	private void mostrarPuzzle(Estado e) {
		System.out.println("--------------------------");
		System.out.println(e);
		System.out.println("h Celdas cambiadas " + e.getHCasillasCambiadas());
		System.out.println("h Manhattan " + e.getHManhattan());
		System.out.println("Coste: " + e.getCoste());
		System.out.println("--------------------------");
		System.out.println();
	}

	private ArrayList<Estado> operadores(Estado estado) {
		ArrayList<Estado> posibilidades = new ArrayList<>();
		// ORDEN DE OPERADORES ><^v
		// DERECHAº
		Point posCelda0 = estado.getPosicion(0);

		if ((int) (posCelda0.getY()) != 2) {
			posibilidades.add(hijo(estado, 0, 1));
		}
		// IZQUIERDA
		if ((int) (posCelda0.getY()) != 0) {
			posibilidades.add(hijo(estado, 0, -1));
		}
		// ARRIBA
		if ((int) (posCelda0.getX()) != 0) {
			posibilidades.add(hijo(estado, -1, 0));
		}
		// ABAJO
		if ((int) (posCelda0.getX()) != 2) {
			posibilidades.add(hijo(estado, 1, 0));
		}

		return posibilidades;
	}

	private Estado hijo(Estado estadoActual, int x, int y) {
		Estado sucesor = new Estado(estadoActual);
		// intercambio posiciones.
		sucesor.intercambiarPosiciones(x, y);

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
