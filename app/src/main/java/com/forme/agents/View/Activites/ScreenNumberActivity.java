package com.forme.agents.View.Activites;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.forme.agents.DTO.LoginResponse;
import com.forme.agents.DTO.Stock;
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.R;
import com.forme.agents.View.Fragments.ChangePasswordFragment;
import com.forme.agents.View.Fragments.ContentFragment;
import com.forme.agents.View.Fragments.HomeFragment;
import com.forme.agents.View.Fragments.Type10Fragment;
import com.forme.agents.View.Fragments.Type11Fragment;
import com.forme.agents.View.Fragments.Type1Fragment;
import com.forme.agents.View.Fragments.Type2Fragment;
import com.forme.agents.View.Fragments.Type3Fragment;
import com.forme.agents.View.Fragments.Type4Fragment;
import com.forme.agents.View.Fragments.Type5Fragment;
import com.forme.agents.View.Fragments.Type6Fragment;
import com.forme.agents.View.Fragments.Type7Fragment;
import com.forme.agents.View.Fragments.Type8Fragment;
import com.forme.agents.View.Fragments.Type9Fragment;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class ScreenNumberActivity extends AppCompatActivity implements  Serializable {
    private DrawerLayout drawerLayout;
    PreferenceHelper preferenceHelper;
    private Stock stock;
    private LinearLayout linearLayout;

    public static void start(Context context, boolean clear) {
        Intent intent = new Intent(context, ScreenNumberActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        preferenceHelper = new PreferenceHelper(this);
        stock = (Stock) getIntent().getSerializableExtra("stock");
        onSwitch();


        /*this.getActionBar().setDisplayOptions
                (this.getActionBar().DISPLAY_SHOW_CUSTOM);*/
        //getSupportActionBar().setCustomView(R.layout.action_bar);
    }



    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int position) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, position, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        findViewById(R.id.content_overlay).setBackground(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        return screenShotable;
    }

    public void onSwitch() {

        int primaryColorCanvas = R.color.colorPrimary;


        System.out.println("stock"+stock);
           if(stock.getScreenNumber().equals("1")) {
               Type1Fragment one = new Type1Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, one).commit();
           }
           else if(stock.getScreenNumber().equals("2")) {
               Type2Fragment two = new Type2Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, two).commit();
           }
           else if(stock.getScreenNumber().equals("3")) {

               Type3Fragment three = new Type3Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, three).commit();
           }
           else if(stock.getScreenNumber().equals("4")) {
               Type4Fragment four = new Type4Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, four).commit();
           }
           else if(stock.getScreenNumber().equals("5")) {
               Type5Fragment five = new Type5Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, five).commit();
           }
           else if(stock.getScreenNumber().equals("6")) {
               Type6Fragment six = new Type6Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, six).commit();
           }
           else if(stock.getScreenNumber().equals("7")) {
               Type7Fragment seven = new Type7Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, seven).commit();
           }
           else if(stock.getScreenNumber().equals("8")) {
               Type8Fragment eight = new Type8Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, eight).commit();
           }
           else if(stock.getScreenNumber().equals("9")) {
               Type9Fragment nine = new Type9Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, nine).commit();
           }
           else if(stock.getScreenNumber().equals("10")){
               Type10Fragment ten = new Type10Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ten).commit();
           }
           else if(stock.getScreenNumber().equals("11")) {
               Type11Fragment eleven = new Type11Fragment();
               getIntent().putExtra("stock", stock);
               getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, eleven).commit();
         }

           /* case ContentFragment.COOKBOOK:
                 fragment3 = DashboardFragment.newInstance(primaryColorCanvas);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment3).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment3).commit();
                return replaceFragment(fragment3, position);*/



    }



}
