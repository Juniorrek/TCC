printObjectives = function(sentences, indexes) {
  row <- 0
  list <- c()
  for (index in indexes) {
    row <- row + 1
    list[row] <- ifelse(length(index) > 0, paste(unlist(sentences[[row]][index]), collapse=' '), "Objetivo não encontrado")
  }
  return(list)
}


findObjective = function (abstracts) {
  library(dplyr)
  
  objectiveSynonyms <- c("ambition", "aspiration", "intent", "purpose", "propose", "mission", "target", "desing", "mission", "object", "end in view", "ground zero", "wish", "goal", " aim ", " mind ", "meaning", " mark ", " gaol ", "final ", "reach ");
  objectiveSynonymsString <- paste(objectiveSynonyms,collapse="|") 
  
  sentences <- strsplit(unlist(abstracts), "\\. ") 
  
  indexes <- lapply(sentences, function(x) {
    grep(objectiveSynonymsString, x)
  })
  
  return(printObjectives(sentences, indexes))
}
