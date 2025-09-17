import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val error: Error? = null
) {
    @Serializable
    enum class Error {
        NOT_FOUND,
        EXPIRED
    }
}