package com.tomaszkopacz.kawernaapp.ui.main.profile

class AccountViewModelTest {

//    TODO Move test to androidTest package, because QRGenerator uses android framework

//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var userManager: UserManager
//    private lateinit var passwordService: RestorePasswordService
//
//    private lateinit var bitmap: Bitmap
//
//    @Before
//    fun setUp() {
//        userManager = Mockito.mock(UserManager::class.java)
//        passwordService = Mockito.mock(RestorePasswordService::class.java)
//
//        bitmap = Mockito.mock(Bitmap::class.java)
//    }
//
//    @Test
//    fun `init{} - check qr code is generated`() {
//        val loggedUser = Player("email", "name", "password")
//
//        Mockito
//            .`when`(QRGenerator.generateQRCode(Mockito.anyString()))
//            .thenReturn(bitmap)
//
//        Mockito
//            .`when`(userManager.getLoggedUser())
//            .thenReturn(loggedUser)
//
//        val newViewModel = AccountViewModel(userManager, passwordService)
//
//        newViewModel.getQRCode().observeOnce { generatedBitmap ->
//            assertTrue(generatedBitmap == bitmap)
//        }
//    }
}