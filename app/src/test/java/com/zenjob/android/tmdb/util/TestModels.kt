package com.zenjob.android.tmdb.util

import com.zenjob.android.tmdb.common.data.api.model.ApiMovie
import com.zenjob.android.tmdb.common.data.api.model.ApiPaginatedList
import com.zenjob.android.tmdb.common.domain.model.*
import java.util.*


val movie1 = Movie(
    id = 1L,
    imdbId = "id1",
    overview = "overview1",
    title = "title1",
    posterPath = "/img1.png",
    releaseDate = Date(),
    voteAverage = 10.0f
)

val movie2 = Movie(
    id = 2L,
    imdbId = "id2",
    overview = "overview2",
    title = "title2",
    posterPath = "/img2.png",
    releaseDate = Date(),
    voteAverage = 10.0f
)

val apiMovie1 = ApiMovie(
    id = 1L,
    imdbId = "id1",
    overview = "overview1",
    title = "title1",
    posterPath = "/img1.png",
    releaseDate = Date(),
    voteAverage = 10.0f
)

val apiMovie2 = ApiMovie(
    id = 2L,
    imdbId = "id2",
    overview = "overview2",
    title = "title2",
    posterPath = "/img2.png",
    releaseDate = Date(),
    voteAverage = 10.0f
)

val fakeResponse = ApiPaginatedList(
    page = 1,
    results = listOf(apiMovie1, apiMovie2),
    totalPages = 10,
    totalResults = 200
)

val configuration = Configuration(Images("/", listOf(LIST_ITEM_POSTER_SIZE, DETAIL_POSTER_SIZE)))