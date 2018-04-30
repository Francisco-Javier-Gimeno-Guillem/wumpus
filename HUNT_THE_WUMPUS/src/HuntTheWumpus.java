import java.awt.event.KeyEvent;
import java.util.*;
import java.io.*;




public class HuntTheWumpus {
	
	private static String orientacion;
	private Tablero tab;
	private int posicionx = 0;
	private int posiciony = 0;
	private int celdas = 0;
	private char valorCazador;
	private boolean killed = false;
	private boolean endgame = false;
	private boolean tengotesoro = false;
	private boolean impacto = false;


	public HuntTheWumpus(){
			
		
	}
	

		

		
	
	// Función principal
	public void jugar() {
	
		
		boolean salida = true;
		boolean visualizar = false;

		int pozos = 0;
		int maxPozos = 0;
		int flechas = 0;

		
		System.out.println("Bienvenido al juego HuntTheWumpus \n");
		System.out.println("¿Desea visualizar el mapa? S o N\n");
		Scanner entradaVis = new Scanner (System.in);
		String entradavisualizar = entradaVis.nextLine ();
		
		if(entradavisualizar.equals("N") || entradavisualizar.equals("n")){
			visualizar = false;
		}else if(entradavisualizar.equals("S") || entradavisualizar.equals("s")){
			visualizar = true;
		}
		
		System.out.println("Introduce el número de celdas nxn: Introduce n donde n tiene que ser 3,4,5 \n");
		
		while(salida){
			Scanner entradaCeldas = new Scanner (System.in);
			String entradaTeclado1 = entradaCeldas.nextLine ();
			
			if(entradaTeclado1.equals("3") || entradaTeclado1.equals("4") || entradaTeclado1.equals("5")){
				celdas = ((Integer.parseInt(entradaTeclado1)));
				salida = false;
			}
			else
				System.out.println("Introduce un número válido (3,4,5)  \n");

		}
		
		salida = true;
		
        if(celdas == 3){
        	maxPozos = 1;
        } else if(celdas == 4){
        	maxPozos = 2;        	
        } else
        	maxPozos = 3;		
		
		
		System.out.println("Introduce el número de pozos: Si seleccionaste 3 Celdas máx Pozos 1, 4 Celdas máx Pozos 2, y 5 Celdas máx Pozos 3 \n");
		
		while(salida){
		Scanner entradaPozos = new Scanner (System.in);
        String entradaTeclado2 = entradaPozos.nextLine ();		
        
        if(entradaTeclado2.equals("1") || entradaTeclado2.equals("2") || entradaTeclado2.equals("3")){
        	pozos = ((Integer.parseInt(entradaTeclado2)));
        	salida = false;
        }else
			System.out.println("Introduce un número válido (1,2,3)  \n");

        
		}
		
		
		salida = true;
		System.out.println("Introduce el número de flechas (1,2,3)  \n");

		
		while(salida){
		Scanner entradaFlechas = new Scanner (System.in);
        String entradaTeclado3 = entradaFlechas.nextLine ();		
        
        if(entradaTeclado3.equals("1") || entradaTeclado3.equals("2") || entradaTeclado3.equals("3")){
        	flechas = ((Integer.parseInt(entradaTeclado3)));
        	salida = false;
        }else
			System.out.println("Introduce un número válido (1,2,3)  \n");

        
		}

		
		// Inicializo tablero
		
		tab = new Tablero(celdas,pozos, flechas);

		
		char[][] tablero = tab.inicializar();
		
		// Orientación cazador
		orientacion = "N";
		posicionx = celdas-1;
		posiciony =	0;
		valorCazador = 'x';
			
		if(visualizar){
			tab.pintar(tablero);
		}
		
		percepcion(tablero);
        
        
        //Jugar
        while(!endgame && !killed){
        	
        	
    		System.out.println("Pulse w para avanzar, a para girar 90 grados a la izquierda, d para girar a la derecha o f para disparar flecha \n");
    		
			Scanner entradaMovimiento = new Scanner (System.in);
			String entradaTecladoMovimiento = entradaMovimiento.nextLine ();
			
			// Si tengo flechas y pulsan la tecla acción.
			if(entradaTecladoMovimiento.equals("f") && flechas > 0){
				
				disparar(tablero);
				percepcion(tablero);

				flechas --;
			}
			
			if(entradaTecladoMovimiento.equals("f") && flechas <= 0){
				System.out.println("No quedan flechas \n");
			}
			
			
			
			if(entradaTecladoMovimiento.equals("w")){
				System.out.println("Muevo hacia delante según orientación \n");
				avanzar(tablero);
				
				//Si estoy muerto no evaluo la percepcion
				if(killed != true)
					percepcion(tablero);
				
				if(visualizar){
					tab.pintar(tablero);
				}
			
				
			}else if(entradaTecladoMovimiento.equals("a")){
				System.out.println("Giro hacia la izquierda \n");
				percepcion(tablero);
				
				if(orientacion.equals("N"))
					orientacion = "O";
				else if (orientacion.equals("O"))
					orientacion = "S";
				else if(orientacion.equals("S"))
					orientacion = "E";	
				else if(orientacion.equals("E"))
					orientacion = "N";
				
			}else if(entradaTecladoMovimiento.equals("d")){
				System.out.println("Giro hacia la derecha \n");
				percepcion(tablero);
				
				if(orientacion.equals("N"))
					orientacion = "E";
				else if (orientacion.equals("E"))
					orientacion = "S";
				else if(orientacion.equals("S"))
					orientacion = "O";	
				else if(orientacion.equals("O"))
					orientacion = "N";
				
			}
			
        	
        	
        }
        
        if(killed){
    		System.out.println("GAME OVER: Has muerto \n");
        	
        }
        if(endgame){
    		System.out.println("Enhorabuena! Has salido con el tesoro!!!!!!!!! \n");
        	
        }
        
	
	}
	
	
	
	
	
	
	// Función para avanzar el cazador y ver si hay algún evento
	public char[] [] avanzar(char tablero [] []){
		

		
		
		if(orientacion.equals("N") && posicionx > 0){
			tablero[posicionx][posiciony] = valorCazador;
			posicionx --;
			if(tablero[posicionx][posiciony] == 'x'){
				tablero[posicionx][posiciony] = '&';
			}else if(tablero[posicionx][posiciony] == '$'){
	    		System.out.println("Encontraste el tesoro, ahora sal \n");
	    		tengotesoro = true;
			}else{
				killed = true;
				
			}

		}
			
		else if (orientacion.equals("E") && posiciony < celdas-1){
			tablero[posicionx][posiciony] = valorCazador;
			posiciony ++;
			if(tablero[posicionx][posiciony] == 'x'){
				tablero[posicionx][posiciony] = '&';
			}else if(tablero[posicionx][posiciony] == '$'){
	    		System.out.println("Encontraste el tesoro, ahora sal \n");
	    		tengotesoro = true;
			}else{
				killed = true;
				
			}
			
		}
		else if(orientacion.equals("S") && posicionx < celdas - 1){
			tablero[posicionx][posiciony] = valorCazador;
			posicionx ++;
			if(tablero[posicionx][posiciony] == 'x'){
				tablero[posicionx][posiciony] = '&';
			}else if(tablero[posicionx][posiciony] == '$'){
	    		System.out.println("Encontraste el tesoro, ahora sal \n");
	    		tengotesoro = true;
			}else{
				killed = true;
				
			}
			
		}
		else if(orientacion.equals("O") && posiciony > 0){
			tablero[posicionx][posiciony] = valorCazador;
			posiciony --;
			if(tablero[posicionx][posiciony] == 'x'){
				tablero[posicionx][posiciony] = '&';
			}else if(tablero[posicionx][posiciony] == '$'){
	    		System.out.println("Encontraste el tesoro, ahora sal \n");
	    		tengotesoro = true;
			}else{
				killed = true;
				
			}
			
		}
		
		if(posicionx == celdas-1 && posiciony == 0 && tengotesoro == true){
			
			endgame = true;
		}
		
		 if(orientacion.equals("O") && posiciony == 0){
	    		System.out.println("Percibes un choque \n");

		 }
		 if(orientacion.equals("E") && posiciony == celdas-1){
	    		System.out.println("Percibes un choque \n");

		 }
		 if(orientacion.equals("N") && posicionx == 0){
	    		System.out.println("Percibes un choque \n");

		 }
		 if(orientacion.equals("S") && posicionx == celdas-1){
	    		System.out.println("Percibes un choque \n");

		 }
		
		
		
		return tablero;
	}
	
	
	
	
	// Función para determinar la percepción de la posición en la que estás
	public void percepcion(char tablero [] []){
		
		
		if(posicionx+1<celdas){
			if(tablero[posicionx+1][posiciony] == '@'){
	    		System.out.println("Percibes hedor \n");
			}
			if(tablero[posicionx+1][posiciony] == '0'){
	    		System.out.println("Percibes brisa \n");
			}
		}
		if(posicionx-1>=0){
			if(tablero[posicionx-1][posiciony] == '@'){
	    		System.out.println("Percibes hedor \n");
			}
			if(tablero[posicionx-1][posiciony] == '0'){
	    		System.out.println("Percibes brisa \n");
			}
		}
		if(posiciony+1<celdas){
			if(tablero[posicionx][posiciony+1] == '@'){
	    		System.out.println("Percibes hedor \n");
			}
			if(tablero[posicionx][posiciony+1] == '0'){
	    		System.out.println("Percibes brisa \n");
			}
		}
		if(posiciony-1>=0){
			if(tablero[posicionx][posiciony-1] == '@'){
	    		System.out.println("Percibes hedor \n");
			}
			if(tablero[posicionx][posiciony-1] == '0'){
	    		System.out.println("Percibes brisa \n");
			}
		}
		
	    if(impacto == true){
    		System.out.println("Percibes un grito \n");
    		impacto = false;
		}
		
	
	}
	
	
	
	
	// Función para el disparo de flechas
	public void disparar(char tablero [] []){
		
		if(orientacion.equals("N")){
			for(int i=posicionx; i>=0; i--){
				if(tablero[i][posiciony]== '@'){
					impacto = true;
				}	
			}
		}
			
		if(orientacion.equals("S")){
			for(int i=posicionx; i<=celdas-1; i++){
				if(tablero[i][posiciony]== '@'){
					impacto = true;
				}	
			}
		}
		
		if(orientacion.equals("E")){
			for(int i=posiciony; i<=celdas-1; i++){
				if(tablero[posicionx][i]== '@'){
					impacto = true;
				}	
			}
		}
		
		if(orientacion.equals("O")){
			for(int i=posiciony; i>=0; i--){
				if(tablero[posicionx][i]== '@'){
					impacto = true;
				}	
			}
		}
			
			
		if(impacto == false){
    		System.out.println("La flecha golpeó la pared \n");
		}
			
	
	}
	
	
	

}
