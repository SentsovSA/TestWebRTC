package test.webrtc.test.context

@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is an experimental API for Calf and is likely to change before becoming " +
            "stable."
)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER
)
@Retention(AnnotationRetention.BINARY)
annotation class ExperimentalCalfApi
