package com.example.uaskel1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.google.firebase.database.*

class MeriasAdminFragment : Fragment() {

    private lateinit var videoListLayout: LinearLayout
    private lateinit var databaseRef: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_merias_admin, container, false)
        videoListLayout = view.findViewById(R.id.listvideomerias)

        val firebaseDatabase = FirebaseDatabase.getInstance()
        databaseRef = firebaseDatabase.reference.child("merias")

        val btnPilihVideo: Button = view.findViewById(R.id.btnPilihVideo)
        btnPilihVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            startActivityForResult(Intent.createChooser(intent, "Pilih Video"), PICK_VIDEO_REQUEST)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        showVideosFromFirebase()
    }

    private fun showVideosFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                videoListLayout.removeAllViews() // Hapus tampilan sebelumnya jika ada

                for (videoSnapshot in snapshot.children) {
                    val videoUrl = videoSnapshot.child("videoUrl").getValue(String::class.java)
                    videoUrl?.let {
                        val videoUri = Uri.parse(it)
                        addVideoToLayout(videoUri)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun addVideoToLayout(videoUri: Uri) {
        val newVideoView = VideoView(requireContext())
        newVideoView.setVideoURI(videoUri)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 8, 0, 8)

        newVideoView.layoutParams = layoutParams
        newVideoView.setOnClickListener {
            // Memainkan video saat diklik
            newVideoView.start()
        }
        videoListLayout.addView(newVideoView)
    }

    companion object {
        private const val PICK_VIDEO_REQUEST = 1
    }
}





