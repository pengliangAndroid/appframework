package com.wstro.app.common.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.ViewHolder;
import com.wstro.app.common.R;
import com.wstro.app.common.base.CommonAdapter;

import java.util.ArrayList;


/**
 * ClassName: BottomSelectDialog
 * Function:
 * Date:     2017/9/22 0020 14:01
 *
 * @author Administrator
 * @see
 */
public class BottomSelectDialog extends BaseNiceDialog {

    public interface OnItemSelectListener{
        void onItemSelect(int position);
    }

    RecyclerView recyclerView;

    CommonAdapter<String> adapter;

    ArrayList<String> itemList;

    OnItemSelectListener listener;

    public static BottomSelectDialog newInstance(ArrayList<String> itemList) {
        Bundle bundle = new Bundle();
        BottomSelectDialog dialog = new BottomSelectDialog();
        bundle.putStringArrayList("data",itemList);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        itemList = bundle.getStringArrayList("data");
    }

    @Override
    public int intLayoutId() {
        return R.layout.dailog_bottom_select;
    }

    @Override
    public void convertView(ViewHolder holder, final BaseNiceDialog dialog) {
        recyclerView = holder.getView(R.id.recycler_view);
        View cancelView = holder.getView(R.id.tv_cancel);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        adapter = new CommonAdapter<String>(R.layout.list_select_item,itemList) {
            @Override
            public void convertViewItem(BaseViewHolder holder, String item) {
                holder.setText(R.id.tv_name,item);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(listener != null)
                    listener.onItemSelect(position);
                dismiss();
            }
        });
    }

    public BottomSelectDialog setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
        return this;
    }
}
