package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean podeMover(Position posicao){
        ChessPiece p = (ChessPiece) getBoard().piece(posicao);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0, 0);

        // acima
        p.setValores(position.getLinha() - 1, position.getColuna());
        if(getBoard().existePosicao(p) && podeMover(p)){
           mat[p.getLinha()][p.getColuna()] = true;
        }

        // abaixo
        p.setValores(position.getLinha() + 1, position.getColuna());
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValores(position.getLinha(), position.getColuna() - 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // direita
        p.setValores(position.getLinha(), position.getColuna() + 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // diagonal acima esquerda (noroeste)
        p.setValores(position.getLinha() - 1, position.getColuna() - 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // diagonal acima direita (nordeste)
        p.setValores(position.getLinha() - 1, position.getColuna() + 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // diagonal abaixo esquerda (sudoeste)
        p.setValores(position.getLinha() + 1, position.getColuna() - 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // diagonal abaixo direita (sudeste)
        p.setValores(position.getLinha() + 1, position.getColuna() + 1);
        if(getBoard().existePosicao(p) && podeMover(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
