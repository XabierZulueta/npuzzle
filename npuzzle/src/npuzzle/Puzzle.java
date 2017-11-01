package npuzzle;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
	  private  Celda[][] puzzle;
      private ArrayList<Celda> abiertos;
      private ArrayList<Celda> cerrados;
      private  Celda[][] puzzlefinal;
      private  int h1;
      private  int h2;
      
      public  void generarPuzzle(){
              h1 = 0;
              h2 = 0;
              puzzlefinal =  new Celda[3][3];
              puzzlefinal[0][0] = new Celda(0,0,1,0,0);
              puzzlefinal[0][1] = new Celda(0,0,2,0,1);
              puzzlefinal[0][2] = new Celda(0,0,3,0,2);
              puzzlefinal[1][0] = new Celda(0,0,8,1,0);
              puzzlefinal[1][1] = new Celda(0,0,0,1,1);
              puzzlefinal[1][2] = new Celda(0,0,4,1,2);
              puzzlefinal[2][0] = new Celda(0,0,7,2,0);
              puzzlefinal[2][1] = new Celda(0,0,6,2,1);
              puzzlefinal[2][2] = new Celda(0,0,5,2,2);
              
              puzzle =  new Celda[3][3];
              puzzle[0][0] = new Celda(0,0,1,0,0);
              puzzle[0][1] = new Celda(0,0,2,0,1);
              puzzle[0][2] = new Celda(0,0,4,0,2);
              puzzle[1][0] = new Celda(0,0,3,1,0);
              puzzle[1][1] = new Celda(0,0,0,1,1);
              puzzle[1][2] = new Celda(0,0,8,1,2);
              puzzle[2][0] = new Celda(0,0,7,2,0);
              puzzle[2][1] = new Celda(0,0,6,2,1);
              puzzle[2][2] = new Celda(0,0,5,2,2);

      }
      
      public  void mostrarPuzzle(){
              for(int i=0;i<3;i++){
                      for(int j=0;j<3;j++){
                              System.out.print("["+puzzle[i][j].getValor()+"]");
                      }
                      System.out.println();
              }
              
              for(int i=0;i<3;i++){
                      for(int j=0;j<3;j++){
                              
                              calcularHeuristicaManhattan(puzzle[i][j]);
                              System.out.print("["+puzzle[i][j].getH()+"]");
                      }
                      System.out.println();
              }
              calcularCeldasDescolocadas();
              for(int i=0;i<3;i++){
                      for(int j=0;j<3;j++){
                              System.out.print("["+puzzle[i][j].getH()+"]");
                      }
                      System.out.println();
              }
              
      }
      
      public boolean esFinal(){
              if(puzzle==puzzlefinal)
                      return true;
              else 
                      return false;
      }
      
      public  void calcularCeldasDescolocadas(){
              for(int i=0;i<3;i++){
                      for(int j=0;j<3;j++){
                              if(puzzle[i][j].getValor()!=puzzlefinal[i][j].getValor()){
                                      puzzle[i][j].setH(1);
                                      h1++;
                              }
                      }
              }
      }
      
      public  void calcularHeuristicaManhattan(Celda celda){
              for(int i=0;i<3;i++){
                      for(int j=0;j<3;j++){
                              if(celda.getValor()==puzzlefinal[i][j].getValor()){
                                      celda.setH(Math.abs(puzzlefinal[i][j].getX()-celda.getX())
                                                      + Math.abs(puzzlefinal[i][j].getY()-celda.getY()));
                                      h2 +=celda.getH();
                                      }
                      }
              }
      }
      
      private ArrayList<Celda> operadores(Celda celda) {
 		 ArrayList<Celda> posibilidades = new ArrayList<>();
 		 Celda posible;
 		 //ORDEN DE OPERADORES ><^v
 	     // DERECHA
	     if(celda.getY()!=2){
	    	 posible = hijo(celda, 0, 1);
	    	 posibilidades.add(posible);
	     }	     
 		 // IZQUIERDA
		 if(celda.getY()!=0){
			 
		 }
 		 // ARRIBA
		 if(celda.getX()!=0){
			 
		 }
 		 // ABAJO
		 if(celda.getX()!=2){
			 
		 }
 		 return posibilidades;
 	 
 	 }
      
      private Celda hijo(Celda celdaActual, int x, int y){
    	  Celda sucesor = null;
	    
    	  sucesor = new Celda(
    			  celdaActual.getG()+1,
    			  puzzle[celdaActual.getX() + x][celdaActual.getY() + y].getH(),
    			  puzzle[celdaActual.getX() + x][celdaActual.getY() + y].getValor(),
    			  celdaActual.getX()+ x,
    			  celdaActual.getY() + y
    			  );	
	    	sucesor.setPadre(celdaActual);
	    	return sucesor;
	    }
}
