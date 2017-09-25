contadorPalavras = function (texto) {
  #Transforma o texto em um dataframe
  library(dplyr)

  texto_df <- data_frame(text = texto)
  
  #Separa as palavras
  library(tidytext)
  palavras <- texto_df %>%
    unnest_tokens(word, text)
  
  #Remove as stop_words
  data(stop_words)
  palavras <- palavras %>%
    anti_join(stop_words)
  
  #Conta as palavras
  count <- palavras %>%
    count(word, sort = TRUE)
  
  return (count)
}
