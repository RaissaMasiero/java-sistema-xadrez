package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch(){
        board = new Board(8, 8);
        setupInicial();
    }

    public ChessPiece[][] getPecas(){
        ChessPiece[][] mat = new ChessPiece[board.getLinhas()][board.getColunas()];

        for(int i=0; i<board.getLinhas(); i++){

            for(int j=0; j<board.getColunas(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    private void setupInicial(){
         board.colocaPeca(new Rook(board, Color.BRANCO), new Position(2, 1));
         board.colocaPeca(new King(board, Color.PRETO), new Position(0, 4));
        board.colocaPeca(new King(board, Color.BRANCO), new Position(7, 4));
    }
}
