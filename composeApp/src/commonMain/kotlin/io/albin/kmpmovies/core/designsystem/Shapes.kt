package io.albin.kmpmovies.core.designsystem

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

data class AppShapes(
    val posterCorner: CornerBasedShape = RoundedCornerShape(12.dp),
    val buttonCorner: CornerBasedShape = RoundedCornerShape(24.dp),
    val chipCorner: CornerBasedShape = RoundedCornerShape(16.dp),
    val cardCorner: CornerBasedShape = RoundedCornerShape(12.dp),
    val pillCorner: CornerBasedShape = RoundedCornerShape(8.dp)
)
