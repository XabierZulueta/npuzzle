package npuzzle;

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
	private Double h1;
	private Double h2;
	private Double coste;

	private Estado padre;

	public Estado() {
		// 1 2 3 || 8 0 4 || 6 5 4
		numeros = new ArrayList<>(3);
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
		numeros.add(new ArrayList<>(3));
	}

	public Estado(Estado anterior) {
		this.numeros = anterior.numeros;
		this.h1 = anterior.calcularCeldasDescolocadas();
		this.h2 = anterior.calcularHeuristicaManhattan();
		this.coste = anterior.coste;
		this.addCoste();
		this.padre = anterior;
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

	public Double calcularCeldasDescolocadas() {
		h1 = 0d;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (numeros.get(i).get(j).getValor() != estadoFinal.numeros.get(i).get(j).getValor()) {
					h1++;
				}
			}
		}
		return h1;
	}

	public Double calcularHeuristicaManhattan() {
		h2 = 0d;
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
		this.numeros.get(0).add(new Celda(2, 0, 1));
		this.numeros.get(0).add(new Celda(4, 0, 2));
		this.numeros.get(1).add(new Celda(3, 1, 0));
		this.numeros.get(1).add(new Celda(0, 1, 1));
		this.numeros.get(1).add(new Celda(8, 1, 2));
		this.numeros.get(2).add(new Celda(7, 2, 0));
		this.numeros.get(2).add(new Celda(6, 2, 1));
		this.numeros.get(2).add(new Celda(5, 2, 2));
	}

	public Celda getCeldaByValorEstadoFinal(int valor) {
		for (ArrayList<Celda> list : estadoFinal.numeros) {
			for (Celda c : list) {
				if (c.getValor() == valor) {
					return new Celda(c);
				}
			}
		}
		throw new NullPointerException("value missing");
	}

	public ArrayList<ArrayList<Celda>> getNumeros() {
		return numeros;
	}

	public void setNumeros(ArrayList<ArrayList<Celda>> numeros) {
		this.numeros = numeros;
	}

	public Double getH1() {
		return h1;
	}

	public void setH1(Double h1) {
		this.h1 = h1;
	}

	public Double getH2() {
		return h2;
	}

	public void setH2(Double h2) {
		this.h2 = h2;
	}

	public Double getCoste() {
		return coste;
	}

	public void setCoste(Double coste) {
		this.coste = coste;
	}

}
