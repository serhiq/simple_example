package com.gmail.uia059466.test_for_work_db

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gmail.uia059466.test_for_work_db.db.*
import com.gmail.uia059466.test_for_work_db.db.currency.AddUserCurrency
import com.gmail.uia059466.test_for_work_db.db.currency.GetAUserCurrencyById
import com.gmail.uia059466.test_for_work_db.db.currency.GetAllUserCurrency
import com.gmail.uia059466.test_for_work_db.db.currency.UserCurrency
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CurrencyTest {

    lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        localDataSource = LocalDataSource.getInstance(appContext)
    }

    @After
    fun finish() {
        runBlocking {
            localDataSource.reset()
        }
    }

    @Test
    fun testPreConditions() {
        assertNotNull(localDataSource)
    }


    @Test
    fun saveCurrency() {
        val currency1=Currency.getInstance("EUR")
        val currency2=Currency.getInstance("RUB")

        runBlocking {
             localDataSource.execute(AddUserCurrency(currency1))
             localDataSource.execute(AddUserCurrency(currency2))

            val result = localDataSource.execute(GetAllUserCurrency())

            result as HolderResult.Success<List<UserCurrency>>
            MatcherAssert.assertThat(result.data.size, CoreMatchers.`is`(2))
        }


        //        mDataSource.delteAllStorage()
//        mDataSource.createStorage(StorageImpl.createStorage(id = 0, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 1, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 2, title = "title", icon =IconAccount.account, amount = 2222))
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(3))
//        mDataSource.deleteStorage(rate[0])
//        mDataSource.deleteStorage(rate[1])
//        rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(1))

    }

    @Test
    fun saveCurrency_retrievesCurrency() {
        // GIVEN - a new currency saved in the database

        val currency=Currency.getInstance("EUR")


        runBlocking {
            val id=localDataSource.execute(AddUserCurrency(currency))

            val result = GetAUserCurrencyById(1).execute(localDataSource.db)

            result as HolderResult.Success<UserCurrency>
            MatcherAssert.assertThat(result.data.code, CoreMatchers.`is`("EUR"))
        }


        //        mDataSource.delteAllStorage()
//        mDataSource.createStorage(StorageImpl.createStorage(id = 0, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 1, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 2, title = "title", icon =IconAccount.account, amount = 2222))
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(3))
//        mDataSource.deleteStorage(rate[0])
//        mDataSource.deleteStorage(rate[1])
//        rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(1))

    }

    @Test
    fun testGetCurrencies(){}

    @Test
    fun testDeleteCurrencies(){}

//    @Test
//    fun testAddAndDelete() {
//        mDataSource.delteAllStorage()
//        mDataSource.createStorage(StorageImpl.createStorage(id = 0, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 1, title = "title", icon =IconAccount.account, amount = 2222))
//        mDataSource.createStorage(StorageImpl.createStorage(id = 2, title = "title", icon =IconAccount.account, amount = 2222))
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(3))
//        mDataSource.deleteStorage(rate[0])
//        mDataSource.deleteStorage(rate[1])
//        rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(1))
//    }
//
//    @Test
//    fun testDeleteAll() {
//        mDataSource.delteAllStorage()
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(0))
//    }
//
//    @Test
//    fun testDeleteOnlyOne() {
//        mDataSource.createStorage(StorageImpl.createStorage(id = 0, title = "title", icon =IconAccount.account, amount = 2222))
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(1))
//        mDataSource.deleteStorage(rate[0])
//        rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(0))
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testShouldAddExpenseType() {
//        mDataSource.createStorage(StorageImpl.createStorage(id = 0, title = "title", icon =IconAccount.account, amount = 2222))
//        var rate = mDataSource.getAllStorage()
//        MatcherAssert.assertThat(rate.size, CoreMatchers.`is`(1))
//        assertTrue(rate[0].getName().equals("title"))
//        assertTrue(rate[0].getAmount().toInt().equals(2222))
//    }


//    @Test
//    fun findProduct() {
//        insertIntoDatabase(Product(100, "Smartphone"))
//        val product: Product = dao.findProduct(100)
//        assertThat(product.getName()).isEqualTo("Smartphone")
//    }
//


    /*

    // Do
ProductDTO actualProduct = requestProduct(1);

ProductDTO expectedProduct = new ProductDTO("1", List.of(State.ACTIVE, State.REJECTED))
assertThat(actualProduct).isEqualTo(expectedProduct); // nice and clear.




// Do
@Test
public void categoryQueryParameter2() throws Exception {
    insertIntoDatabase(
            createProductWithCategory("1", "Office"),
            createProductWithCategory("2", "Office"),
            createProductWithCategory("3", "Hardware")
    );

    String responseJson = requestProductsByCategory("Office");

    assertThat(toDTOs(responseJson))
            .extracting(ProductDTO::getId)
            .containsOnly("1", "2");
}




// Do
@Test
public void variables() throws Exception {
    insertIntoDatabase(
            createProductWithCategory("4243", "Office"),
            createProductWithCategory("1123", "Office"),
            createProductWithCategory("9213", "Hardware")
    );

    String responseJson = requestProductsByCategory("Office");

    assertThat(toDTOs(responseJson))
            .extracting(ProductDTO::getId)
            .containsOnly("4243", "1123");
}



// Do
public class ProductControllerTest {
    @Test
    public void multipleProductsAreReturned() {}
    @Test
    public void allProductValuesAreReturned() {}
    @Test
    public void filterByCategory() {}
    @Test
    public void filterByDateCreated() {}
}



// Do
assertThat(actualDTO.states).isEqualTo(List.of(States.ACTIVE, States.REJECTED));

// Do
ProductDTO expectedDTO = new ProductDTO("1", "evelope", new Category("office"), List.of(States.ACTIVE, States.REJECTED))
assertThat(actualDTO).isEqualTo(expectedDTO);


// Do
assertThat(actualProductList).contains(expectedProduct);
assertThat(actualProductList).hasSize(5);
assertThat(actualProduct).isInstanceOf(Product.class);


public class DisplayNameTest {
    @Test
    @DisplayName("Design is removed from database")
    void designIsRemoved() {}
    @Test
    @DisplayName("Return 404 in case of an invalid parameter")
    void return404() {}
    @Test
    @DisplayName("Return 401 if the request is not authorized")
    void return401() {}
}





     */

//    prodcutsAreCorrectlyDisplayedInTable

    @Test
    fun useAppContext() {
        // Context of the app under test.
//        assertEquals("com.gmail.uia059466.sqliteoperation", appContext.packageName)

    }

//
//    fun shouldInsertNote(){}
//    fun shouldVerifyUpdateNote(){}
//    fun shouldInsertNote(){}
//    fun shouldInsertNote(){}

}