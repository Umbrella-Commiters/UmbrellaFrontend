package eu.eurofel.web.commons;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

public class GenericSelectModel<T> extends AbstractSelectModel implements
		ValueEncoder<T> {
	private List<T> list;

	public GenericSelectModel(List<T> list) {
		this.list = list;
	}

	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	public List<OptionModel> getOptions() {
		List<OptionModel> optionModelList = new ArrayList<OptionModel>();
		for (T obj : list) {
			optionModelList.add(new OptionModelImpl(nvl(obj), obj));
		}
		return optionModelList;
	}

	public List<T> getList() {
		return list;
	}

	// ValueEncoder methods
	public String toClient(T obj) {
			return obj.toString() + "";
	}

	public T toValue(String string) {
			for (T obj : list) {
				if (nvl(obj).equals(string))
					return obj;
			}
		return null;
	}

	private String nvl(Object o) {
		if (o == null)
			return "";
		else
			return o.toString();
	}
}
