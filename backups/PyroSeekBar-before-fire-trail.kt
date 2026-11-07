// Backup of the first pyro seek bar, before the played-trail effect.
// To restore it, replace PyroSeekBar in app/src/main/java/com/example/helloworldandmyfirstapp/MainActivity.kt.
// The existing drawPyroFlame helper in MainActivity.kt is shared by this version.
@Composable
private fun PyroSeekBar(
    progress: Float,
    isPlaying: Boolean,
    onProgressChange: (Float) -> Unit
) {
    val transition = rememberInfiniteTransition(label = "PyroSeekBar")
    val animatedPhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = LinearEasing)
        ),
        label = "PyroEmbers"
    )
    val animatedFlicker by transition.animateFloat(
        initialValue = 0.82f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 260, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PyroFlicker"
    )
    val phase = if (isPlaying) animatedPhase else 0.45f
    val flicker = if (isPlaying) animatedFlicker else 0.92f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawWithContent {
                drawContent()
                val density = density
                val thumbRadius = 10.dp.toPx()
                val centerX = thumbRadius + (size.width - 2f * thumbRadius) * progress.coerceIn(0f, 1f)
                val trackY = size.height / 2f
                val baseY = trackY + 3.dp.toPx()
                val flameWidth = 22.dp.toPx() * flicker
                val flameHeight = 27.dp.toPx() * flicker

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFFFF6D00).copy(alpha = 0.48f), Color.Transparent),
                        center = Offset(centerX, trackY),
                        radius = 25.dp.toPx() * flicker
                    ),
                    radius = 25.dp.toPx() * flicker,
                    center = Offset(centerX, trackY)
                )
                drawPyroFlame(centerX, baseY, flameWidth, flameHeight, Color(0xFFFF3D00))
                drawPyroFlame(
                    centerX,
                    baseY + 1.dp.toPx(),
                    flameWidth * 0.48f,
                    flameHeight * 0.68f,
                    Color(0xFFFFD54F)
                )

                if (isPlaying) {
                    repeat(5) { index ->
                        val rise = (phase + index * 0.27f) % 1f
                        val drift = kotlin.math.sin((phase + index * 0.61f) * 6.28318f)
                        val side = if (index % 2 == 0) -1f else 1f
                        val sparkX = centerX + side * (5.dp.toPx() + drift * 7.dp.toPx())
                        val sparkY = baseY - rise * 31.dp.toPx()
                        val alpha = (1f - rise) * 0.9f
                        drawCircle(
                            color = if (index % 2 == 0) Color(0xFFFFB300) else Color(0xFFFF7043),
                            radius = (1.1f + (1f - rise) * 1.1f) * density,
                            center = Offset(sparkX, sparkY),
                            alpha = alpha
                        )
                    }
                }
            }
    ) {
        Slider(
            value = progress.coerceIn(0f, 1f),
            onValueChange = onProgressChange,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFE5007D),
                activeTrackColor = Color(0xFFE5007D),
                inactiveTrackColor = Color.White.copy(alpha = 0.15f)
            ),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
    }
}
