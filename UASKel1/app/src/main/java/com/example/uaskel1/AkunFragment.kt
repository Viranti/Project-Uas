package com.example.uaskel1

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.example.uaskel1.databinding.FragmentAkunBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.bumptech.glide.Glide

class AkunFragment : Fragment() {

    private lateinit var binding: FragmentAkunBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageRef: StorageReference
    private lateinit var userRef: DatabaseReference
    private val PICK_IMAGE_REQUEST = 1
    private var isFragmentAttached = false // Variabel untuk menandai fragment terpasang

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFragmentAttached = true // Set variabel bahwa fragment terpasang
        firebaseAuth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().reference
        userRef = FirebaseDatabase.getInstance().getReference("user")

        displayUserInfo()

        binding.gantifotoprofil.setOnClickListener {
            changeProfileImage()
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    private fun logout() {
        firebaseAuth.signOut()
        val i = Intent(requireContext(), SplashActivity::class.java)
        startActivity(i)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentAttached = false // Set variabel bahwa fragment sudah tidak terpasang lagi
    }

    private fun displayUserInfo() {
        val user = firebaseAuth.currentUser
        val profileImageUri = user?.photoUrl
        val userId = user?.uid

        userId?.let {
            userRef.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isFragmentAttached) { // Pastikan fragment masih terpasang sebelum menggunakan konteks
                        if (snapshot.exists()) {
                            val email = snapshot.child("email").getValue(String::class.java)
                            val username = snapshot.child("nama").getValue(String::class.java)

                            binding.edtEmail.setText(email)
                            binding.edtUsername.setText(username)

                            profileImageUri?.let {
                                Glide.with(requireContext())
                                    .load(profileImageUri)
                                    .placeholder(R.drawable.fotoprofil)
                                    .error(R.drawable.fotoprofil)
                                    .into(binding.imageViewProfile)
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    private fun changeProfileImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            uploadImageToFirebaseStorage(imageUri)
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri) {
        val user = firebaseAuth.currentUser
        val userId = user?.uid

        userId?.let {
            val profileImageRef = storageRef.child("profile_images/$it.jpg")

            profileImageRef.putFile(imageUri)
                .addOnSuccessListener { _ ->
                    profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(uri)
                            .build()

                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful && isFragmentAttached) {
                                    displayUserInfo()
                                }
                            }
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failure
                }
        }
    }
}