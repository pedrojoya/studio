package es.iessaladillo.pedrojoya.pr224.base

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ANIMATION_DURATION: Long = 200

@Suppress("unused")
class ScrollAwareFabBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior() {

    private var isHidden: Boolean = false

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton, directTargetChild: View, target: View,
                                     nestedScrollAxes: Int, type: Int): Boolean {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
                coordinatorLayout, child, directTargetChild, target, nestedScrollAxes, type)
    }


    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton, target: View, dxConsumed: Int,
                                dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed, type)
        if (dyConsumed > 0 && !isHidden) {
            child.animate().scaleX(0f).scaleY(0f).rotation(-90f).setDuration(ANIMATION_DURATION)
                    .setListener(
                            object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {
                                    isHidden = true
                                }

                                override fun onAnimationEnd(animation: Animator) {}

                                override fun onAnimationCancel(animation: Animator) {

                                }

                                override fun onAnimationRepeat(animation: Animator) {

                                }
                            })

        } else if (dyConsumed < 0 && isHidden) {
            child.animate().scaleX(1f).scaleY(1f).rotation(0f).setDuration(ANIMATION_DURATION)
                    .setListener(
                            object : Animator.AnimatorListener {
                                override fun onAnimationStart(animation: Animator) {
                                    isHidden = false
                                }

                                override fun onAnimationEnd(animation: Animator) {}

                                override fun onAnimationCancel(animation: Animator) {

                                }

                                override fun onAnimationRepeat(animation: Animator) {

                                }
                            })
        }
    }

}