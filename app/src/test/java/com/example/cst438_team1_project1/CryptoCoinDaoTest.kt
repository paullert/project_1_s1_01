package com.example.cst438_team1_project1

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cst438_team1_project1.data.AppDatabase
import com.example.cst438_team1_project1.data.Dao.CryptoCoinDao
import com.example.cst438_team1_project1.data.entity.CryptoCoin
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.collections.single

class CryptoCoinDaoTest {
    private lateinit var cryptoCoinDao: CryptoCoinDao
    private lateinit var db: AppDatabase

    val testCoins = arrayOf(
        CryptoCoin(
            coinId = 1,
            coinName = "bitcoin",
            coinTicker = "BTC",
            coinImage = "bitcoin_url"
        ), CryptoCoin(
            coinId = 2,
            coinName = "dogecoin",
            coinTicker = "DOGE",
            coinImage = "dogecoin_url"
        ),
        CryptoCoin(
            coinId = 3,
            coinName = "etherium",
            coinTicker = "ETH",
            coinImage = "etherium_url"
        ),
        CryptoCoin(
            coinId = 4,
            coinName = "solana",
            coinTicker = "SOL",
            coinImage = "solana_url"
        ),
        CryptoCoin(
            coinId = 5,
            coinName = "evil bitcoin",
            coinTicker = "EBC",
            coinImage = "evilbitcoin_url"
        ),
    )


    @Before
    fun createDb() {
        // In Room 3, for JVM tests with BundledSQLiteDriver, we don't need a Context
        db = Room.inMemoryDatabaseBuilder<AppDatabase>()
            .setDriver(BundledSQLiteDriver())
            .build()
        cryptoCoinDao = db.cryptoCoinDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertCoins() = runTest {

    }

    @Test
    fun getCoinById() = runTest {
        System.out.printf("Test %s: Result 1%n", "getCoinById")
        cryptoCoinDao.insertCoin(testCoins[0])
        val result = cryptoCoinDao.findById(1)
        assertNotNull(result)
        assertEquals(testCoins[0].coinName, result?.coinName)
        assertEquals(testCoins[0], result)

        System.out.printf("Test %s: Result 2%n", "getCoinById")
        cryptoCoinDao.insertCoin(testCoins[1])
        val result2 = cryptoCoinDao.findById(1)
        assertNotNull(result2)
        assertEquals(result, result2)

        System.out.printf("Test %s: Result 3%n", "getCoinById")
        val result3 = cryptoCoinDao.findById(2)
        assertNotNull(result3)
        assertEquals(testCoins[1], result3)
    }

    @Test
    fun getCoinByName() = runTest {
        System.out.printf("Test %s: Result 1%n", "getCoinByName")
        cryptoCoinDao.insertCoin(testCoins[0])
        val result = cryptoCoinDao.findByName(testCoins[0].coinName)
        assertNotNull(result)
        assertEquals(testCoins[0].coinName, result?.coinName)
        assertEquals(testCoins[0], result)

        System.out.printf("Test %s: Result 2%n", "getCoinByName")
        cryptoCoinDao.insertCoin(testCoins[1])
        val result2 = cryptoCoinDao.findByName(testCoins[0].coinName)
        assertNotNull(result2)
        assertEquals(result, result2)

        System.out.printf("Test %s: Result 3%n", "getCoinByName")
        val result3 = cryptoCoinDao.findByName(testCoins[1].coinName)
        assertNotNull(result3)
        assertEquals(testCoins[1], result3)
    }

    @Test
    fun getCoinByTicker() = runTest {
        System.out.printf("Test %s: Result 1%n", "getCoinByTicker")
        cryptoCoinDao.insertCoin(testCoins[0])
        val result = cryptoCoinDao.findByTicker(testCoins[0].coinTicker)
        assertNotNull(result)
        assertEquals(testCoins[0].coinName, result?.coinName)
        assertEquals(testCoins[0], result)

        System.out.printf("Test %s: Result 2%n", "getCoinByTicker")
        cryptoCoinDao.insertCoin(testCoins[1])
        val result2 = cryptoCoinDao.findByTicker(testCoins[0].coinTicker)
        assertNotNull(result2)
        assertEquals(result, result2)

        System.out.printf("Test %s: Result 3%n", "getCoinByTicker")
        val result3 = cryptoCoinDao.findByTicker(testCoins[1].coinTicker)
        assertNotNull(result3)
        assertEquals(testCoins[1], result3)
    }


    @Test
    fun getCoinsByName() = runTest {
        for (coin in testCoins) {
            cryptoCoinDao.insertCoin(coin)
        }

        System.out.printf("Test %s: Result 1%n", "getCoinsByName")
        val result = cryptoCoinDao.searchByName(testCoins[1].coinName)
        assertNotNull(result)
        assertEquals(testCoins[1], result.single())

        System.out.printf("Test %s: Result 2%n", "getCoinsByName")
        val result2 = cryptoCoinDao.searchByName("doge");
        assertNotNull(result2)
        assertEquals(result, result2)

        System.out.printf("Test %s: Result 3%n", "getCoinsByName")
        val result3 = cryptoCoinDao.searchByName("DoGe");
        assertNotNull(result3)
        assertEquals(result, result3)
        assertEquals(result2, result3)

        System.out.printf("Test %s: Result 4%n", "getCoinsByName")
        val result4 = cryptoCoinDao.searchByName(testCoins[0].coinName)
        assertNotNull(result4)
        assertEquals(2, result4.size)
        assertEquals(testCoins[0], result4[0])
        assertEquals(testCoins[4], result4[1])
    }
}
