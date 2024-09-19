//package com.example.toastertoast
//
//import android.app.Dialog
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//import android.graphics.drawable.ColorDrawable
//import android.os.Bundle
//import android.view.Window
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModelProvider
//import com.gimble.segmk3.R
//import com.gimble.segmk3.data.remote.apiClient.NewApiClient
//import com.gimble.segmk3.data.remote.models.palletizationModel.GetGRNDataResponse
//import com.gimble.segmk3.data.remote.repositories.binMappingRepo.FirstBinMappingRepositoryImpl
//import com.gimble.segmk3.domain.palletizationUseCase.GetGRNDataUseCase
//import com.gimble.segmk3.presentation.binMapping.activities.ui.theme.SEGMK3Theme
//import com.gimble.segmk3.presentation.commonComposables.SearchView
//import com.gimble.segmk3.presentation.commonComposables.ui.theme.primaryColor
//import com.gimble.segmk3.presentation.commonComposables.ui.theme.secondaryColor
//import com.gimble.segmk3.presentation.commonComposables.ui.theme.white
//import com.gimble.segmk3.presentation.jetpackCompose.TopNavigationBar
//import com.gimble.segmk3.presentation.jetpackCompose.mappingFirstCard
//import com.gimble.segmk3.presentation.palletization.viewModelFactory.FirstPalletizationViewModelFactory
//import com.gimble.segmk3.presentation.palletization.viewModels.FirstPalletizationViewModel
//import com.gimble.segmk3.presentation.utils.AppConstants
//import com.gimble.segmk3.presentation.utils.Resource
//import com.gimble.segmk3.presentation.utils.formatDateTime
//import kotlinx.coroutines.launch
//import java.lang.reflect.Modifier
//
//class FirstBinMappingJetpackComposeActivity : ComponentActivity() {
//
//        lateinit var viewModel : FirstPalletizationViewModel
//        private var pageNumber  = 1
//        private var rowsPerPage = 20
//        private var isSearched = false
//        private lateinit var dialog:Dialog
//
//
//        override fun onCreate(savedInstanceState : Bundle?) {
//                super.onCreate(savedInstanceState)
//
//                val newApiInterface = NewApiClient.getService()
//
//                val repository = FirstBinMappingRepositoryImpl(newApiInterface)
//                val getGRNDataUseCase = GetGRNDataUseCase(repository)
//
//
//                viewModel = ViewModelProvider(
//                        this,
//                        FirstPalletizationViewModelFactory(getGRNDataUseCase)
//                )[FirstPalletizationViewModel::class.java]
//
//                dialog = Dialog(this)
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//                dialog.setCancelable(false)
//                dialog.setContentView(R.layout.progress_bar_dialog)
//                dialog.window?.setBackgroundDrawable(
//                        ColorDrawable(
//                                android.graphics.Color.TRANSPARENT)
//                )
//
//                dialog.show()
//
//
//                getData("")
//
//                setContent {
//                        MaterialTheme {
//                                // A surface container using the 'background' color from the theme
//                                Surface(
//                                        modifier = Modifier.fillMaxSize(),
//                                        color = MaterialTheme.colorScheme.background
//                                ) {
//
//
//                                        Column(
//                                                modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .fillMaxHeight()
//                                        ) {
//
//                                                var query by remember { mutableStateOf("") }
//
//                                                TopNavigationBar(
//                                                        AppConstants.BIN_MAPPING_MODULE_HEADER,
//                                                        this@FirstBinMappingJetpackComposeActivity,
//                                                        homeButtonNeeded = false
//                                                )
//
//                                                SearchView(
//                                                        query = query,
//                                                        hint = "Search",
//                                                        onQueryChanged = { newquery ->
//                                                                // Handle the query change here
//                                                                query = newquery
//                                                                isSearched = if (query.isNullOrEmpty()){
//                                                                        false
//                                                                }else{
//                                                                        true
//                                                                }
//                                                                getData(query)
//                                                                println("Query changed: $query")
//                                                        },
//                                                        onClearQuery = {
//                                                                // Handle the clear action here
//                                                                println("Query cleared")
//                                                        }
//                                                )
//
//                                                PaginationListScreen(viewModel)
//
//                                        }
//                                }
//                        }
//                }
//        }
//
//        private fun getData(search : String) {
//                viewModel.getDataFromAPI(GetGRNDataResponse(pageNumber, rowsPerPage, search))
//        }
//
//
//        @Preview(showBackground = true)
//        @Composable
//        fun GreetingPreview2() {
//                SEGMK3Theme {
//                        Column(
//                                modifier = Modifier
//                                        .fillMaxWidth()
//                                        .fillMaxHeight()
//                        ) {
//
//                                TopNavigationBar(
//                                        AppConstants.BIN_MAPPING_MODULE_HEADER, this@FirstBinMappingJetpackComposeActivity,
//                                )
//
//                        }
//                }
//        }
//
//        @Composable
//        fun PaginationListScreen(viewModel: FirstPalletizationViewModel) {
//                val items1 by viewModel.allItems.observeAsState(emptyList())
//                val items2 by viewModel.searchedItems.observeAsState(emptyList())
//                val loading by viewModel.getPalletData.observeAsState(Resource.Loading())
//                val coroutineScope = rememberCoroutineScope()
//
//                var hideLoadMoreBtn : Boolean
//
//                val items = if (isSearched){
//                        items2
//                }else{
//                        items1
//                }
//
//
//                LazyColumn {
//                        // Show items
//                        items(items) { item ->
//                                item.let {
//
//                                        mappingFirstCard(
//                                                date = formatDateTime(it.cd) ?: "NA",
//                                                invoice = it.itemCode ?: "NA",
//                                                grn = it.grnNumber ?: "NA",
//                                                asnPO = it.asnCode ?: "NA",
//                                                vc = it.vendorCode ?: "NA",
//                                                vn = it.vendorName ?: "NA",
//                                                onClick = {
//                                                        val sharedPreferences: SharedPreferences = getSharedPreferences(
//                                                                "myPrefs", Context.MODE_PRIVATE
//                                                        )
//                                                        val editor = sharedPreferences.edit()
//                                                        editor.putString("grnNUMber", it.grnNumber?:"")
//                                                        editor.putString("palletizationDate", formatDateTime(it.cd) ?:"")
//                                                        editor.putString("palletizationGRN", it.grnNumber?:"")
//                                                        editor.putString("palletizationASN_PO", it.asnCode?:"")
//                                                        editor.putString("palletizationVC", it.vendorCode?:"")
//                                                        editor.putString("palletizationVN", it.vendorName?:"")
//                                                        editor.putString("palletizationQty", "${it.qty ?:""} ${it.uom?:""}")
//                                                        editor.apply()
//
//                                                        val intent = Intent(
//                                                                this@FirstBinMappingJetpackComposeActivity,
//                                                                FragmentsOnBinMappingJetpackComposeActivity::class.java
//                                                        )
//                                                        startActivity(intent)
//                                                }
//                                        )
//                                }
//                        }
//
//                        // Show loading indicator at the bottom
//                        if (loading is Resource.Loading) {
//                                item {
//                                }
//                        }
//
//                        // Show error message at the bottom
//                        if (loading is Resource.Error) {
//                                dialog.dismiss()
//
//                                item {
//                                        Text(text = "Error: ${(loading as Resource.Error).message}", color = Color.Red, modifier = Modifier.padding(16.dp))
//                                }
//                        }
//
//                        // Show "Load More" button at the bottom
//                        if (loading is Resource.Success && items.isNotEmpty()) {
//                                dialog.dismiss()
//                                item {
//                                        val checkLastPage = loading.data!!.pagination.lastPage
//                                        hideLoadMoreBtn = pageNumber == checkLastPage
//                                        if (!hideLoadMoreBtn){
//                                                Button(
//                                                        colors = ButtonDefaults.buttonColors(
//                                                                containerColor = primaryColor,
//                                                                contentColor = white
//                                                        ),
//                                                        onClick = {
//
//                                                                dialog.show()
//
//                                                                pageNumber++
//
//                                                                val lastpage =
//                                                                        loading.data!!.pagination.lastPage
//
//                                                                if (pageNumber <= lastpage) {
//
//                                                                        coroutineScope.launch {
//                                                                                viewModel.loadNextPage(
//                                                                                        GetGRNDataResponse(
//                                                                                                pageNumber,
//                                                                                                rowsPerPage,
//                                                                                                ""
//                                                                                        )
//                                                                                )
//                                                                        }
//                                                                        if(pageNumber == lastpage){
//                                                                                hideLoadMoreBtn = true
//                                                                        }
//
//                                                                }
//                                                        },
//                                                        modifier = Modifier
//                                                                .fillMaxWidth()
//                                                                .padding(16.dp)
//                                                ) {
//                                                        Text(text = "Load More")
//                                                }
//                                        }
//
//                                }
//                        }
//                }
//        }
//
//        @Composable
//        fun SearchScreen() {
//                var searchText by remember { mutableStateOf("") }
//
//                OutlinedTextField(
//                        value = searchText,
//                        onValueChange = { searchKeyword ->
//                                searchText = searchKeyword
//                                isSearched = if (searchKeyword.isNullOrEmpty()){
//                                        false
//                                }else{
//                                        true
//                                }
//                                getData(searchKeyword)
//                        },
//                        label = { Text("Search Item") },
//                        modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 10.dp, end = 10.dp, top = 3.dp, bottom = 3.dp),
//                        leadingIcon = {  // Add leadingIcon parameter
//                                Icon(
//                                        painter = painterResource(id = R.drawable.icons8_search), // Use your icon drawable
//                                        contentDescription = "Search Icon"  // Provide accessibility description
//                                )
//                        }
//                )
//
//        }
//
//}
//
//
//
