package com.company.voicenotes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.company.voicenotes.databinding.FragmentRecorderBinding
import com.company.voicenotes.di.RecorderFragmentComponent
import com.company.voicenotes.di.RecorderViewComponent
import com.company.voicenotes.model.RecordViewModel

class RecorderFragment : Fragment() {


    private var _binding: FragmentRecorderBinding? = null
    private val binding get() = _binding!!

    private lateinit var recorderFragmentComponent: RecorderFragmentComponent
    private lateinit var recorderViewComponent: RecorderViewComponent
    private lateinit var recorderViewController: RecorderViewController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = (requireActivity() as MainActivity).appComponent
        val viewModel: RecordViewModel by viewModels {
            component.viewModelFactory
        }
        recorderFragmentComponent = component.recorderFragmentComponent().create(this, viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRecorderBinding.inflate(inflater, container, false)

        recorderViewComponent = recorderFragmentComponent.recorderViewComponentComponent().create(
            root = binding,
            viewLifecycleOwner = viewLifecycleOwner
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recorderViewController = recorderViewComponent.recorderViewController
        recorderViewController.checkPermissions()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

// todo: папка сейва, кнопка play,stop, может быть длительность