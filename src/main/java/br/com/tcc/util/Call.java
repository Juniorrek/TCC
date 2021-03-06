package br.com.tcc.util;

import br.com.tcc.dao.ProjetoDao;
import br.com.tcc.model.*;
import br.com.tcc.singleton.Singleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Call {
        private final String pdftotext = Singleton.PDF_TO_TEXT_PATH; //caminho pro pdftotext
        
        public List<Artigo> articlesAnalysis(String pathorigem, Pesquisa p) throws REXPMismatchException, REngineException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            File f = new File(path);
            f.mkdirs();
            try {
                    connection = new RConnection();
                    connection.eval("library(dplyr)"); 
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.TIDYNATOR + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_BIGRAM + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_TRIGRAM + "')");
                    
                    p.setSinonimosObjetivo(p.getProjeto().getSinonimosToR("objetivo"));
                    p.setSinonimosMetodologia(p.getProjeto().getSinonimosToR("metodologia"));
                    p.setSinonimosResultado(p.getProjeto().getSinonimosToR("resultado"));
                    ProjetoDao projetoDao = new ProjetoDao();
                    Pesquisa p2 = projetoDao.carregarPesquisa(p, 1);
                    int analise=0;
                    connection.eval("temp <- gsub(\"\\\\..*\", \"\", list.files(\"" + path + "\" , pattern = \".txt\"))");
                    connection.eval("origem <- gsub(\"\\\\..*\", \"\", list.files(\"" + pathorigem + "\" , pattern = \".pdf\"))");
                    if(connection.eval("length(origem)==0").asString().equals("TRUE")) {
                        projetoDao.excluirPesquisa(p);
                        return null;
                    }
                    if(p2 != null){    
                        connection.eval("origemadapt <- gsub(\" \", \"_\", origem)"); //para adaptação
                        connection.eval("origemadapt <- paste(origemadapt, \"pdf\", sep=\"\")"); //para adaptação
                        if(!connection.eval("setequal(origemadapt, temp)").asString().equals("TRUE")){
                            analise++;
                        }
                    }
                    else{
                        connection.eval("origemadapt <- origem");
                        analise++;
                    }
                    
                    if(analise!=0){ //NOVA ANÁLISE
                        connection.eval("synonyms <- list()");
                        connection.eval("synonyms$objective <- c(" + p.getSinonimosObjetivo() + ")");
                        connection.eval("synonyms$methodology <- c(" + p.getSinonimosMetodologia() + ")");
                        connection.eval("synonyms$conclusion <- c(" + p.getSinonimosResultado() + ")");
                        
                        connection.eval("excluir <- temp[!temp %in% origemadapt]"); //Excluir arquivos que não serão usados na analise
                        connection.eval("excluir <- paste(excluir, \".txt\", sep=\"\")");
                        connection.eval("excluir <- paste(\"" + path + "/\", excluir, sep=\"\")");
                        connection.eval("file.remove(excluir)");
                                                
                        connection.eval("incluir <- which(!origemadapt %in% temp)"); //Arquivos que serão adicionados na analise
                        connection.eval("incluir <- origem[incluir]");
                        connection.eval("incluir <- paste(incluir, \".pdf\", sep=\"\")");
                        String caminhoTemporario = pathorigem + "temp2";
                        connection.eval("incluir <- paste(\"" + pathorigem + "\", incluir, sep=\"\")");
                        File f2 = new File(caminhoTemporario);
                        f2.mkdirs();
                        connection.eval("file.copy(incluir,\"" + caminhoTemporario + "\")");
                        connection.eval("xx <- extractAbstract(\"" + caminhoTemporario + "\",\'\"" + pdftotext + "\"\')");
                        connection.eval("junk <- dir(path = \"" + caminhoTemporario + "\", pattern = \"*pdf$\", full.names = TRUE)");
                        connection.eval("file.remove(junk)");
                        connection.eval("incluir <- list.files(\"" + caminhoTemporario + "\" , pattern = \".txt\")");
                        connection.eval("incluir <- paste(\"" + caminhoTemporario + "/\", incluir, sep=\"\")");
                        connection.eval("file.copy(incluir,\"" + path + "\")");
                        connection.eval("file.remove(incluir)");
                        f2.delete();

                        connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");

                        connection.eval("TFWord <- find_tf_idf(meanVal)");
                        connection.eval("TFBigram <- find_tf_idf_bigram(meanVal)");
                        connection.eval("TFTrigram <- find_tf_idf_trigram(meanVal)");
                        
                        List<Artigo> artigos = new ArrayList();
                        int total = Integer.parseInt(connection.eval("nrow(meanVal)").asString());
                        for (int i=1; i<=total; i++) {
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
                            System.out.println("Artigo " + i + ": " + artigo.getNome());
                        }
                        p.setLista(artigos);
                        projetoDao.salvarPesquisa(p);
                        connection.eval("saveRDS(meanVal, file=\"" + path + "/segmentos.rds\")");
                        return artigos;
                    }
                    connection.eval("meanVal <- readRDS(\"" + path + "/segmentos.rds\")");
                    return p2.getLista();
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
        
        
        public ArrayList<String> graphicTfIdf(String pathorigem, Pesquisa p) throws REXPMismatchException, REngineException, IOException, FileNotFoundException, ClassNotFoundException, SQLException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            ProjetoDao projetoDao = new ProjetoDao();
            p = projetoDao.carregarPesquisa(p, 2);
            System.out.println("entrei no tfidf");
            if(!(p != null)){
                System.out.println("sem pesquisa");
                return null;
            }
            if((p != null && !(p.getLista() != null)) || p.getLista().size()<2){
                System.out.println("lista null ou lista menor q 2");
                return null;
            }
            if(p.getTermosRelevantes().get(0).equals("novo")){
                try {
                    System.out.println("novotfidf");
                    connection = new RConnection();
                    connection.eval("library(dplyr)");
                    connection.eval("library(readr)");
                    connection.eval("library(dplyr)");
                    connection.eval("library(tidyr)");
                    connection.eval("library(purrr)");
                    connection.eval("library(tidytext)");
                    connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_BIGRAM + "')");
                    connection.eval("source('" + Singleton.FIND_TF_IDF_TRIGRAM + "')");
                    
                    connection.eval("meanVal <- readRDS(\"" + path + "/segmentos.rds\")");
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
                    tfs.add(wordTop20.replace("'", ""));
                    tfs.add(bigramTop20);
                    tfs.add(trigramTop20);
                    p.setTermosRelevantes(tfs);
                    projetoDao.editarPesquisa(p);
                    return tfs;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                return null;
            }
            else{
                return (ArrayList)p.getTermosRelevantes();
            }
        }
        
        public List<Artigo> ordenar(String segment, List<Tag> tags, Pesquisa p, String pathorigem) throws REXPMismatchException, IOException, REngineException, SQLException, ClassNotFoundException {
            RConnection connection = null;
            String path = pathorigem + "temp";
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
                    connection.eval("source('" + Singleton.ARRANGE_BY_RELEVANCY + "')");
                    connection.eval("meanVal <- readRDS(\"" + path + "/segmentos.rds\")");
                    connection.eval("ordenado <- arrangeByRelevancy(meanVal, \"" + segment + "\", " + keywords + ")");
                    List<Artigo> artigos = new ArrayList();
                    ProjetoDao projetoDao = new ProjetoDao();
                    p = projetoDao.carregarPesquisa(p, 2);
                    List<Integer> aux= new ArrayList();
                    for (Artigo artigo : p.getLista()) {
                        connection.eval("num <- which(ordenado$id == \"" + artigo.getNome() + "\")");
                        int num = Integer.parseInt(connection.eval("num").asString());
                        aux.add(num);
                    }
                    for(int i: aux){
                        artigos.add(p.getLista().get(i-1));
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
        
        
        public List<Grupo> toGroups(String segmento, int x, Pesquisa p, String pathorigem) throws REXPMismatchException, IOException, REngineException {
            RConnection connection = null;
            String path = pathorigem + "temp";
            try {
                    connection = new RConnection();
                    connection.eval("library(tm)");
                    connection.eval("library(dplyr)");         
                    connection.eval("library(tidytext)");         
                    connection.eval("library(readr)");         
                    connection.eval("library(tidyr)");         
                    connection.eval("library(purrr)");                  
                    connection.eval("source('" + Singleton.TO_GROUPS + "')");
                    connection.eval("meanVal <- readRDS(\"" + path + "/segmentos.rds\")");
                    connection.eval("meanVal2 <- toGroups(meanVal, \"" + segmento + "\", " + x + ")");
                    connection.eval("grupoOrd <- meanVal2[order(meanVal2$id),]");
                    connection.eval("ret <- NULL");
                  
                    List<Grupo> grupos = new ArrayList();
                    List<List<Artigo>> listas = new ArrayList();
                    
                    for (int i=1; i<=x; i++) {
                        List<String> palavras = new ArrayList();
                        connection.eval("aux <- meanVal2[meanVal2$topic==" + i + ", ]");
                        int palavra = Integer.parseInt(connection.eval("length(aux[[1,9]])").asString());
                        for(int j=1;j<=palavra;j++) {
                            palavras.add(connection.eval("aux[[1,9]][" + j + "]").asString());
                        }
                        Grupo grupo = new Grupo();
                        grupo.setKeywords(palavras);
                        grupo.setNumero(i);
                        grupos.add(grupo);
                        listas.add(new ArrayList());
                    }
                  
                    for(Artigo artigo: p.getLista()){
                        connection.eval("grupo <- grupoOrd[grupoOrd$id==\"" + artigo.getNome() + "\",]");
                        int num = Integer.parseInt(connection.eval("grupo$topic").asString());
                        listas.get(num-1).add(artigo);
                    }
                    for(int i=0; i<x; i++){
                        grupos.get(i).setArtigos(listas.get(i));
                    }
                    return grupos;
            } catch (RserveException e) {
                e.printStackTrace();
            }finally{
                connection.close();
            }
            return null;
        }
}
