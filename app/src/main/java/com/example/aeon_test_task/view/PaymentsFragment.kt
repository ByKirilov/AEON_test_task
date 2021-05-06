package com.example.aeon_test_task.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aeon_test_task.AEONApplication
import com.example.aeon_test_task.R
import com.example.aeon_test_task.databinding.FragmentPaymentsBinding
import com.example.aeon_test_task.viewModel.PaymentsViewModel
import com.example.aeon_test_task.viewModel.PaymentsViewModelFactory

class PaymentsFragment : Fragment() {

    private lateinit var binding: FragmentPaymentsBinding
    private val viewModel: PaymentsViewModel by viewModels {
        PaymentsViewModelFactory((activity?.application as AEONApplication).repository)
    }

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(TOKEN)
        }
        if (savedInstanceState == null) {
            token?.let { viewModel.getPayments(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsBinding.inflate(layoutInflater)

        val adapter = PaymentListAdapter.getAdapter()

        binding.paymentRecyclerview.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
            requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, AuthFragment.newInstance(true))
                    .remove(this@PaymentsFragment)
                    .addToBackStack("payments")
                    .commit()
        }

        viewModel.payments.observe(viewLifecycleOwner) { payments ->
            payments?.let {
                adapter.submitList(it)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading: Boolean ->
            binding.loading.visibility = when (isLoading) {
                true -> View.VISIBLE
                false -> View.GONE
            }
            binding.paymentRecyclerview.visibility = when (isLoading) {
                true -> View.GONE
                false -> View.VISIBLE
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(token: String) =
            PaymentsFragment().apply {
                arguments = Bundle().apply {
                    putString(TOKEN, token)
                }
            }

        const val TOKEN = "token"
    }
}