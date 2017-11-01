package npuzzle;

public class Celda {
	 private int g;
     private int h;
     private int f;
     private int valor;
     private int x;
     private int y;
     
     private Celda padre;
     
     public Celda(){
             
     }
     
     public Celda(int g, int h,  int valor, int x, int y) {
             super();
             this.g = g;
             this.h = h;
             this.f = g+h;
             this.valor = valor;
             this.x = x;
             this.y = y;
     }

     @Override
     public String toString() {
             return "Celda [g=" + g + ", h=" + h + ", f=" + f + ", valor=" + valor + ", x=" + x + ", y=" + y + ", padre="
                             + padre + "]";
     }

     public int getG() {
             return g;
     }
     public void setG(int g) {
             this.g = g;
     }
     public int getH() {
             return h;
     }
     public void setH(int h) {
             this.h = h;
     }
     public int getF() {
             return f;
     }
     public void setF(int f) {
             this.f = f;
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
