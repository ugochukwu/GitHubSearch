package utils

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor


class RxSchedulersOverride(private val scheduler: Scheduler = object : Scheduler() {
    override fun createWorker(): Worker = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
}) : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
                RxJavaPlugins.setIoSchedulerHandler { scheduler }
                RxJavaPlugins.setInitComputationSchedulerHandler { scheduler }
                RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler }
                RxJavaPlugins.setInitSingleSchedulerHandler { scheduler }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }

                base?.evaluate()

                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }
        }
    }
}