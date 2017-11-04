package npuzzle;

import java.awt.Point;
import java.util.ArrayList;
//Cada nPuzzle que salga a partir del primero, nuestros estados.

public class Estado {
	// Coste de cada operador.
	private static int COSTE_OPERADOR = 1;
	// Estado final que será el que queramos alcanzar. (SOLO PARA 3x3). (Habria
	// que modificarlo para NxN).
	protected static Estado estadoFinal = new Estado();
	static {
		estadoFinal.numeros.get(0).add(new Celda(1, 0, 0));
		estadoFinal.numeros.get(0).add(new Celda(2, 0, 1));
		estadoFinal.numeros.get(0).add(new Celda(3, 0, 2));
		estadoFinal.numeros.get(1).add(new Celda(8, 1, 0));
		estadoFinal.numeros.get(1).add(new Celda(0, 1, 1));
		estadoFinal.numeros.get(1).add(new Celda(4, 1, 2));
		estadoFinal.numeros.get(2).add(new Celda(7, 2, 0));
		estadoFinal.numeros.get(2).add(new Celda(6, 2, 1));
		estadoFinal.numeros.get(2).add(new Celda(5, 2, 2));
	}
	// Celdas dentro de nuestro nPuzzle.
	private ArrayList<ArrayList<Celda>> numeros;
	// Coste acumulado de un estado determinado.
	private int coste;
	// Para el backtracking.
	private Estado padre;

	protected Estado() {
		// Inizialización de variables.
		numeros = new ArrayList<>(3);
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
	}

	// Copy de un estado dado
	protected Estado(Estado anterior) {
		this.numeros = copyArray(anterior.numeros);
		this.coste = anterior.coste;
		this.addCoste();
		this.padre = anterior;
	}

	// En java hay que reinicializar todas las variables por problemas de
	// pointers. Cada nueevo estado tiene que tener sus propios atributos.
	private ArrayList<ArrayList<Celda>> copyArray(ArrayList<ArrayList<Celda>> numeros2) {
		ArrayList<ArrayList<Celda>> numeros = new ArrayList<>(3);
		numeros.add(new ArrayList<>(3));
		Celda aux = numeros2.get(0).get(0);
		numeros.get(0).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(0).get(1);
		numeros.get(0).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(0).get(2);
		numeros.get(0).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		numeros.add(new ArrayList<>(3));
		aux = numeros2.get(1).get(0);
		numeros.get(1).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(1).get(1);
		numeros.get(1).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(1).get(2);
		numeros.get(1).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		numeros.add(new ArrayList<>(3));
		aux = numeros2.get(2).get(0);
		numeros.get(2).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(2).get(1);
		numeros.get(2).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		aux = numeros2.get(2).get(2);
		numeros.get(2).add(new Celda(aux.getValor(), aux.getX(), aux.getY()));

		return numeros;
	}

	private Double getHCasillasCambiadas() {
		double h1 = 0d;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (numeros.get(i).get(j).getValor() != estadoFinal.numeros.get(i).get(j).getValor()) {
					h1++;
				}
			}
		}
		return h1;
	}

	private Double getHManhattan() {
		double h2 = 0d;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Celda miCelda = numeros.get(i).get(j);
				Celda celdaEstadoFinal = this.getCeldaByValorEstadoFinal(miCelda.getValor());
				h2 += Math.abs(celdaEstadoFinal.getX() - miCelda.getX())
						+ Math.abs(celdaEstadoFinal.getY() - miCelda.getY());
			}
		}
		return h2;
	}

	protected Double getHeuristica(boolean manhattan) {
		return manhattan ? getHManhattan() : getHCasillasCambiadas();
	}

	private Celda getCeldaByValorEstadoFinal(int valor) {
		for (ArrayList<Celda> list : estadoFinal.numeros) {
			for (Celda c : list) {
				if (c.getValor() == valor) {
					return new Celda(c.getValor(), c.getX(), c.getY());
				}
			}
		}
		throw new NullPointerException("value missing - " + valor);
	}

	private Celda getCeldaByValor(int valor) {
		for (ArrayList<Celda> list : this.numeros) {
			for (Celda c : list) {
				if (c.getValor() == valor) {
					return new Celda(c.getValor(), c.getX(), c.getY());
				}
			}
		}
		throw new NullPointerException("value missing - " + valor);
	}

	// Obtener la posicion de un valor dado.
	protected Point getPosicion(int valor) {
		for (int i = 0; i < this.numeros.size(); i++) {
			for (int j = 0; j < this.numeros.get(i).size(); j++) {
				if (this.numeros.get(i).get(j).getValor() == valor) {
					return new Point(i, j);
				}
			}
		}
		throw new NullPointerException("value missing - 0");
	}

	// Intercambio de posiciones, con control de fallo, No es posible
	// movimientos en diagonal.
	protected void intercambiarPosiciones(int x, int y) {
		if ((x == 1 && y == 1) || (x == -1 && y == -1) || (x == 1 && y == -1) || (x == -1 && y == 1)) {
			throw new NullPointerException("No es posible movimientos en diagonal");
		}
		Celda celda0 = getCeldaByValor(0);
		Point posCelda0 = getPosicion(0);
		Celda next = this.numeros.get((int) (posCelda0.getX()) + x).get((int) (posCelda0.getY()) + y);
		Point posNextCelda = getPosicion(next.getValor());
		Celda aux = celda0;
		next.setPosicion(posCelda0);
		aux.setPosicion(posNextCelda);
		this.numeros.get((int) posCelda0.getX()).set((int) posCelda0.getY(), next);
		this.numeros.get((int) posNextCelda.getX()).set((int) posNextCelda.getY(), aux);
	}

	protected boolean isFinal() {
		return this.equals(estadoFinal);
	}

	protected void generateNewPuzzle() {
		// TODO: random with scanner.
		this.numeros.get(0).add(new Celda(1, 0, 0));
		this.numeros.get(0).add(new Celda(3, 0, 1));
		this.numeros.get(0).add(new Celda(0, 0, 2));
		this.numeros.get(1).add(new Celda(6, 1, 0));
		this.numeros.get(1).add(new Celda(2, 1, 1));
		this.numeros.get(1).add(new Celda(4, 1, 2));
		this.numeros.get(2).add(new Celda(8, 2, 0));
		this.numeros.get(2).add(new Celda(7, 2, 1));
		this.numeros.get(2).add(new Celda(5, 2, 2));
	}

	protected void addCoste() {
		coste += COSTE_OPERADOR;
	}

	// GETTERS && SETTERS

	protected Estado getPadre() {
		return padre;
	}

	protected void setPadre(Estado padre) {
		this.padre = padre;
	}

	protected ArrayList<ArrayList<Celda>> getNumeros() {
		return numeros;
	}

	protected void setNumeros(ArrayList<ArrayList<Celda>> numeros) {
		this.numeros = numeros;
	}

	protected int getCoste() {
		return coste;
	}

	protected void setCoste(int coste) {
		this.coste = coste;
	}

	protected Double getFN(boolean manhattan) {
		return this.coste + (manhattan ? this.getHManhattan() : this.getHCasillasCambiadas());
	}

	@Override
	public String toString() {
		return this.numeros.get(0) + "\n" + this.numeros.get(1) + "\n" + this.numeros.get(2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeros == null) ? 0 : numeros.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		if (numeros == null) {
			if (other.numeros != null)
				return false;
		} else {
			for (int i = 0; i < numeros.size(); i++) {
				for (int j = 0; j < numeros.get(i).size(); j++) {
					if (numeros.get(i).get(j).getValor() != other.numeros.get(i).get(j).getValor())
						return false;
				}
			}
		}
		return true;
	}

}
