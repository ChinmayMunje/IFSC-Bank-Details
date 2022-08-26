package com.gtappdevelopers.bankdetails

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var ifscEdt: TextInputEditText
    lateinit var ifscBtn: Button
    lateinit var ifscTV: TextView
    lateinit var loadingPB: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ifscEdt = findViewById(R.id.idEdtIFSCCode)
        ifscBtn = findViewById(R.id.idBtnGetIFSC)
        ifscTV = findViewById(R.id.idTVIFSC)
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
            val branch = response.getString("BRANCH")
            val address = response.getString("ADDRESS")
            val micr = response.getString("MICR")
            val swift = response.getString("SWIFT")
            val bankCode = response.getString("BANKCODE")

            val str =
                "Bank Branch : " + branch + "\n" + "Address : " + address + "\n" + "MICR : " + micr + "\n" + "Swift : " + swift + "\n" + "Bank Code : " + bankCode
            ifscTV.text = str

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