package com.example.targil4.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.targil4.AdminAddCategoryFragment;
import com.example.targil4.AdminAddMovieFragment;
import com.example.targil4.AdminDeleteCategoryFragment;
import com.example.targil4.AdminEditCategoryFragment;

import java.util.Arrays;
import java.util.List;

public class AdminPagerAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList;

    public AdminPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentList = Arrays.asList(
                new AdminAddMovieFragment(),
                new AdminAddMovieFragment(),
                new AdminAddMovieFragment(),
                new AdminAddCategoryFragment(),
                new AdminEditCategoryFragment(),
                new AdminDeleteCategoryFragment()
        );
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
