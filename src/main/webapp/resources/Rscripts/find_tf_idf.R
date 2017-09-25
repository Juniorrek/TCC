find_tf_idf = function (txt_folder) {
  library(ggplot2)
  
  word_list <- tidynator(txt_folder)
  
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