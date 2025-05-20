package com.example.cuoiki.narvigation
import android.R
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.cuoiki.Trang.Kinhdoanh.*
import com.example.cuoiki.Trang.Danhmuc.*
import com.example.cuoiki.Trang.Ban.*
import com.example.cuoiki.Trang.Doanhthu.*
import com.example.cuoiki.Trang.Nhanvien.*
import com.example.cuoiki.Trang.Sanpham.*
import com.example.cuoiki.Trang.*



@Composable
fun Dieuhuong() {
    val navController = rememberNavController()


    NavHost(navController, startDestination = "Danhsachban") {

        composable("dangnhap") { dangnhap(navController)  }

        composable("chonban") { Chonban(navController) }

        composable("chonmon/{idban}"){ backStackEntry ->
            val idban = backStackEntry.arguments?.getString("idban")?.toIntOrNull()
            if (idban != null) {
                Chonmon(navController,idban)
            }
        }

        composable("donhang/{idban}"){ backStackEntry ->
            val idban = backStackEntry.arguments?.getString("idban")?.toIntOrNull()
            if (idban != null) {
                Donhang(navController, idban)
            }
        }


        //______________________________________________________

        composable("Danhsachban") { Danhsachban(navController) }

        composable("Suaban/{id}"){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Suaban(navController, id)
            }
        }

        composable("Themban"){
                Themban(navController)
        }

        //______-______________________________

        composable("Danhsachdm") { Danhsachdm(navController) }

        composable("Themdm") { Themdm(navController) }

        composable("Suadm/{id}"){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Suadm(navController, id)
            }
        }

        //_________________________________________

        composable("Doanhthu") { Doanhthu(navController) }

        //________________________________________________________________

        composable("Danhsachnv") {Danhsachnv(navController) }

        composable("Suanv/{id}"){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Suanv(navController, id)
            }
        }

        composable("Themnv") { Themnv(navController) }


        //_________________________________________________________________________________

        composable("Danhsachsp") { Danhsachsp(navController) }

        composable("Themsp") { Themsp(navController) }

        composable("Suasp/{id}"){ backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (id != null) {
                Suasp(navController, id)
            }
        }

        composable("Menu") {menu(navController) }
    }
}
