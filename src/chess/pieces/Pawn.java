package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    // Pawn: Peão
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0, 0);

        if(getColor() == Color.BRANCO){
            // move 1 casa pra frente
            p.setValores(position.getLinha() - 1, position.getColuna());
            if(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move 2 casas pra frente
            p.setValores(position.getLinha() - 2, position.getColuna());
            Position p2 = new Position(position.getLinha() - 1, position.getColuna());

            if(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)
                    && getBoard().existePosicao(p2) && !getBoard().existeUmaPeca(p2)
                    && getContagemMovimento() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move diagonal esquerda
            p.setValores(position.getLinha() - 1, position.getColuna() - 1);
            if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move diagonal direita
            p.setValores(position.getLinha() - 1, position.getColuna() + 1);
            if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

        }else{
            // move 1 casa pra frente
            p.setValores(position.getLinha() + 1, position.getColuna());
            if(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move 2 casas pra frente
            p.setValores(position.getLinha() + 2, position.getColuna());
            Position p2 = new Position(position.getLinha() + 1, position.getColuna());

            if(getBoard().existePosicao(p) && !getBoard().existeUmaPeca(p)
                    && getBoard().existePosicao(p2) && !getBoard().existeUmaPeca(p2)
                    && getContagemMovimento() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move diagonal esquerda
            p.setValores(position.getLinha() + 1, position.getColuna() - 1);
            if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // move diagonal direita
            p.setValores(position.getLinha() + 1, position.getColuna() + 1);
            if(getBoard().existePosicao(p) && existePecaAdversaria(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
