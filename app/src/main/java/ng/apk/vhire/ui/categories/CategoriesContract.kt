package ng.apk.vhire.ui.categories

import ng.apk.vhire.models.Service
import ng.apk.vhire.ui.base.BasePresenter
import ng.apk.vhire.ui.base.BaseView

/**
 * Created by othree on 2/17/18.
 */

public interface CategoriesContract {

    public interface View : BaseView<Presenter> {

        fun updateServiceList(services: List<Service>)

    }

    public interface Presenter : BasePresenter {
        fun fetchServices(id: Int)
    }
}
