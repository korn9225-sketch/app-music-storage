package com.example.helloworldandmyfirstapp

import androidx.compose.ui.graphics.Color

enum class CRTStyle(val displayName: String, val beamColor: Color, val glowColor: Color, val description: String) {
    CLASSIC_WHITE(
        displayName = "Classic White CRT",
        beamColor = Color.White,
        glowColor = Color(0x8000FFFF),
        description = "Standard 90s Cathode Ray Tube television glow"
    ),
    MATRIX_GREEN(
        displayName = "Matrix Green Phosphor",
        beamColor = Color(0xFF00FF66),
        glowColor = Color(0x80003311),
        description = "Vintage monochrome green terminal phosphor glow"
    ),
    AMBER_GLOW(
        displayName = "Amber Phosphor",
        beamColor = Color(0xFFFFB000),
        glowColor = Color(0x80332200),
        description = "Classic 80s amber monitor display glow"
    ),
    NEON_CYAN(
        displayName = "Cyberpunk Cyan",
        beamColor = Color(0xFF00E5FF),
        glowColor = Color(0x80002233),
        description = "Electric neon cyan beam flash"
    )
}

object CRTSettings {
    var selectedStyle: CRTStyle = CRTStyle.CLASSIC_WHITE
    var durationMs: Int = 300
    var isScreenOffEnabled: Boolean = true
    var isScreenOnEnabled: Boolean = true
    var isPowerButtonInterceptEnabled: Boolean = true
}
