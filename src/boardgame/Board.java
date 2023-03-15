package boardgame;

public class Board {

    private Integer linhas;
    private Integer colunas;
    private Piece[][] pecas;

    public Board(Integer linhas, Integer colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        pecas = new Piece[linhas][colunas];
    }

    public Integer getLinhas() {
        return linhas;
    }

    public void setLinhas(Integer linhas) {
        this.linhas = linhas;
    }

    public Integer getColunas() {
        return colunas;
    }

    public void setColunas(Integer colunas) {
        this.colunas = colunas;
    }

    public Piece piece(int linha, int coluna){
        return pecas[linha][coluna];
    }

    public Piece piece(Position position){
        return pecas[position.getLinha()][position.getColuna()];
    }

    public void colocaPeca(Piece peca, Position posicao){
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.position = posicao;
    }
}
