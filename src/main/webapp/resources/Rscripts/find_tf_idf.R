find_tf_idf = function (txt_folder, aa) {
  library(ggplot2)
  
  aa$abstract <- NULL
  aa$objective <- NULL
  aa$methodology <- NULL
  aa$conclusion <- NULL
  
  word_list <- aa %>%
    unnest_tokens(word, text)
  
  word_list <- word_list %>%
    anti_join(stop_words)
  
  word_list <- word_list %>%
    count(id, word, sort = TRUE) %>%
    ungroup()
  
  total_words <- word_list %>% 
    group_by(id) %>% 
    summarize(total = sum(n))
  
  word_list <- left_join(word_list, total_words)
  
  tf_idf <- word_list %>%
    bind_tf_idf(word, id, n)
  
  #Ordem do maior idf para o menor
  tf_idf %>%
    select(-total) %>%
    arrange(desc(tf_idf))
  
  return (tf_idf)
}