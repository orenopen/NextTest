package persistance

interface PersistanceLayerInf{
    val INDEX_RANGE : Long
    // query db for max index for the requested agent
    // if new agent return the first index in its range
    fun getAgentMaxIndex(agentId : Int) : Long

    // save new shortUrl in DB
    // {agent, index , expirarion, counter = 0}
    fun saveWithExpiration(agentId: Int, shortStringIndex: String)

    // query and update db record by the index
    // increase record counter by 1
    // return the url
    fun getLongUrl(index : Long) : String

}