package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    // Pawn: Peão
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

            // movimento especial: en passant (peça branca)
            if(position.getLinha() == 3){
               Position esquerda = new Position(position.getLinha(), position.getColuna() - 1);
               if(getBoard().existePosicao(esquerda) && existePecaAdversaria(esquerda)
                                                        && getBoard().piece(esquerda) == chessMatch.getEnPassantVulneravel()){
                  mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
               }

                Position direita = new Position(position.getLinha(), position.getColuna() + 1);
                if(getBoard().existePosicao(direita) && existePecaAdversaria(direita)
                        && getBoard().piece(direita) == chessMatch.getEnPassantVulneravel()){
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
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

            // movimento especial: en passant (peça preta)
            if(position.getLinha() == 4) {
                Position esquerda = new Position(position.getLinha(), position.getColuna() - 1);
                if (getBoard().existePosicao(esquerda) && existePecaAdversaria(esquerda)
                                                    && getBoard().piece(esquerda) == chessMatch.getEnPassantVulneravel()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Position direita = new Position(position.getLinha(), position.getColuna() + 1);
                if (getBoard().existePosicao(direita) && existePecaAdversaria(direita)
                                                     && getBoard().piece(direita) == chessMatch.getEnPassantVulneravel()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
