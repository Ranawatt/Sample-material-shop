package com.example.sample_material_shop.shop

import android.animation.ValueAnimator
import android.view.Surface
import android.view.SurfaceView
import android.view.animation.LinearInterpolator
import com.google.android.filament.*
import com.google.android.filament.android.DisplayHelper
import com.google.android.filament.android.UiHelper
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private const val kNearPlane = 0.5
private const val kFarPlane = 10000.0
private const val kFovDegrees = 45.0
private const val kAperture = 16f
private const val kShutterSpeed = 1f / 125f
private const val kSensitivity = 100f

class ModelViewer(
    val engine: Engine,
    val surfaceView: SurfaceView
) {
    val view: View = engine.createView()
    val camera: Camera =
        engine.createCamera().apply { setExposure(kAperture, kShutterSpeed, kSensitivity) }
    var scene: Scene? = null
        set(value) {
            view.scene = value
            field = value
        }

    private val uiHelper: UiHelper = UiHelper(UiHelper.ContextErrorPolicy.DONT_CHECK)
    private var displayHelper: DisplayHelper
    private var swapChain: SwapChain? = null
    private val renderer: Renderer = engine.createRenderer()

    private val animator = ValueAnimator.ofFloat(0.0f, (2.0 * PI).toFloat())

    init {
        view.camera = camera

        displayHelper = DisplayHelper(surfaceView.context)
        uiHelper.renderCallback = SurfaceCallback()
        uiHelper.attachTo(surfaceView)
        addDetachListener(surfaceView)

        val start = Random.nextFloat()

        animator.interpolator = LinearInterpolator()
        animator.duration = 36_000
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener { a ->
            val v = (a.animatedValue as Float) + start
            camera.lookAt(
                cos(v) * 2.5, 0.5, sin(v) * 2.5,
                0.0, 0.0, 0.0,
                0.0, 1.0, 0.0
            )
        }
        animator.start()
    }

    fun render(frameTimeNanos: Long) {
        if (!uiHelper.isReadyToRender) {
            return
        }

        // Render the scene, unless the renderer wants to skip the frame.
        if (renderer.beginFrame(swapChain!!, frameTimeNanos)) {
            renderer.render(view)
            renderer.endFrame()
        }
    }

    private fun addDetachListener(view: android.view.View) {
        view.addOnAttachStateChangeListener(object : android.view.View.OnAttachStateChangeListener {
            var detached = false

            override fun onViewAttachedToWindow(v: android.view.View?) { detached = false }

            override fun onViewDetachedFromWindow(v: android.view.View?) {
                if (!detached) {
                    animator.cancel()

                    uiHelper.detach()

                    engine.destroyRenderer(renderer)
                    engine.destroyView(this@ModelViewer.view)
                    engine.destroyCamera(camera)

                    detached = true
                }
            }
        })
    }

    inner class SurfaceCallback : UiHelper.RendererCallback {
        override fun onNativeWindowChanged(surface: Surface) {
            swapChain?.let { engine.destroySwapChain(it) }
            swapChain = engine.createSwapChain(surface)
            displayHelper.attach(renderer, surfaceView.display)
        }

        override fun onDetachedFromSurface() {
            displayHelper.detach()
            swapChain?.let {
                engine.destroySwapChain(it)
                engine.flushAndWait()
                swapChain = null
            }
        }

        override fun onResized(width: Int, height: Int) {
            view.viewport = Viewport(0, 0, width, height)
            val aspect = width.toDouble() / height.toDouble()
            camera.setProjection(kFovDegrees, aspect, kNearPlane, kFarPlane, Camera.Fov.VERTICAL)
        }
    }
}