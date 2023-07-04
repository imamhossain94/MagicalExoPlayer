package com.potyvideo.library

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.android.exoplayer2.ui.PlayerView
import com.potyvideo.library.globalEnums.*
import com.potyvideo.library.utils.DoubleClick

abstract class AndExoPlayerRoot @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var inflatedView: View = inflate(context, R.layout.layout_player_base_kotlin, this)

    var playerView: PlayerView
    var retryView: LinearLayout
    var retryViewTitle: TextView
    var position: AppCompatTextView
    var duration: AppCompatTextView
    var progress: DefaultTimeBar
    var retryButton: Button
    var backwardView: AppCompatButton
    var forwardView: AppCompatButton
    var play: AppCompatImageButton
    var pause: AppCompatImageButton
    var mute: AppCompatImageButton
    var unMute: AppCompatImageButton
    var settingContainer: FrameLayout
    var fullScreenContainer: FrameLayout
    var enterFullScreen: AppCompatImageButton
    var exitFullScreen: AppCompatImageButton
    var leadingContainer: FrameLayout
    var videoTitle: AppCompatTextView
    var actionDownloadContainer: FrameLayout
    var actionDeleteContainer: FrameLayout
    var actionShareContainer: FrameLayout
    var leadingButton: AppCompatImageButton
    var downloadButton: AppCompatImageButton
    var deleteButton: AppCompatImageButton
    var shareButton: AppCompatImageButton
    private var containerHeader:LinearLayout
    private var containerControllers: ConstraintLayout


    abstract var customClickListener: DoubleClick

    var currAspectRatio: EnumAspectRatio = EnumAspectRatio.ASPECT_16_9
    var currRepeatMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF
    var currPlayerSize: EnumPlayerSize = EnumPlayerSize.EXACTLY
    var currResizeMode: EnumResizeMode = EnumResizeMode.FILL
    var currMute: EnumMute = EnumMute.UNMUTE
    var currPlaybackSpeed: EnumPlaybackSpeed = EnumPlaybackSpeed.NORMAL
    var currScreenMode: EnumScreenMode = EnumScreenMode.MINIMISE

    init {

        // views
        playerView = inflatedView.findViewById(R.id.playerView)
        retryView = inflatedView.findViewById(R.id.retry_view)
        position = inflatedView.findViewById(R.id.exo_position)
        duration = inflatedView.findViewById(R.id.exo_duration)
        progress = inflatedView.findViewById(R.id.exo_progress)
        backwardView = inflatedView.findViewById(R.id.exo_backward)
        forwardView = inflatedView.findViewById(R.id.exo_forward)
        retryViewTitle = retryView.findViewById(R.id.textView_retry_title)
        retryButton = retryView.findViewById(R.id.button_try_again)
        play = playerView.findViewById(R.id.exo_play)
        pause = playerView.findViewById(R.id.exo_pause)
        mute = playerView.findViewById(R.id.exo_mute)
        unMute = playerView.findViewById(R.id.exo_unmute)
        settingContainer = playerView.findViewById(R.id.container_setting)
        fullScreenContainer = playerView.findViewById(R.id.container_fullscreen)
        enterFullScreen = playerView.findViewById(R.id.exo_enter_fullscreen)
        exitFullScreen = playerView.findViewById(R.id.exo_exit_fullscreen)
        leadingContainer = playerView.findViewById(R.id.container_leading)
        videoTitle = playerView.findViewById(R.id.exo_video_title)
        actionDownloadContainer = playerView.findViewById(R.id.container_action_download)
        actionDeleteContainer = playerView.findViewById(R.id.container_action_delete)
        actionShareContainer = playerView.findViewById(R.id.container_action_share)
        leadingButton = playerView.findViewById(R.id.exo_leading)
        downloadButton = playerView.findViewById(R.id.action_download)
        deleteButton = playerView.findViewById(R.id.action_delete)
        shareButton = playerView.findViewById(R.id.action_share)
        containerHeader = playerView.findViewById(R.id.container_header)
        containerControllers = playerView.findViewById(R.id.container_controllers)

        // listeners
        initListeners()
    }

    private fun initListeners() {
        retryButton.setOnClickListener(customClickListener)
        backwardView.setOnClickListener(customClickListener)
        forwardView.setOnClickListener(customClickListener)
        mute.setOnClickListener(customClickListener)
        unMute.setOnClickListener(customClickListener)
        fullScreenContainer.setOnClickListener(customClickListener)
        enterFullScreen.setOnClickListener(customClickListener)
        exitFullScreen.setOnClickListener(customClickListener)
        exitFullScreen.setOnClickListener(customClickListener)
        leadingButton.setOnClickListener(customClickListener)
        downloadButton.setOnClickListener(customClickListener)
        deleteButton.setOnClickListener(customClickListener)
        shareButton.setOnClickListener(customClickListener)
    }

    protected fun showRetryView() {
        showRetryView(null)
    }

    protected fun showRetryView(retryTitle: String?) {
        retryView.visibility = VISIBLE
        if (retryTitle != null)
            retryViewTitle.text = retryTitle
    }

    protected fun hideRetryView() {
        retryView.visibility = GONE
    }

    protected fun showLoading() {
        hideRetryView()
    }

    protected fun hideLoading() {
        hideRetryView()
    }

    protected fun setShowController(showController: Boolean = true) {
        if (showController)
            showController()
        else
            hideController()
    }

    protected fun showController() {
        playerView.showController()
    }

    protected fun hideController() {
        playerView.hideController()
    }

    protected fun showUnMuteButton() {
        mute.visibility = GONE
        unMute.visibility = VISIBLE
    }

    protected fun showMuteButton() {
        mute.visibility = VISIBLE
        unMute.visibility = GONE
    }

    protected fun setShowSettingButton(showSetting: Boolean = false) {
        if (showSetting)
            settingContainer.visibility = VISIBLE
        else
            settingContainer.visibility = GONE
    }

    protected fun setShowFullScreenButton(showFullscreenButton: Boolean = false) {
        if (showFullscreenButton)
            fullScreenContainer.visibility = VISIBLE
        else
            fullScreenContainer.visibility = GONE
    }

    protected fun setShowLeadingButton(value: Boolean = false) {
        if (value)
            leadingContainer.visibility = VISIBLE
        else
            leadingContainer.visibility = GONE
    }

    protected fun setVideoTitle(tile: String = "Video player") {
        videoTitle.text = tile
    }

    protected fun setShowDownloadButton(value: Boolean = false) {
        if (value)
            actionDownloadContainer.visibility = VISIBLE
        else
            actionDownloadContainer.visibility = GONE
    }

    protected fun setShowDeleteButton(value: Boolean = false) {
        if (value)
            actionDeleteContainer.visibility = VISIBLE
        else
            actionDeleteContainer.visibility = GONE
    }

    protected fun setShowShareButton(value: Boolean = false) {
        if (value)
            actionShareContainer.visibility = VISIBLE
        else
            actionShareContainer.visibility = GONE
    }

    protected fun setPlayerBackgroundColor(color: Int) {
        inflatedView.setBackgroundColor(color)
        playerView.setBackgroundColor(color)
    }

    protected fun setControllerBackgroundColor(color: Int) {
        containerHeader.setBackgroundColor(color)
        containerControllers.setBackgroundColor(color)
    }

    protected fun setPlayerForegroundColor(color: Int) {

        val colorStateList = ColorStateList.valueOf(color)

        // Container header
        videoTitle.setTextColor(colorStateList)
        leadingButton.imageTintList = colorStateList
        downloadButton.imageTintList = colorStateList
        deleteButton.imageTintList = colorStateList
        shareButton.imageTintList = colorStateList

        // Container controller
        play.imageTintList = colorStateList
        pause.imageTintList = colorStateList
        mute.imageTintList = colorStateList
        unMute.imageTintList = colorStateList
        enterFullScreen.imageTintList = colorStateList
        exitFullScreen.imageTintList = colorStateList
        position.setTextColor(colorStateList)
        duration.setTextColor(colorStateList)

        // Update progress tint
        progress.setPlayedColor(ColorUtils.setAlphaComponent(color, 200))
        progress.setUnplayedColor(ColorUtils.setAlphaComponent(color, 128))
        progress.setScrubberColor(color)
        progress.setBufferedColor(ColorUtils.setAlphaComponent(color, 178))
    }

    protected fun setShowScreenModeButton(screenMode: EnumScreenMode = EnumScreenMode.MINIMISE) {
        when (screenMode) {
            EnumScreenMode.FULLSCREEN -> {
                enterFullScreen.visibility = GONE
                exitFullScreen.visibility = VISIBLE
            }
            EnumScreenMode.MINIMISE -> {
                enterFullScreen.visibility = VISIBLE
                exitFullScreen.visibility = GONE
            }
            else -> {
                enterFullScreen.visibility = VISIBLE
                exitFullScreen.visibility = GONE
            }
        }
    }

    protected fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = (context as? Activity)?.window?.insetsController
            windowInsetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            (context as? Activity)?.window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }

    protected fun showSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsetsController = (context as? Activity)?.window?.insetsController
            windowInsetsController?.show(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            (context as? Activity)?.window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

}
