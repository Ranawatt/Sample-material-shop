package com.example.sample_material_shop.shop

import android.content.Context
import com.google.android.filament.Engine
import com.google.android.filament.Scene
import com.google.android.filament.View
import com.google.android.filament.gltfio.AssetLoader
import com.google.android.filament.gltfio.FilamentAsset
import com.google.android.filament.gltfio.ResourceLoader
import com.google.android.filament.utils.*
import java.nio.Buffer
import java.nio.ByteBuffer

data class ProductScene(val engine: Engine, val scene: Scene, val asset: FilamentAsset)

val scenes = mutableMapOf<String, ProductScene>()

fun loadModelGlb(assetLoader: AssetLoader,
                 resourceLoader: ResourceLoader, buffer: Buffer
): FilamentAsset {
    val asset = assetLoader.createAssetFromBinary(buffer)
    asset?.apply {
        resourceLoader.loadResources(asset)
        asset.releaseSourceData()
    }
    return asset!!
}

fun transformToUnitCube(engine: Engine, asset: FilamentAsset) {
    val tm = engine.transformManager
    val center = asset.boundingBox.center.let { v-> Float3(v[0], v[1], v[2]) }
    val halfExtent = asset.boundingBox.halfExtent.let { v-> Float3(v[0], v[1], v[2]) }
    val maxExtent = 2.0f * max(halfExtent)
    val scaleFactor = 2.0f / maxExtent
    val transform = scale(Float3(scaleFactor)) * translation(Float3(-center))
    tm.setTransform(tm.getInstance(asset.root), transpose(transform).toFloatArray())
}

fun readCompressedAsset(context: Context, assetName: String): ByteBuffer {
    val input = context.assets.open(assetName)
    val bytes = ByteArray(input.available())
    input.read(bytes)
    return ByteBuffer.wrap(bytes)
}

fun setupModelViewer(viewer: ModelViewer) {
    val options = viewer.view.dynamicResolutionOptions
    options.enabled = true
    viewer.view.dynamicResolutionOptions = options

    // viewer.view.ambientOcclusion = View.AmbientOcclusion.SSAO
    viewer.view.antiAliasing = View.AntiAliasing.FXAA
    viewer.view.sampleCount = 4

    val bloom = viewer.view.bloomOptions
    bloom.enabled = true
    viewer.view.bloomOptions = bloom
}