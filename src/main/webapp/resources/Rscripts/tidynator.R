tidynator = function (txt_folder) {
    library(readr)
    library(dplyr)
    library(tidyr)
    library(purrr)
    library(tidytext)
    
    #prepara nome dos arquivos simplificados
    file_names <- list.files(path = txt_folder, pattern = ".txt")
    file_names <- substr(file_names, 1, 50)
    
    #prepara o dataframe com os arquivos e seus respectivos textos
    abstract_tidy <- data_frame(file = dir(path = txt_folder, pattern = ".txt", full.names = TRUE)) %>%
        mutate(text = gsub('[0-9]+', '', map(file, read_lines))) %>%
        mutate(id = as.factor(file_names), text) %>% #basename(file)
        unnest(text)
    
    #abstract_plot <- data_frame(file = dir(path = txt_folder, pattern = "abstract.txt", full.names = TRUE)) %>%
    #  mutate(text = map(file, read_lines)) %>%
    #  mutate(id = factor(id, levels = (file_names))) %>% #basename(file)
    #  unnest(text)
    
  
    #separa palavra por palavra
    word_list <- abstract_tidy %>%
        unnest_tokens(word, text)

    
    #View(teste)
    
    #tira as stop_words, to com medo de tirar coisa errada
    word_list <- word_list %>%
        anti_join(stop_words)
    

    #conta
    word_list <- word_list %>%
      count(id, word, sort = TRUE) %>%
      ungroup()
    
    return (word_list)
}
