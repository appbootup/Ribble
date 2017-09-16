package com.luseen.ribble.presentation.screen.about


import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.luseen.ribble.BuildConfig
import com.luseen.ribble.R
import com.luseen.ribble.presentation.base_mvp.base.BaseFragment
import com.luseen.ribble.presentation.widget.navigation_view.NavigationId
import com.luseen.ribble.utils.S
import com.luseen.ribble.utils.extensions.actionView
import com.luseen.ribble.utils.extensions.iconTint
import com.luseen.ribble.utils.extensions.leftIcon
import com.luseen.ribble.utils.extensions.sendEmail
import kotlinx.android.synthetic.main.fragment_about.*
import javax.inject.Inject


class AboutFragment : BaseFragment<AboutContract.View, AboutContract.Presenter>(),
        AboutContract.View, View.OnClickListener {

    private val EMAIL = 0
    private val TWITTER = 1
    private val FACEBOOK = 2
    private val GITHUB = 3

    @Inject
    protected lateinit var aboutPresenter: AboutPresenter

    private val items = mutableListOf(
            AboutItem(R.string.email, R.drawable.email, R.color.colorAccent),
            AboutItem(R.string.twitter, R.drawable.twitter, R.color.twitter),
            AboutItem(R.string.facebook, R.drawable.facebook, R.color.facebook),
            AboutItem(R.string.github, R.drawable.github, R.color.github)
    )

    override fun injectDependencies() {
        activityComponent.inject(this)
    }

    override fun layoutResId() = R.layout.fragment_about

    override fun initPresenter() = aboutPresenter

    override fun getTitle(): String {
        return NavigationId.ABOUT.name
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (3 until 10 step 2)
                .map { rootView.getChildAt(it) as TextView }
                .onEach { it.setOnClickListener(this) }
                .forEachIndexed { current, textView ->
                    val (stringRes, icon, tintColor) = items[current]
                    with(textView) {
                        text = getString(stringRes)
                        leftIcon(icon)
                        iconTint(tintColor)
                        id = current
                    }
                }
        appInfo.text = """${getString(S.app_name)} ${BuildConfig.VERSION_NAME}
            |Copyright © 2014-2017
            |Arman Chatikyan""".trimMargin()
    }

    override fun onClick(view: View) {

        when (view.id) {
            EMAIL -> {
                context.sendEmail(getString(S.app_name), getString(S.mail), getString(S.sen_us_emial))
            }
            TWITTER -> {
                context.actionView {
                    "https://twitter.com/ArmanChatikyan"
                }
            }
            FACEBOOK -> {
                context.actionView {
                    "https://web.facebook.com/chatikyana"
                }
            }
            GITHUB -> {
                context.actionView {
                    "https://github.com/armcha/Ribble"
                }
            }
        }
    }
}

data class AboutItem(val stringResId: Int, val icon: Int, val tintColor: Int = 0)
