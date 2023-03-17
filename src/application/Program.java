package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch cm = new ChessMatch();

        while (true) {
            try {
                UI.limpaTela();
                UI.printTabuleiro(cm.getPecas());
                System.out.println();
                System.out.print("Origem: ");
                ChessPosition origem = UI.lerChessPosition(sc);

                System.out.println();
                System.out.print("Destino: ");
                ChessPosition destino = UI.lerChessPosition(sc);

                ChessPiece pecaCapturada = cm.performChessMove(origem, destino);

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }

        }
    }
}
