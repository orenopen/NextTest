package persistance

class PersistanceFactory {
    companion object {
        fun getPersistLayer(): PersistanceLayerInf {
            return PersistanceLayerImpl()
        }
    }
}