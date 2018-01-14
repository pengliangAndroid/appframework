package com.wstro.app.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wstro.app.common.R;
import com.wstro.app.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengl
 */
public class BottomTabBar extends LinearLayout {

    private static final int DEFAULT_SELECTED_COLOR = 0xFFFE5977;

    private static final int DEFAULT_UNSELECTED_COLOR = 0xFF666666;

    private static final int DEFAULT_TEXT_SIZE = 12; //sp

    private static final int DEFAULT_HEIGHT_WITH_ICON = 20; // dps

    private static final int DEFAULT_GAP_TEXT = 3; // dps

    private static final int DEFAULT_GAP_MSG = 4; // dps

    private static final int DEFAULT_HEIGHT = 52; // dps

    private static final int DEFAULT_WIDTH_MSG = 46; // dps

    private int tabBackgroundColor;

    /**
     * tab高度
     */
    private int tabBarHeight;

    /**
     * Tab选中的文字颜色，默认颜色#fe5977
     */
    private int selectedColor ;

    /**
     * Tab未选中的文字颜色，默认颜色#666666
     */
    private int unSelectedColor;

    /**
     * 文字尺寸
     */
    private int fontSize;

    /**
     * tab的图片宽高
     */
    private int imgWidth,imgHeight;


    /**
     * tab的文字顶部间距
     */
    private int tabTextMarginTop;


    /**
     * tab的消息布局宽度
     */
    private int tabMsgWidth;

    /**
     * tab的消息布局顶部间距
     */
    private int tabMsgMarginTop;


    private List<TabItem> tabItemList = new ArrayList<>();

    private int selectedPos = -1;



    private int containerViewId = -1;

    private List<Fragment> fragmentList;

    private FragmentManager fragmentManager;

    public interface OnTabChangedListener {
        void onTabChanged(int position);
    }

    private OnTabChangedListener onTabChangedListener;


    public BottomTabBar(Context context) {
        this(context, null);
    }

    public BottomTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomTabBar);
        if(array != null){
            tabBackgroundColor = array.getColor(R.styleable.BottomTabBar_tab_bar_background, Color.WHITE);
            selectedColor = array.getColor(R.styleable.BottomTabBar_tab_selected_color, DEFAULT_SELECTED_COLOR);
            unSelectedColor = array.getColor(R.styleable.BottomTabBar_tab_unselected_color, DEFAULT_UNSELECTED_COLOR);

            tabBarHeight =  array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_bar_height, dp2px(DEFAULT_HEIGHT));
            fontSize = array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_font_size, dp2px(DEFAULT_TEXT_SIZE));
            imgWidth =  array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_img_width, dp2px(DEFAULT_HEIGHT_WITH_ICON));
            imgHeight = array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_img_height, dp2px(DEFAULT_HEIGHT_WITH_ICON));

            tabTextMarginTop =  array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_text_margin_top,dp2px(DEFAULT_GAP_TEXT));
            tabMsgMarginTop =  array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_msg_margin_top,dp2px(DEFAULT_GAP_MSG));
            tabMsgWidth =  array.getDimensionPixelSize(R.styleable.BottomTabBar_tab_msg_width, dp2px(DEFAULT_WIDTH_MSG));

            array.recycle();
        }

        initTabBar();
    }

    private void initTabBar(){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                tabBarHeight);
        setLayoutParams(layoutParams);
        setBackgroundColor(tabBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(heightMode == MeasureSpec.AT_MOST){
            heightSize = tabBarHeight;
        }

        setMeasuredDimension(widthMeasureSpec,MeasureSpec.makeMeasureSpec(heightSize,heightMode));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.d("width:"+w+",height:"+h);
    }


    public BottomTabBar addTabItem(TabItem item){
        addTabItemView(item);
        return this;
    }

    public BottomTabBar setupFragmets(int containerViewId, FragmentManager fm, List<Fragment> fragments){
        if (fm == null)
            throw new RuntimeException("FragmentManager is null");

        if (fragments == null)
            throw new RuntimeException("fragments is null");

        this.containerViewId = containerViewId;
        this.fragmentManager = fm;
        this.fragmentList = fragments;
        return this;
    }

    public void setCurrentTab(int position){
        if (selectedPos == position)
            return;

        selectedPos = position;

        setSelectedFragment(position);
        for (int i = 0; i < tabItemList.size(); i++) {
            setSelectedTabItem(tabItemList.get(i),position);
        }

        if(onTabChangedListener != null){
            onTabChangedListener.onTabChanged(position);
        }
    }



    public BottomTabBar setOnTabChangeListener(OnTabChangedListener listener) {
        this.onTabChangedListener = listener;
        return this;
    }


    public BottomTabBar setTabBarHeight(int tabBarHeight) {
        this.tabBarHeight = tabBarHeight;
        return this;
    }

    public BottomTabBar setTextColor(int selectedColor,int unSelectedColor) {
        this.selectedColor = selectedColor;
        this.unSelectedColor = unSelectedColor;
        return this;
    }

    public BottomTabBar setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public BottomTabBar setImgSize(int imgWidth,int imgHeight) {
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        return this;
    }

    public BottomTabBar setTabTextMarginTop(int tabTextMarginTop) {
        this.tabTextMarginTop = tabTextMarginTop;
        return this;
    }

    public BottomTabBar setTabMsgWidth(int tabMsgWidth) {
        this.tabMsgWidth = tabMsgWidth;
        return this;
    }

    public BottomTabBar setTabMsgMarginTop(int tabMsgMarginTop) {
        this.tabMsgMarginTop = tabMsgMarginTop;
        return this;
    }

    public int getSelectPosition() {
        return selectedPos;
    }

    public TabViewHolder getTabViewHolder(int index){
        TabItem item = tabItemList.get(index);
        if(item == null || item.getTabViewHolder() == null)
            return null;

        return tabItemList.get(index).getTabViewHolder();
    }


    private final OnClickListener internalListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer index = (Integer) v.getTag();

            setCurrentTab(index);
        }
    };

    public TabItem newTabItem(String text, int selectedResId, int unselectedResID){
        return newTabItem(text, ContextCompat.getDrawable(getContext(), selectedResId),
                ContextCompat.getDrawable(getContext(), unselectedResID));
    }

    public TabItem newTabItem(String text, Drawable selectedIcon, Drawable unselectedIcon){
        TabItem item = new TabItem(text, selectedIcon, unselectedIcon);
        return item;
    }

    private void addTabItemView(TabItem tabItem){
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_tab_bar_item, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,1.0f);
        itemView.setLayoutParams(lp);

        TabViewHolder viewHolder = new TabViewHolder();
        viewHolder.msgLayout = (FrameLayout) itemView.findViewById(R.id.fl_msg_layout);
        viewHolder.msgView = (ImageView) itemView.findViewById(R.id.iv_msg);
        viewHolder.iconView = (ImageView) itemView.findViewById(R.id.tab_bar_img);
        viewHolder.textView = (TextView) itemView.findViewById(R.id.tab_bar_tv);

        viewHolder.iconView.setLayoutParams(new LayoutParams(imgWidth,imgHeight));
        viewHolder.iconView.setImageDrawable(tabItem.getUnselectedIcon());

        MarginLayoutParams params = (MarginLayoutParams) viewHolder.textView.getLayoutParams();
        params.topMargin = tabTextMarginTop;
        viewHolder.textView.setLayoutParams(params);
        viewHolder.textView.setText(tabItem.getText());
        viewHolder.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,fontSize);
        viewHolder.textView.setTextColor(unSelectedColor);

        viewHolder.msgLayout.setLayoutParams(new RelativeLayout.LayoutParams(tabMsgWidth,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        MarginLayoutParams params2 = (MarginLayoutParams) viewHolder.msgView.getLayoutParams();
        params2.topMargin = tabMsgMarginTop;
        viewHolder.msgView.setLayoutParams(params2);

        itemView.setTag(tabItemList.size());
        itemView.setOnClickListener(internalListener);

        tabItem.setTabViewHolder(viewHolder);
        tabItem.setPosition(tabItemList.size());
        tabItemList.add(tabItem);

        addView(itemView);
    }

    private void setSelectedTabItem(TabItem item,int position){
        if(item == null || item.getTabViewHolder() == null)
            return;

        TabViewHolder holder = item.getTabViewHolder();
        boolean selected = (position == item.getPosition());

        holder.iconView.setImageDrawable(selected ? item.getSelectIcon() : item.getUnselectedIcon());
        holder.textView.setTextColor(selected ? selectedColor : unSelectedColor);
    }

    private void setSelectedFragment(int position) {
        if(fragmentList == null || fragmentManager == null)
            return;

        Fragment fragment = fragmentList.get(position);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        if(fragmentManager.getFragments() != null && fragmentManager.getFragments().contains(fragment)){
            transaction.show(fragment);
        }else{
            transaction.add(containerViewId, fragment, fragment.getClass().getSimpleName());
            transaction.show(fragment);
        }

        transaction.commitAllowingStateLoss();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if(fragmentList == null)
            return;

        for (int i = 0; i < fragmentList.size(); i++) {
            if(fragmentList.get(i) == null)
                continue;

            transaction.hide(fragmentList.get(i));
        }
    }

    private int dp2px(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    public void clear(){
        fragmentList = null;
        fragmentManager = null;
        tabItemList = null;
    }

    public static class TabItem{
        private Object tag;

        private CharSequence text;

        private Drawable selectIcon;

        private Drawable unselectedIcon;

        private int position = -1;

        private TabViewHolder tabViewHolder;

        public TabItem(CharSequence text, Drawable selectIcon, Drawable unselectedIcon) {
            this.text = text;
            this.selectIcon = selectIcon;
            this.unselectedIcon = unselectedIcon;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }

        public CharSequence getText() {
            return text;
        }

        public void setText(CharSequence text) {
            this.text = text;
        }

        public Drawable getSelectIcon() {
            return selectIcon;
        }

        public void setSelectIcon(Drawable selectIcon) {
            this.selectIcon = selectIcon;
        }

        public Drawable getUnselectedIcon() {
            return unselectedIcon;
        }

        public void setUnselectedIcon(Drawable unselectedIcon) {
            this.unselectedIcon = unselectedIcon;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public TabViewHolder getTabViewHolder() {
            return tabViewHolder;
        }

        public void setTabViewHolder(TabViewHolder tabViewHolder) {
            this.tabViewHolder = tabViewHolder;
        }
    }

    static class TabViewHolder{
        public ImageView iconView;
        public TextView textView;
        public FrameLayout msgLayout;
        public ImageView msgView;

        public TabViewHolder(){}

        public TabViewHolder(ImageView iconView, TextView textView, FrameLayout msgLayout, ImageView msgView) {
            this.iconView = iconView;
            this.textView = textView;
            this.msgLayout = msgLayout;
            this.msgView = msgView;
        }
    }

}
