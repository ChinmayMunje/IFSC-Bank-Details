package com.gtappdevelopers.bankdetails

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var ifscEdt: TextInputEditText
    lateinit var ifscBtn: Button
    lateinit var ifscCodeTV: TextView
    lateinit var micrCodeTV: TextView
    lateinit var stateTV: TextView
    lateinit var distTV: TextView
    lateinit var ifscRL: RelativeLayout
    lateinit var bankNameTV: TextView
    lateinit var branchTV: TextView
    lateinit var contactTV: TextView
    lateinit var addressTV: TextView
    lateinit var loadingPB: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ifscEdt = findViewById(R.id.idEdtIFSCCode)
        ifscBtn = findViewById(R.id.idBtnGetIFSC)
        ifscRL = findViewById(R.id.idRLIFSCDetails)
        ifscCodeTV = findViewById(R.id.idTVIFSCCode)
        micrCodeTV = findViewById(R.id.idTVMICRCode)
        stateTV = findViewById(R.id.idTVState)
        bankNameTV = findViewById(R.id.idTVBankName)
        distTV = findViewById(R.id.idTVDistrict)
        branchTV = findViewById(R.id.idTVBranch)
        contactTV = findViewById(R.id.idTVContact)
        addressTV = findViewById(R.id.idTVAddress)
        loadingPB = findViewById(R.id.idPBLoading)

        ifscBtn.setOnClickListener {
            loadingPB.visibility = View.VISIBLE
            if (ifscEdt.text.toString() != null) {
                getBankDetails(ifscEdt.text.toString())
            } else {
                ifscEdt.setError("Please enter valid Code")
            }
        }

    }

    private fun getBankDetails(ifscCode: String) {
        // request queue and initializing it.
        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        var apiURL = "https://ifsc.razorpay.com/" + ifscCode
        val request = JsonObjectRequest(Request.Method.GET, apiURL, null, { response ->
            loadingPB.visibility = View.GONE
            ifscRL.visibility = View.VISIBLE
            val branch = response.getString("BRANCH")
            val address = response.getString("ADDRESS")
            val micr = response.getString("MICR")
            val swift = response.getString("SWIFT")
            val bankCode = response.getString("BANKCODE")

            ifscCodeTV.text = ifscCode
            bankNameTV.text = response.getString("BANK")
            micrCodeTV.text = micr
            stateTV.text = response.getString("STATE")
            distTV.text = response.getString("DISTRICT")
            branchTV.text = branch
            contactTV.text = response.getString("CONTACT")
            addressTV.text = address

        }, { error ->
            loadingPB.visibility = View.GONE
            Toast.makeText(this@MainActivity, "Fail to get Bank Details", Toast.LENGTH_SHORT)
                .show()
        })
        // at last we are adding
        // our request to our queue.
        queue.add(request)

    }
}