#analizedArticles = retorno da funcao articlesAnalysis
#segment = pelo que o algoritmo deve agrupar (text, abstract, objective, methodology, conclusion)
#qntd = quantidade de grupos a serem formados
toGroups = function(analizedArticles, segment, qntd, primaryGroup = TRUE) {
  library(tm)
  library(topicmodels)
  library(dplyr)
  library(tidytext)
  library(readr)
  library(tidyr)
  library(purrr)
  
  id <- analizedArticles$id
  segment <- get(segment, analizedArticles)
  
  words_count <- data_frame(id, segment) %>%
    unnest(segment) %>%
    unnest_tokens(word, segment) %>%
    anti_join(stop_words) %>%
    count(id, word, sort = TRUE) %>%
    ungroup()
  
  dtm = words_count %>%
    cast_dtm(id, word, n)
  
  groups <- LDA(dtm, k = qntd, control = list(seed = 1234))
  
  topics_gamma <- tidy(groups, matrix = "gamma")

  groups_gamma <- topics_gamma %>%
    separate(document, c("id"), sep = ".txt", convert = TRUE) %>%
    arrange(id, desc(gamma))
  
  if (primaryGroup) {
    groups_gamma$id <- as.factor(groups_gamma$id)
    groups_gamma <- do.call(rbind, lapply(split(groups_gamma,groups_gamma$id), function(x) {return(x[which.max(x$gamma),])}))
  }

  #For beta(token)
  tokens_relevancy <- tidy(groups, matrix = "beta") %>%
    arrange(topic, desc(beta))
  
  key_words <- tokens_relevancy[order(tokens_relevancy$beta), ]
  key_words <- by(key_words, key_words["topic"], tail, n=5)
  key_words <- Reduce(rbind, key_words) %>%
    arrange(topic, desc(beta)) %>%
    group_by(topic) %>%
    summarise(key_words = list(unique(term)))
  
  topics <- left_join(groups_gamma, key_words) %>%
    arrange(topic)
  
  return(topics)
  
  beta_spread <- topics %>%
    mutate(topic = paste0("topic", topic)) %>%
    spread(topic, beta) %>%
    filter(topic1 > .001 | topic2 > .001) %>%
    mutate(log_ratio = log2(topic2 / topic1))
  
  ##PLOTS
  ap_top_terms <- topics %>%
    group_by(topic) %>%
    top_n(10, beta) %>%
    ungroup() %>%
    arrange(topic, -beta)
  
  ap_top_terms %>%
    mutate(term = reorder(term, beta)) %>%
    ggplot(aes(term, beta, fill = factor(topic))) +
    geom_col(show.legend = FALSE) +
    facet_wrap(~ topic, scales = "free") +
    coord_flip()
  
  gamma_plot %>%
    mutate(id = reorder(id, gamma * topic)) %>%
    ggplot(aes(factor(topic), gamma)) +
    geom_boxplot() +
    facet_wrap(~ id)
  
  #####
  plot <-  groups %>%
    arrange(desc(gamma)) %>%
    mutate(topic = factor(topic, levels = rev(unique(topic))))
  
  plot %>% 
    group_by(topic) %>%
    filter(gamma > .09) %>%
    ungroup %>%
    ggplot(aes(id, gamma, fill = topic)) +
    geom_col(show.legend = FALSE) +
    labs(x = NULL, y = "tf-idf") +
    facet_wrap(~topic, ncol = 2, scales = "free") +
    coord_flip()

}