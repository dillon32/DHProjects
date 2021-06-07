
def cutAnnotation(char, str):
    firstLoc = str.find(char)

    if firstLoc != -1:
        secondLoc = str.find(char, (firstLoc + 1))
        return str[0:secondLoc]
    return

taleListPath = "C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/TaleList.txt"
with open(taleListPath) as f:
    taleList = f.read().splitlines()

for line in taleList:

    originalPath = "C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/%s.txt" % line
    newPath = "C://Users/dhhey/Documents/TextAnalysisWithR/data/plainText/%s%s.txt" % ("EDITED ", line)
    originalText = open(originalPath)
    newText = open(newPath, "w")

    for line in originalText:
            if(line.find('*') != -1):
                newText.write(cutAnnotation('*', line) + '\n')
            else:
                newText.write(line)

    originalText.close()

