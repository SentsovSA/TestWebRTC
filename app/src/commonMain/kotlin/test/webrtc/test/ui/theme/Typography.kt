package test.webrtc.test.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import test.webrtc.test.resources.Inter_Black
import test.webrtc.test.resources.Inter_Bold
import test.webrtc.test.resources.Inter_ExtraBold
import test.webrtc.test.resources.Inter_ExtraLight
import test.webrtc.test.resources.Inter_Light
import test.webrtc.test.resources.Inter_Medium
import test.webrtc.test.resources.Inter_Regular
import test.webrtc.test.resources.Inter_SemiBold
import test.webrtc.test.resources.Inter_Thin
import test.webrtc.test.resources.Res

@Composable
internal fun InterFont() = FontFamily(
	Font(
		resource = Res.font.Inter_Black,
		weight = FontWeight.Black
	),
	Font(
		resource = Res.font.Inter_Bold,
		weight = FontWeight.Bold
	),
	Font(
		resource = Res.font.Inter_ExtraBold,
		weight = FontWeight.ExtraBold
	),
	Font(
		resource = Res.font.Inter_ExtraLight,
		weight = FontWeight.ExtraLight
	),
	Font(
		resource = Res.font.Inter_Light,
		weight = FontWeight.Light
	),
	Font(
		resource = Res.font.Inter_Medium,
		weight = FontWeight.Medium
	),
	Font(
		resource = Res.font.Inter_Regular,
		weight = FontWeight.Normal
	),
	Font(
		resource = Res.font.Inter_SemiBold,
		weight = FontWeight.SemiBold
	),
	Font(
		resource = Res.font.Inter_Thin,
		weight = FontWeight.Thin
	)
)

/*
{
	"name": "H0",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 40,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 48,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": -2,
	"letterSpacingUnit": "PERCENT",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "H1 desktop",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 28,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 36,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "H1 mobile",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 24,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 32,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "H2 desktop",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 20,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 28,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "H2 mobile",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 18,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 28,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "H3",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 16,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 24,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Body Medium",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 16,
	"fontFamily": "Inter",
	"fontWeight": "Medium",
	"lineHeight": 20,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Body Regular",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 16,
	"fontFamily": "Inter",
	"fontWeight": "Regular",
	"lineHeight": 20,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Paragraph",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 16,
	"fontFamily": "Inter",
	"fontWeight": "Regular",
	"lineHeight": 24,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Footnote Medium",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 14,
	"fontFamily": "Inter",
	"fontWeight": "Medium",
	"lineHeight": 20,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Footnote Regular",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 14,
	"fontFamily": "Inter",
	"fontWeight": "Regular",
	"lineHeight": 20,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Caption Semibold",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 12,
	"fontFamily": "Inter",
	"fontWeight": "Semi Bold",
	"lineHeight": 16,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Caption Medium",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 12,
	"fontFamily": "Inter",
	"fontWeight": "Medium",
	"lineHeight": 16,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Caption",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 12,
	"fontFamily": "Inter",
	"fontWeight": "Regular",
	"lineHeight": 16,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}
},
{
	"name": "Small Caption",
	"type": "typography",
	"isAlias": false,
	"value": {
	"fontSize": 10,
	"fontFamily": "Inter",
	"fontWeight": "Medium",
	"lineHeight": 12,
	"lineHeightUnit": "PIXELS",
	"letterSpacing": 0,
	"letterSpacingUnit": "PIXELS",
	"textCase": "ORIGINAL",
	"textDecoration": "NONE"
}*/
