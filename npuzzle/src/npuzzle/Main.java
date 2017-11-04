package npuzzle;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Puzzle puzzle = new Puzzle();
		Estado estadoInicial = puzzle.generarPuzzle();

		puzzle.busquedaCosteUniforme(estadoInicial);

	}

}
