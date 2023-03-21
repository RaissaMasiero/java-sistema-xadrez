package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Integer turno;
    private Color jogadorAtual;
    private Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece promovida;
    private ChessPiece enPassantVulneravel;
    private List<Piece> pecasNoTabuleiro = new ArrayList<>();
    private List<Piece> pecasCapturadas = new ArrayList<>();

    public ChessMatch(){
        board = new Board(8, 8);
        turno = 1;
        jogadorAtual = Color.BRANCO;
        setupInicial();
    }

    public int getTurno(){
        return turno;
    }

    public Color getJogadorAtual(){
        return jogadorAtual;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public ChessPiece getPromovida() {
        return promovida;
    }

    public ChessPiece getEnPassantVulneravel() {
        return enPassantVulneravel;
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

        if(testeCheck(jogadorAtual)){
           desfazerMovimento(origem, alvo, pecaCapturada);
           throw new ChessException("Você não pode se colocar em cheque!");
        }

        ChessPiece pecaMovida = (ChessPiece) board.piece(alvo);

        // movimento especial: promoção

        promovida = null;
        if(pecaMovida instanceof Pawn){
           if((pecaMovida.getColor() == Color.BRANCO && alvo.getLinha() == 0) ||
                                                              pecaMovida.getColor() == Color.PRETO && alvo.getLinha() == 7){
              promovida = (ChessPiece) board.piece(alvo);
              promovida = trocaPecaPromovida("Q");
           }
        }

        check = (testeCheck(oponente(jogadorAtual))) ? true : false;

        if(testeCheckMate(oponente(jogadorAtual))){
           checkMate = true;
        }else{
           proximoTurno();
        }

        // movimento especial: en passant

        if(pecaMovida instanceof Pawn && (alvo.getLinha() == origem.getLinha() - 2 || alvo.getLinha() == origem.getLinha() + 2)){
           enPassantVulneravel = pecaMovida;
        }else{
           enPassantVulneravel = null;
        }

        return (ChessPiece) pecaCapturada;
    }

    public ChessPiece trocaPecaPromovida(String tipo){
        if(promovida == null){
           throw new IllegalStateException("Não há peça para ser promovida!");
        }
        if(!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")){
           return promovida;
        }

        Position pos = promovida.getChessPosition().toPosition();
        Piece p = board.removePeca(pos);
        pecasNoTabuleiro.remove(p);

        ChessPiece novaPeca = novaPeca(tipo, promovida.getColor());
        board.colocaPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private ChessPiece novaPeca(String tipo, Color color){
        if(tipo.equals("B")) return new Bishop(board, color);
        if(tipo.equals("N")) return new Knight(board, color);
        if(tipo.equals("Q")) return new Queen(board, color);
        return new Rook(board, color);
    }

    private Piece makeMove(Position origem, Position destino){
        ChessPiece p = (ChessPiece) board.removePeca(origem);
        p.aumentaContagemMovimento();
        Piece capturada = board.removePeca(destino);
        board.colocaPeca(p, destino);

        if(capturada != null){
           pecasNoTabuleiro.remove(capturada);
           pecasCapturadas.add(capturada);
        }

        // movimento especial: roque pequeno (torre a direita)
        if(p instanceof King && destino.getColuna() == origem.getColuna() + 2){
           Position origemT = new Position(origem.getLinha(), origem.getColuna() + 3);
           Position destinoT = new Position(origem.getLinha(), origem.getColuna() + 1);
           ChessPiece torre = (ChessPiece) board.removePeca(origemT);
           board.colocaPeca(torre, destinoT);
           torre.aumentaContagemMovimento();
        }

        // movimento especial: roque grande (torre a esquerda)
        if(p instanceof King && destino.getColuna() == origem.getColuna() - 2){
            Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
            Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
            ChessPiece torre = (ChessPiece) board.removePeca(origemT);
            board.colocaPeca(torre, destinoT);
            torre.aumentaContagemMovimento();
        }

        // movimento especial: en passant
        if(p instanceof Pawn){
           if(origem.getColuna() != destino.getColuna() && capturada == null){
              Position posicaoPeao;
              if(p.getColor() == Color.BRANCO){
                 posicaoPeao = new Position(destino.getLinha() + 1, destino.getColuna());
              }else{
                 posicaoPeao = new Position(destino.getLinha() - 1, destino.getColuna());
              }
              capturada = board.removePeca(posicaoPeao);
              pecasCapturadas.add(capturada);
              pecasNoTabuleiro.remove(capturada);
           }
        }

        return capturada;
    }

    private void desfazerMovimento(Position origem, Position destino, Piece pecaCapturada){
        ChessPiece p = (ChessPiece) board.removePeca(destino);
        p.diminuiContagemMovimento();
        board.colocaPeca(p, origem);
        if(pecaCapturada != null){
           board.colocaPeca(pecaCapturada, destino);
           pecasCapturadas.remove(pecaCapturada);
           pecasNoTabuleiro.add(pecaCapturada);
        }

        // movimento especial: roque pequeno (torre a direita)
        if(p instanceof King && destino.getColuna() == origem.getColuna() + 2){
            Position origemT = new Position(origem.getLinha(), origem.getColuna() + 3);
            Position destinoT = new Position(origem.getLinha(), origem.getColuna() + 1);
            ChessPiece torre = (ChessPiece) board.removePeca(destinoT);
            board.colocaPeca(torre, origemT);
            torre.diminuiContagemMovimento();
        }

        // movimento especial: roque grande (torre a esquerda)
        if(p instanceof King && destino.getColuna() == origem.getColuna() - 2){
            Position origemT = new Position(origem.getLinha(), origem.getColuna() - 4);
            Position destinoT = new Position(origem.getLinha(), origem.getColuna() - 1);
            ChessPiece torre = (ChessPiece) board.removePeca(destinoT);
            board.colocaPeca(torre, origemT);
            torre.diminuiContagemMovimento();
        }

        // movimento especial: en passant
        if(p instanceof Pawn){
            if(origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel){
                ChessPiece peao = (ChessPiece) board.removePeca(destino);
                Position posicaoPeao;
                if(p.getColor() == Color.BRANCO){
                    posicaoPeao = new Position(3, destino.getColuna());
                }else{
                    posicaoPeao = new Position(4, destino.getColuna());
                }
                board.colocaPeca(peao, posicaoPeao);
            }
        }
    }

    private void validaPosicaoOrigem(Position posicao){
        if(!board.existeUmaPeca(posicao)){
            throw new ChessException("Não existe peça na posição de origem!");
        }
        if(jogadorAtual != ((ChessPiece)board.piece(posicao)).getColor()){
            throw new ChessException("A peça escolhida não é sua!");
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

    private void proximoTurno(){
        turno++;
        jogadorAtual = (jogadorAtual == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private Color oponente(Color color){
        return (color == Color.BRANCO) ? Color.PRETO : Color.BRANCO;
    }

    private ChessPiece king(Color color){
        List<Piece> list = pecasNoTabuleiro.stream()
                            .filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            if(p instanceof King){
               return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("Não existe rei " + color + " no tabuleiro!");
    }

    private boolean testeCheck(Color color){
        Position posicaoRei = king(color).getChessPosition().toPosition();
        List<Piece> pecasOponente = pecasNoTabuleiro.stream()
                                      .filter(x -> ((ChessPiece)x).getColor() == oponente(color)).collect(Collectors.toList());
        for(Piece p : pecasOponente){
            boolean[][] mat = p.movimentosPossiveis();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]){
               return true;
            }
        }
        return false;
    }

    private boolean testeCheckMate(Color color){
        if(!testeCheck(color)){
            return false;
        }
        List<Piece> list = pecasNoTabuleiro.stream()
                                .filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            boolean[][] mat = p.movimentosPossiveis();
            for(int i=0; i<board.getLinhas(); i++){
                for(int j=0; j<board.getColunas(); j++){
                    if(mat[i][j]){
                       Position origem = ((ChessPiece)p).getChessPosition().toPosition();
                       Position destino = new Position(i, j);
                       Piece pecaCapturada = makeMove(origem, destino);
                       boolean testeCheck = testeCheck(color);
                       desfazerMovimento(origem, destino, pecaCapturada);
                       if(!testeCheck){
                           return false;
                       }
                    }
                }
            }
        }
        return true;
    }

    private void colocaNovaPeca(char coluna, int linha, ChessPiece peca){
        board.colocaPeca(peca, new ChessPosition(coluna, linha).toPosition());
        pecasNoTabuleiro.add(peca);
    }

    private void setupInicial(){
        colocaNovaPeca('a', 1, new Rook(board, Color.BRANCO));
        colocaNovaPeca('b', 1, new Knight(board, Color.BRANCO));
        colocaNovaPeca('c', 1, new Bishop(board, Color.BRANCO));
        colocaNovaPeca('d', 1, new Queen(board, Color.BRANCO));
        colocaNovaPeca('e', 1, new King(board, Color.BRANCO, this));
        colocaNovaPeca('f', 1, new Bishop(board, Color.BRANCO));
        colocaNovaPeca('g', 1, new Knight(board, Color.BRANCO));
        colocaNovaPeca('h', 1, new Rook(board, Color.BRANCO));
        colocaNovaPeca('a', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('b', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('c', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('d', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('e', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('f', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('g', 2, new Pawn(board, Color.BRANCO, this));
        colocaNovaPeca('h', 2, new Pawn(board, Color.BRANCO, this));

        colocaNovaPeca('a', 8, new Rook(board, Color.PRETO));
        colocaNovaPeca('b', 8, new Knight(board, Color.PRETO));
        colocaNovaPeca('c', 8, new Bishop(board, Color.PRETO));
        colocaNovaPeca('d', 8, new Queen(board, Color.PRETO));
        colocaNovaPeca('e', 8, new King(board, Color.PRETO, this));
        colocaNovaPeca('f', 8, new Bishop(board, Color.PRETO));
        colocaNovaPeca('g', 8, new Knight(board, Color.PRETO));
        colocaNovaPeca('h', 8, new Rook(board, Color.PRETO));
        colocaNovaPeca('a', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('b', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('c', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('d', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('e', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('f', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('g', 7, new Pawn(board, Color.PRETO, this));
        colocaNovaPeca('h', 7, new Pawn(board, Color.PRETO, this));
    }
}
