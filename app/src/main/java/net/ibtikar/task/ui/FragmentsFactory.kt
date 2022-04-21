package net.ibtikar.task.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import net.ibtikar.task.adapters.NotesAdapter
import net.ibtikar.task.ui.fragments.addFragment.AddFragment
import net.ibtikar.task.ui.fragments.listFragment.NotesListFragment
import javax.inject.Inject

class FragmentsFactory @Inject constructor(
    private val notesAdapter: NotesAdapter
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AddFragment::class.java.name -> AddFragment()
            NotesListFragment::class.java.name -> NotesListFragment(notesAdapter)
            else -> return super.instantiate(classLoader, className)
        }
    }
}