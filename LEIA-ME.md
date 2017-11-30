# Artemis: Software para Análise de Artigos
Artemis: Software para Análise de Artigos

### Dependências
* JRE 1.8.0
* JDK 1.8.0
* Node.js 6.11.0
* Netbeans 8.2
* Apache Tomcat 8.0.27
* Apache Maven 3.5.2
* Mysql 5.7
* R 3.4.2
* Rstudio 1.1 (opcional)
* Xpdf reader
  * Windows (https://github.com/adrianpietka/pdfiv/tree/master/bin/xpdfbin-win-3.04)
  * Linux (https://github.com/kusmierz/xpdfbin-amd64)

OBS: Pode ser utilizado qualquer outra IDE e servidor de sua preferência.

### Instalação
0 - Instale o JRE, JDK, Node.js, Netbeans, MySql, R, Rstudio.

1 - Abra no Netbeans -> Tools -> Options -> Java -> Maven -> e em maven home selecione a pasta extraida do Maven.

2 - Configure o servidor Apache Tomcat na IDE.

4 - Abra o terminal e navegue até a pasta do projeto.

5 - Execute os seguintes comandos um de cada vez e em ordem:

`npm install -g yarn`

`yarn install`

6 - Abra o servidor Mysql e execute o script SQL que está localizado em: src/main/sql.sql

7 - Deixe o banco ligado.

8 - Abra o Rstudio e execute os seguinte comandos um de cada vez e em ordem:

`install.packages('tidytext')`

`install.packages('dplyr')`

`install.packages('readr')`

`install.packages('tidyr')`

`install.packages('ggplot2')`

`install.packages('tm')`

`install.packages('topicmodels')`

`install.packages('Rserve')`

`library('Rserve')`

`Rserve()`

9 - Deixe o Rstudio ligado.

10 - Abra a classe Singleton do projeto, que está localizada em: src/main/java/br/com/tcc/singleton/Singleton.java/br/com/tcc/singleton/Singleton

11 - Configure as seguintes variaveis e salve a classe:
* 11.1 - PDF_TO_TEXT_PATH = Caminho do arquivo pdftotext (Xpdf) ex: `public static final String PDF_TO_TEXT_PATH = "C:/Program Files/xpdfbin-win-3.04/bin64/pdftotext.exe";`
* 11.2 - UPLOAD_DIR = Caminho onde os artigos ficarão armazenados ex: `public static final String UPLOAD_DIR = "C:/Sistema_TCC/arquivos/artigos";`
* 11.3 - DB_URL = URL do banco de dados ex: `public static final String DB_URL = "jdbc:mysql://localhost:3306/TCC?useSSL=false";`
* 11.4 - DB_USER = Usuario do banco de dados ex: `public static final String DB_USER = "root"`
* 11.5 - DB_PASSWORD = Senha do banco de dados ex: `public static final String DB_PASSWORD = "admin";`

12 - Clique em 'clean and build' no Netbeans

13 - Clique em 'executar projeto'.