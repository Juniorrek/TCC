package br.com.tcc.model;

public class Artigo implements java.io.Serializable{
    private String objetivo, metodologia, resultado, resumo, nome, img, wordcloud;

   
    public Artigo() {
    }

    public String getWordcloud() {
        return wordcloud;
    }

    public void setWordcloud(String wordcloud) {
        this.wordcloud = wordcloud;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
        
        
}
