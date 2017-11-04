package npuzzle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Puzzle {
	private static enum Busquedas {
		AVARA, ASTAR, UNIFORME
	};

	private ArrayList<Estado> abiertos = new ArrayList<>();
	private ArrayList<Estado> cerrados;

	public Estado generarPuzzle() {
		Estado estado = new Estado();
		estado.generateNewPuzzle();
		mostrarPuzzle(estado);
		return estado;
	}

	public void busquedaCosteUniforme(Estado estadoInicial) {
		implementarBusqueda(estadoInicial, Busquedas.UNIFORME, false);
	}

	public void busquedaAvaraManhattan(Estado estadoInicial) {
		implementarBusqueda(estadoInicial, Busquedas.AVARA, true);
	}

	public void busquedaAvaraCasillasDescolocadas(Estado estadoInicial) {
		implementarBusqueda(estadoInicial, Busquedas.AVARA, false);
	}

	public void busquedaAStarManhattan(Estado estadoInicial) {
		implementarBusqueda(estadoInicial, Busquedas.ASTAR, true);
	}

	public void busquedaAStarCasillasDescolocadas(Estado estadoInicial) {
		implementarBusqueda(estadoInicial, Busquedas.ASTAR, false);
	}

	// Busqueda de coste uniforme (Busqueda no informada).
	private void implementarBusqueda(Estado estadoInicial, Busquedas busqueda, boolean manhattan) {
		System.out.println("EMPIEZA LA BUSQUEDA " + busqueda);
		abiertos.add(estadoInicial);
		cerrados = new ArrayList<>();
		do {
			Estado nodoActual = abiertos.remove(0);
			cerrados.add(nodoActual);
			if (nodoActual.isFinal()) {
				mostrarSolucion(nodoActual);
				return;
			} else {
				ArrayList<Estado> sucesores = operadores(nodoActual);

				switch (busqueda) {
				case AVARA:
					// ORDENAMOS SUCESORES EN FUNCION DE FN = H.

					// TODO: Gestionar abiertos y cerrados
					sucesores.sort((c1, c2) -> c1.getHeuristica(manhattan).intValue()
							- c2.getHeuristica(manhattan).intValue());
					break;
				case ASTAR:
					// ORDENAMOS SUCESORES EN FUNCION DE FN = H + G.
					// TODO: Gestionar abiertos y cerrados
					sucesores.sort((c1, c2) -> c1.getFN(manhattan).intValue() - c2.getFN(manhattan).intValue());
					break;
				case UNIFORME:
					// ORDENAMOS SUCESORES EN FUNCION DE FN = G.
					sucesores.removeIf(estado -> abiertos.contains(estado) || cerrados.contains(estado));

					sucesores.sort((c1, c2) -> c1.getCoste() - c2.getCoste());
					break;
				default:
					break;
				}

				abiertos.addAll(sucesores);
			}
		} while (!abiertos.isEmpty());
	}

	// Método para mostrar el camino de la solución.
	private void mostrarSolucion(Estado nodoActual) {
		if (nodoActual.getPadre() == null) {
			mostrarPuzzle(nodoActual);
			return;
		} else {
			mostrarSolucion(nodoActual.getPadre());
			mostrarPuzzle(nodoActual);
		}
	}

	// Método para mostrar el puzzle en un momento determinado.
	private void mostrarPuzzle(Estado e) {
		System.out.println("--------------------------");
		System.out.println(e);
		System.out.println("h Celdas cambiadas " + e.getHeuristica(false));
		System.out.println("h Manhattan " + e.getHeuristica(true));
		System.out.println("Coste: " + e.getCoste());
		System.out.println("--------------------------");
		System.out.println();
	}

	// Función para mostrar todos los sucesores dado un estado actual.
	private void mostrarSucesores(Estado actual, List<Estado> sucesores) {
		StringBuilder linea1 = new StringBuilder(actual.getNumeros().get(0).toString()).append("---->");
		StringBuilder linea2 = new StringBuilder(actual.getNumeros().get(1).toString()).append("---->");
		StringBuilder linea3 = new StringBuilder(actual.getNumeros().get(2).toString()).append("---->");

		for (Estado e : sucesores) {
			linea1.append(e.getNumeros().get(0).toString()).append("\t");
			linea2.append(e.getNumeros().get(1).toString()).append("\t");
			linea3.append(e.getNumeros().get(2).toString()).append("\t");
		}

		System.out.println(linea1);
		System.out.println(linea2);
		System.out.println(linea3);
		System.out.println();
	}

	// funcion que te devuelve todos los sucesores dados los operadores -> en
	// este caso (derecha, izquierda, arriba, abajo)
	private ArrayList<Estado> operadores(Estado estado) {
		ArrayList<Estado> posiblesSucesores = new ArrayList<>();
		// ORDEN DE OPERADORES ><^v
		Point posCelda0 = estado.getPosicion(0);

		// DERECHA
		if ((int) (posCelda0.getY()) != 2) {
			Estado hijo = hijo(estado, 0, 1);
			posiblesSucesores.add(hijo);
		}
		// IZQUIERDA
		if ((int) (posCelda0.getY()) != 0) {
			posiblesSucesores.add(hijo(estado, 0, -1));
		}
		// ARRIBA
		if ((int) (posCelda0.getX()) != 0) {
			posiblesSucesores.add(hijo(estado, -1, 0));
		}
		// ABAJO
		if ((int) (posCelda0.getX()) != 2) {
			posiblesSucesores.add(hijo(estado, 1, 0));
		}

		mostrarSucesores(estado, posiblesSucesores);

		return posiblesSucesores;
	}

	// Funcion que te devuelve el sucesor que corresponde al intercambiar el 0
	// con su casilla adyacente (pos0.x + x, pos0.y + y).
	private Estado hijo(Estado estadoActual, int x, int y) {
		Estado hijo = new Estado(estadoActual);
		// intercambio posiciones.
		hijo.intercambiarPosiciones(x, y);
		return hijo;
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
