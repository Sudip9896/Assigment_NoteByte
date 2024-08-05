package com.raj.notehype.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.raj.notehype.MainActivity
import com.raj.notehype.R
import com.raj.notehype.databinding.FragmentEditNoteBinding
import com.raj.notehype.model.Note
import com.raj.notehype.viewmodel.NoteViewModel


class Edit_note_Fragment : Fragment(R.layout.fragment_edit_note_), MenuProvider {

    private var editNoteBinding: FragmentEditNoteBinding? = null
    private val binding get() = editNoteBinding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var currentNote: Note

    private val args : Edit_note_FragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate##
        editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.noteTitle)
        binding.editNoteDesc.setText(currentNote.noteDesc)

        binding.editNoteFab.setOnClickListener {
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()
            val userEmail = FirebaseAuth.getInstance().currentUser?.email ?: ""

            //chekcing if  title  isempty
            if (noteTitle.isNotEmpty()) {

                val note = Note(currentNote.id,noteTitle,noteDesc, userEmail)
                noteViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)



            }else {
                Toast.makeText(context,"please Enter Note TItle",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private  fun deleteNote(){

        AlertDialog.Builder(activity).apply {

            setTitle("Delete Note")
            setMessage("Do you want  to delete this  note")
            setPositiveButton("delete"){_,_->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context, "Please Enter the Note title", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel", null)

        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return when (menuItem.itemId){

           R.id.deleteMenu -> {
               deleteNote()
               true

           }else ->false
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding = null

    }


}
