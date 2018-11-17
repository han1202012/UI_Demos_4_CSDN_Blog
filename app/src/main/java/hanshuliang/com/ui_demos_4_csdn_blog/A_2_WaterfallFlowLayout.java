package hanshuliang.com.ui_demos_4_csdn_blog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class A_2_WaterfallFlowLayout extends ViewGroup {


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
    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

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

            //AT_MOST 测量 : 2. 遍历所有的子组件,
            for(int i = 0; i < getChildCount(); i ++){
                View child = getChildAt(i);
                int childWidth = 0;
                int childHeight = 0;

                //测量子组件
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                //获取子组件 四个方向的 margin 值, 将布局参数强转为 MarginLayoutParams 类型, 需要重写 generateLayoutParams 方法, 让其返回 MarginLayoutParams 类型
                //注意 : 这里只计算 margin 值, 即 以组件大小为基准, 向外扩展的大小; padding 值是以组件宽高为基准, 向内部的压缩子组件的宽高, 不在测量的考虑范围内
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();

                //获取当前组件的实际宽度
                childWidth = child.getMeasuredWidth() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
                childHeight = child.getMeasuredHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;


                //存放一行的子组件, 换行时将该队列放入 childViewLists 集合中, 并创建新的集合
                ArrayList<View> childViewList = new ArrayList<>();
                //计算该组件是否换行
                if(childWidth + currentWidth > width){
                    //累加超出了计算的大小, 换行

                    //1.保存当前行, 每次换行的时候都
                    //取值策略 : 两个值相加大于总宽度, 此时该子组件的宽度取 currentWidth 累加值 或 childWidth 子组件中的最大值
                    width = Math.max(currentWidth, childWidth);
                    //如果换行, 那么高度累加
                    height += currentHeight;

                    //更新记录信息
                    heightLists.add(height);
                    childViewsLists.add(childViewList);


                    //2. 记录新的行信息, 更新当前记录的 宽 和 高
                    currentWidth = childWidth;
                    currentHeight = currentWidth;
                    //创建新的行组件记录集合
                    childViewList = new ArrayList<>();

                }else{//累加后可以在本行显示, 不换行
                    //不换行的话, 宽度累加
                    currentWidth += childWidth;
                    //高度设置策略 : 取 childHeight 值 : 如果是第一次累加, currentHeight 为 0, 这时取 currentHeight = childHeight 为最大值
                    //             取 currentHeight 值 : 第一次之后的累加时都是 currentHeight = currentHeight;
                    currentHeight = Math.max(currentHeight, childHeight);
                    //向代表每行组件的 childViewList 集合中添加该子组件
                    childViewList.add(child);

                    if(i == getChildCount() - 1){
                        //处理换行逻辑, 虽然没有换行, 但是处理到了最后一个, 需要处理整行信息
                        width = Math.max(currentWidth, childWidth);
                        height += currentHeight;

                        heightLists.add(height);
                        childViewsLists.add(childViewList);
                    }
                }
            }

            //设置最终测量出来的宽和高
            setMeasuredDimension(width, height);
        }

    }*/


    //List<Integer> lstLineHegiht = new ArrayList<>();
    //List<List<View>> lstLineView = new ArrayList<>();
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //1.先完成自己的宽高测量
        //需要得到mode进行判断我的显示模式是怎样的
        //得到宽高模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //父容器宽高
        int widthSize =  MeasureSpec.getSize(widthMeasureSpec);
        int heightSize =  MeasureSpec.getSize(heightMeasureSpec);

        //当前控件宽高(自己)
        int measureWidth = 0;
        int measureHeight = 0;


        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY ){
            measureWidth = widthSize;
            measureHeight = heightSize;
        }else{

            //当前行高宽
            int iChildHegiht = 0;
            int iChildWidth = 0;

            int iCurWidth = 0;
            int iCurHeight = 0;


            //数量
            int childCount = getChildCount();

            ArrayList<View> viewList = new ArrayList<>();
            for (int i = 0;i < childCount ; i++){
                //确定两个事情，当前行高，当前行宽
                View child = getChildAt(i);
                //先让子控件测量自己
                measureChild(child,widthMeasureSpec,heightMeasureSpec);

                //MARGIN 获取到当前LayoutParams
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

                //获取实际宽高
                iChildWidth = child.getMeasuredWidth() + layoutParams.rightMargin + layoutParams.leftMargin;
                iChildHegiht = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;


                //是否需要开始换行
                if(iChildWidth + iCurWidth > widthSize){
                    //1.保存当前行信息
                    measureWidth = Math.max(measureWidth,iCurWidth);
                    measureHeight += iCurHeight;
                    heightLists.add(iCurHeight);
                    childViewsLists.add(viewList);


                    //更新的行信息
                    iCurWidth = iChildWidth;
                    iCurHeight = iChildHegiht;

                    viewList = new ArrayList<>();
                    viewList.add(child);



                    //2.记录新行信息
                }else{
                    iCurWidth += iChildWidth;
                    iCurHeight = Math.max(iCurHeight,iChildHegiht);

                    viewList.add(child);

                }

                //6.如果正好是最后一行需要换行
                if(i == childCount - 1){
                    //6.1.记录当前行的最大宽度，高度累加
                    measureWidth = Math.max(measureWidth,iCurWidth);
                    measureHeight += iCurHeight;


                    //6.2.将当前行的viewList添加至总的mViewsList，将行高添加至总的行高List
                    childViewsLists.add(viewList);
                    heightLists.add(iCurHeight);

                }



            }

        }

        setMeasuredDimension(measureWidth,measureHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //用于记录每个组件的 左上右下 坐标的变量
        int left, top, right, bottom;
        //用于记录累加的 左 和 上 的坐标
        int currentLeft = 0, currentTop = 0;

        //遍历所有的子组件, 并放置子组件, 该层循环是遍历一行组件的 集合, 单个元素是一个组件集合
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

        //清空集合, 最大限度节省内存
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
