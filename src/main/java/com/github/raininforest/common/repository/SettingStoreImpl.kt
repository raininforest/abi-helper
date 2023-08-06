package com.github.raininforest.common.repository

data class SettingStoreImpl(
    override var shouldGenerateConstructor: Boolean,
    override var shouldGenerateCopy: Boolean
) : SettingsStore
