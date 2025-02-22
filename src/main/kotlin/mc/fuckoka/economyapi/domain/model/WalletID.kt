package mc.fuckoka.economyapi.domain.model

data class WalletID(val value: Int) {
    init {
        require(value >= 0)
    }
}
