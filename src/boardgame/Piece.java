package boardgame;

abstract public class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    protected Board getBoard() {
        return board;
    }

    abstract public boolean[][] movimentosPossiveis();

    public boolean movimentoPossivel(Position position){
        return movimentosPossiveis()[position.getLinha()][position.getColuna()];
    }

    public boolean temQualquerMovimentoPossivel(){
        boolean[][] mat = movimentosPossiveis();
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat.length; j++){
                if(mat[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
}
