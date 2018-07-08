package asryab.com.mvvmproject.abstracts.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates2.AdapterDelegate;
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager;

import java.util.ArrayList;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    private final AdapterDelegatesManager<T> delegatesManager = new AdapterDelegatesManager<>();
    private final ArrayList<T> listData                       = new ArrayList<>();

    public BaseRecyclerAdapter() {
        fillDelegatesManger(true);
    }

    public BaseRecyclerAdapter(final boolean isFillDelegates) {
        fillDelegatesManger(isFillDelegates);
    }

    protected void fillDelegatesManger(final boolean isFillDelegates) {
        if (isFillDelegates)
            initDelegatesManager(delegatesManager);
    }

    public void addData(final ArrayList<T> listData) {
        this.listData.addAll(listData);
        this.notifyDataSetChanged();
    }

    public void updateItem(final int position, final T object) {
        listData.set(position, object);
        notifyDataSetChanged();
    }

    public ArrayList<T> getListData() {
        return listData;
    }

    public void clearAllData() {
        this.listData.clear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(listData.get(position), position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(getItem(position), position);
    }

    private T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    protected abstract void initDelegatesManager(final AdapterDelegatesManager<T> delegatesManager);

    protected void addDelegate(final AdapterDelegate<T> adapterDelegate) {
        delegatesManager.addDelegate(adapterDelegate);
    }

    protected void deleteDelegate(final AdapterDelegate<T> adapterDelegate) {
        delegatesManager.removeDelegate(adapterDelegate);
    }

}
