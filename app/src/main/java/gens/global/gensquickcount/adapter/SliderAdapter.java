package gens.global.gensquickcount.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import gens.global.gensquickcount.fragment.FragmentSlider;

public class SliderAdapter extends FragmentStatePagerAdapter {

    List<Fragment> mFrags;

    public SliderAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        mFrags = frags;
    }

    @Override
    public Fragment getItem(int position) {
        int index = position % mFrags.size();
        return FragmentSlider.newInstance(mFrags.get(index).getArguments().getString("imgSlider"));
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
}
