package application;

import boardgame.Board;
import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch cm = new ChessMatch();
        List<ChessPiece> capturadas = new ArrayList<>();

        while (!cm.getCheckMate()) {
             try {
                UI.limpaTela();
                UI.printMatch(cm, capturadas);
                System.out.println();
                System.out.print("Origem: ");
                ChessPosition origem = UI.lerChessPosition(sc);

                boolean[][] movimentosPossiveis = cm.movimentosPossiveis(origem);
                UI.limpaTela();
                UI.printTabuleiro(cm.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                ChessPosition destino = UI.lerChessPosition(sc);

                ChessPiece pecaCapturada = cm.performChessMove(origem, destino);

                if(pecaCapturada != null){
                   capturadas.add(pecaCapturada);
                }

                if(cm.getPromovida() != null){
                   System.out.print("Digite a peça para promoção (B/N/R/Q): ");
                   String tipo = sc.nextLine();
                   cm.trocaPecaPromovida(tipo);
                }

             } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.limpaTela();
        UI.printMatch(cm, capturadas);
    }
}
