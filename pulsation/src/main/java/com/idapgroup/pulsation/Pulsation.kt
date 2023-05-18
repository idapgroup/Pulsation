package com.idapgroup.pulsation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay

/**
 * Pulsation animation that duplicates content and animate it under it.
 * @param enabled - if true uses the given parameters and start the animation.
 * @param type - set of animation params to be used for animated content
 * @param content - composable content that will be drawn and copied for animation.
 */
@Composable
fun Pulsation(
    enabled: Boolean,
    type: PulsationType,
    content: @Composable () -> Unit
) {
    Pulsation(
        enabled = enabled,
        repeatsCount = type.repeatsCount,
        iterations = type.iterations,
        iterationDuration = type.iterationDuration,
        iterationDelay = type.iterationDelay,
        delayBetweenRepeats = type.delayBetweenRepeats,
        alphaRange = type.alphaRange,
        pulseRange = type.pulseRange,
        content = content
    )
}

/**
 * Pulsation animation that duplicates content and animate it under it.
 * Core function that gives more options to modify.
 * @param enabled - if true uses the given parameters and start the animation.
 * @param repeatsCount - amount of animation repeats. Use [Int.MAX_VALUE] for infinite repeats
 * @param delayBetweenRepeats - milliseconds between each repeat of animation cycle.
 * @param iterations - a cycle of repetitive animations.
 * @param iterationDuration - duration of 1 animation.
 * @param iterationDelay - delay between iterations in 1 animation cycle.
 * @param pulseRange - range between minimum and maximum animated [content] scale.
 * @param alphaRange - range between started and ended alpha for animated [content].
 * @param content - composable content that will be drawn and copied for animation.
 */
@Composable
fun Pulsation(
    enabled: Boolean,
    repeatsCount: Int,
    delayBetweenRepeats: Int,
    iterations: Int,
    iterationDuration: Int,
    iterationDelay: Int,
    pulseRange: ClosedFloatingPointRange<Float>,
    alphaRange: ClosedFloatingPointRange<Float>,
    content: @Composable () -> Unit
) {
    val scale = remember { Animatable(pulseRange.start) }
    val alpha = remember { Animatable(alphaRange.start) }
    LaunchedEffect(scale, enabled) {
        while (enabled) {
            var count = 0
            listOf(
                async {
                    scale.animateTo(
                        targetValue = pulseRange.endInclusive,
                        animationSpec = repeatable(
                            iterations = iterations,
                            animation = tween(
                                durationMillis = iterationDuration,
                                delayMillis = iterationDelay
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    )
                    scale.snapTo(pulseRange.start)
                },
                async {
                    alpha.animateTo(
                        targetValue = alphaRange.endInclusive,
                        animationSpec = repeatable(
                            iterations = iterations,
                            animation = tween(
                                durationMillis = iterationDuration,
                                delayMillis = iterationDelay
                            ),
                            repeatMode = RepeatMode.Restart
                        )
                    )
                    alpha.snapTo(alphaRange.start)
                }
            ).awaitAll()
            count += 1
            if (repeatsCount <= count) {
                return@LaunchedEffect
            }
            delay(delayBetweenRepeats.toLong())
        }
        scale.snapTo(pulseRange.start)
        alpha.snapTo(alphaRange.start)
    }
    Box {
        Box(
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
        ) {
            content()
        }
        content()
    }
}

/**
 * Top level declaration of animation type for simplified usages of animation params
 */
sealed class PulsationType(
    val repeatsCount: Int,
    val iterations: Int,
    val iterationDuration: Int,
    val iterationDelay: Int,
    val delayBetweenRepeats: Int,
    val pulseRange: ClosedFloatingPointRange<Float> = 1f..1.4f,
    val alphaRange: ClosedFloatingPointRange<Float> = 1f..0f,
) {
    /**
     * Linear animation type
     */
    class Linear(
        repeatsCount: Int = Int.MAX_VALUE,
        duration: Int = 500,
        delayBetweenRepeats: Int = 0,
        pulseRange: ClosedFloatingPointRange<Float> = 1f..1.4f,
        alphaRange: ClosedFloatingPointRange<Float> = 1f..0f,
    ) : PulsationType(
        repeatsCount = repeatsCount,
        iterations = 1,
        iterationDuration = duration,
        iterationDelay = 0,
        delayBetweenRepeats = delayBetweenRepeats,
        pulseRange = pulseRange,
        alphaRange = alphaRange
    )

    /**
     * Added possibility to make animation cycles inside animation process.
     */
    class Iterative(
        repeatCount: Int = Int.MAX_VALUE,
        iterations: Int = 3,
        iterationDuration: Int = 500,
        iterationDelay: Int = 0,
        delayBetweenRepeats: Int = 500,
        pulseRange: ClosedFloatingPointRange<Float> = 1f..1.4f,
        alphaRange: ClosedFloatingPointRange<Float> = 1f..0f,
    ) : PulsationType(
        repeatsCount = repeatCount,
        iterations = iterations,
        iterationDuration = iterationDuration,
        iterationDelay = iterationDelay,
        delayBetweenRepeats = delayBetweenRepeats,
        pulseRange = pulseRange,
        alphaRange = alphaRange
    )

    /**
     * Start of next animations before end of previous
     */
    internal class Races // Todo: future
}

//TODO: future
internal sealed interface ViewType {
    class Colored(val color: Color) : ViewType
    class Gradient(val brush: Brush) : ViewType
    object ContentTwin : ViewType
}