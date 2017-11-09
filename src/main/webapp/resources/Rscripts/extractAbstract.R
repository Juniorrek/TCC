extractAbstract = function (pdfFolder, pdfToText) {
  #Lista recebe os nomes dos PDF's
  myfiles <- list.files(path = pdfFolder, pattern = ".pdf",  full.names = TRUE)
  
  #Remoção dos espaços para que o pdftotext.exe funcione
  sapply(myfiles, FUN = function(i) {
    newName <- gsub("[^a-zA-Z\\d_ ]", "", basename(i), perl=TRUE)
    newName <- gsub(" ", "_", newName)
    file.rename(from = i, to =  paste0(dirname(i), "/", newName))
  })
  
  #Atualiza a lista com os nomes agora sem espaços
  myfiles <- list.files(path = pdfFolder, pattern = ".pdf",  full.names = TRUE)
  
  #Transforma os PDFs em .txt utilizando o pdftotext.exe
  lapply(myfiles, function(i) system(paste(pdfToText, paste0('"', i, '"')), wait = TRUE))
  
  #Guarda os nomes dos arquivos txt criados
  mytxtfiles <- list.files(path = pdfFolder, pattern = "txt",  full.names = TRUE)
  #Extrai os abstracts dos arquivos txt
  
  abstracts <- lapply(mytxtfiles, function(i) {
    j <- paste0(scan(i, what = character()), collapse = " ")
    regmatches(j, gregexpr("(?i)(?<=abstract)([\\S\\s]*?)(?=introduction|introdução|keyword|resumo|©)", j, perl=TRUE))
  })
  
  
  #Guarda os abstracts em arquivos separados
  #lapply(1:length(abstracts),  function(i) write.table(abstracts[i], file=paste(mytxtfiles[i], "abstract", "txt", sep="."), quote = FALSE, row.names = FALSE, col.names = FALSE, eol = " " ))
  
  #Retorna a lista de abstracts
  return (abstracts)
}
# Em caso de erro 127, verificar se o pdfToText está dentro de aspas simples e aspas duplas