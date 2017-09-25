package br.com.tcc.util;

import br.com.tcc.model.*;
import br.com.tcc.singleton.Singleton;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;

public class Call {
        private final String pdftotext = Singleton.PDF_TO_TEXT_PATH; //caminho pro pdftotext
              
        public List<Texto> soloArtigo(String nome, String caminho) throws REXPMismatchException { //extrair data_frame com pdf upado
	RConnection connection = null;
                try {
                        connection = new RConnection();
                        connection.eval("library(pdftools)");
                        connection.eval("library(tidytext)");
                        connection.eval("library(dplyr)");
                        connection.eval("meanVal=pdf_text(\"" + caminho + "/" + nome + "\")");
                        connection.eval("meanVal=data_frame(text = meanVal)");
                        connection.eval("meanVal=meanVal %>% unnest_tokens(word, text)");
                        connection.eval("meanVal=meanVal %>% count(word, sort=TRUE)");
                        connection.eval("contador=paste(count(meanVal))");
                        int linha = Integer.parseInt(connection.eval("contador").asString());                        
                        List<Texto> termos = new ArrayList();
                        for(int i=1;i<=linha;i++){
                                Texto a = new Texto();
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",1])");
                                a.setWord(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",2])");
                                a.setQuant(connection.eval("aux").asString());
                                termos.add(a);
                        }
                        return termos;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                return null;
        }
        
        
        public List<Texto> teste5(List<String> nomes, String caminho) throws REXPMismatchException { //extrair data_frame com mais de um pdf upado
	RConnection connection = null;
                try {
                        connection = new RConnection();
                        connection.eval("library(pdftools)");
                        connection.eval("library(tidytext)");
                        connection.eval("library(dplyr)");
                        int j=1;
                        for(String nome: nomes){
                            if(j==1){
                                connection.eval("meanVal=pdf_text(\"" + caminho + "/" + nome + "\")");
                                connection.eval("meanVal=data_frame(artigo = \"" + nome + "\", text = meanVal)");
                                j++;
                            }
                            else{
                                connection.eval("aux=pdf_text(\"" + caminho + "/" + nome + "\")");
                                connection.eval("aux=data_frame(artigo = \"" + nome + "\", text = aux)");
                                connection.eval("meanVal = full_join(meanVal, aux)");
                            }
                        }
                        connection.eval("meanVal=meanVal %>% unnest_tokens(word, text)");
                        connection.eval("meanVal=meanVal %>% count(word, sort=TRUE)");
                        connection.eval("contador=paste(count(meanVal))");
                        int linha = Integer.parseInt(connection.eval("contador").asString());                        
                        List<Texto> termos = new ArrayList();
                        for(int i=1;i<=linha;i++){
                                Texto a = new Texto();
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",1])");
                                a.setWord(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",2])");
                                a.setQuant(connection.eval("aux").asString());
                                termos.add(a);
                        }
                        return termos;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                return null;
        }

        
        
        
        public List<Texto> manyArtigo(String[] nomes, String caminho) throws REXPMismatchException { //tf-idf + nome
	RConnection connection = null;
                try {
                        connection = new RConnection();
                        connection.eval("library(pdftools)");
                        connection.eval("library(tidytext)");
                        connection.eval("library(dplyr)");
                        int j=1;
                        for(String nome: nomes){
                            if(j==1){
                                connection.eval("meanVal=pdf_text(\"" + caminho + "/" + nome + "\")");
                                connection.eval("meanVal=data_frame(artigo = \"" + nome + "\", text = meanVal)");
                                j++;
                            }
                            else{
                                connection.eval("aux=pdf_text(\"" + caminho + "/" + nome + "\")");
                                connection.eval("aux=data_frame(artigo = \"" + nome + "\", text = aux)");
                                connection.eval("meanVal = full_join(meanVal, aux)");
                            }
                        }
                        connection.eval("meanVal=meanVal %>% unnest_tokens(word, text) %>% count(artigo, word, sort=TRUE) %>% ungroup()");
                        connection.eval("total = meanVal %>% group_by(artigo) %>% summarize(total = sum(n))");
                        connection.eval("meanVal = left_join(meanVal, total)");
                        connection.eval("meanVal = meanVal %>% bind_tf_idf(word, artigo, n)");
                        connection.eval("contador=paste(count(meanVal))");
                        int linha = Integer.parseInt(connection.eval("contador").asString());                        
                        List<Texto> termos = new ArrayList();
                        for(int i=1;i<=linha;i++){
                                Texto a = new Texto();
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",1])");
                                a.setArtigo(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",2])");
                                a.setWord(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",3])");
                                a.setQuant(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",5])");
                                a.setTf(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",6])");
                                a.setIdf(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",7])");
                                a.setTfidf(connection.eval("aux").asString());
                                termos.add(a);
                        }
                        return termos;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                return null;
        }
        
        
        
        public List<Texto> abstractTfIdf(String pathorigem) throws REXPMismatchException {
                RConnection connection = null;
                String path = pathorigem + "/temp";
                new File(path).mkdirs();
                try {
                        connection = new RConnection();
                        connection.eval("library(tidytext)");
                        connection.eval("library(dplyr)");                       
                        connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')"); //MUDEM PARA OS CAMINHOS DE VOCÊS!
                        connection.eval("source('" + Singleton.TIDYNATOR + "')");
                        connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                        List<String> nomes = arquivos(pathorigem);
                        for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                            connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                            connection.eval("file.copy(flist,\"" + path + "\")");
                        }
                        connection.eval("xx = extractAbstract(\"" + path + "\",\"" + pdftotext + "\")");
                        connection.eval("meanVal = find_tf_idf(\"" + path + "\")");
                        connection.eval("meanVal = meanVal %>% arrange(desc(tf_idf))");
                        connection.eval("do.call(file.remove, list(list.files(\"" + path + "\", full.names = TRUE)))"); //TIRANDO TUDO DA PASTA DE ANÁLISE PRA NÃO OCORRER ERROS
                        connection.eval("contador=paste(count(meanVal))");
                        int linha = Integer.parseInt(connection.eval("contador").asString());                        
                        List<Texto> termos = new ArrayList();
                        for(int i=1;i<=linha;i++){
                                Texto a = new Texto();
                                connection.eval("aux=as.character(meanVal[[" + Integer.toString(i) + ",1]])");
                                a.setArtigo(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",2])");
                                a.setWord(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",3])");
                                a.setQuant(connection.eval("aux").asString());
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",5])");
                                a.setTf(String.format("%.4f", Double.parseDouble(connection.eval("aux").asString())));
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",6])");
                                a.setIdf(String.format("%.4f", Double.parseDouble(connection.eval("aux").asString())));
                                connection.eval("aux=paste(meanVal[" + Integer.toString(i) + ",7])");
                                a.setTfidf(String.format("%.4f", Double.parseDouble(connection.eval("aux").asString())));
                                termos.add(a);
                        }
                        return termos;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                return null;
        }
        
        public List<Artigo> abstractObjective(String pathorigem) throws REXPMismatchException {
                RConnection connection = null;
                String path = pathorigem + "/temp";
                File f = new File(path);
                f.mkdirs();
                try {
                        connection = new RConnection();
                        connection.eval("library(dplyr)");                       
                        connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')"); //MUDEM PARA OS CAMINHOS DE VOCÊS!
                        connection.eval("source('" + Singleton.TIDYNATOR + "')");
                        connection.eval("source('" + Singleton.FIND_TF_IDF + "')");
                        connection.eval("source('" + Singleton.FIND_OBJECTIVE + "')");
                        List<String> nomes = arquivos(pathorigem);
                        for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                            connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                            connection.eval("file.copy(flist,\"" + path + "\")");
                        }
                        connection.eval("xx = extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                        connection.eval("meanVal = findObjective(xx)");
                        connection.eval("do.call(file.remove, list(list.files(\"" + path + "\", full.names = TRUE)))"); //TIRANDO TUDO DA PASTA DE ANÁLISE PRA NÃO OCORRER ERROS
                        List<Artigo> termos = new ArrayList();
                        int i=1;
                        for(String arq: nomes){
                                Artigo a = new Artigo();
                                a.setNome(arq);
                                connection.eval("aux=paste(meanVal[" + i + "])");
                                i++;
                                a.setObjetivo(connection.eval("aux").asString());
                                termos.add(a);
                        }
                        f.delete();
                        return termos;
                } catch (RserveException e) {
                    e.printStackTrace();
                }finally{
                    connection.close();
                }
                f.delete();
                return null;
        }
        
        public List<Artigo> articlesAnalysis(String pathorigem) throws REXPMismatchException {
            RConnection connection = null;
            String path = pathorigem + "/temp";
            File f = new File(path);
            f.mkdirs();
            try {
                    connection = new RConnection();
                    connection.eval("library(dplyr)"); 
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    List<String> nomes = arquivos(pathorigem);
                    for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                        connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                        connection.eval("file.copy(flist,\"" + path + "\")");
                    }
                    connection.eval("xx = extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    
                    //limpando os pdf e o txt abstract
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+pdf\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("junk <- dir(path = \"" + path + "\", pattern = \".+abstract.+\", full.names = TRUE)");
                    connection.eval("file.remove(junk)");
                    connection.eval("meanVal = articlesAnalysis(\"" + path + "\")");
                    List<Artigo> artigos = new ArrayList();
                    int i = 1;
                    for (String s : nomes) {
                        Artigo artigo = new Artigo();
                        artigo.setNome(connection.eval("meanVal[" + i + ", 2]").asList().at(0).asString());
                        artigo.setResumo(connection.eval("meanVal[" + i + ", 3]").asList().at(0).asList().at(0).asString());
                        artigo.setObjetivo(connection.eval("meanVal[" + i + ", 4]").asList().at(0).asString());
                        artigo.setMetodologia(connection.eval("meanVal[" + i + ", 5]").asList().at(0).asString());
                        artigo.setResultado(connection.eval("meanVal[" + i + ", 6]").asList().at(0).asString());
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
        
        public List<Artigo> ordenar(String pathorigem, String segment, List<Tag> tags) throws REXPMismatchException {
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
                    connection.eval("source('" + Singleton.EXTRACT_ABSTRACT + "')");
                    connection.eval("source('" + Singleton.FIND_SEGMENT + "')");
                    connection.eval("source('" + Singleton.ARTICLES_ANALYSIS + "')");
                    connection.eval("source('" + Singleton.ARRANGE_BY_RELEVANCY + "')");
                    connection.eval("a <- 2 + 3");
                    connection.eval("meanVal <- articlesAnalysis(\"" + path + "\")");
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
                        artigos.add(artigo);
                        //talvez puxar o numero e ordenar para garantir
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
        
        
        public List<Grupo> toGroups(String pathorigem, String segmento, int x) throws REXPMismatchException {
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
                    List<String> nomes = arquivos(pathorigem);
                    for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                        connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                        connection.eval("file.copy(flist,\"" + path + "\")");
                    }
                    connection.eval("xx = extractAbstract(\"" + path + "\",\'\"" + pdftotext + "\"\')");
                    
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
                        List<String> nome = new ArrayList();
                        List<String> palavras = new ArrayList();
                        connection.eval("aux = meanVal[which(meanVal$topic==" + i +"),]");
                        int cont = Integer.parseInt(connection.eval("paste(count(aux))").asString());
                        for(int j=1;j<=cont;j++){
                            nome.add(connection.eval("aux[" + j + ", 1]").asList().at(0).asString());
                        }
                        int palavra = Integer.parseInt(connection.eval("length(aux[[1,4]])").asString());
                        for(int j=1;j<=palavra;j++){
                            palavras.add(connection.eval("aux[[1,4]][" + j + "]").asString());
                        }
                        Grupo grupo = new Grupo();
                        grupo.setArtigos(nome);
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
