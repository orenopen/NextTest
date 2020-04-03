package bl

import persistance.PersistanceFactory

public class UrlHandler(agentId : Int){
    // ***should be ENV VARIABLE****
    val BASE_URL =  "http://localhost:8080/fetch/"

    // Any index will be converted to string, with those 73 characters, it will be part of the long Url
    val urlCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789?#@!$&()*+=".toCharArray()

    private val selectedAgentId = agentId
    private val persistLayer = PersistanceFactory.getPersistLayer()
    private val indexGenerator = IndexGenerator(agentId)

    val encryptionLength = urlCharacters.size


    fun getShortUrl () : String{
        var index = indexGenerator.getNextIndex()
        var shortString = indexToString(index)
        persistLayer.saveWithExpiration(selectedAgentId,shortString)
        return BASE_URL + shortString
    }

    fun fetch (shortString : String) : String{
        var index = stringToIndex(java.lang.StringBuilder(shortString))
        persistLayer.getLongUrl(index)
        return "${persistLayer.getLongUrl(index)}"
    }

    // Converts a number to a string, the logic is to take the "remain" and "div" of dividing by encryptionLength (73)
    // and translate the "remain" to a char as a index to urlCharacters, and the "div" will be the value for the next iteration
    // the max index is encryptionLength^8 so we will have maximum 8 iterations => maximum 8 chars
    fun indexToString(generatedIndex: Long): String {
        var token: StringBuilder = StringBuilder()
        var index = generatedIndex
        var remain : Long
        while (index > encryptionLength){
            remain = index.rem(encryptionLength)
            if (remain>0) {
                token.append(urlCharacters[(remain.toInt() - 1)])
            } else if (remain.toInt()==0){
                token.append(urlCharacters[urlCharacters.lastIndex])
            }
            index = index.div(encryptionLength)
        }
        token.append(urlCharacters.get(index.toInt()-1))
        token=token.reverse()
        println("@@@@@@@@@@@ Logger  The index $generatedIndex translated to the string ${token.toString()}")
        return(token.toString())

    }

    // doing the opposite, iterate over the string and translating every char to its position in urlCharacters and
    // multiply by the encryptionLength and aggregate by maximum 7 iterations, the last char is the string is been
    // added to the total amount without multiplication
    fun stringToIndex(token : StringBuilder) : Long {
        var index=0L
        var i =0
        while( i < token.length-1){
            index = (index + urlCharacters.indexOf(token.get(i))+1) * encryptionLength
            i++
        }
        if(i<token.length)
            index += urlCharacters.indexOf(token.get(i))+1
        println("@@@@@@@@@@@ Logger  The string ${token.toString()} translated to the index $index")
        return (index)
    }
}

