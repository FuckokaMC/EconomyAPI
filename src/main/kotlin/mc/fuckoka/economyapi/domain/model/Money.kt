package mc.fuckoka.economyapi.domain.model

data class Money(val value: Int) {
    companion object {
        const val MIN_VALUE = 0
        const val MAX_VALUE = 999999999
    }

    init {
        require(value in MIN_VALUE..MAX_VALUE)
    }
}
