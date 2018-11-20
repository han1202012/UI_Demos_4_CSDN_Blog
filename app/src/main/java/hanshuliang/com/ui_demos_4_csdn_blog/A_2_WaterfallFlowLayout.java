package hanshuliang.com.ui_demos_4_csdn_blog;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class A_2_WaterfallFlowLayout extends ViewGroup {

    public static final String TAG = "A_2_WaterfallFlowLayout";

    /**
     * ArrayList<View> 存放每一行的组件集合
     * ArrayList<ArrayList<View>> 存放多行的 组件集合 的集合
     */
    private ArrayList<ArrayList<View>> childViewsLists = new ArrayList<>();

    /**
     * 每一行的高度集合
     */
    private ArrayList<Integer> heightLists = new ArrayList<>();


    public A_2_WaterfallFlowLayout(Context context) {
        super(context);
    }

    public A_2_WaterfallFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public A_2_WaterfallFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 宽和高 都不规则的组件 进行排列
     *
     * 基本思想 : 确定布局组件的宽度 和 高度, 根据 WaterfallFlowLayout 布局的 width 和 height 属性进行计算
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i(TAG, "onMeasure");

        childViewsLists.clear();
        heightLists.clear();

        //布局测量 : 1. 定义存储测量最终结果的变量
        int width = 0;
        int height = 0;

        //布局测量 : 2. 获取 宽 和 高 的 模式
        int widthMod = MeasureSpec.getMode(widthMeasureSpec);
        int heightMod = MeasureSpec.getMode(heightMeasureSpec);

        //布局测量 : 3. 获取 宽 和 高 的 值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //布局测量 : 4.  根据 宽 和 高 的 模式 和 大小 计算出组件的 宽 和 高
        if(widthMod == MeasureSpec.EXACTLY && heightMod == MeasureSpec.EXACTLY){
            //实际测量 : EXACTLY 模式 : EXACTLY 模式对应布局中的 match_parent 或者 实际px 或 dip 等实际值设置
            width = widthSize;
            height = heightSize;
        }else{
            //实际测量 : AT_MOST 模式 : 如果是其他模式, 那么就需要遍历所有的子组件, 并计算所需要的大小

            //AT_MOST 测量 : 1. 定义变量存储, 累加实时的宽高
            int currentWidth = 0;
            int currentHeight = 0;

            //AT_MOST 测量 : 2. 定义变量存储 子组件的 宽高
            int childWidth = 0;
            int childHeight = 0;

            //AT_MOST 测量 : 3. 存放一行的子组件, 换行时将该队列放入 childViewLists 集合中, 并创建新的集合
            ArrayList<View> childViewList = new ArrayList<>();

            //AT_MOST 测量 : 4. 遍历所有的子组件,
            for(int i = 0; i < getChildCount(); i ++){
                View child = getChildAt(i);

                //子组件测量 : 1. 测量子组件
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                //子组件测量 : 2. 获取子组件的布局参数
                //获取子组件 四个方向的 margin 值, 将布局参数强转为 MarginLayoutParams 类型, 需要重写 generateLayoutParams 方法, 让其返回 MarginLayoutParams 类型
                //注意 : 这里只计算 margin 值, 即 以组件大小为基准, 向外扩展的大小; padding 值是以组件宽高为基准, 向内部的压缩子组件的宽高, 不在测量的考虑范围内
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

                //子组件测量 : 3. 获取子组件占用的实际宽度, 组件大小 + 左右 margin 大小
                childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;


                //子组件测量 : 4. 计算该组件是否需要换行, 当组件实际占用宽度 + 累加宽度 大于组件宽度时, 进行换行操作
                if(childWidth + currentWidth > widthSize){
                    //累加超出了计算的大小, 换行

                    //子组件测量 换行逻辑 : 1.保存当前行, 每次换行的时候都
                    //取值策略 : 两个值相加大于总宽度, 此时该子组件的宽度取 currentWidth 累加值 或 childWidth 子组件中的最大值
                    width = Math.max(width, currentWidth);
                    //如果换行, 那么高度累加
                    height += currentHeight;

                    //更新记录信息
                    heightLists.add(currentHeight);
                    childViewsLists.add(childViewList);


                    //2. 记录新的行信息, 更新当前记录的 宽 和 高
                    currentWidth = childWidth;
                    currentHeight = childHeight;
                    //创建新的行组件记录集合
                    childViewList = new ArrayList<>();
                    childViewList.add(child);

                }else{//累加后可以在本行显示, 不换行

                    //不换行的话, 宽度累加
                    currentWidth += childWidth;
                    //高度设置策略 : 取 childHeight 值 : 如果是第一次累加, currentHeight 为 0, 这时取 currentHeight = childHeight 为最大值
                    //             取 currentHeight 值 : 第一次之后的累加时都是 currentHeight = currentHeight;
                    currentHeight = Math.max(currentHeight, childHeight);
                    //向代表每行组件的 childViewList 集合中添加该子组件
                    childViewList.add(child);
                }

                if(i == getChildCount() - 1){
                    //处理换行逻辑, 虽然没有换行, 但是处理到了最后一个, 需要处理整行信息
                    width = Math.max(width, currentWidth);
                    height += currentHeight;

                    heightLists.add(currentHeight);
                    childViewsLists.add(childViewList);
                }
            }
        }

        //布局测量 : 5. 设置最终测量出来的宽和高
        setMeasuredDimension(width, height);
        Log.i(TAG, "onMeasure width : " + width + " , height : " + height + " , heightLists : " + heightLists.size() + " , childViewsLists : " + childViewsLists.size());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        Log.i(TAG, "onLayout");

        //布局摆放 : 1. 定义用于记录每个组件的 左上右下 坐标的变量
        int left, top, right, bottom;

        //布局摆放 : 2. 定义 用于记录累加的 左 和 上 的坐标
        int currentLeft = 0, currentTop = 0;

        //布局摆放 : 3. 进行布局摆放, 遍历所有的子组件, 并放置子组件, 该层循环是遍历一行组件的 集合, 单个元素是一个组件集合
        for (int i = 0; i < childViewsLists.size(); i ++){
            ArrayList<View> childViewsList = childViewsLists.get(i);

            //该层循环的遍历的是 每行 具体的 子组件 集合, 单个元素是一个子组件
            for(int j = 0; j < childViewsList.size(); j ++){
                View child = childViewsList.get(j);

                //将布局参数转为可获取 左上右下 margin 的 MarginLayoutParams 类型布局参数
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

                //组件的左 上 右 下 的四个位置
                left = currentLeft + marginLayoutParams.leftMargin;
                top = currentTop + marginLayoutParams.topMargin;
                right = left + child.getMeasuredWidth();
                bottom = top + child.getMeasuredHeight();

                //放置子组件
                child.layout(left, top, right, bottom);

                //处理累加数据值 : 累加水平方向的左侧值
                currentLeft = right + marginLayoutParams.rightMargin;
            }

            //处理累加数据 : 重置水平方向的左侧值 为 0, 累加垂直方向的高度值
            currentLeft = 0;
            currentTop += heightLists.get(i);
            childViewsList.clear();
        }

        //布局摆放 : 4. 清空集合, 最大限度及时节省内存
        childViewsLists.clear();
        heightLists.clear();
    }

    /**
     * 子组件获取的 LayoutParams 转成 MarginLayoutParams, 必须在此处将返回值修改为 MarginLayoutParams 对象
     * 否则获取子组件的 布局参数 转为 MarginLayoutParams 类型会出错
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
