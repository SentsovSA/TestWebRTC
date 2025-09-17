package test.webrtc.test.ui.navigation

import test.webrtc.test.domain.base.ViewModel

internal object ViewModelStore {

    private val map = mutableMapOf<String, ViewModel>()

    @Suppress("UNCHECKED_CAST")
    fun <VM : ViewModel> get(key: String, factory: () -> VM) : VM =
        (map[key] ?: run {
            map.put(key, factory())?.cancel()
            map[key]
        }) as VM

    //@Suppress("UNCHECKED_CAST")
    //fun <T : ViewModel> get(key: String) : T? = map[key] as T?

    fun remove(key: String) {
        map.remove(key)?.cancel()
    }

    val keys get(): Set<String> = HashSet(map.keys)

    fun clearAll() {
        val iterator = map.iterator()
        while (iterator.hasNext()) {
            iterator.next().value.cancel()
            iterator.remove()
        }
    }

    fun clear(scope: ViewModelScope) {
        keys.filter { key ->
            val scopeName = key.split('-')[0]
            scopeName == scope.name
        }.forEach(ViewModelStore::remove)
    }

}

enum class ViewModelScope {
    Global,
    Login,
    PhoneVerification,
    EmailVerification,
    PhoneConfirmation,
    EmailConfirmation,
    Registration,
    RestorePassword,
    CreatePinCode,
    UpdatePinCode,
    Lock,
    FirstAndLastName,
    Home,
    Counters,
    CountersByAddress,
    UserProfile,
    UserBalanceUp,
    InvoicesByEls,
    Notifications,
    Notification,
    UserNotificationChannels,
    SupportChat,
    Faq,
    FaqQuestions,
    FaqAnswer,
    AboutApp,
    WebView,
    ProfileSettings,
    GisZkhPaymentByServiceProvider,
    GisZkhPaymentByEls,
    GisZkhPayment,
    GisZkhPaymentReceipt,
    ChangePassword,
    SevEnergoAddAccountBottomSheet,
    SevEnergoAccountInfoBottomSheet,
    SevEnergoSuccessPaymentScreen,
    SevEnergoPaymentByAddress,
    SevEnergoPaymentByAccount,
    History,
    HistoryReceipt,
    SevEnergoInvoice,
    UserInfo,
    RSOInfo,
    StatementsTemplates,
    Statement,
    Camera
}