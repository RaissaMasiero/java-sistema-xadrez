package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    private Board board;

    public ChessMatch(){
        board = new Board(8, 8);
        setupInicial();
    }

    public ChessPiece[][] getPecas(){
        ChessPiece[][] mat = new ChessPiece[board.getLinhas()][board.getColunas()];

        for(int i=0; i<board.getLinhas(); i++){

            for(int j=0; j<board.getColunas(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] movimentosPossiveis(ChessPosition posicaoOrigem){
        Position posicao = posicaoOrigem.toPosition();
        validaPosicaoOrigem(posicao);
        return board.piece(posicao).movimentosPossiveis();
    }

    public ChessPiece performChessMove(ChessPosition posicaoOriginal, ChessPosition posicaoDestino){
        Position origem = posicaoOriginal.toPosition();
        Position alvo = posicaoDestino.toPosition();
        validaPosicaoOrigem(origem);
        validaPosicaoDestino(origem, alvo);
        Piece pecaCapturada = makeMove(origem, alvo);
        return (ChessPiece) pecaCapturada;
    }

    private Piece makeMove(Position origem, Position destino){
        Piece p = board.removePeca(origem);
        Piece capturada = board.removePeca(destino);
        board.colocaPeca(p, destino);
        return capturada;
    }

    private void validaPosicaoOrigem(Position posicao){
        if(!board.existeUmaPeca(posicao)){
            throw new ChessException("Não existe peça na posição de origem!");
        }
        if(!board.piece(posicao).temQualquerMovimentoPossivel()){
            throw new ChessException("Não existem movimentos possíveis para a peça escolhida!");
        }
    }

    private void validaPosicaoDestino(Position origem, Position destino){
        if(!board.piece(origem).movimentoPossivel(destino)){
            throw new ChessException("A peça escolhida não pode se mover para a posição de destino!");
        }
    }

    private void colocaNovaPeca(char coluna, int linha, ChessPiece peca){
        board.colocaPeca(peca, new ChessPosition(coluna, linha).toPosition());
    }

    private void setupInicial(){
        colocaNovaPeca('c', 1, new Rook(board, Color.BRANCO));
        colocaNovaPeca('c', 2, new Rook(board, Color.BRANCO));
        colocaNovaPeca('d', 2, new Rook(board, Color.BRANCO));
        colocaNovaPeca('e', 2, new Rook(board, Color.BRANCO));
        colocaNovaPeca('e', 1, new Rook(board, Color.BRANCO));
        colocaNovaPeca('d', 1, new King(board, Color.BRANCO));

        colocaNovaPeca('c', 7, new Rook(board, Color.PRETO));
        colocaNovaPeca('c', 8, new Rook(board, Color.PRETO));
        colocaNovaPeca('d', 7, new Rook(board, Color.PRETO));
        colocaNovaPeca('e', 7, new Rook(board, Color.PRETO));
        colocaNovaPeca('e', 8, new Rook(board, Color.PRETO));
        colocaNovaPeca('d', 8, new King(board, Color.PRETO));
    }
}
