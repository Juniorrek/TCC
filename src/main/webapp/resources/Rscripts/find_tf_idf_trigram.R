find_tf_idf_trigram = function (txt_folder) {
  library(readr)
  library(dplyr)
  library(tidyr)
  library(purrr)
  library(tidytext)
  library(ggplot2)
  
  file_names <- list.files(path = txt_folder, pattern = ".txt")
  file_names <- substr(file_names, 1, 23)
  
  trigrams <- data_frame(file = dir(path = txt_folder, pattern = ".txt", full.names = TRUE)) %>%
    mutate(text = gsub('[0-9]+', '', map(file, read_lines))) %>%
    mutate(id = as.factor(file_names), text) %>%
    unnest(text)  %>%
    unnest_tokens(trigram, text, token = "ngrams", n = 3) %>%
    separate(trigram, c("word1", "word2", "word3"), sep = " ") %>%
    filter(!word1 %in% stop_words$word,
           !word2 %in% stop_words$word,
           !word3 %in% stop_words$word) %>%
    unite(trigram, word1, word2, word3, sep = " ") %>%
    select(-file) %>%
    count(id, trigram, sort = TRUE)
  
  trigram_tf_idf <- trigrams %>%
    bind_tf_idf(trigram, id, n) %>%
    arrange(desc(tf_idf))
  
  return (trigram_tf_idf)
}