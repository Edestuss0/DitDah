package com.ditdah.core.designsystem.theme

import androidx.compose.ui.graphics.Color

// Gray primary
val Gray80 = Color(0xFFE5E7EB)
val Gray40 = Color(0xFF4B5563)

// Neutral secondary
val Neutral80 = Color(0xFFD1D5DB)
val Neutral40 = Color(0xFF6B7280)

// Dark accent
val Dark80 = Color(0xFF9CA3AF)
val Dark40 = Color(0xFF374151)

// ============================================
// ACCENT — Orange / Orange-Brown (Primary)
// ============================================
val Orange99 = Color(0xFFFFFAF7)
val Orange95 = Color(0xFFFFE9D9)
val Orange90 = Color(0xFFFFD3B0)
val Orange80 = Color(0xFFF5B385)   // тон для тёмной темы (Primary)
val Orange70 = Color(0xFFEC9760)
val Orange60 = Color(0xFFDD7C3D)
val Orange50 = Color(0xFFC96825)
val Orange40 = Color(0xFFAD5518)   // тон для светлой темы (Primary)
val Orange30 = Color(0xFF8A4514)
val Orange20 = Color(0xFF663311)
val Orange10 = Color(0xFF3D1E0B)

// Secondary accent — приглушённый коричневый, для второстепенных акцентов
val Brown80 = Color(0xFFD6B693)
val Brown40 = Color(0xFF77593A)

// Primary role mapping — Light theme
val PrimaryLight            = Orange40
val OnPrimaryLight          = Color(0xFFFFFFFF)
val PrimaryContainerLight   = Orange90
val OnPrimaryContainerLight = Orange10

// Primary role mapping — Dark theme
val PrimaryDark             = Orange80
val OnPrimaryDark           = Orange10
val PrimaryContainerDark    = Orange30
val OnPrimaryContainerDark  = Orange95

// Secondary role mapping
val SecondaryLight          = Brown40
val SecondaryDark           = Brown80

// Dark theme
val BackgroundDark      = Color(0xFF0A0A0A)
val SurfaceDark         = Color(0xFF121212)
val SurfaceVariantDark  = Color(0xFF1C1C1C)
val BorderDark          = Color(0xFF2A2A2A)

val OnDark              = Color(0xFFF5F5F5)
val OnDarkMuted         = Color(0xFFA3A3A3)
val SurfaceContainerLowestDark = Color(0xFF080808)
val SurfaceContainerLowDark    = Color(0xFF121212)
val SurfaceContainerDark       = Color(0xFF1C1C1C)
val SurfaceContainerHighDark   = Color(0xFF242424)
val SurfaceContainerHighestDark= Color(0xFF2E2E2E)
val SurfaceBrightDark          = Color(0xFF323232)
val SurfaceDimDark             = Color(0xFF0A0A0A)

// Light theme
val SurfaceContainerLowestLight  = Color(0xFFFFFFFF)
val SurfaceContainerLowLight     = Color(0xFFF1F1F1)
val SurfaceContainerLight        = Color(0xFFEAEAEA)
val SurfaceContainerHighLight    = Color(0xFFE0E0E0)
val SurfaceContainerHighestLight = Color(0xFFD6D6D6)
val SurfaceBrightLight           = Color(0xFFF8F8F8)
val SurfaceDimLight              = Color(0xFFE8E8E8)
val BackgroundLight     = Color(0xFFF8F8F8)
val SurfaceLight        = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFF1F1F1)
val BorderLight         = Color(0xFFE0E0E0)

val OnLight             = Color(0xFF111111)
val OnLightMuted        = Color(0xFF6B7280)

// ============================================
// Secondary container extension (Brown)
// ============================================
val Brown10 = Color(0xFF241708)
val Brown30 = Color(0xFF5C4224)
val Brown90 = Color(0xFFEDDBC4)

// ============================================
// Error — контейнерные варианты
// ============================================
val ErrorContainerLight   = Color(0xFFFFDAD6)
val OnErrorContainerLight = Color(0xFF410002)
val ErrorRedDark          = Color(0xFFFFB4AB)   // светлее — для читаемости на тёмном фоне
val OnErrorDark           = Color(0xFF690005)
val ErrorContainerDark    = Color(0xFF93000A)
val OnErrorContainerDark  = Color(0xFFFFDAD6)

// ============================================
// Outline — тёплый серо-коричневый, а не холодный серый
// ============================================
val OutlineLight = Color(0xFF8A857D)
val OutlineDark  = Color(0xFF9C948A)

// ============================================
// Inverse surface (снекбары, тултипы поверх контента)
// ============================================
val InverseSurfaceLight   = Color(0xFF322F2B)
val InverseOnSurfaceLight = Color(0xFFF7F0EA)
val InverseSurfaceDark    = Color(0xFFEDE7E0)
val InverseOnSurfaceDark  = Color(0xFF322F2B)

val ScrimColor = Color(0xFF000000)

// CORE COLORS
val ErrorRed     = Color(0xFFE53935)
val SuccessGreen = Color(0xFF43A047)
val InfoBlue     = Color(0xFF2563EB)