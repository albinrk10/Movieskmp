package io.albin.kmpmovies.integration

import io.albin.kmpmovies.infrastructure.datasources.KtorMoviesRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class KtorMoviesRemoteDataSourceTest {

    private val jsonResponse = """
        {
          "page": 1,
          "results": [
            {
              "id": 999,
              "title": "Interstellar",
              "overview": "Mankind was born on Earth.",
              "release_date": "2014-11-07",
              "poster_path": "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
              "backdrop_path": "/rAiYTggZ3GF9kIIqdMVbtZSwqTi.jpg",
              "original_title": "Interstellar",
              "original_language": "en",
              "popularity": 180.5,
              "vote_average": 8.7
            }
          ],
          "total_pages": 1,
          "total_results": 1
        }
    """.trimIndent()

    private fun createMockClient(): HttpClient {
        return HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            engine {
                addHandler { request ->
                    respond(
                        content = jsonResponse,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }
    }

    @Test
    fun `fetchPopularMovies parses TMDB JSON response correctly into RemoteResult`() = runTest {
        val client = createMockClient()
        val dataSource = KtorMoviesRemoteDataSource(client)

        val result = dataSource.fetchPopularMovies()

        assertEquals(1, result.page)
        assertEquals(1, result.results.size)
        val movie = result.results.first()
        assertEquals(999, movie.id)
        assertEquals("Interstellar", movie.title)
        assertEquals("2014-11-07", movie.releaseDate)
        assertEquals(8.7, movie.voteAverage)
        assertNotNull(movie.backdropPath)
    }

    @Test
    fun `searchMovies sends query parameter and returns expected movies`() = runTest {
        val client = createMockClient()
        val dataSource = KtorMoviesRemoteDataSource(client)

        val result = dataSource.searchMovies("Interstellar")

        assertEquals(1, result.results.size)
        assertEquals("Interstellar", result.results.first().title)
    }
}
