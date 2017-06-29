package viewModel;

import javafx.collections.ObservableList;
import model.ClientInfo;

public interface ViewModel {
	
	public void deleteClient(int id);
	public void setBindData(ObservableList<ClientInfo> bindedData);
	public void close();

}
