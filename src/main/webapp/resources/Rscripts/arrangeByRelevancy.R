index2List = function(sentences, indexes, segment) {
  row <- 0
  list <- c()
  for (index in indexes) {
    row <- row + 1
    list[row] <- ifelse(length(index) > 0, paste(unlist(sentences[[row]][index]), collapse=' '), paste0("Keywords not found in ", segment, sep=" "))
  }
  return(list)
}

#analizedArticles = retorno da funcao articlesAnalysis
#segment = {text, abstract, objective, methodology, conclusion}
#keywords = vetor de palavras
arrangeByRelevancy = function (analizedArticles, segment, keywords) {
  library(dplyr)
  library(tidyr)
  library(tidytext)
  keywordsRegex <- paste(tolower(keywords),collapse="|") 
  
  id <- analizedArticles$id
  segment <- get(segment, analizedArticles)
  
  words_count <- data_frame(id, segment) %>%
    unnest(segment) %>%
    unnest_tokens(word, segment) %>%
    anti_join(stop_words) %>%
    count(id, word, sort = TRUE) %>%
    ungroup()
  
  words_total <- words_count %>% 
    group_by(id) %>% 
    summarize(total = sum(n))

  words_filtered <-  filter(left_join(words_count, words_total), grepl(keywordsRegex, word))
  
  words_filtered_summ <- words_filtered %>%
    group_by(id) %>%
    mutate(n = sum(n))
  
  tf <- words_filtered %>%
    group_by(id) %>%
    mutate(n_sum = sum(n)) %>%
    mutate(tf = n_sum/total) %>%
    group_by(id) %>%
    summarize(rel = mean(tf))
  
  tf <- left_join(analizedArticles, tf) %>%
    arrange(desc(rel))
    
  return (tf)
}
