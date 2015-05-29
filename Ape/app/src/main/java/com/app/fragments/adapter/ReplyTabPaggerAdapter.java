package com.app.fragments.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.ape.constants.Const;
import com.app.ape.helper.CommentTag;
import com.app.fragments.ChallengeFragment;
import com.app.fragments.FeedFragment;
import com.app.fragments.LoadingFragment;
import com.app.fragments.ReplyFragment;

import org.w3c.dom.Comment;

import java.util.HashMap;

/**
 * Created by ionel.merca on 4/30/2015.
 */
public class ReplyTabPaggerAdapter extends FragmentStatePagerAdapter {

    public Activity activity = null;
    public CommentTag reply = null;

    public ReplyTabPaggerAdapter(FragmentManager fm, Activity activity, CommentTag reply) {
        super(fm);
        this.activity = activity;
        this.reply = reply;
    }


    @Override
    public Fragment getItem(int item) {

        switch (item) {
            case 0:
                return ReplyFragment.newInstance(reply);

            case 1:
                return new ChallengeFragment();

            case 2:
                return new FeedFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }
}
