package com.example.note_frontend.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.note_frontend.NoteApplication
import com.example.note_frontend.R
import com.example.note_frontend.databinding.FragmentAddOrEditBinding
import com.example.note_frontend.model.Note
import com.example.note_frontend.repository.NoteRepository
import com.example.note_frontend.viewModel.NoteViewModel
import com.example.note_frontend.viewModel.NoteViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddOrEditFragment : Fragment() {

    lateinit var binding: FragmentAddOrEditBinding
    private val args: AddOrEditFragmentArgs by navArgs()
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(NoteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddOrEditBinding.inflate(inflater, container, false)
        val note = args.note
        if (note == null) {
            binding.btnDone.setOnClickListener {
                val stamp = Timestamp(System.currentTimeMillis()) // from java.sql.timestamp
                val date = Date(stamp.time)

                val sdf = SimpleDateFormat("dd MMM yyyy hh:mm a")

                sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

                val formattedDateTime = sdf.format(date)
                val newNote = Note(
                    title = binding.etTitle.text.toString(),
                    description = binding.etTitleDescription.text.toString(),
                    time = formattedDateTime,
                    color = randomColor()
                )
                noteViewModel.insert(newNote)
                it.findNavController().popBackStack()
            }
        } else {
            binding.etTitle.setText(note.title)
            binding.etTitleDescription.setText(note.description)
            binding.btnDone.setOnClickListener {
                val newNote = Note(
                    id = note.id,
                    title = binding.etTitle.text.toString(),
                    description = binding.etTitleDescription.text.toString(),
                    time = note.time,
                    color = note.color
                )

                noteViewModel.update(newNote)
                it.findNavController().popBackStack()
            }
        }

        binding.btnBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        return binding.root
    }

    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.card)
        list.add(R.color.card1)
        list.add(R.color.card2)
        list.add(R.color.card3)
        list.add(R.color.card4)
        list.add(R.color.card5)
        list.add(R.color.card6)
        list.add(R.color.card7)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

}