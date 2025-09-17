package test.webrtc.test.core.utils

/**
 * from 79990001122 to +7 (999) 000-11-22
 */
internal fun String.toPrettyPhoneFormat() : String {
    val chars = reversed().toCharArray()
    var result = ""
    for (i in chars.indices) {
        val char = chars[i]
        result += char
        if (i == 1 || i == 3) {
            result += '-'
        }
        if (i == 6) {
            result += " )"
        }
        if (i == 9) {
            result += "( "
        }
    }
    if (!startsWith('+')) result += '+'
    return result.reversed()
}

/*
internal fun String.phoneFormatter() = replace("\\B(?=(\\d{4})+(?!\\d))".toRegex(), "-")
internal fun String.phoneFormatter2() = replaceFirst("(\\d{3})(\\d{2})(\\d+)".toRegex(), "($1) $2-$3")*/
