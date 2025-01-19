package mc.fuckoka.economyapi.domain.model

data class Reason(val value: String) {
    companion object {
        private const val MAX_LENGTH = 255

        fun validate(value: String): Boolean {
            return value.length <= MAX_LENGTH
        }
    }

    init {
        require(validate(value))
    }
}
