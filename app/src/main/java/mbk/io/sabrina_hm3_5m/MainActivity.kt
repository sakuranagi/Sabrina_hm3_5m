package mbk.io.sabrina_hm3_5m

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import mbk.io.sabrina_hm3_5m.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var adapter = ImageAdapter(arrayListOf())
    var page = 1
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
        binding.recyclerView.adapter = adapter
    }

    private fun initClickers() {
        with(binding) {
            btnNext.setOnClickListener {
                page++
                getImages(page)
            }
            btnSearch.setOnClickListener {
                getImages(1)
            }
        }
    }

    private fun ActivityMainBinding.getImages(page: Int) {
        progressView.isVisible = true
        RetrofitService().api.getImages(etWord.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(
                    call: Call<PixaModel>,
                    response: Response<PixaModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            progressView.isVisible = false
                            adapter.addNewImages(it.hits as ArrayList<ImageModel>)
                        }
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}