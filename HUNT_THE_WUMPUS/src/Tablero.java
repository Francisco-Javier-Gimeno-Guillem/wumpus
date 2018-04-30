import java.util.*;


public class Tablero {
	
	private char tablero[] [];
	private int numPozos;
	private int numCeldas;
	private int maxPozos;
	private int numFlechas;
	
	


	public Tablero(int celdas, int pozos, int flechas){
		
		this.numPozos = pozos;
		this.numCeldas = celdas;
		this.tablero = new char [celdas] [celdas]; 
		this.numFlechas = flechas;
	}
	
	public void pintar(char tablero[][]){
		
		for(int i=0; i<numCeldas;i++){
			for(int j=0; j<numCeldas;j++){
				System.out.print(tablero[i][j]);
				if(j==numCeldas-1){
					System.out.println("\t");
				}
			}
		}
		
		
		
	}
	
	
	public char[][] inicializar(){
		
		char dinero = '$';
		char wumpus = '@';
		char pozo = '0';
		char cazador = '&';
		
		tablero = new char [numCeldas] [numCeldas];
		
		
		for(int i=0; i<numCeldas;i++){
			for(int j=0; j<numCeldas;j++){
				tablero[i] [j] = 'x';
				}
			}
		
		//El cazador siempre en la esquina izquierda
		tablero[numCeldas-1] [0] = cazador;
		
		
		
		// Función random
		
		String prueba = "$@0";
		
		if(numPozos == 2){
			prueba = prueba.replace(prueba, "$@00");
			
		}else if(numPozos == 3)
			prueba = prueba.replace(prueba, "$@000");
		
		
		for(int n=0; n<prueba.length();n++){
			
			Random random = new Random();
			boolean aux = true;
			
			while(aux){
				
				int numerox_dinero = random.nextInt(numCeldas);
				int numeroy_dinero = random.nextInt(numCeldas);
				
				
				if(tablero[numerox_dinero][numeroy_dinero] == 'x'){
					
					tablero[numerox_dinero][numeroy_dinero] = prueba.charAt(n);
					aux = false;
				}
			
			}
			
						
				
		}
			
			
		
		return tablero;

		
	}

	
	
	public int getMaxPozos() {
		return maxPozos;
	}

	public void setMaxPozos(int maxPozos) {
		this.maxPozos = maxPozos;
	}
	
	
	public char[][] getTablero() {
		return tablero;
	}

	public void setTablero(char[][] tablero) {
		this.tablero = tablero;
	}
	
}
