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
        
        public List<Artigo> articlesAnalysis(String pathorigem) throws REXPMismatchException, REngineException, IOException {
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
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");
                    connection.eval("TFWord <- find_tf_idf(meanVal)");
                    connection.eval("TFBigram <- find_tf_idf_bigram(meanVal)");
                    connection.eval("TFTrigram <- find_tf_idf_trigram(meanVal)");
                    List<Artigo> artigos = new ArrayList();
                    int i = 1;
                    for (String s : nomes) {
                        Artigo artigo = new Artigo();
                        artigo.setNome(connection.eval("meanVal[" + i + ", 2]").asList().at(0).asString());
                        artigo.setResumo(connection.eval("meanVal[" + i + ", 3]").asList().at(0).asList().at(0).asString());
                        artigo.setObjetivo(connection.eval("meanVal[" + i + ", 4]").asList().at(0).asString());
                        artigo.setMetodologia(connection.eval("meanVal[" + i + ", 5]").asList().at(0).asString());
                        artigo.setResultado(connection.eval("meanVal[" + i + ", 6]").asList().at(0).asString());
                        
                        String imagemWord = path + "/" + s.substring(0,s.length()-4) + ".png";
                        connection.eval("png(\"" + imagemWord + "\")");
                        connection.eval("rankWord <- TFWord[which(TFWord$id=='" + artigo.getNome() + "'),][1:10,] %>% mutate(word = reorder(word, n))");
                        connection.eval("print(ggplot(data=rankWord, aes(word, n)) + geom_col() + xlab(NULL) + coord_flip())");
                        connection.eval("dev.off()");
                        java.nio.file.Path arquivo = Paths.get(imagemWord);
                        byte[] bytes = Files.readAllBytes(arquivo);
                        byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
                        String base64Encoded = new String(encodeBase64, "UTF-8");
                        artigo.setImgWord(base64Encoded);
                        
                        String imagemBigram = path + "/" + s.substring(0,s.length()-4) + "Bigram.png";
                        connection.eval("png(\"" + imagemBigram + "\")");
                        connection.eval("rankBigram <- TFBigram[which(TFBigram$id=='" + artigo.getNome() + "'),][1:10,] %>% mutate(bigram = reorder(bigram, n))");
                        connection.eval("print(ggplot(data=rankBigram, aes(bigram, n)) + geom_col() + xlab(NULL) + coord_flip())");
                        connection.eval("dev.off()");
                        java.nio.file.Path arquivoBigram = Paths.get(imagemBigram);
                        byte[] bytesBigram = Files.readAllBytes(arquivoBigram);
                        byte[] encodeBase64Bigram = Base64.getEncoder().encode(bytesBigram);
                        String base64EncodedBigram = new String(encodeBase64Bigram, "UTF-8");
                        artigo.setImgBigram(base64EncodedBigram);
                        
                        String imagemTrigram = path + "/" + s.substring(0,s.length()-4) + "Trigram.png";
                        connection.eval("png(\"" + imagemTrigram + "\")");
                        connection.eval("rankTrigram <- TFTrigram[which(TFTrigram$id=='" + artigo.getNome() + "'),][1:10,] %>% mutate(trigram = reorder(trigram, n))");
                        connection.eval("print(ggplot(data=rankTrigram, aes(trigram, n)) + geom_col() + xlab(NULL) + coord_flip())");
                        connection.eval("dev.off()");
                        java.nio.file.Path arquivoTrigram = Paths.get(imagemTrigram);
                        byte[] bytesTrigram = Files.readAllBytes(arquivoTrigram);
                        byte[] encodeBase64Trigram = Base64.getEncoder().encode(bytesTrigram);
                        String base64EncodedTrigram = new String(encodeBase64Trigram, "UTF-8");
                        artigo.setImgTrigram(base64EncodedTrigram);
                        
                        String lista = "";
                        for(int aa=1;aa<=10;aa++){
                            lista+=(connection.eval("rankWord[" + aa + ",2]").asList().at(0).asString());
                            lista+= " " + (connection.eval("rankWord[" + aa + ",3]").asList().at(0).asString()) + " ";
                        }
                        artigo.setWordcloud(lista);
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
        
        public String graphicTfIdf(String pathorigem) throws REXPMismatchException, REngineException, IOException {
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
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
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
                    connection.eval("ranking <- find_tf_idf(meanVal)");
                    
                    String imagem = path + "/tfidf.png";
                    connection.eval("png(\"" + imagem + "\")");
                    connection.eval("ranking <- ranking %>% arrange(desc(tf_idf)) %>% mutate(word = factor(word, levels = rev(unique(word)))) %>% top_n(20)");
                    connection.eval("grafico <- ggplot(data=ranking, aes(word, tf_idf, fill = id)) + geom_col() + labs(x = NULL, y = \"tf-idf\") + coord_flip()");
                    connection.eval("print(grafico + scale_fill_discrete(name = \"Artigos\"))");
                    connection.eval("dev.off()");
                    java.nio.file.Path arquivo = Paths.get(imagem);
                    byte[] bytes = Files.readAllBytes(arquivo);
                    byte[] encodeBase64 = Base64.getEncoder().encode(bytes);
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    return base64Encoded;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
        
        public List<Artigo> ordenar(String pathorigem, String segment, List<Tag> tags) throws REXPMismatchException, IOException, REngineException {
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
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");
                    connection.eval("ranking <- find_tf_idf(\"" + path + "\",meanVal)");
                    connection.eval("ordenado <- arrangeByRelevancy(meanVal, \"" + segment + "\", " + keywords + ")");
                    List<Artigo> artigos = new ArrayList();
                    List<String> nomes = arquivos(pathorigem);
                    int i = 1;
                    for (String s : nomes) {
                        Artigo artigo = new Artigo();
                        artigo.setNome(connection.eval("ordenado[" + i + ", 2]").asList().at(0).asString());
                        artigo.setResumo(connection.eval("ordenado[" + i + ", 3]").asList().at(0).asList().at(0).asString());
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
        
        
        public List<Grupo> toGroups(String pathorigem, String segmento, int x) throws REXPMismatchException, IOException, REngineException {
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
                    connection.eval("xx = extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    connection.eval("ranking = find_tf_idf(\"" + path + "\")");
                    
                    //limpando os pdf e o txt abstract
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+pdf\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+abstract.+\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");
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
