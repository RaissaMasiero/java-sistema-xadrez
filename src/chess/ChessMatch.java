package chess;

import boardgame.Board;

public class ChessMatch {

    private Board board;

    public ChessMatch(){
        board = new Board(8, 8);
    }

    public ChessPiece[][] getPecas(){
        ChessPiece[][] mat = new ChessPiece[board.getLinhas()][board.getColunas()];

        for(int i=0; i< board.getLinhas(); i++){

            for(int j=0; j< board.getColunas(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }
}
