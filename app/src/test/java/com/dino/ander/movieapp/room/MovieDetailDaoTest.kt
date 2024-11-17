package com.dino.ander.movieapp.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.dino.ander.movieapp.room.dao.MovieDetailDao
import com.dino.ander.movieapp.room.entity.MovieDetailDBEntity
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class MovieDetailDaoTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Inject
    @Named("test_db")
    lateinit var dataBase: MovieDataBase
    private lateinit var dao : MovieDetailDao

    @Before
    fun setup(){
        Dispatchers.setMain(mainThreadSurrogate)
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDataBase::class.java).allowMainThreadQueries().build()
        dao = dataBase.movieDetailDao()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `When I register and search for a registered movie`() =  runTest{
        val movieDetail : MovieDetailDBEntity? = MovieDetailDBEntity(
            id = 1,
            title = "Spiderman",
            status = "active",
            overview = ""
        )
        dao.saveMovie(movieDetail!!)
        val all = dao.getMovie(1)
        assertEquals("Spiderman", all.first()?.title)
    }
}