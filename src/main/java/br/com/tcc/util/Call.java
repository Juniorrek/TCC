package br.com.tcc.util;

import br.com.tcc.model.*;
import br.com.tcc.singleton.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Call {
        private final String pdftotext = Singleton.PDF_TO_TEXT_PATH; //caminho pro pdftotext
        
        public List<Artigo> articlesAnalysis(String pathorigem, Projeto projeto) throws REXPMismatchException, REngineException, IOException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            File f = new File(path);
            f.mkdirs();
            try {
                    connection = new RConnection();
                    connection.eval("library(dplyr)"); 
                    connection.eval("library(ggplot2)"); 
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.TIDYNATOR + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_BIGRAM + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_TRIGRAM + "')");
                    List<String> nomes = arquivos(pathorigem);
                    for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                        connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                        connection.eval("file.copy(flist,\"" + path + "\")");
                    }
                    connection.eval("xx <- extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    connection.eval("ret <- NULL");
                    
                    //limpando os pdf e o txt abstract
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+pdf\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+abstract.+\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("synonyms <- list()");
                    connection.eval("synonyms$objective <- c(" + projeto.getSinonimosToR("objetivo") + ")");
                    connection.eval("synonyms$methodology <- c(" + projeto.getSinonimosToR("metodologia") + ")");
                    connection.eval("synonyms$conclusion <- c(" + projeto.getSinonimosToR("resultado") + ")");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\", synonyms)");
                    connection.eval("TFWord <- find_tf_idf(meanVal)");
                    connection.eval("TFBigram <- find_tf_idf_bigram(meanVal)");
                    connection.eval("TFTrigram <- find_tf_idf_trigram(meanVal)");
                    List<Artigo> artigos = new ArrayList();
                    int i = 1;
                    for (String s : nomes) {
                        Artigo artigo = new Artigo();
                        artigo.setNome(connection.eval("meanVal[" + i + ", 2]").asList().at(0).asString());
                        artigo.setResumo(connection.eval("meanVal[" + i + ", 3]").asList().at(0).asString());
                        artigo.setObjetivo(connection.eval("meanVal[" + i + ", 4]").asList().at(0).asString());
                        artigo.setMetodologia(connection.eval("meanVal[" + i + ", 5]").asList().at(0).asString());
                        artigo.setResultado(connection.eval("meanVal[" + i + ", 6]").asList().at(0).asString());
                        
                        connection.eval("rankWord <- TFWord[which(TFWord$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        connection.eval("rankBigram <- TFBigram[which(TFBigram$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        connection.eval("rankTrigram <- TFTrigram[which(TFTrigram$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        
                        String mainWords = "";
                        String mainBigrams = "";
                        String mainTrigrams = "";
                        for(int aa=1;aa<11;aa++) {
                            mainWords+=(connection.eval("rankWord[" + aa + ",2]").asList().at(0).asString());
                            mainWords+= ";" + (connection.eval("rankWord[" + aa + ",3]").asList().at(0).asString()) + ";";
                            
                            mainBigrams+=(connection.eval("rankBigram[" + aa + ",2]").asList().at(0).asString());
                            mainBigrams+= ";" + (connection.eval("rankBigram[" + aa + ",3]").asList().at(0).asString()) + ";";

                            mainTrigrams+=(connection.eval("rankTrigram[" + aa + ",2]").asList().at(0).asString());
                            mainTrigrams+= ";" + (connection.eval("rankTrigram[" + aa + ",3]").asList().at(0).asString()) + ";";
                        }
                        
                        artigo.setMainWords(mainWords);
                        artigo.setMainBigrams(mainBigrams);
                        artigo.setMainTrigrams(mainTrigrams);
                        artigos.add(artigo);
                        i++;
                    }
                    //connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+png\", full.names = TRUE)");
                    //connection.eval("file.remove(junk)");
                    return artigos;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
        
        public ArrayList<String> graphicTfIdf(String pathorigem) throws REXPMismatchException, REngineException, IOException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            File f = new File(path);
            f.mkdirs();
            try {
                    connection = new RConnection();
                    connection.eval("library(dplyr)");
                    connection.eval("library(readr)");
                    connection.eval("library(dplyr)");
                    connection.eval("library(tidyr)");
                    connection.eval("library(purrr)");
                    connection.eval("library(tidytext)");
                    connection.eval("library(ggplot2)"); 
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_BIGRAM + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_TRIGRAM + "')");
                    List<String> nomes = arquivos(pathorigem);
                    for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                        connection.eval("flist <- list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                        connection.eval("file.copy(flist,\"" + path + "\")");
                    }
                    connection.eval("xx <- extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    connection.eval("ret <- NULL");
                    
                    //limpando os pdf e o txt abstract
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+pdf\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+abstract.+\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");
                    connection.eval("wordTop20 <- find_tf_idf(meanVal) %>% top_n(20, tf_idf)");
                    connection.eval("bigramTop20 <- find_tf_idf_bigram(meanVal) %>% top_n(20, tf_idf)");
                    connection.eval("trigramTop20 <- find_tf_idf_trigram(meanVal) %>% top_n(20, tf_idf)");
                    
                    String wordTop20 = "";
                    String bigramTop20 = "";
                    String trigramTop20 = "";
                    for(int i=1;i<21;i++) {
                        wordTop20 += (connection.eval("wordTop20[" + i + ",1]").asList().at(0).asString());
                        wordTop20 += ";" + (connection.eval("wordTop20[" + i + ",2]").asList().at(0).asString());
                        wordTop20 += ";" + (connection.eval("wordTop20[" + i + ",3]").asList().at(0).asString());
                        wordTop20 += ";" + (connection.eval("wordTop20[" + i + ",4]").asList().at(0).asString());
                        wordTop20 += ";" + (connection.eval("wordTop20[" + i + ",7]").asList().at(0).asString()) + ";";
                        
                        bigramTop20 += (connection.eval("bigramTop20[" + i + ",1]").asList().at(0).asString());
                        bigramTop20 += ";" + (connection.eval("bigramTop20[" + i + ",2]").asList().at(0).asString());
                        bigramTop20 += ";" + (connection.eval("bigramTop20[" + i + ",3]").asList().at(0).asString());
                        bigramTop20 += ";" + (connection.eval("bigramTop20[" + i + ",4]").asList().at(0).asString());
                        bigramTop20 += ";" + (connection.eval("bigramTop20[" + i + ",7]").asList().at(0).asString()) + ";";
                        
                        trigramTop20 += (connection.eval("trigramTop20[" + i + ",1]").asList().at(0).asString());
                        trigramTop20 += ";" + (connection.eval("trigramTop20[" + i + ",2]").asList().at(0).asString());
                        trigramTop20 += ";" + (connection.eval("trigramTop20[" + i + ",3]").asList().at(0).asString());
                        trigramTop20 += ";" + (connection.eval("trigramTop20[" + i + ",4]").asList().at(0).asString());
                        trigramTop20 += ";" + (connection.eval("trigramTop20[" + i + ",7]").asList().at(0).asString()) + ";";
                    }
                   
                    ArrayList<String> tfs = new ArrayList<>();
                    tfs.add(wordTop20);
                    tfs.add(bigramTop20);
                    tfs.add(trigramTop20);
                    
                    return tfs;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
        
        public List<Artigo> ordenar(String pathorigem, String segment, List<Tag> tags, Projeto projeto) throws REXPMismatchException, IOException, REngineException {
            RConnection connection = null;
            String path = pathorigem + "/temp";
            File f = new File(path);
            
            String keywords = "c(";
            for (Tag t : tags) {
                keywords += "'" + t.getTag() + "',";
            }
            keywords = keywords.substring(0, keywords.length() - 1);
            keywords += ")";
            
            try {
                    connection = new RConnection();
                    connection.eval("library(dplyr)");
                    connection.eval("library(readr)");
                    connection.eval("library(tidyr)");
                    connection.eval("library(purrr)");
                    connection.eval("library(tidytext)");
                    connection.eval("library(ggplot2)");                   
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.ARRANGE_BY_RELEVANCY + "')");
                    connection.eval("source('" + Singleton.TIDYNATOR + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("synonyms <- list()");
                    connection.eval("synonyms$objective <- c(" + projeto.getSinonimosToR("objetivo") + ")");
                    connection.eval("synonyms$methodology <- c(" + projeto.getSinonimosToR("metodologia") + ")");
                    connection.eval("synonyms$conclusion <- c(" + projeto.getSinonimosToR("resultado") + ")");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\", synonyms)");
                    connection.eval("ranking <- find_tf_idf(meanVal)");
                    connection.eval("ordenado <- arrangeByRelevancy(meanVal, \"" + segment + "\", " + keywords + ")");
                    List<Artigo> artigos = new ArrayList();
                    List<String> nomes = arquivos(pathorigem);
                    int i = 1;
                    for (String s : nomes) {
                        Artigo artigo = new Artigo();
                        artigo.setNome(connection.eval("ordenado[" + i + ", 2]").asList().at(0).asString());
                        artigo.setResumo(connection.eval("ordenado[" + i + ", 3]").asList().at(0).asString());
                        artigo.setObjetivo(connection.eval("ordenado[" + i + ", 4]").asList().at(0).asString());
                        artigo.setMetodologia(connection.eval("ordenado[" + i + ", 5]").asList().at(0).asString());
                        artigo.setResultado(connection.eval("ordenado[" + i + ", 6]").asList().at(0).asString());
                        
                        //talvez puxar o numero e ordenar para garantir
                        
                        List<String> nomesOrigem = arquivos(pathorigem);   
                            for(String b: nomesOrigem){
                                String a = b.replace("_", " ");
                                if(a.length()>=artigo.getNome().length()){
                                    a = a.substring(0, artigo.getNome().length());
                                    if(artigo.getNome().substring(0, artigo.getNome().length()-4).equals(a.substring(0, a.length()-4))){
                                        s = b;
                                        break;
                                    }
                                }
                            }
                        String imagem = path + "/" + s.substring(0,s.length()-4) + ".png";
                        java.nio.file.Path arquivo = Paths.get(imagem);
                        byte[] bytes = Files.readAllBytes(arquivo);
                        byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
                        String base64Encoded = new String(encodeBase64, "UTF-8");
                        artigo.setImgWord(base64Encoded);
                        
                        connection.eval("rankN <- ranking[which(ranking$id=='" + artigo.getNome() + "'),][1:10,] %>% mutate(word = reorder(word, n))");
                        String lista = "";
                        for(int aa=1;aa<=10;aa++){
                            lista+=(connection.eval("rankN[" + aa + ",2]").asList().at(0).asString());
                            lista+= " " + (connection.eval("rankN[" + aa + ",3]").asList().at(0).asString()) + " ";
                        }
                        artigo.setWordcloud(lista);
                        
                        connection.eval("rankWord <- TFWord[which(TFWord$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        connection.eval("rankBigram <- TFBigram[which(TFBigram$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        connection.eval("rankTrigram <- TFTrigram[which(TFTrigram$id=='" + artigo.getNome() + "'),][1:10,] %>% arrange(desc(n))");
                        
                        String mainWords = "";
                        String mainBigrams = "";
                        String mainTrigrams = "";
                        for(int aa=1;aa<11;aa++) {
                            mainWords+=(connection.eval("rankWord[" + aa + ",2]").asList().at(0).asString());
                            mainWords+= ";" + (connection.eval("rankWord[" + aa + ",3]").asList().at(0).asString()) + ";";
                            
                            mainBigrams+=(connection.eval("rankBigram[" + aa + ",2]").asList().at(0).asString());
                            mainBigrams+= ";" + (connection.eval("rankBigram[" + aa + ",3]").asList().at(0).asString()) + ";";

                            mainTrigrams+=(connection.eval("rankTrigram[" + aa + ",2]").asList().at(0).asString());
                            mainTrigrams+= ";" + (connection.eval("rankTrigram[" + aa + ",3]").asList().at(0).asString()) + ";";
                        }
                        
                        artigo.setMainWords(mainWords);
                        artigo.setMainBigrams(mainBigrams);
                        artigo.setMainTrigrams(mainTrigrams);
                        
                        artigos.add(artigo);
                        i++;
                    }
                    return artigos;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
        
        public List<String> arquivos(String path){
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            List<String> arqs = new ArrayList();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".pdf")) {
                     arqs.add(listOfFiles[i].getName());
                } 
            }
            return arqs;
        }
        
        
        public List<Grupo> toGroups(String pathorigem, String segmento, int x, Projeto projeto) throws REXPMismatchException, IOException, REngineException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            File f = new File(path);
            f.mkdirs();
            try {
                    connection = new RConnection();
                    connection.eval("library(tm)");
                    connection.eval("library(dplyr)");         
                    connection.eval("library(tidytext)");         
                    connection.eval("library(readr)");         
                    connection.eval("library(tidyr)");         
                    connection.eval("library(purrr)");                  
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.TO_GROUPS + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("source('" + Singleton.TIDYNATOR + "')");
                    List<String> nomes = arquivos(pathorigem);
                    for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                        connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                        connection.eval("file.copy(flist,\"" + path + "\")");
                    }
                    connection.eval("xx <- extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    connection.eval("synonyms <- list()");
                    connection.eval("synonyms$objective <- c(" + projeto.getSinonimosToR("objetivo") + ")");
                    connection.eval("synonyms$methodology <- c(" + projeto.getSinonimosToR("metodologia") + ")");
                    connection.eval("synonyms$conclusion <- c(" + projeto.getSinonimosToR("resultado") + ")");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\", synonyms)");
                    connection.eval("ranking <- find_tf_idf(meanVal)");
                    
                    //limpando os pdf e o txt abstract
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+pdf\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+abstract.+\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("meanVal <- toGroups(meanVal, \"" + segmento + "\", " + x + ")");
                    connection.eval("ret <- NULL");
                    
                    int topicos = Integer.parseInt(connection.eval("paste(max(meanVal$topic))").asString());
                    List<Grupo> grupos = new ArrayList();
                    
                    for (int i=1; i<=topicos; i++) {
                        List<Artigo> artigos = new ArrayList();
                        List<String> palavras = new ArrayList();
                        connection.eval("aux <- meanVal[which(meanVal$topic==" + i +"),]");
                        int cont = Integer.parseInt(connection.eval("paste(count(aux))").asString());
                        for(int j=1;j<=cont;j++) {
                            Artigo artigo = new Artigo();
                            artigo.setNome(connection.eval("aux[" + j + ", 2]").asList().at(0).asString());
                            artigo.setResumo(connection.eval("aux[" + j + ", 3]").asList().at(0).asList().at(0).asString());
                            artigo.setObjetivo(connection.eval("aux[" + j + ", 4]").asList().at(0).asString());
                            artigo.setMetodologia(connection.eval("aux[" + j + ", 5]").asList().at(0).asString());
                            artigo.setResultado(connection.eval("aux[" + j + ", 6]").asList().at(0).asString());
                            
                            List<String> nomesOrigem = arquivos(pathorigem);   
                            String s="";
                            for(String b: nomesOrigem){
                                String a = b.replace("_", " ");
                                if(a.length()>=artigo.getNome().length()){
                                    a = a.substring(0, artigo.getNome().length());
                                    if(artigo.getNome().substring(0, artigo.getNome().length()-4).equals(a.substring(0, a.length()-4))){
                                        s = b;
                                        break;
                                    }
                                }
                            }
                            String imagem = path + "/" + s.substring(0,s.length()-4) + ".png";
                            java.nio.file.Path arquivo = Paths.get(imagem);
                            byte[] bytes = Files.readAllBytes(arquivo);
                            byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
                            String base64Encoded = new String(encodeBase64, "UTF-8");
                            artigo.setImgWord(base64Encoded);
                            connection.eval("rankN <- ranking[which(ranking$id=='" + artigo.getNome() + "'),][1:10,] %>% mutate(word = reorder(word, n))");
                            String lista = "";
                            for(int aa=1;aa<=10;aa++){
                                lista+=(connection.eval("rankN[" + aa + ",2]").asList().at(0).asString());
                                lista+= " " + (connection.eval("rankN[" + aa + ",3]").asList().at(0).asString()) + " ";
                            }
                            artigo.setWordcloud(lista);
                            artigos.add(artigo);
                        }
                        int palavra = Integer.parseInt(connection.eval("length(aux[[1,9]])").asString());
                        //System.out.println(palavra);
                        for(int j=1;j<=palavra;j++) {
                            //System.out.println(connection.eval("aux[[1,9]][" + j + "]").asString());
                            palavras.add(connection.eval("aux[[1,9]][" + j + "]").asString());
                        }
                        Grupo grupo = new Grupo();
                        grupo.setArtigos(artigos);
                        grupo.setKeywords(palavras);
                        grupo.setNumero(i);
                        grupos.add(grupo);
                    }
                    f.delete();
                    return grupos;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            f.delete();
            return null;
        }
}
