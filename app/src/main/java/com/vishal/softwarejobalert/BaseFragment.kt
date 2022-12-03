package com.vishal.softwarejobalert

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

class BaseFragment <VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set
    protected lateinit var window: Window

    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    @Inject
    lateinit var progress: CShowProgress
    lateinit var activity: Activity

    private var _binding : ViewBinding? = null
        get() = binding


    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = inflateMethod.invoke(null, inflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, {
            viewModelStore
        }).value
        window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showtoast(str : String) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show()

    }


    fun showprogress(){
        if(progress.mDialog?.isShowing == true){
            progress.hideProgress()
        }else{
            progress.showProgress(requireContext())
        }
    }

    fun hideProgress(){
        progress.hideProgress()
    }


}