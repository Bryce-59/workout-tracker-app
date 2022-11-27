package com.example.android.finalproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.finalproject.R
import com.example.android.finalproject.data.WorkoutsApplication
import com.example.android.finalproject.databinding.FragmentUserBinding
import com.example.android.finalproject.model.User
import com.example.android.finalproject.ui.viewmodel.UserViewModel
import com.example.android.finalproject.ui.viewmodel.UserViewModelFactory

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((activity?.application as WorkoutsApplication).userRepository)
    }

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userViewModel.userData.value == null) {
            Toast.makeText(
                this.context,
                "user Data is empty",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        userViewModel.userData.observe(viewLifecycleOwner) {
            user = it
            bindUser()
        }
    }

    private fun bindUser() {
        binding.apply {

        }
    }
}