package com.zenjob.android.tmdb.common.presentation.model.mappers

interface UiMapper<E, V> {
    fun mapToView(domainEntity: E): V
}