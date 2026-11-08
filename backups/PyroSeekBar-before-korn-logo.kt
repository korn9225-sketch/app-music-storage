// Backup of PyroSeekBar before Korn logo customization.
// To restore, replace PyroSeekBar in app/src/main/java/com/example/helloworldandmyfirstapp/MainActivity.kt.
@Composable
private fun PyroSeekBar(
    progress: Float,
    artist: String?,
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
    val isNofx = artist?.contains("NOFX", ignoreCase = true) == true

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawWithContent {
                drawContent()
                val density = density
                val thumbRadius = 10.dp.toPx()
                val trackStart = thumbRadius
                val trackEnd = size.width - thumbRadius
                val centerX = trackStart + (trackEnd - trackStart) * progress.coerceIn(0f, 1f)
                val trackY = size.height / 2f
                val baseY = trackY + 3.dp.toPx()
                val flameWidth = 22.dp.toPx() * flicker
                val flameHeight = 27.dp.toPx() * flicker

                if (isNofx) {
                    val charcoal = Color(0xFF211B1B)
                    val protestRed = Color(0xFFE53935)
                    val paper = Color(0xFFFFE9CF)
                    drawLine(
                        color = charcoal,
                        start = Offset(trackStart, trackY),
                        end = Offset(trackEnd, trackY),
                        strokeWidth = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    if (centerX > trackStart) {
                        drawLine(
                            color = protestRed,
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        var dashX = trackStart + 5.dp.toPx()
                        while (dashX < centerX - 2.dp.toPx()) {
                            drawLine(
                                color = paper.copy(alpha = 0.88f),
                                start = Offset(dashX, trackY - 1.dp.toPx()),
                                end = Offset(dashX + 3.dp.toPx(), trackY - 1.dp.toPx()),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                            dashX += 12.dp.toPx()
                        }
                    }
                    drawRollingSkateboard(centerX, trackY, phase, density, paper, protestRed)
                    if (isPlaying) {
                        repeat(3) { index ->
                            val slashPhase = phase * 6.28318f + index * 2.1f
                            val offsetY = kotlin.math.sin(slashPhase) * 2.dp.toPx()
                            val motionLength = (5f + index * 2f) * density
                            drawLine(
                                color = if (index % 2 == 0) paper else protestRed,
                                start = Offset(centerX - (12.dp.toPx() + index * 2.dp.toPx()), trackY - 5.dp.toPx() + offsetY),
                                end = Offset(centerX - (12.dp.toPx() + index * 2.dp.toPx()) - motionLength, trackY - 5.dp.toPx() + offsetY),
                                strokeWidth = 1.5.dp.toPx(),
                                cap = StrokeCap.Round,
                                alpha = 0.75f - index * 0.15f
                            )
                        }
                    }
                } else {
                    drawLine(
                        color = Color.White.copy(alpha = 0.13f),
                        start = Offset(trackStart, trackY),
                        end = Offset(trackEnd, trackY),
                        strokeWidth = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                    if (centerX > trackStart) {
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF3D00), Color(0xFFFF9100), Color(0xFFFFD54F)),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 7.dp.toPx(),
                            cap = StrokeCap.Round,
                            alpha = 0.38f
                        )
                        drawLine(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFF6D00), Color(0xFFFFB300), Color(0xFFFFF176)),
                                startX = trackStart,
                                endX = centerX
                            ),
                            start = Offset(trackStart, trackY),
                            end = Offset(centerX, trackY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    val flameSpacing = 8.dp.toPx()
                    var flameX = trackStart + flameSpacing * 0.5f
                    var flameIndex = 0
                    while (flameX < centerX - 4.dp.toPx()) {
                        val flamePhase = phase * 6.28318f + flameIndex * 1.31f
                        val flameFlicker = 0.5f + 0.5f * kotlin.math.sin(flamePhase)
                        val flameWidth = 12.dp.toPx() * flicker
                        val flameHeight = (10.dp.toPx() + 9.dp.toPx() * flameFlicker) * flicker
                        val flameVariation = kotlin.math.sin(flamePhase * 0.73f) * 0.35f
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameWidth,
                            flameHeight,
                            flameVariation,
                            Color(0xFFFF6D00)
                        )
                        drawFireBandFlame(
                            flameX,
                            baseY,
                            flameWidth * 0.42f,
                            flameHeight * 0.58f,
                            flameVariation,
                            Color(0xFFFFD54F)
                        )
                        flameX += flameSpacing
                        flameIndex++
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF6D00).copy(alpha = 0.48f), Color.Transparent),
                            center = Offset(centerX, trackY),
                            radius = 25.dp.toPx() * flicker
                        ),
                        radius = 25.dp.toPx() * flicker,
                        center = Offset(centerX, trackY)
                    )
                    drawFireBandFlame(
                        centerX,
                        baseY,
                        flameWidth,
                        flameHeight,
                        kotlin.math.sin(phase * 6.28318f) * 0.35f,
                        Color(0xFFFF3D00)
                    )
                    drawFireBandFlame(
                        centerX,
                        baseY,
                        flameWidth * 0.48f,
                        flameHeight * 0.68f,
                        kotlin.math.sin(phase * 6.28318f) * 0.35f,
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
            }
    ) {
        Slider(
            value = progress.coerceIn(0f, 1f),
            onValueChange = onProgressChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )
    }
}
