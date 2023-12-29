package gens.global.gensquickcount.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import gens.global.gensquickcount.fragment.DataQuickFragment;
import gens.global.gensquickcount.fragment.DataSurveyFragment;

public class DtaTabAdapter extends FragmentPagerAdapter {

    public DtaTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new DataSurveyFragment();
        }
        else if (position == 1)
        {
            fragment = new DataQuickFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Survey Suara";
        }
        else if (position == 1)
        {
            title = "Hitung Cepat";
        }
        return title;
    }
}
