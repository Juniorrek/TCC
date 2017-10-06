package br.com.tcc.singleton;

import br.com.tcc.context.ServletContextProvider;

public final class Singleton {
    public static final String CONTEXT = ServletContextProvider.getServletContext().getRealPath("/").replace("\\", "/");
    
    //public static final String PDF_TO_TEXT_PATH = "C:/Users/Orestes/Desktop/TCC/pdftotext.exe";
    public static final String PDF_TO_TEXT_PATH = "C:/Program Files/pdftotext/bin64/pdftotext.exe";
    
    /***********\
    *  UPLOADS  *
    \***********/
    //public static final String UPLOAD_DIR = "C:/Users/Orestes/Desktop/TCC/SobAnalise";
    public static final String UPLOAD_DIR = "C:/Users/bruno/Documents/up";
    
    /***********\
    *  DATABASE *
    \***********/
    public static final String DB_URL = "jdbc:mysql://localhost:3306/TCC?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    
    /****************************************************************************\
    * SCRIPTS R *
    \***********/
    
    public static final String EXTRACT_ABSTRACT = CONTEXT + "resources/Rscripts/extractAbstract.R";
    public static final String TIDYNATOR = CONTEXT + "resources/Rscripts/tidynator.R";
    public static final String FIND_TF_IDF = CONTEXT + "resources/Rscripts/find_tf_idf.R";
    public static final String FIND_TF_IDF_BIGRAM = CONTEXT + "resources/Rscripts/find_tf_idf_bigram.R";
    public static final String FIND_TF_IDF_TRIGRAM = CONTEXT + "resources/Rscripts/find_tf_idf_trigram.R";
    public static final String FIND_OBJECTIVE = CONTEXT + "resources/Rscripts/findObjective.R";
    public static final String FIND_SEGMENT = CONTEXT + "resources/Rscripts/findSegment.R";
    public static final String ARTICLES_ANALYSIS = CONTEXT + "resources/Rscripts/articlesAnalysis.R";
    public static final String ARRANGE_BY_RELEVANCY = CONTEXT + "resources/Rscripts/arrangeByRelevancy.R";
    public static final String TO_GROUPS = CONTEXT + "resources/Rscripts/toGroups.R"; 
}
