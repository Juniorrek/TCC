find_tf_idf_trigram = function (aa) {
  library(readr)
  library(dplyr)
  library(tidyr)
  library(purrr)
  library(tidytext)
  library(ggplot2)
  
  aa$abstract <- NULL
  aa$objective <- NULL
  aa$methodology <- NULL
  aa$conclusion <- NULL
  
  trigrams <- aa %>%
    unnest(text) %>%
    unnest_tokens(trigram, text, token = "ngrams", n = 3) %>%
    separate(trigram, c("word1", "word2", "word3"), sep = " ") %>%
    filter(!word1 %in% stop_words$word,
           !word2 %in% stop_words$word,
           !word3 %in% stop_words$word) %>%
    unite(trigram, word1, word2, word3, sep = " ") %>%
    count(id, trigram, sort = TRUE)
  
  trigram_tf_idf <- trigrams %>%
    bind_tf_idf(trigram, id, n) %>%
    arrange(desc(tf_idf))
  
  return (trigram_tf_idf)
}