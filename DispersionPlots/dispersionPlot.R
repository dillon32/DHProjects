searchWords <- vector()
searchWords[1] <- readline(prompt="Enter word to search:  ")
searchWords[2] <- readline(prompt="Enter word to search:  ")
searchWords[3] <- readline(prompt="Enter word to search:  ")
searchWords[4] <- readline(prompt="Enter word to search:  ")

taleList.v <- scan("C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/TaleList.txt", what='char', sep='\n')

for (i in taleList.v){
fileName <- paste("C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/EDITED ",i,".txt", sep="")
text.v <- scan(fileName, what='char', sep='\n')
text.lower.v <- tolower(text.v)
text.words.l <- strsplit(text.lower.v, "\\W")
text.words.v <- unlist(text.words.l)
not.blanks.text <- which(text.words.v != "")
text.words.v <- text.words.v[not.blanks.text]

n.time.v <- seq(1:length(text.words.v))

jpegName <- paste(i," plot.jpg",sep="")
jpeg(jpegName, width = 500, height = 450)

#Create graph space
par(mfrow = c(2,2))

#Loop and create
loop.v <- 1:4
for(j in loop.v){



word.v <- which(text.words.v == searchWords[j])
word.count.v <- rep(NA, length(n.time.v))
word.count.v[word.v] <- 1
graphTitle <- paste("Dispersion Plot of '",searchWords[j],"'",sep="")
plot(word.count.v, main=graphTitle, xlab="Novel Time",
	ylab=searchWords[j], type="h", ylim=c(0,1), yaxt='n')

}
dev.off()

}