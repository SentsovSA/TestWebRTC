package test.webrtc.test.database.row

data class CounterRow(
    val addressId: String,
    val type: Type,
    val value: Int,
    val isOutdated: Boolean
) {
    enum class Type {
        ColdWater,
        HotWater,
        DaytimeElectricity,
        NightElectricity,
        Gas
    }
}