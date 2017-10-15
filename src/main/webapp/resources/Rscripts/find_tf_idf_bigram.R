find_tf_idf_bigram = function (aa) {
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
  
  
  bigrams <- aa %>%
    unnest(text) %>%
    unnest_tokens(bigram, text, token = "ngrams", n = 2) %>%
    separate(bigram, c("word1", "word2"), sep = " ") %>%
    filter(!word1 %in% stop_words$word,
           !word2 %in% stop_words$word) %>%
    unite(bigram, word1, word2, sep = " ") %>%
    count(id, bigram, sort = TRUE)
  
  total_bigrams <- bigrams %>% 
    group_by(id) %>% 
    summarize(total = sum(n))
  
  bigrams <- left_join(bigrams, total_bigrams)
  
  bigram_tf_idf <- bigrams %>%
    bind_tf_idf(bigram, id, n) %>%
    arrange(desc(tf_idf))

  return (bigram_tf_idf)
}