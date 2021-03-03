package com.synerzip.currentweather.ui.weather.current

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.synerzip.currentweather.databinding.CurrentWeatherFragmentBinding
import com.synerzip.currentweather.util.getAppComponent
import com.synerzip.currentweather.util.onSubmit
import javax.inject.Inject

class CurrentWeatherFragment : Fragment() {

    private lateinit var mBinding: CurrentWeatherFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CurrentWeatherViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.viewModel = viewModel
        initView()
        initObservers()
    }

    private fun initView() {
        // Configuring the search EditText to search when users enter on the soft keyboard
        mBinding.etSearch.onSubmit {
            try {
                viewModel.fetchWeatherForecastForCity(mBinding.etSearch.text.toString())
                mBinding.tiSearch.error = null
            } catch (e: IllegalArgumentException) {
                mBinding.tiSearch.error = e.message
            }
        }
    }


    private fun initObservers() {
        viewModel.run {
            toastMessage.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Dagger injection init
        requireActivity().getAppComponent()?.inject(this)
    }

}
