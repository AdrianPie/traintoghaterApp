package com.example.newmainproject.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmainproject.R
import com.example.newmainproject.adapters.ProfilePicturesAdapter
import com.example.newmainproject.databinding.FragmentProfileBinding
import com.example.newmainproject.models.User
import com.example.newmainproject.ui.viewmodel.FirestoreDatabaseViewModel
import com.example.newmainproject.ui.viewmodel.GoldScoreViewModel
import com.example.newmainproject.ui.viewmodel.ImagesViewModel
import com.example.newmainproject.ui.viewmodel.UserSingletonViewModel
import com.example.newmainproject.utils.Constants
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.NonDisposableHandle.parent


class ProfileFragment : Fragment(R.layout.fragment_profile), ProfilePicturesAdapter.OnItemClickListener  {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileAdapter: ProfilePicturesAdapter
    private lateinit var imagesViewModel: ImagesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseViewModel:FirestoreDatabaseViewModel
    private var userSingletonViewModel: UserSingletonViewModel = UserSingletonViewModel()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val goldScoreViewModel by viewModels<GoldScoreViewModel>()
    private var profilePicture: Int = 0
    private lateinit var user: User




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        user = userSingletonViewModel.getUser()!!
        binding.profilePicture.setImageResource(user.image)
        binding.fullName.text = user.name.toString()
        binding.goldProfil.text = goldScoreViewModel.getGoldScore().toString()
        val chart: LineChart = binding.chart

        val entries = listOf(
            Entry(0f, 2f),
            Entry(1f, 4f),
            Entry(2f, 6f),
            Entry(3f, 8f),
            Entry(4f, 10f)
        )

        val dataSet = LineDataSet(entries, "Label")

        chart.data = LineData(dataSet)
        chart.invalidate()


        binding.logout.setOnClickListener {
            auth.signOut()
        }

        databaseViewModel = FirestoreDatabaseViewModel()
        databaseViewModel.getUserInfo()

        imagesViewModel = ImagesViewModel()
        databaseViewModel = FirestoreDatabaseViewModel()
        databaseViewModel.getUserInfo()
        databaseViewModel.userInfo.observe(viewLifecycleOwner) {
            user -> profilePicture = user.image

        }

        binding.profilePicture.setOnClickListener {
            showBottomSheet()
        }


    }

    private fun showBottomSheet() {
        val dialogView = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        val dialog = layoutInflater.inflate(R.layout.bottom_sheet_profile, null)
        dialogView.setContentView(dialog)

        recyclerView = dialogView.findViewById(R.id.rc_profile_select)!!
        profileAdapter = ProfilePicturesAdapter(imagesViewModel.returnAllProfileImages(),this)
        recyclerView.adapter = profileAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dialogView.show()
    }

    //Change profile picture in firestore and ui
    override fun onItemClick(position: Int) {
        val picture = imagesViewModel.returnAllProfileImages()[position]
        binding.profilePicture.setImageResource(picture)
        databaseViewModel.updateUserInfo(picture)

        user.image = picture
        userSingletonViewModel.updateUser(user)
    }


}

