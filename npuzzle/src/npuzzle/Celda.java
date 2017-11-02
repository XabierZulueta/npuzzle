package npuzzle;

public class Celda {
	private int valor;
	private int x;
	private int y;

	private Celda padre;

	public Celda() {

	}

	public Celda(Celda celda) {
		this.valor = celda.valor;
		this.x = celda.x;
		this.y = celda.y;
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
		return "Celda [valor=" + valor + ", x=" + x + ", y=" + y + "]";
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

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Celda getPadre() {
		return padre;
	}

	public void setPadre(Celda padre) {
		this.padre = padre;
	}
}
