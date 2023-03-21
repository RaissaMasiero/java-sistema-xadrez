package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    // Knight: Cavalo
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean podeMover(Position posicao){
        ChessPiece p = (ChessPiece) getBoard().piece(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0, 0);

        p.setValores(position.getLinha() - 1, position.getColuna() - 2);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() - 2, position.getColuna() - 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() - 2, position.getColuna() + 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() - 1, position.getColuna() + 2);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() + 1, position.getColuna() + 2);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() + 2, position.getColuna() + 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() + 2, position.getColuna() - 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValores(position.getLinha() + 1, position.getColuna() - 2);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}

