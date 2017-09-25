find_tf_idf_bigram = function (txt_folder) {
  library(readr)
  library(dplyr)
  library(tidyr)
  library(purrr)
  library(tidytext)
  library(ggplot2)
  
  file_names <- list.files(path = txt_folder, pattern = ".txt")
  file_names <- substr(file_names, 1, 50)
  
  bigrams <- data_frame(file = dir(path = txt_folder, pattern = ".txt", full.names = TRUE)) %>%
    mutate(text = gsub('[0-9]+', '', map(file, read_lines))) %>%
    mutate(id = as.factor(file_names), text) %>%
    unnest(text)  %>%
    unnest_tokens(bigram, text, token = "ngrams", n = 2) %>%
    separate(bigram, c("word1", "word2"), sep = " ") %>%
    filter(!word1 %in% stop_words$word,
           !word2 %in% stop_words$word) %>%
    unite(bigram, word1, word2, sep = " ") %>%
    select(-file) %>%
    count(id, bigram, sort = TRUE)
  
  bigram_tf_idf <- bigrams %>%
    bind_tf_idf(bigram, id, n) %>%
    arrange(desc(tf_idf))

  return (bigram_tf_idf)
}