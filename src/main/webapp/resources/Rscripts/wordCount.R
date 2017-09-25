wordCount = function (text, word) {
  #Transforma o texto em um dataframe
  library(dplyr)
  text_df <- data_frame(text = text)
  
  #Separa as palavras
  library(tidytext)
  words <- text_df %>%
    unnest_tokens(word, text)
  
  #Remove as stop_words
  #data(stop_words)
  #palavras <- palavras %>%
  #  anti_join(stop_words)
  
  library(stringr)
  #Conta as palavras
  count <- str_count(words, word)
  
  return (count)
}
