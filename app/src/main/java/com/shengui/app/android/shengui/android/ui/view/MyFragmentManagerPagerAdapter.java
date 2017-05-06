package com.shengui.app.android.shengui.android.ui.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.base.platform.utils.android.Logger;
import com.shengui.app.android.shengui.android.ui.fragment.BaseInfoActivityFragment;
import com.shengui.app.android.shengui.android.ui.fragment.Fragment1;
import com.shengui.app.android.shengui.models.QuanziList;

/**
 * Created by HongJay on 2016/8/11.
 */
public class MyFragmentManagerPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitles;

    QuanziList models;
    private Fragment1 fragment1;
    private Fragment1 fragment2;
    private Fragment1 fragment3;
    private Fragment1 fragment4;
    private Fragment1 fragment5;
    public void init(){
        fragment1=null;
        fragment2=null;
        fragment3=null;
        fragment4=null;
        fragment5=null;
    }
    public MyFragmentManagerPagerAdapter(FragmentManager fm, String[] mTitle, QuanziList model) {
        super(fm);
       mTitles = mTitle;
        models=model;
        fragment1= new Fragment1().newInstance(models,0);
        fragment2= new Fragment1().newInstance(models,1);
        fragment3= new Fragment1().newInstance(models,2);
        fragment4= new Fragment1().newInstance(models,3);
        fragment5= new Fragment1().newInstance(models,4);
    }

    public void setmodel( QuanziList m){
        models=m;

    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return  fragment1;
//            return fragment1 == null ? new Fragment1().newInstance(models,position) : fragment1;
//            return new Fragment1.newInstance(models,position);
        } else if (position == 1) {
            return  fragment2;
//            return fragment2 == null ? new Fragment1().newInstance(models,position) : fragment2;
//            return new Fragment1();
        } else if(position==2){
            return  fragment3;
//            return fragment3 == null ? new Fragment1().newInstance(models,position) : fragment3;
//            return new Fragment1();
        } else if(position==3){
            return  fragment4;
//            return fragment4 == null ? new Fragment1().newInstance(models,position) : fragment4;
//            return new Fragment1();
        }
        return  fragment5;
//        return fragment5 == null ? new Fragment1().newInstance(models,position) : fragment5;
//        return new Fragment1();
    }
    private int mChildCount;
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    public void refresh (){
        if(getCount()>0){
            if(getCount()==2){
                if(fragment1!=null){
                    fragment1.setModel(models);
                    fragment1.initdata();
                }
                if(fragment2!=null){
                    fragment1.setModel(models);
                    fragment2.initdata();
                }
            }else if(getCount()==3){
                if(fragment1!=null){
                    fragment1.setModel(models);
                    fragment1.initdata();
                }
                if(fragment2!=null){
                    fragment1.setModel(models);
                    fragment2.initdata();
                }
                if(fragment3!=null){
                    fragment1.setModel(models);
                    fragment3.initdata();
                }
            }else if(getCount()==4){
                if(fragment1!=null){
                    fragment1.setModel(models);
                    fragment1.initdata();
                }
                if(fragment2!=null){
                    fragment1.setModel(models);
                    fragment2.initdata();
                }
                if(fragment3!=null){
                    fragment1.setModel(models);
                    fragment3.initdata();
                }
                if(fragment4!=null){
                    fragment1.setModel(models);
                    fragment4.initdata();
                }
            }else if(getCount()==5){
                Logger.e("------getCount--------"+fragment1);
                if(fragment1!=null){
                    fragment1.setModel(models);
                    fragment1.initdata();
                }
                if(fragment2!=null){
                    fragment1.setModel(models);
                    fragment2.initdata();
                }
                if(fragment3!=null){
                    fragment1.setModel(models);
                    fragment3.initdata();
                }
                if(fragment4!=null){
                    fragment1.setModel(models);
                    fragment4.initdata();
                }
                if(fragment5!=null){
                    fragment1.setModel(models);
                    fragment5.initdata();
                }
            }
        }


    }
    public void refresh ( QuanziList model){
        if(getCount()>0){
            if(getCount()==2){
                if(fragment1!=null){
                    fragment1.initdata(model,0);
                }
                if(fragment2!=null){
                    fragment2.initdata(model,1);
                }
            }else if(getCount()==3){
                if(fragment1!=null){
                    fragment1.initdata(model,0);
                }
                if(fragment2!=null){
                    fragment2.initdata(model,1);
                }
                if(fragment3!=null){
                    fragment3.initdata(model,2);
                }
            }else if(getCount()==4){
                if(fragment1!=null){
                    fragment1.initdata(model,0);
                }
                if(fragment2!=null){
                    fragment2.initdata(model,1);
                }
                if(fragment3!=null){
                    fragment3.initdata(model,2);
                }
                if(fragment4!=null){
                    fragment4.initdata(model,3);
                }
            }else if(getCount()==5){
                Logger.e("------getCount--------"+fragment1);
                if(fragment1!=null){
                    fragment1.initdata(model,0);
                }
                if(fragment2!=null){
                    fragment2.initdata(model,1);
                }
                if(fragment3!=null){
                    fragment3.initdata(model,2);
                }
                if(fragment4!=null){
                    fragment4.initdata(model,3);
                }
                if(fragment5!=null){
                    fragment5.initdata(model,4);
                }
            }
        }


    }
    @Override
    public int getCount() {
        return mTitles.length;
    }

    //用来设置tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
