package mc.fuckoka.economyapi.domain.model

data class WalletID(val value: Long) {
    init {
        require(value >= 0)
    }
}
