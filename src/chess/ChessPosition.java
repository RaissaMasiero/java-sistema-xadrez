package chess;

import boardgame.Position;

public class ChessPosition {

    private Character coluna;
    private Integer linha;

    public ChessPosition(char coluna, int linha) {
        if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8){
            throw new ChessException("Erro instanciando ChessPosition. Valores válidos são de a1 até h8");
        }
        this.coluna = coluna;
        this.linha = linha;
    }

    public char getColuna() {
        return coluna;
    }

    public int getLinha() {
        return linha;
    }

    protected Position toPosition(){
        return new Position(8 - linha, coluna - 'a');
    }

    protected static ChessPosition fromPosicao(Position posicao){
        return new ChessPosition((char) ('a' + posicao.getColuna()), 8 - posicao.getLinha());
    }

    @Override
    public String toString() {
        return "" + coluna + linha;
    }
}
