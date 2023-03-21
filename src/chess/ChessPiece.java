package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

abstract public class ChessPiece extends Piece {

    private Color color;
    private int contagemMovimento;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public int getContagemMovimento() {
        return contagemMovimento;
    }

    public void aumentaContagemMovimento(){
        contagemMovimento++;
    }

    public void diminuiContagemMovimento(){
        contagemMovimento--;
    }

    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosicao(position);
    }

    protected boolean existePecaAdversaria(Position posicao){
        ChessPiece p = (ChessPiece) getBoard().piece(posicao);
        return p != null && p.getColor() != color;
    }
}
