package com.job.tuchat.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.job.tuchat.mainFragments.ChatsFragment;
import com.job.tuchat.mainFragments.FriendsFragment;
import com.job.tuchat.mainFragments.RequestFragment;

/**
 * Created by Job on Monday : 3/26/2018.
 */

public class TabsFragmentAdapter extends FragmentPagerAdapter {

    public TabsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new RequestFragment();
            case 1:
                return new ChatsFragment();
            case 2:
                return new FriendsFragment();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
         super.getPageTitle(position);

         switch (position){
             case 0:
                 return "REQUESTS";
             case 1:
                 return "CHATS";
             case 2:
                 return "FRIENDS";
                 default:
                     return null;
         }
    }
}
