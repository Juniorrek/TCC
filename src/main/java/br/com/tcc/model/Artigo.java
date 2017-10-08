package br.com.tcc.model;

public class Artigo implements java.io.Serializable {
    private int id;
    private String objetivo, metodologia, resultado, resumo, nome, imgWord, imgBigram, imgTrigram, wordcloud, filepath;

   
    public Artigo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getWordcloud() {
        return wordcloud;
    }

    public void setWordcloud(String wordcloud) {
        this.wordcloud = wordcloud;
    }

    public String getImgWord() {
        return imgWord;
    }

    public void setImgWord(String imgWord) {
        this.imgWord = imgWord;
    }
    
    public String getImgBigram() {
        return imgBigram;
    }

    public void setImgBigram(String imgBigram) {
        this.imgBigram = imgBigram;
    }
    
    public String getImgTrigram() {
        return imgTrigram;
    }

    public void setImgTrigram(String imgTrigram) {
        this.imgTrigram = imgTrigram;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
