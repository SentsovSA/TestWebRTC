package test.webrtc.test.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue

class MaskVisualTransformation private constructor(
    private val mask: Mask
) : VisualTransformation, OffsetMapping {

    companion object {
        val PHONE = MaskVisualTransformation(Mask.PHONE)
        val GOG_NUMBER = MaskVisualTransformation(Mask.GOS_NUMBER)
    }

    val length = mask.length

    private fun out(text: AnnotatedString) = buildAnnotatedString {
        var result = ""
        var maskIndex = 0
        text.forEach { char ->
            while (mask.specialSymbolsIndices.contains(maskIndex)) {
                result += mask.value[maskIndex]
                maskIndex++
            }
            result += char
            maskIndex++
        }
        append(result)
        /*mask.placeholder?.let {
            withStyle(SpanStyle(color = Color.LightGray)) {
                append(it.takeLast(it.length - length))
            }
        }*/
    }

    override fun originalToTransformed(offset: Int): Int {
        val offsetValue = offset.absoluteValue
        if (offsetValue == 0) return 0
        var numberOfHashtags = 0
        val masked = mask.value.takeWhile {
            if (it == '#') numberOfHashtags++
            numberOfHashtags < offsetValue
        }
        return masked.length + 1
    }

    //transformedToOriginal returned invalid mapping: 12 -> 8 is not in range of original text [0, 0]
    override fun transformedToOriginal(offset: Int): Int {
        return mask.value.take(offset.absoluteValue).count { it == '#' }
    }

    override fun filter(text: AnnotatedString) = TransformedText(out(text), this)

    private enum class Mask(
        val value: String,
        val placeholder: String?
    ) {
        PHONE(value = "(###) ###-##-##", placeholder = "(000) 000-00-00"),
        GOS_NUMBER(value = "###### ###", placeholder = "A000AA 000");

        val length = value.count { it == '#' }
        val specialSymbolsIndices = value.indices.filter { value[it] != '#' }
    }
}