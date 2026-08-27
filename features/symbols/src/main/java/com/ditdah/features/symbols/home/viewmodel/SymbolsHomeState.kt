package com.ditdah.features.symbols.home.viewmodel

import com.ditdah.core.settings.domain.entity.Language

data class SymbolsHomeState(
    val language: Language = Language.EN,
    val alphabet: Map<String, String>
)
