package parkingsimulator.views;

import parkingsimulator.controllers.BaseController;
import parkingsimulator.models.BaseModel;

/**
 * Created by Hilko on 18-1-2018.
 */
public abstract class BaseView<T extends BaseModel> {

	protected T model;

	public BaseView(T model, BaseController controller) {
		this.model = model;
		createPresenter(controller);
	}

	protected abstract void createPresenter(BaseController controller);

	public abstract void updateView();

}
