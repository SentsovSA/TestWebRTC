package test.webrtc.test.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Scale
import com.valentinilk.shimmer.shimmer

@Composable
fun MarkdownParser(markdownText: String) {

    val lines = markdownText.split("\n")

    Column(
        modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),

        ) {
        lines.forEach { line ->
            val trimmedLine = line.trimStart()
            when {
                trimmedLine.startsWith("![") && trimmedLine.contains("](") -> {
                    val imageUrl = trimmedLine.substringAfter("](").substringBefore(")")

                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(imageUrl)
                            .size(coil3.size.Size.ORIGINAL)
                            .scale(Scale.FIT)
                            .build()
                    )

                    val state = painter.state.collectAsState()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Crossfade(
                            targetState = state,
                            animationSpec = tween(1000, easing = LinearOutSlowInEasing),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            when (it.value) {
                                is AsyncImagePainter.State.Loading -> {
                                    Box(
                                        modifier = Modifier
                                            .width(170.dp)
                                            .height(300.dp)
                                            .padding(8.dp)
                                            .shimmer()
                                            .background(Color.Gray, RoundedCornerShape(8.dp)),
                                    )
                                }

                                is AsyncImagePainter.State.Error -> {
                                    /*Icon(
                                        AppImages.Alarm,
                                        contentDescription = "Ошибка загрузки",
                                        tint = Color.Red
                                    )*/
                                }

                                is AsyncImagePainter.State.Success -> {
                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    )
                                }

                                else -> {}
                            }
                        }
                    }

                }

                trimmedLine.startsWith("[") && trimmedLine.contains("](") -> {
                    val linkText = trimmedLine.substringAfter("[").substringBefore("](")
                    val linkUrl = trimmedLine.substringAfter("](").substringBefore(")")

                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.Blue,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(linkText)
                            }
                        },
                        modifier = Modifier.clickable {

                        }
                    )
                }

                trimmedLine.contains("**") -> {
                    val boldText = trimmedLine.substringAfter("**").substringBefore("**")

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(boldText)
                            }
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                trimmedLine.contains("####") -> {
                    val boldText = trimmedLine.substringAfter("####")

                    Text(
                        text = boldText,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                trimmedLine.contains("*") -> {
                    val italicText = trimmedLine.substringAfter("*").substringBefore("*")

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append(italicText)
                            }
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                else -> {
                    Text(text = trimmedLine, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}
