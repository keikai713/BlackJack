package blackjack;

/* Autor: Alejandro Pérez Durán.
 * Descripción: Juego de Blackjack, usuario contra PC.
 * Fecha de creación: 09/09/2016.
 */
import java.util.Scanner;

public class BlackJack {

    static byte f = 0, c = 0;
    static byte baraja[][] = new byte[14][4];
    static byte[] CartasUsuario = new byte[11];//Contiene las cartas en juego
    static byte[] CartasMaquina = new byte[11];//Contiene las cartas en juego
    static byte[] estUsuario = {0, 0, 0};

    static void generarCoordenadas() {
        f = (byte) (Math.random() * 13 + 1);//Random de 1 a 14
        c = (byte) (Math.random() * 3 + 0);//Random de 0 a 3
    }

    static byte i = 0;

    static void pedirCartaPC() {
        generarCoordenadas();
        if (baraja[f][c] != 0) {
            CartasMaquina[i] = baraja[f][c];
            baraja[f][c] = 0;//Indica que fue usada
            i++;
        } else {
            pedirCartaPC();
        }
    }

    static byte j = 0;

    static void pedirCartaUsuario() {
        generarCoordenadas();
        if (baraja[f][c] != 0) {
            CartasUsuario[j] = baraja[f][c];
            baraja[f][c] = 0;//Indica que fue usada
            j++;
        } else {
            pedirCartaUsuario();
        }
    }

    static void imprimirJuegoPC() {
        for (int k = 0; k < m.length; k++) {
            System.out.print("[" + m[k] + "] ");
        }
        System.out.println("");
        System.out.println("+-------+");
        System.out.println("|MÁQUINA|");
        System.out.println("+-------+");
        for (int i = 0; CartasMaquina[i] != 0; i++) {
            if (imprimirPcTodo) {
                System.out.print("[" + CartasMaquina[i] + "] ");
            } else if (i == 1) {
                System.out.print("[?] ");
            } else {
                System.out.print("[" + CartasMaquina[i] + "] ");
            }
        }
        System.out.println("");
    }

    static void imprimirJuegoUsuario() {
        System.out.println("+-------+");
        System.out.println("|USUARIO|");
        System.out.println("+-------+");
        for (int i = 0; CartasUsuario[i] != 0; i++) {
            System.out.print("[" + CartasUsuario[i] + "] ");
        }
        System.out.println("");
    }

    static boolean imprimirPcTodo = false;

    static byte sumar(byte cartasMano[]) {
        byte suma = 0;
        for (int i = 0; i < cartasMano.length; i++) {
            suma += cartasMano[i];
        }
        return suma;
    }

    static void ganador() {//Se usa cuando ambos jugadores se plantan
        byte sumaPC = (sumar(CartasMaquina));
        byte sumaUsuario = (sumar(CartasUsuario));
        System.out.println("=====================================");
        imprimirPcTodo = true;
        imprimirJuegoPC();
        imprimirJuegoUsuario();
        if (((sumaPC > 21) && (sumaUsuario > 21)) || (sumaPC == sumaUsuario)) {
            System.out.println("\n¡EMPATE!");
            estUsuario[1]++;
            System.out.println("=====================================");
        }
        if ((sumaPC > sumaUsuario && sumaPC <= 21) || sumaUsuario > 21 && sumaPC <= 21) {
            System.out.println("\n¡GANADOR MÁQUINA!");
            estUsuario[2]++;
            System.out.println("=====================================");
        }
        if ((sumaUsuario > sumaPC && sumaUsuario <= 21) || sumaPC > 21 && sumaUsuario <= 21) {
            System.out.println("\n¡GANADOR USUARIO!");
            estUsuario[0]++;
            System.out.println("=====================================");
        }
    }

    static byte siNo;

    static void cambiarAsPC() {
        siNo = (byte) (Math.random() * 2);//Posibilidad 50-50 (Si o no) de cambiar valor AS. Random de 0 a 1.
        if (siNo == 1) {
            for (int i = 0; i < CartasMaquina.length; i++) {
                if (CartasMaquina[i] == 1) {
                    CartasMaquina[i] = 11;
                }
                if (CartasMaquina[i] == 11) {
                    CartasMaquina[i] = 1;
                }
            }
        }
    }

    static void cambiarAs(byte manoCartas[], byte nuevo) {
        for (int i = 0; i < manoCartas.length; i++) {
            if (nuevo == 11 && manoCartas[i] == 1) {
                manoCartas[i] = nuevo;
            }
            if (nuevo == 1 && manoCartas[i] == 11) {
                manoCartas[i] = nuevo;
            }
        }
    }

    static boolean plantarPc;
    static boolean plantarUsuario;
    static int m[] = {50, 50, 50, 50, 50, 50, 50, 50, 50};//No se toman en cuanta las 2 primeras cartas de la PC.

    static byte cont = 0;
    static byte r;

    static void jugadasPC() {
        cont = 0;//Contador del arreglo de probabilidades
        while (sumar(CartasMaquina) < 21 && plantarPc == false) {
            r = (byte) (Math.random() * 100);//Random de 0 a 100.
            if (r <= m[cont]) {//m[cont] (Con aprendizaje)o 50 (Sin aprendizaje)
                pedirCartaPC();
                if (sumar(CartasMaquina) <= 21 && m[cont] < 100) {
                    m[cont] += 2;
                } else if (m[cont] > 0) {
                    m[cont] -= 2;
                }
                cont++;
            } else {
                plantarPc = true;
            }
        }
        cambiarAsPC();
        plantarPc = true;
        if ((plantarPc && plantarUsuario) == true) {
            ganador();
            menu();
        }
    }

    static void menu() {
        Scanner esc = new Scanner(System.in);
        System.out.println("*****BLACKJACK*****\n");
        byte opc;
        do {
            System.out.println("       MENÚ");
            System.out.println("1 .- Nueva partida");
            System.out.println("2 .- Estadísticas");
            System.out.println("3 .- Salir");
            System.out.println("\n[Elige una opción]:");
            opc = esc.nextByte();

            switch (opc) {
                case 1:
                    j = 0;//Contador cartas máquina
                    i = 0;//Contador cartas usuario
                    imprimirPcTodo = false;
                    plantarPc = false;
                    plantarUsuario = false;
                    for (byte i = 0; i < baraja.length; i++) {//Volver baraja a su estado original
                        for (byte j = 0; j < baraja[0].length; j++) {
                            if (i <= 10) {
                                baraja[i][j] = i;
                            } else {
                                baraja[i][j] = 10;
                            }
                        }
                    }

                    for (int k = 0; k < CartasMaquina.length; k++) {//Volver los arreglos con sumas a su estado original
                        CartasMaquina[k] = 0;
                        CartasUsuario[k] = 0;
                    }
                    //Establecer las condiciones iniciales del juego
                    pedirCartaPC();
                    pedirCartaPC();
                    pedirCartaUsuario();
                    pedirCartaUsuario();
                    System.out.println("=====================================");
                    imprimirJuegoPC();
                    imprimirJuegoUsuario();
                    do {
                        System.out.println("=====================================");
                        System.out.println("+---------------------+");
                        System.out.println("|1 .- Pedir carta     |");
                        System.out.println("|2 .- Plantarse       |");
                        System.out.println("|3 .- Cambiar valor AS|");
                        System.out.println("|4 .- Atrás           |");
                        System.out.println("+---------------------+");
                        System.out.println("\n[Elige una opción]:");
                        opc = esc.nextByte();
                        System.out.println("=====================================");
                        switch (opc) {
                            case 1:
                                pedirCartaUsuario();
                                jugadasPC();//Todas las cartas en total que va a pedir la PC.
                                imprimirJuegoPC();
                                imprimirJuegoUsuario();
                                break;
                            case 2:
                                jugadasPC();
                                plantarUsuario = true;
                                if ((plantarPc && plantarUsuario) == true) {
                                    ganador();
                                }
                                opc = 4;//Ir atrás ya que el juego terminó.
                                break;
                            case 3:
                                System.out.println("¿A qué valor deseas cambiar el AS?");
                                byte nuevo = esc.nextByte();
                                cambiarAs(CartasUsuario, nuevo);
                                imprimirJuegoPC();
                                imprimirJuegoUsuario();
                                break;
                            case 4:
                                break;
                            default:
                                System.out.println("=====================================");
                                System.out.println("[¡ERROR!]");
                                System.out.println("=====================================");
                        }
                    } while (opc != 4);
                    break;
                case 2:
                    System.out.println("=====================================");
                    System.out.println("\n[ESTADÍSTICAS DEL USUARIO]");
                    System.out.println("Ganados: " + estUsuario[0]);
                    System.out.println("Empates: " + estUsuario[1]);
                    System.out.println("Perdidos: " + estUsuario[2]);
                    System.out.println("=====================================");
                    break;
                case 3:
                    System.out.println("=====================================");
                    System.out.println("¡GRACIAS POR JUGAR! :)");
                    System.out.println("=====================================");
                    break;
                default:
                    System.out.println("=====================================");
                    System.out.println("[¡ERROR!]");
                    System.out.println("=====================================");
            }
        } while (opc != 3);
    }

    public static void main(String[] args) {
        menu();
    }
}
