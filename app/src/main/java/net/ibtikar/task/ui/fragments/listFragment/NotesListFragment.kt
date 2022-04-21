package net.ibtikar.task.ui.fragments.listFragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_notes.*
import net.ibtikar.task.R
import net.ibtikar.task.adapters.NotesAdapter
import net.ibtikar.task.data.local.Note
import net.ibtikar.task.ui.NotesViewModel
import javax.inject.Inject


class NotesListFragment @Inject constructor(
    val notesAdapter: NotesAdapter,
    var viewModel: NotesViewModel? = null

) : Fragment(R.layout.fragment_notes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()
        floatingBtn.setOnClickListener {
            findNavController().navigate(
                NotesListFragmentDirections
                    .actionNotesListFragmentToAddFragment(Note(null, "", "", ""))
            )
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = notesAdapter.notesList[pos]
            viewModel?.deleteNote(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel?.insertNote(item.id, item.title, item.discription, item.date)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel?.notes?.observe(viewLifecycleOwner, Observer {
            notesAdapter.notesList = it
        })
    }

    private fun setupRecyclerView() {
        rvNotes.apply {
            adapter = notesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

}


