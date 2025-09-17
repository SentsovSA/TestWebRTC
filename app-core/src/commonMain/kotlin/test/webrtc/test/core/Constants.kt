package test.webrtc.test.core

internal const val DEV_MODE = true
//internal const val BASE_URL = BuildConfig.BASE_URL
//internal const val BASE_URL = "https://main-service-test.test.ost.group"
internal const val BASE_URL = "https://jku.ost.group"
//internal const val BASE_URL = "https://main-service-stage.test.ost.group"
internal const val DEFAULT_COUNTRY_CODE = 7L

internal const val ACTION_GRANTED_PERMISSIONS = "group.ost.paymentservice.ACTION_GRANTED_PERMISSIONS"
internal const val EXTRA_REQUEST_PERMISSIONS = "request-permissions"

const val APP_VERSION = "1.4.2"
const val PRIVACY_URL = "$BASE_URL/privacy"
const val LICENSE_URL = "$BASE_URL/license"
const val CONFIRMATION_CODE_MAX_LENGTH = 4
const val PHONE_NUMBER_MAX_LENGTH = 10
const val PASSWORD_MAX_LENGTH = 25
const val PASSWORD_MIN_LENGTH = 6
const val PIN_CODE_MAX_LENGTH = 4
const val PIN_CODE_UNLOCK_MAX_ATTEMPTS = 6
const val PIN_CODE_UNLOCK_WARNING_ATTEMPTS_NUMBER = 3

const val BALANCE_UP_SUCCESS_URL = "${BASE_URL}/"
//const val BALANCE_UP_SUCCESS_URL = "https://main-service-test.test.ost.group/"