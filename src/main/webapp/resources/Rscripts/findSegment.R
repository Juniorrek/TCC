synonyms <- list()
synonyms$objective <- c("ambition", "aspiration", "intent", "purpose", "propose", "mission", "target", "desing", "mission", "object", "end in view", "ground zero", "wish", "goal", " aim ", " mind ", "meaning", " mark ", " gaol ", "final ", "reach");
synonyms$methodology <- c(" mode ", "procedure", "technique", "approach", "channels", "design", "manner", " plan ", "practice", "process", "program", " way ", "method", "conduct", "measure", "operation", "proceeding", "scheme", "strategy", "step", " form ", "arrangement")
synonyms$conclusion <- c("closure", "complet", "consequen", "denouement", "development", "ending", "result", "culmination", "finaliz", "fulfillment", "windup", "outcome", "conclu", "reaction", "achievement", "attainment", "realization", "succes")

index2List = function(sentences, indexes, segment) {
  row <- 0
  list <- c()
  for (index in indexes) {
    row <- row + 1
    list[row] <- ifelse(length(index) > 0, paste(unlist(sentences[[row]][index]), collapse=' '), paste0(segment, " not found", sep=" "))
  }
  return(list)
}


findSegment = function (abstracts, segment, customKeys = NULL) {
  library(dplyr)

  if (!is.null(customKeys))
    usedSynonyms <- customKeys
  else 
    usedSynonyms <- get(segment, synonyms)
  
  synonymsString <- paste(usedSynonyms,collapse="|") 
  
  sentences <- strsplit(unlist(abstracts), "\\. ") 
  
  indexes <- lapply(sentences, function(x) {
    grep(synonymsString, x)
  })
  
  return(index2List(sentences, indexes, segment))
}
