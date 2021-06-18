package com.example.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private lateinit var beatBoxViewModel: BeatBoxViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
                .apply {
                    playbackViewModel = PlaybackViewModel()
                }
        val beatBoxViewModelFactory = BeatBoxViewModelFactory(assets)
        beatBoxViewModel =
            ViewModelProvider(this, beatBoxViewModelFactory).get(BeatBoxViewModel::class.java)
        mainActivityViewModel.soundRateLiveData.observe(this@MainActivity, { soundRate ->
            binding?.playbackViewModel?.rate = soundRate
            beatBoxViewModel.beatBox.rate = soundRate / 100f
        })
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundRecyclerViewAdapter(beatBoxViewModel.beatBox.sounds)
        }
        binding.playbackSpeedSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mainActivityViewModel.soundRateLiveData.value = progress
                beatBoxViewModel.beatBox.rate = progress / 100f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private inner class SoundViewHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings() // Immediate refreshing RecyclerView (instead pending 1-2 ms)
            }
        }
    }

    private inner class SoundRecyclerViewAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundViewHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundViewHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundViewHolder, position: Int) {
            holder.bind(sounds[position])
        }

        override fun getItemCount() = sounds.size
    }
}