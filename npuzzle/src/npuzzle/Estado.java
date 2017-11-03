package npuzzle;

import java.awt.Point;
import java.util.ArrayList;

public class Estado {
	private static int COSTE_OPERADOR = 1;
	public static Estado estadoFinal = new Estado();
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
	private ArrayList<ArrayList<Celda>> numeros;
	private int coste;

	private Estado padre;

	public Estado() {
		// 1 2 3 || 8 0 4 || 6 5 4
		numeros = new ArrayList<>(3);
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
	}

	public Estado(Estado anterior) {
		this.numeros = copyArray(anterior.numeros);
		new ArrayList<>(anterior.numeros);
		this.coste = anterior.coste;
		this.addCoste();
		this.padre = anterior;
	}

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

	public void addCoste() {
		coste += COSTE_OPERADOR;
	}

	public Estado getPadre() {
		return padre;
	}

	public void setPadre(Estado padre) {
		this.padre = padre;
	}

	public Double getHCasillasCambiadas() {
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

	public Double getHManhattan() {
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

	public void generateNewPuzzle() {
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

	public Point getPosicion(int valor) {
		for (int i = 0; i < this.numeros.size(); i++) {
			for (int j = 0; j < this.numeros.get(i).size(); j++) {
				if (this.numeros.get(i).get(j).getValor() == valor) {
					return new Point(i, j);
				}
			}
		}
		throw new NullPointerException("value missing - 0");
	}

	public void intercambiarPosiciones(int x, int y) {
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

	public boolean isFinal() {
		return this.equals(estadoFinal);
	}

	public ArrayList<ArrayList<Celda>> getNumeros() {
		return numeros;
	}

	public void setNumeros(ArrayList<ArrayList<Celda>> numeros) {
		this.numeros = numeros;
	}

	public int getCoste() {
		return coste;
	}

	public void setCoste(int coste) {
		this.coste = coste;
	}

	public double getFN(boolean manhattan) {
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
