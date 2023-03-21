package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    // Bishop: Bispo
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0, 0);

        // verifica diagonal esquerda acima (noroeste)
        p.setValores(position.getLinha() - 1, position.getColuna() - 1);
        while(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() -1, p.getColuna() - 1);
        }
        if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // verifica diagonal direita acima (nordeste)
        p.setValores(position.getLinha() - 1, position.getColuna() + 1);
        while(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() -1, p.getColuna() + 1);
        }
        if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // verifica diagonal direita abaixo (sudeste)
        p.setValores(position.getLinha() + 1, position.getColuna() + 1);
        while(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() + 1);
        }
        if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // verifica diagonal esquerda abaixo (sudoeste)
        p.setValores(position.getLinha() + 1, position.getColuna() - 1);
        while(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
            mat[p.getLinha()][p.getColuna()] = true;
            p.setValores(p.getLinha() + 1, p.getColuna() - 1);
        }
        if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }
}
