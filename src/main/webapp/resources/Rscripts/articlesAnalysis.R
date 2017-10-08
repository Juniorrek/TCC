
articlesAnalysis = function (txt_folder, synonyms = NULL) {
  library(dplyr)
  library(purrr)
  library(readr)
  library(stringi)
  
  file_names <- list.files(path = txt_folder, pattern = ".txt")
  file_names <- substr(file_names, 1, 50)
  
  articles <- data_frame(file = dir(path = txt_folder, pattern = ".txt", full.names = TRUE)) %>%
    mutate(text = unlist(gsub('[0-9]+', '', map(file, read_lines)))) %>%
    mutate(id = as.factor( gsub("_", " ", file_names)), text) #%>%

  articles$file <- NULL
  
  mytxtfiles <- list.files(path = txt_folder, pattern = ".txt",  full.names = TRUE)
  
  abstracts <- lapply(mytxtfiles, function(i) {
    j <- paste0(scan(i, what = character()), collapse = " ")
    abstract <- unlist(regmatches(j, gregexpr("(?i)(?<=abstract)([\\S\\s]*?)(?=introduction|introdução|keyword|resumo|(?:\r*\n){2}|©)", j, ignore.case = TRUE, perl=TRUE)))
    #abstract <- paste( unlist(regmatches(j, gregexpr("(?i)(?<=[\\n]abstract)([\\S\\s]*?)(?=introduction|introdução|keyword|resumo|(?:\r*\n){2}|©)", j, ignore.case = TRUE, perl=TRUE))), collapse = '')
    ifelse(!is.na(abstract[1]) , abstract[1], "Abstract not found")
  })
  
  
  articles$abstract <- abstracts
  articles$objective <- findSegment(abstracts, "objective", synonyms$objective)
  articles$methodology <- findSegment(abstracts, "methodology", synonyms$methodology)
  articles$conclusion <- findSegment(abstracts, "conclusion", synonyms$conclusion)


  return (articles)
}