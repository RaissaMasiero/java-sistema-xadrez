package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition lerChessPosition(Scanner sc){
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new ChessPosition(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro ao ler ChessPosition. Valores válidos são de a1 até h8!");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturadas){
        printTabuleiro(chessMatch.getPecas());
        System.out.println();
        printPecasCapturadas(capturadas);
        System.out.println();
        System.out.println("Turno: " + chessMatch.getTurno());
        System.out.println("Esperando jogador: " + chessMatch.getJogadorAtual());
    }

    public static void printTabuleiro(ChessPiece[][] pecas){
         for(int i=0; i<pecas.length; i++){
             System.out.print((8 - i) + " ");

             for(int j=0; j<pecas.length; j++){
                 printPiece(pecas[i][j], false);
             }
             System.out.println();
         }
        System.out.println("  a b c d e f g h");
    }

    public static void printTabuleiro(ChessPiece[][] pecas, boolean[][] movimentosPossiveis){
        for(int i=0; i<pecas.length; i++){
            System.out.print((8 - i) + " ");

            for(int j=0; j<pecas.length; j++){
                printPiece(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if(background){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);

        } else {

            if (piece.getColor() == Color.BRANCO) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printPecasCapturadas(List<ChessPiece> capturadas){
        List<ChessPiece> brancas = capturadas.stream().filter(x -> x.getColor() == Color.BRANCO).collect(Collectors.toList());
        List<ChessPiece> pretas = capturadas.stream().filter(x -> x.getColor() == Color.PRETO).collect(Collectors.toList());

        System.out.println("Peças capturadas:");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(brancas.toArray()));
        System.out.print(ANSI_RESET);

        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(pretas.toArray()));
        System.out.print(ANSI_RESET);
    }
}
