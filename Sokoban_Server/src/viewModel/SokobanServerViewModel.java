package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import model.ClientInfo;
import model.Model;

public class SokobanServerViewModel extends Observable implements Observer, ViewModel {

	private ObservableList<ClientInfo> bindedData;
	private Model model;

	public SokobanServerViewModel(Model model) {
		this.model = model;
		try {
			model.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			model.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteClient(int id) {
		model.deleteClient(id);

	}

	@Override
	public void setBindData(ObservableList<ClientInfo> bindedData) {
		this.bindedData = bindedData;

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 == model) {
			new Thread(() -> {
				bindedData.clear();
				bindedData.addAll(model.getConnectedClients());
			}).start();
		}

	}

}
