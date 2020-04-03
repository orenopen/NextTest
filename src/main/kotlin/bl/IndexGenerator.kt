package bl

import persistance.PersistanceFactory


class IndexGenerator(agentId: Int){

    private val persistLayer = PersistanceFactory.getPersistLayer()
    private var currentIndex : Long = 0L

    init {
        currentIndex =   persistLayer.getAgentMaxIndex(agentId)+1
    }

    fun getNextIndex() : Long{
        return currentIndex++
    }
}