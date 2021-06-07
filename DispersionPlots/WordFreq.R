text.v <- scan("C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/EDITED Wife of Bath's Tale.txt", what='char', sep='\n')
text.lower.v <- tolower(text.v)
text.words.l <- strsplit(text.lower.v, "\\W")
text.words.v <- unlist(text.words.l)
not.blanks.text <- which(text.words.v != "")
text.words.v <- text.words.v[not.blanks.text]

#The below section creates a table of the
# most frequently used words.

text.freqs.t <- table(text.words.v)
sorted.text.freqs.t <- sort(text.freqs.t, decreasing=TRUE)
sorted.text.freqs.t