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
import com.forme.agents.Helper.PreferenceHelper;
import com.forme.agents.R;
import com.forme.agents.View.Fragments.AllowusersFragment;
import com.forme.agents.View.Fragments.ChangePasswordFragment;
import com.forme.agents.View.Fragments.ContentFragment;
import com.forme.agents.View.Fragments.HomeFragment;
import com.forme.agents.View.Fragments.ShowusersFragment;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    Context context;
    PreferenceHelper preferenceHelper;
    TextView nav_header_subtitle;
    ActionBar actionbar;
    private LinearLayout linearLayout;

    public static void start(Context context, boolean clear) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeFragment Home = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,Home).commit();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        preferenceHelper = new PreferenceHelper(this);
        linearLayout = findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
         actionbar = getSupportActionBar();
        getSupportActionBar().setDisplayOptions
                (actionbar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(getLayoutInflater().inflate(R.layout.action_bar, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT
                       // Gravity.CENTER
                )
        );
        /*this.getActionBar().setDisplayOptions
                (this.getActionBar().DISPLAY_SHOW_CUSTOM);*/
        //getSupportActionBar().setCustomView(R.layout.action_bar);
        viewAnimator = new ViewAnimator<>(this, list, Home, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.HOME, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.CATALOG, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.DEMAND, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.SEARCH, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.ACCOUNT, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PAY, R.drawable.icn_6);
        list.add(menuItem6);
        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.SARF, R.drawable.icn_7);
        list.add(menuItem7);
        SlideMenuItem menuItem8 = new SlideMenuItem(ContentFragment.ALLOWUSER, R.drawable.icn_8);
        list.add(menuItem8);
        SlideMenuItem menuItem9 = new SlideMenuItem(ContentFragment.SHOWUSER, R.drawable.icn_9);
        list.add(menuItem9);
        SlideMenuItem menuItem10 = new SlideMenuItem(ContentFragment.NOTIF, R.drawable.icn_10);
        list.add(menuItem10);
        SlideMenuItem menuItem11 = new SlideMenuItem(ContentFragment.ADD, R.drawable.icn_11);
        list.add(menuItem11);
        SlideMenuItem menuItem12 = new SlideMenuItem(ContentFragment.CHANGEPASS, R.drawable.icn_12);
        list.add(menuItem12);
        SlideMenuItem menuItem13 = new SlideMenuItem(ContentFragment.LOGOUT, R.drawable.icn_13);
        list.add(menuItem13);
    }


    private void setActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }


                return super.onOptionsItemSelected(item);

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
    @Override
    public ScreenShotable onSwitch(Resourceble resourceble, ScreenShotable screenShotable, int position) {

        int primaryColorCanvas = R.color.colorPrimary;

        switch (resourceble.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;

            case ContentFragment.HOME:
                HomeFragment Home = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,Home).commit();
                return replaceFragment(Home, position);



            case ContentFragment.CHANGEPASS:
                ChangePasswordFragment passwordFragment = new ChangePasswordFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,passwordFragment).commit();
                return replaceFragment(passwordFragment, position);

            case ContentFragment.ALLOWUSER:
                AllowusersFragment allowUsersFragment = new AllowusersFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,allowUsersFragment).commit();
                return replaceFragment(allowUsersFragment, position);

            case ContentFragment.SHOWUSER:
                ShowusersFragment showUsersFragment = new ShowusersFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,showUsersFragment).commit();
                return replaceFragment(showUsersFragment, position);

            case ContentFragment.LOGOUT:
                comfirmLogout();

            default:
                HomeFragment Home1 = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,Home1).commit();
                return replaceFragment(Home1, position);
        }
    }


    AlertDialog dialog;

    //دالة الخروج من البرنامج
    private void comfirmLogout() {
        preferenceHelper.saveLoginData("");
        LoginActivity.start(MainActivity.this, true);
    }




    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    private void fillName() {
        String loginData = preferenceHelper.getLoginData();
        try {
            LoginResponse loginResponse = new Gson().fromJson(loginData, LoginResponse.class);
            nav_header_subtitle.setText(loginResponse.username);

        } catch (Exception ex) {
            LoginActivity.start(this, true);
        }
    }
    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
