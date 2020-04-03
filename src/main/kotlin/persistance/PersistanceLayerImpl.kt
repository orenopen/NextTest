package persistance

class PersistanceLayerImpl : PersistanceLayerInf{


    // every handler will have 8 billion indexes to provide
    // ***should be ENV VARIABLE****
    override val INDEX_RANGE = 8000000000L

    override fun getAgentMaxIndex(agentId : Int) : Long {
        // query db for max index for the requested agent
        // if new agent return the first index in its range

        return agentId * INDEX_RANGE +1
    }

    override fun saveWithExpiration(agentId: Int, shortStringIndex: String) {
       // save new shortUrl in DB
       // {agent, index , expirarion, counter = 0}
    }

    override fun getLongUrl(index : Long): String {
        // query and update db record by the index
        // increase record counter by 1 fro stats support
        // return the url
        return "https://previews.123rf.com/images/vectorshots/vectorshots1211/vectorshots121100267/16104680-smile-icon-vector.jpg"
    }
}