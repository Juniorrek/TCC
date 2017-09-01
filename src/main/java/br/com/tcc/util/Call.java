package br.com.tcc.util;

import br.com.tcc.model.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class Call {
        private final String pdftotext = "C:/Users/Orestes/Desktop/TCC/pdftotext.exe"; //caminho pro pdftotext
              
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
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/extractAbstract.R')"); //MUDEM PARA OS CAMINHOS DE VOCÊS!
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/tidynator.R')");
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/find_tf_idf.R')");
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
                try {
                        connection = new RConnection();
                        connection.eval("library(dplyr)");                       
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/extractAbstract.R')"); //MUDEM PARA OS CAMINHOS DE VOCÊS!
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/tidynator.R')");
                        connection.eval("source('C:/Users/Orestes/Desktop/TCC/R_files/findObjective.R')");
                        List<String> nomes = arquivos(pathorigem);
                        for(String arq: nomes) { //PASSANDO ARQUIVOS PARA PASTA DE ANÁLISE
                            connection.eval("flist = list.files(\"" + pathorigem + "\",\"" + arq + "\", full.names = TRUE)");
                            connection.eval("file.copy(flist,\"" + path + "\")");
                        }
                        connection.eval("xx = extractAbstract(\"" + path + "\",\"" + pdftotext + "\")");
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
                        return termos;
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
}
