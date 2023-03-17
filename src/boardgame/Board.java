package boardgame;

public class Board {

    private Integer linhas;
    private Integer colunas;
    private Piece[][] pecas;

    public Board(Integer linhas, Integer colunas) {
        if(linhas < 1 || colunas < 1){
           throw new BoardException("Erro ao criar tabuleiro: é necessário pelo menos 1 linha e 1 coluna!");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Piece[linhas][colunas];
    }

    public Integer getLinhas() {
        return linhas;
    }

    public Integer getColunas() {
        return colunas;
    }

    public Piece piece(int linha, int coluna){
        if(!existePosicao(linha, coluna)){
            throw new BoardException("Posição não existe no tabuleiro!");
        }
        return pecas[linha][coluna];
    }

    public Piece piece(Position position){
        if(!existePosicao(position)){
            throw new BoardException("Posição não existe no tabuleiro!");
        }
        return pecas[position.getLinha()][position.getColuna()];
    }

    public void colocaPeca(Piece peca, Position posicao){
        if(existeUmaPeca(posicao)){
            throw new BoardException("Já tem uma peça na posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.position = posicao;
    }

    public Piece removePeca(Position posicao){
        if(!existePosicao(posicao)){
            throw new BoardException("Posição não existe no tabuleiro!");
        }
        if(piece(posicao) == null){
            return null;
        }
        Piece aux = piece(posicao);
        aux.position = null;
        pecas[posicao.getLinha()][posicao.getColuna()] = null;
        return aux;
    }

    private boolean existePosicao(int linha, int coluna){
        return linha >=0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean existePosicao(Position posicao){
        return existePosicao(posicao.getLinha(), posicao.getColuna());
    }

    public boolean existeUmaPeca(Position posicao){
        if(!existePosicao(posicao)){
            throw new BoardException("Posição não existe no tabuleiro!");
        }
        return piece(posicao) != null;
    }
}
