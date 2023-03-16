package chess;

import boardgame.Board;
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

    private void colocaNovaPeca(char coluna, int linha, ChessPiece peca){
        board.colocaPeca(peca, new ChessPosition(coluna, linha).toPosition());
    }

    private void setupInicial(){
         colocaNovaPeca('b', 6, new Rook(board, Color.BRANCO));
         colocaNovaPeca('e', 8, new King(board, Color.PRETO));
        colocaNovaPeca('e', 1, new King(board, Color.BRANCO));
    }
}
