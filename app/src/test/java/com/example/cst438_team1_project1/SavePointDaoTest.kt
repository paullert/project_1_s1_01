package com.example.cst438_team1_project1

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.Dao.SavePointDao
import com.example.cst438_team1_project1.data.Dao.UserDao
import com.example.cst438_team1_project1.data.api.SavePointRepository
import com.example.cst438_team1_project1.data.entity.CryptoCoin
import com.example.cst438_team1_project1.data.entity.SavePoint
import com.example.cst438_team1_project1.data.entity.User
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SavePointDaoTest {
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var cryptoCoinDao: CryptoCoinDao
    private lateinit var savePointDao: SavePointDao
    private lateinit var savePointRepository: SavePointRepository

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()
        userDao = db.userDao()
        cryptoCoinDao = db.cryptoCoinDao()
        savePointDao = db.savePointDao()

        savePointRepository = SavePointRepository(
            coinGeckoAPI = RetrofitClientDummy,
            cryptoCoinDao = cryptoCoinDao,
            savePointDao = savePointDao
        )
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun addSavePointWithValidUserAndCoinSucceeds() = runTest {
        val userId = userDao.insertUser(User(username = "testuser", password = "password")).toInt()
        val coinId = cryptoCoinDao.insertCoin(
            CryptoCoin(coinName = "bitcoin", coinTicker = "BTC", coinImage = "btc.png")
        ).toInt()

        savePointRepository.addSavePoint(
            SavePoint(
                coinId = coinId,
                userId = userId,
                valueSnapshot = "50000.0"
            )
        )

        val savePoints = savePointDao.findSavePointsByUserId(userId)
        assertEquals(1, savePoints.size)
        assertEquals(userId, savePoints[0].userId)
        assertEquals(coinId, savePoints[0].coinId)
    }

    @Test
    fun addSavePointWithInvalidUserDoesNotCrash() = runTest {
        val coinId = cryptoCoinDao.insertCoin(
            CryptoCoin(coinName = "bitcoin", coinTicker = "BTC", coinImage = "btc.png")
        ).toInt()

        // userId = 999 does not exist in User table
        savePointRepository.addSavePoint(
            SavePoint(
                coinId = coinId,
                userId = 999,
                valueSnapshot = "50000.0"
            )
        )

        val savePoints = savePointDao.getAllSavePoints()
        assertTrue(savePoints.isEmpty())
    }

    @Test
    fun addSavePointWithInvalidCoinDoesNotCrash() = runTest {
        val userId = userDao.insertUser(User(username = "testuser", password = "password")).toInt()

        // coinId = 999 does not exist in crypto_coins table
        savePointRepository.addSavePoint(
            SavePoint(
                coinId = 999,
                userId = userId,
                valueSnapshot = "50000.0"
            )
        )

        val savePoints = savePointDao.getAllSavePoints()
        assertTrue(savePoints.isEmpty())
    }

    @Test
    fun deleteUserCascadesSavePoints() = runTest {
        val user = User(username = "testuser", password = "password")
        val userId = userDao.insertUser(user).toInt()
        val coinId = cryptoCoinDao.insertCoin(
            CryptoCoin(coinName = "bitcoin", coinTicker = "BTC", coinImage = "btc.png")
        ).toInt()

        savePointRepository.addSavePoint(
            SavePoint(
                coinId = coinId,
                userId = userId,
                valueSnapshot = "50000.0"
            )
        )
        assertEquals(1, savePointDao.getAllSavePoints().size)

        userDao.deleteUserById(userId)

        val savePoints = savePointDao.getAllSavePoints()
        assertTrue(savePoints.isEmpty())
    }
}

private object RetrofitClientDummy : com.example.cst438_team1_project1.data.api.CoinGeckoAPI {
    override suspend fun searchCoins(
        query: String,
        apiKey: String
    ): com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse {
        return com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse(
            coins = emptyList()
        )
    }

    override suspend fun getCoinPricesByName(name: String): com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse {
        return searchCoins("", "")
    }

    override suspend fun getCoinPricesByID(
        ids: String,
        vsCurrencies: String,
        apiKey: String
    ): Map<String, Map<String, Double>> {
        return emptyMap()
    }

    override suspend fun getCoinPricesBySymbol(symbols: String): com.example.cst438_team1_project1.data.api.api_responses.SearchCoinsResponse {
        return searchCoins("", "")
    }
}
