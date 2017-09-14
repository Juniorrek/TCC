articlesAnalysis = function (txt_folder) {
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
    abstract <- paste( unlist(regmatches(j, gregexpr("(?i)(?<=abstract)([\\S\\s]*?)(?=introduction|introdução|keyword|resumo)", j, perl=TRUE))), collapse = '')
    ifelse(stri_length(abstract) > 0, abstract, "Abstract not found")
  })
  
  
  articles$abstract <- abstracts
  articles$objective <- findSegment(abstracts, "objective")
  articles$methodology <- findSegment(abstracts, "methodology")
  articles$conclusion <- findSegment(abstracts, "conclusion")

  return (articles)
}
