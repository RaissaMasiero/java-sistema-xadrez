package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean podeMover(Position posicao){
        ChessPiece p = (ChessPiece) getBoard().piece(posicao);
        return p == null || p.getColor() != getColor();
    }

    private boolean testeTorreRoque(Position posicao){
        ChessPiece p = (ChessPiece) getBoard().piece(posicao);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getContagemMovimento() == 0;
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

        // movimento especial: roque
        if(getContagemMovimento() == 0 && !chessMatch.getCheck()){
            // movimento especial: roque pequeno (rei com torre direita)
            Position posicaoTorreDireita = new Position(position.getLinha(), position.getColuna() + 3);
            if(testeTorreRoque(posicaoTorreDireita)){
                Position p1 = new Position(position.getLinha(), position.getColuna() + 1);
                Position p2 = new Position(position.getLinha(), position.getColuna() + 2);
                if(getBoard().piece(p1) == null && getBoard().piece(p2) == null){
                   mat[position.getLinha()][position.getColuna() + 2] = true;
                }
            }
            // movimento especial: roque grande (rei com torre esquerda)
            Position posicaoTorreEsquerda = new Position(position.getLinha(), position.getColuna() - 4);
            if(testeTorreRoque(posicaoTorreEsquerda)){
                Position p1 = new Position(position.getLinha(), position.getColuna() - 1);
                Position p2 = new Position(position.getLinha(), position.getColuna() - 2);
                Position p3 = new Position(position.getLinha(), position.getColuna() - 3);
                if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
                    mat[position.getLinha()][position.getColuna() - 2] = true;
                }
            }
        }

        return mat;
    }
}
