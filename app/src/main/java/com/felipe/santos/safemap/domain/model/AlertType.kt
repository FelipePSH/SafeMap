package com.felipe.santos.safemap.domain.model

enum class AlertType(val title: String) {
    VERBAL("Verbal harassment"),
    PHYSICAL("Physical assault"),
    STALKING("Stalking"),
    FOLLOWING("Followed by someone"),
    EXPOSURE("Indecent exposure");

    companion object {
        fun fromTitle(title: String): AlertType? {
            return entries.firstOrNull { it.title == title }
        }
    }
}